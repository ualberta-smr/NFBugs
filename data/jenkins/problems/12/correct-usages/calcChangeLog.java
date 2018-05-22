package hudson.scm;

import hudson.EnvVars;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.Launcher;
import hudson.Proc;
import hudson.Util;
import hudson.security.Permission;
import static hudson.Util.fixEmpty;
import static hudson.Util.fixNull;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.model.Hudson;
import hudson.model.Job;
import hudson.model.ModelObject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.TaskThread;
import hudson.org.apache.tools.ant.taskdefs.cvslib.ChangeLogTask;
import hudson.remoting.Future;
import hudson.remoting.RemoteOutputStream;
import hudson.remoting.VirtualChannel;
import hudson.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


private boolean calcChangeLog(AbstractBuild build, FilePath ws, final List<String> changedFiles, File changelogFile, final BuildListener listener) throws InterruptedException {
        if(build.getPreviousBuild()==null || (changedFiles!=null && changedFiles.isEmpty())) {
            // nothing to compare against, or no changes
            // (note that changedFiles==null means fallback, so we have to run cvs log.
            listener.getLogger().println("$ no changes detected");
            return createEmptyChangeLog(changelogFile,listener, "changelog");
        }
        if(skipChangeLog) {
            listener.getLogger().println("Skipping changelog computation");
            return createEmptyChangeLog(changelogFile,listener, "changelog");
        }

        listener.getLogger().println("$ computing changelog");

        final String cvspassFile = getDescriptor().getCvspassFile();
        final String cvsExe = getDescriptor().getCvsExe();

        OutputStream o = null;
        try {
            // range of time for detecting changes
            final Date startTime = build.getPreviousBuild().getTimestamp().getTime();
            final Date endTime = build.getTimestamp().getTime();
            final OutputStream out = o = new RemoteOutputStream(new FileOutputStream(changelogFile));

            ChangeLogResult result = ws.act(new FileCallable<ChangeLogResult>() {
                public ChangeLogResult invoke(File ws, VirtualChannel channel) throws IOException {
                    final StringWriter errorOutput = new StringWriter();
                    final boolean[] hadError = new boolean[1];

                    ChangeLogTask task = new ChangeLogTask() {
                        public void log(String msg, int msgLevel) {
                            // send error to listener. This seems like the route in which the changelog task
                            // sends output
                            if(msgLevel==org.apache.tools.ant.Project.MSG_ERR) {
                                hadError[0] = true;
                                errorOutput.write(msg);
                                errorOutput.write('\n');
                                return;
                            }
                            if(debug) {
                                listener.getLogger().println(msg);
                            }
                        }
                    };
                    task.setProject(new org.apache.tools.ant.Project());
                    task.setCvsExe(cvsExe);
                    task.setDir(ws);
                    if(cvspassFile.length()!=0)
                        task.setPassfile(new File(cvspassFile));
                    if (canUseUpdate && cvsroot.startsWith("/")) {
                        // cvs log of built source trees unreliable in local access method:
                        // https://savannah.nongnu.org/bugs/index.php?15223
                        task.setCvsRoot(":fork:" + cvsroot);
                    } else if (canUseUpdate && cvsroot.startsWith(":local:")) {
                        task.setCvsRoot(":fork:" + cvsroot.substring(7));
                    } else {
                        task.setCvsRoot(cvsroot);
                    }
                    task.setCvsRsh(cvsRsh);
                    task.setFailOnError(true);
                    BufferedOutputStream bufferedOutput = new BufferedOutputStream(out);
                    task.setDeststream(bufferedOutput);
                    task.setBranch(branch);
                    task.setStart(startTime);
                    task.setEnd(endTime);
                    if(changedFiles!=null) {
                        // we can optimize the processing if we know what files have changed.
                        // but also try not to make the command line too long so as no to hit
                        // the system call limit to the command line length (see issue #389)
                        // the choice of the number is arbitrary, but normally we don't really
                        // expect continuous builds to have too many changes, so this should be OK.
                        if(changedFiles.size()<100 || !Hudson.isWindows()) {
                            // if the directory doesn't exist, cvs changelog will die, so filter them out.
                            // this means we'll lose the log of those changes
                            for (String filePath : changedFiles) {
                                if(new File(ws,filePath).getParentFile().exists())
                                    task.addFile(filePath);
                            }
                        }
                    } else {
                        // fallback
                        if(!flatten)
                            task.setPackage(getAllModulesNormalized());
                    }

                    try {
                        task.execute();
                    } catch (BuildException e) {
                        throw new BuildExceptionWithLog(e,errorOutput.toString());
                    } finally {
                        bufferedOutput.close();
                    }

                    return new ChangeLogResult(hadError[0],errorOutput.toString());
                }
            });

            if(result.hadError) {
                // non-fatal error must have occurred, such as cvs changelog parsing error.s
                listener.getLogger().print(result.errorOutput);
            }
            return true;
        } catch( BuildExceptionWithLog e ) {
            // capture output from the task for diagnosis
            listener.getLogger().print(e.errorOutput);
            // then report an error
            BuildException x = (BuildException) e.getCause();
            PrintWriter w = listener.error(x.getMessage());
            w.println("Working directory is "+ ws);
            x.printStackTrace(w);
            return false;
        } catch( RuntimeException e ) {
            // an user reported a NPE inside the changeLog task.
            // we don't want a bug in Ant to prevent a build.
            e.printStackTrace(listener.error(e.getMessage()));
            return true;    // so record the message but continue
        } catch( IOException e ) {
            e.printStackTrace(listener.error("Failed to detect changlog"));
            return true;
        } finally {
            IOUtils.closeQuietly(o);
        }
    }

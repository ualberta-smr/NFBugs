 package hudson.scm;

import static hudson.Util.fixNull;
import hudson.FilePath;
import hudson.FilePath.FileCallable;
import hudson.Launcher;
import hudson.Proc;
import hudson.Util;
import static hudson.Util.fixEmpty;
import hudson.model.AbstractBuild;
import hudson.model.AbstractModelObject;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Hudson;
import hudson.model.Job;
import hudson.model.LargeText;
import hudson.model.ModelObject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.org.apache.tools.ant.taskdefs.cvslib.ChangeLogTask;
import hudson.remoting.RemoteOutputStream;
import hudson.remoting.VirtualChannel;
import hudson.util.ArgumentListBuilder;
import hudson.util.ByteBuffer;
import hudson.util.ForkOutputStream;
import hudson.util.FormFieldValidator;
import hudson.util.StreamTaskListener;
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
import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public synchronized void doSubmit(StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
            if(!Hudson.adminCheck(req,rsp))
                return;

            Map<AbstractBuild,String> tagSet = new HashMap<AbstractBuild,String>();

            String name = req.getParameter("name");
            if(isInvalidTag(name)) {
                sendError("No valid tag name given",req,rsp);
                return;
            }

            tagSet.put(build,name);

            if(req.getParameter("upstream")!=null) {
                // tag all upstream builds
                Enumeration e = req.getParameterNames();
                Map<AbstractProject, Integer> upstreams = build.getUpstreamBuilds(); // TODO: define them at AbstractBuild level

                while(e.hasMoreElements()) {
                    String upName = (String) e.nextElement();
                    if(!upName.startsWith("upstream."))
                        continue;

                    String tag = req.getParameter(upName);
                    if(isInvalidTag(tag)) {
                        sendError("No valid tag name given for "+upName,req,rsp);
                        return;
                    }

                    upName = upName.substring(9);   // trim off 'upstream.'
                    Job p = Hudson.getInstance().getItemByFullName(upName,Job.class);

                    Run build = p.getBuildByNumber(upstreams.get(p));
                    tagSet.put((AbstractBuild) build,tag);
                }
            }

            new TagWorkerThread(tagSet).start();

            doIndex(req,rsp);
        }

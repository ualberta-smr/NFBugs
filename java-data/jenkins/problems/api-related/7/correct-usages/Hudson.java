package hudson.model;

import com.thoughtworks.xstream.XStream;
import groovy.lang.GroovyShell;
import hudson.FeedAdapter;
import hudson.Launcher;
import hudson.Launcher.LocalLauncher;
import hudson.Plugin;
import hudson.PluginManager;
import hudson.PluginWrapper;
import hudson.Util;
import hudson.XmlFile;
import hudson.FilePath;
import static hudson.Util.fixEmpty;
import hudson.model.Descriptor.FormException;
import hudson.model.listeners.ItemListener;
import hudson.model.listeners.JobListener;
import hudson.model.listeners.JobListener.JobListenerAdapter;
import hudson.model.listeners.SCMListener;
import hudson.remoting.LocalChannel;
import hudson.remoting.VirtualChannel;
import hudson.scm.CVSSCM;
import hudson.scm.SCM;
import hudson.scm.SCMS;
import hudson.tasks.BuildStep;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrappers;
import hudson.tasks.Builder;
import hudson.tasks.Mailer;
import hudson.tasks.Publisher;
import hudson.triggers.Trigger;
import hudson.triggers.Triggers;
import hudson.triggers.TriggerDescriptor;
import hudson.util.CopyOnWriteList;
import hudson.util.CopyOnWriteMap;
import hudson.util.FormFieldValidator;
import hudson.util.XStream2;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.StringTokenizer;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Root object of the system.
 * @author Kohsuke Kawaguchi
 */
public final class Hudson extends View implements ItemGroup<TopLevelItem>, Node {
  /**
  * Called once the user logs in. Just forward to the top page.
  */
    public void doLoginEntry( StaplerRequest req, StaplerResponse rsp ) throws IOException {
        if(req.getUserPrincipal()==null)
            rsp.sendRedirect2("noPrincipal");
        else
            rsp.sendRedirect2(".");
     }




}

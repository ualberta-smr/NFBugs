package hudson;

import hudson.util.IOException2;
import hudson.model.Hudson;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.types.FileSet;
import org.apache.commons.logging.LogFactory;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;
import java.util.logging.Logger;

private static final class Dependency {
        
        public void pattern(StaplerRequest req, StaplerResponse rsp) throws IOException {
                if(!Hudson.adminCheck(req,rsp))
                    return;
                enable();
                rsp.setStatus(200);
            }
}

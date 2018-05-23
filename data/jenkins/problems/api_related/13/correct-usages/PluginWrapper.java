package hudson;

import hudson.util.IOException2;
import hudson.util.VersionNumber;
import hudson.model.Hudson;
import hudson.model.UpdateCenter;
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

public PluginWrapper(PluginManager owner, File archive) throws IOException {
        LOGGER.info("Loading plugin: "+archive);
        this.archive = archive;

        boolean isLinked = archive.getName().endsWith(".hpl");

        File expandDir = null;  // if .hpi, this is the directory where war is expanded

        if(isLinked) {
            // resolve the .hpl file to the location of the manifest file
            BufferedReader archiveReader = new BufferedReader(new FileReader(archive));
            try {
                String firstLine = archiveReader.readLine();
                if(firstLine.startsWith("Manifest-Version:")) {
                    // this is the manifest already
                } else {
                    // indirection
                    archive = resolve(archive, firstLine);
                }
            } finally {
                archiveReader.close();
            }
            // then parse manifest
            FileInputStream in = new FileInputStream(archive);
            try {
                manifest = new Manifest(in);
            } catch(IOException e) {
                throw new IOException2("Failed to load "+archive,e);
            } finally {
                in.close();
            }
        } else {
            expandDir = new File(archive.getParentFile(), getBaseName(archive));
            explode(archive,expandDir);

            File manifestFile = new File(expandDir,"META-INF/MANIFEST.MF");
            if(!manifestFile.exists()) {
                throw new IOException("Plugin installation failed. No manifest at "+manifestFile);
            }
            FileInputStream fin = new FileInputStream(manifestFile);
            try {
                manifest = new Manifest(fin);
            } finally {
                fin.close();
            }
        }

        this.shortName = computeShortName(manifest,archive);

        // TODO: define a mechanism to hide classes
        // String export = manifest.getMainAttributes().getValue("Export");

        List<URL> paths = new ArrayList<URL>();
        if(isLinked) {
            parseClassPath(archive, paths, "Libraries", ",");
            parseClassPath(archive, paths, "Class-Path", " +"); // backward compatibility

            this.baseResourceURL = resolve(archive,
                manifest.getMainAttributes().getValue("Resource-Path")).toURL();
        } else {
            File classes = new File(expandDir,"WEB-INF/classes");
            if(classes.exists())
                paths.add(classes.toURL());
            File lib = new File(expandDir,"WEB-INF/lib");
            File[] libs = lib.listFiles(JAR_FILTER);
            if(libs!=null) {
                for (File jar : libs)
                    paths.add(jar.toURL());
            }

            this.baseResourceURL = expandDir.toURL();
        }
        ClassLoader dependencyLoader = new DependencyClassLoader(getClass().getClassLoader(),owner);
        this.classLoader = new URLClassLoader(paths.toArray(new URL[0]), dependencyLoader);

        disableFile = new File(archive.getPath()+".disabled");
        if(disableFile.exists()) {
            LOGGER.info("Plugin is disabled");
            this.active = false;
        } else {
            this.active = true;
        }

        // compute dependencies
        String v = manifest.getMainAttributes().getValue("Plugin-Dependencies");
        if(v!=null) {
            for(String s : v.split(",")) {
                Dependency d = new Dependency(s);
                if (d.optional) {
                    optionalDependencies.add(d);
                } else {
                    dependencies.add(d);
                }
            }
        }
    }

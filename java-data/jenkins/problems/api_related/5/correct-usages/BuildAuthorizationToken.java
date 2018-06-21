package hudson.model;

import com.thoughtworks.xstream.converters.basic.AbstractBasicConverter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import javax.servlet.ServletException;
import java.io.IOException;

public final class BuildAuthorizationToken {
        public static void startBuildIfAuthorized(BuildAuthorizationToken token, BuildableItem job, StaplerRequest req, StaplerResponse rsp) throws IOException, ServletException {
                if(!Hudson.getInstance().isUseSecurity() || (token!=null && token.authorizedToStartBuild(req,rsp))) {
                    job.scheduleBuild();
                    rsp.forwardToPreviousPage(req);
                }
            }
}

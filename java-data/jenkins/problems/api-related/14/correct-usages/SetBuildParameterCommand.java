package hudson.cli;

import hudson.Extension;
import hudson.model.ParametersAction;
import hudson.model.Run;
import hudson.model.StringParameterValue;
import org.kohsuke.args4j.Argument;

import java.util.Collections;

public class SetBuildParameterCommand extends CommandDuringBuild {

    @Override
    protected int run() throws Exception {
        Run r = getCurrentlyBuilding();
        r.checkPermission(Run.UPDATE);

        ParametersAction a = r.getAction(ParametersAction.class);
        return 0;
    }   
}

package oracle;

import configfile.ConfigFileReader;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;

/**
 * @author panc
 */
public final class OracleUserReviewsAnalyzer {
    //TODO: this is the same code as runReqSpecRScript
    public static void runUserReviewRScript(ConfigFileReader cfr) throws ExecutionException, RunFailedException {
        OracleRequirementSpecificationsAnalyzer.runReqSpecRScript(cfr);
    }
}

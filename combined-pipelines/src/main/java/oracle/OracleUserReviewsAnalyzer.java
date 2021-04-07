package oracle;

import configfile.ConfigFileReader;
import java.io.IOException;


/**
 * @author panc
 */
public class OracleUserReviewsAnalyzer {
    //TODO: this is the same code as runReqSpecRScript
    public static void runUserReviewRScript(ConfigFileReader cfr) throws IOException {
        OracleRequirementSpecificationsAnalyzer.runReqSpecRScript(cfr);
    }
}

package oracle;

import configfile.ConfigFileReader;
import helpers.RscriptExecutor;
import java.io.IOException;
import java.util.logging.Logger;


/**
 * @author panc
 */
public final class OracleRequirementSpecificationsAnalyzer {

    private static final Logger logger = Logger.getLogger(OracleUserReviewsAnalyzer.class.getName());


    public static void runReqSpecRScript(ConfigFileReader cfr) throws IOException {
        String pathRScriptOracle = cfr.getPathRScriptOracle();
        String baseFolder = cfr.getPathBaseFolder();
        String oraclePath = cfr.getPathTruthSet();
        String nameOfAttributeID = cfr.getNameOfAttributeID();
        String nameOfAttributeText = cfr.getNameOfAttributeText();
        String nameOfAttributeClass = cfr.getNameOfAttributeClass();
        String threshold = String.valueOf(cfr.getThreshold());

        //make command for r-script
        logger.info("R-Script execution begins:");
        RscriptExecutor.execute(pathRScriptOracle, baseFolder, oraclePath, threshold,
                nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);
    }
}

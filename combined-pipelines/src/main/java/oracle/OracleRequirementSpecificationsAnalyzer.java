package oracle;

import configfile.ConfigFileReader;
import helpers.ProcessExecutor;

import java.io.IOException;
import java.util.logging.Logger;


/**
 * @author panc
 */
public class OracleRequirementSpecificationsAnalyzer {

    private final static Logger logger = Logger.getLogger(OracleUserReviewsAnalyzer.class.getName());


    public static void runReqSpecRScript(ConfigFileReader cfr) throws IOException {
        String pathRScriptOracle = cfr.getPathRScriptOracle();
        String baseFolder = cfr.getPathBaseFolder();
        String oracle_path = cfr.getPathTruthSet();
        String nameOfAttributeID = cfr.getNameOfAttributeID();
        String nameOfAttributeText = cfr.getNameOfAttributeText();
        String nameOfAttributeClass = cfr.getNameOfAttributeClass();
        String threshold = String.valueOf(cfr.getThreshold());

        //make command for r-script
        logger.info("R-Script execution begins:");
        ProcessExecutor.execute("Rscript", pathRScriptOracle, baseFolder, oracle_path, threshold,
                nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);
    }
}

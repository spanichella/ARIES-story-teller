package oracle;

import configfile.ConfigFileReader;
import helpers.RscriptExecutor;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;
import java.util.logging.Logger;


/**
 * @author panc
 */
public final class OracleAnalyzer {
    private static final Logger LOGGER = Logger.getLogger(OracleAnalyzer.class.getName());

    public static void runRScript(ConfigFileReader cfr) throws ExecutionException, RunFailedException {
        //make command for r-script
        LOGGER.info("R-Script execution begins:");
        RscriptExecutor.execute(cfr.pathRScriptOracle, cfr.pathBaseFolder, cfr.pathTruthSet, cfr.threshold,
                cfr.nameOfAttributeID, cfr.nameOfAttributeText, cfr.nameOfAttributeClass);
    }
}

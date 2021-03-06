package filegeneration;

import config.Configuration;
import helpers.RscriptExecutor;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;
import java.util.logging.Logger;


/**
 * @author panc
 */
final class OracleAnalyzer {
    private static final Logger LOGGER = Logger.getLogger(OracleAnalyzer.class.getName());

    static void runRScript(Configuration cfr) throws ExecutionException, RunFailedException {
        //make command for r-script
        LOGGER.info("R-Script execution begins:");
        RscriptExecutor.execute(cfr.pathRScriptOracle.toString(), cfr.pathBaseFolder.toString(), cfr.pathTruthSet.toString(),
            cfr.threshold.toPlainString(), Configuration.nameOfAttributeID, cfr.nameOfAttributeText, Configuration.nameOfAttributeClass);
    }
}

package oracle;

import helpers.RscriptExecutor;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;
import java.util.logging.Logger;
import pipelines.Configuration;


/**
 * @author panc
 */
public final class OracleAnalyzer {
    private static final Logger LOGGER = Logger.getLogger(OracleAnalyzer.class.getName());

    public static void runRScript(Configuration cfr) throws ExecutionException, RunFailedException {
        //make command for r-script
        LOGGER.info("R-Script execution begins:");
        RscriptExecutor.execute(cfr.pathRScriptOracle.toString(), cfr.pathBaseFolder.toString(), cfr.pathTruthSet.toString(),
            cfr.threshold.toPlainString(), cfr.nameOfAttributeID, cfr.nameOfAttributeText, cfr.nameOfAttributeClass);
    }
}

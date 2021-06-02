package filegeneration;

import config.Configuration;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;
import java.util.logging.Logger;

public final class FileGeneration {
    private static final Logger LOGGER = Logger.getLogger(FileGeneration.class.getName());

    public static void oracleAnalysis(Configuration configuration) throws ExecutionException, RunFailedException {
        //Select Oracle Analysis according to data type
        LOGGER.info("Oracle getting analyzed...");
        OracleAnalyzer.runRScript(configuration);
        LOGGER.info("Oracle analysis completed");

        LOGGER.info("Creating & analyzing TBD Matrices...");
        TermByDocumentCreation.createTBD(configuration);
        LOGGER.info("Creating & analyzing TBD Matrices completed");
    }
}

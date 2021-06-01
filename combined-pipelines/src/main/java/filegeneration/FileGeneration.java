package filegeneration;

import configfile.ConfigFileReader;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;
import java.util.logging.Logger;
import oracle.OracleAnalyzer;

public final class FileGeneration {
    private static final Logger LOGGER = Logger.getLogger(FileGeneration.class.getName());

    public static void oracleAnalysis(ConfigFileReader configFileReader) throws ExecutionException, RunFailedException {
        //Select Oracle Analysis according to data type
        LOGGER.info("Oracle getting analyzed...");
        OracleAnalyzer.runRScript(configFileReader);
        LOGGER.info("Oracle analysis completed");

        LOGGER.info("Creating & analyzing TBD Matrices...");
        TermByDocumentCreation.createTBD(configFileReader);
        LOGGER.info("Creating & analyzing TBD Matrices completed");
    }
}

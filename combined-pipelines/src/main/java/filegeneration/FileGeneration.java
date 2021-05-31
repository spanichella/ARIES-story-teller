package filegeneration;

import configfile.ConfigFileReader;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;
import java.util.logging.Logger;
import oracle.OracleRequirementSpecificationsAnalyzer;
import oracle.OracleUserReviewsAnalyzer;

public final class FileGeneration {
    private static final Logger LOGGER = Logger.getLogger(FileGeneration.class.getName());

    public static void oracleAnalysis(ConfigFileReader configFileReader) throws ExecutionException, RunFailedException {
        //Select Oracle Analysis according to data type
        LOGGER.info("Oracle getting analyzed...");
        switch (configFileReader.dataType) {
            case "Requirement Specifications" -> OracleRequirementSpecificationsAnalyzer.runReqSpecRScript(configFileReader);
            case "User Reviews" -> OracleUserReviewsAnalyzer.runUserReviewRScript(configFileReader);
            default -> throw new IllegalArgumentException("Data type does not match analysis options");
        }
        LOGGER.info("Oracle analysis completed");

        LOGGER.info("Creating & analyzing TBD Matrices...");
        TermByDocumentCreation.createTBD(configFileReader);
        LOGGER.info("Creating & analyzing TBD Matrices completed");
    }
}

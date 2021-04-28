package filegeneration;

import configfile.ConfigFileReader;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.OracleRequirementSpecificationsAnalyzer;
import oracle.OracleUserReviewsAnalyzer;

public final class FileGeneration {
    private static final Logger logger = Logger.getLogger(FileGeneration.class.getName());

    public static void oracleAnalysis(ConfigFileReader configFileReader) throws ExecutionException, RunFailedException {
        //Select Oracle Analysis according to data type
        logger.info("Oracle getting analyzed...");
        switch (configFileReader.dataType) {
            case "Requirement Specifications" -> OracleRequirementSpecificationsAnalyzer.runReqSpecRScript(configFileReader);
            case "User Reviews" -> OracleUserReviewsAnalyzer.runUserReviewRScript(configFileReader);
            default -> throw new IllegalArgumentException("Data type does not match analysis options");
        }
        logger.info("Oracle analysis completed");

        logger.info("Creating & analyzing TBD Matrices...");
        TermByDocumentCreation.createTBD(configFileReader);
        logger.info("Creating & analyzing TBD Matrices completed");
    }
}

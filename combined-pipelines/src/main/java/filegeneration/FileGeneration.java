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
        if (configFileReader.dataType != null) {
            //Select Oracle Analysis according to data type
            logger.info("Oracle getting analyzed...");
            if (configFileReader.dataType.equals("Requirement Specifications")) {
                OracleRequirementSpecificationsAnalyzer.runReqSpecRScript(configFileReader);
            } else if (configFileReader.dataType.equals("User Reviews")) {
                OracleUserReviewsAnalyzer.runUserReviewRScript(configFileReader);
            } else {
                logger.log(Level.SEVERE, "Data type does not match analysis options");
                System.exit(1);
            }
            logger.info("Oracle analysis completed");

            logger.info("Creating & analyzing TBD Matrices...");
            TermByDocumentCreation.createTBD(configFileReader);
            logger.info("Creating & analyzing TBD Matrices completed");
        }
    }
}

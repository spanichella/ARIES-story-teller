package filegeneration;

import configfile.ConfigFileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.OracleRequirementSpecificationsAnalyzer;
import oracle.OracleUserReviewsAnalyzer;

public class FileGeneration {

    private static final Logger logger = Logger.getLogger(FileGeneration.class.getName());

    public static void oracleAnalysis(ConfigFileReader configFileReader) throws IOException {
        if (configFileReader.getDataType() != null) {
            //Select Oracle Analysis according to data type
            logger.info("Oracle getting analyzed...");
            if (configFileReader.getDataType().equals("Requirement Specifications")) {
                OracleRequirementSpecificationsAnalyzer.runReqSpecRScript(configFileReader);
            } else if (configFileReader.getDataType().equals("User Reviews")) {
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

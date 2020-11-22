package manager.pipeline;

import R_Tm_package.TermByDocumentCreation;
import configFile.ConfigFileReader;
import oracle.OracleRequirementSpecificationsAnalyzer;
import oracle.OracleUserReviewsAnalyzer;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FileGeneration {

    private final static Logger logger = Logger.getLogger(FileGeneration.class.getName());


    public static void oracleAnalysis(ConfigFileReader configFileReader) {

        if (configFileReader.getDataType() != null) {
            logger.info("Oracle getting analyzed...");

            //Select Oracle Analysis according to data type
            if(configFileReader.getDataType().equals("Requirement Specifications")) {
                OracleRequirementSpecificationsAnalyzer.runReqSpecRScript(configFileReader);
            }else if(configFileReader.getDataType().equals("User Reviews")){
                OracleUserReviewsAnalyzer.runUserReviewRScript(configFileReader);
            }else{
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

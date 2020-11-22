package manager.pipeline;

import R_Tm_package.TermByDocumentCreation;
import configFile.ConfigFileReader;
import oracle.OracleRequirementSpecificationsAnalyzer;
import oracle.OracleUserReviewsAnalyzer;

public class FileGeneration {

    public static void oracleAnalysis(ConfigFileReader configFileReader) {

        if (configFileReader.getDataType() != null) {
            System.out.println("PART 1. - ORACLE Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");

            // We finally run the ORACLE analysis
            if(configFileReader.getDataType().equals("Requirement Specifications")) {
                OracleRequirementSpecificationsAnalyzer.runReqSpecRScript(configFileReader);
            }else if(configFileReader.getDataType().equals("User Reviews")){
                OracleUserReviewsAnalyzer.runUserReviewRScript(configFileReader);
            }else{
                //todo log error
                System.exit(1);
            }
            System.out.println("\n END of PART 1. - ORACLE Analysis\n\n ");
            System.out.println("PART 2. - PART OF TbD Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");

            //run TbDAnalysis
            TermByDocumentCreation.createTBD(configFileReader);

            System.out.println("\n END of PART 2. - TbD Analysis \n\n ");
        }
    }
}

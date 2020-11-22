package manager.pipeline;

import R_Tm_package.TermByDocumentCreation;
import configFile.ConfigFileReader;
import oracle.OracleUserReviewsAnalyzer;

public class FileGeneration {

    public static void oracleAnalysis(ConfigFileReader configFileReader) {

        //Todo implement get from config file
        double splitSetPercentage = 0.5;


        if (configFileReader.getDataType() != null) {
            System.out.println("PART 1. - ORACLE Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");

            //we update parameters - used later to run oracle analysis
            MainPipeline mainPipeline = null;
            mainPipeline = new MainPipeline(configFileReader.getDataType(), configFileReader.getNameOfAttributeID(), configFileReader.getNameOfAttributeText(), configFileReader.getNameOfAttributeClass());
            mainPipeline.setPathOracleRScript(configFileReader.getPathRScriptOracle());
            mainPipeline.setPathBaseFolder(configFileReader.getPathBaseFolder());
            mainPipeline.setPathTruthSet(configFileReader.getPathTruthSet());
            //todo update when reading from config file is implemented
            mainPipeline.setSplitSetPercentage(splitSetPercentage);

            // We finally run the ORACLE analysis
            //TODO refactor object generation runs r-script
            OracleUserReviewsAnalyzer oracleUserReviewsAnalyzer = mainPipeline.runOracleAnalysis();

            System.out.println("\n END of PART 1. - ORACLE Analysis\n\n ");
            System.out.println("PART 2. - PART OF TbD Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");

            //Set parameters
            mainPipeline.setPathRScripts(configFileReader.getPathRScripts());
            mainPipeline.setPathTbDRScript(configFileReader.getPathTbDRScript());
            mainPipeline.setPathTrainingSetDocuments(configFileReader.getPathTrainingSetDocuments());
            mainPipeline.setPathTestSetDocuments(configFileReader.getPathTestSetDocuments());
            mainPipeline.setPathSimplifiedTruthSet(configFileReader.getPathSimplifiedTruthSet());

            //run TbDAnalysis
            TermByDocumentCreation.createTBD(configFileReader);

            System.out.println("\n END of PART 2. - TbD Analysis \n\n ");
        }
    }
}

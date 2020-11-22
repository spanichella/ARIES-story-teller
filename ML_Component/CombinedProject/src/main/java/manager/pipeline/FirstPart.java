package manager.pipeline;

import configFile.ConfigFileReader;
import oracle.OracleUserReviewsAnalyzer;

public class FirstPart {

    public static void oracleAnalysis(ConfigFileReader configFileReader) {

        // here are located the "documents" folder and the  "utilities.R script"
        String docs_location = configFileReader.getDocs_location();
        //local path to the R script "MainScript.r"
        String pathRScriptOracle = configFileReader.getPathRScriptOracle();
        // here are located the "documents" folder and the  "utilities.R script"
        String baseFolder = configFileReader.getBaseFolder();
        String oracle_path = configFileReader.getOracle_path();
        double setPartitioning = 0.5;

        // Type of the data to classify
        String dataType = configFileReader.getDataType();
        // Name of the column "ID" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
        String nameOfAttributeID = configFileReader.getNameOfAttributeID();
        // Name of the column "review" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
        String nameOfAttributeText = configFileReader.getNameOfAttributeText();
        // Name of the column "class" in the CSV file modeling the Oracle information - it will be used as argument of the main R script
        String nameOfAttributeClass = configFileReader.getNameOfAttributeClass();
        // PART 1. - ORACLE  - SOME PARAMETERS ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)

        if (dataType != null) {
            System.out.println("PART 1. - ORACLE Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");

            //we update parameters - used later to run oracle analysis
            MainPipeline mainPipeline = null;
            mainPipeline = new MainPipeline(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);
            mainPipeline.setPathRScriptsFolder(pathRScriptOracle);
            mainPipeline.setPathBaseFolder(baseFolder);
            mainPipeline.setPathTruthFile(oracle_path);
            mainPipeline.setThreshold(setPartitioning);

            // We finally run the ORACLE analysis
            //TODO refactor object generation runs r-script
            OracleUserReviewsAnalyzer oracleUserReviewsAnalyzer = mainPipeline.runOracleAnalysis();

            System.out.println("\n END of PART 1. - ORACLE Analysis\n\n ");
            System.out.println("PART 2. - PART OF TbD Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");

            //local path to the R script "MainScript.r"
            String pathTbDRScript = configFileReader.getPathTbDRScript();

            // locations of training and test sets
            String documentsTrainingSet = configFileReader.getDocumentsTrainingSet();
            String documentsTestSet = configFileReader.getDocumentsTestSet();
            // path oracle
            String simplifiedOracle_path = configFileReader.getSimplifiedOracle_path();

            //we update parameters - used later to run TbD analysis
            mainPipeline.setDocs_location(docs_location);
            mainPipeline.setPathTbDRScript(pathTbDRScript);
            mainPipeline.setDocumentsTrainingSet(documentsTrainingSet);
            mainPipeline.setDocumentsTestSet(documentsTestSet);
            mainPipeline.setSimplifiedOracle_path(simplifiedOracle_path);
            mainPipeline.runTbDAnalysis(nameOfAttributeID, nameOfAttributeClass);

            System.out.println("\n END of PART 2. - TbD Analysis \n\n ");
        }
    }
}

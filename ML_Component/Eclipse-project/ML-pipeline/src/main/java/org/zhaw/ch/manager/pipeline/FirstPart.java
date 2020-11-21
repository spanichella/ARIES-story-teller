package org.zhaw.ch.manager.pipeline;

import org.zhaw.ch.configFile.ConfigFileReader;
import org.zhaw.ch.oracle.OracleRequirementSpecificationsAnalyzer;
import org.zhaw.ch.oracle.OracleUserReviewsAnalyzer;

public class FirstPart {

    ConfigFileReader configFileReader = null;

    public void firstPart(ConfigFileReader configFileReader, String pathXMLConfigFile) {
        configFileReader = new ConfigFileReader(pathXMLConfigFile, "ORACLE_AND_TbD_ANALYSIS");

        // here are located the "documents" folder and the  "utilities.R script"
        String docs_location = configFileReader.getDocs_location();

        //local path to the R script "MainScript.r"
        String pathRScriptOracle = configFileReader.getPathRScriptOracle();
        // here are located the "documents" folder and the  "utilities.R script"
        String baseFolder = configFileReader.getBaseFolder();
        // path oracle
        String oracle_path = configFileReader.getOracle_path();
        // path threshold
        double threshold = 0.5;

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
            MainRequirementSpecificationsPipeline mainRequirementSpecificationsPipeline = null;
            if (dataType.equals("User Reviews")) {
                mainPipeline = new MainPipeline(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);
                mainPipeline.setPathRScriptsFolder(pathRScriptOracle);
                mainPipeline.setPathBaseFolder(baseFolder);
                mainPipeline.setPathTruthFile(oracle_path);
                mainPipeline.setThreshold(threshold);
            }
            if (dataType.equals("Requirement Specifications")) {
                mainRequirementSpecificationsPipeline = new MainRequirementSpecificationsPipeline(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);
                mainRequirementSpecificationsPipeline.setPathRScript(pathRScriptOracle);
                mainRequirementSpecificationsPipeline.setBaseFolder(baseFolder);
                mainRequirementSpecificationsPipeline.setOracle_path(oracle_path);
                mainRequirementSpecificationsPipeline.setThreshold(threshold);
            }

            // We finally run the ORACLE analysis
            if (dataType.equals("User Reviews")) {
                OracleUserReviewsAnalyzer oracleUserReviewsAnalyzer = mainPipeline.runOracleAnalysis();
            }
            if (dataType.equals("Requirement Specifications")) {
                OracleRequirementSpecificationsAnalyzer oracleRequirementSpecificationsAnalyzer = mainRequirementSpecificationsPipeline.runOracleAnalysis();
            }
            // END of 1. PART OF ORACLE
            System.out.println("\n END of PART 1. - ORACLE Analysis\n\n ");

            // PART 2. - TbD Analysis - SOME OF THEM ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)
            System.out.println("PART 2. - PART OF TbD Analysis - SOME Parameters ARE ALSO USED BY THE OTHER STEPS (TEXT PREPROCESSING AND ML/PL-based prediction)");
            //local path to the R script "MainScript.r"
            //String pathTbDRScript = docs_location+"MainScript.r";
            String pathTbDRScript = configFileReader.getPathTbDRScript();

            // locations of training and test sets
            //String documentsTrainingSet = docs_location+"documents2/training-set";
            String documentsTrainingSet = configFileReader.getDocumentsTrainingSet();
            //String documentsTestSet = docs_location+"documents2/test-set";
            String documentsTestSet = configFileReader.getDocumentsTestSet();
            // path oracle
            //String simplifiedOracle_path = docs_location+"documents2/truth_set-simplified.csv";
            String simplifiedOracle_path = configFileReader.getSimplifiedOracle_path();

            //we update parameters - used later to run TbD analysis
            if (dataType.equals("User Reviews")) {
                mainPipeline.setDocs_location(docs_location);
                mainPipeline.setPathTbDRScript(pathTbDRScript);
                mainPipeline.setDocumentsTrainingSet(documentsTrainingSet);
                mainPipeline.setDocumentsTestSet(documentsTestSet);
                mainPipeline.setSimplifiedOracle_path(simplifiedOracle_path);
                // We finally run the TbD analysis
                mainPipeline.runTbDAnalysis(nameOfAttributeID, nameOfAttributeClass);
            }
            if (dataType.equals("Requirement Specifications")) {
                mainRequirementSpecificationsPipeline.setDocs_location(docs_location);
                mainRequirementSpecificationsPipeline.setPathTbDRScript(pathTbDRScript);
                mainRequirementSpecificationsPipeline.setDocumentsTrainingSet(documentsTrainingSet);
                mainRequirementSpecificationsPipeline.setDocumentsTestSet(documentsTestSet);
                mainRequirementSpecificationsPipeline.setSimplifiedOracle_path(simplifiedOracle_path);
                // We finally run the TbD analysis
                mainRequirementSpecificationsPipeline.runTbDAnalysis(nameOfAttributeID, nameOfAttributeClass);
            }
            // END of 2. PART OF TbD Analysis
            System.out.println("\n END of PART 2. - TbD Analysis \n\n ");

        }
    }
}

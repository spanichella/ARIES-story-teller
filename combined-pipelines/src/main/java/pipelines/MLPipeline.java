package pipelines;

import configFile.ConfigFileReader;
import ml.WekaClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class MLPipeline {
    private final static Logger logger = Logger.getLogger(MLPipeline.class.getName());

    // runs ML according to selections made
    public static void performMlAnalysis(ConfigFileReader configFileReader) {
        logger.info("Starting Machine Learning Analysis...");
        if (configFileReader.getStrategy() != null) {
            if (configFileReader.getStrategy().equals("Percentage-Split")) {
                WekaClassifier wekaClassifier = new WekaClassifier(configFileReader.getPathTDMTrainingSet(), configFileReader.getPathTDMTestSet(), configFileReader.getPathModel());

                if (checkWhetherTestSetIsLabeled(configFileReader.getPathTDMTestSet())) {
                    wekaClassifier.runSpecifiedMachineLearningModel(configFileReader.getMachineLearningModel(), configFileReader.getPathResultsPrediction()); //default behaviour it does prediction with given training and test sets with J48
                } else {
                    wekaClassifier.runSpecifiedMachineLearningModelToLabelInstances(configFileReader.getMachineLearningModel()); //default behaviour it does prediction with given training and test sets with J48 - it label instances in the test set
                }
            }

            if (configFileReader.getStrategy().equals("10-Fold")) {
                WekaClassifier wekaClassifier = new WekaClassifier();
                try {
                    wekaClassifier.runSpecifiedModelWith10FoldStrategy(configFileReader.getPathFullTDMDataset(), configFileReader.getMachineLearningModel(), configFileReader.getPathResultsPrediction());
                } catch (Exception e) {
                    logger.severe(e.getMessage());
                    System.exit(1);
                }
            }
            logger.info("Machine Learning Analysis completed");
        }
    }

    private static boolean checkWhetherTestSetIsLabeled(String pathTestSet) {
        File file = new File(pathTestSet);

        try {
            Scanner scanner = new Scanner(file);

            //now read the file line by line...
            int lineNum = 0;
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lineNum++;
                //System.out.println(line);
                if (line.contains("\"?\"") || line.contains("'?'") || line.endsWith("?")) {
                    logger.info("The test-set is non labeled, we need to first label such instances");
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logger.info("The test-set is labeled.");
        return true;
    }
}

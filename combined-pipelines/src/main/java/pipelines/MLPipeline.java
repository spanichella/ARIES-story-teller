package pipelines;

import configfile.ConfigFileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;
import ml.WekaClassifier;

final class MLPipeline {
    private static final Logger LOGGER = Logger.getLogger(MLPipeline.class.getName());

    // runs ML according to selections made
    static void performMlAnalysis(ConfigFileReader configFileReader) throws Exception {
        LOGGER.info("Starting Machine Learning Analysis...");
        if (configFileReader.strategy.equals("Percentage-Split")) {
            WekaClassifier wekaClassifier = new WekaClassifier(configFileReader.pathTDMTrainingSet,
                configFileReader.pathTDMTestSet, configFileReader.pathModel);

            if (checkWhetherTestSetIsLabeled(configFileReader.pathTDMTestSet)) {
                wekaClassifier.runSpecifiedMachineLearningModel(configFileReader.machineLearningModel,
                    configFileReader.pathResultsPrediction);
                // default behaviour it does prediction with given training and test sets with J48
            } else {
                wekaClassifier.runSpecifiedMachineLearningModelToLabelInstances(configFileReader.machineLearningModel);
                // default behaviour it does prediction with given training and test sets with J48 - it label instances in the test set
            }
        }

        if (configFileReader.strategy.equals("10-Fold")) {
            WekaClassifier.runSpecifiedModelWith10FoldStrategy(configFileReader.pathFullTDMDataset,
                configFileReader.machineLearningModel, configFileReader.pathResultsPrediction);
        }
        LOGGER.info("Machine Learning Analysis completed");
    }

    private static boolean checkWhetherTestSetIsLabeled(String pathTestSet) throws FileNotFoundException {
        File file = new File(pathTestSet);

        try (Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name())) {
            //now read the file line by line...
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                //System.out.println(line);
                if (line.contains("\"?\"") || line.contains("'?'") || line.endsWith("?")) {
                    LOGGER.info("The test-set is non labeled, we need to first label such instances");
                    return false;
                }
            }
        }
        LOGGER.info("The test-set is labeled.");
        return true;
    }
}

package pipelines;

import config.Configuration;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Logger;
import ml.WekaClassifier;
import types.StrategyType;

final class MLPipeline {
    private static final Logger LOGGER = Logger.getLogger(MLPipeline.class.getName());

    // runs ML according to selections made
    static void performMlAnalysis(Configuration configuration) throws Exception {
        if (configuration.machineLearningModel == null) {
            throw new IllegalArgumentException("The machine learning model may not be empty for the ML pipeline");
        }
        LOGGER.info("Starting Machine Learning Analysis...");
        if (configuration.strategy == StrategyType.PERCENTAGE_SPLIT) {
            if (!checkWhetherTestSetIsLabeled(configuration.pathTDMTestSet)) {
                throw new IllegalArgumentException(String.join(System.lineSeparator(), "Test set items need to be labeled.",
                    "To classify such instances, consider to use the GUI version of WEKA as reported in the following example:",
                    "https://github.com/spanichella/Requirement-Collector-ML-Component/blob/master/ClassifyingNewDataWeka.pdf"));
            }
            WekaClassifier.runSpecifiedMachineLearningModel(configuration.pathTDMTrainingSet, configuration.pathTDMTestSet,
                Configuration.pathModel, configuration.machineLearningModel, Configuration.pathResultsPrediction);
            // default behaviour it does prediction with given training and test sets with J48
        }

        if (configuration.strategy == StrategyType.TEN_FOLD) {
            WekaClassifier.runSpecifiedModelWith10FoldStrategy(configuration.pathFullTDMDataset,
                configuration.machineLearningModel, Configuration.pathResultsPrediction);
        }
        LOGGER.info("Machine Learning Analysis completed");
    }

    private static boolean checkWhetherTestSetIsLabeled(Path pathTestSet) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(pathTestSet.toFile(), StandardCharsets.UTF_8.name())) {
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

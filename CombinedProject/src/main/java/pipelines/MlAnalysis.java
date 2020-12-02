package pipelines;

import configFile.ConfigFileReader;
import ml.WekaClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Logger;

public class MlAnalysis {
    private final static Logger logger = Logger.getLogger(MlAnalysis.class.getName());

    public static void performMlAnalysis(ConfigFileReader configFileReader) {
        logger.info("Starting Machine Learning Analysis...");
        WekaClassifier wekaClassifier = new WekaClassifier();
        if (configFileReader.getStrategy() != null) {
            if (configFileReader.getStrategy().equals("Percentage-Split")) {

                wekaClassifier = new WekaClassifier(configFileReader.getPathTDMTrainingSet(), configFileReader.getPathTDMTestSet(), configFileReader.getPathModel());

                if (checkWehetherTestSetIsLabeled(configFileReader.getPathTDMTestSet())) {
                    wekaClassifier.runSpecifiedMachineLearningModel(configFileReader.getMachineLearningModel(), configFileReader.getPathResultsPrediction()); //default behaviour it does prediction with given training and test sets with J48
                } else {
                    wekaClassifier.runSpecifiedMachineLearningModelToLabelInstances(configFileReader.getMachineLearningModel(), configFileReader.getPathResultsPrediction()); //default behaviour it does prediction with given training and test sets with J48 - it label instances in the test set
                }
            }

            if (configFileReader.getStrategy().equals("10-Fold")) {
                try {
                    wekaClassifier.runSpecifiedModelWith10FoldStrategy(configFileReader.getPathFullTDMDataset(), configFileReader.getPathModel(), configFileReader.getMachineLearningModel(), configFileReader.getPathResultsPrediction());
                } catch (Exception e) {
                    logger.severe(e.getMessage());
                    System.exit(1);
                }
            }
            logger.info("Machine Learning Analysis completed");
        }
    }

    private static boolean checkWehetherTestSetIsLabeled(String pathTestSet) {
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
                    System.out.println("CHECK DONE: the test set is non labeled, we need to label such instances");
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            //TODO handle this; generally throw error instead of blind catch; also check correct return in case of failure
            //e.printStackTrace();
        }
        System.out.println("CHECK DONE: the test set is labeled.");
        return true;
    }
}

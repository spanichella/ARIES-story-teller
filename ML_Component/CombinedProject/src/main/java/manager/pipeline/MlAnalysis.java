package manager.pipeline;

import configFile.ConfigFileReader;
import ml.WekaClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MlAnalysis {

    public static void performMlAnalysis(ConfigFileReader configFileReader) {

        WekaClassifier wekaClassifier = new WekaClassifier();
        if (configFileReader.getStrategy() != null) {
            System.out.println("PART 3. - ML prediction ");

            if (configFileReader.getStrategy().equals("Training_and_test_set")) {
                wekaClassifier = new WekaClassifier(configFileReader.getPathTDMTrainingSet(), configFileReader.getPathTDMTestSet(), configFileReader.getPathModel());
                if (checkWehetherTestSetIsLabeled(configFileReader.getPathTDMTestSet()) == true) {
                    wekaClassifier.runSpecifiedMachineLearningModel(configFileReader.getMachineLearningModel(), configFileReader.getPathResultsPrediction()); //default behaviour it does prediction with given training and test sets with J48
                } else {
                    wekaClassifier.runSpecifiedMachineLearningModelToLabelInstances(configFileReader.getMachineLearningModel(), configFileReader.getPathResultsPrediction()); //default behaviour it does prediction with given training and test sets with J48 - it label instances in the test set
                }
            }

            if (configFileReader.getStrategy().equals("10-fold")) {
                try {
                    wekaClassifier.runSpecifiedModelWith10FoldStrategy(configFileReader.getPathFullTDMDataset(), configFileReader.getPathModel(), configFileReader.getMachineLearningModel(), configFileReader.getPathResultsPrediction());
                } catch (Exception e) {
                    System.out.print("Error: Could not run 10-fold strategy");
                }
            }
            System.out.println("\n END of PART 3. - ML prediction\n\n");
            /**/

        }
    }

    private static boolean checkWehetherTestSetIsLabeled(String pathTestSet) {
        File file = new File(pathTestSet);

        try {
            Scanner scanner = new Scanner(file);

            //now read the file line by line...
            int lineNum = 0;
            String line = "";
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                lineNum++;
                //System.out.println(line);
                if (line.contains("\"?\"") || line.contains("'?'") || line.endsWith("?")) {
                    System.out.println("\nCHECK DONE: the test set is non labeled, we need to label such instances");
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            //handle this
        }
        System.out.println("\\nCHECK DONE: the test set is labeled.");
        return true;
    }
}

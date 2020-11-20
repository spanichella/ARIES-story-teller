package org.zhaw.ch.manager.pipeline;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.zhaw.ch.configFile.ConfigFileReader;
import org.zhaw.ch.ml.WekaClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ThirdPart {

    public void tp(ConfigFileReader configFileReader, String pathXMLConfigFile){

            configFileReader = new ConfigFileReader(pathXMLConfigFile, "ML_ANALYSIS");
            //path where Model gets created
            String pathModel = configFileReader.getPathModel();
            String machineLearningModel = configFileReader.getMachineLearningModel();
            String strategy = configFileReader.getStrategy();
            String pathResultsPrediction = configFileReader.getPathResultsPrediction();
            WekaClassifier wekaClassifier = new WekaClassifier();
            if (strategy != null) {
                System.out.println("PART 3. - ML prediction ");
                if (strategy.equals("Training_and_test_set")) {
                    String pathTrainingSet = configFileReader.getPathTrainingSet();
                    //path test set
                    String pathTestSet = configFileReader.getPathTestSet();
                    wekaClassifier = new WekaClassifier(pathTrainingSet, pathTestSet, pathModel);
                    if (checkWehetherTestSetIsLabeled(pathTestSet) == true) {
                        wekaClassifier.runSpecifiedMachineLearningModel(machineLearningModel, pathResultsPrediction); //default behaviour it does prediction with given training and test sets with J48
                    } else {
                        wekaClassifier.runSpecifiedMachineLearningModelToLabelInstances(machineLearningModel, pathResultsPrediction); //default behaviour it does prediction with given training and test sets with J48 - it label instances in the test set
                    }
                }
                if (strategy.equals("10-fold")) {
                    //path pathWholeDataset
                    //String pathWholeDataset = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed-review/tdm_full_with_oracle_info_information giving.csv";
                    String pathWholeDataset = configFileReader.getPathWholeDataset();
                    try {
                        wekaClassifier.runSpecifiedModelWith10FoldStrategy(pathWholeDataset, pathModel, machineLearningModel, pathResultsPrediction);
                    }catch (Exception e){
                        //TODO
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

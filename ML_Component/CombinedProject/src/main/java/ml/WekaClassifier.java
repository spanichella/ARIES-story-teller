package ml;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.evaluation.output.prediction.PlainText;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SGD;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.LogitBoost;
import weka.classifiers.meta.RegressionByDiscretization;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * @author panc
 */

public class WekaClassifier extends MachineLearningClassifier {

    private static String pathTrainingSet;
    private static String pathTestSet;
    private static String pathModel;

    public WekaClassifier() {
        this.classifierToolChain = "Weka";
    }

    public WekaClassifier(String pathTrainingSet, String pathTestSet, String pathModel) {
        this.classifierToolChain = "Weka";
        this.pathTrainingSet = pathTrainingSet;
        this.pathTestSet = pathTestSet;
        this.pathModel = pathModel;
    }

    public static void runSpecifiedMachineLearningModel(String machineLearningModel, String pathResultsPrediction) {

        //System.out.println("ClassifierToolChain: "+getClassifierToolChain()+" - model "+machineLearningModel);
        //path training set
        //String pathTrainingSet = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed/tdm_full_trainingSet_with_oracle_info.csv";
        String pathTrainingSet = getPathTrainingSet();
        //path test set
        //String pathTestSet = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed/tdm_full_testSet_with_oracle_info.csv";
        String pathTestSet = getPathTestSet();
        //path output model
        //String pathModel = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed/j48.model";
        String pathModel = getPathModel();


        try {
            //we create istances for training and test sets
            DataSource sourceTraining = new DataSource(pathTrainingSet);
            DataSource sourceTesting = new DataSource(pathTestSet);
            Instances train = sourceTraining.getDataSet(); // from somewhere
            Instances test = null;

            test = sourceTesting.getDataSet();     // from somewhere

            System.out.println("Loading data");

            // Set class the last attribute as class
            train.setClassIndex(train.numAttributes() - 1);
            test.setClassIndex(train.numAttributes() - 1);
            System.out.println("- Training data loaded - runSpecifiedMachineLearningModel");

            //2) Classifier declaration
            Classifier classifier = getClassifierClassName(machineLearningModel);
            //we print the model
            System.out.print(classifier.getClass());
            // train classifier

            classifier.buildClassifier(train);
            // evaluate classifier and print some statistics

            Evaluation eval = new Evaluation(train);
            weka.core.SerializationHelper.write(pathModel, classifier);
            eval.evaluateModel(classifier, test);

            System.out.println("training performance results of: " + classifier.getClass().getSimpleName()
                    + "\n---------------------------------");
            System.out.println(eval.toSummaryString("\nResults", true));
            System.out.println("fmeasure: " + eval.fMeasure(1) + " Precision: " + eval.precision(1) + " Recall: " + eval.recall(1));
            System.out.println(eval.toMatrixString());
            System.out.println(eval.toClassDetailsString());
            System.out.println("AUC = " + eval.areaUnderROC(1));
            String strDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
            FileWriter fileWriter = new FileWriter(pathResultsPrediction + strDate + ".txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("training performance results of: " + classifier.getClass().getSimpleName()
                    + "\n---------------------------------");
            printWriter.println(eval.toSummaryString("\nResults", true));
            printWriter.println("fmeasure: " + eval.fMeasure(1) + " Precision: " + eval.precision(1) + " Recall: " + eval.recall(1));
            printWriter.println(eval.toMatrixString());
            printWriter.println(eval.toClassDetailsString());
            printWriter.println("AUC = " + eval.areaUnderROC(1));
            printWriter.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static void runSpecifiedMachineLearningModelToLabelInstances(String machineLearningModel, String pathResultsPrediction) {

        //System.out.println("ClassifierToolChain: "+getClassifierToolChain()+" - model "+machineLearningModel);
        //path training set
        //String pathTrainingSet = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed/tdm_full_trainingSet_with_oracle_info.csv";
        String pathTrainingSet = getPathTrainingSet();
        //path test set
        //String pathTestSet = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed/tdm_full_testSet_with_oracle_info.csv";
        String pathTestSet = getPathTestSet();
        //path output model
        //String pathModel = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed/j48.model";
        String pathModel = getPathModel();

        //start by providing the paths for your training and testing ARFF files make sure both files have the same structure and the exact classes in the header
        try {
            //we create istances for training and test sets
            DataSource sourceTraining = new DataSource(pathTrainingSet);
            DataSource sourceTesting = new DataSource(pathTestSet);
            Instances train = sourceTraining.getDataSet(); // from somewhere
            Instances test = null;

            test = sourceTesting.getDataSet();     // from somewhere

            System.out.println("Loading data");

            // Set class the last attribute as class
            train.setClassIndex(train.numAttributes() - 1);
            test.setClassIndex(test.numAttributes() - 1);
            System.out.println("- Training data loaded - runSpecifiedMachineLearningModel");

            //2) Classifier declaration
            Classifier classifier = getClassifierClassName(machineLearningModel);
            //we print the model
            System.out.println(classifier.getClass());
            // train classifier
            System.out.println("Test set items that need to be labeled:" + test.numInstances());
            System.out.println("To classify such instances, consider to use the GUI version of WEKA as reported in the following example:");
            System.out.println("https://github.com/spanichella/Requirement-Collector-ML-Component/blob/master/ClassifyingNewDataWeka.pdf");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


    public static void runSpecifiedModelWith10FoldStrategy(String pathWholeDataset, String j48ModelPath, String machineLearningModel, String pathResultsPrediction) throws Exception {
        //String j48ModelPath = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed/j48.model";
        // String wholeDataset = "/Users/panc/Desktop/Zurich-applied-Science/Collaborations/Marcela/eclipse/workspace/ML-pipeline/R-resources/R-scripts/documents-preprocessed/tdm_full_with_oracle_info_information giving.csv";
        DataSource sourceWholeDataset = new DataSource(pathWholeDataset);
        Instances wholeDataset = sourceWholeDataset.getDataSet(); // from somewhere
        wholeDataset.setClassIndex(wholeDataset.numAttributes() - 1);

        System.out.println("Loading data");

        // Set class the last attribute as class

        System.out.println("- Whole dataset loaded - runSpecifiedModelWith10FoldStrategy");
        String[] options;
        //2) Classifier declaration
        Classifier classifier = getClassifierClassName(machineLearningModel);
        //we print the model
        System.out.print(classifier.getClass());
        // train classifier
        //options = weka.core.Utils.splitOptions("-D");
        //((J48) classifier).setOptions(options);
        classifier.buildClassifier(wholeDataset);


        Evaluation eval = new Evaluation(wholeDataset);
        eval.crossValidateModel(classifier, wholeDataset, 10, new Random(1));

        System.out.println("performance results of: " + classifier.getClass().getSimpleName()
                + "\n---------------------------------");
        System.out.println(eval.toSummaryString("\nResults", true));
        System.out.println("fmeasure: " + eval.fMeasure(1) + " Precision: " + eval.precision(1) + " Recall: " + eval.recall(1));
        System.out.println(eval.toMatrixString());
        System.out.println(eval.toClassDetailsString());
        System.out.println("AUC = " + eval.areaUnderROC(1));

        FileWriter fileWriter = new FileWriter(pathResultsPrediction);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println("performance results of: " + classifier.getClass().getSimpleName()
                + "\n---------------------------------");
        printWriter.println(eval.toSummaryString("\nResults", true));
        printWriter.println("fmeasure: " + eval.fMeasure(1) + " Precision: " + eval.precision(1) + " Recall: " + eval.recall(1));
        printWriter.println(eval.toMatrixString());
        printWriter.println(eval.toClassDetailsString());
        printWriter.println("AUC = " + eval.areaUnderROC(1));
        printWriter.close();
        //weka.core.SerializationHelper.write(j48ModelPath,classifier);

    }


    /**
     * Get classifier's class name by a short name
     */
    public static Classifier getClassifierClassName(String classifierName) {
        Classifier classifier = null;
        switch (classifierName) {
            case "J48":
                classifier = new J48();
                break;
            case "PART":
                classifier = new PART();
                break;
            case "NaiveBayes":
                classifier = new NaiveBayes();
                break;
            case "IBk":
                classifier = new IBk();
                break;
            case "OneR":
                classifier = new OneR();
                break;
            case "SMO":
                classifier = new SMO();
                break;
            case "Logistic":
                classifier = new Logistic();
                break;
            case "AdaBoostM1":
                classifier = new AdaBoostM1();
                break;
            case "LogitBoost":
                classifier = new LogitBoost();
                break;
            case "DecisionStump":
                classifier = new DecisionStump();
                break;
            case "LinearRegression":
                classifier = new LinearRegression();
                break;
            case "RegressionByDiscretization":
                classifier = new RegressionByDiscretization();
                break;
            default:
                classifier = new J48();
        }
        return classifier;
    }


    public static String getPathTrainingSet() {
        return pathTrainingSet;
    }


    public void setPathTrainingSet(String pathTrainingSet) {
        this.pathTrainingSet = pathTrainingSet;
    }


    public static String getPathTestSet() {
        return pathTestSet;
    }


    public void setPathTestSet(String pathTestSet) {
        this.pathTestSet = pathTestSet;
    }


    public static String getPathModel() {
        return pathModel;
    }


    public void setPathModel(String pathModel) {
        this.pathModel = pathModel;
    }


}

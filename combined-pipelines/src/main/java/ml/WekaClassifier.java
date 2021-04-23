package ml;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.LogitBoost;
import weka.classifiers.meta.RegressionByDiscretization;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * @author panc
 */

public final class WekaClassifier {
    private static final Logger logger = Logger.getLogger(WekaClassifier.class.getName());

    @Nonnull private final String pathTrainingSet;
    @Nonnull private final String pathTestSet;
    @Nonnull private final String pathModel;

    public WekaClassifier(@Nonnull String pathTrainingSet, @Nonnull String pathTestSet, @Nonnull String pathModel) {
        this.pathTrainingSet = pathTrainingSet;
        this.pathTestSet = pathTestSet;
        this.pathModel = pathModel;
    }

    public void runSpecifiedMachineLearningModel(String machineLearningModel, String pathResultsPrediction) throws Exception {
        //we create instances for training and test sets
        DataSource sourceTraining = new DataSource(pathTrainingSet);
        DataSource sourceTesting = new DataSource(pathTestSet);
        Instances train = sourceTraining.getDataSet();
        Instances test = sourceTesting.getDataSet();

        logger.info("Loading data...");

        // Set class the last attribute as class
        train.setClassIndex(train.numAttributes() - 1);
        test.setClassIndex(train.numAttributes() - 1);
        logger.info("Training data loaded");

        Classifier classifier = getClassifierClassName(machineLearningModel);
        logger.info("Classifier used: %s".formatted(classifier.getClass()));
        classifier.buildClassifier(train);

        Evaluation eval = new Evaluation(train);
        weka.core.SerializationHelper.write(pathModel, classifier);
        eval.evaluateModel(classifier, test);

        printAndWriteModelMeasures("training performance", classifier, eval, pathResultsPrediction);
    }

    public void runSpecifiedMachineLearningModelToLabelInstances(String machineLearningModel) throws Exception {
        DataSource sourceTraining = new DataSource(pathTrainingSet);
        DataSource sourceTesting = new DataSource(pathTestSet);
        Instances train = sourceTraining.getDataSet();
        Instances test = sourceTesting.getDataSet();

        logger.info("Loading data");

        // Set class the last attribute as class
        train.setClassIndex(train.numAttributes() - 1);
        test.setClassIndex(test.numAttributes() - 1);
        logger.info("Training data loaded");

        Classifier classifier = getClassifierClassName(machineLearningModel);
        logger.info("Classifier used: %s".formatted(classifier.getClass()));
        logger.info("Test set items that need to be labeled:%d".formatted(test.numInstances()));
        logger.info("To classify such instances, consider to use the GUI version of WEKA as reported in the following example:");
        logger.info("https://github.com/spanichella/Requirement-Collector-ML-Component/blob/master/ClassifyingNewDataWeka.pdf");
    }

    public static void runSpecifiedModelWith10FoldStrategy(String pathWholeDataset, String machineLearningModel, String pathResultsPrediction)
            throws Exception {
        DataSource sourceWholeDataset = new DataSource(pathWholeDataset);
        Instances wholeDataset = sourceWholeDataset.getDataSet(); // from somewhere
        wholeDataset.setClassIndex(wholeDataset.numAttributes() - 1);

        logger.info("Loading data");
        Classifier classifier = getClassifierClassName(machineLearningModel);
        logger.info("Using 10-Fold");
        logger.info("Classifier used: %s".formatted(classifier.getClass()));
        classifier.buildClassifier(wholeDataset);

        Evaluation eval = new Evaluation(wholeDataset);
        eval.crossValidateModel(classifier, wholeDataset, 10, new Random(1));
        printAndWriteModelMeasures("performance", classifier, eval, pathResultsPrediction);
    }

    private static void printAndWriteModelMeasures(String title, Classifier classifier, Evaluation eval, String pathResultsPrediction)
            throws Exception {
        printModelMeasures(title, classifier, eval, logger::info);
        String strDate = LocalDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        try (FileWriter fileWriter = new FileWriter("%s%s.txt".formatted(pathResultsPrediction, strDate), StandardCharsets.UTF_8);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printModelMeasures(title, classifier, eval, printWriter::println);
        }
    }

    private static void printModelMeasures(String title, Classifier classifier, Evaluation eval, Consumer<? super String> println)
            throws Exception {
        println.accept("%s results of: %s".formatted(title, classifier.getClass().getSimpleName()));
        println.accept("---------------------------------");
        println.accept("");
        println.accept(eval.toSummaryString("Results", true));
        println.accept("fmeasure: %s Precision: %s Recall: %s".formatted(eval.fMeasure(1), eval.precision(1), eval.recall(1)));
        println.accept(eval.toMatrixString());
        println.accept(eval.toClassDetailsString());
        println.accept("AUC = %s".formatted(eval.areaUnderROC(1)));
    }

    /**
     * Get classifier's class name by a short name
     */
    private static Classifier getClassifierClassName(String classifierName) {
        return switch (classifierName) {
            case "PART" -> new PART();
            case "NaiveBayes" -> new NaiveBayes();
            case "IBk" -> new IBk();
            case "OneR" -> new OneR();
            case "SMO" -> new SMO();
            case "Logistic" -> new Logistic();
            case "AdaBoostM1" -> new AdaBoostM1();
            case "LogitBoost" -> new LogitBoost();
            case "DecisionStump" -> new DecisionStump();
            case "LinearRegression" -> new LinearRegression();
            case "RegressionByDiscretization" -> new RegressionByDiscretization();
            default -> new J48();
        };
    }
}

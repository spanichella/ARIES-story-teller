package pipelines;

import configFile.ConfigFileReader;
import org.apache.commons.lang.ArrayUtils;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.DropoutLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DLPipeline {

    private static final Logger LOGGER = LoggerFactory.getLogger(DLPipeline.class.getName());

    private static final int GLOVE_DIM = 100;

    // captures everything that's not a letter greedily, e.g. ", "
    private static final String WORD_SPLIT_PATTERN = "\\P{L}+";
    // columns are split by ","
    private static final String INPUT_TURN_DELIMITER_PATTERN = "\",\"";

    // https://deeplearning4j.org/tutorials/setup

    public static void runDLPipeline(ConfigFileReader cfg) throws IOException {

        // the training set
        String labelledTurns = cfg.getPathTrainingSet();
        // the test set (but used for validation..)
        String validationSet = cfg.getPathTestSet();

        // load pre-trained GloVe w2v
        String glove = cfg.getPathGloveFile();
        System.out.println("TEST" + glove);

        WordVectors wordVectors = WordVectorSerializer.readWord2VecModel(new File(glove));

        String modelFileName = "model_6b_" + GLOVE_DIM + "d_v1_0.bin";

        int lengthTrainingSet = getLineCount(labelledTurns)-1;
        System.out.println(lengthTrainingSet);
        int lengthTestSet = getLineCount(validationSet)-1;
        System.out.println(lengthTestSet);


        int nrOfBatches = 4;
        int nrOfExamplesPerBatch = lengthTrainingSet/nrOfBatches;
        LOGGER.info(String.valueOf(nrOfExamplesPerBatch));

        int nrOfEpochs = 100;

        int wordsPerTurn = 100; // estimated. Longer will be cut, shorter will be empty padded.

        // columns of input. The rows are the number of examples.
        int inputColumns = wordsPerTurn * GLOVE_DIM;

        // create the model
        LOGGER.info("Creating model");
        MultiLayerNetwork model = createMultiLayerNetwork(inputColumns, 0.3);
        model.init();

        // save it to file
        File modelFile = new File(cfg.getPathModel().replace("MLModel.model", ""), modelFileName);
        //noinspection ResultOfMethodCallIgnored
        modelFile.createNewFile();
        LOGGER.info("Saving model to " + modelFile);
        ModelSerializer.writeModel(model, modelFile, true);
        System.out.println(labelledTurns);

        LOGGER.info("Start training...");
        train(labelledTurns, wordVectors, lengthTrainingSet, nrOfBatches, nrOfExamplesPerBatch, nrOfEpochs, inputColumns,
                wordsPerTurn, model, validationSet, lengthTestSet);
        LOGGER.info("Finished training");

        LOGGER.info("Save trained model to " + modelFile);
        // save parameter
        ModelSerializer.writeModel(model, modelFile, true);

        LOGGER.info("Evaluate model");
        String result = evaluate(validationSet, lengthTestSet, model, wordVectors, inputColumns, wordsPerTurn);
        System.out.println(result);
        String strDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        FileWriter fileWriter = new FileWriter(cfg.getPathResultsPrediction() + strDate + ".txt", StandardCharsets.UTF_8);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(result);
        printWriter.close();
    }

    private static void train(String labelledTurns, WordVectors wordVectors, int nrOfExamples, int nrOfBatches,
                              int nrOfExamplesPerBatch, int nrOfEpochs, int inputColumns, int wordsPerTurn,
                              MultiLayerNetwork model, String validationSet, int inputSize) throws IOException {

        // prepare test data
        List<INDArray> inputList = new ArrayList<>(inputSize);
        double[][] labelsList = new double[inputSize][];
        try (BufferedReader reader = new BufferedReader(new FileReader(validationSet, StandardCharsets.UTF_8))) {

            String line;
            int labelsId = 0;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] lineAr = line.split(INPUT_TURN_DELIMITER_PATTERN);

                for (int i = 0 ;  i<lineAr.length;i++){
                    lineAr[i] = lineAr[i].replace("\"","");
                }

                if(line.contains("req_specification")){
                    System.out.println("skipped");
                    continue;
                }
                Optional<INDArray> inputMatrix = getInputValueMatrix(lineAr, wordVectors, wordsPerTurn);
                inputList.add(inputMatrix.get().ravel());
                labelsList[labelsId++] = getEvalLabel(lineAr);
            }
        }
        INDArray evalInput = Nd4j.create(inputList, shape(inputList.size(), inputColumns));
        INDArray evalLabels = Nd4j.create(labelsList);



        LOGGER.info("Train with " + nrOfExamplesPerBatch + " examples in " + nrOfBatches + " batches for " + nrOfEpochs
                + " epochs");
        for (int epoch = 0; epoch < nrOfEpochs; ++epoch) {
            LOGGER.info("Epoch " + epoch);

            try (BufferedReader reader = new BufferedReader(new FileReader(labelledTurns, StandardCharsets.UTF_8))) {

                reader.readLine();
                String line = reader.readLine();
                for (int batch = 0; line != null && batch < nrOfBatches; ++batch) {
                    LOGGER.info("Batch " + batch);

                    List<INDArray> inputsBatch = new ArrayList<>(nrOfExamplesPerBatch);
                    int[] labelsBatch = new int[nrOfExamplesPerBatch];

                    for (int example = 0; example < nrOfExamplesPerBatch; ++example, line = reader.readLine()) {
                        // LOGGER.info("Example " + example);

                        if (line == null) {
                            LOGGER.warn("Premature end of test set file. Expected: {}. Actual: {}.", nrOfExamples,
                                    (batch + 1) * nrOfExamplesPerBatch + example + 1);
                            for (; example < nrOfExamplesPerBatch; ++example) {
                                // fill labels array, so input and label shape match
                                labelsBatch[example] = -1;
                            }
                            break;
                        }

                        String[] lineAr = line.split(INPUT_TURN_DELIMITER_PATTERN);


                        for (int i = 0 ;  i<lineAr.length;i++){
                            lineAr[i] = lineAr[i].replace("\"","");
                        }

                        if(line.contains("req_specification")){
                            System.out.println("skipped");
                            continue;
                        }

                        // check if matrix has been built
                        Optional<INDArray> wordVectorMatrixOpt = getInputValueMatrix(lineAr, wordVectors, wordsPerTurn);
                        if (wordVectorMatrixOpt.isEmpty()) {
                            labelsBatch[example] = -1;
                            continue;
                        }

                        inputsBatch.add(wordVectorMatrixOpt.get().ravel());
                        labelsBatch[example] = getLabelValue(lineAr);
                    }

                    INDArray input = Nd4j.create(inputsBatch, shape(inputsBatch.size(), inputColumns));
                    while (ArrayUtils.contains(labelsBatch, -1)) {
                        labelsBatch = ArrayUtils.removeElement(labelsBatch, -1);
                    }
                    model.fit(input, labelsBatch);
                }

            }
                Evaluation eval = new Evaluation(3);
                eval.eval(evalLabels, evalInput, model);
                LOGGER.info(eval.stats());
        }
    }

    private static String evaluate(String validationSet, int inputSize, MultiLayerNetwork model, WordVectors wordVectors,
                                 int inputColumns, int wordsPerTurn) throws IOException {

        List<INDArray> inputList = new ArrayList<>(inputSize);
        double[][] labelsList = new double[inputSize][];

        try (BufferedReader reader = new BufferedReader(new FileReader(validationSet, StandardCharsets.UTF_8))) {

            String line;
            int labelsId = 0;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                String[] lineAr = line.split(INPUT_TURN_DELIMITER_PATTERN);
                for (int i = 0 ;  i<lineAr.length;i++){
                    lineAr[i] = lineAr[i].replace("\"","");
                }

                if(line.contains("req_specification")){
                    System.out.println("skipped");
                    continue;
                }
                Optional<INDArray> inputMatrix = getInputValueMatrix(lineAr, wordVectors, wordsPerTurn);
                inputList.add(inputMatrix.get().ravel());
                labelsList[labelsId++] = getEvalLabel(lineAr);
            }

        }

        INDArray evalInput = Nd4j.create(inputList, shape(inputSize, inputColumns));
        INDArray evalLabels = Nd4j.create(labelsList);
        Evaluation eval = new Evaluation(3);
        eval.eval(evalLabels, evalInput, model);
        LOGGER.info(eval.stats());
        return eval.stats();
    }

    private static double[] getEvalLabel(String[] lineAr) {
        String labelStr = lineAr[2];
        switch (labelStr) {
            case "NULL":
                return new double[] { 1, 0, 0 };
            case "A":
                return new double[] { 0, 1, 0 };
            case "F":
                return new double[] { 0, 0, 1 };
        }

        throw new IllegalArgumentException("Unknown label: " + labelStr);
    }

    private static int getLabelValue(String[] lineAr) {
        String labelStr = lineAr[2];
        return getLabel(labelStr);
    }

    private static int getLabel(String labelStr) {
        switch (labelStr) {
            case "NULL":
                return 0;
            case "A":
                return 1;
            case "F":
                return 2;
        }

        throw new IllegalArgumentException("Unknown label: " + labelStr);
    }

    /**
     * @return input values or null if they couldn't be matched
     */
    private static Optional<INDArray> getInputValueMatrix(String[] lineAr, WordVectors wordVectors, int maxWords) {
        String text = lineAr[1].trim();
        String[] textWordAr = text.split(WORD_SPLIT_PATTERN); // split by non letter characters

        String[] ar;
        if (textWordAr.length > maxWords) {
            ar = (String[]) ArrayUtils.subarray(textWordAr, 0, maxWords);
        } else {
            ar = textWordAr;
        }

        List<String> wordList = getWordList(ar, wordVectors);
        if (wordList.isEmpty()) {
            return Optional.ofNullable(wordVectors.getWordVectors(List.of("nothing", "receive")));
        }

        return Optional.ofNullable(wordVectors.getWordVectors(wordList));
    }

    /**
     * Filter words which are not in the vocabulary, to prevent failing edge cases
     */
    private static List<String> getWordList(String[] textWordAr, WordVectors wordVectors) {
        return Arrays.stream(textWordAr).filter(wordVectors::hasWord).collect(Collectors.toList());
    }

    private static int[] shape(int rows, int columns) {
        return new int[] { rows, columns };
    }

    public static MultiLayerNetwork createMultiLayerNetwork(int nIn, double learningRate) {
        int rngSeed = 123; // random number seed for reproducibility
        int optimizationIterations = 1;
        int outputNum = 3; // number of output classes: FR, NFR, None

        //@formatter:off
        MultiLayerConfiguration multiLayerConf = new NeuralNetConfiguration.Builder()
                // high level configuration
                .seed(rngSeed)
                .learningRate(learningRate)
                .regularization(true).l2(1e-4)
                .iterations(optimizationIterations)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(Updater.ADAM)
                .weightInit(WeightInit.XAVIER)
                // layer configuration
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(nIn)
                        .nOut(10000)
                        .activation(Activation.LEAKYRELU)
                        .build())
                .layer(1, new DropoutLayer.Builder(0.6)
                        .build())
                .layer(2, new DenseLayer.Builder()
                        .nIn(10000)
                        .nOut(5000)
                        .activation(Activation.LEAKYRELU)
                        .build())
                .layer(3, new DenseLayer.Builder()
                        .nIn(5000)
                        .nOut(100)
                        .activation(Activation.TANH)
                        .build())
                .layer(4, new OutputLayer.Builder()
                        .nIn(100)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .lossFunction(LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR)
                        .build())

                // Pretraining and Backprop configuration
                .pretrain(false)
                .backprop(true)

                .build();
        //@formatter:on
        // System.out.println(multiLayerConf.toJson());
        return new MultiLayerNetwork(multiLayerConf);
    }

    static int getLineCount(String path) throws IOException {
        try (FileReader input = new FileReader(path, StandardCharsets.UTF_8);
             LineNumberReader count = new LineNumberReader(input)) {
            //noinspection StatementWithEmptyBody
            while (count.skip(Long.MAX_VALUE) > 0) {
                // Loop just in case the file is > Long.MAX_VALUE or skip() decides to not read the entire file
            }

            return count.getLineNumber();
        }
    }
}

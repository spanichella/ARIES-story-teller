package pipelines;

import helpers.CommonPaths;
import java.math.BigDecimal;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import types.DataType;
import types.MlModelType;
import types.StrategyType;

public final class Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class.getName());

    @Nonnull
    public final Path pathRScripts;
    @Nonnull
    public final Path pathRScriptOracle;
    @Nonnull
    public final Path pathBaseFolder;
    @Nonnull
    public final Path pathTruthSet;
    @Nonnull
    public final BigDecimal threshold;
    @Nonnull
    public final Path pathTbDRScript;
    @Nonnull
    public final String pathTrainingSetDocuments;
    @Nonnull
    public final String pathTestSetDocuments;
    @Nonnull
    public final Path pathSimplifiedTruthSet;
    @Nonnull
    public final Path pathTrainingSet;
    @Nonnull
    public final Path pathTestSet;
    @Nonnull
    public final Path pathGloveFile = CommonPaths.GLOVE_FILE;
    @Nullable
    public final MlModelType machineLearningModel;
    @Nonnull
    public final String nameOfAttributeClass;
    @Nonnull
    public final String nameOfAttributeID;
    @Nonnull
    public final String nameOfAttributeText;
    @Nonnull
    public final Path pathModel;
    @Nonnull
    public final Path pathFullTDMDataset;
    @Nonnull
    public final Path pathResultsPrediction;
    @Nonnull
    public final Path pathTDMTrainingSet;
    @Nonnull
    public final Path pathTDMTestSet;
    @Nullable
    public final StrategyType strategy;

    public Configuration(@Nonnull Path pathTruthFile, @Nonnull DataType dataType, @Nullable MlModelType model,
                         @Nonnull BigDecimal percentage, @Nullable StrategyType strategy) {
        Path baseFolder;
        String dataTypePostfix;
        String attributeTextName;

        switch (dataType) {
            case REQUIREMENT_SPECIFICATIONS -> {
                baseFolder = CommonPaths.RESOURCES.resolve("ReqSpec");
                dataTypePostfix = "-Req-Specifications";
                attributeTextName = "req_specification";
                LOGGER.info("Creating configuration for Requirement-Specifications...");
            }
            case USER_REVIEWS -> {
                baseFolder = CommonPaths.RESOURCES.resolve("UserReviews");
                dataTypePostfix = "";
                attributeTextName = "review";
                LOGGER.info("Creating configuration for User Reviews...");
            }
            default -> throw new IllegalArgumentException("Unknown type \"%s\"".formatted(dataType));
        }

        pathRScripts = CommonPaths.R_SCRIPTS;
        pathRScriptOracle = CommonPaths.R_SCRIPTS.resolve("Script-to-create-test-dataset" + dataTypePostfix + ".r");
        pathBaseFolder = baseFolder;
        pathTruthSet = pathTruthFile;
        threshold = percentage;
        pathTbDRScript = CommonPaths.R_SCRIPTS.resolve("MainScript.r");
        pathTrainingSetDocuments = "training-set%s".formatted(dataTypePostfix);
        pathTestSetDocuments = "test-set%s".formatted(dataTypePostfix);
        pathSimplifiedTruthSet = baseFolder.resolve("truth_set-simplified" + dataTypePostfix + ".csv");
        pathTrainingSet = baseFolder.resolve("trainingSet_truth_set" + dataTypePostfix + ".csv");
        pathTestSet = baseFolder.resolve("testSet_truth_set" + dataTypePostfix + ".csv");
        machineLearningModel = model;
        nameOfAttributeClass = "class";
        nameOfAttributeID = "id";
        nameOfAttributeText = attributeTextName;
        pathModel = CommonPaths.PROJECT_ROOT.resolve("models").resolve("MLModel.model");
        Path preprocessedFolder = baseFolder.resolve("documents-preprocessed-" + attributeTextName);
        pathFullTDMDataset = preprocessedFolder.resolve("tdm_full_with_oracle_info.csv");
        pathResultsPrediction = CommonPaths.PROJECT_ROOT.resolve("results").resolve("result_");
        pathTDMTrainingSet = preprocessedFolder.resolve("tdm_full_trainingSet_with_oracle_info.csv");
        pathTDMTestSet = preprocessedFolder.resolve("tdm_full_testSet_with_oracle_info.csv");
        this.strategy = strategy;
    }
}

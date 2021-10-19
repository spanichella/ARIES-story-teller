package config;

import helpers.CommonPaths;
import java.math.BigDecimal;
import java.nio.file.Path;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import types.MlModelType;
import types.StrategyType;

public final class Configuration {
    public static final String nameOfAttributeClass = "class";
    public static final String nameOfAttributeID = "id";
    public static final Path pathModel = CommonPaths.PROJECT_ROOT.resolve("models").resolve("MLModel.model");
    public static final Path pathResultsPrediction = CommonPaths.PROJECT_ROOT.resolve("results").resolve("result_");
    private static final String baseFolderName = "ReqSpec";
    private static final String dataTypePostfix = "-Req-Specifications";
    private static final String attributeTextName = "req_specification";

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
    public final String pathTrainingSetDocuments;
    @Nonnull
    public final String pathTestSetDocuments;
    @Nonnull
    public final Path pathSimplifiedTruthSet;
    @Nonnull
    public final Path pathTrainingSet;
    @Nonnull
    public final Path pathTestSet;
    @Nullable
    public final MlModelType machineLearningModel;
    @Nonnull
    public final String nameOfAttributeText;
    @Nonnull
    public final Path pathFullTDMDataset;
    @Nonnull
    public final Path pathTDMTrainingSet;
    @Nonnull
    public final Path pathTDMTestSet;
    @Nullable
    public final StrategyType strategy;

    public Configuration(
        @Nonnull Path pathTruthFile, @Nullable MlModelType model, @Nonnull BigDecimal percentage,
        @Nullable StrategyType strategy
    ) {
        pathRScripts = CommonPaths.R_SCRIPTS;
        pathRScriptOracle = CommonPaths.R_SCRIPTS.resolve("Script-to-create-test-dataset" + dataTypePostfix + ".r");
        pathBaseFolder = CommonPaths.RESOURCES.resolve(baseFolderName);
        pathTruthSet = pathTruthFile;
        threshold = percentage;
        pathTrainingSetDocuments = "training-set%s".formatted(dataTypePostfix);
        pathTestSetDocuments = "test-set%s".formatted(dataTypePostfix);
        pathSimplifiedTruthSet = pathBaseFolder.resolve("truth_set-simplified" + dataTypePostfix + ".csv");
        pathTrainingSet = pathBaseFolder.resolve("trainingSet_truth_set" + dataTypePostfix + ".csv");
        pathTestSet = pathBaseFolder.resolve("testSet_truth_set" + dataTypePostfix + ".csv");
        machineLearningModel = model;
        nameOfAttributeText = attributeTextName;
        Path preprocessedFolder = pathBaseFolder.resolve("documents-preprocessed-" + attributeTextName);
        pathFullTDMDataset = preprocessedFolder.resolve("tdm_full_with_oracle_info.csv");
        pathTDMTrainingSet = preprocessedFolder.resolve("tdm_full_trainingSet_with_oracle_info.csv");
        pathTDMTestSet = preprocessedFolder.resolve("tdm_full_testSet_with_oracle_info.csv");
        this.strategy = strategy;
    }
}

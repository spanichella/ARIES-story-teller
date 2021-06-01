package test.pipelines;

import helpers.CommonPaths;
import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pipelines.Configuration;
import types.DataType;
import types.MlModelType;
import types.StrategyType;

final class ConfigurationTests {
    @Test
    void testCreatingRequirementsSpecificationsFile() {
        Configuration reader = new Configuration(Path.of("example truth file"), DataType.REQUIREMENT_SPECIFICATIONS,
            MlModelType.ADA_BOOST_M1, BigDecimal.valueOf(123), null);
        Assertions.assertEquals(reader.pathRScripts, CommonPaths.R_SCRIPTS);
        Assertions.assertEquals(reader.pathRScriptOracle,
            CommonPaths.R_SCRIPTS.resolve("Script-to-create-test-dataset-Req-Specifications.r"));
        Path baseFolder = CommonPaths.RESOURCES.resolve("ReqSpec");
        Assertions.assertEquals(reader.pathBaseFolder, baseFolder);
        Assertions.assertEquals(reader.pathTruthSet, Path.of("example truth file"));
        Assertions.assertEquals(reader.nameOfAttributeID, "id");
        Assertions.assertEquals(reader.nameOfAttributeText, "req_specification");
        Assertions.assertEquals(reader.nameOfAttributeClass, "class");
        Assertions.assertEquals(reader.pathTbDRScript, CommonPaths.R_SCRIPTS.resolve("MainScript.r"));
        Assertions.assertEquals(reader.pathTrainingSetDocuments, "training-set-Req-Specifications");
        Assertions.assertEquals(reader.pathTestSetDocuments, "test-set-Req-Specifications");
        Assertions.assertEquals(reader.pathSimplifiedTruthSet, baseFolder.resolve("truth_set-simplified-Req-Specifications.csv"));
        Assertions.assertNull(reader.strategy);
        Assertions.assertEquals(reader.machineLearningModel, MlModelType.ADA_BOOST_M1);
        Assertions.assertEquals(reader.pathModel, CommonPaths.PROJECT_ROOT.resolve("models").resolve("MLModel.model"));
        Assertions.assertEquals(reader.threshold, BigDecimal.valueOf(123));
        Assertions.assertEquals(reader.pathResultsPrediction, CommonPaths.PROJECT_ROOT.resolve("results").resolve("result_"));
        Path processedDocumentsFolder = baseFolder.resolve("documents-preprocessed-req_specification");
        Assertions.assertEquals(reader.pathTDMTestSet, processedDocumentsFolder.resolve("tdm_full_testSet_with_oracle_info.csv"));
        Assertions.assertEquals(reader.pathTrainingSet, baseFolder.resolve("trainingSet_truth_set-Req-Specifications.csv"));
        Assertions.assertEquals(reader.pathTestSet, baseFolder.resolve("testSet_truth_set-Req-Specifications.csv"));
        Assertions.assertEquals(reader.pathTDMTrainingSet, processedDocumentsFolder.resolve("tdm_full_trainingSet_with_oracle_info.csv"));
        Assertions.assertEquals(reader.pathFullTDMDataset, processedDocumentsFolder.resolve("tdm_full_with_oracle_info.csv"));
        Assertions.assertEquals(reader.pathGloveFile, CommonPaths.RESOURCES.resolve("DL").resolve("glove.6B.100d.txt"));
    }

    @Test
    void testCreatingUserReviewsFile() {
        Configuration reader = new Configuration(Path.of("new example truth file"), DataType.USER_REVIEWS, null,
            BigDecimal.valueOf(1.2), StrategyType.TEN_FOLD);
        Assertions.assertEquals(reader.pathRScripts, CommonPaths.R_SCRIPTS);
        Assertions.assertEquals(reader.pathRScriptOracle, CommonPaths.R_SCRIPTS.resolve("Script-to-create-test-dataset.r"));
        Path baseFolder = CommonPaths.RESOURCES.resolve("UserReviews");
        Assertions.assertEquals(reader.pathBaseFolder, baseFolder);
        Assertions.assertEquals(reader.pathTruthSet, Path.of("new example truth file"));
        Assertions.assertEquals(reader.nameOfAttributeID, "id");
        Assertions.assertEquals(reader.nameOfAttributeText, "review");
        Assertions.assertEquals(reader.nameOfAttributeClass, "class");
        Assertions.assertEquals(reader.pathTbDRScript, CommonPaths.R_SCRIPTS.resolve("MainScript.r"));
        Assertions.assertEquals(reader.pathTrainingSetDocuments, "training-set");
        Assertions.assertEquals(reader.pathTestSetDocuments, "test-set");
        Assertions.assertEquals(reader.pathSimplifiedTruthSet, baseFolder.resolve("truth_set-simplified.csv"));
        Assertions.assertEquals(reader.strategy, StrategyType.TEN_FOLD);
        Assertions.assertNull(reader.machineLearningModel);
        Assertions.assertEquals(reader.pathModel, CommonPaths.PROJECT_ROOT.resolve("models").resolve("MLModel.model"));
        Assertions.assertEquals(reader.threshold, BigDecimal.valueOf(12, 1));
        Assertions.assertEquals(reader.pathResultsPrediction, CommonPaths.PROJECT_ROOT.resolve("results").resolve("result_"));
        Path processedDocumentsFolder = baseFolder.resolve("documents-preprocessed-review");
        Assertions.assertEquals(reader.pathTDMTestSet, processedDocumentsFolder.resolve("tdm_full_testSet_with_oracle_info.csv"));
        Assertions.assertEquals(reader.pathTrainingSet, baseFolder.resolve("trainingSet_truth_set.csv"));
        Assertions.assertEquals(reader.pathTestSet, baseFolder.resolve("testSet_truth_set.csv"));
        Assertions.assertEquals(reader.pathTDMTrainingSet, processedDocumentsFolder.resolve("tdm_full_trainingSet_with_oracle_info.csv"));
        Assertions.assertEquals(reader.pathFullTDMDataset, processedDocumentsFolder.resolve("tdm_full_with_oracle_info.csv"));
        Assertions.assertEquals(reader.pathGloveFile, CommonPaths.RESOURCES.resolve("DL").resolve("glove.6B.100d.txt"));
    }
}

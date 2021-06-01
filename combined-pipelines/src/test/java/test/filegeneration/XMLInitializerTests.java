package test.filegeneration;

import configfile.ConfigFileReader;
import filegeneration.XMLInitializer;
import helpers.CommonPaths;
import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import types.DataType;
import types.MlModelType;
import types.StrategyType;

final class XMLInitializerTests {
    @Test
    void testCreatingRequirementsSpecificationsFile() throws Exception {
        File targetFile = CommonPaths.XML_FILES.resolve("RequirementSpecificationsXML.xml").toFile();
        ensureDeleted(targetFile);
        try {
            XMLInitializer.createXML(Path.of("example truth file"), DataType.REQUIREMENT_SPECIFICATIONS, MlModelType.ADA_BOOST_M1,
                BigDecimal.valueOf(123), null);
            Assertions.assertTrue(targetFile.exists());
            ConfigFileReader reader = new ConfigFileReader(targetFile.toPath());
            assertPath(reader.pathRScripts, CommonPaths.R_SCRIPTS);
            assertPath(reader.pathRScriptOracle, CommonPaths.R_SCRIPTS.resolve("Script-to-create-test-dataset-Req-Specifications.r"));
            Path baseFolder = CommonPaths.RESOURCES.resolve("ReqSpec");
            assertPath(reader.pathBaseFolder, baseFolder);
            Assertions.assertEquals(reader.pathTruthSet, "example truth file");
            Assertions.assertEquals(reader.dataType, "Requirement Specifications");
            Assertions.assertEquals(reader.nameOfAttributeID, "id");
            Assertions.assertEquals(reader.nameOfAttributeText, "req_specification");
            Assertions.assertEquals(reader.nameOfAttributeClass, "class");
            assertPath(reader.pathTbDRScript, CommonPaths.R_SCRIPTS.resolve("MainScript.r"));
            Assertions.assertEquals(reader.pathTrainingSetDocuments, "training-set-Req-Specifications");
            Assertions.assertEquals(reader.pathTestSetDocuments, "test-set-Req-Specifications");
            assertPath(reader.pathSimplifiedTruthSet, baseFolder.resolve("truth_set-simplified-Req-Specifications.csv"));
            Assertions.assertEquals(reader.strategy, "null");
            Assertions.assertEquals(reader.machineLearningModel, "AdaBoostM1");
            assertPath(reader.pathModel, CommonPaths.PROJECT_ROOT.resolve("models").resolve("MLModel.model"));
            Assertions.assertEquals(reader.threshold, "123");
            assertPath(reader.pathResultsPrediction, CommonPaths.PROJECT_ROOT.resolve("results").resolve("result_"));
            Path processedDocumentsFolder = baseFolder.resolve("documents-preprocessed-req_specification");
            assertPath(reader.pathTDMTestSet, processedDocumentsFolder.resolve("tdm_full_testSet_with_oracle_info.csv"));
            assertPath(reader.pathTrainingSet, baseFolder.resolve("trainingSet_truth_set-Req-Specifications.csv"));
            assertPath(reader.pathTestSet, baseFolder.resolve("testSet_truth_set-Req-Specifications.csv"));
            assertPath(reader.pathTDMTrainingSet, processedDocumentsFolder.resolve("tdm_full_trainingSet_with_oracle_info.csv"));
            assertPath(reader.pathFullTDMDataset, processedDocumentsFolder.resolve("tdm_full_with_oracle_info.csv"));
            assertPath(reader.pathGloveFile, CommonPaths.RESOURCES.resolve("DL").resolve("glove.6B.100d.txt"));
        } finally {
            ensureDeleted(targetFile);
        }
    }

    @Test
    void testCreatingUserReviewsFile() throws Exception {
        File targetFile = CommonPaths.XML_FILES.resolve("UserReviewsXML.xml").toFile();
        ensureDeleted(targetFile);
        try {
            XMLInitializer.createXML(Path.of("new example truth file"), DataType.USER_REVIEWS, null,
                BigDecimal.valueOf(1.2), StrategyType.TEN_FOLD);
            Assertions.assertTrue(targetFile.exists());
            ConfigFileReader reader = new ConfigFileReader(targetFile.toPath());
            assertPath(reader.pathRScripts, CommonPaths.R_SCRIPTS);
            assertPath(reader.pathRScriptOracle, CommonPaths.R_SCRIPTS.resolve("Script-to-create-test-dataset.r"));
            Path baseFolder = CommonPaths.RESOURCES.resolve("UserReviews");
            assertPath(reader.pathBaseFolder, baseFolder);
            Assertions.assertEquals(reader.pathTruthSet, "new example truth file");
            Assertions.assertEquals(reader.dataType, "User Reviews");
            Assertions.assertEquals(reader.nameOfAttributeID, "id");
            Assertions.assertEquals(reader.nameOfAttributeText, "review");
            Assertions.assertEquals(reader.nameOfAttributeClass, "class");
            assertPath(reader.pathTbDRScript, CommonPaths.R_SCRIPTS.resolve("MainScript.r"));
            Assertions.assertEquals(reader.pathTrainingSetDocuments, "training-set");
            Assertions.assertEquals(reader.pathTestSetDocuments, "test-set");
            assertPath(reader.pathSimplifiedTruthSet, baseFolder.resolve("truth_set-simplified.csv"));
            Assertions.assertEquals(reader.strategy, "10-Fold");
            Assertions.assertEquals(reader.machineLearningModel, "null");
            assertPath(reader.pathModel, CommonPaths.PROJECT_ROOT.resolve("models").resolve("MLModel.model"));
            Assertions.assertEquals(reader.threshold, "1.2");
            assertPath(reader.pathResultsPrediction, CommonPaths.PROJECT_ROOT.resolve("results").resolve("result_"));
            Path processedDocumentsFolder = baseFolder.resolve("documents-preprocessed-review");
            assertPath(reader.pathTDMTestSet, processedDocumentsFolder.resolve("tdm_full_testSet_with_oracle_info.csv"));
            assertPath(reader.pathTrainingSet, baseFolder.resolve("trainingSet_truth_set.csv"));
            assertPath(reader.pathTestSet, baseFolder.resolve("testSet_truth_set.csv"));
            assertPath(reader.pathTDMTrainingSet, processedDocumentsFolder.resolve("tdm_full_trainingSet_with_oracle_info.csv"));
            assertPath(reader.pathFullTDMDataset, processedDocumentsFolder.resolve("tdm_full_with_oracle_info.csv"));
            assertPath(reader.pathGloveFile, CommonPaths.RESOURCES.resolve("DL").resolve("glove.6B.100d.txt"));
        } finally {
            ensureDeleted(targetFile);
        }
    }

    private static void ensureDeleted(File file) {
        if (file.exists()) {
            Assertions.assertTrue(file.delete());
        }
    }

    private static void assertPath(String actual, Path expected) {
        Assertions.assertEquals(actual, expected.toString());
    }
}

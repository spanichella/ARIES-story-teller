package test.pipelines;

import config.Configuration;
import helpers.CommonPaths;
import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import types.MlModelType;

@SuppressWarnings({"ClassOnlyUsedInOneModule", "ClassIndependentOfModule"})
final class ConfigurationTests {
    @Test
    void testCreatingRequirementsSpecificationsFile() {
        Configuration reader = new Configuration(Path.of("example truth file"),
            MlModelType.ADA_BOOST_M1, BigDecimal.valueOf(123), null);
        Assertions.assertEquals(reader.pathRScripts, CommonPaths.R_SCRIPTS);
        Assertions.assertEquals(reader.pathRScriptOracle,
            CommonPaths.R_SCRIPTS.resolve("Script-to-create-test-dataset-Req-Specifications.r"));
        Path baseFolder = CommonPaths.RESOURCES.resolve("ReqSpec");
        Assertions.assertEquals(reader.pathBaseFolder, baseFolder);
        Assertions.assertEquals(reader.pathTruthSet, Path.of("example truth file"));
        Assertions.assertEquals(reader.nameOfAttributeText, "req_specification");
        Assertions.assertEquals(reader.pathTrainingSetDocuments, "training-set-Req-Specifications");
        Assertions.assertEquals(reader.pathTestSetDocuments, "test-set-Req-Specifications");
        Assertions.assertEquals(reader.pathSimplifiedTruthSet, baseFolder.resolve("truth_set-simplified-Req-Specifications.csv"));
        Assertions.assertNull(reader.strategy);
        Assertions.assertEquals(reader.machineLearningModel, MlModelType.ADA_BOOST_M1);
        Assertions.assertEquals(reader.threshold, BigDecimal.valueOf(123));
        Path processedDocumentsFolder = baseFolder.resolve("documents-preprocessed-req_specification");
        Assertions.assertEquals(reader.pathTDMTestSet, processedDocumentsFolder.resolve("tdm_full_testSet_with_oracle_info.csv"));
        Assertions.assertEquals(reader.pathTrainingSet, baseFolder.resolve("trainingSet_truth_set-Req-Specifications.csv"));
        Assertions.assertEquals(reader.pathTestSet, baseFolder.resolve("testSet_truth_set-Req-Specifications.csv"));
        Assertions.assertEquals(reader.pathTDMTrainingSet, processedDocumentsFolder.resolve("tdm_full_trainingSet_with_oracle_info.csv"));
        Assertions.assertEquals(reader.pathFullTDMDataset, processedDocumentsFolder.resolve("tdm_full_with_oracle_info.csv"));
    }
}

package test.configfile;

import configfile.ConfigFileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringJoiner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.xml.sax.SAXParseException;

final class ConfigFileReaderTests {
    @Test
    void testEmptyFile(@TempDir Path tempDir) throws IOException {
        Path tmpFile = tempDir.resolve("test.xml");
        Files.writeString(tmpFile, "");
        assertInvalidConfigFile(SAXParseException.class, "Premature end of file.", tmpFile);
    }

    @Test
    void testEmptyRootTag(@TempDir Path tempDir) throws IOException {
        Path tmpFile = tempDir.resolve("test.xml");
        Files.writeString(tmpFile, String.join(System.lineSeparator(),
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
            "<company>",
            "</company>"
        ));
        assertInvalidConfigFile(IllegalArgumentException.class, "No matching element found in the config file", tmpFile);
    }

    @Test
    void testWithMissingTags(@TempDir Path tempDir) throws Exception {
        Path tmpFile = tempDir.resolve("test.xml");
        String[] availableTags = {
            "pathRScripts", "pathRScriptOracle", "pathBaseFolder", "pathTruthSet", "dataType",
            "nameOfAttributeID", "nameOfAttributeText", "nameOfAttributeClass", "pathTbDRScript", "pathTrainingSetDocuments",
            "pathTestSetDocuments", "pathSimplifiedTruthSet", "pathGloveFile", "pathTestSet", "pathTrainingSet", "percentageSplit",
            "strategy", "pathModel", "machineLearningModel", "pathResultsPrediction", "pathTDMTrainingSet", "pathTDMTestSet",
            "pathFullTDMDataset", null};
        Collection<String> tags = new ArrayList<>(availableTags.length);
        for (String tag : availableTags) {
            StringJoiner joiner = new StringJoiner(System.lineSeparator());
            joiner.add("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
            joiner.add("<company>");
            joiner.add("<ADSORB id=\"1\">");
            tags.forEach(joiner::add);
            joiner.add("</ADSORB>");
            joiner.add("</company>");
            Files.writeString(tmpFile, joiner.toString());
            // if last element skip the check
            if (tag != null) {
                assertInvalidConfigFile(IllegalArgumentException.class, "Exactly one element of type %s is needed".formatted(tag), tmpFile);
                tags.add("<%s></%s>".formatted(tag, tag));
            }
        }
        ConfigFileReader reader = new ConfigFileReader(tmpFile);
        int checked = 0;
        for (Field field : reader.getClass().getDeclaredFields()) {
            if ((field.getModifiers() & Modifier.STATIC) == 0) {
                Assertions.assertEquals(field.get(reader), "");
                checked++;
            }
        }
        Assertions.assertEquals(checked, availableTags.length - 1);
    }

    @Test
    void testBasicFile(@TempDir Path tempDir) throws Exception {
        Path tmpFile = tempDir.resolve("test.xml");
        Files.writeString(tmpFile, String.join(System.lineSeparator(),
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>",
            "<company>",
            "<ADSORB id=\"1\">",
            "<pathRScripts>TestValue1</pathRScripts>",
            "<pathRScriptOracle>TestValue2</pathRScriptOracle>",
            "<pathBaseFolder>TestValue3</pathBaseFolder>",
            "<pathTruthSet>TestValue4</pathTruthSet>",
            "<dataType>TestValue5</dataType>",
            "<nameOfAttributeID>TestValue6</nameOfAttributeID>",
            "<nameOfAttributeText>TestValue7</nameOfAttributeText>",
            "<nameOfAttributeClass>TestValue8</nameOfAttributeClass>",
            "<pathTbDRScript>TestValue9</pathTbDRScript>",
            "<pathTrainingSetDocuments>TestValue10</pathTrainingSetDocuments>",
            "<pathTestSetDocuments>TestValue11</pathTestSetDocuments>",
            "<pathSimplifiedTruthSet>TestValue12</pathSimplifiedTruthSet>",
            "<pathGloveFile>TestValue13</pathGloveFile>",
            "<pathTestSet>TestValue14</pathTestSet>",
            "<pathTrainingSet>TestValue15</pathTrainingSet>",
            "<percentageSplit>TestValue16</percentageSplit>",
            "<strategy>TestValue17</strategy>",
            "<pathModel>TestValue18</pathModel>",
            "<machineLearningModel>TestValue18</machineLearningModel>",
            "<pathResultsPrediction>TestValue19</pathResultsPrediction>",
            "<pathTDMTrainingSet>TestValue20</pathTDMTrainingSet>",
            "<pathTDMTestSet>TestValue21</pathTDMTestSet>",
            "<pathFullTDMDataset>TestValue22</pathFullTDMDataset>",
            "</ADSORB>",
            "</company>"
        ));
        ConfigFileReader reader = new ConfigFileReader(tmpFile);
        Assertions.assertEquals(reader.pathRScripts, "TestValue1");
        Assertions.assertEquals(reader.pathRScriptOracle, "TestValue2");
        Assertions.assertEquals(reader.pathBaseFolder, "TestValue3");
        Assertions.assertEquals(reader.pathTruthSet, "TestValue4");
        Assertions.assertEquals(reader.dataType, "TestValue5");
        Assertions.assertEquals(reader.nameOfAttributeID, "TestValue6");
        Assertions.assertEquals(reader.nameOfAttributeText, "TestValue7");
        Assertions.assertEquals(reader.nameOfAttributeClass, "TestValue8");
        Assertions.assertEquals(reader.pathTbDRScript, "TestValue9");
        Assertions.assertEquals(reader.pathTrainingSetDocuments, "TestValue10");
        Assertions.assertEquals(reader.pathTestSetDocuments, "TestValue11");
        Assertions.assertEquals(reader.pathSimplifiedTruthSet, "TestValue12");
        Assertions.assertEquals(reader.pathGloveFile, "TestValue13");
        Assertions.assertEquals(reader.pathTestSet, "TestValue14");
        Assertions.assertEquals(reader.pathTrainingSet, "TestValue15");
        Assertions.assertEquals(reader.threshold, "TestValue16");
        Assertions.assertEquals(reader.strategy, "TestValue17");
        Assertions.assertEquals(reader.pathModel, "TestValue18");
        Assertions.assertEquals(reader.machineLearningModel, "TestValue18");
        Assertions.assertEquals(reader.pathResultsPrediction, "TestValue19");
        Assertions.assertEquals(reader.pathTDMTrainingSet, "TestValue20");
        Assertions.assertEquals(reader.pathTDMTestSet, "TestValue21");
        Assertions.assertEquals(reader.pathFullTDMDataset, "TestValue22");
    }

    private static void assertInvalidConfigFile(Class<? extends Throwable> expectedType, String expectedMessage, Path path) {
        Throwable exception = Assertions.assertThrows(expectedType, () -> new ConfigFileReader(path));
        Assertions.assertEquals(exception.getMessage(), expectedMessage);
    }
}

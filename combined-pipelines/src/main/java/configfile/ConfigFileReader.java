package configfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public final class ConfigFileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigFileReader.class.getName());

    @Nonnull public final String pathRScripts;
    @Nonnull public final String pathRScriptOracle;
    @Nonnull public final String pathBaseFolder;
    @Nonnull public final String pathTruthSet;
    @Nonnull public final String threshold;
    @Nonnull public final String pathTbDRScript;
    @Nonnull public final String pathTrainingSetDocuments;
    @Nonnull public final String pathTestSetDocuments;
    @Nonnull public final String pathSimplifiedTruthSet;
    @Nonnull public final String pathTrainingSet;
    @Nonnull public final String pathTestSet;
    @Nonnull public final String pathGloveFile;
    @Nonnull public final String dataType;
    @Nonnull public final String machineLearningModel;
    @Nonnull public final String nameOfAttributeClass;
    @Nonnull public final String nameOfAttributeID;
    @Nonnull public final String nameOfAttributeText;
    @Nonnull public final String pathModel;
    @Nonnull public final String pathFullTDMDataset;
    @Nonnull public final String pathResultsPrediction;
    @Nonnull public final String pathTDMTrainingSet;
    @Nonnull public final String pathTDMTestSet;
    @Nonnull public final String strategy;

    public ConfigFileReader(Path pathXMLConfigFile) throws ParserConfigurationException, IOException, SAXException {
        LOGGER.info("Loading the config file...");
        Document doc;

        File xmlFile = pathXMLConfigFile.toFile();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        doc = documentBuilder.parse(xmlFile);

        // optional, but recommended
        // read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        LOGGER.info("Root element :{}", doc.getDocumentElement().getNodeName());

        NodeList nodeList = doc.getElementsByTagName("ADSORB");
        LOGGER.info("----------------------------");

        Element element = (Element) IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .filter(node -> node.getNodeType() == Node.ELEMENT_NODE)
                // get last element
                .reduce((first, second) -> second)
                .orElseThrow(() -> new IllegalArgumentException("No matching element found in the config file"));

        pathRScripts = readText(element, "pathRScripts");
        pathRScriptOracle = readText(element, "pathRScriptOracle");
        pathBaseFolder = readText(element, "pathBaseFolder");
        pathTruthSet = readText(element, "pathTruthSet");
        dataType = readText(element, "dataType");
        nameOfAttributeID = readText(element, "nameOfAttributeID");
        nameOfAttributeText = readText(element, "nameOfAttributeText");
        nameOfAttributeClass = readText(element, "nameOfAttributeClass");
        pathTbDRScript = readText(element, "pathTbDRScript");
        pathTrainingSetDocuments = readText(element, "pathTrainingSetDocuments");
        pathTestSetDocuments = readText(element, "pathTestSetDocuments");
        pathSimplifiedTruthSet = readText(element, "pathSimplifiedTruthSet");
        pathGloveFile = readText(element, "pathGloveFile");
        pathTestSet = readText(element, "pathTestSet");
        pathTrainingSet = readText(element, "pathTrainingSet");
        threshold = readText(element, "percentageSplit");
        strategy = readText(element, "strategy");
        pathModel = readText(element, "pathModel");
        machineLearningModel = readText(element, "machineLearningModel");
        pathResultsPrediction = readText(element, "pathResultsPrediction");
        pathTDMTrainingSet = readText(element, "pathTDMTrainingSet");
        pathTDMTestSet = readText(element, "pathTDMTestSet");
        pathFullTDMDataset = readText(element, "pathFullTDMDataset");
    }

    @Nonnull
    private static String readText(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() != 1) {
            throw new IllegalArgumentException("Exactly one element of type %s is needed".formatted(tagName));
        }
        String text = nodeList.item(0).getTextContent();
        if (text == null) {
            throw new IllegalArgumentException("The tag %s has no content".formatted(tagName));
        }
        return text;
    }
}

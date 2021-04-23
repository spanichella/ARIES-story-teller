package configfile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.IntStream;
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

    private final String pathRScripts;
    private final String pathRScriptOracle;
    private final String pathBaseFolder;
    private final String pathTruthSet;
    private final String threshold;
    private final String pathTbDRScript;
    private final String pathTrainingSetDocuments;
    private final String pathTestSetDocuments;
    private final String pathSimplifiedTruthSet;
    private final String pathTrainingSet;
    private final String pathTestSet;
    private final String pathGloveFile;
    private final String dataType;
    private final String machineLearningModel;
    private final String nameOfAttributeClass;
    private final String nameOfAttributeID;
    private final String nameOfAttributeText;
    private final String pathModel;
    private final String pathFullTDMDataset;
    private final String pathResultsPrediction;
    private final String pathTDMTrainingSet;
    private final String pathTDMTestSet;
    private final String strategy;

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

        pathRScripts = element.getElementsByTagName("pathRScripts").item(0).getTextContent();
        pathRScriptOracle = element.getElementsByTagName("pathRScriptOracle").item(0).getTextContent();
        pathBaseFolder = element.getElementsByTagName("pathBaseFolder").item(0).getTextContent();
        pathTruthSet = element.getElementsByTagName("pathTruthSet").item(0).getTextContent();
        dataType = element.getElementsByTagName("dataType").item(0).getTextContent();
        nameOfAttributeID = element.getElementsByTagName("nameOfAttributeID").item(0).getTextContent();
        nameOfAttributeText = element.getElementsByTagName("nameOfAttributeText").item(0).getTextContent();
        nameOfAttributeClass = element.getElementsByTagName("nameOfAttributeClass").item(0).getTextContent();
        pathTbDRScript = element.getElementsByTagName("pathTbDRScript").item(0).getTextContent();
        pathTrainingSetDocuments = element.getElementsByTagName("pathTrainingSetDocuments").item(0).getTextContent();
        pathTestSetDocuments = element.getElementsByTagName("pathTestSetDocuments").item(0).getTextContent();
        pathSimplifiedTruthSet = element.getElementsByTagName("pathSimplifiedTruthSet").item(0).getTextContent();
        pathGloveFile = element.getElementsByTagName("pathGloveFile").item(0).getTextContent();
        pathTestSet = element.getElementsByTagName("pathTestSet").item(0).getTextContent();
        pathTrainingSet = element.getElementsByTagName("pathTrainingSet").item(0).getTextContent();
        threshold = element.getElementsByTagName("percentageSplit").item(0).getTextContent();
        strategy = element.getElementsByTagName("strategy").item(0).getTextContent();
        pathModel = element.getElementsByTagName("pathModel").item(0).getTextContent();
        machineLearningModel = element.getElementsByTagName("machineLearningModel").item(0).getTextContent();
        pathResultsPrediction = element.getElementsByTagName("pathResultsPrediction").item(0).getTextContent();
        pathTDMTrainingSet = element.getElementsByTagName("pathTDMTrainingSet").item(0).getTextContent();
        pathTDMTestSet = element.getElementsByTagName("pathTDMTestSet").item(0).getTextContent();
        pathFullTDMDataset = element.getElementsByTagName("pathFullTDMDataset").item(0).getTextContent();
    }

    public  String getPathResultsPrediction() {
        return pathResultsPrediction;
    }

    public  String getStrategy() {
        return strategy;
    }

    public  String getMachineLearningModel() {
        return machineLearningModel;
    }

    public String getPathFullTDMDataset() {
        return pathFullTDMDataset;
    }

    public String getPathTDMTrainingSet() {
        return pathTDMTrainingSet;
    }

    public String getPathTDMTestSet() {
        return pathTDMTestSet;
    }

    public String getPathModel() {
        return pathModel;
    }

    public String getDataType() {
        return dataType;
    }

    public String getNameOfAttributeID() {
        return nameOfAttributeID;
    }

    public String getNameOfAttributeText() {
        return nameOfAttributeText;
    }

    public String getNameOfAttributeClass() {
        return nameOfAttributeClass;
    }

    public String getPathGloveFile() {
        return pathGloveFile;
    }

    public String getPathRScriptOracle() {
        return pathRScriptOracle;
    }

    public String getPathRScripts() {
        return pathRScripts;
    }

    public String getPathBaseFolder() {
        return pathBaseFolder;
    }

    public String getPathTruthSet() {
        return pathTruthSet;
    }

    public String getThreshold() {
        return threshold;
    }

    public String getPathTbDRScript() {
        return pathTbDRScript;
    }

    public String getPathTrainingSetDocuments() {
        return pathTrainingSetDocuments;
    }

    public String getPathTestSet() {
        return pathTestSet;
    }

    public String getPathTestSetDocuments() {
        return pathTestSetDocuments;
    }

    public String getPathSimplifiedTruthSet() {
        return pathSimplifiedTruthSet;
    }

    public String getPathTrainingSet() {
        return pathTrainingSet;
    }
}

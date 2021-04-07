package configfile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class ConfigFileReader {

    private String pathRScripts;
    private String pathRScriptOracle;
    private String pathBaseFolder;
    private String pathTruthSet;
    private String threshold;
    private String pathTbDRScript;
    private String pathTrainingSetDocuments;
    private String pathTestSetDocuments;
    private String pathSimplifiedTruthSet;
    private String pathTrainingSet;
    private String pathTestSet;
    private String pathGloveFile;
    private String dataType;
    private String machineLearningModel;
    private String nameOfAttributeClass;
    private String nameOfAttributeID;
    private String nameOfAttributeText;
    private String pathModel;
    private String pathFullTDMDataset;
    private String pathResultsPrediction;
    private String pathTDMTrainingSet;
    private String pathTDMTestSet;
    private String strategy;

    public ConfigFileReader(String pathXMLConfigFile) throws ParserConfigurationException, IOException, SAXException {
        System.out.println("Loading the config file...");
        Document doc;

        File fXmlFile = new File(pathXMLConfigFile);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(fXmlFile);

        // optional, but recommended
        // read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("ADSORB");
        System.out.println("----------------------------");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                this.pathRScripts = eElement.getElementsByTagName("pathRScripts").item(0).getTextContent();
                this.pathRScriptOracle = eElement.getElementsByTagName("pathRScriptOracle").item(0).getTextContent();
                this.pathBaseFolder = eElement.getElementsByTagName("pathBaseFolder").item(0).getTextContent();
                this.pathTruthSet = eElement.getElementsByTagName("pathTruthSet").item(0).getTextContent();
                this.dataType = eElement.getElementsByTagName("dataType").item(0).getTextContent();
                this.nameOfAttributeID = eElement.getElementsByTagName("nameOfAttributeID").item(0).getTextContent();
                this.nameOfAttributeText = eElement.getElementsByTagName("nameOfAttributeText").item(0).getTextContent();
                this.nameOfAttributeClass = eElement.getElementsByTagName("nameOfAttributeClass").item(0).getTextContent();
                this.pathTbDRScript = eElement.getElementsByTagName("pathTbDRScript").item(0).getTextContent();
                this.pathTrainingSetDocuments = eElement.getElementsByTagName("pathTrainingSetDocuments").item(0).getTextContent();
                this.pathTestSetDocuments = eElement.getElementsByTagName("pathTestSetDocuments").item(0).getTextContent();
                this.pathSimplifiedTruthSet = eElement.getElementsByTagName("pathSimplifiedTruthSet").item(0).getTextContent();
                this.pathGloveFile = eElement.getElementsByTagName("pathGloveFile").item(0).getTextContent();
                this.pathTestSet = eElement.getElementsByTagName("pathTestSet").item(0).getTextContent();
                this.pathTrainingSet = eElement.getElementsByTagName("pathTrainingSet").item(0).getTextContent();
                this.threshold = eElement.getElementsByTagName("percentageSplit").item(0).getTextContent();
                this.strategy = eElement.getElementsByTagName("strategy").item(0).getTextContent();
                this.pathModel = eElement.getElementsByTagName("pathModel").item(0).getTextContent();
                this.machineLearningModel = eElement.getElementsByTagName("machineLearningModel").item(0).getTextContent();
                this.pathResultsPrediction = eElement.getElementsByTagName("pathResultsPrediction").item(0).getTextContent();
                this.pathTDMTrainingSet = eElement.getElementsByTagName("pathTDMTrainingSet").item(0).getTextContent();
                this.pathTDMTestSet = eElement.getElementsByTagName("pathTDMTestSet").item(0).getTextContent();
                this.pathFullTDMDataset = eElement.getElementsByTagName("pathFullTDMDataset").item(0).getTextContent();
            }
        }
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

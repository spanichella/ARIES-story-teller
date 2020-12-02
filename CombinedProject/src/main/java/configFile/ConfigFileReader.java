package configFile;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;

public class ConfigFileReader {

    // PART 1. - ORACLE PARAMETERS
    //local path to the R script "MainScript.r"
    private String pathRScript;
    private String pathRScripts; // TODO: difference? terrible naming
    private String pathRScriptOracle;
    // here are located the "documents" folder and the "utilities.R" script
    private String pathBaseFolder;
    // path oracle
    private String pathTruthSet;
    // path threshold
    private String threshold;
    //local path to the R script "MainScript.r"
    private String pathTbDRScript;
    // locations of training and test sets
    private String pathTrainingSetDocuments;
    private String pathTestSetDocuments;
    // path oracle
    private String pathSimplifiedTruthSet;


    private String pathTrainingSet;


    private String pathTestSet;


    private String pathGloveFile;

    private static String dataType;
    private static String machineLearningModel;
    private static String nameOfAttributeClass;
    private static String nameOfAttributeID;
    private static String nameOfAttributeText;
    private static String pathModel;
    private static String pathFullTDMDataset;
    private static String pathResultsPrediction;
    private static String pathTDMTrainingSet;
    private static String pathTDMTestSet;
    private static String strategy;

    public ConfigFileReader(String pathXMLConfigFile) {
        System.out.println("Loading the config file...");
        Document doc = null;

        try {
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

            if (doc != null) {
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    System.out.println("\nCurrent Element :" + nNode.getNodeName());

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        // ORACLE_AND_TbD_ANALYSIS
                        Element eElement = (Element) nNode;
                        System.out.print(eElement);
                        String id = eElement.getAttribute("id");
                        System.out.println("ADSORB-ML id : " + id);

                        this.pathRScripts = eElement.getElementsByTagName("pathRScripts").item(0).getTextContent();
                        System.out.println("pathRScripts : " + this.pathRScripts);
                        this.pathRScriptOracle = eElement.getElementsByTagName("pathRScriptOracle").item(0).getTextContent();
                        System.out.println("pathRScriptOracle : " + this.pathRScriptOracle);
                        this.pathBaseFolder = eElement.getElementsByTagName("pathBaseFolder").item(0).getTextContent();
                        System.out.println("pathBaseFolder: " + this.pathBaseFolder);
                        this.pathTruthSet = eElement.getElementsByTagName("pathTruthSet").item(0).getTextContent();
                        System.out.println("pathTruthSet: " + this.pathTruthSet);
                        this.dataType = eElement.getElementsByTagName("dataType").item(0).getTextContent();
                        System.out.println("dataType: " + this.dataType);
                        this.nameOfAttributeID = eElement.getElementsByTagName("nameOfAttributeID").item(0).getTextContent();
                        System.out.println("nameOfAttributeID: " + this.nameOfAttributeID);
                        this.nameOfAttributeText = eElement.getElementsByTagName("nameOfAttributeText").item(0).getTextContent();
                        System.out.println("nameOfAttributeText: " + this.nameOfAttributeText);
                        this.nameOfAttributeClass = eElement.getElementsByTagName("nameOfAttributeClass").item(0).getTextContent();
                        System.out.println("nameOfAttributeClass: " + this.nameOfAttributeClass);
                        this.pathTbDRScript = eElement.getElementsByTagName("pathTbDRScript").item(0).getTextContent();
                        System.out.println("pathTbDRScript: " + this.pathTbDRScript);
                        this.pathTrainingSetDocuments = eElement.getElementsByTagName("pathTrainingSetDocuments").item(0).getTextContent();
                        System.out.println("pathTrainingSetDocuments: " + this.pathTrainingSetDocuments);
                        this.pathTestSetDocuments = eElement.getElementsByTagName("pathTestSetDocuments").item(0).getTextContent();
                        System.out.println("pathTestSetDocuments: " + this.pathTestSetDocuments);
                        this.pathSimplifiedTruthSet = eElement.getElementsByTagName("pathSimplifiedTruthSet").item(0).getTextContent();
                        System.out.println("simplifiedOracle_path: " + this.pathSimplifiedTruthSet);
                        this.pathGloveFile = eElement.getElementsByTagName("pathGloveFile").item(0).getTextContent();
                        this.pathTestSet = eElement.getElementsByTagName("pathTestSet").item(0).getTextContent();
                        this.pathTrainingSet = eElement.getElementsByTagName("pathTrainingSet").item(0).getTextContent();
                        this.threshold = eElement.getElementsByTagName("percentageSplit").item(0).getTextContent();

                        // ML_ANALYSIS
                        this.strategy = eElement.getElementsByTagName("strategy").item(0).getTextContent();
                        System.out.println("strategy: " + this.strategy);
                        this.pathModel = eElement.getElementsByTagName("pathModel").item(0).getTextContent();
                        System.out.println("pathModel: " + this.pathModel);
                        this.machineLearningModel = eElement.getElementsByTagName("machineLearningModel").item(0).getTextContent();
                        System.out.println("machineLearningModel: " + this.machineLearningModel);
                        this.pathResultsPrediction = eElement.getElementsByTagName("pathResultsPrediction").item(0).getTextContent();
                        System.out.println("pathResultsPrediction: " + this.pathResultsPrediction);

                        this.pathTDMTrainingSet = eElement.getElementsByTagName("pathTDMTrainingSet").item(0).getTextContent();
                        System.out.println("pathTDMTrainingSet: " + this.pathTDMTrainingSet);
                        this.pathTDMTestSet = eElement.getElementsByTagName("pathTDMTestSet").item(0).getTextContent();
                        System.out.println("pathTDMTestSet: " + this.pathTDMTestSet);
                        this.pathFullTDMDataset = eElement.getElementsByTagName("pathFullTDMDataset").item(0).getTextContent();
                        System.out.println("pathFullTDMDataset: " + this.pathFullTDMDataset);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (doc == null) {
                System.out.println(" Error message: No such file or directory -> " + pathXMLConfigFile); // TODO: specifically catch FileNotFoundException for this!
            }
        }
    }

    public static String getPathResultsPrediction() {
        return pathResultsPrediction;
    }

    public static String getStrategy() {
        return strategy;
    }

    public static void setStrategy(String strategy) {
        ConfigFileReader.strategy = strategy;
    }

    public static String getMachineLearningModel() {
        return machineLearningModel;
    }

    public static String getPathFullTDMDataset() {
        return pathFullTDMDataset;
    }

    public static void setPathFullTDMDataset(String pathFullTDMDataset) {
        ConfigFileReader.pathFullTDMDataset = pathFullTDMDataset;
    }

    public static String getPathTDMTrainingSet() {
        return pathTDMTrainingSet;
    }

    public static void setPathTDMTrainingSet(String pathTDMTrainingSet) {
        ConfigFileReader.pathTDMTrainingSet = pathTDMTrainingSet;
    }

    public static String getPathTDMTestSet() {
        return pathTDMTestSet;
    }

    public String getPathModel() {
        return pathModel;
    }

    public void setPathModel(String pathModel) {
        this.pathModel = pathModel;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getNameOfAttributeID() {
        return nameOfAttributeID;
    }

    public String getNameOfAttributeText() {
        return nameOfAttributeText;
    }

    public void setNameOfAttributeText(String nameOfAttributeText) {
        this.nameOfAttributeText = nameOfAttributeText;
    }

    public String getNameOfAttributeClass() {
        return nameOfAttributeClass;
    }

    public void setNameOfAttributeClass(String nameOfAttributeClass) {
        this.nameOfAttributeClass = nameOfAttributeClass;
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

    public void setPathRScripts(String pathRScripts) {
        this.pathRScripts = pathRScripts;
    }

    public String getPathRScript() {
        return pathRScript;
    }

    public void setPathRScript(String pathRScript) {
        this.pathRScript = pathRScript;
    }

    public String getPathBaseFolder() {
        return pathBaseFolder;
    }

    public void setPathBaseFolder(String pathBaseFolder) {
        this.pathBaseFolder = pathBaseFolder;
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

    public void setPathTrainingSetDocuments(String pathTrainingSetDocuments) {
        this.pathTrainingSetDocuments = pathTrainingSetDocuments;
    }

    public String getPathTestSet() {
        return pathTestSet;
    }

    public String getPathTestSetDocuments() {
        return pathTestSetDocuments;
    }

    public void setPathTestSetDocuments(String pathTestSetDocuments) {
        this.pathTestSetDocuments = pathTestSetDocuments;
    }

    public String getPathSimplifiedTruthSet() {
        return pathSimplifiedTruthSet;
    }

    public void setPathSimplifiedTruthSet(String pathSimplifiedTruthSet) {
        this.pathSimplifiedTruthSet = pathSimplifiedTruthSet;
    }

    public String getPathTrainingSet() {
        return pathTrainingSet;
    }
}

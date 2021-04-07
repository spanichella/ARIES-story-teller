package filegeneration;

import java.io.File;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Fills XML files on startup with correct paths and other values
 */
public class XMLInitializer {

    private static final Logger logger = Logger.getLogger(XMLInitializer.class.getName());

    public static void createXML(String basePath, String pathTruthFile, String type, String model, String percentage, String strategy)
            throws ParserConfigurationException, TransformerException {
        String baseFolder = "";
        String name = "";

        if (type.equals("Requirement-Specifications")) {
            baseFolder = "resources/ReqSpec/";
            name = "RequirementSpecifications";
            logger.info("Creating XML-file for Requirement-Specifications...");
        } else if (type.equals("User-Reviews")) {
            baseFolder = "resources/UserReviews/";
            name = "UserReviews";
            logger.info("Creating XML-file for User Reviews...");
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("company");
        document.appendChild(root);

        Element adsorb = document.createElement("ADSORB");
        root.appendChild(adsorb);
        Attr attr = document.createAttribute("id");
        attr.setValue("1");
        adsorb.setAttributeNode(attr);
        root.appendChild(adsorb);

        Element pathRScripts = document.createElement("pathRScripts");
        pathRScripts.appendChild(document.createTextNode(basePath + "resources/R-scripts/"));
        adsorb.appendChild(pathRScripts);

        Element pathRScriptOracle = document.createElement("pathRScriptOracle");
        if (type.equals("Requirement-Specifications")) {
            pathRScriptOracle.appendChild(document.createTextNode(
                    basePath + "resources/R-scripts/Script-to-create-test-dataset-Req-Specifications.r"));
        } else {
            pathRScriptOracle.appendChild(document.createTextNode(basePath + "resources/R-scripts/Script-to-create-test-dataset.r"));
        }
        adsorb.appendChild(pathRScriptOracle);

        Element pathBaseFolder = document.createElement("pathBaseFolder");
        pathBaseFolder.appendChild(document.createTextNode(basePath + baseFolder));
        adsorb.appendChild(pathBaseFolder);

        Element pathTruthSet = document.createElement("pathTruthSet");
        pathTruthSet.appendChild(document.createTextNode(pathTruthFile));
        adsorb.appendChild(pathTruthSet);

        Element dataType = document.createElement("dataType");
        if (type.equals("Requirement-Specifications")) {
            dataType.appendChild(document.createTextNode("Requirement Specifications"));
        } else {
            dataType.appendChild(document.createTextNode("User Reviews"));

        }
        adsorb.appendChild(dataType);

        Element nameOfAttributeID = document.createElement("nameOfAttributeID");
        nameOfAttributeID.appendChild(document.createTextNode("id"));
        adsorb.appendChild(nameOfAttributeID);

        Element nameOfAttributeText = document.createElement("nameOfAttributeText");
        if (type.equals("Requirement-Specifications")) {
            nameOfAttributeText.appendChild(document.createTextNode("req_specification"));
        } else {
            nameOfAttributeText.appendChild(document.createTextNode("review"));

        }
        adsorb.appendChild(nameOfAttributeText);

        Element nameOfAttributeClass = document.createElement("nameOfAttributeClass");
        nameOfAttributeClass.appendChild(document.createTextNode("class"));
        adsorb.appendChild(nameOfAttributeClass);


        Element pathTbDRScript = document.createElement("pathTbDRScript");
        pathTbDRScript.appendChild(document.createTextNode(basePath + "resources/R-scripts/MainScript.r"));
        adsorb.appendChild(pathTbDRScript);

        Element pathTrainingSetDocuments = document.createElement("pathTrainingSetDocuments");
        if (type.equals("Requirement-Specifications")) {
            pathTrainingSetDocuments.appendChild(document.createTextNode(basePath + baseFolder + "training-set-Req-Specifications"));
        } else {
            pathTrainingSetDocuments.appendChild(document.createTextNode(basePath + baseFolder + "training-set"));
        }
        adsorb.appendChild(pathTrainingSetDocuments);

        Element pathTestSetDocuments = document.createElement("pathTestSetDocuments");
        if (type.equals("Requirement-Specifications")) {
            pathTestSetDocuments.appendChild(document.createTextNode(basePath + baseFolder + "test-set-Req-Specifications"));
        } else {
            pathTestSetDocuments.appendChild(document.createTextNode(basePath + baseFolder + "test-set"));
        }
        adsorb.appendChild(pathTestSetDocuments);

        Element pathSimplifiedTruthSet = document.createElement("pathSimplifiedTruthSet");
        if (type.equals("Requirement-Specifications")) {
            pathSimplifiedTruthSet.appendChild(document.createTextNode(
                    basePath + baseFolder + "truth_set-simplified-Req-Specifications.csv"));
        } else {
            pathSimplifiedTruthSet.appendChild(document.createTextNode(basePath + baseFolder + "truth_set-simplified.csv"));
        }
        adsorb.appendChild(pathSimplifiedTruthSet);

        Element strategyEl = document.createElement("strategy");
        strategyEl.appendChild(document.createTextNode(strategy));
        adsorb.appendChild(strategyEl);

        Element machineLearningModel = document.createElement("machineLearningModel");
        machineLearningModel.appendChild(document.createTextNode(model));
        adsorb.appendChild(machineLearningModel);

        Element pathModel = document.createElement("pathModel");
        pathModel.appendChild(document.createTextNode(basePath.replace("combined-pipelines/", "") + "models/MLModel.model"));
        adsorb.appendChild(pathModel);

        Element percentageSplit = document.createElement("percentageSplit");
        percentageSplit.appendChild(document.createTextNode(percentage));
        adsorb.appendChild(percentageSplit);

        Element pathResultsPrediction = document.createElement("pathResultsPrediction");
        pathResultsPrediction.appendChild(document.createTextNode(basePath.replace("combined-pipelines/", "") + "results/" + "result_"));
        adsorb.appendChild(pathResultsPrediction);

        Element pathTDMTestSet = document.createElement("pathTDMTestSet");
        if (type.equals("Requirement-Specifications")) {
            pathTDMTestSet.appendChild(document.createTextNode(
                    basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_testSet_with_oracle_info.csv"));
        } else {
            pathTDMTestSet.appendChild(document.createTextNode(
                    basePath + baseFolder + "documents-preprocessed-review/tdm_full_testSet_with_oracle_info.csv"));
        }
        adsorb.appendChild(pathTDMTestSet);

        Element pathTrainingSet = document.createElement("pathTrainingSet");
        if (type.equals("Requirement-Specifications")) {
            pathTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "trainingSet_truth_set-Req-Specifications.csv"));
        } else {
            //TODO if adding user reviews to DL
            throw new RuntimeException("Path of training-set not defined for UserReviews if using DL-Pipeline");
        }
        adsorb.appendChild(pathTrainingSet);

        Element pathTestSet = document.createElement("pathTestSet");
        if (type.equals("Requirement-Specifications")) {
            pathTestSet.appendChild(document.createTextNode(basePath + baseFolder + "testSet_truth_set-Req-Specifications.csv"));
        } else {
            //TODO if adding user reviews to DL
            throw new RuntimeException("Path of test-set not defined for UserReviews if using DL-Pipeline");
        }
        adsorb.appendChild(pathTestSet);

        Element pathTDMTrainingSet = document.createElement("pathTDMTrainingSet");
        if (type.equals("Requirement-Specifications")) {
            pathTDMTrainingSet.appendChild(document.createTextNode(
                    basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_trainingSet_with_oracle_info.csv"));
        } else {
            pathTDMTrainingSet.appendChild(document.createTextNode(
                    basePath + baseFolder + "documents-preprocessed-review/tdm_full_trainingSet_with_oracle_info.csv"));
        }
        adsorb.appendChild(pathTDMTrainingSet);

        Element pathFullTDMDataset = document.createElement("pathFullTDMDataset");
        if (type.equals("Requirement-Specifications")) {
            pathFullTDMDataset.appendChild(document.createTextNode(
                    basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_with_oracle_info.csv"));
        } else {
            pathFullTDMDataset.appendChild(document.createTextNode(
                    basePath + baseFolder + "documents-preprocessed-review/tdm_full_with_oracle_info.csv"));
        }
        adsorb.appendChild(pathFullTDMDataset);

        Element gloveFile = document.createElement("pathGloveFile");
        gloveFile.appendChild(document.createTextNode(basePath + "resources/DL/glove.6B.100d.txt"));
        adsorb.appendChild(gloveFile);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(basePath + "resources/XMLFiles/" + name + "XML.xml"));
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(domSource, streamResult);

        logger.info("XML-file created");
    }
}

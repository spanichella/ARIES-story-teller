package filegeneration;

import helpers.CommonPaths;
import java.nio.file.Path;
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

    public static void createXML(String pathTruthFile, String dataType, String model, String percentage, String strategy)
            throws ParserConfigurationException, TransformerException {
        Path baseFolder;
        String name;

        if (dataType.equals("Requirement-Specifications")) {
            baseFolder = CommonPaths.RESOURCES.resolve("ReqSpec");
            name = "RequirementSpecifications";
            logger.info("Creating XML-file for Requirement-Specifications...");
        } else if (dataType.equals("User-Reviews")) {
            baseFolder = CommonPaths.RESOURCES.resolve("UserReviews");
            name = "UserReviews";
            logger.info("Creating XML-file for User Reviews...");
        } else {
            throw new IllegalArgumentException("Unknown type \"" + dataType + "\"");
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
        pathRScripts.appendChild(document.createTextNode(CommonPaths.R_SCRIPTS.toString()));
        adsorb.appendChild(pathRScripts);

        Element pathRScriptOracle = document.createElement("pathRScriptOracle");
        if (dataType.equals("Requirement-Specifications")) {
            pathRScriptOracle.appendChild(document.createTextNode(CommonPaths.R_SCRIPTS
                    .resolve("Script-to-create-test-dataset-Req-Specifications.r").toString()));
        } else {
            pathRScriptOracle.appendChild(document.createTextNode(CommonPaths.R_SCRIPTS
                    .resolve("Script-to-create-test-dataset.r").toString()));
        }
        adsorb.appendChild(pathRScriptOracle);

        Element pathBaseFolder = document.createElement("pathBaseFolder");
        pathBaseFolder.appendChild(document.createTextNode(baseFolder.toString()));
        adsorb.appendChild(pathBaseFolder);

        Element pathTruthSet = document.createElement("pathTruthSet");
        pathTruthSet.appendChild(document.createTextNode(pathTruthFile));
        adsorb.appendChild(pathTruthSet);

        Element dataTypeElement = document.createElement("dataType");
        if (dataType.equals("Requirement-Specifications")) {
            dataTypeElement.appendChild(document.createTextNode("Requirement Specifications"));
        } else {
            dataTypeElement.appendChild(document.createTextNode("User Reviews"));

        }
        adsorb.appendChild(dataTypeElement);

        Element nameOfAttributeID = document.createElement("nameOfAttributeID");
        nameOfAttributeID.appendChild(document.createTextNode("id"));
        adsorb.appendChild(nameOfAttributeID);

        Element nameOfAttributeText = document.createElement("nameOfAttributeText");
        if (dataType.equals("Requirement-Specifications")) {
            nameOfAttributeText.appendChild(document.createTextNode("req_specification"));
        } else {
            nameOfAttributeText.appendChild(document.createTextNode("review"));

        }
        adsorb.appendChild(nameOfAttributeText);

        Element nameOfAttributeClass = document.createElement("nameOfAttributeClass");
        nameOfAttributeClass.appendChild(document.createTextNode("class"));
        adsorb.appendChild(nameOfAttributeClass);


        Element pathTbDRScript = document.createElement("pathTbDRScript");
        pathTbDRScript.appendChild(document.createTextNode(CommonPaths.R_SCRIPTS.resolve("MainScript.r").toString()));
        adsorb.appendChild(pathTbDRScript);

        Element pathTrainingSetDocuments = document.createElement("pathTrainingSetDocuments");
        if (dataType.equals("Requirement-Specifications")) {
            pathTrainingSetDocuments.appendChild(document.createTextNode(baseFolder.resolve("training-set-Req-Specifications").toString()));
        } else {
            pathTrainingSetDocuments.appendChild(document.createTextNode(baseFolder.resolve("training-set").toString()));
        }
        adsorb.appendChild(pathTrainingSetDocuments);

        Element pathTestSetDocuments = document.createElement("pathTestSetDocuments");
        if (dataType.equals("Requirement-Specifications")) {
            pathTestSetDocuments.appendChild(document.createTextNode(baseFolder.resolve("test-set-Req-Specifications").toString()));
        } else {
            pathTestSetDocuments.appendChild(document.createTextNode(baseFolder.resolve("test-set").toString()));
        }
        adsorb.appendChild(pathTestSetDocuments);

        Element pathSimplifiedTruthSet = document.createElement("pathSimplifiedTruthSet");
        if (dataType.equals("Requirement-Specifications")) {
            pathSimplifiedTruthSet.appendChild(document.createTextNode(
                    baseFolder.resolve("truth_set-simplified-Req-Specifications.csv").toString()));
        } else {
            pathSimplifiedTruthSet.appendChild(document.createTextNode(baseFolder.resolve("truth_set-simplified.csv").toString()));
        }
        adsorb.appendChild(pathSimplifiedTruthSet);

        Element strategyEl = document.createElement("strategy");
        strategyEl.appendChild(document.createTextNode(strategy));
        adsorb.appendChild(strategyEl);

        Element machineLearningModel = document.createElement("machineLearningModel");
        machineLearningModel.appendChild(document.createTextNode(model));
        adsorb.appendChild(machineLearningModel);

        Element pathModel = document.createElement("pathModel");
        pathModel.appendChild(document.createTextNode(CommonPaths.PROJECT_ROOT.resolve("models").resolve("MLModel.model").toString()));
        adsorb.appendChild(pathModel);

        Element percentageSplit = document.createElement("percentageSplit");
        percentageSplit.appendChild(document.createTextNode(percentage));
        adsorb.appendChild(percentageSplit);

        Element pathResultsPrediction = document.createElement("pathResultsPrediction");
        pathResultsPrediction.appendChild(document.createTextNode(CommonPaths.PROJECT_ROOT.resolve("results").resolve("result_")
                .toString()));
        adsorb.appendChild(pathResultsPrediction);

        Element pathTDMTestSet = document.createElement("pathTDMTestSet");
        if (dataType.equals("Requirement-Specifications")) {
            pathTDMTestSet.appendChild(document.createTextNode(baseFolder.resolve("documents-preprocessed-req_specification")
                    .resolve("tdm_full_testSet_with_oracle_info.csv").toString()));
        } else {
            pathTDMTestSet.appendChild(document.createTextNode(baseFolder.resolve("documents-preprocessed-review")
                    .resolve("tdm_full_testSet_with_oracle_info.csv").toString()));
        }
        adsorb.appendChild(pathTDMTestSet);

        Element pathTrainingSet = document.createElement("pathTrainingSet");
        if (dataType.equals("Requirement-Specifications")) {
            pathTrainingSet.appendChild(document.createTextNode(baseFolder.resolve("trainingSet_truth_set-Req-Specifications.csv")
                    .toString()));
        } else {
            //TODO if adding user reviews to DL
            throw new RuntimeException("Path of training-set not defined for UserReviews if using DL-Pipeline");
        }
        adsorb.appendChild(pathTrainingSet);

        Element pathTestSet = document.createElement("pathTestSet");
        if (dataType.equals("Requirement-Specifications")) {
            pathTestSet.appendChild(document.createTextNode(baseFolder.resolve("testSet_truth_set-Req-Specifications.csv").toString()));
        } else {
            //TODO if adding user reviews to DL
            throw new RuntimeException("Path of test-set not defined for UserReviews if using DL-Pipeline");
        }
        adsorb.appendChild(pathTestSet);

        Element pathTDMTrainingSet = document.createElement("pathTDMTrainingSet");
        if (dataType.equals("Requirement-Specifications")) {
            pathTDMTrainingSet.appendChild(document.createTextNode(baseFolder.resolve("documents-preprocessed-req_specification")
                    .resolve("tdm_full_trainingSet_with_oracle_info.csv").toString()));
        } else {
            pathTDMTrainingSet.appendChild(document.createTextNode(baseFolder.resolve("documents-preprocessed-review")
                    .resolve("tdm_full_trainingSet_with_oracle_info.csv").toString()));
        }
        adsorb.appendChild(pathTDMTrainingSet);

        Element pathFullTDMDataset = document.createElement("pathFullTDMDataset");
        if (dataType.equals("Requirement-Specifications")) {
            pathFullTDMDataset.appendChild(document.createTextNode(baseFolder.resolve("documents-preprocessed-req_specification")
                    .resolve("tdm_full_with_oracle_info.csv").toString()));
        } else {
            pathFullTDMDataset.appendChild(document.createTextNode(baseFolder.resolve("documents-preprocessed-review")
                    .resolve("tdm_full_with_oracle_info.csv").toString()));
        }
        adsorb.appendChild(pathFullTDMDataset);

        Element gloveFile = document.createElement("pathGloveFile");
        gloveFile.appendChild(document.createTextNode(CommonPaths.GLOVE_FILE.toString()));
        adsorb.appendChild(gloveFile);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(CommonPaths.XML_FILES.resolve(name + "XML.xml").toFile());
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //noinspection HardcodedFileSeparator
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(domSource, streamResult);

        logger.info("XML-file created");
    }
}

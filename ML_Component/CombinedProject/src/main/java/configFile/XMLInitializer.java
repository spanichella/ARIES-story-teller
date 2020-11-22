package configFile;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Fills XML files on startup with correct paths and other standard values
 */
public class XMLInitializer {
    public static void createXML(String basePath, String type) {
        String baseFolder = "";
        String name = "";

        if (type.equals("RS")) {
            baseFolder = "Resources/ReqSpec/";
            name = "RequirementSpecifications";
        } else if (type.equals("UR")) {
            baseFolder = "Resources/UserReviews/";
            name = "UserReviews";
        }
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            // root element
            Element root = document.createElement("company");
            document.appendChild(root);

            // ADSORB element
            Element ADSORB = document.createElement("ADSORB");
            root.appendChild(ADSORB);
            Attr attr = document.createAttribute("id");
            attr.setValue("1");
            ADSORB.setAttributeNode(attr);
            root.appendChild(ADSORB);

            //docs_loc
            Element docs_loc = document.createElement("docs_location");
            docs_loc.appendChild(document.createTextNode(basePath + "Resources/R-scripts/"));
            ADSORB.appendChild(docs_loc);

            //pathRScriptOracle
            Element pathRScriptOracle = document.createElement("pathRScriptOracle");
            if (type.equals("RS")) {
                pathRScriptOracle.appendChild(document.createTextNode(basePath + "Resources/R-scripts/Script-to-create-test-dataset-Req-Specifications.r"));
            } else {
                pathRScriptOracle.appendChild(document.createTextNode(basePath + "Resources/R-scripts/Script-to-create-test-dataset.r"));
            }
            ADSORB.appendChild(pathRScriptOracle);

            //baseFolder
            Element baseFolder1 = document.createElement("baseFolder");
            baseFolder1.appendChild(document.createTextNode(basePath + baseFolder));
            ADSORB.appendChild(baseFolder1);

            //oracle_path
            Element oracle_path = document.createElement("oracle_path");
            if (type.equals("RS")) {
                oracle_path.appendChild(document.createTextNode(basePath + baseFolder + "truth_set_recording-ReqSpec.txt"));
            } else {
                oracle_path.appendChild(document.createTextNode(basePath + baseFolder + "truth_set_ICSME2015.csv"));

            }
            ADSORB.appendChild(oracle_path);

            //dataType
            Element dataType = document.createElement("dataType");
            if (type.equals("RS")) {
                dataType.appendChild(document.createTextNode("Requirement Specifications"));
            } else {
                dataType.appendChild(document.createTextNode("User Reviews"));

            }
            ADSORB.appendChild(dataType);

            //nameOfAttributeID
            Element nameOfAttributeID = document.createElement("nameOfAttributeID");
            nameOfAttributeID.appendChild(document.createTextNode("id"));
            ADSORB.appendChild(nameOfAttributeID);

            //nameOfAttributeText
            Element nameOfAttributeText = document.createElement("nameOfAttributeText");
            if (type.equals("RS")) {
                nameOfAttributeText.appendChild(document.createTextNode("req_specification"));
            } else {
                nameOfAttributeText.appendChild(document.createTextNode("review"));

            }
            ADSORB.appendChild(nameOfAttributeText);

            //nameOfAttributeClass
            Element nameOfAttributeClass = document.createElement("nameOfAttributeClass");
            nameOfAttributeClass.appendChild(document.createTextNode("class"));
            ADSORB.appendChild(nameOfAttributeClass);

            //pathTbDRScript
            Element pathTbDRScript = document.createElement("pathTbDRScript");
            pathTbDRScript.appendChild(document.createTextNode(basePath + "Resources/R-scripts/MainScript.r"));
            ADSORB.appendChild(pathTbDRScript);

            //documentsTrainingSet
            Element documentsTrainingSet = document.createElement("documentsTrainingSet");
            if (type.equals("RS")) {
                documentsTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "training-set-Req-Specifications"));
            } else {
                documentsTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "training-set"));

            }
            ADSORB.appendChild(documentsTrainingSet);

            //documentsTestSet
            Element documentsTestSet = document.createElement("documentsTestSet");
            if (type.equals("RS")) {
                documentsTestSet.appendChild(document.createTextNode(basePath + baseFolder + "test-set-Req-Specifications"));
            } else {
                documentsTestSet.appendChild(document.createTextNode(basePath + baseFolder + "test-set"));
            }
            ADSORB.appendChild(documentsTestSet);

            //simplifiedOracle_path
            Element simplifiedOracle_path = document.createElement("simplifiedOracle_path");
            if (type.equals("RS")) {
                simplifiedOracle_path.appendChild(document.createTextNode(basePath + baseFolder + "truth_set-simplified-Req-Specifications.csv"));
            } else {
                simplifiedOracle_path.appendChild(document.createTextNode(basePath + baseFolder + "truth_set-simplified.csv"));
            }
            ADSORB.appendChild(simplifiedOracle_path);

            //strategy
            //TODO: implement strategy
            Element strategy = document.createElement("strategy");
            strategy.appendChild(document.createTextNode("10-fold"));
            ADSORB.appendChild(strategy);

            //machineLearningModel
            //TODO: implement machineLearningModel
            Element machineLearningModel = document.createElement("machineLearningModel");
            machineLearningModel.appendChild(document.createTextNode("J48"));
            ADSORB.appendChild(machineLearningModel);

            //pathModel
            Element pathModel = document.createElement("pathModel");
            pathModel.appendChild(document.createTextNode(basePath + baseFolder + "MLModel.model"));
            ADSORB.appendChild(pathModel);

            //pathResultsPrediction
            //TODO: implement ResultPath
            Element pathResultsPrediction = document.createElement("pathResultsPrediction");
            pathResultsPrediction.appendChild(document.createTextNode(basePath + baseFolder + "result_"));
            ADSORB.appendChild(pathResultsPrediction);

            //pathTestSet
            Element pathTestSet = document.createElement("pathTestSet");
            if (type.equals("RS")) {
                pathTestSet.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_testSet_with_oracle_info.csv"));
            } else {
                pathTestSet.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-review/tdm_full_testSet_with_oracle_info.csv"));
            }
            ADSORB.appendChild(pathTestSet);

            //pathTrainingSet
            Element pathTrainingSet = document.createElement("pathTrainingSet");
            if (type.equals("RS")) {
                pathTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_trainingSet_with_oracle_info.csv"));
            } else {
                pathTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-review/tdm_full_trainingSet_with_oracle_info.csv"));
            }
            ADSORB.appendChild(pathTrainingSet);

            //pathWholeDataset
            Element pathWholeDataset = document.createElement("pathWholeDataset");
            if (type.equals("RS")) {
                pathWholeDataset.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_with_oracle_info.csv"));
            } else {
                pathWholeDataset.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-review/tdm_full_with_oracle_info.csv"));
            }
            ADSORB.appendChild(pathWholeDataset);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(basePath+"Resources/XMLFiles/" + name + "XML.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}

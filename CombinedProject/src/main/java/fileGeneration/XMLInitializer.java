package fileGeneration;

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
    public static void createXML(String basePath, String pathTruthFile,String type, String model, String percentage, String strategy) {
        String baseFolder = "";
        String name = "";

        if (type.equals("Requirement-Specifications")) {
            baseFolder = "Resources/ReqSpec/";
            name = "RequirementSpecifications";
        } else if (type.equals("User-Reviews")) {
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

            //pathRScripts
            Element pathRScripts = document.createElement("pathRScripts");
            pathRScripts.appendChild(document.createTextNode(basePath + "Resources/R-scripts/"));
            ADSORB.appendChild(pathRScripts);

            //pathRScriptOracle
            Element pathRScriptOracle = document.createElement("pathRScriptOracle");
            if (type.equals("Requirement-Specifications")) {
                pathRScriptOracle.appendChild(document.createTextNode(basePath + "Resources/R-scripts/Script-to-create-test-dataset-Req-Specifications.r"));
            } else {
                pathRScriptOracle.appendChild(document.createTextNode(basePath + "Resources/R-scripts/Script-to-create-test-dataset.r"));
            }
            ADSORB.appendChild(pathRScriptOracle);

            //baseFolder
            Element pathBaseFolder = document.createElement("pathBaseFolder");
            pathBaseFolder.appendChild(document.createTextNode(basePath + baseFolder));
            ADSORB.appendChild(pathBaseFolder);

            //pathTruthSet
            Element pathTruthSet = document.createElement("pathTruthSet");
            pathTruthSet.appendChild(document.createTextNode(pathTruthFile));
            ADSORB.appendChild(pathTruthSet);

            //dataType
            Element dataType = document.createElement("dataType");
            if (type.equals("Requirement-Specifications")) {
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
            if (type.equals("Requirement-Specifications")) {
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

            //pathTrainingSetDocuments
            Element pathTrainingSetDocuments = document.createElement("pathTrainingSetDocuments");
            if (type.equals("Requirement-Specifications")) {
                pathTrainingSetDocuments.appendChild(document.createTextNode(basePath + baseFolder + "training-set-Req-Specifications"));
            } else {
                pathTrainingSetDocuments.appendChild(document.createTextNode(basePath + baseFolder + "training-set"));
            }
            ADSORB.appendChild(pathTrainingSetDocuments);

            //pathTestSetDocuments
            Element pathTestSetDocuments = document.createElement("pathTestSetDocuments");
            if (type.equals("Requirement-Specifications")) {
                pathTestSetDocuments.appendChild(document.createTextNode(basePath + baseFolder + "test-set-Req-Specifications"));
            } else {
                pathTestSetDocuments.appendChild(document.createTextNode(basePath + baseFolder + "test-set"));
            }
            ADSORB.appendChild(pathTestSetDocuments);

            //pathSimplifiedTruthSet
            Element pathSimplifiedTruthSet = document.createElement("pathSimplifiedTruthSet");
            if (type.equals("Requirement-Specifications")) {
                pathSimplifiedTruthSet.appendChild(document.createTextNode(basePath + baseFolder + "truth_set-simplified-Req-Specifications.csv"));
            } else {
                pathSimplifiedTruthSet.appendChild(document.createTextNode(basePath + baseFolder + "truth_set-simplified.csv"));
            }
            ADSORB.appendChild(pathSimplifiedTruthSet);

            //strategy
            Element strategyEl = document.createElement("strategy");
            strategyEl.appendChild(document.createTextNode(strategy));
            ADSORB.appendChild(strategyEl);

            //machineLearningModel
            Element machineLearningModel = document.createElement("machineLearningModel");
            machineLearningModel.appendChild(document.createTextNode(model));
            ADSORB.appendChild(machineLearningModel);

            //pathModel
            Element pathModel = document.createElement("pathModel");
            pathModel.appendChild(document.createTextNode(basePath.replace("CombinedProject/", "")+"Models/MLModel.model"));
            ADSORB.appendChild(pathModel);

            //percentageSplit
            Element percentageSplit = document.createElement("percentageSplit");
            percentageSplit.appendChild(document.createTextNode(percentage));
            ADSORB.appendChild(percentageSplit);

            //pathResultsPrediction
            Element pathResultsPrediction = document.createElement("pathResultsPrediction");
            pathResultsPrediction.appendChild(document.createTextNode(basePath.replace("CombinedProject/", "")+"Results/" + "result_"));
            ADSORB.appendChild(pathResultsPrediction);

            //pathTDMTestSet
            Element pathTDMTestSet = document.createElement("pathTDMTestSet");
            if (type.equals("Requirement-Specifications")) {
                pathTDMTestSet.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_testSet_with_oracle_info.csv"));
            } else {
                pathTDMTestSet.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-review/tdm_full_testSet_with_oracle_info.csv"));
            }
            ADSORB.appendChild(pathTDMTestSet);

            //pathTrainingSet
            Element pathTrainingSet = document.createElement("pathTrainingSet");
            if (type.equals("Requirement-Specifications")) {
                pathTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "trainingSet_truth_set-Req-Specifications.csv"));
            } else {
                //TODO if adding user reviews to DL
                System.out.println("");
            }
            ADSORB.appendChild(pathTrainingSet);

            //pathTestSet
            Element pathTestSet = document.createElement("pathTestSet");
            if (type.equals("Requirement-Specifications")) {
                pathTestSet.appendChild(document.createTextNode(basePath + baseFolder + "testSet_truth_set-Req-Specifications.csv"));
            } else {
                //TODO if adding user reviews to DL
                System.out.println("");
            }
            ADSORB.appendChild(pathTestSet);

            //pathTDMTrainingSet
            Element pathTDMTrainingSet = document.createElement("pathTDMTrainingSet");
            if (type.equals("Requirement-Specifications")) {
                pathTDMTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_trainingSet_with_oracle_info.csv"));
            } else {
                pathTDMTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-review/tdm_full_trainingSet_with_oracle_info.csv"));
            }
            ADSORB.appendChild(pathTDMTrainingSet);

            //pathFullTDMDataset
            Element pathFullTDMDataset = document.createElement("pathFullTDMDataset");
            if (type.equals("Requirement-Specifications")) {
                pathFullTDMDataset.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-req_specification/tdm_full_with_oracle_info.csv"));
            } else {
                pathFullTDMDataset.appendChild(document.createTextNode(basePath + baseFolder + "documents-preprocessed-review/tdm_full_with_oracle_info.csv"));
            }
            ADSORB.appendChild(pathFullTDMDataset);

            Element gloveFile = document.createElement("pathGloveFile");
            gloveFile.appendChild(document.createTextNode(basePath + "Resources/DL/glove.6B.100d.txt"));
            ADSORB.appendChild(gloveFile);

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

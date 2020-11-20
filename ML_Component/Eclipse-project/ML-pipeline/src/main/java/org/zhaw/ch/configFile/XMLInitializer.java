package org.zhaw.ch.configFile;

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
    public void createXML(String basePath, String type) {
        String baseFolder = "";
        String name = "";

        if (type == "RS") {
            baseFolder = "Resources/ReqSpec/";
            name = "RequirementSpecifications";
        } else if (type == "UR") {
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
            docs_loc.appendChild(document.createTextNode(basePath+"Resources/R-scripts/"));
            ADSORB.appendChild(docs_loc);

            //pathRScriptOracle
            Element pathRScriptOracle = document.createElement("pathRScriptOracle");
            if(type=="RS") {
                pathRScriptOracle.appendChild(document.createTextNode(basePath + "Resources/R-scripts/Script-to-create-test-dataset-Req-Specifications.r"));
            }else {
                pathRScriptOracle.appendChild(document.createTextNode(basePath + "Resources/R-scripts/Script-to-create-test-dataset.r"));
            }
            ADSORB.appendChild(pathRScriptOracle);

            //baseFolder
            Element baseFolder1 = document.createElement("baseFolder");
            baseFolder1.appendChild(document.createTextNode(basePath+baseFolder));
            ADSORB.appendChild(baseFolder1);

            //oracle_path
            Element oracle_path = document.createElement("oracle_path");
            if(type=="RS") {
                oracle_path.appendChild(document.createTextNode(basePath + baseFolder + "truth_set_ReqSpecification.txt"));
            }else{
                oracle_path.appendChild(document.createTextNode(basePath + baseFolder + "truth_set_ICSME2015.csv"));

            }
            ADSORB.appendChild(oracle_path);

            //dataType
            Element dataType = document.createElement("dataType");
            if(type=="RS") {
                dataType.appendChild(document.createTextNode("Requirement Specifications"));
            }else{
                dataType.appendChild(document.createTextNode("User Reviews"));

            }
            ADSORB.appendChild(dataType);

            //nameOfAttributeID
            Element nameOfAttributeID = document.createElement("nameOfAttributeID");
            nameOfAttributeID.appendChild(document.createTextNode("id"));
            ADSORB.appendChild(nameOfAttributeID);

            //nameOfAttributeText
            Element nameOfAttributeText = document.createElement("nameOfAttributeText");
            if(type=="RS") {
                nameOfAttributeText.appendChild(document.createTextNode("req_specification"));
            }else{
                nameOfAttributeText.appendChild(document.createTextNode("review"));

            }
            ADSORB.appendChild(nameOfAttributeText);

            //nameOfAttributeClass
            Element nameOfAttributeClass = document.createElement("nameOfAttributeClass");
            nameOfAttributeClass.appendChild(document.createTextNode("class"));
            ADSORB.appendChild(nameOfAttributeClass);

            //pathTbDRScript
            Element pathTbDRScript = document.createElement("pathTbDRScript");
            pathTbDRScript.appendChild(document.createTextNode(basePath+"Resources/R-scripts/MainScript.r"));
            ADSORB.appendChild(pathTbDRScript);

            //documentsTrainingSet
            Element documentsTrainingSet = document.createElement("documentsTrainingSet");
            if(type=="RS") {
                documentsTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "training-set-Req-Specifications"));
            }else{
                documentsTrainingSet.appendChild(document.createTextNode(basePath + baseFolder + "training-set"));

            }
            ADSORB.appendChild(documentsTrainingSet);

            //documentsTestSet
            Element documentsTestSet = document.createElement("documentsTestSet");
            if(type=="RS") {
                documentsTestSet.appendChild(document.createTextNode(basePath + baseFolder + "test-set-Req-Specifications"));
            }else{
                documentsTestSet.appendChild(document.createTextNode(basePath + baseFolder + "test-set"));
            }
            ADSORB.appendChild(documentsTestSet);

            //simplifiedOracle_path
            Element simplifiedOracle_path = document.createElement("simplifiedOracle_path");
            if(type=="RS") {
                simplifiedOracle_path.appendChild(document.createTextNode(basePath + baseFolder + "truth_set-simplified.csv"));
            }else{
                simplifiedOracle_path.appendChild(document.createTextNode(basePath + baseFolder + "truth_set-simplified.csv"));
            }
            ADSORB.appendChild(simplifiedOracle_path);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("/Users/marckramer/Repos/SWME_G2_HS20/ML_Component/Eclipse-project/ML-pipeline/Resources/XMLFiles/"+name+"XML.xml"));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(domSource, streamResult);
        } catch(Exception e){
            System.out.print(e);
        }
    }
}

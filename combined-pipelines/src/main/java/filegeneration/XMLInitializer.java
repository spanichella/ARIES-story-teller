package filegeneration;

import helpers.CommonPaths;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import types.DataType;

/**
 * Fills XML files on startup with correct paths and other values
 */
public final class XMLInitializer {
    private static final Logger LOGGER = Logger.getLogger(XMLInitializer.class.getName());

    public static void createXML(@Nonnull String pathTruthFile, @Nonnull DataType dataType, @Nonnull String model,
                                 @Nonnull BigDecimal percentage, @Nonnull String strategy)
            throws ParserConfigurationException, TransformerException {
        Path baseFolder;
        String name;
        String dataTypePostfix;
        String attributeTextName;

        switch (dataType) {
            case REQUIREMENT_SPECIFICATIONS -> {
                baseFolder = CommonPaths.RESOURCES.resolve("ReqSpec");
                name = "RequirementSpecifications";
                dataTypePostfix = "-Req-Specifications";
                attributeTextName = "req_specification";
                LOGGER.info("Creating XML-file for Requirement-Specifications...");
            }
            case USER_REVIEWS -> {
                baseFolder = CommonPaths.RESOURCES.resolve("UserReviews");
                name = "UserReviews";
                dataTypePostfix = "";
                attributeTextName = "review";
                LOGGER.info("Creating XML-file for User Reviews...");
            }
            default -> throw new IllegalArgumentException("Unknown type \"%s\"".formatted(dataType));
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

        XMLNodeHelper adsorbHelper = new XMLNodeHelper(document, adsorb);
        adsorbHelper.addSimpleNode("pathRScripts", CommonPaths.R_SCRIPTS);
        adsorbHelper.addSimpleNode("pathRScriptOracle",
                CommonPaths.R_SCRIPTS.resolve("Script-to-create-test-dataset" + dataTypePostfix + ".r"));
        adsorbHelper.addSimpleNode("pathBaseFolder", baseFolder);
        adsorbHelper.addSimpleNode("pathTruthSet", pathTruthFile);
        adsorbHelper.addSimpleNode("dataType", switch (dataType) {
            case REQUIREMENT_SPECIFICATIONS -> "Requirement Specifications";
            case USER_REVIEWS -> "User Reviews";
        });
        adsorbHelper.addSimpleNode("nameOfAttributeID", "id");
        adsorbHelper.addSimpleNode("nameOfAttributeText", attributeTextName);
        adsorbHelper.addSimpleNode("nameOfAttributeClass", "class");
        adsorbHelper.addSimpleNode("pathTbDRScript", CommonPaths.R_SCRIPTS.resolve("MainScript.r"));
        adsorbHelper.addSimpleNode("pathTrainingSetDocuments", "training-set%s".formatted(dataTypePostfix));
        adsorbHelper.addSimpleNode("pathTestSetDocuments", "test-set%s".formatted(dataTypePostfix));
        adsorbHelper.addSimpleNode("pathSimplifiedTruthSet", baseFolder.resolve("truth_set-simplified" + dataTypePostfix + ".csv"));
        adsorbHelper.addSimpleNode("strategy", strategy);
        adsorbHelper.addSimpleNode("machineLearningModel", model);
        adsorbHelper.addSimpleNode("pathModel", CommonPaths.PROJECT_ROOT.resolve("models").resolve("MLModel.model"));
        adsorbHelper.addSimpleNode("percentageSplit", percentage.toPlainString());
        adsorbHelper.addSimpleNode("pathResultsPrediction", CommonPaths.PROJECT_ROOT.resolve("results").resolve("result_"));
        Path preprocessedFolder = baseFolder.resolve("documents-preprocessed-" + attributeTextName);
        adsorbHelper.addSimpleNode("pathTDMTestSet", preprocessedFolder.resolve("tdm_full_testSet_with_oracle_info.csv"));
        adsorbHelper.addSimpleNode("pathTrainingSet", baseFolder.resolve("trainingSet_truth_set" + dataTypePostfix + ".csv"));
        adsorbHelper.addSimpleNode("pathTestSet", baseFolder.resolve("testSet_truth_set" + dataTypePostfix + ".csv"));
        adsorbHelper.addSimpleNode("pathTDMTrainingSet", preprocessedFolder.resolve("tdm_full_trainingSet_with_oracle_info.csv"));
        adsorbHelper.addSimpleNode("pathFullTDMDataset", preprocessedFolder.resolve("tdm_full_with_oracle_info.csv"));

        Element gloveFile = document.createElement("pathGloveFile");
        gloveFile.appendChild(document.createTextNode(CommonPaths.GLOVE_FILE.toString()));
        adsorb.appendChild(gloveFile);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        Source domSource = new DOMSource(document);
        Result streamResult = new StreamResult(CommonPaths.XML_FILES.resolve(name + "XML.xml").toFile());
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        //noinspection HardcodedFileSeparator
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(domSource, streamResult);

        LOGGER.info("XML-file created");
    }
}

package fileGeneration;

import configFile.ConfigFileReader;
import helpers.ProcessExecutor;

import java.util.logging.Logger;

/**
 * @author panc
 */
public class TermByDocumentCreation {
    private final static Logger logger = Logger.getLogger(TermByDocumentCreation.class.getName());

    public static void createTBD(ConfigFileReader cfr) {

        String pathTbDRScript = cfr.getPathTbDRScript();
        String docs_location = cfr.getPathRScripts();
        String documentsTrainingSet = cfr.getPathTrainingSetDocuments();
        String documentsTestSet = cfr.getPathTestSetDocuments();
        String simplifiedOracle_path = cfr.getPathSimplifiedTruthSet();
        String nameOfAttributeID = cfr.getNameOfAttributeID();
        String nameOfAttributeClass = cfr.getNameOfAttributeClass();
        String nameOfAttributeText = cfr.getNameOfAttributeText();
        String oracleFolder = cfr.getPathBaseFolder();

        // path of command "/usr/local/bin/Rscript" identified using: "which Rscript" from command line
        logger.info("R-Script execution begins:");
        ProcessExecutor.execute("Rscript", pathTbDRScript, docs_location, documentsTrainingSet,
                documentsTestSet, simplifiedOracle_path, nameOfAttributeID, nameOfAttributeClass, nameOfAttributeText,
                oracleFolder);
    }
}

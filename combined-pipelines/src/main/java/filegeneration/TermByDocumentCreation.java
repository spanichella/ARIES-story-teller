package filegeneration;

import configfile.ConfigFileReader;
import helpers.RscriptExecutor;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author panc
 */
final class TermByDocumentCreation {
    private static final Logger logger = Logger.getLogger(TermByDocumentCreation.class.getName());

    static void createTBD(ConfigFileReader cfr) throws IOException {
        String pathTbDRScript = cfr.getPathTbDRScript();
        String docsLocation = cfr.getPathRScripts();
        String documentsTrainingSet = cfr.getPathTrainingSetDocuments();
        String documentsTestSet = cfr.getPathTestSetDocuments();
        String simplifiedOraclePath = cfr.getPathSimplifiedTruthSet();
        String nameOfAttributeID = cfr.getNameOfAttributeID();
        String nameOfAttributeClass = cfr.getNameOfAttributeClass();
        String nameOfAttributeText = cfr.getNameOfAttributeText();
        String oracleFolder = cfr.getPathBaseFolder();

        // path of command "/usr/local/bin/Rscript" identified using: "which Rscript" from command line
        logger.info("R-Script execution begins:");
        RscriptExecutor.execute(pathTbDRScript, docsLocation, documentsTrainingSet,
                documentsTestSet, simplifiedOraclePath, nameOfAttributeID, nameOfAttributeClass, nameOfAttributeText,
                oracleFolder);
    }
}

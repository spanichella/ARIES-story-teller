package filegeneration;

import configfile.ConfigFileReader;
import helpers.RscriptExecutor;
import java.util.logging.Logger;

/**
 * @author panc
 */
final class TermByDocumentCreation {
    private static final Logger LOGGER = Logger.getLogger(TermByDocumentCreation.class.getName());

    static void createTBD(ConfigFileReader cfr) throws RscriptExecutor.ExecutionException, RscriptExecutor.RunFailedException {
        String pathTbDRScript = cfr.pathTbDRScript;
        String docsLocation = cfr.pathRScripts;
        String documentsTrainingSet = cfr.pathTrainingSetDocuments;
        String documentsTestSet = cfr.pathTestSetDocuments;
        String simplifiedOraclePath = cfr.pathSimplifiedTruthSet;
        String nameOfAttributeID = cfr.nameOfAttributeID;
        String nameOfAttributeClass = cfr.nameOfAttributeClass;
        String nameOfAttributeText = cfr.nameOfAttributeText;
        String oracleFolder = cfr.pathBaseFolder;

        // path of command "/usr/local/bin/Rscript" identified using: "which Rscript" from command line
        LOGGER.info("R-Script execution begins:");
        RscriptExecutor.execute(pathTbDRScript, docsLocation, documentsTrainingSet,
                documentsTestSet, simplifiedOraclePath, nameOfAttributeID, nameOfAttributeClass, nameOfAttributeText,
                oracleFolder);
    }
}

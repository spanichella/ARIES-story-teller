package filegeneration;

import helpers.RscriptExecutor;
import java.util.logging.Logger;
import pipelines.Configuration;

/**
 * @author panc
 */
final class TermByDocumentCreation {
    private static final Logger LOGGER = Logger.getLogger(TermByDocumentCreation.class.getName());

    static void createTBD(Configuration cfr) throws RscriptExecutor.ExecutionException, RscriptExecutor.RunFailedException {
        String pathTbDRScript = cfr.pathTbDRScript.toString();
        String docsLocation = cfr.pathRScripts.toString();
        String documentsTrainingSet = cfr.pathTrainingSetDocuments;
        String documentsTestSet = cfr.pathTestSetDocuments;
        String simplifiedOraclePath = cfr.pathSimplifiedTruthSet.toString();
        String nameOfAttributeID = cfr.nameOfAttributeID;
        String nameOfAttributeClass = cfr.nameOfAttributeClass;
        String nameOfAttributeText = cfr.nameOfAttributeText;
        String oracleFolder = cfr.pathBaseFolder.toString();

        // path of command "/usr/local/bin/Rscript" identified using: "which Rscript" from command line
        LOGGER.info("R-Script execution begins:");
        RscriptExecutor.execute(pathTbDRScript, docsLocation, documentsTrainingSet,
                documentsTestSet, simplifiedOraclePath, nameOfAttributeID, nameOfAttributeClass, nameOfAttributeText,
                oracleFolder);
    }
}

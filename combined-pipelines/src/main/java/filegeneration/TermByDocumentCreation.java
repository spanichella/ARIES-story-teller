package filegeneration;

import helpers.CommonPaths;
import helpers.RscriptExecutor;
import java.nio.file.Path;
import java.util.logging.Logger;
import pipelines.Configuration;

/**
 * @author panc
 */
final class TermByDocumentCreation {
    private static final Path pathTbDRScript = CommonPaths.R_SCRIPTS.resolve("MainScript.r");
    private static final Logger LOGGER = Logger.getLogger(TermByDocumentCreation.class.getName());

    static void createTBD(Configuration cfr) throws RscriptExecutor.ExecutionException, RscriptExecutor.RunFailedException {
        String docsLocation = cfr.pathRScripts.toString();
        String documentsTrainingSet = cfr.pathTrainingSetDocuments;
        String documentsTestSet = cfr.pathTestSetDocuments;
        String simplifiedOraclePath = cfr.pathSimplifiedTruthSet.toString();
        String nameOfAttributeID = Configuration.nameOfAttributeID;
        String nameOfAttributeClass = Configuration.nameOfAttributeClass;
        String nameOfAttributeText = cfr.nameOfAttributeText;
        String oracleFolder = cfr.pathBaseFolder.toString();

        // path of command "/usr/local/bin/Rscript" identified using: "which Rscript" from command line
        LOGGER.info("R-Script execution begins:");
        RscriptExecutor.execute(pathTbDRScript.toString(), docsLocation, documentsTrainingSet,
                documentsTestSet, simplifiedOraclePath, nameOfAttributeID, nameOfAttributeClass, nameOfAttributeText,
                oracleFolder);
    }
}

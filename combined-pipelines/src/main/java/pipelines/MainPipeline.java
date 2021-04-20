package pipelines;

import configfile.ConfigFileReader;
import filegeneration.FileGeneration;
import helpers.CommonPaths;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.SWMGui;

/**
 * This class extends the Main Program class by supporting the execution of
 * the main steps of the ML- and DL-based classification pipelines
 * concerning User reviews (extracted from App stores).
 *
 * @author panc
 */
public class MainPipeline {
    private static final Logger logger = Logger.getLogger(MainPipeline.class.getName());

    public static void runPipeline(String selectedPipeline, String type) throws Exception {
        Path xmlFiles = CommonPaths.XML_FILES;

        //chooses path of config file according to data-type
        Path pathConfigFile;
        if (type.equals("Requirement-Specifications")) {
            pathConfigFile = xmlFiles.resolve("RequirementSpecificationsXML.xml");
        } else if (type.equals("User-Reviews")) {
            pathConfigFile = xmlFiles.resolve("UserReviewsXML.xml");
        } else {
            throw new IllegalArgumentException("type \"" + type + "\" not recognized: use Requirement-Specifications or User-Reviews");
        }
        logger.log(Level.INFO, "Path of ConfigFile: " + pathConfigFile);

        //Read Configfile
        ConfigFileReader configFileReader = new ConfigFileReader(pathConfigFile);
        //Generate files for ML/DL
        logger.info("Starting file generation ");
        FileGeneration.oracleAnalysis(configFileReader);

        //run selected pipeline
        if (selectedPipeline.equals("ML")) {
            MLPipeline.performMlAnalysis(configFileReader);
        } else if (selectedPipeline.equals("DL")) {
            DLPipeline.runDLPipeline(configFileReader);
        } else {
            logger.severe("Pipeline selection invalid");
        }
        logger.info("Program execution completed");
        SWMGui.killFrames();
    }
}

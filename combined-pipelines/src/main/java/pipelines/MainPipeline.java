package pipelines;

import configfile.ConfigFileReader;
import filegeneration.FileGeneration;
import helpers.CommonPaths;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
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

    public static void runPipeline(String pipelineType, @Nonnull DataType dataType) throws Exception {
        Path xmlFiles = CommonPaths.XML_FILES;

        //chooses path of config file according to data-type
        Path pathConfigFile = switch (dataType) {
            case REQUIREMENT_SPECIFICATIONS -> xmlFiles.resolve("RequirementSpecificationsXML.xml");
            case USER_REVIEWS -> xmlFiles.resolve("UserReviewsXML.xml");
        };

        logger.log(Level.INFO, "Path of ConfigFile: " + pathConfigFile);

        //Read Config file
        ConfigFileReader configFileReader = new ConfigFileReader(pathConfigFile);
        //Generate files for ML/DL
        logger.info("Starting file generation ");
        FileGeneration.oracleAnalysis(configFileReader);

        //run selected pipeline
        if (pipelineType.equals("ML")) {
            MLPipeline.performMlAnalysis(configFileReader);
        } else if (pipelineType.equals("DL")) {
            DLPipeline.runDLPipeline(configFileReader);
        } else {
            logger.severe("Pipeline selection invalid");
        }
        logger.info("Program execution completed");
        SWMGui.killFrames();
    }
}

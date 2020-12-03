package pipelines;

import configFile.ConfigFileReader;
import fileGeneration.FileGeneration;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author panc
 * <p>
 * This class extends the Main Program class by supporting the execution of
 * the main steps of the ML- and DL-based classification pipelines
 * concerning User reviews (extracted from App stores).
 */
public class MainPipeline{
    private final static Logger logger = Logger.getLogger(MainPipeline.class.getName());

    public static void runPipeline(String mainPath, String selectedPipeline,String type) throws Exception {


        //chooses path of config file according to data-type
        String pathConfigFile = "";
        if (type.equals("Requirement-Specifications")) {
            pathConfigFile = mainPath + "Resources/XMLFiles/RequirementSpecificationsXML.xml";
        } else if (type.equals("User-Reviews")) {
            pathConfigFile = mainPath + "Resources/XMLFiles/UserReviewsXML.xml";
        } else {
            System.out.println("type not recognized: use Requirement-Specifications or UR");
            System.exit(1);
        }
        logger.log(Level.INFO, "Path of ConfigFile: "+pathConfigFile);

        //Read Configfile
        ConfigFileReader configFileReader = new ConfigFileReader(pathConfigFile);
        //Generate files for ML/DL
        logger.info("Starting file generation ");
        FileGeneration.oracleAnalysis(configFileReader);

        //run selected pipeline
        if(selectedPipeline.equals("ML")){
            MLPipeline.performMlAnalysis(configFileReader);
        }else if(selectedPipeline.equals("DL")){
            DLPipeline.runDLPipeline(configFileReader);
        }else{
            logger.severe("Pipeline selection invalid");
        }
        logger.info("Program execution completed");

    }
}

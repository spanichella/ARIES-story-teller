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

    //TODO: these are no longer needed
    /*
    // PART 1. - ORACLE PARAMETERS
    private String pathRScripts;
    //local path to the R script "MainScript.r"
    private String pathOracleRScript;
    // here are located the "documents" folder and the  "utilities.R script"
    private String pathBaseFolder;
    // path oracle
    private String pathTruthSet;
    // path threshold
    private double splitSetPercentage;

    //local path to the R script "MainScript.r"
    private String pathTbDRScript;

    // locations of training and test sets
    private String pathTrainingSetDocuments;
    private String pathTestSetDocuments;
    // path oracle
    private String pathSimplifiedTruthSet;
    */

    private final static Logger logger = Logger.getLogger(MainPipeline.class.getName());

    public static void runPipeline(String mainPath, String selectedPipeline,String type) throws Exception {


        //chooses path of config file according to data-type
        String pathConfigFile = "";
        if (type.equals("RS")) {
            pathConfigFile = mainPath + "Resources/XMLFiles/RequirementSpecificationsXML.xml";
        } else if (type.equals("UR")) {
            pathConfigFile = mainPath + "Resources/XMLFiles/UserReviewsXML.xml";
        } else {
            System.out.println("type not recognized: use RS or UR");
            System.exit(1);
        }
        logger.log(Level.INFO, "Path of ConfigFile: "+pathConfigFile);

        //Read Config
        ConfigFileReader configFileReader = new ConfigFileReader(pathConfigFile);
        FileGeneration.oracleAnalysis(configFileReader);
        //Generate files for ML
        System.out.println(selectedPipeline);
        if(selectedPipeline.equals("ML")){
            MlAnalysis.performMlAnalysis(configFileReader);
        }else if(selectedPipeline.equals("DL")){
           // DLPipeline.runDLPipeline(configFileReader);
        }else if(selectedPipeline.equals("Both")){
            MlAnalysis.performMlAnalysis(configFileReader);
            System.out.print("both");
        }else{
            logger.severe("Pipeline selection invalid");
        }

    }

    // TODO: This is no longer needed
    /*
    // loading arguments for various pipeline steps
    public MainPipeline(String dataType, String nameOfAttributeID, String nameOfAttributeText, String nameOfAttributeClass) {
        //loading data concerning the oracle
        this.dataType = dataType;
        this.nameOfAttributeID = nameOfAttributeID;
        this.nameOfAttributeText = nameOfAttributeText;
        this.nameOfAttributeClass = nameOfAttributeClass;
    }

    public String getPathRScripts() {
        return pathRScripts;
    }

    public void setPathRScripts(String pathRScripts) {
        this.pathRScripts = pathRScripts;
    }

    public String getPathOracleRScript() {
        return pathOracleRScript;
    }

    public void setPathOracleRScript(String pathOracleRScript) {
        this.pathOracleRScript = pathOracleRScript;
    }

    public String getPathBaseFolder() {
        return pathBaseFolder;
    }

    public void setPathBaseFolder(String pathBaseFolder) {
        this.pathBaseFolder = pathBaseFolder;
    }

    public String getPathTruthSet() {
        return pathTruthSet;
    }

    public void setPathTruthSet(String pathTruthSet) {
        this.pathTruthSet = pathTruthSet;
    }

    public double getSplitSetPercentage() {
        return splitSetPercentage;
    }

    public void setSplitSetPercentage(double splitSetPercentage) {
        this.splitSetPercentage = splitSetPercentage;
    }

    public String getPathTbDRScript() {
        return pathTbDRScript;
    }

    public void setPathTbDRScript(String pathTbDRScript) {
        this.pathTbDRScript = pathTbDRScript;
    }

    public String getPathTrainingSetDocuments() {
        return pathTrainingSetDocuments;
    }

    public void setPathTrainingSetDocuments(String pathTrainingSetDocuments) {
        this.pathTrainingSetDocuments = pathTrainingSetDocuments;
    }

    public String getPathTestSetDocuments() {
        return pathTestSetDocuments;
    }

    public void setPathTestSetDocuments(String pathTestSetDocuments) {
        this.pathTestSetDocuments = pathTestSetDocuments;
    }

    public String getPathSimplifiedTruthSet() {
        return pathSimplifiedTruthSet;
    }

    public void setPathSimplifiedTruthSet(String pathSimplifiedTruthSet) {
        this.pathSimplifiedTruthSet = pathSimplifiedTruthSet;
    }
    */
}

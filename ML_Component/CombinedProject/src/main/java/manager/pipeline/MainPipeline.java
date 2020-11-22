package manager.pipeline;

import R_Tm_package.TermByDocumentCreation;
import configFile.ConfigFileReader;
import configFile.XMLInitializer;
import oracle.OracleUserReviewsAnalyzer;

/**
 * @author panc
 * <p>
 * This class extends the Main Program class by supporting the execution of
 * the main steps of the ML- and DL-based classification pipelines
 * concerning User reviews (extracted from App stores).
 */
public class MainPipeline extends MainProgram {

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

    public static void main(String[] args) throws Exception {

        String type = "UR";
        //set mainPath according to Operating System
        String mainPath = MainPipeline.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/classes/", "");
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            mainPath = mainPath.substring(1);
        }
        System.out.print("LOCAL PATH:"+mainPath);
        //create XML files with paths, etc.
        XMLInitializer.createXML(mainPath, type);

        //select path of XML according to data-type
        String pathXMLConfigFile = "";
        if (type.equals("RS")) {
            pathXMLConfigFile = mainPath + "Resources/XMLFiles/RequirementSpecificationsXML.xml";
        } else if (type.equals("UR")) {
            pathXMLConfigFile = mainPath + "Resources/XMLFiles/UserReviewsXML.xml";
        } else {
            System.out.println("type not recognized: use RS or UR");
            System.exit(1);
        }
        //Read Config
        ConfigFileReader configFileReader = new ConfigFileReader(pathXMLConfigFile);
        //Generate files for ML
        FileGeneration.oracleAnalysis(configFileReader);
        //ML predictions
        ThirdPart.performMlAnalysis(configFileReader);

    }


    // loading arguments for various pipeline steps
    public MainPipeline(String dataType, String nameOfAttributeID, String nameOfAttributeText, String nameOfAttributeClass) {
        //loading data concerning the oracle
        this.dataType = dataType;
        this.nameOfAttributeID = nameOfAttributeID;
        this.nameOfAttributeText = nameOfAttributeText;
        this.nameOfAttributeClass = nameOfAttributeClass;
    }


    public OracleUserReviewsAnalyzer runOracleAnalysis() {
        return new OracleUserReviewsAnalyzer(this.dataType, this.nameOfAttributeID, this.nameOfAttributeText, this.nameOfAttributeClass, this.pathOracleRScript, this.pathBaseFolder, this.pathTruthSet, this.splitSetPercentage);
    }


    public void runTbDAnalysis(String nameOfAttributeID, String nameOfAttributeClass) {
        String[] tbdArgs = new String[9];
        tbdArgs[0] = this.pathTbDRScript;
        tbdArgs[1] = this.pathRScripts;
        tbdArgs[2] = this.pathTrainingSetDocuments;
        tbdArgs[3] = this.pathTestSetDocuments; //we pass this as String argument, it will be converted later
        tbdArgs[4] = this.pathSimplifiedTruthSet;
        tbdArgs[5] = this.nameOfAttributeID;
        tbdArgs[6] = this.nameOfAttributeClass;
        tbdArgs[7] = this.nameOfAttributeText;
        tbdArgs[8] = this.pathBaseFolder;
        TermByDocumentCreation.main(tbdArgs);
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
}

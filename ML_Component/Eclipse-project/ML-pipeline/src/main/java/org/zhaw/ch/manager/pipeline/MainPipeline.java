package org.zhaw.ch.manager.pipeline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.zhaw.ch.R_Tm_package.TermByDocumentCreation;
import org.zhaw.ch.configFile.ConfigFileReader;
import org.zhaw.ch.configFile.XMLInitializer;
import org.zhaw.ch.ml.WekaClassifier;
import org.zhaw.ch.oracle.OracleRequirementSpecificationsAnalyzer;
import org.zhaw.ch.oracle.OracleUserReviewsAnalyzer;

/**
 * @author panc
 * <p>
 * This class extends the Main Program class by supporting the execution of
 * the main steps of the ML- and DL-based classification pipelines
 * concerning User reviews (extracted from App stores).
 */
public class MainPipeline extends MainProgram {

    // PART 1. - ORACLE PARAMETERS
    private String docs_location;
    //local path to the R script "MainScript.r"
    private String pathRScriptsFolder;
    // here are located the "documents" folder and the  "utilities.R script"
    private String pathBaseFolder;
    // path oracle
    private String pathTruthFile;
    // path threshold
    private double threshold;

    //local path to the R script "MainScript.r"
    private String pathTbDRScript;

    // locations of training and test sets
    private String documentsTrainingSet;
    private String documentsTestSet;
    // path oracle
    private String simplifiedOracle_path;

    private static String OS = System.getProperty("os.name").toLowerCase();

    /**
     * @param args
     * @throws Exception
     */

    public static void main(String[] args) throws Exception {

        //String mode = "ML_ANALYSIS";
        //String mode = args[0];
        /*if (!mode.equals("ORACLE_AND_TbD_ANALYSIS") && !mode.equals("ML_ANALYSIS") )
        {
        	System.out.println(" Error message concerning the Option \"mode\" (it must to be \"ML_ANALYSIS\" or \"ORACLE_AND_TbD_ANALYSIS\") -> "+mode );
        }*/
        String type = "UR";
        // set mainPath according to Operating System
        String mainPath = MainRequirementSpecificationsPipeline.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/classes/","");
        if(OS.contains("win")){
            mainPath=mainPath.substring(1);
        }
        XMLInitializer i = new XMLInitializer();
        i.createXML(mainPath,type);
        String pathXMLConfigFile = "";
        if(type=="RS") {
             pathXMLConfigFile = mainPath+"Resources/XMLFiles/RequirementSpecificationsXML.xml";
        }else if (type=="UR"){
             pathXMLConfigFile = mainPath+"Resources/XMLFiles/UserReviewsXML.xml";
        }else{
            System.out.println("type not recognized: use RS or UR");
            System.exit(1);
        }
        ConfigFileReader configFileReader = null;
        FirstPart fp = new FirstPart();
        fp.firstPart(configFileReader,pathXMLConfigFile);
        /**/
        // PART 3. - ML prediction
        ThirdPart tp = new ThirdPart();
        tp.tp(configFileReader,pathXMLConfigFile);

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

        OracleUserReviewsAnalyzer oracleUserReviewsAnalyzer = new OracleUserReviewsAnalyzer(this.dataType, this.nameOfAttributeID, this.nameOfAttributeText, this.nameOfAttributeClass, this.pathRScriptsFolder, this.pathBaseFolder, this.pathTruthFile, this.threshold);

        return oracleUserReviewsAnalyzer;
    }


    public void runTbDAnalysis(String nameOfAttributeID, String nameOfAttributeClass) {
        String[] tbdArgs = new String[9];
        tbdArgs[0] = this.pathTbDRScript;
        tbdArgs[1] = this.docs_location;
        tbdArgs[2] = this.documentsTrainingSet;
        tbdArgs[3] = this.documentsTestSet; //we pass this as String argument, it will be converted later
        tbdArgs[4] = this.simplifiedOracle_path;
        tbdArgs[5] = this.nameOfAttributeID;
        tbdArgs[6] = this.nameOfAttributeClass;
        tbdArgs[7] = this.nameOfAttributeText;
        tbdArgs[8] = this.pathBaseFolder;
        TermByDocumentCreation.main(tbdArgs);

    }


    public String getDocs_location() {
        return docs_location;
    }

    public void setDocs_location(String docs_location) {
        this.docs_location = docs_location;
    }

    public String getPathRScriptsFolder() {
        return pathRScriptsFolder;
    }

    public void setPathRScriptsFolder(String pathRScriptsFolder) {
        this.pathRScriptsFolder = pathRScriptsFolder;
    }

    public String getPathBaseFolder() {
        return pathBaseFolder;
    }

    public void setPathBaseFolder(String pathBaseFolder) {
        this.pathBaseFolder = pathBaseFolder;
    }

    public String getPathTruthFile() {
        return pathTruthFile;
    }

    public void setPathTruthFile(String pathTruthFile) {
        this.pathTruthFile = pathTruthFile;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getPathTbDRScript() {
        return pathTbDRScript;
    }

    public void setPathTbDRScript(String pathTbDRScript) {
        this.pathTbDRScript = pathTbDRScript;
    }

    public String getDocumentsTrainingSet() {
        return documentsTrainingSet;
    }

    public void setDocumentsTrainingSet(String documentsTrainingSet) {
        this.documentsTrainingSet = documentsTrainingSet;
    }

    public String getDocumentsTestSet() {
        return documentsTestSet;
    }

    public void setDocumentsTestSet(String documentsTestSet) {
        this.documentsTestSet = documentsTestSet;
    }

    public String getSimplifiedOracle_path() {
        return simplifiedOracle_path;
    }

    public void setSimplifiedOracle_path(String simplifiedOracle_path) {
        this.simplifiedOracle_path = simplifiedOracle_path;
    }


}

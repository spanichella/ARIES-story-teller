package oracle;

import configFile.ConfigFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;


/**
 * @author panc
 */
//public class OracleUserReviewsAnalyzer extends Oracle {
public class OracleUserReviewsAnalyzer {
    //TODO: no longer needed

//    public OracleUserReviewsAnalyzer(String dataType, String nameOfAttributeID, String nameOfAttributeText, String nameOfAttributeClass, String pathRScriptOracle, String baseFolder, String oracle_path, double threshold) {
//        super(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);
//        String[] oracleArgs = new String[4];
//        oracleArgs[0] = pathRScriptOracle;
//        oracleArgs[1] = baseFolder;
//        oracleArgs[2] = oracle_path;
//        oracleArgs[3] = "" + threshold; //we pass this as String argument, it will be converted later
//    }

    private final static Logger logger = Logger.getLogger(OracleUserReviewsAnalyzer.class.getName());

    //TODO: this is the same code as runReqSpecRScript
    public static void runUserReviewRScript(ConfigFileReader cfr) {
        //TODO check that none of these is null before exec of script
        String pathRScriptOracle = cfr.getPathRScriptOracle();
        String baseFolder = cfr.getPathBaseFolder();
        String oracle_path = cfr.getPathTruthSet();
        String nameOfAttributeID = cfr.getNameOfAttributeID();
        String nameOfAttributeText = cfr.getNameOfAttributeText();
        String nameOfAttributeClass = cfr.getNameOfAttributeClass();
        //TODO implement threshold
        String threshold = String.valueOf(0.5);

        //command to execute
        String command = String.join(" ","Rscript", pathRScriptOracle,baseFolder,oracle_path,threshold,nameOfAttributeID,nameOfAttributeText,nameOfAttributeClass);
        logger.info("Command used: "+command);

        // -- Linux/Mac osx --
        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package oracle;

import configFile.ConfigFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;


/**
 * @author panc
 */
public class OracleRequirementSpecificationsAnalyzer{

    private final static Logger logger = Logger.getLogger(OracleUserReviewsAnalyzer.class.getName());


    public static void runReqSpecRScript(ConfigFileReader cfr) {
        String pathRScriptOracle = cfr.getPathRScriptOracle();
        String baseFolder = cfr.getPathBaseFolder();
        String oracle_path = cfr.getPathTruthSet();
        String nameOfAttributeID = cfr.getNameOfAttributeID();
        String nameOfAttributeText = cfr.getNameOfAttributeText();
        String nameOfAttributeClass = cfr.getNameOfAttributeClass();
        String threshold = String.valueOf(cfr.getThreshold());

        //make command for r-script
        String command = String.join(" ","Rscript", pathRScriptOracle,baseFolder,oracle_path,threshold,nameOfAttributeID,nameOfAttributeText,nameOfAttributeClass);
        logger.info("R-Script execution begins:");
        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
            logger.info("R-Script execution complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

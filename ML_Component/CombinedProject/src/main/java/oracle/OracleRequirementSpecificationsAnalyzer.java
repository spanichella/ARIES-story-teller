package oracle;

import configFile.ConfigFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * @author panc
 */
public class OracleRequirementSpecificationsAnalyzer extends Oracle {

    public OracleRequirementSpecificationsAnalyzer(String dataType, String nameOfAttributeID, String nameOfAttributeText, String nameOfAttributeClass, String pathRScriptOracle, String baseFolder, String oracle_path, double threshold) {
        super(dataType, nameOfAttributeID, nameOfAttributeText, nameOfAttributeClass);
        // TODO NOT NEEDED ANYWHERE
        String[] oracleArgs = new String[4];
        oracleArgs[0] = pathRScriptOracle;
        oracleArgs[1] = baseFolder;
        oracleArgs[2] = oracle_path;
        oracleArgs[3] = "" + threshold; //we pass this as String argument, it will be converted later
    }

    public static void runReqSpecRScript(ConfigFileReader cfr) {

        //TODO check that none of these is null before exec of script
        String pathRScriptOracle = cfr.getPathRScriptOracle();
        String baseFolder = cfr.getPathBaseFolder();
        String oracle_path = cfr.getPathTruthSet();
        String nameOfAttributeID = cfr.getNameOfAttributeID();
        String nameOfAttributeText = cfr.getNameOfAttributeText();
        String nameOfAttributeClass = cfr.getNameOfAttributeClass();
        //todo implement threshold
        String threshold = String.valueOf(0.5);

        //command to execute
        String command = "Rscript " + pathRScriptOracle + " " + baseFolder + " " + oracle_path + " " + threshold + " " + nameOfAttributeID + " " + nameOfAttributeText + " " + nameOfAttributeClass;
        System.out.println("RSSSSSSSSSSSSSSSSSSSSSSSSS" + pathRScriptOracle);

        // -- Linux/Mac osx --
        try {
            //Process process = Runtime.getRuntime().exec("ls /Users/panc/Desktop");
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
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

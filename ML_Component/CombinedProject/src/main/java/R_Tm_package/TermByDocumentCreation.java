package R_Tm_package;

import configFile.ConfigFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author panc
 */

public class TermByDocumentCreation {

    public static void createTBD(ConfigFileReader cfr) {

        //TODO check that none of these is null before exec of script
        String pathTbDRScript = cfr.getPathTbDRScript();
        String docs_location = cfr.getPathRScripts();
        String documentsTrainingSet = cfr.getPathTrainingSetDocuments();
        String documentsTestSet = cfr.getPathTestSetDocuments();
        String simplifiedOracle_path = cfr.getPathSimplifiedTruthSet();
        String nameOfAttributeID = cfr.getNameOfAttributeID();
        String nameOfAttributeClass = cfr.getNameOfAttributeClass();
        String nameOfAttributeText = cfr.getNameOfAttributeText();
        String oracleFolder = cfr.getPathBaseFolder();

        //command to execute
        String command = "Rscript " + pathTbDRScript + " " + docs_location + " " + documentsTrainingSet + " " + documentsTestSet + " " + simplifiedOracle_path + " " + nameOfAttributeID + " " + nameOfAttributeClass + " " + nameOfAttributeText + " " + oracleFolder;// path of command "/usr/local/bin/Rscript" identified using: "which Rscript" from command line
        System.out.println(command);

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

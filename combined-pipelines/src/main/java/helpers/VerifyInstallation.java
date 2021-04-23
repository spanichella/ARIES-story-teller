package helpers;

import java.nio.file.Path;
import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;

final class VerifyInstallation {
    public static void main(String[] args) {
        boolean failed = false;
        Path mainScript = CommonPaths.R_SCRIPTS.resolve("install.r");
        try {
            RscriptExecutor.execute(mainScript.toString());
        } catch (ExecutionException e) {
            System.err.println("ERROR: RScript not found.");
            System.err.println("Verify that you have a running installation of R and it can be found in the $PATH variable");
            failed = true;
        } catch (RunFailedException e) {
            System.err.println("ERROR: The installation did not succeed. Try to run " + mainScript + " manually.");
            e.printStackTrace();
            failed = true;
        }

        Path gloveFile = CommonPaths.GLOVE_FILE;
        if (!gloveFile.toFile().exists()) {
            //noinspection HardcodedFileSeparator
            System.err.println("ERROR: The glove file " + gloveFile + " does not exist. Please download it from https://www.kaggle.com/danielwillgeorge/glove6b100dtxt");
            failed = true;
        }

        if (failed) {
            System.exit(1);
        }
    }
}

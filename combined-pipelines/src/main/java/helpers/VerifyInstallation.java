package helpers;

import helpers.RscriptExecutor.ExecutionException;
import helpers.RscriptExecutor.RunFailedException;
import java.nio.file.Path;

final class VerifyInstallation {
    @SuppressWarnings({"UseOfSystemOutOrSystemErr", "CallToPrintStackTrace"})
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
            System.err.printf("ERROR: The installation did not succeed. Try to run %s manually.%n", mainScript);
            e.printStackTrace();
            failed = true;
        }

        Path gloveFile = CommonPaths.GLOVE_FILE;
        if (!gloveFile.toFile().exists()) {
            System.err.printf("ERROR: The glove file %s does not exist. Please download it from %s%n", gloveFile,
                "https://www.kaggle.com/danielwillgeorge/glove6b100dtxt");
            failed = true;
        }

        if (failed) {
            System.exit(1);
        }
    }
}

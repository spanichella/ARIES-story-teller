package helpers;

public class RscriptValidator {
    public static void main(String[] args) {
        String mainScript = RscriptValidator.class.getResource("/R-Scripts/install.r").getFile();
        try {
            RscriptExecutor.execute(mainScript);
        } catch (RscriptExecutor.ExecutionException e) {
            System.err.println("ERROR: RScript not found.");
            System.err.println("Verify that you have a running installation of R and it can be found in the $PATH variable");
            System.exit(1);
        } catch (RscriptExecutor.ProcessException e) {
            System.err.println("ERROR: The installation did not succeed. Try to run " + mainScript + " manually.");
            e.printStackTrace();
            System.exit(2);
        }
    }
}

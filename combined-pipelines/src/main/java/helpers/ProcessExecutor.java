package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessExecutor {
    private final static Logger LOGGER = Logger.getLogger(ProcessExecutor.class.getName());

    /**
     * Executes the command and arguments as a system command
     * @param commandParts The command and its arguments to run
     * @throws ExecutionException If there is a general problem with executing the process (e.g. the command is not found)
     * @throws RunFailedException If the run finishes with a non-zero exit status
     */
    public static void execute(String... commandParts) {
        try {
            Process process = Runtime.getRuntime().exec(commandParts);
            logStream(process.getInputStream(), Level.INFO);
            logStream(process.getErrorStream(), Level.SEVERE);
            if (process.exitValue() != 0) {
                throw new RunFailedException(commandParts);
            }
        } catch (IOException exception) {
            throw new ExecutionException(commandParts, exception);
        }
    }

    private static void logStream(InputStream stream, Level level) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            reader.lines().forEach(line -> LOGGER.log(level, line));
        }
    }

    public static abstract class ProcessException extends RuntimeException {
        public final String[] commandParts;

        public ProcessException(String[] commandParts, String message) {
            this(commandParts, message, null);
        }

        public ProcessException(String[] commandParts, String message, Throwable cause) {
            super(message, cause);
            this.commandParts = commandParts;
        }

        protected static String toCommand(String[] commandParts) {
            return String.join(" ", commandParts);
        }
    }

    public static class ExecutionException extends ProcessException {
        public ExecutionException(String[] commandParts, Throwable cause) {
            super(commandParts, "Could not execute the script \"" + toCommand(commandParts) + "\"", cause);
        }
    }

    public static class RunFailedException extends ProcessException {
        public RunFailedException(String[] commandParts) {
            super(commandParts, "Running the script \"" + toCommand(commandParts) + "\" failed");
        }
    }
}

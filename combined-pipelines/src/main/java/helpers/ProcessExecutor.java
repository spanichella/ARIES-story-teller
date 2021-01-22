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
    public static void execute(String... commandParts) throws ProcessException {
        Process process;
        try {
            process = Runtime.getRuntime().exec(commandParts);
            logStream(process.getInputStream(), Level.INFO);
            logStream(process.getErrorStream(), Level.SEVERE);
        } catch (IOException exception) {
            throw new ExecutionException(commandParts, exception);
        }
        if (process.exitValue() != 0) {
            throw new RunFailedException(commandParts);
        }
    }

    private static void logStream(InputStream stream, Level level) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            reader.lines().forEach(line -> LOGGER.log(level, line));
        }
    }

    public static abstract class ProcessException extends IOException {
        private static final long serialVersionUID = -4081653183392141979L;

        public ProcessException(String message) {
            super(message);
        }

        public ProcessException(String message, Throwable cause) {
            super(message, cause);
        }

        protected static String toCommand(String[] commandParts) {
            return String.join(" ", commandParts);
        }
    }

    public static class ExecutionException extends ProcessException {
        private static final long serialVersionUID = 49748945902517787L;

        public ExecutionException(String[] commandParts, Throwable cause) {
            super("Could not execute the script \"" + toCommand(commandParts) + "\"", cause);
        }
    }

    public static class RunFailedException extends ProcessException {
        private static final long serialVersionUID = -592869500939986619L;

        public RunFailedException(String[] commandParts) {
            super("Running the script \"" + toCommand(commandParts) + "\" failed");
        }
    }
}

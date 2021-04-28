package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class RscriptExecutor {
    private static final Logger LOGGER = Logger.getLogger(RscriptExecutor.class.getName());
    private static final String rscriptCommand = "Rscript";

    /**
     * Executes the command and arguments as a system command
     *
     * @param commandParts The command and its arguments to run
     * @throws ExecutionException If there is a general problem with executing the process (e.g. the command is not found)
     * @throws RunFailedException If the run finishes with a non-zero exit status
     */
    public static void execute(String... commandParts) throws ExecutionException, RunFailedException {
        executeWithRuntime(Runtime.getRuntime(), commandParts);
    }

    @SuppressWarnings("CallToRuntimeExec")
    public static void executeWithRuntime(Runtime runtime, String... commandParts) throws ExecutionException, RunFailedException {
        Process process;
        String[] fullCommand = toRscriptCommand(commandParts);
        try {
            process = runtime.exec(fullCommand);
            logStream(process.getInputStream(), Level.INFO);
            logStream(process.getErrorStream(), Level.SEVERE);
        } catch (IOException exception) {
            throw new ExecutionException(fullCommand, exception);
        }
        if (process.exitValue() != 0) {
            throw new RunFailedException(fullCommand);
        }
    }

    private static String[] toRscriptCommand(String[] rest) {
        String[] combined = new String[rest.length + 1];
        combined[0] = rscriptCommand;
        System.arraycopy(rest, 0, combined, 1, rest.length);
        return combined;
    }

    private static void logStream(InputStream stream, Level level) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
            if (!content.isEmpty()) {
                LOGGER.log(level, content);
            }
        }
    }

    private abstract static class ProcessException extends IOException {
        @Serial
        private static final long serialVersionUID = -4081653183392141979L;

        ProcessException(String message) {
            super(message);
        }

        ProcessException(String message, Throwable cause) {
            super(message, cause);
        }

        static String toCommand(String[] commandParts) {
            return String.join(" ", commandParts);
        }
    }

    public static final class ExecutionException extends ProcessException {
        @Serial
        private static final long serialVersionUID = 49748945902517787L;

        ExecutionException(String[] commandParts, Throwable cause) {
            super("Could not execute the script \"%s\"".formatted(toCommand(commandParts)), cause);
        }
    }

    public static final class RunFailedException extends ProcessException {
        @Serial
        private static final long serialVersionUID = -592869500939986619L;

        RunFailedException(String[] commandParts) {
            super("Running the script \"%s\" failed".formatted(toCommand(commandParts)));
        }
    }
}

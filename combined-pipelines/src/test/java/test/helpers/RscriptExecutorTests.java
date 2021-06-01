package test.helpers;

import helpers.RscriptExecutor;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import test.utilities.LogData;
import test.utilities.LogMessageFetcher;

@SuppressWarnings("CallToRuntimeExec")
final class RscriptExecutorTests {
    @Test
    void testArgumentsGetPassedCorrectly() throws Exception {
        Runtime runtimeMock = Mockito.mock(Runtime.class);
        Process returnedProcess = getProcessMock(0);
        Mockito.when(runtimeMock.exec(ArgumentMatchers.any(String[].class))).thenReturn(returnedProcess);

        RscriptExecutor.executeWithRuntime(runtimeMock, "some", "argument");
        Mockito.verify(runtimeMock, Mockito.times(1)).exec(new String[] { "Rscript", "some", "argument" });
        Mockito.verify(returnedProcess, Mockito.times(1)).getInputStream();
        Mockito.verify(returnedProcess, Mockito.times(1)).getErrorStream();
        Mockito.verify(returnedProcess, Mockito.times(1)).exitValue();

        Mockito.verifyNoMoreInteractions(runtimeMock, returnedProcess);
    }

    @Test
    void testLogIsWrittenCorrectly() throws Exception {
        LogMessageFetcher fetcher = new LogMessageFetcher();
        RscriptExecutor.LOGGER.setFilter(fetcher);

        Runtime runtimeMock = Mockito.mock(Runtime.class);
        Process returnedProcess = getProcessMock(0, "Test STDOUT Output", "Test STDERR Output");
        Mockito.when(runtimeMock.exec(ArgumentMatchers.any(String[].class))).thenReturn(returnedProcess);

        RscriptExecutor.executeWithRuntime(runtimeMock, "some", "argument");

        Assertions.assertEquals(fetcher.getMessages(), List.of(
            new LogData(Level.INFO, "Executing: Rscript some argument"),
            new LogData(Level.INFO, "Test STDOUT Output"),
            new LogData(Level.SEVERE, "Test STDERR Output")
        ));
    }

    @Test
    void testFailingCommand() throws Exception {
        Runtime runtimeMock = Mockito.mock(Runtime.class);
        Process returnedProcess = getProcessMock(1);
        Mockito.when(runtimeMock.exec(ArgumentMatchers.any(String[].class))).thenReturn(returnedProcess);

        RscriptExecutor.RunFailedException exception = Assertions.assertThrows(RscriptExecutor.RunFailedException.class,
            () -> RscriptExecutor.executeWithRuntime(runtimeMock, "some", "other", "argument"));
        Assertions.assertEquals(exception.getMessage(), "Running the script \"Rscript some other argument\" failed");
    }

    @Test
    void testExecutionException() throws Exception {
        Runtime runtimeMock = Mockito.mock(Runtime.class);
        IOException thrownException = new IOException("Some Message");
        Mockito.when(runtimeMock.exec(ArgumentMatchers.any(String[].class))).thenThrow(thrownException);

        RscriptExecutor.ExecutionException exception = Assertions.assertThrows(RscriptExecutor.ExecutionException.class,
            () -> RscriptExecutor.executeWithRuntime(runtimeMock, "some", "other", "argument"));
        Assertions.assertEquals(exception.getMessage(), "Could not execute the script \"Rscript some other argument\"");
        Assertions.assertEquals(exception.getCause(), thrownException);
    }

    private static Process getProcessMock(int exitValue) {
        return getProcessMock(exitValue, "", "");
    }

    private static Process getProcessMock(int exitValue, String stdout, String stderr) {
        Process returnedProcess = Mockito.mock(Process.class);
        Mockito.when(returnedProcess.getInputStream()).thenReturn(IOUtils.toInputStream(stdout, StandardCharsets.UTF_8));
        Mockito.when(returnedProcess.getErrorStream()).thenReturn(IOUtils.toInputStream(stderr, StandardCharsets.UTF_8));
        Mockito.when(returnedProcess.exitValue()).thenReturn(exitValue);
        return returnedProcess;
    }
}

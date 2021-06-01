package test.pipelines;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pipelines.AsyncPipeline;
import test.utilities.LogData;
import test.utilities.LogMessageFetcher;

@Timeout(1000)
final class AsyncPipelineTests {
    private final LogMessageFetcher fetcher = new LogMessageFetcher();

    @BeforeEach
    void setup() {
        AsyncPipeline.LOGGER.setFilter(fetcher);
    }

    @AfterEach
    void tearDown() {
        Assertions.assertNotNull(fetcher);
        Assertions.assertEquals(fetcher.getMessages(), List.of(
            new LogData(Level.INFO, "Thread Running")
        ));
    }

    @Test
    void testSuccessfulPipeline() throws Exception {
        CompletableFuture<Void> actionResult = new CompletableFuture<>();
        CompletableFuture<Optional<Throwable>> completeResult = new CompletableFuture<>();
        AsyncPipeline.run(() -> actionResult.complete(null), completeResult::complete);
        actionResult.get();
        Optional<Throwable> error = completeResult.get();

        Assertions.assertTrue(error.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test Message", "Other Message"})
    void testRuntimeException(String message) throws Exception {
        CompletableFuture<Optional<Throwable>> completeResult = new CompletableFuture<>();
        AsyncPipeline.run(() -> {
            throw new IllegalArgumentException(message);
        }, completeResult::complete);
        Optional<Throwable> error = completeResult.get();

        Assertions.assertTrue(error.isPresent());
        Assertions.assertSame(error.get().getClass(), IllegalArgumentException.class);
        Assertions.assertEquals(error.get().getMessage(), message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test Message", "Other Message"})
    void testCheckedException(String message) throws Exception {
        CompletableFuture<Optional<Throwable>> completeResult = new CompletableFuture<>();
        AsyncPipeline.run(() -> {
            throw new IOException(message);
        }, completeResult::complete);
        Optional<Throwable> error = completeResult.get();

        Assertions.assertTrue(error.isPresent());
        Assertions.assertSame(error.get().getClass(), IOException.class);
        Assertions.assertEquals(error.get().getMessage(), message);
    }
}

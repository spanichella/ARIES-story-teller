package pipelines;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

public final class AsyncPipeline {
    public static final Logger LOGGER = Logger.getLogger(AsyncPipeline.class.getName());

    public static void run(ExceptionRunnable action, Consumer<? super Optional<Throwable>> onCompleted) {
        Thread thread = new Thread(() -> {
            LOGGER.info("Thread Running");
            try {
                action.run();
                onCompleted.accept(Optional.empty());
            } catch (Exception e) {
                onCompleted.accept(Optional.of(e));
            }
        });
        thread.start();
        thread.setUncaughtExceptionHandler((currentThread, throwable) -> onCompleted.accept(Optional.of(throwable)));
    }

    @FunctionalInterface
    public interface ExceptionRunnable {
        void run() throws Exception;
    }
}

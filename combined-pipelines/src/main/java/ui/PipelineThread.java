package ui;

import java.io.Serial;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import pipelines.DataType;
import pipelines.MainPipeline;
import pipelines.PipelineType;

public class PipelineThread extends Thread {
    private static final Logger logger = Logger.getLogger(PipelineThread.class.getName());
    private final @Nonnull PipelineType pipelineType;
    private final @Nonnull DataType dataType;

    PipelineThread(@Nonnull PipelineType pipelineType, @Nonnull DataType dataType) {
        this.pipelineType = pipelineType;
        this.dataType = dataType;
    }

    @Override
    public void run() {
        logger.info("Thread Running");

        try {
            MainPipeline.runPipeline(pipelineType, dataType);
        } catch (Exception e) {
            throw new ThreadException(e);
        }
    }

    static class ThreadException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 7785242162506057983L;

        private ThreadException(Throwable cause) {
            super(cause);
        }
    }
}

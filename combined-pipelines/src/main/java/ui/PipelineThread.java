package ui;

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
            UIHelpers.showErrorMessage(logger, "Pipeline thread failed", e);
        }
    }
}

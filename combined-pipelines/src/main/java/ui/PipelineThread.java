package ui;

import java.util.logging.Logger;
import pipelines.MainPipeline;

public class PipelineThread extends Thread {
    private static final Logger logger = Logger.getLogger(PipelineThread.class.getName());
    private final String pipeline;
    private final String dataType;

    PipelineThread(String pipeline, String dataType) {
        this.pipeline = pipeline;
        this.dataType = dataType;
    }

    @Override
    public void run() {
        logger.info("Thread Running");

        try {
            MainPipeline.runPipeline(pipeline, dataType);
        } catch (Exception e) {
            UIHelpers.showErrorMessage(logger, "Pipeline thread failed", e);
        }
    }
}

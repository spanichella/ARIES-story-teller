package ui;

import java.util.logging.Logger;
import pipelines.DataType;
import pipelines.MainPipeline;

public class PipelineThread extends Thread {
    private static final Logger logger = Logger.getLogger(PipelineThread.class.getName());
    private final String pipelineType;
    private final DataType dataType;

    PipelineThread(String pipelineType, DataType dataType) {
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

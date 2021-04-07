package ui;

import java.util.logging.Level;
import java.util.logging.Logger;
import pipelines.MainPipeline;

public class PipelineThread extends Thread {
    private static final Logger logger = Logger.getLogger(PipelineThread.class.getName());
    private final String mainPath;
    private final String args2;
    private final String args1;

    PipelineThread(String mainPath, String args2, String args1) {
        this.mainPath = mainPath;
        this.args2 = args2;
        this.args1 = args1;
    }

    @Override
    public void run() {
        System.out.println("Thread Running");

        try {
            MainPipeline.runPipeline(mainPath, args2, args1);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Pipeline thread failed", e);
        }
    }
}

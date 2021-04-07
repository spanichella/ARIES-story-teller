package UI;

import java.util.logging.Level;
import java.util.logging.Logger;
import pipelines.MainPipeline;

public class Pipeline_Thread extends Thread {
    private final static Logger logger = Logger.getLogger(Pipeline_Thread.class.getName());
    private final String mainPath, args2, args1;

    Pipeline_Thread(String mainPath, String args2, String args1){
        this.mainPath = mainPath;
        this.args2 = args2;
        this.args1 = args1;
    }

    @Override
    public void run(){
        System.out.println("Thread Running");

        try {
            MainPipeline.runPipeline(mainPath, args2, args1);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Pipeline thread failed", e);
        }
    }
}

package UI;
import pipelines.MainPipeline;

import javax.swing.*;
import java.awt.*;

public class Pipeline_Thread extends Thread {
    String mainPath, args2, args1;

    Pipeline_Thread(String mainPath, String args2, String args1){
        this.mainPath = mainPath;
        this.args2 = args2;
        this.args1 = args1;
    }

    public void run(){
        System.out.println("Thread Running");

        try {
            MainPipeline.runPipeline(mainPath, args2, args1);
        } catch (Exception e1) {
            System.out.print(e1);
        }
    }
}

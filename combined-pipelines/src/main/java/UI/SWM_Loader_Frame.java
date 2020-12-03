package UI;
import javax.swing.*;
import java.awt.*;

public class SWM_Loader_Frame extends JFrame {



    SWM_Loader_Frame(String path, String content, String pipeline, String threshold){
        ImageIcon loadingAnimation = new ImageIcon(new ImageIcon("Images/swm_loading.gif")
                .getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
        JLabel gifLabel = new JLabel(loadingAnimation);

        ImageIcon icon = new ImageIcon("Images/STIcon.jpg");
        this.setIconImage(icon.getImage());
        this.setTitle("Story Teller Loader");
        this.setSize(200,200);
        this.setLayout(new GridLayout(2,1));
        this.getContentPane().setBackground(new Color(88, 102, 148));
        this.setVisible(true);



        JLabel pathLabel = new JLabel("Path directory: "+path);
        JLabel contentLabel = new JLabel("Content: "+content);
        JLabel pipelineLabel = new JLabel("Pipeline: "+pipeline);
        JLabel thresholdLabel = new JLabel("Threshold: "+threshold);
        JLabel explanationText = new JLabel("Your file is being processed. Please wait...");
        explanationText.setHorizontalAlignment(JLabel.CENTER);
        explanationText.setForeground(new Color(230,230,230));

        //this.add(pathLabel);
        //this.add(contentLabel);
        //this.add(pipelineLabel);
        //this.add(thresholdLabel);
        this.add(explanationText);

        this.add(gifLabel);

    }

    public void closeWindow(){
        dispose();
    }
}

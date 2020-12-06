package UI;
import javax.swing.*;
import java.awt.*;

public class SWM_Loader_Frame extends Thread {

    JFrame frame = new JFrame();

    SWM_Loader_Frame(){

    }

    public void closeWindow(){
        frame.dispose();
    }

    public void run(){
        ImageIcon loadingAnimation = new ImageIcon(new ImageIcon("Images/swm_loading.gif")
                .getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT));
        JLabel gifLabel = new JLabel(loadingAnimation);

        ImageIcon icon = new ImageIcon("Images/STIcon.jpg");
        frame.setIconImage(icon.getImage());
        frame.setTitle("Story Teller Loader");
        frame.setSize(200,200);
        frame.setLayout(new GridLayout(2,1));
        frame.getContentPane().setBackground(new Color(88, 102, 148));
        frame.setVisible(true);


        JLabel explanationText = new JLabel("Your file is being processed. Please wait...");
        explanationText.setHorizontalAlignment(JLabel.CENTER);
        explanationText.setForeground(new Color(230,230,230));

        frame.add(explanationText);
        frame.add(gifLabel);
    }
}

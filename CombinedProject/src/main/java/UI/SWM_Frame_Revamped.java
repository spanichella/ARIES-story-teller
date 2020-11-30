package UI;

import javax.swing.*;
import java.awt.*;

public class SWM_Frame_Revamped extends JFrame {

    SWM_Frame_Revamped(){
        Color backGroundColor = new Color(88, 102, 148);

        ImageIcon logoImage = new ImageIcon("Images/swmlogo2.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);


        ImageIcon icon = new ImageIcon("Images/STIcon.jpg");
        this.setIconImage(icon.getImage());
        this.setTitle("Story Teller");
        this.setSize(300,800);
        this.setLayout(new GridLayout(7,1));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(backGroundColor);

        JPanel step1Panel = new JPanel();
        step1Panel.setBackground(backGroundColor);
        step1Panel.setLayout(new BorderLayout());

        JPanel step2Panel = new JPanel();
        step2Panel.setBackground(backGroundColor);
        step2Panel.setLayout(new BorderLayout());

        JPanel step3Panel = new JPanel();
        step3Panel.setBackground(backGroundColor);
        step3Panel.setLayout(new BorderLayout());

        JPanel step4Panel = new JPanel();
        step4Panel.setBackground(backGroundColor);
        step4Panel.setLayout(new BorderLayout());

        JPanel step5Panel = new JPanel();
        step5Panel.setBackground(backGroundColor);
        step5Panel.setLayout(new BorderLayout());

        JPanel step6Panel = new JPanel();
        step6Panel.setBackground(backGroundColor);
        step6Panel.setLayout(new BorderLayout());

        JPanel step7Panel = new JPanel();
        step7Panel.setBackground(backGroundColor);
        step7Panel.setLayout(new BorderLayout());


        //this.add(new JButton("1"));
        this.add(step1Panel);
        this.add(step2Panel);
        this.add(step3Panel);
        this.add(step4Panel);
        this.add(step5Panel);
        this.add(step6Panel);
        this.add(step7Panel);

        step1Panel.add(logoLabel);


        this.setVisible(true);
    }
}

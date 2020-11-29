package UI;

import javax.swing.*;
import java.awt.*;

public class SWM_GUI_Revamped {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Story Teller");
        frame.setSize(420,420);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        ImageIcon icon = new ImageIcon("Images/STIcon.jpg");
        frame.setIconImage(icon.getImage());

        frame.getContentPane().setBackground(new Color(88,102,148));
    }
}

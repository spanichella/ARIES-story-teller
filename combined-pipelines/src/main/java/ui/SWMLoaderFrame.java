package ui;

import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import ui.utilities.DefaultColors;

final class SWMLoaderFrame extends Thread {
    private final JFrame frame = new JFrame();

    void closeWindow() {
        frame.dispose();
    }

    @Override
    public void run() {
        ImageIcon icon = new ImageIcon("images/STIcon.jpg");
        frame.setIconImage(icon.getImage());
        frame.setTitle("Story Teller Loader");
        frame.setSize(200, 200);
        frame.setLayout(new GridLayout(2, 1));
        frame.getContentPane().setBackground(DefaultColors.BACKGROUND);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        JLabel explanationText = new JLabel("Your file is being processed. Please wait...");
        explanationText.setHorizontalAlignment(SwingConstants.CENTER);
        explanationText.setForeground(DefaultColors.TEXT);

        frame.add(explanationText);

        ImageIcon loadingAnimation = new ImageIcon(new ImageIcon("images/swm_loading.gif")
                .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        frame.add(new JLabel(loadingAnimation));
    }
}

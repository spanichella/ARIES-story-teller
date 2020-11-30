package UI;

import weka.core.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class SWM_Frame_Revamped extends JFrame implements ActionListener {

    private final JButton truthSetSelector;
    JLabel s1_l_step, s1_l_text;
    private final String[] args;

    SWM_Frame_Revamped(){
        args = new String[] {"null","null","null","null","0.5"}; //File,Type,Pipeline,Method,split

        Color backGroundColor = new Color(88, 102, 148);
        Color textColor = new Color(230,230,230);

        ImageIcon logoImage = new ImageIcon("Images/swmlogo3.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        truthSetSelector = new JButton("Select Truth Set");
        truthSetSelector.addActionListener(this);

        s1_l_step = new JLabel("<html><div style='text-align: center;'>[Step 1]</div></html>");
        s1_l_step.setHorizontalAlignment(JLabel.CENTER);
        s1_l_step.setForeground(textColor);

        s1_l_text = new JLabel("<html><div style='text-align: center;'>Select a truth set to" +
                " be analyzed by the algorithm</div></html>");
        s1_l_text.setHorizontalAlignment(JLabel.CENTER);
        s1_l_text.setForeground(textColor);


        ImageIcon icon = new ImageIcon("Images/STIcon.jpg");
        this.setIconImage(icon.getImage());
        this.setTitle("Story Teller");
        this.setSize(300,800);
        this.setLayout(new GridLayout(7,1));
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(backGroundColor);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(backGroundColor);
        logoPanel.setLayout(new BorderLayout());

        JPanel break1Panel = new JPanel();
        break1Panel.setBackground(backGroundColor);
        break1Panel.setLayout(new BorderLayout());

        JPanel blackBorder = new JPanel();
        blackBorder.setBackground(new Color(40,40,40));

        JPanel step1Panel = new JPanel();
        step1Panel.setBackground(backGroundColor);
        step1Panel.setLayout(new GridLayout(3,1));

        JPanel borderCenterPanel = new JPanel();
        borderCenterPanel.setBackground(backGroundColor);
        borderCenterPanel.setLayout(new GridLayout(4,1));

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
        this.add(logoPanel);
        this.add(break1Panel);
        this.add(step1Panel);
        this.add(step4Panel);
        this.add(step5Panel);
        this.add(step6Panel);
        this.add(step7Panel);

        logoPanel.add(logoLabel);
        break1Panel.add(blackBorder, BorderLayout.PAGE_START);
        break1Panel.add(borderCenterPanel, BorderLayout.CENTER);
        borderCenterPanel.add(s1_l_step);
        borderCenterPanel.add(s1_l_text);
        borderCenterPanel.add(truthSetSelector);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == truthSetSelector) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                if(!file.toString().substring(file.toString().lastIndexOf(".")).equals(".txt")){
                    displayErrorMessage("Wrong filetype selected." +
                            " Please select a .txt file");
                    args[0] = "null";
                }
                else{
                    args[0] = file.toString();
                }
            }
        }
        updateStatus();
    }


    public void displayErrorMessage(String errorText){
        JOptionPane.showMessageDialog(this,errorText,
                "Error", JOptionPane.ERROR_MESSAGE);
    }


    private void updateStatus(){
        if(args[0].equals("null")){
            s1_l_step.setText("<html><div style='text-align: center;'>[Step 1]</div></html>");
        }
        else{
            s1_l_step.setText("<html><div style='text-align: center;'>[Step 1] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if(args[1].equals("null")){

        }
        else{

        }

        if(args[2].equals("null") || args[2].equals("Select")){

        }
        else{

        }

        if(args[3].equals("null") || args[3].equals("Select")){

        }
        else{

        }
    }
}

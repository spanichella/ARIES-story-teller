package UI;

import weka.core.Debug;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

public class SWM_Frame_Revamped extends JFrame implements ActionListener, ItemListener {

    private final JButton truthSetSelector;
    JLabel s1_l_step, s1_l_text;
    JLabel s2_l_step, s2_l_text;
    private final String[] args;
    //private final JRadioButton cb1, cb2;
    private final JComboBox c1, c2;
    String[] contentArray = {"Select", "User Review Data", "Requirement Specification Data"};
    String[] pipeLineArray = {"Select", "ML", "DL", "Both"};

    SWM_Frame_Revamped(){
        args = new String[] {"null","null","null","null","0.5"}; //File,Type,Pipeline,Method,split

        Color backGroundColor = new Color(88, 102, 148);
        Color textColor = new Color(230,230,230);
        Color successColor = new Color(86,243,16);
        Color separatorColor = new Color(79,92,134);

        ImageIcon logoImage = new ImageIcon("Images/swmlogo3.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        truthSetSelector = new JButton("Truth Set");
        truthSetSelector.addActionListener(this);

        c1 = new JComboBox(contentArray);
        c1.addItemListener(this);

        c2 = new JComboBox(pipeLineArray);
        c2.addItemListener(this);

        s1_l_step = new JLabel("<html><div style='text-align: center;'>[Step 1]</div></html>");
        s1_l_step.setHorizontalAlignment(JLabel.CENTER);
        s1_l_step.setForeground(textColor);

        s1_l_text = new JLabel("<html><div style='text-align: center;'>Select a truth set to" +
                " be analyzed by the algorithm</div></html>");
        s1_l_text.setHorizontalAlignment(JLabel.CENTER);
        s1_l_text.setForeground(textColor);

        s2_l_step = new JLabel("<html><div style='text-align: center;'>[Step 2]</div></html>");
        s2_l_step.setHorizontalAlignment(JLabel.CENTER);
        s2_l_step.setForeground(textColor);

        s2_l_text = new JLabel("<html><div style='text-align: center;'>Select Content Type</div></html>");
        s2_l_text.setHorizontalAlignment(JLabel.CENTER);
        s2_l_text.setForeground(textColor);

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

        //Step 1 Panels
        JPanel step1Panel = new JPanel();
        step1Panel.setBackground(backGroundColor);
        step1Panel.setLayout(new BorderLayout());

        JPanel s1_borderCenterPanel = new JPanel();
        s1_borderCenterPanel.setBackground(backGroundColor);
        s1_borderCenterPanel.setLayout(new GridLayout(3,1));

        JPanel s1_blackBorder_1 = new JPanel();
        s1_blackBorder_1.setBackground(separatorColor);

        JPanel s1_blackBorder_2 = new JPanel();
        s1_blackBorder_2.setBackground(separatorColor);

        JPanel s1_bottomPanel = new JPanel();
        s1_bottomPanel.setBackground(backGroundColor);
        s1_bottomPanel.setLayout(new GridLayout(3,1));

        JPanel s1_centerPanel = new JPanel();
        s1_centerPanel.setBackground(backGroundColor);
        s1_centerPanel.setLayout(new GridLayout(0,3));

        JPanel s1_empty = new JPanel();
        s1_empty.setBackground(backGroundColor);

        //step 2 panels
        JPanel step2Panel = new JPanel();
        step2Panel.setBackground(backGroundColor);
        step2Panel.setLayout(new BorderLayout());

        JPanel s2_borderCenterPanel = new JPanel();
        s2_borderCenterPanel.setBackground(backGroundColor);
        s2_borderCenterPanel.setLayout(new GridLayout(3,1));

        JPanel s2_blackBorder_1 = new JPanel();
        s2_blackBorder_1.setBackground(separatorColor);

        //step 3 panels
        JPanel step3Panel = new JPanel();
        step3Panel.setBackground(backGroundColor);
        step3Panel.setLayout(new BorderLayout());

        JPanel s3_borderCenterPanel = new JPanel();
        s3_borderCenterPanel.setBackground(backGroundColor);
        s3_borderCenterPanel.setLayout(new GridLayout(3,1));

        JPanel s3_blackBorder_1 = new JPanel();
        s3_blackBorder_1.setBackground(separatorColor);

        //step 4 panel
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
        this.add(step1Panel);
        this.add(step2Panel);
        this.add(step4Panel);
        this.add(step5Panel);
        this.add(step6Panel);
        this.add(step7Panel);

        logoPanel.add(logoLabel);

        //step 1 panels
        step1Panel.add(s1_blackBorder_1, BorderLayout.PAGE_START);
        step1Panel.add(s1_borderCenterPanel, BorderLayout.CENTER);
        step1Panel.add(s1_blackBorder_2, BorderLayout.PAGE_END);
        s1_borderCenterPanel.add(s1_l_step);
        s1_borderCenterPanel.add(s1_l_text);
        s1_borderCenterPanel.add(s1_centerPanel);
        s1_centerPanel.add(s1_empty);
        s1_centerPanel.add(truthSetSelector);

        //step 2 panels
        step2Panel.add(s2_borderCenterPanel, BorderLayout.CENTER);
        step2Panel.add(s2_blackBorder_1, BorderLayout.PAGE_END);
        s2_borderCenterPanel.add(s2_l_step);
        s2_borderCenterPanel.add(s2_l_text);
        s2_borderCenterPanel.add(c1);

        //step 3 panels
        step3Panel.add(s3_borderCenterPanel, BorderLayout.CENTER);
        step3Panel.add(s3_blackBorder_1, BorderLayout.PAGE_END);
        s3_borderCenterPanel.add(s2_l_step);
        s3_borderCenterPanel.add(s2_l_text);
        s3_borderCenterPanel.add(c1);


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
        /*else if (e.getSource() == cb1) {
            args[1] = "UR";
            System.out.println(args[1]);
            cb2.setSelected(false);
        } else if (e.getSource() == cb2) {
            args[1] = "RS";
            cb1.setSelected(false);
        }*/
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
            s2_l_step.setText("<html><div style='text-align: center;'>[Step 2]</div></html>");
        }
        else{
            s2_l_step.setText("<html><div style='text-align: center;'>[Step 2] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
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

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == c1){
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if(c1.getItemAt(0).equals("Select")){
                    c1.removeItemAt(0);
                }
                args[1] = e.getItem().toString();
            }
        }
        else if (e.getSource() == c2){
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if(c1.getItemAt(0).equals("Select")){
                    c1.removeItemAt(0);
                }
                args[1] = e.getItem().toString();
            }
        }
        updateStatus();
    }
}

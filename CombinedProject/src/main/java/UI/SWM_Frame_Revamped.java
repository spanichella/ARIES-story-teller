package UI;

import weka.core.Debug;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Hashtable;

public class SWM_Frame_Revamped extends JFrame implements ActionListener, ItemListener, ChangeListener {

    private final JButton truthSetSelector;
    private final JLabel s1_l_step, s1_l_text;
    JLabel s2_l_step, s2_l_text;
    JLabel s3_l_step, s3_l_text;
    JLabel s4a_l_step, s4a_l_text;
    JLabel s4b_l_step, s4b_l_text, s4b_l_value, s4b_l_left, s4b_l_right;
    private final String[] args;
    //private final JRadioButton cb1, cb2;
    private final JComboBox c1, c2, c3;
    private final JSlider thresholdSlider;
    String[] contentArray = {"Select", "User Review Data", "Requirement Specification Data"};
    String[] pipeLineArray = {"Select", "ML", "DL"};
    String[] methodArray = {
            "Select", "J48", "PART", "NaiveBayes", "IBk", "OneR", "SMO",
            "Logistic", "AdaBoostM1", "LogitBoost",
            "DecisionStump", "LinearRegression",
            "RegressionByDiscretization"
    };
    private final DecimalFormat df = new DecimalFormat("#.##");

    SWM_Frame_Revamped(){
        args = new String[] {"null","null","null","null","0.5"}; //File,Type,Pipeline,Method,split

        Color backGroundColor = new Color(88, 102, 148);
        Color textColor = new Color(230,230,230);
        //Color successColor = new Color(86,243,16);
        Color separatorColor = new Color(79,92,134);

        ImageIcon logoImage = new ImageIcon("Images/swmlogo3.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        truthSetSelector = new JButton("Truth Set");
        truthSetSelector.addActionListener(this);

        thresholdSlider = new JSlider();
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.setBackground(backGroundColor);
        thresholdSlider.addChangeListener(this);

        c1 = new JComboBox(contentArray);
        c1.addItemListener(this);
        ((JLabel)c1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        c2 = new JComboBox(pipeLineArray);
        c2.addItemListener(this);
        ((JLabel)c2.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        c3 = new JComboBox(methodArray);
        c3.addItemListener(this);
        ((JLabel)c3.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

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

        s3_l_step = new JLabel("<html><div style='text-align: center;'>[Step 3]</div></html>");
        s3_l_step.setHorizontalAlignment(JLabel.CENTER);
        s3_l_step.setForeground(textColor);

        s3_l_text = new JLabel("<html><div style='text-align: center;'>Select a Pipeline</div></html>");
        s3_l_text.setHorizontalAlignment(JLabel.CENTER);
        s3_l_text.setForeground(textColor);

        s4a_l_step = new JLabel("<html><div style='text-align: center;'>[Step 4]</div></html>");
        s4a_l_step.setHorizontalAlignment(JLabel.CENTER);
        s4a_l_step.setForeground(textColor);

        s4a_l_text = new JLabel("<html><div style='text-align: center;'>Select Method</div></html>");
        s4a_l_text.setHorizontalAlignment(JLabel.CENTER);
        s4a_l_text.setForeground(textColor);

        s4b_l_text = new JLabel("<html><div style='text-align: center;'>Set Threshold</div></html>");
        s4b_l_text.setHorizontalAlignment(JLabel.CENTER);
        s4b_l_text.setForeground(textColor);

        s4b_l_step = new JLabel("<html><div style='text-align: center;'>[Step 4]</div></html>");
        s4b_l_step.setHorizontalAlignment(JLabel.CENTER);
        s4b_l_step.setForeground(textColor);

        s4b_l_value = new JLabel("<html><div style='text-align: center;'>value: 0.5</div></html>");
        s4b_l_value.setHorizontalAlignment(JLabel.CENTER);
        s4b_l_value.setForeground(textColor);

        s4b_l_left = new JLabel("<html><div style='text-align: center;'>0.1</div></html>");
        s4b_l_left.setHorizontalAlignment(JLabel.LEFT);
        s4b_l_left.setForeground(textColor);

        s4b_l_right = new JLabel("<html><div style='text-align: center;'>1.0</div></html>");
        s4b_l_right.setHorizontalAlignment(JLabel.RIGHT);
        s4b_l_right.setForeground(textColor);

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

        //step 4a panel
        JPanel step4aPanel = new JPanel();
        step4aPanel.setBackground(backGroundColor);
        step4aPanel.setLayout(new BorderLayout());

        JPanel s4a_borderCenterPanel = new JPanel();
        s4a_borderCenterPanel.setBackground(backGroundColor);
        s4a_borderCenterPanel.setLayout(new GridLayout(3,1));

        JPanel s4a_blackBorder_1 = new JPanel();
        s4a_blackBorder_1.setBackground(separatorColor);

        //step 4b Panel
        JPanel step4bPanel = new JPanel();
        step4bPanel.setBackground(backGroundColor);
        step4bPanel.setLayout(new BorderLayout());

        JPanel s4b_borderCenterPanel = new JPanel();
        s4b_borderCenterPanel.setBackground(backGroundColor);
        s4b_borderCenterPanel.setLayout(new GridLayout(4,1));

        JPanel s4b_blackBorder_1 = new JPanel();
        s4b_blackBorder_1.setBackground(separatorColor);

        JPanel s4b_horizontalSplitter = new JPanel();
        s4b_horizontalSplitter.setBackground(backGroundColor);
        s4b_horizontalSplitter.setLayout(new GridLayout(0,3));


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
        this.add(step3Panel);
        this.add(step4bPanel);

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
        s3_borderCenterPanel.add(s3_l_step);
        s3_borderCenterPanel.add(s3_l_text);
        s3_borderCenterPanel.add(c2);

        //step 4a panels
        step4aPanel.add(s4a_borderCenterPanel, BorderLayout.CENTER);
        step4aPanel.add(s4a_blackBorder_1, BorderLayout.PAGE_END);
        s4a_borderCenterPanel.add(s4a_l_step);
        s4a_borderCenterPanel.add(s4a_l_text);
        s4a_borderCenterPanel.add(c3);

        //step 4b panels
        step4bPanel.add(s4b_borderCenterPanel, BorderLayout.CENTER);
        step4bPanel.add(s4b_blackBorder_1, BorderLayout.PAGE_END);
        s4b_borderCenterPanel.add(s4b_l_step);
        s4b_borderCenterPanel.add(s4b_l_text);
        s4b_borderCenterPanel.add(s4b_horizontalSplitter);
        s4b_horizontalSplitter.add(s4b_l_left);
        s4b_horizontalSplitter.add(s4b_l_value);
        s4b_horizontalSplitter.add(s4b_l_right);
        //s4b_borderCenterPanel.add(s4b_l_value);
        //s4b_borderCenterPanel.add(s4b_horizontalSplitter);
        s4b_borderCenterPanel.add(thresholdSlider);



        this.setVisible(true);
    }

    public void displayErrorMessage(String errorText){
        JOptionPane.showMessageDialog(this,errorText,
                "Error", JOptionPane.ERROR_MESSAGE);
    }


    public void populatePanels(){

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
            s3_l_step.setText("<html><div style='text-align: center;'>[Step 3]</div></html>");
        }
        else{
            s3_l_step.setText("<html><div style='text-align: center;'>[Step 3] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if(args[3].equals("null") || args[3].equals("Select")){

        }
        else{

        }
    }

    public void redrawFrame(){
        //panel.revalidate();
        //panel.repaint();
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


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == c1){
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String check = "null";
                if (e.getItem() == "User Review Data") {
                    for(int i=0;i<c2.getItemCount();i++){
                        if(c2.getItemAt(i).equals("DL")){
                            check = "found";
                        }
                    }
                    if(check.equals("null")){
                        c2.insertItemAt("DL",c2.getItemCount());
                    }
                } else if (e.getItem() == "Requirement Specification Data") {
                    c2.setSelectedItem("ML");
                    c2.removeItem("DL");
                }
                if(c1.getItemAt(0).equals("Select")){
                    c1.removeItemAt(0);
                }
                args[1] = e.getItem().toString();
            }
        }
        else if (e.getSource() == c2){
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if(c2.getItemAt(0).equals("Select")){
                    c2.removeItemAt(0);
                }
                if(e.getItem().equals("DL")){
                    //this.add(step4aPanel);
                }
                args[2] = e.getItem().toString();
            }
        }
        else if (e.getSource() == c3){
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if(c3.getItemAt(0).equals("Select")){
                    c3.removeItemAt(0);
                }
                args[3] = e.getItem().toString();
            }
        }
        updateStatus();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        double value = ((JSlider) e.getSource()).getValue() * 0.01;
        if(value<0.1){
            value = 0.1;
        }
        String decimalFixedDouble = df.format(value);
        s4b_l_value.setText("value: " + decimalFixedDouble);
        args[4] = decimalFixedDouble;
        redrawFrame();
    }
}

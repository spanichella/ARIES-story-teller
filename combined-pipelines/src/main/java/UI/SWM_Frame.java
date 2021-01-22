package UI;

import fileGeneration.XMLInitializer;
import pipelines.MainPipeline;

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


public class SWM_Frame extends JFrame implements ActionListener, ItemListener, ChangeListener {
    private static final long serialVersionUID = -592869500939986619L;

    private static final Color backGroundColor = new Color(88, 102, 148);
    private static final Color textColor = new Color(230, 230, 230);
    private static final Color separatorColor = new Color(79, 92, 134);

    private SWM_Loader_Frame loader;

    private final JButton truthSetSelector, execute_b;
    private final JLabel s1_l_step;
    private final JLabel s2_l_step;
    private final JLabel s3_l_step;
    private final JLabel s4a_l_step, s4a_l_text;
    private final JLabel s4b_l_step, s4b_l_text, s4b_l_value, s4b_l_left, s4b_l_right;
    private final JLabel s5_l_step, s5_l_text;
    private final String[] args;
    private final JComboBox<String> c1, c2, c3, c4;
    private final JSlider thresholdSlider;
    private static final String[] dataTypeArray = {"Select", "User-Reviews", "Requirement-Specifications"};
    private static final String[] pipeLineArray = {"Select", "ML", "DL"};
    private static final String[] strategyArray = {"Select", "10-Fold", "Percentage-Split"};
    private static final String[] mlModelArray = {
            "Select", "J48", "PART", "NaiveBayes", "IBk", "OneR", "SMO",
            "Logistic", "AdaBoostM1", "LogitBoost",
            "DecisionStump", "LinearRegression",
            "RegressionByDiscretization"
    };
    private final DecimalFormat df = new DecimalFormat("#.##");


    SWM_Frame() {
        args = new String[]{"null", "null", "null", "null", "0.5", "null"}; //truthFilePath,DataType,Pipeline,MLModel,split,Strategy

        ImageIcon logoImage = new ImageIcon("images/swmlogo.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        truthSetSelector = new JButton("Truth Set");
        truthSetSelector.addActionListener(this);


        execute_b = new JButton("Run");
        execute_b.addActionListener(this);
        execute_b.setEnabled(false);

        thresholdSlider = new JSlider();
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.setBackground(backGroundColor);
        thresholdSlider.addChangeListener(this);
        thresholdSlider.setVisible(false);

        c1 = new JComboBox<>(dataTypeArray);
        c1.addItemListener(this);
        ((JLabel) c1.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        c2 = new JComboBox<>(pipeLineArray);
        c2.addItemListener(this);
        ((JLabel) c2.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        c3 = new JComboBox<>(mlModelArray);
        c3.addItemListener(this);
        ((JLabel) c3.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        c3.setVisible(false);

        c4 = new JComboBox<>(strategyArray);
        c4.addItemListener(this);
        ((JLabel) c4.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        c4.setVisible(false);

        s1_l_step = new JLabel("<html><div style='text-align: center;'>[Step 1]</div></html>");
        s1_l_step.setHorizontalAlignment(JLabel.CENTER);
        s1_l_step.setForeground(textColor);

        JLabel s1_l_text = new JLabel("<html><div style='text-align: center;'>Select a truth set to" +
                " be analyzed by the algorithm</div></html>");
        s1_l_text.setHorizontalAlignment(JLabel.CENTER);
        s1_l_text.setForeground(textColor);

        s2_l_step = new JLabel("<html><div style='text-align: center;'>[Step 2]</div></html>");
        s2_l_step.setHorizontalAlignment(JLabel.CENTER);
        s2_l_step.setForeground(textColor);

        JLabel s2_l_text = new JLabel("<html><div style='text-align: center;'>Select Content Type</div></html>");
        s2_l_text.setHorizontalAlignment(JLabel.CENTER);
        s2_l_text.setForeground(textColor);

        s3_l_step = new JLabel("<html><div style='text-align: center;'>[Step 3]</div></html>");
        s3_l_step.setHorizontalAlignment(JLabel.CENTER);
        s3_l_step.setForeground(textColor);

        JLabel s3_l_text = new JLabel("<html><div style='text-align: center;'>Select a Pipeline</div></html>");
        s3_l_text.setHorizontalAlignment(JLabel.CENTER);
        s3_l_text.setForeground(textColor);

        s4a_l_step = new JLabel("<html><div style='text-align: center;'>[Step 4]</div></html>");
        s4a_l_step.setHorizontalAlignment(JLabel.CENTER);
        s4a_l_step.setForeground(textColor);
        s4a_l_step.setVisible(false);

        s4a_l_text = new JLabel("<html><div style='text-align: center;'>Select Method</div></html>");
        s4a_l_text.setHorizontalAlignment(JLabel.CENTER);
        s4a_l_text.setForeground(textColor);
        s4a_l_text.setVisible(false);

        s4b_l_text = new JLabel("<html><div style='text-align: center;'>Set Size of Training-Set</div></html>");
        s4b_l_text.setHorizontalAlignment(JLabel.CENTER);
        s4b_l_text.setForeground(textColor);
        s4b_l_text.setVisible(false);

        s4b_l_step = new JLabel("<html><div style='text-align: center;'>[Step 5]</div></html>");
        s4b_l_step.setHorizontalAlignment(JLabel.CENTER);
        s4b_l_step.setForeground(textColor);
        s4b_l_step.setVisible(false);

        s4b_l_value = new JLabel("<html><div style='text-align: center;'>value: 0.5</div></html>");
        s4b_l_value.setHorizontalAlignment(JLabel.CENTER);
        s4b_l_value.setForeground(textColor);
        s4b_l_value.setVisible(false);

        s4b_l_left = new JLabel("<html><div style='text-align: center;'>0.1</div></html>");
        s4b_l_left.setHorizontalAlignment(JLabel.LEFT);
        s4b_l_left.setForeground(textColor);
        s4b_l_left.setVisible(false);

        s4b_l_right = new JLabel("<html><div style='text-align: center;'>1.0</div></html>");
        s4b_l_right.setHorizontalAlignment(JLabel.RIGHT);
        s4b_l_right.setForeground(textColor);
        s4b_l_right.setVisible(false);

        s5_l_step = new JLabel("<html><div style='text-align: center;'>[Step 5]</div></html>");
        s5_l_step.setHorizontalAlignment(JLabel.CENTER);
        s5_l_step.setForeground(textColor);
        s5_l_step.setVisible(false);

        s5_l_text = new JLabel("<html><div style='text-align: center;'>Select Strategy</div></html>");
        s5_l_text.setHorizontalAlignment(JLabel.CENTER);
        s5_l_text.setForeground(textColor);
        s5_l_text.setVisible(false);

        ImageIcon icon = new ImageIcon("images/STIcon.jpg");
        this.setIconImage(icon.getImage());
        this.setTitle("Story Teller");
        this.setSize(350, 800);
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 1));
        this.add(mainPanel, BorderLayout.CENTER);

        JPanel topBorder = new JPanel();
        topBorder.setBackground(separatorColor);
        this.add(topBorder, BorderLayout.PAGE_START);

        JPanel bottomBorder = new JPanel();
        bottomBorder.setBackground(separatorColor);
        this.add(bottomBorder, BorderLayout.PAGE_END);

        JPanel leftBorder = new JPanel();
        leftBorder.setBackground(separatorColor);
        this.add(leftBorder, BorderLayout.WEST);

        JPanel rightBorder = new JPanel();
        rightBorder.setBackground(separatorColor);
        this.add(rightBorder, BorderLayout.EAST);

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
        s1_borderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s1_blackBorder_1 = new JPanel();
        s1_blackBorder_1.setBackground(separatorColor);

        JPanel s1_blackBorder_2 = new JPanel();
        s1_blackBorder_2.setBackground(separatorColor);

        JPanel s1_bottomPanel = new JPanel();
        s1_bottomPanel.setBackground(backGroundColor);
        s1_bottomPanel.setLayout(new GridLayout(3, 1));

        JPanel s1_centerPanel = new JPanel();
        s1_centerPanel.setBackground(backGroundColor);
        s1_centerPanel.setLayout(new GridLayout(0, 3));

        JPanel s1_empty = new JPanel();
        s1_empty.setBackground(backGroundColor);

        //step 2 panels
        JPanel step2Panel = new JPanel();
        step2Panel.setBackground(backGroundColor);
        step2Panel.setLayout(new BorderLayout());

        JPanel s2_borderCenterPanel = new JPanel();
        s2_borderCenterPanel.setBackground(backGroundColor);
        s2_borderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s2_blackBorder_1 = new JPanel();
        s2_blackBorder_1.setBackground(separatorColor);

        //step 3 panels
        JPanel step3Panel = new JPanel();
        step3Panel.setBackground(backGroundColor);
        step3Panel.setLayout(new BorderLayout());

        JPanel s3_borderCenterPanel = new JPanel();
        s3_borderCenterPanel.setBackground(backGroundColor);
        s3_borderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s3_blackBorder_1 = new JPanel();
        s3_blackBorder_1.setBackground(separatorColor);

        //step 4a panels
        JPanel step4aPanel = new JPanel();
        step4aPanel.setBackground(backGroundColor);
        step4aPanel.setLayout(new BorderLayout());

        JPanel s4a_borderCenterPanel = new JPanel();
        s4a_borderCenterPanel.setBackground(backGroundColor);
        s4a_borderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s4a_blackBorder_1 = new JPanel();
        s4a_blackBorder_1.setBackground(separatorColor);

        //step 4b Panels
        JPanel step4bPanel = new JPanel();
        step4bPanel.setBackground(backGroundColor);
        step4bPanel.setLayout(new BorderLayout());

        JPanel s4b_borderCenterPanel = new JPanel();
        s4b_borderCenterPanel.setBackground(backGroundColor);
        s4b_borderCenterPanel.setLayout(new GridLayout(4, 1));

        JPanel s4b_blackBorder_1 = new JPanel();
        s4b_blackBorder_1.setBackground(separatorColor);

        JPanel s4b_horizontalSplitter = new JPanel();
        s4b_horizontalSplitter.setBackground(backGroundColor);
        s4b_horizontalSplitter.setLayout(new GridLayout(0, 3));

        //step 5 Panels
        JPanel step5Panel = new JPanel();
        step5Panel.setBackground(backGroundColor);
        step5Panel.setLayout(new BorderLayout());

        JPanel s5_borderCenterPanel = new JPanel();
        s5_borderCenterPanel.setBackground(backGroundColor);
        s5_borderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s5_blackBorder_1 = new JPanel();
        s5_blackBorder_1.setBackground(separatorColor);

        //step 6 Panels
        JPanel step6Panel = new JPanel();
        step6Panel.setBackground(backGroundColor);
        step6Panel.setLayout(new BorderLayout());

        JPanel step6MainGrid = new JPanel();
        step6MainGrid.setLayout(new GridLayout(3, 1));

        JPanel s6_empty1 = new JPanel();
        s6_empty1.setBackground(backGroundColor);

        JPanel s6_empty2 = new JPanel();
        s6_empty2.setBackground(backGroundColor);

        step6MainGrid.add(s6_empty1);
        step6MainGrid.add(execute_b);
        step6MainGrid.add(s6_empty2);
        step6Panel.add(step6MainGrid, BorderLayout.CENTER);

        mainPanel.add(logoPanel);
        mainPanel.add(step1Panel);
        mainPanel.add(step2Panel);
        mainPanel.add(step3Panel);
        mainPanel.add(step4aPanel);
        mainPanel.add(step5Panel);
        mainPanel.add(step4bPanel);
        mainPanel.add(step6Panel);

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
        s4b_borderCenterPanel.add(thresholdSlider);

        //step 5 panels
        step5Panel.add(s5_borderCenterPanel, BorderLayout.CENTER);
        step5Panel.add(s5_blackBorder_1, BorderLayout.PAGE_END);
        s5_borderCenterPanel.add(s5_l_step);
        s5_borderCenterPanel.add(s5_l_text);
        s5_borderCenterPanel.add(c4);

        this.setVisible(true);
    }


    public void displayErrorMessage(String errorText) {
        JOptionPane.showMessageDialog(this, errorText,
                "Error", JOptionPane.ERROR_MESSAGE);
    }


    public void populatePanels(String input) {
        switch (input) {
            case "DL":
                s4a_l_text.setVisible(false);
                s4a_l_step.setVisible(false);
                c3.setVisible(false);
                s4b_l_step.setText("<html><div style='text-align: center;'>[Step 4]</div></html>");
                s4b_l_text.setVisible(false);
                s4b_l_step.setVisible(false);
                s4b_l_value.setVisible(false);
                s4b_l_left.setVisible(false);
                s4b_l_right.setVisible(false);
                thresholdSlider.setVisible(false);
                s5_l_text.setVisible(false);
                s5_l_step.setVisible(false);
                c4.setVisible(false);
                break;
            case "ML":
                s4a_l_text.setVisible(true);
                s4a_l_step.setVisible(true);
                c3.setVisible(true);
                s5_l_text.setVisible(true);
                s5_l_step.setVisible(true);
                c4.setVisible(true);
                s4b_l_text.setVisible(false);
                s4b_l_step.setVisible(false);
                s4b_l_value.setVisible(false);
                s4b_l_left.setVisible(false);
                s4b_l_right.setVisible(false);
                thresholdSlider.setVisible(false);
                break;
            case "Threshold":
                s4b_l_step.setText("<html><div style='text-align: center;'>[Step 6]</div></html>");
                s4b_l_text.setVisible(true);
                s4b_l_step.setVisible(true);
                s4b_l_value.setVisible(true);
                s4b_l_left.setVisible(true);
                s4b_l_right.setVisible(true);
                thresholdSlider.setVisible(true);
                break;
            case "NoThreshold":
                s4b_l_text.setVisible(false);
                s4b_l_step.setVisible(false);
                s4b_l_value.setVisible(false);
                s4b_l_left.setVisible(false);
                s4b_l_right.setVisible(false);
                thresholdSlider.setVisible(false);
                break;
        }
    }


    public void checkIfRunnable() {
        boolean runnable = true;

        if (c2.getSelectedItem() == "DL") {
            if (args[0].equals("null") || args[1].equals("null") || args[2].equals("null")) {
                runnable = false;
            }
        } else {
            for (String arg : args) {
                if (arg.equals("null")) {
                    runnable = false;
                    break;
                }
            }
        }
        execute_b.setEnabled(runnable);
    }


    public void closeWindow() {
        loader.closeWindow();
        dispose();
    }


    private void updateStatus() {
        if (args[0].equals("null")) {
            s1_l_step.setText("<html><div style='text-align: center;'>[Step 1]</div></html>");
        } else {
            s1_l_step.setText("<html><div style='text-align: center;'>[Step 1] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if (args[1].equals("null")) {
            s2_l_step.setText("<html><div style='text-align: center;'>[Step 2]</div></html>");
        } else {
            s2_l_step.setText("<html><div style='text-align: center;'>[Step 2] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if (args[2].equals("null") || args[2].equals("Select")) {
            s3_l_step.setText("<html><div style='text-align: center;'>[Step 3]</div></html>");
        } else {
            s3_l_step.setText("<html><div style='text-align: center;'>[Step 3] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if (args[3].equals("null") || args[3].equals("Select")) {
            s4a_l_step.setText("<html><div style='text-align: center;'>[Step 4]</div></html>");
        } else {
            s4a_l_step.setText("<html><div style='text-align: center;'>[Step 4] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if (args[5].equals("null") || args[3].equals("Select")) {
            s5_l_step.setText("<html><div style='text-align: center;'>[Step 5]</div></html>");
        } else {
            s5_l_step.setText("<html><div style='text-align: center;'>[Step 5] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == truthSetSelector) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String extension = file.toString().substring(file.toString().lastIndexOf("."));
                if (!extension.equals(".txt") && !extension.equals(".csv")) {
                    displayErrorMessage("Wrong filetype selected." +
                            " Please select a .txt or .csv file");
                    args[0] = "null";
                } else {
                    args[0] = file.toString();
                }
            }
        } else if (e.getSource() == execute_b) {
            //loader = new SWM_Loader_Frame();
            loader = new SWM_Loader_Frame();
            loader.start();
            //set mainPath according to Operating System
            String mainPath = MainPipeline.class.getProtectionDomain().getCodeSource().getLocation().getPath().
                    replace("target/classes/", "");
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                mainPath = mainPath.substring(1);
            }

            generateXML();
            Pipeline_Thread mainThread = new Pipeline_Thread(mainPath,args[2],args[1]);
            mainThread.start();
            this.setEnabled(false);
        }
        updateStatus();
        checkIfRunnable();
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == c1) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String check = "null";
                if (e.getItem() == "Requirement-Specifications") {
                    for (int i = 0; i < c2.getItemCount(); i++) {
                        if (c2.getItemAt(i).equals("DL")) {
                            check = "found";
                        }
                    }
                    if (check.equals("null")) {
                        c2.insertItemAt("DL", c2.getItemCount());
                    }
                } else if (e.getItem() == "User-Reviews") {
                    c2.setSelectedItem("ML");
                    c2.removeItem("DL");
                }
                if (c1.getItemAt(0).equals("Select")) {
                    c1.removeItemAt(0);
                }
                args[1] = e.getItem().toString();
            }
        } else if (e.getSource() == c2) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (c2.getItemAt(0).equals("Select")) {
                    c2.removeItemAt(0);
                }
                if (e.getItem().equals("DL")) {
                    populatePanels("DL");
                } else if (e.getItem().equals("ML")) {
                    populatePanels("ML");
                }
                args[2] = e.getItem().toString();
            }
        } else if (e.getSource() == c3) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (c3.getItemAt(0).equals("Select")) {
                    c3.removeItemAt(0);
                }
                args[3] = e.getItem().toString();
            }
        } else if (e.getSource() == c4) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (c4.getItemAt(0).equals("Select")) {
                    c4.removeItemAt(0);
                }
                if (e.getItem().equals("Percentage-Split")) {
                    populatePanels("Threshold");
                } else if (e.getItem().equals("10-Fold")) {
                    populatePanels("NoThreshold");
                }
                args[5] = e.getItem().toString();
            }
        }
        updateStatus();
        checkIfRunnable();
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        double value = ((JSlider) e.getSource()).getValue() * 0.01;
        if (value < 0.1) {
            value = 0.1;
        }
        String decimalFixedDouble = df.format(value);
        s4b_l_value.setText("value: " + decimalFixedDouble);
        args[4] = decimalFixedDouble;
    }

    void generateXML() {

        //create XML files with paths, etc.
        //set mainPath according to Operating System
        String mainPath = MainPipeline.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/classes/", "");
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            mainPath = mainPath.substring(1);
        }
        System.out.print(mainPath);
        XMLInitializer.createXML(mainPath, args[0], args[1], args[3], args[4], args[5]);
    }
}

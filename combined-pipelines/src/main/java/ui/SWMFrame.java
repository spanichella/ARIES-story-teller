package ui;

import filegeneration.XMLInitializer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.Serial;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import pipelines.MainPipeline;


public class SWMFrame extends JFrame implements ActionListener, ItemListener, ChangeListener {
    @Serial
    private static final long serialVersionUID = -592869500939986619L;
    private static final Logger logger = Logger.getLogger(SWMFrame.class.getName());

    private static final Color backGroundColor = new Color(88, 102, 148);
    private static final Color textColor = new Color(230, 230, 230);
    private static final Color separatorColor = new Color(79, 92, 134);

    private SWMLoaderFrame loader;

    private final JButton truthSetSelector;
    private final JButton executeB;
    private final JLabel s1LStep;
    private final JLabel s2LStep;
    private final JLabel s3LStep;
    private final JLabel s4ALStep;
    private final JLabel s4ALText;
    private final JLabel s4BLStep;
    private final JLabel s4BLText;
    private final JLabel s4BLValue;
    private final JLabel s4BLLeft;
    private final JLabel s4BLRight;
    private final JLabel s5LStep;
    private final JLabel s5LText;
    private final String[] args;
    private final JComboBox<String> c1;
    private final JComboBox<String> c2;
    private final JComboBox<String> c3;
    private final JComboBox<String> c4;
    private final JSlider thresholdSlider;
    private static final String[] dataTypeArray = {"Select", "User-Reviews", "Requirement-Specifications"};
    private static final String[] pipeLineArray = {"Select", "ML", "DL"};
    private static final String[] strategyArray = {"Select", "10-Fold", "Percentage-Split"};
    private static final String[] mlModelArray = {
        "Select", "J48", "PART", "NaiveBayes", "IBk", "OneR", "SMO",
        "Logistic", "AdaBoostM1", "LogitBoost",
        "DecisionStump", "LinearRegression",
        "RegressionByDiscretization",
    };
    private final DecimalFormat df = new DecimalFormat("#.##");


    SWMFrame() {
        args = new String[]{"null", "null", "null", "null", "0.5", "null"}; //truthFilePath,DataType,Pipeline,MLModel,split,Strategy

        ImageIcon logoImage = new ImageIcon("images/swmlogo.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        truthSetSelector = new JButton("Truth Set");
        truthSetSelector.addActionListener(this);


        executeB = new JButton("Run");
        executeB.addActionListener(this);
        executeB.setEnabled(false);

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

        s1LStep = new JLabel("<html><div style='text-align: center;'>[Step 1]</div></html>");
        s1LStep.setHorizontalAlignment(JLabel.CENTER);
        s1LStep.setForeground(textColor);

        JLabel s1LText = new JLabel("<html><div style='text-align: center;'>Select a truth set to" +
                " be analyzed by the algorithm</div></html>");
        s1LText.setHorizontalAlignment(JLabel.CENTER);
        s1LText.setForeground(textColor);

        s2LStep = new JLabel("<html><div style='text-align: center;'>[Step 2]</div></html>");
        s2LStep.setHorizontalAlignment(JLabel.CENTER);
        s2LStep.setForeground(textColor);

        JLabel s2LText = new JLabel("<html><div style='text-align: center;'>Select Content Type</div></html>");
        s2LText.setHorizontalAlignment(JLabel.CENTER);
        s2LText.setForeground(textColor);

        s3LStep = new JLabel("<html><div style='text-align: center;'>[Step 3]</div></html>");
        s3LStep.setHorizontalAlignment(JLabel.CENTER);
        s3LStep.setForeground(textColor);

        JLabel s3LText = new JLabel("<html><div style='text-align: center;'>Select a Pipeline</div></html>");
        s3LText.setHorizontalAlignment(JLabel.CENTER);
        s3LText.setForeground(textColor);

        s4ALStep = new JLabel("<html><div style='text-align: center;'>[Step 4]</div></html>");
        s4ALStep.setHorizontalAlignment(JLabel.CENTER);
        s4ALStep.setForeground(textColor);
        s4ALStep.setVisible(false);

        s4ALText = new JLabel("<html><div style='text-align: center;'>Select Method</div></html>");
        s4ALText.setHorizontalAlignment(JLabel.CENTER);
        s4ALText.setForeground(textColor);
        s4ALText.setVisible(false);

        s4BLText = new JLabel("<html><div style='text-align: center;'>Set Size of Training-Set</div></html>");
        s4BLText.setHorizontalAlignment(JLabel.CENTER);
        s4BLText.setForeground(textColor);
        s4BLText.setVisible(false);

        s4BLStep = new JLabel("<html><div style='text-align: center;'>[Step 5]</div></html>");
        s4BLStep.setHorizontalAlignment(JLabel.CENTER);
        s4BLStep.setForeground(textColor);
        s4BLStep.setVisible(false);

        s4BLValue = new JLabel("<html><div style='text-align: center;'>value: 0.5</div></html>");
        s4BLValue.setHorizontalAlignment(JLabel.CENTER);
        s4BLValue.setForeground(textColor);
        s4BLValue.setVisible(false);

        s4BLLeft = new JLabel("<html><div style='text-align: center;'>0.1</div></html>");
        s4BLLeft.setHorizontalAlignment(JLabel.LEFT);
        s4BLLeft.setForeground(textColor);
        s4BLLeft.setVisible(false);

        s4BLRight = new JLabel("<html><div style='text-align: center;'>1.0</div></html>");
        s4BLRight.setHorizontalAlignment(JLabel.RIGHT);
        s4BLRight.setForeground(textColor);
        s4BLRight.setVisible(false);

        s5LStep = new JLabel("<html><div style='text-align: center;'>[Step 5]</div></html>");
        s5LStep.setHorizontalAlignment(JLabel.CENTER);
        s5LStep.setForeground(textColor);
        s5LStep.setVisible(false);

        s5LText = new JLabel("<html><div style='text-align: center;'>Select Strategy</div></html>");
        s5LText.setHorizontalAlignment(JLabel.CENTER);
        s5LText.setForeground(textColor);
        s5LText.setVisible(false);

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

        JPanel s1BorderCenterPanel = new JPanel();
        s1BorderCenterPanel.setBackground(backGroundColor);
        s1BorderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s1BlackBorder1 = new JPanel();
        s1BlackBorder1.setBackground(separatorColor);

        JPanel s1BlackBorder2 = new JPanel();
        s1BlackBorder2.setBackground(separatorColor);

        JPanel s1BottomPanel = new JPanel();
        s1BottomPanel.setBackground(backGroundColor);
        s1BottomPanel.setLayout(new GridLayout(3, 1));

        JPanel s1CenterPanel = new JPanel();
        s1CenterPanel.setBackground(backGroundColor);
        s1CenterPanel.setLayout(new GridLayout(0, 3));

        JPanel s1Empty = new JPanel();
        s1Empty.setBackground(backGroundColor);

        //step 2 panels
        JPanel step2Panel = new JPanel();
        step2Panel.setBackground(backGroundColor);
        step2Panel.setLayout(new BorderLayout());

        JPanel s2BorderCenterPanel = new JPanel();
        s2BorderCenterPanel.setBackground(backGroundColor);
        s2BorderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s2BlackBorder1 = new JPanel();
        s2BlackBorder1.setBackground(separatorColor);

        //step 3 panels
        JPanel step3Panel = new JPanel();
        step3Panel.setBackground(backGroundColor);
        step3Panel.setLayout(new BorderLayout());

        JPanel s3BorderCenterPanel = new JPanel();
        s3BorderCenterPanel.setBackground(backGroundColor);
        s3BorderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s3BlackBorder1 = new JPanel();
        s3BlackBorder1.setBackground(separatorColor);

        //step 4a panels
        JPanel step4aPanel = new JPanel();
        step4aPanel.setBackground(backGroundColor);
        step4aPanel.setLayout(new BorderLayout());

        JPanel s4ABorderCenterPanel = new JPanel();
        s4ABorderCenterPanel.setBackground(backGroundColor);
        s4ABorderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s4ABlackBorder1 = new JPanel();
        s4ABlackBorder1.setBackground(separatorColor);

        //step 4b Panels
        JPanel step4bPanel = new JPanel();
        step4bPanel.setBackground(backGroundColor);
        step4bPanel.setLayout(new BorderLayout());

        JPanel s4BBorderCenterPanel = new JPanel();
        s4BBorderCenterPanel.setBackground(backGroundColor);
        s4BBorderCenterPanel.setLayout(new GridLayout(4, 1));

        JPanel s4BBlackBorder1 = new JPanel();
        s4BBlackBorder1.setBackground(separatorColor);

        JPanel s4BHorizontalSplitter = new JPanel();
        s4BHorizontalSplitter.setBackground(backGroundColor);
        s4BHorizontalSplitter.setLayout(new GridLayout(0, 3));

        //step 5 Panels
        JPanel step5Panel = new JPanel();
        step5Panel.setBackground(backGroundColor);
        step5Panel.setLayout(new BorderLayout());

        JPanel s5BorderCenterPanel = new JPanel();
        s5BorderCenterPanel.setBackground(backGroundColor);
        s5BorderCenterPanel.setLayout(new GridLayout(3, 1));

        JPanel s5BlackBorder1 = new JPanel();
        s5BlackBorder1.setBackground(separatorColor);

        //step 6 Panels
        JPanel step6Panel = new JPanel();
        step6Panel.setBackground(backGroundColor);
        step6Panel.setLayout(new BorderLayout());

        JPanel step6MainGrid = new JPanel();
        step6MainGrid.setLayout(new GridLayout(3, 1));

        JPanel s6Empty1 = new JPanel();
        s6Empty1.setBackground(backGroundColor);

        JPanel s6Empty2 = new JPanel();
        s6Empty2.setBackground(backGroundColor);

        step6MainGrid.add(s6Empty1);
        step6MainGrid.add(executeB);
        step6MainGrid.add(s6Empty2);
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
        step1Panel.add(s1BlackBorder1, BorderLayout.PAGE_START);
        step1Panel.add(s1BorderCenterPanel, BorderLayout.CENTER);
        step1Panel.add(s1BlackBorder2, BorderLayout.PAGE_END);
        s1BorderCenterPanel.add(s1LStep);
        s1BorderCenterPanel.add(s1LText);
        s1BorderCenterPanel.add(s1CenterPanel);
        s1CenterPanel.add(s1Empty);
        s1CenterPanel.add(truthSetSelector);

        //step 2 panels
        step2Panel.add(s2BorderCenterPanel, BorderLayout.CENTER);
        step2Panel.add(s2BlackBorder1, BorderLayout.PAGE_END);
        s2BorderCenterPanel.add(s2LStep);
        s2BorderCenterPanel.add(s2LText);
        s2BorderCenterPanel.add(c1);

        //step 3 panels
        step3Panel.add(s3BorderCenterPanel, BorderLayout.CENTER);
        step3Panel.add(s3BlackBorder1, BorderLayout.PAGE_END);
        s3BorderCenterPanel.add(s3LStep);
        s3BorderCenterPanel.add(s3LText);
        s3BorderCenterPanel.add(c2);

        //step 4a panels
        step4aPanel.add(s4ABorderCenterPanel, BorderLayout.CENTER);
        step4aPanel.add(s4ABlackBorder1, BorderLayout.PAGE_END);
        s4ABorderCenterPanel.add(s4ALStep);
        s4ABorderCenterPanel.add(s4ALText);
        s4ABorderCenterPanel.add(c3);

        //step 4b panels
        step4bPanel.add(s4BBorderCenterPanel, BorderLayout.CENTER);
        step4bPanel.add(s4BBlackBorder1, BorderLayout.PAGE_END);
        s4BBorderCenterPanel.add(s4BLStep);
        s4BBorderCenterPanel.add(s4BLText);
        s4BBorderCenterPanel.add(s4BHorizontalSplitter);
        s4BHorizontalSplitter.add(s4BLLeft);
        s4BHorizontalSplitter.add(s4BLValue);
        s4BHorizontalSplitter.add(s4BLRight);
        s4BBorderCenterPanel.add(thresholdSlider);

        //step 5 panels
        step5Panel.add(s5BorderCenterPanel, BorderLayout.CENTER);
        step5Panel.add(s5BlackBorder1, BorderLayout.PAGE_END);
        s5BorderCenterPanel.add(s5LStep);
        s5BorderCenterPanel.add(s5LText);
        s5BorderCenterPanel.add(c4);

        this.setVisible(true);
    }


    public void displayErrorMessage(String errorText) {
        JOptionPane.showMessageDialog(this, errorText,
                "Error", JOptionPane.ERROR_MESSAGE);
    }


    public void populatePanels(String input) {
        switch (input) {
            case "DL":
                s4ALText.setVisible(false);
                s4ALStep.setVisible(false);
                c3.setVisible(false);
                s4BLStep.setText("<html><div style='text-align: center;'>[Step 4]</div></html>");
                s4BLText.setVisible(false);
                s4BLStep.setVisible(false);
                s4BLValue.setVisible(false);
                s4BLLeft.setVisible(false);
                s4BLRight.setVisible(false);
                thresholdSlider.setVisible(false);
                s5LText.setVisible(false);
                s5LStep.setVisible(false);
                c4.setVisible(false);
                break;
            case "ML":
                s4ALText.setVisible(true);
                s4ALStep.setVisible(true);
                c3.setVisible(true);
                s5LText.setVisible(true);
                s5LStep.setVisible(true);
                c4.setVisible(true);
                s4BLText.setVisible(false);
                s4BLStep.setVisible(false);
                s4BLValue.setVisible(false);
                s4BLLeft.setVisible(false);
                s4BLRight.setVisible(false);
                thresholdSlider.setVisible(false);
                break;
            case "Threshold":
                s4BLStep.setText("<html><div style='text-align: center;'>[Step 6]</div></html>");
                s4BLText.setVisible(true);
                s4BLStep.setVisible(true);
                s4BLValue.setVisible(true);
                s4BLLeft.setVisible(true);
                s4BLRight.setVisible(true);
                thresholdSlider.setVisible(true);
                break;
            case "NoThreshold":
                s4BLText.setVisible(false);
                s4BLStep.setVisible(false);
                s4BLValue.setVisible(false);
                s4BLLeft.setVisible(false);
                s4BLRight.setVisible(false);
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
        executeB.setEnabled(runnable);
    }


    public void closeWindow() {
        loader.closeWindow();
        dispose();
    }


    private void updateStatus() {
        if (args[0].equals("null")) {
            s1LStep.setText("<html><div style='text-align: center;'>[Step 1]</div></html>");
        } else {
            s1LStep.setText("<html><div style='text-align: center;'>[Step 1] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if (args[1].equals("null")) {
            s2LStep.setText("<html><div style='text-align: center;'>[Step 2]</div></html>");
        } else {
            s2LStep.setText("<html><div style='text-align: center;'>[Step 2] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if (args[2].equals("null") || args[2].equals("Select")) {
            s3LStep.setText("<html><div style='text-align: center;'>[Step 3]</div></html>");
        } else {
            s3LStep.setText("<html><div style='text-align: center;'>[Step 3] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if (args[3].equals("null") || args[3].equals("Select")) {
            s4ALStep.setText("<html><div style='text-align: center;'>[Step 4]</div></html>");
        } else {
            s4ALStep.setText("<html><div style='text-align: center;'>[Step 4] <font color =" +
                    " '#56f310'>DONE</font></div></html>");
        }

        if (args[5].equals("null") || args[3].equals("Select")) {
            s5LStep.setText("<html><div style='text-align: center;'>[Step 5]</div></html>");
        } else {
            s5LStep.setText("<html><div style='text-align: center;'>[Step 5] <font color =" +
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
        } else if (e.getSource() == executeB) {
            //loader = new SWM_Loader_Frame();
            loader = new SWMLoaderFrame();
            loader.start();
            //set mainPath according to Operating System
            String mainPath = MainPipeline.class.getProtectionDomain().getCodeSource().getLocation().getPath().
                    replace("target/classes/", "");
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                mainPath = mainPath.substring(1);
            }

            try {
                generateXML();
                PipelineThread mainThread = new PipelineThread(mainPath, args[2], args[1]);
                mainThread.start();
                this.setEnabled(false);
            } catch (TransformerException | ParserConfigurationException exception) {
                logger.log(Level.SEVERE, "Generating the XML Failed", exception);
            }
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
        s4BLValue.setText("value: " + decimalFixedDouble);
        args[4] = decimalFixedDouble;
    }

    void generateXML() throws TransformerException, ParserConfigurationException {
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

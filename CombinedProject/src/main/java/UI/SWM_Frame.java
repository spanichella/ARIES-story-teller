package UI;

import fileGeneration.XMLInitializer;
import pipelines.MainPipeline;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.logging.Level;

public class SWM_Frame extends JFrame implements ActionListener, ItemListener, ChangeListener {

    private final JButton selector_b;
    private final JButton execute_b;
    private final JLabel l1, l2, l3, l4, l5, l6, l7;
    private final JComboBox c1, c2;
    private final ImageIcon swmLogo;
    private final JPanel panel;
    private final JRadioButton cb1, cb2;
    private final JSlider thresholdSlider;
    private String[] args;
    private final DecimalFormat df = new DecimalFormat("#.##");


    SWM_Frame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        panel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(150, 770); // TODO: smelly "magic numbers" across entire file
            }
        }; // TODO: what are you doing here? are you calling an object constructor or defining a new class? This looks highly questionable!

        panel.setLayout(null);

        String[] methodArray = {
                "Select", "J48", "PART", "NaiveBayes", "IBk", "OneR", "SMO",
                "Logistic", "AdaBoostM1", "LogitBoost",
                "DecisionStump", "LinearRegression",
                "RegressionByDiscretization"
        };

        String[] pipelineArray = {"Select", "ML", "DL", "Both"};

        //default split is 50%
        args = new String[] {"null","null","null","null","0.5"}; //File,Type,Pipeline,Method,split

        //TruthSet Selector Button
        selector_b = new JButton("Select Truth Set");
        selector_b.setBounds(50, 265, 150, 30);
        selector_b.addActionListener(this);

        //Execute Selector Button
        execute_b = new JButton("Execute");
        execute_b.setBounds(50, 720, 150, 30);
        execute_b.setBackground(Color.BLACK);
        execute_b.setForeground(Color.WHITE);
        execute_b.addActionListener(this);

        //DropDown Selector Button
        c1 = new JComboBox(methodArray);
        c1.setBounds(50, 540, 150, 30);
        c1.addItemListener(this);

        //DropDown Selector Button
        c2 = new JComboBox(pipelineArray);
        c2.setBounds(50, 455, 150, 30);
        c2.addItemListener(this);

        //Labels
        l6 = new JLabel("<html><div style='text-align: center;'>[Step 1] <br> Select a truth set to" +
                " be analyzed by the algorithm</div></html>");
        l6.setBounds(25, 210, 200, 50);
        l2 = new JLabel("<html><div style='text-align: center;'>[Step 2] <br> Select file type</div></html>");
        l2.setBounds(80, 320, 200, 30);
        l7 = new JLabel("<html><div style='text-align: center;'>[Step 3] <br> Select desired" +
                " Pipeline</div></html>");
        l7.setBounds(60, 410, 200, 50);
        l1 = new JLabel("<html><div style='text-align: center;'>[Step 4] <br> Select a method</div></html>");
        l1.setBounds(80, 505, 200, 30);
        l3 = new JLabel("<html><div style='text-align: center;'>[Step 5] <br> Set Dataset " +
                "Threshold</div></html>");
        l3.setBounds(60, 585, 300, 40);
        l4 = new JLabel("<html><div style='text-align: center;'>value: 0.5</div></html>");
        l4.setBounds(90, 620, 300, 40);

        //Images
        swmLogo = new ImageIcon("Images/swmlogo2.jpg");
        l5 = new JLabel();
        l5.setIcon(swmLogo);
        l5.setBounds(25,0,200,200);

        //Checkboxes
        cb1 = new JRadioButton("User Review Data");
        cb2 = new JRadioButton("Requirement Specification Data");
        cb1.setBounds(25, 375, 300, 30);
        cb2.setBounds(25, 350, 300, 30);
        cb1.addActionListener(this);
        cb2.addActionListener(this);

        //Sliders
        thresholdSlider = new JSlider();
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);
        Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
        position.put(0, new JLabel("0.01"));
        position.put(50, new JLabel("0.5"));
        position.put(100, new JLabel("1"));
        thresholdSlider.setLabelTable(position);
        thresholdSlider.addChangeListener(this);
        thresholdSlider.setBounds(20, 630, 200, 100);

        panel.add(cb1);
        panel.add(cb2);
        panel.add(selector_b);
        panel.add(execute_b);
        panel.add(c2);
        panel.add(l5);
        panel.add(l6);
        panel.add(l7);
        panel.add(l2);
        panel.add(l3);
        panel.add(l4);
        panel.add(thresholdSlider);
        this.add(panel);

        this.pack();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == selector_b) {
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
        } else if (e.getSource() == execute_b) { //TODO implement XML
            if((args[2].equals("ML") || args[2].equals("Both")) &&
                    (args[3].equals("null") || args[3].equals("Select"))){
                displayErrorMessage("No method selected! (Step 4)");
            }
            else if(args[0].equals("null")){
                displayErrorMessage("No input file selected! (Step 1)");
            }
            else if(args[1].equals("null")){
                displayErrorMessage("No filetype selected! (Step 2)");
            }
            else if(args[2].equals("null") || args[2].equals("Select")){
                displayErrorMessage("No Pipeline selected! (Step 3)");
            }
            else{
                //set mainPath according to Operating System
                String mainPath = MainPipeline.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/classes/", "");
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    mainPath = mainPath.substring(1);
                }
                generateXML();
                try {
                    MainPipeline.runPipeline(mainPath, args[2], args[1]);
                }catch (Exception e1){
                    System.out.print(e1);
                }
                dispose();
            }

        } else if (e.getSource() == cb1) {
            args[1] = "UR";
            System.out.println(args[1]);
            cb2.setSelected(false);
        } else if (e.getSource() == cb2) {
            args[1] = "RS";
            cb1.setSelected(false);
        }
        updateStatus();
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == c1){
            if (e.getStateChange() == ItemEvent.SELECTED) {
                args[3] = e.getItem().toString();
            }
        }
        else if (e.getSource() == c2){
            if (e.getStateChange() == ItemEvent.SELECTED) {
                args[2] = e.getItem().toString();
                if (e.getItem() == "ML" || e.getItem() == "Both") {
                    panel.add(c1);
                    panel.add(l1);
                } else {
                    panel.remove(c1);
                    panel.remove(l1);
                }
                redrawFrame();
            }
        }
        updateStatus();
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        double value = ((JSlider) e.getSource()).getValue() * 0.01;
        String decimalFixedDouble = df.format(value);
        l4.setText("value: " + decimalFixedDouble);
        args[4] = decimalFixedDouble;
        redrawFrame();
    }


    private void updateStatus(){
        if(args[0].equals("null")){
            l6.setText("<html><div style='text-align: center;'>[Step 1]<br> Select a truth set to be analyzed by the" +
                    " algorithm</div></html>");
        }
        else{
            l6.setText("<html><div style='text-align: center;'>[Step 1] <font color = 'green'>DONE</font>" +
                    " <br> Select a truth set to be analyzed by the algorithm</div></html>");
        }

        if(args[1].equals("null")){
            l2.setText("<html><div style='text-align: center;'>[Step 2] <br> Select file type</div></html>");
        }
        else{
            l2.setText("<html><div style='text-align: center;'>[Step 2] <font color = 'green'>DONE</font>" +
                    " <br> Select file type</div></html>");
        }

        if(args[2].equals("null") || args[2].equals("Select")){
            l7.setText("<html><div style='text-align: center;'>[Step 3] <br> Select desired Pipeline</div></html>");
        }
        else{
            l7.setText("<html><div style='text-align: center;'>[Step 3] <font color = 'green'>DONE</font>" +
                    " <br> Select desired Pipeline</div></html>");
        }

        if(args[3].equals("null") || args[3].equals("Select")){
            l1.setText("<html><div style='text-align: center;'>[Step 4] <br> Select a method</div></html>");
        }
        else{
            l1.setText("<html><div style='text-align: center;'>[Step 4] <font color = 'green'>DONE</font>" +
                    " <br> Select a method</div></html>");
        }
    }

    void generateXML() {

        //create XML files with paths, etc.
        //set mainPath according to Operating System
        String mainPath = MainPipeline.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("target/classes/", "");
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            mainPath = mainPath.substring(1);
        }
        System.out.print(mainPath);
        XMLInitializer.createXML(mainPath, args[0], args[1], args[3], args[4]);

    }

    public void redrawFrame(){
        panel.revalidate();
        panel.repaint();
    }


    public void displayErrorMessage(String errorText){
        JOptionPane.showMessageDialog(this,errorText,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

}

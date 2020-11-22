package  UI;
import configFile.XMLInitializer;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Hashtable;

public class SWM_Frame extends JFrame implements ActionListener, ItemListener, ChangeListener {

    private JButton selector_b;
    private JButton execute_b;
    private JLabel l, i1, i2, i3, l2, l3, l4;
    private JComboBox c1, c2;
    private ImageIcon swmLogo;
    private JPanel panel;
    private JRadioButton cb1, cb2;
    private JSlider thresholdSlider;
    private Double threshold;

    SWM_Frame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel(){
            @Override
            public Dimension getPreferredSize(){
                return new Dimension(150,770);
            };
        };
        panel.setLayout(null);

        String[] methodArray =
                {"J48", "PART", "NaiveBayes", "IBk", "OneR","SMO",
                        "Logistic","AdaBoostM1","LogitBoost",
                        "DecisionStump","LinearRegression",
                        "RegressionByDiscretization"};

        String[] pipelineArray =
                {"Select", "ML", "DL", "Both"};

        //TruthSet Selector Button
        selector_b = new JButton("Select Truth Set");
        selector_b.setBounds(50,265,150,30);
        selector_b.addActionListener(this);

        //Execute Selector Button
        execute_b = new JButton("Execute");
        execute_b.setBounds(50,720,150,30);
        execute_b.setBackground(Color.BLACK);
        execute_b.setForeground(Color.WHITE);
        execute_b.addActionListener(this);

        //DropDown Selector Button
        c1 = new JComboBox(methodArray);
        c1.setBounds(50,540,150,30);
        c1.addItemListener(this);

        //DropDown Selector Button
        c2 = new JComboBox(pipelineArray);
        c2.setBounds(50,455,150,30);
        c2.addItemListener(this);

        //Labels
        l2 = new JLabel("<html><div style='text-align: center;'>[Step 2] <br> Select file type</div></html>");
        l2.setBounds(80,320,200,30);
        l = new JLabel("<html><div style='text-align: center;'>[Step 4] <br> Select a method</div></html>");
        l.setBounds(80,505,200,30);
        i2 = new JLabel("<html><div style='text-align: center;'>[Step 1] <br> Select a truth set to be analyzed by the algorithm</div></html>");
        i2.setBounds(25,210,200,50);
        i3 = new JLabel("<html><div style='text-align: center;'>[Step 3] <br> Select desired Pipeline</div></html>");
        i3.setBounds(60,410,200,50);
        l3 = new JLabel("<html><div style='text-align: center;'>[Step 5] <br> Set Dataset Threshold</div></html>");
        l3.setBounds(60,585,300,40);
        l4 = new JLabel("<html><div style='text-align: center;'>value: 0.5</div></html>");
        l4.setBounds(90,620,300,40);

        //Images TODO
        //swmLogo = new ImageIcon(getClass().getResource("swmlogo2.jpg"));
        //i1 = new JLabel(swmLogo);
        //i1.setBounds(25,0,200,200);

        //Checkboxes
        cb1 = new JRadioButton("User Review Data");
        cb2 = new JRadioButton("Requirement Specification Data");
        cb1.setBounds(25,375,300,30);
        cb2.setBounds(25,350,300,30);
        cb1.addActionListener(this);
        cb2.addActionListener(this);

        //Sliders
        thresholdSlider = new JSlider();
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);
        Hashtable<Integer, JLabel> position = new Hashtable<Integer, JLabel>();
        position.put(0, new JLabel("0.01"));
        position.put(50, new JLabel( "0.5"));
        position.put(100, new JLabel("1"));
        thresholdSlider.setLabelTable(position);
        thresholdSlider.addChangeListener(this);
        thresholdSlider.setBounds(20,630,200,100);


        panel.add(cb1);
        panel.add(cb2);
        panel.add(selector_b);
        panel.add(execute_b);
        panel.add(c2);
        //panel.add(i1);
        panel.add(i2);
        panel.add(i3);
        panel.add(l2);
        panel.add(l3);
        panel.add(l4);
        panel.add(thresholdSlider);
        this.add(panel);

        this.pack();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == selector_b){
            JFileChooser fileChooser = new JFileChooser();

            int response = fileChooser.showOpenDialog(null); //select file to open, returns integer, 0 = open, 1 = cancel

            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                System.out.println((file));
            }
        }
        //TODO implement XML
        else if(e.getSource() == execute_b){
            //TODO XML call
            //TODO call PIPELINE
            //TODO return from pipeline
            System.out.println("Execute a Function and close the window");
            dispose();
        }
        else if(e.getSource() == cb1){
            System.out.println("Rev");
            cb2.setSelected(false);
        }
        else if(e.getSource() == cb2){
            System.out.println("ReqSpec");
            cb1.setSelected(false);
        }
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            System.out.println(e.getItem());
            if(e.getItem() == "ML" || e.getItem() == "Both"){
                panel.add(c1);
                panel.add(l);
                panel.revalidate();
                panel.repaint();
            }
            else{
                panel.remove(c1);
                panel.remove(l);
                panel.revalidate();
                panel.repaint();
            }
        }
        else if(e.getStateChange() == ItemEvent.DESELECTED){
            return;
        }
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        double value = ((JSlider)e.getSource()).getValue()*0.01;
        l4.setText("value: " + value);
        System.out.println(value);
        panel.revalidate();
        panel.repaint();
    }
}

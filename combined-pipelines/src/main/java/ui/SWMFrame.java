package ui;

import filegeneration.XMLInitializer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import pipelines.DataType;
import pipelines.PipelineType;

final class SWMFrame extends JFrame implements ChangeListener {
    @Serial
    private static final long serialVersionUID = -592869500939986619L;
    private static final Logger logger = Logger.getLogger(SWMFrame.class.getName());
    private static final File DATASETS_FOLDER = new File("..", "datasets");
    private static final String EMPTY_TEXT = "Select";

    private static final Color backGroundColor = new Color(88, 102, 148);
    private static final Color textColor = new Color(230, 230, 230);
    private static final Color separatorColor = new Color(79, 92, 134);

    private SWMLoaderFrame loader;

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
    @Nullable private String truthFilePath;
    @Nullable private DataType dataType;
    @Nullable private PipelineType pipelineType;
    @Nonnull private String mlModel = EMPTY_TEXT;
    @Nonnull private BigDecimal split = BigDecimal.valueOf(5, 1);
    @Nonnull private String strategy = EMPTY_TEXT;
    private final JComboBox<String> pipelineTypeComboBox;
    private final JComboBox<String> mlModelComboBox;
    private final JComboBox<String> strategyComboBox;
    private final JSlider thresholdSlider;
    private static final String[] strategyArray = {"Select", "10-Fold", "Percentage-Split"};
    private static final String[] mlModelArray = {
        EMPTY_TEXT, "J48", "PART", "NaiveBayes", "IBk", "OneR", "SMO",
        "Logistic", "AdaBoostM1", "LogitBoost",
        "DecisionStump", "LinearRegression",
        "RegressionByDiscretization",
        };

    SWMFrame() {
        ImageIcon logoImage = new ImageIcon("images/swmlogo.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        executeB = new JButton("Run");
        executeB.addActionListener(this::onRun);
        executeB.setEnabled(false);

        thresholdSlider = new JSlider();
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.setBackground(backGroundColor);
        thresholdSlider.addChangeListener(this);
        thresholdSlider.setVisible(false);

        pipelineTypeComboBox = getTranslatableComboBox(
                new PipelineType[] { null, PipelineType.ML, PipelineType.DL },
                SWMFrame::translatePipelineType, this::onPipelineTypeChange);

        mlModelComboBox = getComboBox(mlModelArray, (String newName) -> mlModel = newName);
        mlModelComboBox.setVisible(false);

        strategyComboBox = getComboBox(strategyArray, this::onStrategyChange);
        strategyComboBox.setVisible(false);

        s1LStep = getLabel("[Step 1]");
        s2LStep = getLabel("[Step 2]");
        s3LStep = getLabel("[Step 3]");
        s4ALStep = getLabel("[Step 4]", false);
        s4ALText = getLabel("Select Method", false);
        s4BLText = getLabel("Set Size of Training-Set", false);
        s4BLStep = getLabel("[Step 5]", false);
        s4BLValue = getLabel("value: 0.5", false);
        s4BLLeft = getLabel("0.1", false);
        s4BLRight = getLabel("1.0", false);
        s5LStep = getLabel("[Step 5]", false);
        s5LText = getLabel("Select Strategy", false);

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
        s1BorderCenterPanel.add(getLabel("Select a truth set to be analyzed by the algorithm"));
        s1BorderCenterPanel.add(s1CenterPanel);
        s1CenterPanel.add(s1Empty);
        JButton truthSetSelector = new JButton("Truth Set");
        truthSetSelector.addActionListener(this::onTruthSetSelector);
        s1CenterPanel.add(truthSetSelector);

        //step 2 panels
        step2Panel.add(s2BorderCenterPanel, BorderLayout.CENTER);
        step2Panel.add(s2BlackBorder1, BorderLayout.PAGE_END);
        s2BorderCenterPanel.add(s2LStep);
        s2BorderCenterPanel.add(getLabel("Select Content Type"));
        s2BorderCenterPanel.add(getTranslatableComboBox(
                new DataType[]{null, DataType.USER_REVIEWS, DataType.REQUIREMENT_SPECIFICATIONS},
                SWMFrame::translateDataType, this::onDataTypeChange));

        //step 3 panels
        step3Panel.add(s3BorderCenterPanel, BorderLayout.CENTER);
        step3Panel.add(s3BlackBorder1, BorderLayout.PAGE_END);
        s3BorderCenterPanel.add(s3LStep);
        s3BorderCenterPanel.add(getLabel("Select a Pipeline"));
        s3BorderCenterPanel.add(pipelineTypeComboBox);

        //step 4a panels
        step4aPanel.add(s4ABorderCenterPanel, BorderLayout.CENTER);
        step4aPanel.add(s4ABlackBorder1, BorderLayout.PAGE_END);
        s4ABorderCenterPanel.add(s4ALStep);
        s4ABorderCenterPanel.add(s4ALText);
        s4ABorderCenterPanel.add(mlModelComboBox);

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
        s5BorderCenterPanel.add(strategyComboBox);

        this.setVisible(true);
    }

    private void populatePipelinePanels() {
        switch (pipelineType) {
            case DL -> {
                s4ALText.setVisible(false);
                s4ALStep.setVisible(false);
                mlModelComboBox.setVisible(false);
                s4BLStep.setText("<html><div style='text-align: center;'>[Step 4]</div></html>");
                s4BLText.setVisible(false);
                s4BLStep.setVisible(false);
                s4BLValue.setVisible(false);
                s4BLLeft.setVisible(false);
                s4BLRight.setVisible(false);
                thresholdSlider.setVisible(false);
                s5LText.setVisible(false);
                s5LStep.setVisible(false);
                strategyComboBox.setVisible(false);
            }
            case ML -> {
                s4ALText.setVisible(true);
                s4ALStep.setVisible(true);
                mlModelComboBox.setVisible(true);
                s5LText.setVisible(true);
                s5LStep.setVisible(true);
                strategyComboBox.setVisible(true);
                s4BLText.setVisible(false);
                s4BLStep.setVisible(false);
                s4BLValue.setVisible(false);
                s4BLLeft.setVisible(false);
                s4BLRight.setVisible(false);
                thresholdSlider.setVisible(false);
            }
            default -> throw new IllegalArgumentException("Unknown pipeline type: " + pipelineType);
        }
    }

    private void populateThresholdPanels(boolean withThreshold) {
        if (withThreshold) {
            s4BLStep.setText("<html><div style='text-align: center;'>[Step 6]</div></html>");
            s4BLText.setVisible(true);
            s4BLStep.setVisible(true);
            s4BLValue.setVisible(true);
            s4BLLeft.setVisible(true);
            s4BLRight.setVisible(true);
            thresholdSlider.setVisible(true);
        } else {
            s4BLText.setVisible(false);
            s4BLStep.setVisible(false);
            s4BLValue.setVisible(false);
            s4BLLeft.setVisible(false);
            s4BLRight.setVisible(false);
            thresholdSlider.setVisible(false);
        }
    }


    private void updateRunnable() {
        boolean runnable = true;

        if (truthFilePath == null || dataType == null || pipelineType == null) {
            runnable = false;
        } else if (!"DL".equals(pipelineTypeComboBox.getSelectedItem())
                && (mlModel.equals(EMPTY_TEXT) || strategy.equals(EMPTY_TEXT))) {
            runnable = false;
        }
        executeB.setEnabled(runnable);
    }

    void closeWindow() {
        loader.closeWindow();
        dispose();
    }

    private void updateStatus() {
        s1LStep.setText(toTitle("[Step 1]", truthFilePath != null));
        s2LStep.setText(toTitle("[Step 2]", dataType != null));
        s3LStep.setText(toTitle("[Step 3]", pipelineType != null));
        s4ALStep.setText(toTitle("[Step 4]", !mlModel.equals(EMPTY_TEXT)));
        s5LStep.setText(toTitle("[Step 5]", !strategy.equals(EMPTY_TEXT)));
    }

    private void onTruthSetSelector(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (DATASETS_FOLDER.exists()) {
            fileChooser.setCurrentDirectory(DATASETS_FOLDER);
        }
        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            String extension = file.toString().substring(file.toString().lastIndexOf("."));
            if (!extension.equals(".txt") && !extension.equals(".csv")) {
                showErrorMessage("Wrong filetype selected. Please select a .txt or .csv file", null);
                truthFilePath = null;
            } else {
                truthFilePath = file.toString();
            }
        }
        updateStatus();
        updateRunnable();
    }

    private void onRun(ActionEvent e) {
        if (truthFilePath == null || pipelineType == null || dataType == null) {
            throw new IllegalArgumentException("truthFilePath or pipelineType or dataType is null");
        }

        loader = new SWMLoaderFrame();
        loader.start();

        try {
            XMLInitializer.createXML(truthFilePath, dataType, translateEmptyText(mlModel), split, translateEmptyText(strategy));
        } catch (TransformerException | ParserConfigurationException | RuntimeException exception) {
            showErrorMessage("Generating the XML Failed", exception);
            this.closeWindow();
            return;
        }
        PipelineThread mainThread = new PipelineThread(pipelineType, dataType);
        mainThread.start();
        mainThread.setUncaughtExceptionHandler((Thread thread, Throwable throwable) -> {
            if (throwable instanceof PipelineThread.ThreadException) {
                throwable = throwable.getCause();
            }
            showErrorMessage("Pipeline Thread failed", throwable);
            this.closeWindow();
        });
        this.setEnabled(false);
        updateStatus();
        updateRunnable();
    }

    private void onDataTypeChange(DataType dataType) {
        boolean found = false;
        switch (dataType) {
            case REQUIREMENT_SPECIFICATIONS -> {
                for (int i = 0; i < pipelineTypeComboBox.getItemCount(); i++) {
                    if (pipelineTypeComboBox.getItemAt(i).equals("DL")) {
                        found = true;
                    }
                }
                if (!found) {
                    pipelineTypeComboBox.insertItemAt("DL", pipelineTypeComboBox.getItemCount());
                }
            }
            case USER_REVIEWS -> {
                pipelineTypeComboBox.setSelectedItem("ML");
                pipelineTypeComboBox.removeItem("DL");
            }
            default -> throw new IllegalArgumentException("Unknown type: " + dataType);
        }
        this.dataType = dataType;
    }

    private void onPipelineTypeChange(PipelineType pipelineType) {
        populatePanels(pipelineType);
        this.pipelineType = pipelineType;
    }

    private void onStrategyChange(String newStrategy) {
        switch (newStrategy) {
            case "Percentage-Split" -> populateThresholdPanels(true);
            case "10-Fold" -> populateThresholdPanels(false);
            default -> throw new IllegalArgumentException("Unknown strategy " + newStrategy);
        }
        strategy = newStrategy;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int rawValue = ((JSlider) e.getSource()).getValue();
        split = BigDecimal.valueOf(Math.max(rawValue, 10), 2);
        s4BLValue.setText(toTitle("value: " + split.toPlainString()));
    }

    private <E> JComboBox<String> getTranslatableComboBox(E[] elements, Function<E, String> translator, Consumer<E> listener) {
        List<String> translations = Arrays.stream(elements).map(translator).collect(Collectors.toUnmodifiableList());
        return getComboBox(translations.toArray(new String[0]), (String value) ->
                listener.accept(elements[translations.indexOf(value)]));
    }

    private JComboBox<String> getComboBox(String[] names, Consumer<String> listener) {
        JComboBox<String> comboBox = new JComboBox<>(names);
        comboBox.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String value = (String) e.getItem();
                if (comboBox.getItemAt(0).equals(EMPTY_TEXT)) {
                    comboBox.removeItemAt(0);
                }
                listener.accept(value);
            }
            updateStatus();
            updateRunnable();
        });
        ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        return comboBox;
    }

    private static String translateDataType(@Nullable DataType dataType) {
        if (dataType == null) {
            return EMPTY_TEXT;
        }
        return switch (dataType) {
            case REQUIREMENT_SPECIFICATIONS -> "Requirement-Specifications";
            case USER_REVIEWS -> "User-Reviews";
        };
    }

    private static String translatePipelineType(@Nullable PipelineType pipelineType) {
        if (pipelineType == null) {
            return EMPTY_TEXT;
        }
        return switch (pipelineType) {
            case ML -> "ML";
            case DL -> "DL";
        };
    }

    @Nonnull private static String translateEmptyText(@Nonnull String text) {
        return text.equals(EMPTY_TEXT) ? "null" : text;
    }

    private void showErrorMessage(@Nonnull String message, @Nullable Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static JLabel getLabel(String title) {
        return getLabel(title, true);
    }

    private static JLabel getLabel(String title, boolean visible) {
        JLabel label = new JLabel(toTitle(title));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(textColor);
        label.setVisible(visible);
        return label;
    }

    private static String toTitle(String title) {
        return toTitle(title, false);
    }

    private static String toTitle(String title, boolean done) {
        if (done) {
            title += " <font color='#56f310'>DONE</font>";
        }
        return "<html>" + title + "</html>";
    }
}

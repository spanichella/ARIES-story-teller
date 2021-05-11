package ui;

import filegeneration.XMLInitializer;
import java.awt.BorderLayout;
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
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.Icon;
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
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import pipelines.MainPipeline;
import types.DataType;
import types.PipelineType;
import static ui.UIHelpers.*;

final class SWMFrame extends JFrame {
    @Serial
    private static final long serialVersionUID = -592869500939986619L;
    private static final Logger logger = Logger.getLogger(SWMFrame.class.getName());
    private static final File DATASETS_FOLDER = new File("..", "datasets");
    private static final String EMPTY_TEXT = "Select";

    @Nullable private SWMLoaderFrame loader;

    @Nonnull private final JButton executeB;
    @Nonnull private final JLabel s1LStep;
    @Nonnull private final JLabel s2LStep;
    @Nonnull private final JLabel s3LStep;
    @Nonnull private final JLabel s4ALStep;
    @Nonnull private final JLabel s4ALText;
    @Nonnull private final JLabel s4BLStep;
    @Nonnull private final JLabel s4BLText;
    @Nonnull private final JLabel s4BLValue;
    @Nonnull private final JLabel s4BLLeft;
    @Nonnull private final JLabel s4BLRight;
    @Nonnull private final JLabel s5LStep;
    @Nonnull private final JLabel s5LText;
    @Nullable private String truthFilePath;
    @Nullable private DataType dataType;
    @Nullable private PipelineType pipelineType;
    @Nonnull private String mlModel = EMPTY_TEXT;
    @Nonnull private BigDecimal split = BigDecimal.valueOf(5, 1);
    @Nonnull private String strategy = EMPTY_TEXT;
    @Nonnull private final JComboBox<String> pipelineTypeComboBox;
    @Nonnull private final JComboBox<String> mlModelComboBox;
    @Nonnull private final JComboBox<String> strategyComboBox;
    @Nonnull private final JSlider thresholdSlider;
    private static final String[] strategyArray = {"Select", "10-Fold", "Percentage-Split"};
    private static final String[] mlModelArray = {
        EMPTY_TEXT, "J48", "PART", "NaiveBayes", "IBk", "OneR", "SMO",
        "Logistic", "AdaBoostM1", "LogitBoost",
        "DecisionStump", "LinearRegression",
        "RegressionByDiscretization",
        };

    SWMFrame() {
        Icon logoImage = new ImageIcon("images/swmlogo.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        executeB = new JButton("Run");
        executeB.addActionListener(this::onRun);
        executeB.setEnabled(false);

        thresholdSlider = new JSlider();
        thresholdSlider.setMinorTickSpacing(1);
        thresholdSlider.setBackground(DefaultColors.BACKGROUND);
        thresholdSlider.addChangeListener(this::onSplitChanged);
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
        setIconImage(icon.getImage());
        setTitle("Story Teller");
        setSize(350, 800);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 1));
        add(mainPanel, BorderLayout.CENTER);

        add(createSeparator(), BorderLayout.PAGE_START);
        add(createSeparator(), BorderLayout.PAGE_END);
        add(createSeparator(), BorderLayout.WEST);
        add(createSeparator(), BorderLayout.EAST);

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(DefaultColors.BACKGROUND);

        JPanel logoPanel = createPanel(new BorderLayout());
        logoPanel.add(logoLabel);
        mainPanel.add(logoPanel);

        //Step 1 Panels
        JPanel s1BorderCenterPanel = createPanel(new GridLayout(3, 1));
        s1BorderCenterPanel.add(s1LStep);
        s1BorderCenterPanel.add(getLabel("Select a truth set to be analyzed by the algorithm"));
        JButton truthSetSelector = new JButton("Truth Set");
        truthSetSelector.addActionListener(this::onTruthSetSelector);
        JPanel s1CenterPanel = createPanel(new GridLayout(0, 3));
        s1CenterPanel.add(createPanel());
        s1CenterPanel.add(truthSetSelector);
        s1BorderCenterPanel.add(s1CenterPanel);
        JPanel step1Panel = createPanel(new BorderLayout());
        step1Panel.add(createSeparator(), BorderLayout.PAGE_START);
        step1Panel.add(s1BorderCenterPanel, BorderLayout.CENTER);
        step1Panel.add(createSeparator(), BorderLayout.PAGE_END);
        mainPanel.add(step1Panel);

        //step 2 panels
        JPanel s2BorderCenterPanel = createPanel(new GridLayout(3, 1));
        s2BorderCenterPanel.add(s2LStep);
        s2BorderCenterPanel.add(getLabel("Select Content Type"));
        s2BorderCenterPanel.add(getTranslatableComboBox(
            new DataType[]{null, DataType.USER_REVIEWS, DataType.REQUIREMENT_SPECIFICATIONS},
            SWMFrame::translateDataType, this::onDataTypeChange));
        JPanel step2Panel = createPanel(new BorderLayout());
        step2Panel.add(s2BorderCenterPanel, BorderLayout.CENTER);
        step2Panel.add(createSeparator(), BorderLayout.PAGE_END);
        mainPanel.add(step2Panel);

        //step 3 panels
        JPanel s3BorderCenterPanel = createPanel(new GridLayout(3, 1));
        s3BorderCenterPanel.add(s3LStep);
        s3BorderCenterPanel.add(getLabel("Select a Pipeline"));
        s3BorderCenterPanel.add(pipelineTypeComboBox);
        JPanel step3Panel = createPanel(new BorderLayout());
        step3Panel.add(s3BorderCenterPanel, BorderLayout.CENTER);
        step3Panel.add(createSeparator(), BorderLayout.PAGE_END);
        mainPanel.add(step3Panel);

        //step 4a panels
        JPanel s4ABorderCenterPanel = createPanel(new GridLayout(3, 1));
        s4ABorderCenterPanel.add(s4ALStep);
        s4ABorderCenterPanel.add(s4ALText);
        s4ABorderCenterPanel.add(mlModelComboBox);
        JPanel step4aPanel = createPanel(new BorderLayout());
        step4aPanel.add(s4ABorderCenterPanel, BorderLayout.CENTER);
        step4aPanel.add(createSeparator(), BorderLayout.PAGE_END);
        mainPanel.add(step4aPanel);

        //step 4b Panels
        JPanel s4BHorizontalSplitter = createPanel(new GridLayout(0, 3));
        s4BHorizontalSplitter.add(s4BLLeft);
        s4BHorizontalSplitter.add(s4BLValue);
        s4BHorizontalSplitter.add(s4BLRight);
        JPanel s4BBorderCenterPanel = createPanel(new GridLayout(4, 1));
        s4BBorderCenterPanel.add(s4BLStep);
        s4BBorderCenterPanel.add(s4BLText);
        s4BBorderCenterPanel.add(s4BHorizontalSplitter);
        s4BBorderCenterPanel.add(thresholdSlider);
        JPanel step4bPanel = createPanel(new BorderLayout());
        step4bPanel.add(s4BBorderCenterPanel, BorderLayout.CENTER);
        step4bPanel.add(createSeparator(), BorderLayout.PAGE_END);
        mainPanel.add(step4bPanel);

        //step 5 Panels
        JPanel s5BorderCenterPanel = createPanel(new GridLayout(3, 1));
        s5BorderCenterPanel.add(s5LStep);
        s5BorderCenterPanel.add(s5LText);
        s5BorderCenterPanel.add(strategyComboBox);
        JPanel step5Panel = createPanel(new BorderLayout());
        step5Panel.add(s5BorderCenterPanel, BorderLayout.CENTER);
        step5Panel.add(createSeparator(), BorderLayout.PAGE_END);
        mainPanel.add(step5Panel);

        //step 6 Panels
        JPanel step6MainGrid = createPanel(new GridLayout(3, 1));
        step6MainGrid.add(createPanel());
        step6MainGrid.add(executeB);
        step6MainGrid.add(createPanel());
        JPanel step6Panel = createPanel(new BorderLayout());
        step6Panel.add(step6MainGrid, BorderLayout.CENTER);
        mainPanel.add(step6Panel);

        setVisible(true);
    }

    private void populatePipelinePanels() {
        if (pipelineType == null) {
            return;
        }
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
            default -> throw new IllegalArgumentException("Unknown pipeline type: %s".formatted(pipelineType));
        }
    }

    private void populateThresholdPanels(boolean withThreshold) {
        if (withThreshold) {
            s4BLStep.setText("<html><div style='text-align: center;'>[Step 6]</div></html>");
        }
        s4BLText.setVisible(withThreshold);
        s4BLStep.setVisible(withThreshold);
        s4BLValue.setVisible(withThreshold);
        s4BLLeft.setVisible(withThreshold);
        s4BLRight.setVisible(withThreshold);
        thresholdSlider.setVisible(withThreshold);
    }

    private boolean isRunnable() {
        if (truthFilePath == null || dataType == null || pipelineType == null) {
            return false;
        }
        return "DL".equals(pipelineTypeComboBox.getSelectedItem())
            || (!mlModel.equals(EMPTY_TEXT) && !strategy.equals(EMPTY_TEXT));
    }

    private void updateRunnable() {
        executeB.setEnabled(isRunnable());
    }

    private void closeWindow() {
        if (loader != null) {
            loader.closeWindow();
        }
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
            String extension = file.toString().substring(file.toString().lastIndexOf('.'));
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
            closeWindow();
            return;
        }
        Thread mainThread = new Thread(() -> runPipeline(pipelineType, dataType));
        mainThread.start();
        mainThread.setUncaughtExceptionHandler((thread, throwable) -> {
            showErrorMessage("Pipeline Thread failed",
                    throwable instanceof ThreadException ? throwable.getCause() : throwable);
            closeWindow();
        });
        setEnabled(false);
        updateStatus();
        updateRunnable();
    }

    private void runPipeline(@Nonnull PipelineType realPipelineType, @Nonnull DataType realDataType) {
        logger.info("Thread Running");
        try {
            MainPipeline.runPipeline(realPipelineType, realDataType);
            closeWindow();
        } catch (Exception e) {
            throw new ThreadException(e);
        }
    }

    private void onDataTypeChange(DataType newDataType) {
        switch (newDataType) {
            case REQUIREMENT_SPECIFICATIONS -> {
                if (IntStream.range(0, pipelineTypeComboBox.getItemCount())
                    .mapToObj(pipelineTypeComboBox::getItemAt)
                    .noneMatch(value -> value.equals("DL"))) {
                    pipelineTypeComboBox.insertItemAt("DL", pipelineTypeComboBox.getItemCount());
                }
            }
            case USER_REVIEWS -> {
                pipelineTypeComboBox.setSelectedItem("ML");
                pipelineTypeComboBox.removeItem("DL");
            }
            default -> throw new IllegalArgumentException("Unknown type: %s".formatted(newDataType));
        }
        dataType = newDataType;
    }

    private void onPipelineTypeChange(PipelineType newPipelineType) {
        pipelineType = newPipelineType;
        populatePipelinePanels();
    }

    private void onStrategyChange(String newStrategy) {
        switch (newStrategy) {
            case "Percentage-Split" -> populateThresholdPanels(true);
            case "10-Fold" -> populateThresholdPanels(false);
            default -> throw new IllegalArgumentException("Unknown strategy %s".formatted(newStrategy));
        }
        strategy = newStrategy;
    }

    private void onSplitChanged(ChangeEvent e) {
        int rawValue = ((JSlider) e.getSource()).getValue();
        split = BigDecimal.valueOf(Math.max(rawValue, 10), 2);
        s4BLValue.setText(toTitle("value: " + split.toPlainString()));
    }

    private <E> JComboBox<String> getTranslatableComboBox(E[] elements, Function<? super E, String> translator,
                                                          Consumer<? super E> listener) {
        List<String> translations = Arrays.stream(elements).map(translator).collect(Collectors.toUnmodifiableList());
        return getComboBox(translations.toArray(new String[0]), (String value) ->
                listener.accept(elements[translations.indexOf(value)]));
    }

    private JComboBox<String> getComboBox(String[] names, Consumer<? super String> listener) {
        JComboBox<String> comboBox = new JComboBox<>(names);
        comboBox.addItemListener((event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String value = (String) event.getItem();
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

    private static final class ThreadException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 7785242162506057983L;

        private ThreadException(Throwable cause) {
            super(cause);
        }
    }
}

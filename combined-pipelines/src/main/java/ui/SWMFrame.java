package ui;

import filegeneration.XMLInitializer;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import pipelines.MainPipeline;
import types.DataType;
import types.PipelineType;

final class SWMFrame extends JFrame {
    @Serial
    private static final long serialVersionUID = -592869500939986619L;
    private static final Logger logger = Logger.getLogger(SWMFrame.class.getName());

    @Nullable private SWMLoaderFrame loader;

    @Nonnull private final JButton executeButton;
    @Nonnull private final TruthSetPanel truthSetPanel;
    @Nonnull private final ContentTypePanel contentTypePanel;
    @Nonnull private final PipelinePanel pipelinePanel;
    @Nonnull private final MlModelPanel mlModelPanel;
    @Nonnull private final StrategyPanel strategyPanel;
    @Nonnull private final ThresholdPanel thresholdPanel;
    @Nullable private String truthFilePath;
    @Nullable private DataType dataType;
    @Nullable private PipelineType pipelineType;
    @Nonnull private String mlModel = UIHelpers.EMPTY_TEXT;
    @Nonnull private BigDecimal split = BigDecimal.valueOf(5, 1);
    @Nonnull private String strategy = UIHelpers.EMPTY_TEXT;

    SWMFrame() {
        Icon logoImage = new ImageIcon("images/swmlogo.jpg");
        JLabel logoLabel = new JLabel();
        logoLabel.setIcon(logoImage);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        executeButton = new JButton("Run");
        executeButton.addActionListener(this::onRun);
        executeButton.setEnabled(false);

        ImageIcon icon = new ImageIcon("images/STIcon.jpg");
        setIconImage(icon.getImage());
        setTitle("Story Teller");
        setSize(350, 800);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 1));
        add(mainPanel, BorderLayout.CENTER);

        add(UIHelpers.createSeparator(), BorderLayout.PAGE_START);
        add(UIHelpers.createSeparator(), BorderLayout.PAGE_END);
        add(UIHelpers.createSeparator(), BorderLayout.WEST);
        add(UIHelpers.createSeparator(), BorderLayout.EAST);

        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(DefaultColors.BACKGROUND);

        JPanel logoPanel = UIHelpers.createPanel(new BorderLayout());
        logoPanel.add(logoLabel);
        mainPanel.add(logoPanel);

        truthSetPanel = new TruthSetPanel(
            this::updateStatus,
            file -> truthFilePath = file.toString(),
            error -> showErrorMessage(error, null));
        mainPanel.add(truthSetPanel);

        contentTypePanel = new ContentTypePanel(this::updateStatus, this::onDataTypeChange);
        mainPanel.add(contentTypePanel);

        pipelinePanel = new PipelinePanel(
            this::onPipelineTypeChange,
            this::updateStatus);
        mainPanel.add(pipelinePanel);

        mlModelPanel = new MlModelPanel(model -> mlModel = model, this::updateStatus);
        mlModelPanel.setItemsVisible(false);
        mainPanel.add(mlModelPanel);

        strategyPanel = new StrategyPanel(this::onStrategyChange, this::updateStatus);
        strategyPanel.setItemsVisible(false);
        mainPanel.add(strategyPanel);

        thresholdPanel = new ThresholdPanel(newSplit -> split = newSplit);
        thresholdPanel.setItemsVisible(false);
        mainPanel.add(thresholdPanel);

        //step 6 Panels
        JPanel step6MainGrid = UIHelpers.createPanel(new GridLayout(3, 1));
        step6MainGrid.add(UIHelpers.createPanel());
        step6MainGrid.add(executeButton);
        step6MainGrid.add(UIHelpers.createPanel());
        mainPanel.add(step6MainGrid);

        setVisible(true);
    }

    private void populatePipelinePanels() {
        if (pipelineType == null) {
            return;
        }
        switch (pipelineType) {
            case DL -> {
                mlModelPanel.setItemsVisible(false);
                thresholdPanel.setItemsVisible(false);
                strategyPanel.setItemsVisible(false);
            }
            case ML -> {
                mlModelPanel.setItemsVisible(true);
                strategyPanel.setItemsVisible(true);
                thresholdPanel.setItemsVisible(false);
            }
            default -> throw new IllegalArgumentException("Unknown pipeline type: %s".formatted(pipelineType));
        }
    }

    private void populateThresholdPanels(boolean withThreshold) {
        thresholdPanel.markDone(false);
        thresholdPanel.setItemsVisible(withThreshold);
    }

    private boolean isRunnable() {
        if (truthFilePath == null || dataType == null || pipelineType == null) {
            return false;
        }
        return pipelinePanel.getSelectedItem() == PipelineType.DL
            || (!mlModel.equals(UIHelpers.EMPTY_TEXT) && !strategy.equals(UIHelpers.EMPTY_TEXT));
    }

    private void closeWindow() {
        if (loader != null) {
            loader.closeWindow();
        }
        dispose();
    }

    private void updateStatus() {
        truthSetPanel.markDone(truthFilePath != null);
        contentTypePanel.markDone(dataType != null);
        pipelinePanel.markDone(pipelineType != null);
        mlModelPanel.markDone(!mlModel.equals(UIHelpers.EMPTY_TEXT));
        strategyPanel.markDone(!strategy.equals(UIHelpers.EMPTY_TEXT));
        executeButton.setEnabled(isRunnable());
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
            case REQUIREMENT_SPECIFICATIONS -> pipelinePanel.addDL();
            case USER_REVIEWS -> pipelinePanel.removeDL();
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

    @Nonnull private static String translateEmptyText(@Nonnull String text) {
        return text.equals(UIHelpers.EMPTY_TEXT) ? "null" : text;
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

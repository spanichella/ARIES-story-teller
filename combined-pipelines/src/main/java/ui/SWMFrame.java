package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.Serial;
import java.nio.file.Path;
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
import pipelines.AsyncPipeline;
import pipelines.MainPipeline;
import types.DataType;
import types.MlModelType;
import types.PipelineType;
import types.StrategyType;
import ui.panels.ContentTypePanel;
import ui.panels.MlModelPanel;
import ui.panels.PipelinePanel;
import ui.panels.StrategyPanel;
import ui.panels.ThresholdPanel;
import ui.panels.TruthSetPanel;
import ui.utilities.DefaultColors;
import ui.utilities.UIHelpers;

@SuppressWarnings("ClassWithTooManyDependencies")
final class SWMFrame extends JFrame {
    @Serial
    private static final long serialVersionUID = -592869500939986619L;
    private static final Logger LOGGER = Logger.getLogger(SWMFrame.class.getName());

    @Nonnull
    private final JButton executeButton;
    @Nonnull
    private final TruthSetPanel truthSetPanel;
    @Nonnull
    private final ContentTypePanel contentTypePanel;
    @Nonnull
    private final PipelinePanel pipelinePanel;
    @Nonnull
    private final MlModelPanel mlModelPanel;
    @Nonnull
    private final StrategyPanel strategyPanel;
    @Nonnull
    private final ThresholdPanel thresholdPanel;
    @Nullable
    private SWMLoaderFrame loader;
    @Nullable
    private Path truthFilePath;
    @Nullable
    private DataType dataType;
    @Nullable
    private PipelineType pipelineType;
    @Nullable
    private MlModelType mlModel;
    @Nullable
    private StrategyType strategy;

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
            file -> truthFilePath = file.toPath(),
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

        thresholdPanel = new ThresholdPanel();
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
        return pipelinePanel.getSelectedItem() == PipelineType.DL || (mlModel != null && strategy != null);
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
        mlModelPanel.markDone(mlModel != null);
        strategyPanel.markDone(strategy != null);
        executeButton.setEnabled(isRunnable());
    }

    private void onRun(ActionEvent e) {
        if (truthFilePath == null || pipelineType == null || dataType == null) {
            throw new IllegalArgumentException("truthFilePath or pipelineType or dataType is null");
        }

        loader = new SWMLoaderFrame();
        loader.start();

        AsyncPipeline.run(
            () -> MainPipeline.runPipeline(truthFilePath, pipelineType, dataType, mlModel, thresholdPanel.getSplit(), strategy),
            error -> {
                if (error.isPresent()) {
                    showErrorMessage("Pipeline Thread failed", error.get());
                } else {
                    JOptionPane.showMessageDialog(this, "The pipeline finished successfully", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                }
                closeWindow();
            }
        );
        setEnabled(false);
        updateStatus();
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

    private void onStrategyChange(StrategyType newStrategy) {
        switch (newStrategy) {
            case PERCENTAGE_SPLIT -> populateThresholdPanels(true);
            case TEN_FOLD -> populateThresholdPanels(false);
            default -> throw new IllegalArgumentException("Unknown strategy %s".formatted(newStrategy));
        }
        strategy = newStrategy;
    }

    private void showErrorMessage(@Nonnull String message, @Nullable Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

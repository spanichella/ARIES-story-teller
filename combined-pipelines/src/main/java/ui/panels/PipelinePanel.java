package ui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import types.PipelineType;
import ui.utilities.UIHelpers;

public final class PipelinePanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = 862256006596193220L;

    private final JComboBox<String> comboBox;

    public PipelinePanel(@Nonnull Consumer<? super PipelineType> onPipelineChange, @Nonnull Runnable onUpdate) {
        super("[Step 2]");

        comboBox = UIHelpers.getTranslatableComboBox(
            new PipelineType[] {null, PipelineType.ML, PipelineType.DL}, onPipelineChange, onUpdate);

        add(buildCenterPanel(), BorderLayout.CENTER);
        add(UIHelpers.createSeparator(), BorderLayout.PAGE_END);
    }

    private JPanel buildCenterPanel() {
        JPanel panel = UIHelpers.createPanel(new GridLayout(3, 1));
        panel.add(getTitleLabel());
        panel.add(UIHelpers.getLabel("Select a Pipeline"));
        panel.add(comboBox);
        return panel;
    }

    public PipelineType getSelectedItem() {
        return UIHelpers.fromDescriptionOrEmpty((String) comboBox.getSelectedItem(), PipelineType.values());
    }
}

package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import types.PipelineType;

final class PipelinePanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = 862256006596193220L;

    private final JComboBox<String> comboBox;

    PipelinePanel(@Nonnull Consumer<? super PipelineType> onPipelineChange, @Nonnull Runnable onUpdate) {
        super("[Step 3]");

        comboBox = UIHelpers.getTranslatableComboBox(
            new PipelineType[] { null, PipelineType.ML, PipelineType.DL },
            UIHelpers::getDescriptionOrEmpty, onPipelineChange, onUpdate);

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

    PipelineType getSelectedItem() {
        return UIHelpers.fromDescriptionOrEmpty((String) comboBox.getSelectedItem(), PipelineType.values());
    }

    void addDL() {
        String dlDescription = PipelineType.DL.getDescription();
        if (IntStream.range(0, comboBox.getItemCount())
            .mapToObj(comboBox::getItemAt)
            .noneMatch(value -> value.equals(dlDescription))) {
            comboBox.insertItemAt(dlDescription, comboBox.getItemCount());
        }
    }

    void removeDL() {
        comboBox.setSelectedItem(PipelineType.ML.getDescription());
        comboBox.removeItem(PipelineType.DL.getDescription());
    }
}

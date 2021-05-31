package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
            PipelinePanel::translatePipelineType, onPipelineChange, onUpdate);

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
        return translatePipelineText((String) comboBox.getSelectedItem());
    }

    void addDL() {
        if (IntStream.range(0, comboBox.getItemCount())
            .mapToObj(comboBox::getItemAt)
            .noneMatch(value -> value.equals("DL"))) {
            comboBox.insertItemAt("DL", comboBox.getItemCount());
        }
    }

    void removeDL() {
        comboBox.setSelectedItem("ML");
        comboBox.removeItem("DL");
    }

    private static String translatePipelineType(@Nullable PipelineType pipelineType) {
        if (pipelineType == null) {
            return UIHelpers.EMPTY_TEXT;
        }
        return switch (pipelineType) {
            case ML -> "ML";
            case DL -> "DL";
        };
    }

    @Nullable
    private static PipelineType translatePipelineText(@Nullable String pipelineText) {
        if (pipelineText == null) {
            return null;
        }
        return switch (pipelineText) {
            case UIHelpers.EMPTY_TEXT -> null;
            case "ML" -> PipelineType.ML;
            case "DL" -> PipelineType.DL;
            default -> throw new IllegalArgumentException("Unknown pipelineText: %s".formatted(pipelineText));
        };
    }
}

package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JPanel;
import types.DataType;

final class ContentTypePanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = 6735293525362364632L;

    ContentTypePanel(@Nonnull Runnable onUpdate, @Nonnull Consumer<? super DataType> onDataTypeChange) {
        super("[Step 2]");
        add(buildCenterPanel(onUpdate, onDataTypeChange), BorderLayout.CENTER);
        add(UIHelpers.createSeparator(), BorderLayout.PAGE_END);
    }

    private JPanel buildCenterPanel(@Nonnull Runnable onUpdate, @Nonnull Consumer<? super DataType> onDataTypeChange) {
        JPanel centerPanel = UIHelpers.createPanel(new GridLayout(3, 1));
        centerPanel.add(getTitleLabel());
        centerPanel.add(UIHelpers.getLabel("Select Content Type"));
        centerPanel.add(UIHelpers.getTranslatableComboBox(
            new DataType[]{null, DataType.USER_REVIEWS, DataType.REQUIREMENT_SPECIFICATIONS},
            ContentTypePanel::translateDataType, onDataTypeChange, onUpdate));
        return centerPanel;
    }

    private static String translateDataType(@Nullable DataType dataType) {
        if (dataType == null) {
            return UIHelpers.EMPTY_TEXT;
        }
        return switch (dataType) {
            case REQUIREMENT_SPECIFICATIONS -> "Requirement-Specifications";
            case USER_REVIEWS -> "User-Reviews";
        };
    }
}

package ui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import types.StrategyType;
import ui.utilities.UIHelpers;

public final class StrategyPanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = 321331743488681896L;

    private final JLabel subTitleLabel;
    private final JComboBox<String> comboBox;

    public StrategyPanel(@Nonnull Consumer<? super StrategyType> onStrategyChange, @Nonnull Runnable onUpdate) {
        super("[Step 4]");

        subTitleLabel = UIHelpers.getLabel("Select Strategy");
        comboBox = UIHelpers.getTranslatableComboBox(
            new StrategyType[] {null, StrategyType.TEN_FOLD, StrategyType.PERCENTAGE_SPLIT}, onStrategyChange, onUpdate);

        add(buildCenterPanel(), BorderLayout.CENTER);
        add(UIHelpers.createSeparator(), BorderLayout.PAGE_END);
    }

    private JPanel buildCenterPanel() {
        JPanel panel = UIHelpers.createPanel(new GridLayout(3, 1));
        panel.add(getTitleLabel());
        panel.add(subTitleLabel);
        panel.add(comboBox);
        return panel;
    }

    @Override
    public void setItemsVisible(boolean visible) {
        super.setItemsVisible(visible);
        subTitleLabel.setVisible(visible);
        comboBox.setVisible(visible);
    }
}

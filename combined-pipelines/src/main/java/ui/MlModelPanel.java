package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

final class MlModelPanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = 6612775420664586742L;

    private final JLabel subTitleLabel;
    private final JComboBox<String> comboBox;

    private static final String[] mlModelArray = {
        UIHelpers.EMPTY_TEXT, "J48", "PART", "NaiveBayes", "IBk", "OneR", "SMO",
        "Logistic", "AdaBoostM1", "LogitBoost",
        "DecisionStump", "LinearRegression",
        "RegressionByDiscretization",
        };

    MlModelPanel(@Nonnull Consumer<? super String> onModelChange, @Nonnull Runnable onUpdate) {
        super("[Step 4]");

        subTitleLabel = UIHelpers.getLabel("Select Method");
        comboBox = UIHelpers.getComboBox(mlModelArray, onModelChange, onUpdate);

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
    void setItemsVisible(boolean visible) {
        super.setItemsVisible(visible);
        subTitleLabel.setVisible(visible);
        comboBox.setVisible(visible);
    }
}

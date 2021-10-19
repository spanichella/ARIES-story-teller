package ui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import types.MlModelType;
import ui.utilities.UIHelpers;

public final class MlModelPanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = 6612775420664586742L;

    private final JLabel subTitleLabel;
    private final JComboBox<String> comboBox;

    private static final MlModelType[] mlModelArray = {
        null, MlModelType.J48, MlModelType.PART, MlModelType.NAIVE_BAYES, MlModelType.IB_K, MlModelType.ONE_R, MlModelType.SMO,
        MlModelType.LOGISTIC, MlModelType.ADA_BOOST_M1, MlModelType.LOGIT_BOOST, MlModelType.DECISION_STUMP, MlModelType.LINEAR_REGRESSION,
        MlModelType.REGRESSION_BY_DISCRETIZATION,
        };

    public MlModelPanel(@Nonnull Consumer<? super MlModelType> onModelChange, @Nonnull Runnable onUpdate) {
        super("[Step 3]");

        subTitleLabel = UIHelpers.getLabel("Select Method");
        comboBox = UIHelpers.getTranslatableComboBox(mlModelArray, onModelChange, onUpdate);

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

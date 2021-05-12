package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.Serial;
import java.math.BigDecimal;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

final class ThresholdPanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = 321331743488681896L;

    @Nonnull private final JLabel subTitleLabel;
    @Nonnull private final JLabel valueLabel;
    @Nonnull private final JLabel minValueLabel;
    @Nonnull private final JLabel maxValueLabel;
    @Nonnull private final JSlider thresholdSlider;
    @Nonnull private BigDecimal split = BigDecimal.valueOf(5, 1);

    ThresholdPanel() {
        super("[Step 6]");

        subTitleLabel = UIHelpers.getLabel("Set Size of Training-Set");
        valueLabel = UIHelpers.getLabel("value: 0.5");
        minValueLabel = UIHelpers.getLabel("0.1");
        maxValueLabel = UIHelpers.getLabel("1.0");
        thresholdSlider = buildSlider();

        add(buildCenterPanel(), BorderLayout.CENTER);
        add(UIHelpers.createSeparator(), BorderLayout.PAGE_END);
    }

    private JSlider buildSlider() {
        JSlider slider = new JSlider();
        slider.setMinorTickSpacing(1);
        slider.setBackground(DefaultColors.BACKGROUND);
        slider.addChangeListener(e -> {
            split = getSplitValueFromEvent(e);
            valueLabel.setText(UIHelpers.toTitle("value: " + split.toPlainString()));
        });
        return slider;
    }

    private JPanel buildCenterPanel() {
        JPanel horizontalSplitter = UIHelpers.createPanel(new GridLayout(0, 3));
        horizontalSplitter.add(minValueLabel);
        horizontalSplitter.add(valueLabel);
        horizontalSplitter.add(maxValueLabel);
        JPanel panel = UIHelpers.createPanel(new GridLayout(4, 1));
        panel.add(getTitleLabel());
        panel.add(subTitleLabel);
        panel.add(horizontalSplitter);
        panel.add(thresholdSlider);
        return panel;
    }

    @Override
    void setItemsVisible(boolean visible) {
        super.setItemsVisible(visible);
        subTitleLabel.setVisible(visible);
        valueLabel.setVisible(visible);
        minValueLabel.setVisible(visible);
        maxValueLabel.setVisible(visible);
        thresholdSlider.setVisible(visible);
    }

    @Nonnull
    BigDecimal getSplit() {
        return split;
    }

    private static BigDecimal getSplitValueFromEvent(ChangeEvent event) {
        int rawValue = ((JSlider) event.getSource()).getValue();
        return BigDecimal.valueOf(Math.max(rawValue, 10), 2);
    }
}

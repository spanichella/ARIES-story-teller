package ui;

import java.io.Serial;
import javax.annotation.Nonnull;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract class CompletablePanel extends JPanel {
    @Serial
    private static final long serialVersionUID = -3208183439207582395L;

    @Nonnull private final String title;
    @Nonnull private final JLabel titleLabel;

    CompletablePanel(@Nonnull String title) {
        this.title = title;
        titleLabel = UIHelpers.getLabel(title);
    }

    void markDone(boolean done) {
        titleLabel.setText(UIHelpers.toTitle(title, done));
    }

    void setItemsVisible(boolean visible) {
        titleLabel.setVisible(visible);
    }

    protected JLabel getTitleLabel() {
        return titleLabel;
    }
}

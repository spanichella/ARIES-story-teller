package ui;

import java.awt.LayoutManager;
import javax.annotation.Nonnull;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class UIHelpers {
    static JLabel getLabel(String title) {
        return getLabel(title, true);
    }

    static JLabel getLabel(String title, boolean visible) {
        JLabel label = new JLabel(toTitle(title));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(DefaultColors.TEXT);
        label.setVisible(visible);
        return label;
    }

    static JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setBackground(DefaultColors.SEPARATOR);
        return separator;
    }

    static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DefaultColors.BACKGROUND);
        return panel;
    }

    static JPanel createPanel(@Nonnull LayoutManager layoutManager) {
        JPanel panel = createPanel();
        panel.setLayout(layoutManager);
        return panel;
    }

    static String toTitle(String title) {
        return toTitle(title, false);
    }

    static String toTitle(String title, boolean done) {
        StringBuilder sb = new StringBuilder("<html>").append(title);
        if (done) {
            sb.append(" <font color='#56f310'>DONE</font>");
        }
        return sb.append("</html>").toString();
    }
}

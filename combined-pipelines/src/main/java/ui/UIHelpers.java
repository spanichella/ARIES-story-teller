package ui;

import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

class UIHelpers {
    static final String EMPTY_TEXT = "Select";

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

    static <E> JComboBox<String> getTranslatableComboBox(E[] elements, Function<? super E, String> translator,
                                                          Consumer<? super E> listener, Runnable onUpdate) {
        List<String> translations = Arrays.stream(elements).map(translator).collect(Collectors.toUnmodifiableList());
        return getComboBox(translations.toArray(new String[0]), (String value) ->
            listener.accept(elements[translations.indexOf(value)]), onUpdate);
    }

    static JComboBox<String> getComboBox(String[] names, Consumer<? super String> listener, Runnable onUpdate) {
        JComboBox<String> comboBox = new JComboBox<>(names);
        comboBox.addItemListener((event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String value = (String) event.getItem();
                if (comboBox.getItemAt(0).equals(EMPTY_TEXT)) {
                    comboBox.removeItemAt(0);
                }
                listener.accept(value);
            }
            onUpdate.run();
        });
        ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        return comboBox;
    }

}

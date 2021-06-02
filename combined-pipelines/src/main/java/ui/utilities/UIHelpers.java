package ui.utilities;

import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import types.IDescribable;

public final class UIHelpers {
    private static final String EMPTY_TEXT = "Select";

    public static JLabel getLabel(String title) {
        JLabel label = new JLabel(toTitle(title));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(DefaultColors.TEXT);
        return label;
    }

    public static JPanel createSeparator() {
        JPanel separator = new JPanel();
        separator.setBackground(DefaultColors.SEPARATOR);
        return separator;
    }

    public static JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(DefaultColors.BACKGROUND);
        return panel;
    }

    public static JPanel createPanel(@Nonnull LayoutManager layoutManager) {
        JPanel panel = createPanel();
        panel.setLayout(layoutManager);
        return panel;
    }

    public static String toTitle(String title) {
        return toTitle(title, false);
    }

    public static String toTitle(String title, boolean done) {
        StringBuilder sb = new StringBuilder("<html>").append(title);
        if (done) {
            sb.append(" <font color='#56f310'>DONE</font>");
        }
        return sb.append("</html>").toString();
    }

    public static <E extends IDescribable> JComboBox<String> getTranslatableComboBox(
        E[] elements, Consumer<? super E> listener, Runnable onUpdate
    ) {
        List<String> translations = Arrays.stream(elements).map(e -> e == null ? EMPTY_TEXT : e.getDescription())
            .collect(Collectors.toUnmodifiableList());
        JComboBox<String> comboBox = new JComboBox<>(translations.toArray(new String[0]));
        comboBox.addItemListener((event) -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                String value = (String) event.getItem();
                if (comboBox.getItemAt(0).equals(EMPTY_TEXT)) {
                    comboBox.removeItemAt(0);
                }
                listener.accept(elements[translations.indexOf(value)]);
            }
            onUpdate.run();
        });
        ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        return comboBox;
    }

    @Nullable
    public static <T extends IDescribable> T fromDescriptionOrEmpty(@Nullable String description, T[] values) {
        if (description == null) {
            return null;
        }
        for (T type : values) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown PipelineType with description \"%s\"".formatted(description));
    }
}

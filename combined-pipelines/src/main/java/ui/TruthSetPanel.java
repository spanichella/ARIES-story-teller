package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.Serial;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

final class TruthSetPanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = -8088616060831842438L;
    private static final File DATASETS_FOLDER = new File("..", "datasets");

    TruthSetPanel(@Nonnull String title, @Nonnull Runnable onUpdate, Consumer<? super File> onFileSelected, Consumer<? super String> onFileSelectionFailed) {
        super(title);
        setLayout(new BorderLayout());

        add(UIHelpers.createSeparator(), BorderLayout.PAGE_START);
        add(buildCenterPanel(onUpdate, onFileSelected, onFileSelectionFailed), BorderLayout.CENTER);
        add(UIHelpers.createSeparator(), BorderLayout.PAGE_END);
    }

    private JPanel buildCenterPanel(Runnable onUpdate, Consumer<? super File> onFileSelected, Consumer<? super String> onFileSelectionFailed) {
        JPanel panel = UIHelpers.createPanel(new GridLayout(3, 1));
        panel.add(getTitleLabel());
        panel.add(UIHelpers.getLabel("Select a truth set to be analyzed by the algorithm"));

        JButton truthSetSelector = new JButton("Truth Set");
        truthSetSelector.addActionListener(e -> selectTruthSet(onUpdate, onFileSelected, onFileSelectionFailed));

        JPanel s1CenterPanel = UIHelpers.createPanel(new GridLayout(0, 3));
        s1CenterPanel.add(UIHelpers.createPanel());
        s1CenterPanel.add(truthSetSelector);
        panel.add(s1CenterPanel);
        return panel;
    }

    private void selectTruthSet(Runnable onUpdate, Consumer<? super File> onSuccess, Consumer<? super String> onError) {
        JFileChooser fileChooser = new JFileChooser();
        if (DATASETS_FOLDER.exists()) {
            fileChooser.setCurrentDirectory(DATASETS_FOLDER);
        }
        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            String extension = file.toString().substring(file.toString().lastIndexOf('.'));
            if (!extension.equals(".txt") && !extension.equals(".csv")) {
                onError.accept("Wrong filetype selected. Please select a .txt or .csv file");
            } else {
                onSuccess.accept(file);
            }
        }
        onUpdate.run();
    }
}

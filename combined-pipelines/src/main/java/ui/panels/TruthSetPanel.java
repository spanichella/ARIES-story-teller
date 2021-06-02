package ui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.Serial;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import ui.utilities.UIHelpers;

public final class TruthSetPanel extends CompletablePanel {
    @Serial
    private static final long serialVersionUID = -8088616060831842438L;
    private static final File DATASETS_FOLDER = new File("..", "datasets");

    public TruthSetPanel(@Nonnull Runnable onUpdate, Consumer<? super File> onFileSelected,
                         Consumer<? super String> onFileSelectionFailed) {
        super("[Step 1]");

        add(UIHelpers.createSeparator(), BorderLayout.PAGE_START);
        add(buildCenterPanel(onUpdate, onFileSelected, onFileSelectionFailed), BorderLayout.CENTER);
        add(UIHelpers.createSeparator(), BorderLayout.PAGE_END);
    }

    private JPanel buildCenterPanel(Runnable onUpdate, Consumer<? super File> onFileSelected,
                                    Consumer<? super String> onFileSelectionFailed) {
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

    private static void selectTruthSet(Runnable onUpdate, Consumer<? super File> onSuccess, Consumer<? super String> onError) {
        JFileChooser fileChooser = new JFileChooser(DATASETS_FOLDER.exists() ? DATASETS_FOLDER : null);
        // remove the default file filters
        for (FileFilter oldFilter : fileChooser.getChoosableFileFilters()) {
            fileChooser.removeChoosableFileFilter(oldFilter);
        }
        FileFilter filter = getExtensionFilter();
        fileChooser.setFileFilter(filter);
        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            if (filter.accept(file)) {
                onSuccess.accept(file);
            } else {
                onError.accept("Wrong filetype selected. Please select a .txt, .csv or .tsv file");
            }
        }
        onUpdate.run();
    }

    private static FileFilter getExtensionFilter() {
        var extensions = new String[] { "csv", "tsv", "txt" };
        var fullDescription = new StringBuilder("Data Files (");
        for (String extension : extensions) {
            fullDescription.append("*.").append(extension);
        }
        fullDescription.append(")");
        return new FileNameExtensionFilter(fullDescription.toString(), extensions);
    }
}

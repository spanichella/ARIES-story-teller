package ui;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

class UIHelpers {
    public static void showErrorMessage(Logger logger, String message, Throwable throwable) {
        showErrorMessage(logger, message, throwable, null);
    }

    public static void showErrorMessage(Logger logger, String message, Throwable throwable, Component parentComponent) {
        logger.log(Level.SEVERE, message, throwable);
        JOptionPane.showMessageDialog(parentComponent, message, message, JOptionPane.ERROR_MESSAGE);
    }
}

package amo.randomFilm.gui.util;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Dialogs {
    
    /**
     * Shows a Dialog with a Warning symbol and a given line of Text.
     * 
     * @param message
     *            the test to display
     */
    public static void showWarning(String message, Component comp) {
        Object[] options = { "OK" };
        JOptionPane.showOptionDialog(
                comp, // Component parentComponent,
                message, // Object message,
                "Warnung", // String title,
                JOptionPane.OK_OPTION, // int optionType,
                JOptionPane.WARNING_MESSAGE, // int messageType,
                new ImageIcon("images/warning.png"), // Icon icon,
                options, // Object[] options,
                options[0] // Object initialValue
                );
    }
    
    /**
     * Displays a Dialog consisting of String s and two buttons: yes, no
     * 
     * @param s
     *            - String to display
     * @return yes = 0; no = 1
     */
    public static boolean showYesNoDialog(String s, Component comp) {
        Object[] options = { "Ja",
                "Nein" };
        
        int i = JOptionPane.showOptionDialog(
                comp, // Component parentComponent,
                s, // Object message,
                "", // String title,
                JOptionPane.YES_NO_OPTION, // int optionType,
                JOptionPane.QUESTION_MESSAGE, // int messageType,
                new ImageIcon("./images/question.png"), // Icon icon,
                options, // Object[] options,
                options[1] // Object initialValue
                );
        
        if (i == 0) return true;
        else
            return false;
    }
    
    /**
     * Displays a Dialog consisting of String s and given buttons
     * 
     * @param s
     *            - String to display
     * @return yes = 0; no = 1
     */
    public static int showOptionsDialog(String s, Object[] options, Component comp) {
        // Object[] options = {"Ja", "Nein"};
        
        return JOptionPane.showOptionDialog(
                comp, // Component parentComponent,
                s, // Object message,
                "", // String title,
                JOptionPane.YES_NO_OPTION, // int optionType,
                JOptionPane.QUESTION_MESSAGE, // int messageType,
                new ImageIcon("./images/question.png"), // Icon icon,
                options, // Object[] options,
                options[1] // Object initialValue
                );
        
    }
    
    /**
     * Displays a Dialog consisting of String s and two buttons: yes, no
     * 
     * @param s
     *            - String to display
     * @return yes = 0; no = 1
     */
    public static boolean showStartFilmDialog(String filmName, String filmPath, Image filmIcon, Component comp) {
        Object[] options = { "Ja",
                "Nein" };
        
        int i = JOptionPane.showOptionDialog(
                comp, // Component parentComponent,
                "Film: " + filmName + " starten?" + "\n(" + filmPath + ")", // Object
                // message,
                "", // String title,
                JOptionPane.YES_NO_OPTION, // int optionType,
                JOptionPane.QUESTION_MESSAGE, // int messageType,
                new ImageIcon(filmIcon), // new
                // ImageIcon("./images/question.png"),
                // //Icon icon,
                options, // Object[] options,
                options[1] // Object initialValue
                );
        
        if (i == 0) return true;
        else
            return false;
    }
    
}

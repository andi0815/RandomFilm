package amo.randomFilm.gui.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;

public class Dialogs {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(Dialogs.class);
    
    /**
     * Shows a Dialog with a Warning symbol and a given line of Text.
     * 
     * @param message
     *            the test to display
     */
    public static void showWarning(String message, Component comp) {
        Object[] options = { "OK" };
        JOptionPane.showOptionDialog(comp, // Component parentComponent,
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
        Object[] options = { "Ja", "Nein" };
        
        int i = JOptionPane.showOptionDialog(comp, // Component parentComponent,
                s, // Object message,
                "", // String title,
                JOptionPane.YES_NO_OPTION, // int optionType,
                JOptionPane.QUESTION_MESSAGE, // int messageType,
                new ImageIcon("./images/question.png"), // Icon icon,
                options, // Object[] options,
                options[1] // Object initialValue
                );
        
        if (i == 0)
            return true;
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
        
        return JOptionPane.showOptionDialog(comp, // Component parentComponent,
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
    public static boolean showStartFilmDialog(String filmName, String filmPath, Image filmIcon, Component parent) {
        Object[] options = { GuiConstants.LABEL_START_RANDOM, GuiConstants.LABEL_CANCEL_RANDOM };
        int MAX_SIZE = 200;
        float scaleFactor = filmIcon.getWidth(null) / (float) filmIcon.getHeight(null);
        int newWidth, newHeight;
        if (filmIcon.getWidth(null) > filmIcon.getHeight(null)) {
            newWidth = MAX_SIZE;
            newHeight = (int) (MAX_SIZE / scaleFactor);
        } else {
            newWidth = (int) (MAX_SIZE * scaleFactor);
            newHeight = MAX_SIZE;
        }
        logger.debug("Scaling from: " + filmIcon.getWidth(null) + "," + filmIcon.getHeight(null) + " TO (SF="
                + scaleFactor + "): " + newWidth + "," + newHeight);
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(filmIcon, 0, 0, newWidth, newHeight, null);
        g.dispose();
        
        int i = JOptionPane.showOptionDialog(parent, // Component parentComponent,
                String.format(GuiConstants.LABEL_START_THIS_MOVIE, filmName, filmPath), // message,
                GuiConstants.LABEL_TITLE_START_RANDOM, // String title,
                JOptionPane.YES_NO_OPTION, // int optionType,
                JOptionPane.QUESTION_MESSAGE, // int messageType,
                new ImageIcon(resizedImage), // new
                options, // Object[] options,
                options[1] // Object initialValue
                );
        
        if (i == 0)
            return true;
        else
            return false;
    }
    
    public static void centerWindow(Window window) {
        // setze Fenster an den rechten rand
        Toolkit tk = Toolkit.getDefaultToolkit();
        Insets screenInsets = tk.getScreenInsets(window.getGraphicsConfiguration());
        Dimension screenDimension = tk.getScreenSize();
        logger.debug("Screen Insets: " + screenInsets);
        logger.debug("Screen Dimension: " + screenDimension);
        window.pack();
        int screenHeight = screenDimension.height - (screenInsets.bottom + screenInsets.top);
        int screenWidth = screenDimension.width - (screenInsets.left + screenInsets.right);
        window.setLocation( //
                screenInsets.left + ((screenWidth - window.getWidth()) / 2), //
                screenInsets.top + ((screenHeight - window.getHeight()) / 2) //
        );
        logger.error("Window-Loc X: " + (screenInsets.left + ((screenWidth - window.getWidth()) / 2)));
        logger.error("Window-Loc Y: " + (screenInsets.top + ((screenHeight - window.getHeight()) / 2)));
        
    }
    
}

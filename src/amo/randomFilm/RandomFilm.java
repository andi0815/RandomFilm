package amo.randomFilm;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;

import amo.randomFilm.configuration.Configuration;
import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.MainFrame;

public class RandomFilm {
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
        
        // SplashScreen splash = new SplashScreen();
        // splash.run();
        
        // initialize Configuration
        Configuration.getInstance();
        
        // OS Look-And-Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        // Toolkit tk = Toolkit.getDefaultToolkit();
        // Dimension d = tk.getScreenSize();
        // System.out.println("Screen Size: " + d);
        // System.out.println("Screen Resolution: " + tk.getScreenResolution());
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.init(GuiConstants.WIDTH_OF_FRAME);
        mainFrame.setMinimumSize(new Dimension(GuiConstants.WIDTH_MIN_OF_FRAME, GuiConstants.HEIGHT_MIN_OF_FRAME));
        mainFrame.setExtendedState(JFrame.MAXIMIZED_VERT);
        mainFrame.setVisible(true);
        
        mainFrame.setResizable(true);
        // Insets screenInsets = tk.getScreenInsets(mainFrame.getGraphicsConfiguration());
        // System.out.println("Insets: " + screenInsets);
        // System.out.println("Insets right: " + screenInsets.right);
        // System.out.println("Insets left: " + screenInsets.left);
        // System.out.println("Insets top: " + screenInsets.top);
        // System.out.println("Insets bottom: " + screenInsets.bottom);
        //        
        // mainFrame.setPreferredSize(new Dimension((int) mainFrame.getSize().getWidth() +
        // screenInsets.right,
        // (int) mainFrame.getSize().getHeight() + screenInsets.bottom));
        
    }
}

package amo.randomFilm;

import java.awt.Dimension;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;

import amo.randomFilm.configuration.Configuration;
import amo.randomFilm.gui.MainFrame;
import amo.randomFilm.gui.SplashScreen;

public class RandomFilm {
    
    public static final int frameWidth = 500;
    // public static final int frameHeight = 600;
    public static final int frameHeightDelta = 50;
    
    public static final boolean DEBUG = true;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
        
        SplashScreen splash = new SplashScreen();
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
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.init(frameWidth, frameHeightDelta);
        mainFrame.setMinimumSize(new Dimension(400, 500));
        mainFrame.setVisible(true);
        
    }
    
}

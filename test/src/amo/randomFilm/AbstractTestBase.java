package amo.randomFilm;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;

/**
 * Base Class for Unit Tests
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 03.10.2011
 */
public abstract class AbstractTestBase {
    
    @BeforeClass
    public static void setup() {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
    }
    
    /**
     * Shows a given image for a given amount of milliseconds.
     * 
     * @param image
     *            image to show
     * @param millies
     *            milliseconds to show frame
     * @throws InterruptedException
     *             in case something went wrong
     */
    public static void showImage(final Image image, long millies) throws InterruptedException {
        JFrame frame = new JFrame();
        JPanel imagePanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(image, //
                        10, 10, image.getWidth(null), image.getHeight(null), //
                        0, 0, image.getWidth(null), image.getHeight(null), null);
            }
            
        };
        imagePanel.setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));
        frame.getContentPane().add(imagePanel);
        frame.pack();
        frame.setVisible(true);
        Thread.sleep(millies);
    }
}

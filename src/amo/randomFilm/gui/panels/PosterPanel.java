package amo.randomFilm.gui.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * Simple Panel that draws an image as big as possible, but preserves its aspect ratio.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 15.10.2011
 */
public class PosterPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(PosterPanel.class);
    
    /** default initial size of this panel */
    private static final Dimension INITIAL_SIZE = new Dimension(60, 80);
    
    // FIXME: set default image
    private static Image NO_IMAGE_AVAILABLE = null;
    static {
        // URL resource = PosterPanel.class.getClassLoader().getResource("images/logo.jpg");
        // NO_IMAGE_AVAILABLE = new ImageIcon(resource, "").getImage();
        NO_IMAGE_AVAILABLE = new JFrame().getToolkit().getImage("images/logo-big.jpg");
    }
    
    private Image image;
    
    private int imageHeight;
    
    private int imageWidth;
    
    public PosterPanel(Image image) {
        setPreferredSize(INITIAL_SIZE);
        // setMinimumSize(new Dimension(50, 50));
        setImage(image);
    }
    
    /**
     * Set new {@link Image} to draw.
     * 
     * @param image
     *            new image
     */
    public void setImage(Image image) {
        if (image != null) {
            this.image = image;
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
        } else {
            this.image = NO_IMAGE_AVAILABLE;
            System.out.println(NO_IMAGE_AVAILABLE);
        }
        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        // calculate current aspect ratio
        double dx = (double) getWidth() / imageWidth;
        double dy = (double) getHeight() / imageHeight;
        int currentWidth, currentHeight;
        
        // determine new size, but keep aspect ratio
        if (dx < dy) {
            logger.debug("PAINT - dx: " + dx + " / dy: " + dy + " \t-->SCALE by X");
            currentWidth = getWidth();
            currentHeight = (int) (imageHeight * dx);
        } else {
            logger.debug("PAINT - dx: " + dx + " / dy: " + dy + " \t-->SCALE by Y");
            currentWidth = (int) (imageWidth * dy);
            currentHeight = getHeight();
        }
        
        // draw
        g.drawImage(image, 0, 0, currentWidth, currentHeight, 0, 0, image.getWidth(null), image.getHeight(null), null);
        
    }
}

package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.model.Movie;

/**
 * Simple Panel that draws an image as big as possible, but preserves its aspect ratio.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 15.10.2011
 */
public class PosterPanel extends JPanel implements Updateable {
    
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(PosterPanel.class);
    
    /** default initial size of this panel */
    private static final Dimension INITIAL_SIZE = new Dimension(75, 80);
    
    private static Image LOADING_IMAGE = null;
    static {
        try {
            File imagefile = new File(GuiConstants.IMAGEPATH_POSTER_LOADING);
            LOADING_IMAGE = ImageIO.read(imagefile);
        } catch (IOException e) {
            logger.warn("could not load image: " + GuiConstants.IMAGEPATH_POSTER_LOADING);
        }
    }
    
    private Image image;
    
    private int imageHeight;
    
    private int imageWidth;
    
    private boolean centerVertically = true;
    
    public PosterPanel(Image image) {
        this.setMinimumSize(INITIAL_SIZE);
        this.setImage(image);
        this.setBackground(GuiConstants.BG_COLOR);
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
        } else {
            this.image = LOADING_IMAGE;
            logger.debug("LOADING-IMAGE DIMENSION: " + LOADING_IMAGE.getWidth(null) + ","
                    + LOADING_IMAGE.getHeight(null));
        }
        logger.debug("setting new image: " + this.image);
        if (image != null) {
            this.imageWidth = this.image.getWidth(null);
            this.imageHeight = this.image.getHeight(null);
        } else {
            this.imageWidth = 1;
            this.imageHeight = 1;
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        // calculate current aspect ratio
        double dx = (double) this.getWidth() / this.imageWidth;
        double dy = (double) this.getHeight() / this.imageHeight;
        int currentWidth, currentHeight;
        
        // determine new size, but keep aspect ratio
        if (dx < dy) {
            // logger.debug("PAINT - dx: " + dx + " / dy: " + dy + " \t-->SCALE by X");
            currentWidth = this.getWidth();
            currentHeight = (int) (this.imageHeight * dx);
        } else {
            // logger.debug("PAINT - dx: " + dx + " / dy: " + dy + " \t-->SCALE by Y");
            currentWidth = (int) (this.imageWidth * dy);
            currentHeight = this.getHeight();
        }
        
        // determine upper corner
        int y = 0;
        if (this.centerVertically) {
            if (this.getHeight() > currentHeight) {
                y = (this.getHeight() - currentHeight) / 2;
            }
        }
        
        // draw
        g.drawImage(this.image, 0, y, currentWidth, currentHeight + y, 0, 0, this.image.getWidth(null), this.image
                .getHeight(null), null);
        
    }
    
    @Override
    public void update(Movie movie) {
        if (movie != null) {
            this.setImage(movie.getMovieImage());
        } else {
            this.setImage(null);
        }
        this.repaint();
    }
    
    public void setCenterVertically(boolean centerVertically) {
        this.centerVertically = centerVertically;
    }
    
    @Override
    public Dimension getPreferredSize() {
        // calculate current aspect ratio
        double dy = (double) this.getHeight() / this.imageHeight;
        int currentWidth, currentHeight;
        // logger.debug("PAINT - dx: " + dx + " / dy: " + dy + " \t-->SCALE by Y");
        currentWidth = (int) (this.imageWidth * dy);
        currentHeight = this.getHeight();
        
        return new Dimension(currentWidth, currentHeight);
    }
    
}

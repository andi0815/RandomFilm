package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.Movie;

/**
 * Simple Panel that draws an image as big as possible, but preserves its aspect ratio.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 15.10.2011
 */
public class PosterPanel extends JPanel implements Updateable {
    
    private static final String PATH_IMAGES_LOADING = "images/LOADING.jpg";
    
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(PosterPanel.class);
    
    /** default initial size of this panel */
    private static final Dimension INITIAL_SIZE = new Dimension(75, 80);
    
    private static Image LOADING_IMAGE = null;
    static {
        try {
            File imagefile = new File(PATH_IMAGES_LOADING);
            LOADING_IMAGE = ImageIO.read(imagefile);
        } catch (IOException e) {
            logger.warn("could not load image: " + PATH_IMAGES_LOADING);
        }
    }
    
    private Image image;
    
    private int imageHeight;
    
    private int imageWidth;
    
    public PosterPanel(Image image) {
        this.setPreferredSize(INITIAL_SIZE);
        // setMinimumSize(new Dimension(50, 50));
        this.setImage(image);
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
            logger.debug("PAINT - dx: " + dx + " / dy: " + dy + " \t-->SCALE by X");
            currentWidth = this.getWidth();
            currentHeight = (int) (this.imageHeight * dx);
        } else {
            logger.debug("PAINT - dx: " + dx + " / dy: " + dy + " \t-->SCALE by Y");
            currentWidth = (int) (this.imageWidth * dy);
            currentHeight = this.getHeight();
        }
        
        // draw
        g.drawImage(this.image, 0, 0, currentWidth, currentHeight, 0, 0, this.image.getWidth(null),
                this.image.getHeight(null), null);
        
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
}
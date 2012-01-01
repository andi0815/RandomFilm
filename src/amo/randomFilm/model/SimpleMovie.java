package amo.randomFilm.model;

import java.awt.Image;
import java.util.List;

import amo.randomFilm.gui.GuiConstants;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 23.10.2011
 */
public class SimpleMovie implements Movie {
    
    private String title;
    
    private Image image;
    
    public SimpleMovie(String title, boolean isImageAvailable) {
        this.title = title;
        this.setImageAvailable(isImageAvailable);
    }
    
    public void setImageAvailable(boolean isAvailable) {
        if (isAvailable) {
            this.image = GuiConstants.IMAGE_POSTER_LOADING;
        } else {
            this.image = GuiConstants.IMAGE_POSTER_NO_AVAIL;
        }
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMovieTitle() {
        return this.title;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double getMovieRating() {
        return UnknownTypes.DOUBLE;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int getMovieRuntime() {
        return UnknownTypes.INT;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Image getMovieImage() {
        return this.image;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMovieShortDescription() {
        return UnknownTypes.STRING;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getMovieYear() {
        return UnknownTypes.STRING;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMovieGenres() {
        return null;
    }
    
}

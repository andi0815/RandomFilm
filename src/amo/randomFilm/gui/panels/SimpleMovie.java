package amo.randomFilm.gui.panels;

import java.awt.Image;
import java.util.List;

import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 23.10.2011
 */
public class SimpleMovie implements Movie {
    
    private String title;
    
    public SimpleMovie(String title) {
        this.title = title;
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
        return UnknownTypes.IMAGE;
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

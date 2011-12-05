/**
 * 
 */
package amo.randomFilm.gui.util;

import org.apache.log4j.Logger;

import amo.randomFilm.model.Movie;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 04.12.2011
 * 
 */
public class BackgroundMovieDataLoader extends Thread {
    
    private static final Logger logger = Logger.getLogger(BackgroundMovieDataLoader.class);
    
    private final Movie movie;
    private UpdatableView viewToUpdate;
    
    public BackgroundMovieDataLoader(Movie movie, UpdatableView viewToUpdate) {
        this.movie = movie;
        this.viewToUpdate = viewToUpdate;
    }
    
    @Override
    public void run() {
        movie.getMovieTitle();
        movie.getMovieGenres();
        movie.getMovieImage();
        movie.getMovieRating();
        movie.getMovieShortDescription();
        movie.getMovieYear();
        movie.getMovieRuntime();
        
        viewToUpdate.updateData(movie);
    }
}

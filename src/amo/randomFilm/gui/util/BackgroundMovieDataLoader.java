/**
 * 
 */
package amo.randomFilm.gui.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import amo.randomFilm.model.Movie;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 04.12.2011
 * 
 */
public class BackgroundMovieDataLoader {
    
    // TODO: make configurable ?!
    private static final int MAX_THREADS = 8;
    
    private static final Logger logger = Logger.getLogger(BackgroundMovieDataLoader.class);
    
    private static BackgroundMovieDataLoader instance = null;
    
    private static ExecutorService threadPool;
    
    private BackgroundMovieDataLoader() {
        threadPool = Executors.newFixedThreadPool(MAX_THREADS);
    }
    
    public static BackgroundMovieDataLoader getInstance() {
        if (instance == null) {
            instance = new BackgroundMovieDataLoader();
        }
        return instance;
    }
    
    public void loadAndUpdateMovie(Movie movie, UpdatableView viewToUpdate) {
        threadPool.execute(new MovieLoader(movie, viewToUpdate));
    }
    
    private class MovieLoader extends Thread {
        
        private Movie movie;
        private UpdatableView viewToUpdate;
        
        public MovieLoader(Movie movie, UpdatableView viewToUpdate) {
            this.movie = movie;
            this.viewToUpdate = viewToUpdate;
        }
        
        @Override
        public void run() {
            this.movie.getMovieTitle();
            this.movie.getMovieGenres();
            this.movie.getMovieImage();
            this.movie.getMovieRating();
            this.movie.getMovieShortDescription();
            this.movie.getMovieYear();
            this.movie.getMovieRuntime();
            
            this.viewToUpdate.updateData(this.movie);
        }
    }
}

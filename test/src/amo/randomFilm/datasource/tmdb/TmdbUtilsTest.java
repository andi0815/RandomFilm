package amo.randomFilm.datasource.tmdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import amo.randomFilm.datasource.exception.TmdbException;
import amo.randomFilm.datasource.tmdb.data.TmdbMovie;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * 
 */
public class TmdbUtilsTest {
    
    private static final Logger logger = Logger.getLogger(TmdbUtilsTest.class);
    
    public static void main(String[] args) throws TmdbException, IOException {
        
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
        
        TmdbFacade session = TmdbFacade.getInstance();
        
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String filmTitle = null;
        System.out.println("Type Film to find: ");
        List<TmdbMovie> movies = null;
        while (!(filmTitle = input.readLine()).equals("q")) {
            try {
                movies = session.searchMovie(filmTitle);
            } catch (TmdbException e) {
                logger.log(Level.ERROR, e);
            }
            if (movies != null && movies.size() > 0) {
                TmdbMovie movie = movies.get(0);
                session.getInfo(movie.getId());
                System.err.println("OHNE: " + movies.get(0));
                System.err.println("MIT:  " + movie);
            }
            System.out.println("Type Film to find: ");
        }
        
        // needed for write access
        // session.getAuthToken();
        
        System.out.println("EXITING ...");
    }
}

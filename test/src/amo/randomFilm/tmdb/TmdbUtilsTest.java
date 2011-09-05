/**
 * 
 */
package amo.randomFilm.tmdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import amo.randomFilm.tmdb.exception.TmdbException;

/**
 * @author andi
 */
public class TmdbUtilsTest {
    
    private static final Logger logger = Logger.getLogger(TmdbUtilsTest.class);
    
    public static void main(String[] args) throws TmdbException, IOException {
        
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
        
        TmdbSession session = TmdbSession.getInstance();
        
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String filmTitle = null;
        System.out.println("Type Film to find: ");
        while (!(filmTitle = input.readLine()).equals("q")) {
            try {
                session.searchMovie(filmTitle);
            } catch (TmdbException e) {
                logger.log(Level.ERROR, e);
            }
            System.out.println("Type Film to find: ");
        }
        
        // needed for write access
        // session.getAuthToken();
        
        System.out.println("EXITING ...");
    }
    
}

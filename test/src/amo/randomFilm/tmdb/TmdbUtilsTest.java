/**
 * 
 */
package amo.randomFilm.tmdb;

import org.apache.log4j.BasicConfigurator;

import amo.randomFilm.tmdb.exception.TmdbException;

/**
 * @author andi
 */
public class TmdbUtilsTest {
    
    public static void main(String[] args) throws TmdbException {
        
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
        
        TmdbSession session = TmdbSession.getInstance();
        session.getAuthToken();
        
        session.searchMovie("Matrix");
        
    }
    
}

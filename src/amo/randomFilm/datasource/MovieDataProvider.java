package amo.randomFilm.datasource;

import java.util.List;

import amo.randomFilm.datasource.exception.MovieDataProviderException;

/**
 * This interface defines a content provider for movie data.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 09.10.2011
 */
public interface MovieDataProvider {
    
    public List<? extends Movie> searchMovie(String title) throws MovieDataProviderException;
    
}

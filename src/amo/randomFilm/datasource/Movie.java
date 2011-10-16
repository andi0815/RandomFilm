/**
 * 
 */
package amo.randomFilm.datasource;

import java.awt.Image;
import java.util.List;

/**
 * Interface for movies.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 29.09.2011
 * 
 */
public interface Movie {
    
    /**
     * @return the movie's title.
     */
    public String getMovieTitle();
    
    /**
     * @return the movie's rating as a floating point number between: [0,1].
     */
    public double getMovieRating();
    
    /**
     * @return the movie's runtime length in minutes.
     */
    public int getMovieRuntime();
    
    /**
     * @return the movie's image / poster.
     */
    public Image getMovieImage();
    
    /**
     * @return the movie's short description.
     */
    public String getMovieShortDescription();
    
    /**
     * @return the movie's production year.
     */
    public String getMovieYear();
    
    /**
     * @return A list of genres for this movie, or <code>null</code>, if there are no genres
     *         available.
     */
    public List<String> getMovieGenres();
    
}

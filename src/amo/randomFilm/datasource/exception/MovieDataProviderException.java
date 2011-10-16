package amo.randomFilm.datasource.exception;

/**
 * This is a wrapper for exceptions caught while requesting or processing content of movie data
 * providers.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 09.10.2011
 */
public class MovieDataProviderException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public MovieDataProviderException() {
        super();
    }
    
    public MovieDataProviderException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MovieDataProviderException(String message) {
        super(message);
    }
    
    public MovieDataProviderException(Throwable cause) {
        super(cause);
    }
    
}

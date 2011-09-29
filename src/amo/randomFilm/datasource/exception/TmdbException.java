/**
 * 
 */
package amo.randomFilm.datasource.exception;

/**
 * @author andi
 */
public class TmdbException extends Exception {
    
    public TmdbException() {
        super();
    }
    
    public TmdbException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public TmdbException(String message) {
        super(message);
    }
    
    public TmdbException(Throwable cause) {
        super(cause);
    }
    
}

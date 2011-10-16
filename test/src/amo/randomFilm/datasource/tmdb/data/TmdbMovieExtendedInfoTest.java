/**
 * 
 */
package amo.randomFilm.datasource.tmdb.data;

import junit.framework.Assert;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.exception.MovieDataProviderException;
import amo.randomFilm.datasource.tmdb.TmdbFacade;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 29.09.2011
 * 
 */
public class TmdbMovieExtendedInfoTest extends AbstractTestBase {
    
    @Test
    public void testMovie() throws MovieDataProviderException {
        
        TmdbMovieExtendedInfo info = TmdbFacade.getInfo("603");
        // System.out.println("EXT-INFO: " + info);
        Assert.assertEquals(136, info.getRuntime());
        Assert.assertEquals("[Action, Abenteuer, Science Fiction, Thriller]", info.getGenres().toString());
        
    }
    
}

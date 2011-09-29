/**
 * 
 */
package amo.randomFilm.datasource.tmdb.data;

import java.util.ArrayList;
import java.util.List;

import amo.randomFilm.datasource.UnknownTypes;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 29.09.2011
 * 
 */
public class TmdbMovieExtendedInfo {
    
    private int runtime = UnknownTypes.INT;
    
    // <categories>
    // <category type="genre" name="Crime" url="http://themoviedb.org/genre/crime" id="80"/>
    // <category type="genre" name="Drama" url="http://themoviedb.org/genre/drama" id="18"/>
    // <category type="genre" name="Thriller" url="http://themoviedb.org/genre/thriller" id="53"/>
    // </categories>
    private List<Category> categories = null;
    
    public int getRuntime() {
        return runtime;
    }
    
    public List<String> getGenres() {
        ArrayList<String> listOfGenres = new ArrayList<String>();
        if (categories != null) {
            for (Category cat : categories) {
                if (cat.type.equals("genre")) {
                    listOfGenres.add(cat.name);
                }
            }
        }
        return listOfGenres;
    }
    
    class Category {
        
        private String type = UnknownTypes.STRING;
        
        private String name = UnknownTypes.STRING;
        
    }
    
}

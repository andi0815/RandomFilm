package amo.randomFilm.datasource.tmdb.data;

import java.util.ArrayList;
import java.util.List;

import amo.randomFilm.model.UnknownTypes;

/**
 * Manages Extended {@link TmdbMovie} infos, such as: runtime and genres.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 29.09.2011
 * 
 */
public class TmdbMovieExtendedInfo extends GsonObject {
    
    private int runtime = UnknownTypes.INT;
    
    private List<Category> genres = null;
    
    public int getRuntime() {
        return runtime;
    }
    
    public List<String> getGenres() {
        ArrayList<String> listOfGenres = new ArrayList<String>();
        if (genres != null) {
            for (Category cat : genres) {
                if (cat.type.equals("genre")) {
                    listOfGenres.add(cat.name);
                }
            }
        }
        return listOfGenres;
    }
    
    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        representation.append("[").append(this.getClass().getSimpleName()).append(": ");
        addIfNotEmpty(representation, "runtime", runtime);
        addIfNotEmpty(representation, "genres", getGenres().toString());
        return representation.append("]").toString();
    }
    
    /**
     * Utility Class that manages a single genre.
     * 
     * @author Andreas Monger (andreas.monger@gmail.com)
     * @date 03.10.2011
     */
    class Category {
        
        private String type = UnknownTypes.STRING;
        
        private String name = UnknownTypes.STRING;
        
    }
    
}

/**
 * 
 */
package amo.randomFilm.tmdb.data;

import org.apache.log4j.Logger;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 05.09.2011
 */
public class Movie {
    
    private static final double UNKNOWN_DOUBLE   = -1d;
    
    private static final int    UNKNOWN_INT      = -1;
    
    private static final String UNKNOWN          = "UNKNOWN";
    
    private static final Logger logger           = Logger.getLogger(Movie.class);
    
    private final double        score            = UNKNOWN_DOUBLE;
    
    private final double        popularity       = UNKNOWN_DOUBLE;
    
    private final boolean       translated       = false;
    
    private final boolean       adult            = false;
    
    private String              language         = UNKNOWN;
    
    private String              original_name    = UNKNOWN;
    private String              name             = UNKNOWN;
    private String              alternative_name = UNKNOWN;
    private String              type             = UNKNOWN;
    private String              id               = UNKNOWN;
    private String              imdb_id          = UNKNOWN;
    private String              url              = UNKNOWN;
    private int                 votes            = UNKNOWN_INT;
    private double              rating           = UNKNOWN_DOUBLE;
    private String              certification    = UNKNOWN;
    private String              overview         = UNKNOWN;
    private String              released         = UNKNOWN;
    // private List<images> = null;
    // <image type="poster"
    // url="http://hwcdn.themoviedb.org/posters/304/4bc91347017a3c57fe007304/transformers-original.jpg"
    // size="original" id="4bc91347017a3c57fe007304"/>
    // <image type="poster"
    // url="http://hwcdn.themoviedb.org/posters/304/4bc91347017a3c57fe007304/transformers-mid.jpg"
    // size="mid" id="4bc91347017a3c57fe007304"/>
    // <image type="poster"
    // url="http://hwcdn.themoviedb.org/posters/304/4bc91347017a3c57fe007304/transformers-cover.jpg"
    // size="cover" id="4bc91347017a3c57fe007304"/>
    // <image type="poster"
    // url="http://hwcdn.themoviedb.org/posters/304/4bc91347017a3c57fe007304/transformers-thumb.jpg"
    // size="thumb" id="4bc91347017a3c57fe007304"/>
    // <image type="backdrop"
    // url="http://hwcdn.themoviedb.org/backdrops/2ce/4bc91339017a3c57fe0072ce/transformers-original.jpg"
    // size="original" id="4bc91339017a3c57fe0072ce"/>
    // <image type="backdrop"
    // url="http://hwcdn.themoviedb.org/backdrops/2ce/4bc91339017a3c57fe0072ce/transformers-poster.jpg"
    // size="poster" id="4bc91339017a3c57fe0072ce"/>
    // <image type="backdrop"
    // url="http://hwcdn.themoviedb.org/backdrops/2ce/4bc91339017a3c57fe0072ce/transformers-thumb.jpg"
    // size="thumb" id="4bc91339017a3c57fe0072ce"/>
    // </images>
    private String              last_modified_at = UNKNOWN;
    
    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        representation.append("[").append(this.getClass().getSimpleName()).append(": ");
        addIfNotEmpty(representation, "name", name);
        addIfNotEmpty(representation, "released", released);
        addIfNotEmpty(representation, "score", score);
        addIfNotEmpty(representation, "language", language);
        addIfNotEmpty(representation, "original_name", original_name);
        addIfNotEmpty(representation, "alternative_name", alternative_name);
        addIfNotEmpty(representation, "popularity", popularity);
        addIfNotEmpty(representation, "translated", translated);
        addIfNotEmpty(representation, "adult", adult);
        addIfNotEmpty(representation, "type", type);
        addIfNotEmpty(representation, "id", id);
        addIfNotEmpty(representation, "url", url);
        addIfNotEmpty(representation, "imdb_id", imdb_id);
        addIfNotEmpty(representation, "votes", votes);
        addIfNotEmpty(representation, "imdb_id", imdb_id);
        addIfNotEmpty(representation, "rating", rating);
        addIfNotEmpty(representation, "certification", certification);
        addIfNotEmpty(representation, "overview", overview);
        return representation.append("]").toString();
    }
    
    private void addIfNotEmpty(StringBuilder builder, String name, Object value) {
        if (value != null && value != UNKNOWN && !Integer.valueOf(UNKNOWN_INT).equals(value) && !Double.valueOf(UNKNOWN_DOUBLE).equals(value)) {
            builder.append(name).append("=").append(value).append(", ");
        }
    }
    
}

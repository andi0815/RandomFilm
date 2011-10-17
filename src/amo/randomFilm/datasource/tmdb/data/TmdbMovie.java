/**
 * 
 */
package amo.randomFilm.datasource.tmdb.data;

import java.awt.Image;
import java.util.List;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.UnknownTypes;
import amo.randomFilm.datasource.exception.MovieDataProviderException;
import amo.randomFilm.datasource.tmdb.TmdbFacade;

/**
 * TMDB Data Object that represents a movie.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 05.09.2011
 */
public class TmdbMovie extends GsonObject implements Movie {
    
    private static final Logger logger = Logger.getLogger(TmdbMovie.class);
    
    private double score = UnknownTypes.DOUBLE;
    
    private double popularity = UnknownTypes.DOUBLE;
    
    private boolean translated = false;
    
    private boolean adult = false;
    
    private String language = UnknownTypes.STRING;
    
    private String original_name = UnknownTypes.STRING;
    
    private String name = UnknownTypes.STRING;
    
    private String alternative_name = UnknownTypes.STRING;
    
    private String movie_type = UnknownTypes.STRING;
    
    private String id = UnknownTypes.STRING;
    
    private String imdb_id = UnknownTypes.STRING;
    
    private String url = UnknownTypes.STRING;
    
    private int votes = UnknownTypes.INT;
    
    private double rating = UnknownTypes.DOUBLE;
    
    private String certification = UnknownTypes.STRING;
    
    private String overview = UnknownTypes.STRING;
    
    private String released = UnknownTypes.STRING;
    
    private List<TmdbPoster> posters = null;
    
    private String last_modified_at = UnknownTypes.STRING;
    
    private TmdbMovieExtendedInfo extInfo = null;
    
    private Image image = null;
    
    public double getScore() {
        return score;
    }
    
    public double getPopularity() {
        return popularity;
    }
    
    public boolean isTranslated() {
        return translated;
    }
    
    public boolean isAdult() {
        return adult;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public String getOriginal_name() {
        return original_name;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAlternative_name() {
        return alternative_name;
    }
    
    public String getType() {
        return movie_type;
    }
    
    public String getId() {
        return id;
    }
    
    public String getImdb_id() {
        return imdb_id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public int getVotes() {
        return votes;
    }
    
    public double getRating() {
        return rating;
    }
    
    public String getCertification() {
        return certification;
    }
    
    public String getOverview() {
        return overview;
    }
    
    public String getReleased() {
        return released;
    }
    
    public String getLast_modified_at() {
        return last_modified_at;
    }
    
    public List<TmdbPoster> getPosters() {
        return posters;
    }
    
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
        addIfNotEmpty(representation, "type", movie_type);
        addIfNotEmpty(representation, "id", id);
        addIfNotEmpty(representation, "url", url);
        addIfNotEmpty(representation, "imdb_id", imdb_id);
        addIfNotEmpty(representation, "votes", votes);
        addIfNotEmpty(representation, "imdb_id", imdb_id);
        addIfNotEmpty(representation, "rating", rating);
        addIfNotEmpty(representation, "certification", certification);
        addIfNotEmpty(representation, "overview", overview);
        addIfNotEmpty(representation, "posters", (posters != null ? posters.toString() : "NONE"));
        return representation.append("]").toString();
    }
    
    @Override
    public Image getMovieImage() {
        if (image == null) {
            try {
                TmdbImage tmdbImage = TmdbFacade.getImage(this.id);
                image = tmdbImage.getImage();
                
            } catch (MovieDataProviderException e) {
                logger.error("Could not fetch image for movie " + name, e);
            }
        }
        return image;
    }
    
    @Override
    public int getMovieRuntime() {
        if (extInfo == null) {
            try {
                extInfo = TmdbFacade.getInfo(id);
            } catch (MovieDataProviderException e) {
                logger.warn("Encountered problems fetching extended Movie Info: " + e.getMessage());
                return UnknownTypes.INT;
            }
        }
        return extInfo.getRuntime();
    }
    
    @Override
    public double getMovieRating() {
        return rating / 10;
    }
    
    @Override
    public String getMovieShortDescription() {
        return overview;
    }
    
    @Override
    public String getMovieTitle() {
        return name;
    }
    
    @Override
    public String getMovieYear() {
        return released.substring(0, 4);
    }
    
    @Override
    public List<String> getMovieGenres() {
        if (extInfo == null) {
            try {
                extInfo = TmdbFacade.getInfo(id);
            } catch (MovieDataProviderException e) {
                logger.warn("Encountered problems fetching extended Movie Info: " + e.getMessage());
            }
        }
        return extInfo.getGenres();
    }
    
}

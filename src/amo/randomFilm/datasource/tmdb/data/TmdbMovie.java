/**
 * 
 */
package amo.randomFilm.datasource.tmdb.data;

import java.awt.Image;
import java.util.List;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.exception.MovieDataProviderException;
import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.UnknownTypes;

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
        return this.score;
    }
    
    public double getPopularity() {
        return this.popularity;
    }
    
    public boolean isTranslated() {
        return this.translated;
    }
    
    public boolean isAdult() {
        return this.adult;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public String getOriginal_name() {
        return this.original_name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getAlternative_name() {
        return this.alternative_name;
    }
    
    public String getType() {
        return this.movie_type;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getImdb_id() {
        return this.imdb_id;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public int getVotes() {
        return this.votes;
    }
    
    public double getRating() {
        return this.rating;
    }
    
    public String getCertification() {
        return this.certification;
    }
    
    public String getOverview() {
        return this.overview;
    }
    
    public String getReleased() {
        return this.released;
    }
    
    public String getLast_modified_at() {
        return this.last_modified_at;
    }
    
    public List<TmdbPoster> getPosters() {
        return this.posters;
    }
    
    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        representation.append("[").append(this.getClass().getSimpleName()).append(": ");
        this.addIfNotEmpty(representation, "name", this.name);
        this.addIfNotEmpty(representation, "released", this.released);
        this.addIfNotEmpty(representation, "score", this.score);
        this.addIfNotEmpty(representation, "language", this.language);
        this.addIfNotEmpty(representation, "original_name", this.original_name);
        this.addIfNotEmpty(representation, "alternative_name", this.alternative_name);
        this.addIfNotEmpty(representation, "popularity", this.popularity);
        this.addIfNotEmpty(representation, "translated", this.translated);
        this.addIfNotEmpty(representation, "adult", this.adult);
        this.addIfNotEmpty(representation, "type", this.movie_type);
        this.addIfNotEmpty(representation, "id", this.id);
        this.addIfNotEmpty(representation, "url", this.url);
        this.addIfNotEmpty(representation, "imdb_id", this.imdb_id);
        this.addIfNotEmpty(representation, "votes", this.votes);
        this.addIfNotEmpty(representation, "imdb_id", this.imdb_id);
        this.addIfNotEmpty(representation, "rating", this.rating);
        this.addIfNotEmpty(representation, "certification", this.certification);
        this.addIfNotEmpty(representation, "overview", this.overview);
        this.addIfNotEmpty(representation, "posters", (this.posters != null ? this.posters.toString() : "NONE"));
        return representation.append("]").toString();
    }
    
    @Override
    public Image getMovieImage() {
        if (this.image == null) {
            try {
                TmdbImage tmdbImage = TmdbFacade.getImage(this.id);
                this.image = tmdbImage.getImage();
                
            } catch (MovieDataProviderException e) {
                logger.error("Could not fetch image for movie " + this.name, e);
            }
        }
        return this.image;
    }
    
    @Override
    public int getMovieRuntime() {
        if (this.extInfo == null) {
            try {
                this.extInfo = TmdbFacade.getInfo(this.id);
            } catch (MovieDataProviderException e) {
                logger.warn("Encountered problems fetching extended Movie Info: " + e.getMessage());
                return UnknownTypes.INT;
            }
        }
        return this.extInfo.getRuntime();
    }
    
    @Override
    public double getMovieRating() {
        return this.rating / 10;
    }
    
    @Override
    public String getMovieShortDescription() {
        return this.overview;
    }
    
    @Override
    public String getMovieTitle() {
        return this.name;
    }
    
    @Override
    public String getMovieYear() {
        if (this.released != null) {
            return this.released.substring(0, 4);
        } else {
            return UnknownTypes.STRING;
        }
    }
    
    @Override
    public List<String> getMovieGenres() {
        if (this.extInfo == null) {
            try {
                this.extInfo = TmdbFacade.getInfo(this.id);
            } catch (MovieDataProviderException e) {
                logger.warn("Encountered problems fetching extended Movie Info: " + e.getMessage());
            }
        }
        return this.extInfo.getGenres();
    }
    
}

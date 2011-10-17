package amo.randomFilm.gui.panels;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import amo.randomFilm.datasource.Movie;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class MovieInfoPanel extends JPanel {
    
    private Movie movie;
    private String path;
    
    public MovieInfoPanel(Movie movie, String path) {
        this.movie = movie;
        this.path = path;
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        // title
        // constraints.gridwidth = 2;
        TitlePanel titlePanel = new TitlePanel(movie.getMovieTitle());
        add(titlePanel);
        
        // rating
        StarRater starRater = new StarRater(10);
        starRater.setRating((float) movie.getMovieRating());
        add(starRater);
        
        // runtime & rating
        RuntimePanel runtimePanel = new RuntimePanel(movie.getMovieRuntime());
        add(runtimePanel);
        
        // year
        YearPanel yearPanel = new YearPanel(movie.getMovieYear());
        add(yearPanel);
        
        // genres
        GenrePanel genrePanel = new GenrePanel(movie.getMovieGenres());
        add(genrePanel);
        
        // path
        PathPanel pathPanel = new PathPanel(path);
        add(pathPanel);
        
    }
}

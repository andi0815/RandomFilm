package amo.randomFilm.gui.panels.moviepanel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class MovieInfoPanel extends JPanel implements Updateable {
    
    private Movie movie;
    private String path;
    private StarRater starRater;
    private RuntimePanel runtimePanel;
    private YearPanel yearPanel;
    private GenrePanel genrePanel;
    private TitlePanel titlePanel;
    
    public MovieInfoPanel(Movie movie, String path) {
        this.movie = movie;
        this.path = path;
        
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        this.titlePanel = new TitlePanel(movie != null ? movie.getMovieTitle() : UnknownTypes.STRING);
        this.add(this.titlePanel);
        
        // rating
        this.starRater = new StarRater(10);
        this.starRater.setRating(movie != null ? (float) movie.getMovieRating() : (float) UnknownTypes.DOUBLE);
        this.add(this.starRater);
        
        // runtime length
        this.runtimePanel = new RuntimePanel(movie != null ? movie.getMovieRuntime() : UnknownTypes.INT);
        this.add(this.runtimePanel);
        
        // year
        this.yearPanel = new YearPanel(movie != null ? movie.getMovieYear() : UnknownTypes.STRING);
        this.add(this.yearPanel);
        
        // genres
        this.genrePanel = new GenrePanel(movie != null ? movie.getMovieGenres() : null);
        this.add(this.genrePanel);
        
        // path
        PathPanel pathPanel = new PathPanel(path);
        this.add(pathPanel);
        
    }
    
    @Override
    public void update(Movie movie) {
        
        // title
        this.titlePanel.update(movie);
        
        // rating
        this.starRater.update(movie);
        
        // runtime length
        this.runtimePanel.update(movie);
        
        // year
        this.yearPanel.update(movie);
        
        // genres
        this.genrePanel.update(movie);
        
        this.repaint();
        
    }
}

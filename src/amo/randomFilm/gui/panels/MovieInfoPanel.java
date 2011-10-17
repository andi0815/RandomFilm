package amo.randomFilm.gui.panels;

import java.awt.BorderLayout;

import javax.swing.Box;
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
        
        // switch to BoxLayout ...
        setLayout(new BorderLayout());
        
        // title & rating
        TitlePanel titlePanel = new TitlePanel(movie.getMovieTitle());
        add(titlePanel, BorderLayout.PAGE_START);
        
        // runtime & rating
        RuntimePanel runtimePanel = new RuntimePanel(movie.getMovieRuntime());
        StarRater starRater = new StarRater(10);
        starRater.setRating((float) movie.getMovieRating());
        JPanel timeAndRating = new JPanel();
        timeAndRating.setLayout(new BoxLayout(timeAndRating, BoxLayout.LINE_AXIS));
        timeAndRating.add(runtimePanel);
        timeAndRating.add(Box.createHorizontalGlue());
        timeAndRating.add(starRater);
        
        YearPanel yearPanel = new YearPanel(movie.getMovieYear());
        JPanel infoLines = new JPanel(new BorderLayout());
        infoLines.add(timeAndRating, BorderLayout.NORTH);
        infoLines.add(yearPanel, BorderLayout.PAGE_END);
        
        // genres
        GenrePanel genrePanel = new GenrePanel(movie.getMovieGenres());
        // add(genrePanel, BorderLayout.CENTER);
        infoLines.add(genrePanel, BorderLayout.CENTER);
        add(infoLines, BorderLayout.LINE_START);
        
        // path
        PathPanel pathPanel = new PathPanel(path);
        add(pathPanel, BorderLayout.PAGE_END);
        
        System.out.println("infoLines: " + infoLines.getSize());
        System.out.println("timeAndRating: " + timeAndRating.getSize());
        
    }
}

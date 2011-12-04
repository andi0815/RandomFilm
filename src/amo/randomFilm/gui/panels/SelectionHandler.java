package amo.randomFilm.gui.panels;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.panels.moviepanel.MoviePanelBasicView;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.MovieDataProvider;

/**
 * Class that contains information about a certain movie file. The information to display is
 * provided
 * by a {@link MovieDataProvider} within a {@link Movie} object.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 23.10.2011
 */
public class SelectionHandler implements MouseListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(SelectionHandler.class);
    
    private List<MoviePanelBasicView> movieAlternatives = new ArrayList<MoviePanelBasicView>();
    
    private MoviePanelBasicView selectedMovie = null;
    
    public SelectionHandler() {
        super();
    }
    
    public void addMovies(List<MoviePanelBasicView> movieAlternatives) {
        this.movieAlternatives.addAll(movieAlternatives);
    }
    
    public void addMovie(MoviePanelBasicView movie) {
        this.movieAlternatives.add(movie);
        movie.getComponent().addMouseListener(this);
    }
    
    public MoviePanelBasicView getSelectedMovie() {
        return this.selectedMovie;
    }
    
    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
    
    @Override
    public void mouseClicked(MouseEvent arg0) {
    }
    
    @Override
    public void mouseEntered(MouseEvent arg0) {
    }
    
    @Override
    public void mouseExited(MouseEvent arg0) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        Iterator<MoviePanelBasicView> iterator = this.movieAlternatives.iterator();
        while (iterator.hasNext()) {
            MoviePanelBasicView next = iterator.next();
            if (next.getComponent().equals(e.getComponent())) {
                if (next.isSelected()) {
                    next.setSelected(false);
                    this.selectedMovie = null;
                } else {
                    next.setSelected(true);
                    this.selectedMovie = next;
                }
            } else {
                next.setSelected(false);
            }
        }
    }
    
}

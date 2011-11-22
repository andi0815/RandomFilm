package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import amo.randomFilm.model.Movie;
import amo.randomFilm.model.UnknownTypes;

/**
 * Simple Panel that displays a movie title.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class TitlePanel extends JLabel implements Updateable {
    
    public TitlePanel(String title) {
        this.setText(title);
        this.setFont(new Font("Sans-Serif", Font.BOLD, 18));
    }
    
    @Override
    public void update(Movie movie) {
        if (movie != null) {
            this.setText(movie.getMovieTitle());
        } else {
            this.setText(UnknownTypes.STRING);
        }
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(0, 0);
    }
    
}

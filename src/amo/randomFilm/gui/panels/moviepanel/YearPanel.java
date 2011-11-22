package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Font;

import javax.swing.JLabel;

import amo.randomFilm.model.Movie;
import amo.randomFilm.model.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class YearPanel extends JLabel implements Updateable {
    
    public YearPanel(String year) {
        this.doUpdate(year);
        this.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
    }
    
    private void doUpdate(String year) {
        this.setText("YEAR: " + year);
    }
    
    @Override
    public void update(Movie movie) {
        if (movie != null) {
            this.doUpdate(movie.getMovieYear());
        } else {
            this.doUpdate(UnknownTypes.STRING);
        }
    }
    
}

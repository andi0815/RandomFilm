package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

import amo.randomFilm.model.Movie;
import amo.randomFilm.model.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class RuntimePanel extends JLabel implements Updateable {
    
    public RuntimePanel(int runtimeLength) {
        this.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        this.doUpdate(runtimeLength);
    }
    
    @Override
    public void update(Movie movie) {
        if (movie != null) {
            this.doUpdate(movie.getMovieRuntime());
        } else {
            this.doUpdate(UnknownTypes.INT);
        }
        
    }
    
    private void doUpdate(int runtimeLength) {
        if (runtimeLength != UnknownTypes.INT) {
            this.setText("RUNTIME: " + runtimeLength + " min");
        } else {
            this.setText("RUNTIME: " + UnknownTypes.STRING);
        }
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(0, 0);
    }
}

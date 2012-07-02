package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;

import javax.swing.JLabel;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class RuntimePanel extends JLabel implements Updateable {
    
    public RuntimePanel(int runtimeLength) {
        this.setFont(GuiConstants.FONT_NORMAL);
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
            this.setText("LAUFZEIT: " + runtimeLength + " min");
        } else {
            this.setText("LAUFZEIT: " + UnknownTypes.STRING);
        }
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(0, 0);
    }
}

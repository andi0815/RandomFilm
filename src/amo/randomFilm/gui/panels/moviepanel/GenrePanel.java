package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class GenrePanel extends JLabel implements Updateable {
    
    public GenrePanel(List<String> genres) {
        this.setFont(GuiConstants.FONT_NORMAL);
        this.updateLabel(genres);
    }
    
    @Override
    public void update(Movie movie) {
        if (movie != null) {
            this.updateLabel(movie.getMovieGenres());
        }
    }
    
    private void updateLabel(List<String> movieGenres) {
        StringBuilder label = new StringBuilder();
        label.append("GENRES: ");
        boolean isFirst = true;
        if (movieGenres != null && movieGenres.size() > 0) {
            for (String genre : movieGenres) {
                if (isFirst) {
                    isFirst = false;
                } else {
                    label.append(", ");
                }
                label.append(genre);
            }
        } else {
            label.append(UnknownTypes.STRING);
        }
        this.setText(label.toString());
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(0, 0);
    }
    
}

package amo.randomFilm.gui.panels;

import java.awt.Font;
import java.util.List;

import javax.swing.JLabel;

import amo.randomFilm.datasource.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class GenrePanel extends JLabel {
    
    public GenrePanel(List<String> genres) {
        this.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        StringBuilder label = new StringBuilder();
        label.append("GENRES: ");
        boolean isFirst = true;
        if (genres != null && genres.size() > 0) {
            for (String genre : genres) {
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
    
}

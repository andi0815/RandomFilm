package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class PathPanel extends JLabel {
    
    public PathPanel(String path) {
        this.setFont(new Font("Sans-Serif", Font.ITALIC, 12));
        // setText("PATH: " + path);
        this.setText(path);
    }
    
}
package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Font;
import java.io.File;

import javax.swing.JLabel;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class PathPanel extends JLabel {
    
    public PathPanel(File filePath) {
        this.setFont(new Font("Sans-Serif", Font.ITALIC, 12));
        // setText("PATH: " + path);
        this.setText(filePath.getAbsolutePath());
    }
    
//    public void update(File filePath) {
//        this.setText(filePath.getAbsolutePath());
//    }
}

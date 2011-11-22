package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.JLabel;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class PathPanel extends JLabel {
    
    private File filePath;
    
    public PathPanel(File filePath) {
        this.setFont(new Font("Sans-Serif", Font.ITALIC, 12));
        // setText("PATH: " + path);
        this.setText(filePath.getAbsolutePath());
        this.filePath = filePath;
    }
    
    public File getFile() {
        return this.filePath;
    }
    
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(0, 0);
    }
    
//    public void update(File filePath) {
//        this.setText(filePath.getAbsolutePath());
//    }
    
}

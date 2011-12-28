package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JLabel;

import amo.randomFilm.gui.GuiConstants;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class PathPanel extends JLabel {
    
    private File filePath;
    
    public PathPanel(File filePath) {
        this.setFont(GuiConstants.FONT_NORMAL);
        if (filePath != null) {
            if (!filePath.getName().startsWith(GuiConstants.UNKNOWN_FILE_PREFIX)) {
                this.setText(filePath.getAbsolutePath());
            } else {
                this.setText(GuiConstants.LABEL_NO_FILE_GIVEN);
            }
        }
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

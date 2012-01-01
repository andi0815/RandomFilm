/**
 * 
 */
package amo.randomFilm.model;

import java.awt.Image;
import java.io.File;

import amo.randomFilm.gui.GuiConstants;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 27.09.2011
 * 
 */
public class UnknownTypes {
    
    private UnknownTypes() {
        // hidden constructor
    }
    
    public static final double DOUBLE = -1d;
    
    public static final int INT = -1;
    
    public static final String STRING = "UNKNOWN";
    
    private static int filecounter = 0;
    
    public static File getUnknownFile() {
        return new File(GuiConstants.UNKNOWN_FILE_PREFIX + filecounter++);
    }
    
    public static Image IMAGE = GuiConstants.IMAGE_POSTER_NO_AVAIL;
}

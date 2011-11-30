/**
 * 
 */
package amo.randomFilm.model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 27.09.2011
 * 
 */
public class UnknownTypes {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(UnknownTypes.class);
    
    private UnknownTypes() {
        // hidden constructor
    }
    
    public static final double DOUBLE = -1d;
    
    public static final int INT = -1;
    
    public static final String STRING = "UNKNOWN";
    
    private static final String PATH_IMAGES_NO_IMAGE_AVAIL = "images/NO_IMAGE_AVAIL.png";
    
    private static int filecounter = 0;
    
    public static File getUnknownFile() {
        return new File(GuiConstants.UNKNOWN_FILE_PREFIX + filecounter++);
    }
    
    public static Image IMAGE = null;
    static {
        try {
            File imagefile = new File(PATH_IMAGES_NO_IMAGE_AVAIL);
            IMAGE = ImageIO.read(imagefile);
        } catch (IOException e) {
            logger.warn("could not load image: " + PATH_IMAGES_NO_IMAGE_AVAIL);
        }
    }
}

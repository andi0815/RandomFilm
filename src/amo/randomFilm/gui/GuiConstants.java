package amo.randomFilm.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 23.10.2011
 */
public class GuiConstants {
    
    /**
     * Hidden Constructor.
     */
    private GuiConstants() {
        // unused!
    }
    
    /* =====================
     *  STYLE
     =======================*/
    
    public static final Color BG_COLOR = Color.WHITE;
    
    public static final Color BG_COLOR_SELECTED = new Color(200, 200, 255);
    
    /* =====================
     *  LABELS
     =======================*/
    
    public static final String LABEL_BTN_DELETE = "löschen";
    
    public static final String LABEL_BTN_ALTERNATIVES = "Show Alternatives";
    
    public static final String LABEL_BTN_INFO = "Show detailed Info";
    
    public static final String LABEL_BTN_SELECT_ALL = "Alles markieren";
    
    public static final String LABEL_BTN_SELECT_NOTHING = "Nichts markieren";
    
    public static final String LABEL_BTN_REMOVE_SELECTED = "Markiertes löschen";
    
    public static final String LABEL_BTN_REMOVE_ALL = "Liste löschen";
    
    public static final String LABEL_BTN_GO = "Los geht's !";
    
    public static final String LABEL_BTN_DISABLE_ALWAYS_ON_TOP = "Always On Top ausschalten";
    
    public static final String LABEL_BTN_ENABLE_ALWAYS_ON_TOP = "Always On Top einschalten";
    
    public static final String LABEL_CHOOSE_ALTERNATIVE = "Choose Alternative";
    
    public static final String LABEL_BTN_SELECT = "Select";
    
    public static final String LABEL_BTN_CANCEL = "Cancel";
    
    public static final String LABEL_BTN_CLOSE = "Close";
    
    public static final String LABEL_START_RANDOM = "Ja";
    
    public static final String LABEL_CANCEL_RANDOM = "Nein";
    
    public static final String LABEL_TITLE_START_RANDOM = "Start Movie?";
    
    public static final String LABEL_START_THIS_MOVIE = "Start Movie: %s ?" + "\n(Filename: %s)";
    
    public static final String LABEL_BTN_ADD_TITLE = "Hinzufügen";
    
    public static final String LABEL_NO_FILE_GIVEN = "Keine Datei ...";
    
    /* =====================
     *  IMAGES
     =======================*/
    
    public static final String IMAGE_BTN_DELETE = "images\\BTN_DELETE.png";
    
    public static final String IMAGE_BTN_ALTERNATIVES = "images\\BTN_ALTERNATIVES.png";
    
    public static final String IMAGE_BTN_PLAY = "images\\BTN_PLAY.png";
    
    public static final String IMAGE_BTN_STARTRANDOM = "images\\BTN_STARTRANDOM.png";
    
    public static final String IMAGE_BTN_INFO = "images\\BTN_INFO.png";
    
    public static final String IMAGE_BTN_PREV = "images\\BTN_PREV.png";
    
    public static final String IMAGE_POSTER_LOADING = "images/LOADING.jpg";
    
    /* =====================
     *  FONTS
     =======================*/
    
    public static final Font FONT_NORMAL = new Font("Sans-Serif", Font.PLAIN, 12);
    
    public static final Font FONT_BIG_BOLD = new Font("Sans-Serif", Font.BOLD, 18);
    
    /* =====================
     *  IDENTIFIERS
     =======================*/
    
    public static final String UNKNOWN_FILE_PREFIX = "UNKNOWN_TITLE_OF_MOVIE.598765tzibjzb_";
    
}

package amo.randomFilm.gui.panels;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class YearPanel extends JLabel {
    
    public YearPanel(String year) {
        setText("YEAR: " + year);
        setFont(new Font("Sans-Serif", Font.PLAIN, 12));
    }
    
}

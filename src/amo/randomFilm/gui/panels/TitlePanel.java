package amo.randomFilm.gui.panels;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * Simple Panel that displays a movie title.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class TitlePanel extends JLabel {
    
    public TitlePanel(String title) {
        setText(title);
        setFont(new Font("Sans-Serif", Font.BOLD, 18));
    }
    
}

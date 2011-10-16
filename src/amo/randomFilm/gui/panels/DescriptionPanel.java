package amo.randomFilm.gui.panels;

import java.awt.Font;

import javax.swing.JTextArea;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class DescriptionPanel extends JTextArea {
    
    public DescriptionPanel(String description) {
        setEditable(false);
        setLineWrap(true);
        setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        setText(description);
    }
    
}

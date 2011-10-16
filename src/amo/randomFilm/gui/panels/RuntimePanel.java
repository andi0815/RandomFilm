package amo.randomFilm.gui.panels;

import java.awt.Font;

import javax.swing.JLabel;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.10.2011
 */
public class RuntimePanel extends JLabel {
    
    public RuntimePanel(int runtimeLength) {
        setFont(new Font("Sans-Serif", Font.PLAIN, 12));
        setText("RUNTIME: " + runtimeLength + " min");
    }
}

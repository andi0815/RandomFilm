package amo.randomFilm.gui.panels;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 15.10.2011
 */
public class PosterPanelTest extends AbstractTestBase {
    
    @Test
    public void testIt() throws Exception {
        
        PosterPanel posterPanel = new PosterPanel(getDummyMovie().getMovieImage());
        // posterPanel.setPreferredSize(new Dimension(500, 100));
        posterPanel.setVisible(true);
        // showComponent(posterPanel, 10000);
        
    }
    
    @Test
    public void testDefault() throws Exception {
        
        PosterPanel posterPanel = new PosterPanel(null);
        showComponent(posterPanel, 10000);
        
    }
}

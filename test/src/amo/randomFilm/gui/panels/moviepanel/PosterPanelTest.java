package amo.randomFilm.gui.panels.moviepanel;

import java.util.List;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.model.Movie;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 15.10.2011
 */
public class PosterPanelTest extends AbstractTestBase {
    
//    @Test
    public void testIt() throws Exception {
        
        List<? extends Movie> dummyMovieList = getDummyMovieList();
        PosterPanel posterPanel = new PosterPanel(dummyMovieList.get(0).getMovieImage());
        // posterPanel.setPreferredSize(new Dimension(500, 100));
        posterPanel.setVisible(true);
        showComponent(posterPanel, 2000);
        
        posterPanel.update(dummyMovieList.get(1));
        Thread.sleep(2000);
    }
    
    @Test
    public void testDefault() throws Exception {
        PosterPanel posterPanel = new PosterPanel(null);
        showComponent(posterPanel, 20000);
        
    }
}

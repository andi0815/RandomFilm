package amo.randomFilm.gui.panels.moviepanel;

import java.util.List;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.Movie;

public class MovieInfoPanelTest extends AbstractTestBase {
    
    @Test
    public void testPanelUpdate() throws InterruptedException {
        
        List<? extends Movie> dummyMovieList = getDummyMovieList();
        
        MovieInfoPanel movieInfoPanel = new MovieInfoPanel(dummyMovieList.get(0), "file://path/to/file.ext");
        // movieInfoPanel.setPreferredSize(new Dimension(500, 90));
        showComponent(movieInfoPanel, 2000);
        
        // test update
        movieInfoPanel.update(dummyMovieList.get(1));
        Thread.sleep(2000);
    }
    
}

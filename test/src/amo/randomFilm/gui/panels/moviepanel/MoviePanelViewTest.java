package amo.randomFilm.gui.panels.moviepanel;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.tmdb.TmdbFacade;

public class MoviePanelViewTest extends AbstractTestBase {
    
    @Test
    public void testView() throws Exception {
        
        MoviePanelViewBasic moviePanelView = new MoviePanelViewWithButtons(getDummyMovieFile().getFile());
        MoviePanelPresenter moviePanelPresenter = new MoviePanelPresenter(getDummyMovieFile().getFile(),
                getDummyMovieFile().getTitle(), moviePanelView, TmdbFacade.getInstance());
        
//        moviePanelView.setSelected(true);
        showComponent(moviePanelView.getComponent(), 50000);
        
//        moviePanelView.setSelected(true);
//        Thread.sleep(2000);
//        moviePanelView.setSelected(false);
//        Thread.sleep(1000);
    }
    
}

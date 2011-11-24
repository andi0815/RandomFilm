package amo.randomFilm.gui.panels.moviepanel;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.tmdb.TmdbFacade;

public class MoviePanelViewTest extends AbstractTestBase {
    
    @Test
    public void testView() throws Exception {
        
        MoviePanelBasicView moviePanelView = new MoviePanelWithButtonsView(getDummyMovieFile().getFile());
        MoviePanelBasicPresenter moviePanelPresenter = new MoviePanelBasicPresenter(getDummyMovieFile().getFile(),
                getDummyMovieFile().getTitle(), moviePanelView, TmdbFacade.getInstance());
        
//        moviePanelView.setSelected(true);
        showComponent(moviePanelView.getComponent(), 50000);
        
//        moviePanelView.setSelected(true);
//        Thread.sleep(2000);
//        moviePanelView.setSelected(false);
//        Thread.sleep(1000);
    }
    
}

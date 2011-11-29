package amo.randomFilm.gui.panels;

import java.io.File;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsPresenter;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsView;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 28.11.2011
 */
public class SelectionDialogTest extends AbstractTestBase {
    
    @Test
    public void testIt() throws Exception {
        SelectableMoviePanelView view = new SelectableMoviePanelView();
        SelectableMoviePanelPresenter parentPanel = new SelectableMoviePanelPresenter(view);
        MoviePanelWithButtonsView movieView = new MoviePanelWithButtonsView(new File(""));
        MoviePanelWithButtonsPresenter moviePresenter = new MoviePanelWithButtonsPresenter(null, "dummy title",
                movieView, parentPanel, TmdbFacade.getInstance());
        
        SelectionDialogPanelView selectionDialogPanelView = new SelectionDialogPanelView();
        selectionDialogPanelView.setData(this.getDummyMovieList());
        
        SelectionDialogPanelPresenter presenter = new SelectionDialogPanelPresenter(selectionDialogPanelView,
                moviePresenter);
    }
}

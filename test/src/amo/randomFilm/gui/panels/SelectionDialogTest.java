package amo.randomFilm.gui.panels;

import java.io.File;

import javax.swing.JFrame;

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
        ButtonPanelView buttonPanel = new ButtonPanelView();
        JFrame parentFrame = new JFrame();
        buttonPanel.init(parentFrame, 100, 100);
        
        ListOfMoviesView view = new ListOfMoviesView();
        ListOfMoviesPresenter parentPanel = new ListOfMoviesPresenter(view, buttonPanel, parentFrame);
        MoviePanelWithButtonsView movieView = new MoviePanelWithButtonsView(new File(""));
        MoviePanelWithButtonsPresenter moviePresenter = new MoviePanelWithButtonsPresenter(null, "dummy title",
                movieView, parentPanel, TmdbFacade.getInstance());
        
        SelectAlternativeDialogView selectionDialogPanelView = new SelectAlternativeDialogView();
        selectionDialogPanelView.setData(this.getDummyMovieList());
        
        SelectAlternativeDialogPresenter presenter = new SelectAlternativeDialogPresenter(selectionDialogPanelView,
                moviePresenter);
    }
}

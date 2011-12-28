/**
 * 
 */
package amo.randomFilm.gui.panels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;

import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsPresenter;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsView;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.SimpleMovie;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 28.12.2011
 * 
 */
public class SelectAlternativeDialogTest {
    
    public static void main(String[] args) {
        
        BasicConfigurator.configure();
        
        // Parent Component
        MoviePanelWithButtonsView moviePanelWithButtonsView = new MoviePanelWithButtonsView(new File(""));
        MoviePanelWithButtonsPresenter moviePanelPres = new MoviePanelWithButtonsPresenter(new File(""), "someTitle",
                moviePanelWithButtonsView, null, TmdbFacade.getInstance());
        
        List<Movie> listOfAlternatives = new ArrayList<Movie>();
        for (int i = 0; i < 11; i++) {
            listOfAlternatives.add(new SimpleMovie("" + i));
        }
        SelectAlternativeDialogView selectAlternativeDialogView = new SelectAlternativeDialogView();
        SelectAlternativeDialogPresenter selectAlternativeDialogPresenter = new SelectAlternativeDialogPresenter(
                selectAlternativeDialogView, listOfAlternatives, moviePanelPres);
        
    }
}

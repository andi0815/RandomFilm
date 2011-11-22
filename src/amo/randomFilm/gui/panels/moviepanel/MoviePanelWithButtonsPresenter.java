package amo.randomFilm.gui.panels.moviepanel;

import java.io.File;

import amo.randomFilm.gui.panels.SelectableMoviePanelPresenter;
import amo.randomFilm.model.MovieDataProvider;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 21.11.2011
 */
public class MoviePanelWithButtonsPresenter extends MoviePanelPresenter {
    
    private SelectableMoviePanelPresenter parentPresenter;
    
    public MoviePanelWithButtonsPresenter(File f, String title, MoviePanelViewBasic moviePanel,
            SelectableMoviePanelPresenter parentPresenter, MovieDataProvider movieDataProvider) {
        super(f, title, moviePanel, movieDataProvider);
        this.parentPresenter = parentPresenter;
    }
    
}

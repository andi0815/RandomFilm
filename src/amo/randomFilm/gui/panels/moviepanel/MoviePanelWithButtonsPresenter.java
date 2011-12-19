package amo.randomFilm.gui.panels.moviepanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.ListOfMoviesPresenter;
import amo.randomFilm.gui.panels.MovieDetailsView;
import amo.randomFilm.gui.panels.SelectAlternativeDialogPresenter;
import amo.randomFilm.gui.panels.SelectAlternativeDialogView;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.MovieDataProvider;
import amo.randomFilm.model.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 21.11.2011
 */
public class MoviePanelWithButtonsPresenter extends MoviePanelBasicPresenter implements ActionListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(MoviePanelWithButtonsPresenter.class);
    
    private ListOfMoviesPresenter parentPresenter;
    
    public MoviePanelWithButtonsPresenter(File f, String title, MoviePanelWithButtonsView moviePanel,
            ListOfMoviesPresenter parentPresenter, MovieDataProvider movieDataProvider) {
        super(f, title, moviePanel, movieDataProvider);
        this.parentPresenter = parentPresenter;
        this.moviePanel.setActionListener(this);
    }
    
    /**
     * Enables / Disables button for alternatives
     */
    @Override
    protected void setMovieAlternatives(List<? extends Movie> alternatives) {
        super.setMovieAlternatives(alternatives);
        
        boolean hasAlternatives = alternatives != null && alternatives.size() > 1;
        ((MoviePanelWithButtonsView) this.moviePanel).hasAlternatives(hasAlternatives);
        
        boolean hasDetails = this.getSelectedMovie().getMovieShortDescription() != null
                && this.getSelectedMovie().getMovieShortDescription() != UnknownTypes.STRING;
        ((MoviePanelWithButtonsView) this.moviePanel).hasDetails(hasDetails);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_DELETE)) {
            logger.debug("Got Action Event: " + GuiConstants.LABEL_BTN_DELETE + " -> Source: " + e.getSource());
            this.parentPresenter.removeMovie(this);
            
        }
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_ALTERNATIVES)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_ALTERNATIVES + " -> Source: " + e.getSource());
            
            SelectAlternativeDialogView selectionDialogPanelView = new SelectAlternativeDialogView();
            SelectAlternativeDialogPresenter selectionPresenter = new SelectAlternativeDialogPresenter(
                    selectionDialogPanelView, this.getMovieAlternatives(), this);
            
        }
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_INFO)) {
            logger.debug("Got Action Event: " + GuiConstants.LABEL_BTN_INFO + " -> Source: " + e.getSource());
            MovieDetailsView movieDetailsView = new MovieDetailsView(this.getSelectedMovie());
            movieDetailsView.setVisible(true);
            
        } else {
            logger.warn("Caught unhandled Event: " + e.getActionCommand());
            
        }
        
    }
}

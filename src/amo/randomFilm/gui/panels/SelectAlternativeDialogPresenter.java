package amo.randomFilm.gui.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelBasicView;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsPresenter;
import amo.randomFilm.gui.util.BackgroundMovieDataLoader;
import amo.randomFilm.gui.util.Dialogs;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.SimpleMovie;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 28.11.2011
 */
public class SelectAlternativeDialogPresenter implements ActionListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(SelectAlternativeDialogPresenter.class);
    
    private JDialog jDialog;
    
    private SelectAlternativeDialogView selectionDialogPanelView;
    
    private MoviePanelWithButtonsPresenter moviePanelWithButtonsPresenter;
    
    public SelectAlternativeDialogPresenter(SelectAlternativeDialogView selectionDialogPanelView,
            List<? extends Movie> listOfAlternatives, MoviePanelWithButtonsPresenter moviePanelWithButtonsPresenter) {
        // init View
        this.selectionDialogPanelView = selectionDialogPanelView;
        this.selectionDialogPanelView.addActionListener(this);
        
        // init Dialog
        this.jDialog = new JDialog();
        this.moviePanelWithButtonsPresenter = moviePanelWithButtonsPresenter;
        this.jDialog.getContentPane().add(selectionDialogPanelView.getComponent());
        this.jDialog.setEnabled(true);
        this.jDialog.setModal(true);
        this.jDialog.setTitle(GuiConstants.LABEL_CHOOSE_ALTERNATIVE);
        this.jDialog.setMinimumSize(new Dimension(GuiConstants.WIDTH_MIN_OF_FRAME, GuiConstants.HEIGHT_MIN_OF_FRAME));
        // jDialog.doLayout();
        this.jDialog.pack();
        
        Dialogs.centerWindow(this.jDialog);
        
        for (Movie movie : listOfAlternatives) {
            MoviePanelBasicView movieView = new MoviePanelBasicView(moviePanelWithButtonsPresenter.getFile());
            movieView.setData(new SimpleMovie(movie.getMovieTitle(), true));
            selectionDialogPanelView.addMovieView(movieView);
            BackgroundMovieDataLoader.getInstance().loadAndUpdateMovie(movie, movieView);
        }
        
        this.jDialog.setVisible(true);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        logger.warn("Got ActionEvent from: " + e.getActionCommand());
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_SELECT)) {
            logger.warn("Got ActionEvent:  SELECT "
                    + this.selectionDialogPanelView.getSelectedMovie().getData().getMovieTitle() + " - "
                    + this.selectionDialogPanelView.getSelectedMovie().getFile());
            if (this.selectionDialogPanelView.getSelectedMovie() != null) {
                this.moviePanelWithButtonsPresenter.setSelectedMovie(this.selectionDialogPanelView.getSelectedMovie()
                        .getData());
            }
            this.jDialog.setVisible(false);
            Dialogs.centerWindow(this.jDialog);
            
        } else { // assume CANCEL
            logger.warn("Got ActionEvent:  " + e.getActionCommand());
            this.jDialog.setVisible(false);
        }
    }
}

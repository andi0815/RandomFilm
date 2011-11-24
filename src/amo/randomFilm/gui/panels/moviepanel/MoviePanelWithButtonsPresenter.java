package amo.randomFilm.gui.panels.moviepanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.SelectableMoviePanelPresenter;
import amo.randomFilm.gui.panels.SelectionDialogPanelView;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.MovieDataProvider;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 21.11.2011
 */
public class MoviePanelWithButtonsPresenter extends MoviePanelBasicPresenter implements ActionListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(MoviePanelWithButtonsPresenter.class);
    
    private SelectableMoviePanelPresenter parentPresenter;
    
    public MoviePanelWithButtonsPresenter(File f, String title, MoviePanelWithButtonsView moviePanel,
            SelectableMoviePanelPresenter parentPresenter, MovieDataProvider movieDataProvider) {
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
        if (alternatives != null && alternatives.size() > 1) {
            ((MoviePanelWithButtonsView) this.moviePanel).hasAlternatives(true);
        } else {
            ((MoviePanelWithButtonsView) this.moviePanel).hasAlternatives(false);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_DELETE)) {
            logger.debug("Got Action Event: " + GuiConstants.LABEL_BTN_DELETE + " -> Source: " + e.getSource());
            this.parentPresenter.removeMovie(this);
            
        }
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_ALTERNATIVES)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_ALTERNATIVES + " -> Source: " + e.getSource());
            
            // TODO:
            // selection listener
            // OK, CANCEL Buttons
            // Min / Max Size
            // Scrollpane
            // return selection
            
            SelectionDialogPanelView selectionDialogPanelView = new SelectionDialogPanelView();
            selectionDialogPanelView.setData(this.getMovieAlternatives());
            
            JDialog jDialog = new JDialog();
            jDialog.getContentPane().add(selectionDialogPanelView.getComponent());
            jDialog.setEnabled(true);
            jDialog.setModal(true);
            jDialog.setTitle(GuiConstants.TITLE_CHOOSE_ALTERNATIVE);
            jDialog.pack();
            jDialog.setVisible(true);
            
        } else {
            logger.warn("Caught unhandled Event: " + e.getActionCommand());
            
        }
        
    }
}

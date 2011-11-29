package amo.randomFilm.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsPresenter;
import amo.randomFilm.gui.util.Dialogs;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 28.11.2011
 */
public class SelectionDialogPanelPresenter implements ActionListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(SelectionDialogPanelPresenter.class);
    
    private JDialog jDialog;
    
    private SelectionDialogPanelView selectionDialogPanelView;
    
    private MoviePanelWithButtonsPresenter moviePanelWithButtonsPresenter;
    
    public SelectionDialogPanelPresenter(SelectionDialogPanelView selectionDialogPanelView,
            MoviePanelWithButtonsPresenter moviePanelWithButtonsPresenter) {
        this.jDialog = new JDialog();
        this.moviePanelWithButtonsPresenter = moviePanelWithButtonsPresenter;
        this.selectionDialogPanelView = selectionDialogPanelView;
        selectionDialogPanelView.addActionListener(this);
        this.jDialog.getContentPane().add(selectionDialogPanelView.getComponent());
        this.jDialog.setEnabled(true);
        this.jDialog.setModal(true);
        this.jDialog.setTitle(GuiConstants.LABEL_CHOOSE_ALTERNATIVE);
        this.jDialog.pack();
        Dialogs.centerWindow(this.jDialog);
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

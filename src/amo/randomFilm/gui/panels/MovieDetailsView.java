package amo.randomFilm.gui.panels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.GenrePanel;
import amo.randomFilm.gui.panels.moviepanel.PosterPanel;
import amo.randomFilm.gui.panels.moviepanel.RuntimePanel;
import amo.randomFilm.gui.panels.moviepanel.StarRater;
import amo.randomFilm.gui.panels.moviepanel.TitlePanel;
import amo.randomFilm.gui.panels.moviepanel.YearPanel;
import amo.randomFilm.gui.util.Dialogs;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 16.12.2011
 */
public class MovieDetailsView {
    
    private JDialog detailsDialog = new JDialog();
    private TitlePanel titlePanel = new TitlePanel(UnknownTypes.STRING);
    private StarRater starRater = new StarRater();
    private RuntimePanel runtimePanel = new RuntimePanel(UnknownTypes.INT);
    private YearPanel yearPanel = new YearPanel(UnknownTypes.STRING);
    private GenrePanel genrePanel = new GenrePanel(null);
    private JTextArea shortDescrLabel = new JTextArea();
    private PosterPanel posterPanel = new PosterPanel(null);
    // FIXME: insert close button
    private JButton btnClose = new JButton();
    
    public MovieDetailsView(Movie movie) {
        
        this.detailsDialog = new JDialog();
        this.detailsDialog.setMinimumSize(new Dimension(700, 400));
        this.detailsDialog.setModal(true);
        this.detailsDialog.getContentPane().setBackground(GuiConstants.BG_COLOR);
        
        this.detailsDialog.setTitle("Movie Info: " + movie.getMovieTitle());
        
        this.titlePanel = new TitlePanel(movie.getMovieTitle());
        this.starRater = new StarRater(10);
        this.starRater.setRating((float) movie.getMovieRating());
        this.runtimePanel = new RuntimePanel(movie.getMovieRuntime());
        this.yearPanel = new YearPanel(movie.getMovieYear());
        this.genrePanel = new GenrePanel(movie.getMovieGenres());
        this.shortDescrLabel = new JTextArea("SHORT DESCRIPTION: " + movie.getMovieShortDescription());
        this.shortDescrLabel.setLineWrap(true);
        this.shortDescrLabel.setAutoscrolls(true);
        this.shortDescrLabel.setEditable(false);
        this.shortDescrLabel.setFont(GuiConstants.FONT_NORMAL);
        this.posterPanel = new PosterPanel(movie.getMovieImage());
        this.posterPanel.setCenterVertically(false);
        this.posterPanel.setMinimumSize(new Dimension(250, 0));
        this.btnClose = new JButton(GuiConstants.LABEL_BTN_CLOSE);
        
        // do layout
        GroupLayout groupLayout = new GroupLayout(this.detailsDialog.getContentPane());
        groupLayout.setAutoCreateGaps(true);
        this.detailsDialog.setLayout(groupLayout);
        groupLayout.setHorizontalGroup( //
                groupLayout.createParallelGroup() // START: (Poster & Stuff) & Shortdesc
                        .addGroup(groupLayout.createSequentialGroup() // START: (Poster & Stuff)
                                .addComponent(this.posterPanel)//
                                .addGroup( // START: Stuff
                                        groupLayout.createParallelGroup(Alignment.LEADING) //
                                                .addComponent(this.titlePanel)//
                                                .addComponent(this.starRater) //
                                                .addComponent(this.runtimePanel) //
                                                .addComponent(this.yearPanel) //
                                                .addComponent(this.genrePanel) //
                                                .addComponent(this.shortDescrLabel)) //
                        )// END: (Poster & Stuff)
                );
        
        groupLayout.setVerticalGroup( //
                groupLayout.createSequentialGroup() // START: (Poster & Stuff) & Shortdesc
                        .addGroup( // START: (Poster & Stuff)
                                groupLayout.createParallelGroup() //
                                        .addComponent(this.posterPanel) //
                                        .addGroup( // START: Stuff
                                                groupLayout.createSequentialGroup()//
                                                        .addComponent(this.titlePanel)//
                                                        .addComponent(this.starRater) //
                                                        .addComponent(this.runtimePanel) //
                                                        .addComponent(this.yearPanel) //
                                                        .addComponent(this.genrePanel) //
                                                        .addComponent(this.shortDescrLabel))//
                        )// END: (Poster & Stuff)
                );
        
        Container contentPane = this.detailsDialog.getContentPane();
        contentPane.add(this.posterPanel);
        contentPane.add(this.titlePanel);
        contentPane.add(this.starRater);
        contentPane.add(this.yearPanel);
        contentPane.add(this.genrePanel);
        contentPane.add(this.shortDescrLabel);
        
        this.detailsDialog.doLayout();
        this.detailsDialog.pack();
        Dialogs.centerWindow(this.detailsDialog);
        
    }
    
    public void setActionListener(ActionListener actionListener) {
        
    }
    
    public void setVisible(boolean isVisible) {
        this.detailsDialog.setVisible(isVisible);
    }
    
}

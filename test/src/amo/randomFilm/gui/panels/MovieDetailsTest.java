/**
 * 
 */
package amo.randomFilm.gui.panels;

import java.awt.Color;
import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.GenrePanel;
import amo.randomFilm.gui.panels.moviepanel.PosterPanel;
import amo.randomFilm.gui.panels.moviepanel.RuntimePanel;
import amo.randomFilm.gui.panels.moviepanel.StarRater;
import amo.randomFilm.gui.panels.moviepanel.TitlePanel;
import amo.randomFilm.gui.panels.moviepanel.YearPanel;
import amo.randomFilm.model.Movie;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 11.12.2011
 * 
 */
public class MovieDetailsTest extends AbstractTestBase {
    
    public static void main(String[] args) {
        
        JFrame parentFrame = new JFrame();
        parentFrame.pack();
        parentFrame.setVisible(true);
        
        Movie movie = getDummyMovie();
        
        TitlePanel titlePanel = new TitlePanel(movie.getMovieTitle());
        StarRater starRater = new StarRater(10);
        starRater.setRating((float) movie.getMovieRating());
        RuntimePanel runtimePanel = new RuntimePanel(movie.getMovieRuntime());
        YearPanel yearPanel = new YearPanel(movie.getMovieYear());
        GenrePanel genrePanel = new GenrePanel(movie.getMovieGenres());
        JLabel shortDescrLabel = new JLabel(movie.getMovieShortDescription());
        PosterPanel posterPanel = new PosterPanel(movie.getMovieImage());
        
        // init dialog
        JDialog detailsDialog = new JDialog(parentFrame, "Movie Info: " + movie.getMovieTitle());
        detailsDialog.setModal(true);
        detailsDialog.getContentPane().setBackground(GuiConstants.BG_COLOR);
        
        // do layout
        GroupLayout groupLayout = new GroupLayout(detailsDialog.getContentPane());
        detailsDialog.setLayout(groupLayout);
        groupLayout.setHorizontalGroup( //
                groupLayout.createParallelGroup() // START: (Poster & Stuff) & Shortdesc
                        .addGroup(groupLayout.createSequentialGroup() // START: (Poster & Stuff)
                                .addComponent(posterPanel)//
                                .addGroup( // START: Stuff
                                        groupLayout.createParallelGroup(Alignment.LEADING) //
                                                .addComponent(titlePanel)//
                                                .addComponent(starRater) //
                                                .addComponent(runtimePanel) //
                                                .addComponent(yearPanel) //
                                                .addComponent(genrePanel)) //
                        )// END: (Poster & Stuff)
                        .addComponent(shortDescrLabel));
        
        groupLayout.setVerticalGroup( //
                groupLayout.createSequentialGroup() // START: (Poster & Stuff) & Shortdesc
                        .addGroup( // START: (Poster & Stuff)
                                groupLayout.createParallelGroup() // 
                                        .addComponent(posterPanel, 500, 500, 1000) //
                                        .addGroup( // START: Stuff
                                                groupLayout.createSequentialGroup()//
                                                        .addComponent(titlePanel)//
                                                        .addComponent(starRater) //
                                                        .addComponent(runtimePanel) //
                                                        .addComponent(yearPanel) //
                                                        .addComponent(genrePanel))//
                        )// END: (Poster & Stuff)
                        .addComponent(shortDescrLabel));
        
        Container contentPane = detailsDialog.getContentPane();
        contentPane.add(posterPanel);
        contentPane.add(titlePanel);
        contentPane.add(starRater);
        contentPane.add(yearPanel);
        contentPane.add(genrePanel);
        contentPane.add(shortDescrLabel);
        
        posterPanel.setBackground(Color.blue);
        
        detailsDialog.doLayout();
        detailsDialog.pack();
        detailsDialog.setVisible(true);
        
        System.exit(0);
    }
}

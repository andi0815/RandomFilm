/**
 * 
 */
package amo.randomFilm.gui.panels;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.junit.Test;

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
    
    @Test
    public void testDialog() {
        JFrame parentFrame = new JFrame();
        parentFrame.pack();
        parentFrame.setVisible(true);
        
        MovieDetailsView movieDetailsView = new MovieDetailsView(getDummyMovie());
        movieDetailsView.setVisible(true);
    }
    
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
        JTextArea shortDescrLabel = new JTextArea("SHORT DESCRIPTION: " + movie.getMovieShortDescription());
        shortDescrLabel.setLineWrap(true);
        PosterPanel posterPanel = new PosterPanel(movie.getMovieImage());
        posterPanel.setCenterVertically(false);
        posterPanel.setMinimumSize(new Dimension(250, 0));
        
        // init dialog
        JDialog detailsDialog = new JDialog(parentFrame, "Movie Info: " + movie.getMovieTitle());
        detailsDialog.setMinimumSize(new Dimension(700, 400));
        detailsDialog.setModal(true);
        detailsDialog.getContentPane().setBackground(GuiConstants.BG_COLOR);
        
        // do layout
        GroupLayout groupLayout = new GroupLayout(detailsDialog.getContentPane());
        groupLayout.setAutoCreateGaps(true);
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
                                                .addComponent(genrePanel) //
                                                .addComponent(shortDescrLabel)) //
                        )// END: (Poster & Stuff)
                );
        
        groupLayout.setVerticalGroup( //
                groupLayout.createSequentialGroup() // START: (Poster & Stuff) & Shortdesc
                        .addGroup( // START: (Poster & Stuff)
                                groupLayout.createParallelGroup() //
                                        .addComponent(posterPanel) //
                                        .addGroup( // START: Stuff
                                                groupLayout.createSequentialGroup()//
                                                        .addComponent(titlePanel)//
                                                        .addComponent(starRater) //
                                                        .addComponent(runtimePanel) //
                                                        .addComponent(yearPanel) //
                                                        .addComponent(genrePanel) //
                                                        .addComponent(shortDescrLabel))//
                        )// END: (Poster & Stuff)
                );
        
        Container contentPane = detailsDialog.getContentPane();
        contentPane.add(posterPanel);
        contentPane.add(titlePanel);
        contentPane.add(starRater);
        contentPane.add(yearPanel);
        contentPane.add(genrePanel);
        contentPane.add(shortDescrLabel);
        
        detailsDialog.doLayout();
        detailsDialog.pack();
        detailsDialog.setVisible(true);
        
        System.exit(0);
    }
}

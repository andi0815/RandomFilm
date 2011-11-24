package amo.randomFilm.gui.panels;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import org.apache.log4j.BasicConfigurator;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsView;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelBasicView;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 06.11.2011
 */
public class MovieSelectorTest extends AbstractTestBase {
    
//    @Test
//    public void testSelectorDialog() throws Exception {
    public static void main(String[] args) {
        
        BasicConfigurator.configure();
        
        MoviePanelBasicView mpv1 = new MoviePanelWithButtonsView(new File("file://test.it"));
        MoviePanelBasicView mpv2 = new MoviePanelWithButtonsView(new File("file://test.it_2"));
        MoviePanelBasicView mpv3 = new MoviePanelWithButtonsView(new File("file://test.it_3"));
        MoviePanelBasicView mpv3_nochmal = new MoviePanelWithButtonsView(new File("file://test.it_3"));
//        showComponent(mpv1.getComponent(), 1000);
        
        SelectableMoviePanelView movieSelector = new SelectableMoviePanelView();
//        movieSelector.addData(mpv1);
//        movieSelector.addData(mpv2);
//        movieSelector.addData(mpv3);
//        movieSelector.addData(mpv3_nochmal);
        
        SelectableMoviePanelPresenter selectableMoviePanelPresenter = new SelectableMoviePanelPresenter(movieSelector);
        
//        JPanel movieSelector = new JPanel();
//        movieSelector.add(mpv1.getComponent());
//        movieSelector.add(mpv2.getComponent());
//        movieSelector.add(mpv3.getComponent());
//        movieSelector.add(mpv3_nochmal.getComponent());
        
        movieSelector.getComponent().setPreferredSize(new Dimension(500, 500));
//        movieSelector.resizePanel();
        
        JFrame jFrame = new JFrame();
//        jFrame.setPreferredSize(new Dimension(800, 800));
        jFrame.getContentPane().add(movieSelector.getComponent());
//        jFrame.getContentPane().add(movieSelector);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//        JDialog selectionDialog = new JDialog();
//        selectionDialog.setModalityType(ModalityType.APPLICATION_MODAL);
//        SelectableMoviePanel selectableMoviePanel = new SelectableMoviePanel();
//        selectionDialog.getContentPane().add(selectableMoviePanel);
//        
//        MoviePanel moviePanel = this.getMovieFile(selectableMoviePanel, getDummyMovieFile());
//        List<? extends Movie> movieAlternatives = moviePanel.getMovieAlternatives();
//        System.out.println(movieAlternatives);
//        selectableMoviePanel.addMovie(movieAlternatives.get(0));
//        selectableMoviePanel.addMovie(movieAlternatives.get(1));
//        
//        selectionDialog.setVisible(true);
//        Thread.sleep(10000);
    }
//    private MoviePanel getMovieFile(SelectableMoviePanel selectableMoviePanel, MovieFile mFile) {
//        return new MoviePanel(mFile.getFile(), mFile.getTitle(), TmdbFacade.getInstance(), 500, 110,
//                selectableMoviePanel);
//    }
}

package amo.randomFilm.gui.panels;

import java.awt.Dialog.ModalityType;
import java.util.List;

import javax.swing.JDialog;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.gui.panels.moviepanel.MoviePanel;
import amo.randomFilm.model.MovieFile;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 06.11.2011
 */
public class MovieSelectorTest extends AbstractTestBase {
    
    @Test
    public void testSelectorDialog() throws Exception {
        JDialog selectionDialog = new JDialog();
        selectionDialog.setModalityType(ModalityType.APPLICATION_MODAL);
        SelectableMoviePanel selectableMoviePanel = new SelectableMoviePanel();
        selectionDialog.getContentPane().add(selectableMoviePanel);
        
        MoviePanel moviePanel = this.getMovieFile(selectableMoviePanel, getDummyMovieFile());
        List<? extends Movie> movieAlternatives = moviePanel.getMovieAlternatives();
        System.out.println(movieAlternatives);
        selectableMoviePanel.addMovie(movieAlternatives.get(0));
        selectableMoviePanel.addMovie(movieAlternatives.get(1));
        
        selectionDialog.setVisible(true);
        Thread.sleep(10000);
    }
    
    private MoviePanel getMovieFile(SelectableMoviePanel selectableMoviePanel, MovieFile mFile) {
        return new MoviePanel(mFile.getFile(), mFile.getTitle(), TmdbFacade.getInstance(), 500, 110,
                selectableMoviePanel);
    }
}

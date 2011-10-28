package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.tmdb.DummyMovie;

public class GenrePanelTest extends AbstractTestBase {
    
    @Test
    public void testPanel() throws InterruptedException {
        
        List<String> list = new ArrayList<String>();
        list.add("Drama");
        GenrePanel genrePanel = new GenrePanel(list);
        genrePanel.setPreferredSize(new Dimension(200, 30));
        showComponent(genrePanel, 1000);
        
        // replace Panel
        list.add("Action");
        list.add("Sci-Fi");
        genrePanel = new GenrePanel(list);
        genrePanel.setPreferredSize(new Dimension(200, 30));
        showComponent(genrePanel, 1000);
        
        // Update same Panel
        list.remove(0);
        list.remove(0);
        list.add("UPDATE_TEST");
        DummyMovie movie = new DummyMovie();
        movie.setGenres(list);
        genrePanel.update(movie);
        Thread.sleep(1000);
        
    }
    
    @Test
    public void testNull() {
        GenrePanel genrePanel = new GenrePanel(null);
        genrePanel.setPreferredSize(new Dimension(150, 30));
        showComponent(genrePanel, 1000);
    }
    
    @Test
    public void testEmptyList() {
        List<String> list = new ArrayList<String>();
        GenrePanel genrePanel = new GenrePanel(list);
        genrePanel.setPreferredSize(new Dimension(150, 30));
        showComponent(genrePanel, 1000);
    }
}

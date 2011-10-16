package amo.randomFilm.gui.panels;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

public class GenrePanelTest extends AbstractTestBase {
    
    @Test
    public void testPanel() {
        
        List<String> list = new ArrayList<String>();
        list.add("Drama");
        GenrePanel genrePanel = new GenrePanel(list);
        genrePanel.setPreferredSize(new Dimension(150, 30));
        showComponent(genrePanel, 3000);
        
        list.add("Action");
        list.add("Sci-Fi");
        genrePanel = new GenrePanel(list);
        genrePanel.setPreferredSize(new Dimension(150, 30));
        showComponent(genrePanel, 3000);
    }
}

package amo.randomFilm.gui.panels;

import java.awt.Dimension;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

public class MovieInfoPanelTest extends AbstractTestBase {
    
    @Test
    public void testPanel() {
        MovieInfoPanel movieInfoPanel = new MovieInfoPanel(getDummyMovie(), "file://path/to/file.ext");
        movieInfoPanel.setPreferredSize(new Dimension(500, 90));
        showComponent(movieInfoPanel, 3000);
    }
}

package amo.randomFilm.gui.panels.moviepanel;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.gui.panels.moviepanel.PathPanel;

public class PathPanelTest extends AbstractTestBase {
    
    @Test
    public void testPanel() {
        PathPanel pathPanel = new PathPanel("file://path/to/file.ext");
        showComponent(pathPanel, 3000);
    }
    
}

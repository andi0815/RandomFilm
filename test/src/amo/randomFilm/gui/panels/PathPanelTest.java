package amo.randomFilm.gui.panels;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

public class PathPanelTest extends AbstractTestBase {
    
    @Test
    public void testPanel() {
        PathPanel pathPanel = new PathPanel("file://path/to/file.ext");
        showComponent(pathPanel, 3000);
    }
    
}

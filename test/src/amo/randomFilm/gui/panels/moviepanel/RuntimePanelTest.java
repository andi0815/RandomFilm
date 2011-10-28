package amo.randomFilm.gui.panels.moviepanel;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.gui.panels.moviepanel.RuntimePanel;

public class RuntimePanelTest extends AbstractTestBase {
    
    @Test
    public void testPanel() {
        RuntimePanel runtimePanel = new RuntimePanel(123);
        showComponent(runtimePanel, 3000);
    }
    
}

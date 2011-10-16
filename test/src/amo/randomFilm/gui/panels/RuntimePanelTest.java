package amo.randomFilm.gui.panels;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

public class RuntimePanelTest extends AbstractTestBase {
    
    @Test
    public void testPanel() {
        RuntimePanel runtimePanel = new RuntimePanel(123);
        showComponent(runtimePanel, 3000);
    }
    
}

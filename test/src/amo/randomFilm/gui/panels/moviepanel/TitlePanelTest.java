package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.gui.panels.moviepanel.TitlePanel;

public class TitlePanelTest extends AbstractTestBase {
    
    @Test
    public void testTitle() {
        TitlePanel titlePanel = new TitlePanel("This is my title");
        titlePanel.setPreferredSize(new Dimension(300, 30));
        showComponent(titlePanel, 3000);
    }
    
    @Test
    public void testLongTitle() {
        TitlePanel titlePanel = new TitlePanel(
                "This is my very long title that does not fit into the given parent component");
        titlePanel.setPreferredSize(new Dimension(300, 30));
        showComponent(titlePanel, 3000);
    }
}

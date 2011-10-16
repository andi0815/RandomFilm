package amo.randomFilm.gui.panels;

import java.awt.Dimension;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

public class YearPanelTest extends AbstractTestBase {
    @Test
    public void testTitle() {
        YearPanel yearPanel = new YearPanel("2012");
        yearPanel.setPreferredSize(new Dimension(300, 30));
        showComponent(yearPanel, 3000);
    }
    
    @Test
    public void testLongTitle() {
        YearPanel yearPanel = new YearPanel("1979-03-09");
        yearPanel.setPreferredSize(new Dimension(300, 30));
        showComponent(yearPanel, 3000);
    }
}

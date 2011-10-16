package amo.randomFilm.gui.panels;

import java.awt.Dimension;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

public class DescriptionPanelTest extends AbstractTestBase {
    
    @Test
    public void testTitle() {
        DescriptionPanel titlePanel = new DescriptionPanel(
                "This is my description, This is my description, This is my description, This is my description, This is my description, This is my description, This is my description, This is my description, This is my description, This is my description, This is my description, ");
        titlePanel.setPreferredSize(new Dimension(300, 30));
        showComponent(titlePanel, 10000);
    }
    
}

package amo.randomFilm.gui.panels;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

public class StarRaterTest extends AbstractTestBase {
    
    @Test
    public void testRater() throws Exception {
        StarRater starRater = new StarRater(10);
        showComponent(starRater, 3000);
        
        System.out.println("-> setSelection(3)");
        starRater.setSelection(3);
        starRater.repaint();
        Thread.sleep(3000);
        
        System.out.println("-> setRating(0.3f)");
        starRater.setRating(0.3f);
        starRater.repaint();
        Thread.sleep(3000);
        
        System.out.println("-> setRating(0.9f)");
        starRater.setRating(0.9f);
        starRater.repaint();
        Thread.sleep(3000);
        
        System.out.println("-> setSelection(2)");
        starRater.setSelection(2);
        starRater.repaint();
        Thread.sleep(3000);
    }
    
}

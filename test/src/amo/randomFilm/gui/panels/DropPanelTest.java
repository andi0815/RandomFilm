package amo.randomFilm.gui.panels;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

public class DropPanelTest extends AbstractTestBase {
    
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    @Test
    public void testAddItems() {
        
        // generate List of Files
        File file = new File("test" + FILE_SEPARATOR + "src" + FILE_SEPARATOR + "testMovieFolder");
        File[] filesInFolder = file.listFiles();
        System.out.println(Arrays.toString(filesInFolder));
        List filesList = new ArrayList();
        for (int i = 0; i < filesInFolder.length; i++) {
            if (i == 2 || i == 3) // FIXME: Remove Me
                filesList.add(filesInFolder[i]);
        }
        
        // do Test
        DropPanel dropPanel = new DropPanel(null);
        // dropPanel.init(500, 500);
        dropPanel.setVisible(true);
        dropPanel.addItems(filesList);
        
        showComponent(dropPanel, 60000);
        
    }
}

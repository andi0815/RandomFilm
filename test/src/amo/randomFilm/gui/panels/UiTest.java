package amo.randomFilm.gui.panels;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import org.junit.Test;
import org.uispec4j.Panel;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.Window;
import org.uispec4j.interception.MainClassAdapter;

import amo.randomFilm.RandomFilm;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 18.10.2011
 */
public class UiTest extends UISpecTestCase {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    protected void setUp() throws Exception {
        setAdapter(new MainClassAdapter(RandomFilm.class, new String[0]));
    }
    
    @Test
    public void testAddItems() throws InterruptedException {
        // generate List of Files
        File file = new File("test" + FILE_SEPARATOR + "src" + FILE_SEPARATOR + "testMovieFolder");
        File[] filesInFolder = file.listFiles();
        System.out.println(Arrays.toString(filesInFolder));
        List filesList = new ArrayList();
        for (int i = 0; i < filesInFolder.length; i++) {
            if (i == 2 || i == 3) // FIXME: Remove Me
                filesList.add(filesInFolder[i]);
        }
        
        // 1. Retrieve the components
        Window window = getMainWindow();
        Panel panel = window.getPanel("contentPane");
        Panel scrollPanel = panel.getPanel("ScrollPaneForDropPanel");
        Panel dropPanel = scrollPanel.getPanel();
        System.out.println("PANEL: " + dropPanel.getName());
        DropPanel dropPanel_ = (DropPanel) dropPanel.getAwtComponent();
        // dropPanel_.addItems(filesList);
        
        JFrame frame = (JFrame) window.getAwtComponent();
        frame.setVisible(true);
        Thread.sleep(30000);
        
    }
    
}

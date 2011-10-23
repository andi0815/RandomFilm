package amo.randomFilm.gui.panels;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.gui.MainFrame;

public class DropPanelTest extends AbstractTestBase {
    
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
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
        
        // // do Test
        // DropPanel dropPanel = new DropPanel(null);
        // dropPanel.init(480, 500);
        // dropPanel.setVisible(true);
        // dropPanel.addItems(filesList);
        //
        // JScrollPane scrollPane = new JScrollPane(dropPanel,
        // JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        // JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // scrollPane.setBounds(0, 0, 500, 600);
        // scrollPane.setVisible(true);
        //
        // showComponent(scrollPane, 60000);
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.init(550);
        mainFrame.setMinimumSize(new Dimension(400, 500));
        mainFrame.setVisible(true);
        
        mainFrame.doLayout();
        mainFrame.pack();
        
        Thread.sleep(60000);
        
    }
}

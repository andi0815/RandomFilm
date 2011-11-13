package amo.randomFilm.gui.panels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.tmdb.TmdbFacade;

public class DropPanelTest extends AbstractTestBase {
    
    @Test
    public void testAddDuplicates() {
        List<File> filesList = this.getFilesIinMovieFolder();
        
        // do Test
        DropPanel dropPanel = new DropPanel(null);
        dropPanel.addMovieFiles(filesList, TmdbFacade.getInstance());
        int numMoviesFound = dropPanel.getComponentCount();
        dropPanel.addMovieFiles(filesList, TmdbFacade.getInstance());
        Assert.assertEquals(numMoviesFound, dropPanel.getComponentCount());
        
    }
    
    private List<File> getFilesIinMovieFolder() {
        File[] filesInFolder = this.MOVIE_TEST_FOLDER.listFiles();
//        System.out.println("Files in Folder: " + Arrays.toString(filesInFolder));
        List<File> filesList = new ArrayList();
        for (int i = 0; i < filesInFolder.length; i++) {
            filesList.add(filesInFolder[i]);
        }
        return filesList;
    }
}

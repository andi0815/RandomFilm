package amo.randomFilm.gui.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.panels.MoviePanel;

public class FileListHandler implements Comparator<MoviePanel> {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(FileListHandler.class);
    
    /** list of movies */
    private List<MoviePanel> list = new ArrayList<MoviePanel>();
    
    /**
     * Inserts given item in the movie list
     * 
     * @param item
     *            item to insert
     * @return <code>true</code>, if has been inserted, <code>false</code> if it already is in the
     *         list.
     */
    public boolean insertItem(MoviePanel item) {
        if (!this.contains(item)) {
            this.list.add(item);
            return true;
        } else {
            logger.debug("File " + item.getFile().getAbsolutePath() + " is already in list!");
            return false;
        }
        
    }
    
    public boolean contains(MoviePanel item) {
        return this.list.contains(item);
    }
    
    public List<MoviePanel> getList() {
        return this.list;
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public void clearList() {
        this.list.clear();
    }
    
    public void remove(MoviePanel item) {
        this.list.remove(item);
    }
    
    /**
     * TODO: add sort-attribute to method
     */
    public void sort() {
        Collections.sort(this.list, this);
    }
    
    /**
     * TODO: currently only sorts by file, implement other options
     */
    @Override
    public int compare(MoviePanel arg0, MoviePanel arg1) {
        File file1 = arg0.getFile();
        File file2 = arg1.getFile();
        return file1.compareTo(file2);
    }
    
}

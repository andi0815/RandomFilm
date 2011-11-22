package amo.randomFilm.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.panels.moviepanel.MoviePanelViewBasic;

public class FileListHandler implements Comparator<MoviePanelViewBasic> {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(FileListHandler.class);
    
    /** list of movies */
    private List<MoviePanelViewBasic> list = new ArrayList<MoviePanelViewBasic>();
    
    /**
     * Inserts given item in the movie list
     * 
     * @param item
     *            item to insert
     * @return <code>true</code>, if has been inserted, <code>false</code> if it already is in the
     *         list.
     */
    public boolean insertItem(MoviePanelViewBasic item) {
        if (!this.contains(item)) {
            this.list.add(item);
            return true;
        } else {
            logger.debug("File " + item.getFile() + " is already in list!");
            return false;
        }
        
    }
    
    public boolean contains(MoviePanelViewBasic item) {
        return this.list.contains(item);
    }
    
    public boolean contains(File file) {
        for (MoviePanelViewBasic moviePanel : this.list) {
            if (moviePanel.getFile().equals(file)) {
                return true;
            }
        }
        return false;
    }
    
    public List<MoviePanelViewBasic> getList() {
        return this.list;
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public void clearList() {
        this.list.clear();
    }
    
    public void remove(MoviePanelViewBasic item) {
        this.list.remove(item);
    }
    
    /**
     * TODO: add sort-attribute to method
     */
    public void sort() {
        Collections.sort(this.list, this);
    }
    
    /**
     * TODO: currently only sorts by title, implement other options
     */
    @Override
    public int compare(MoviePanelViewBasic arg0, MoviePanelViewBasic arg1) {
        String title1 = arg0.getData().getMovieTitle();
        String title2 = arg1.getData().getMovieTitle();
        return title1.compareTo(title2);
    }
    
}

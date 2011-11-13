package amo.randomFilm.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.MovieDataProvider;
import amo.randomFilm.gui.panels.moviepanel.MoviePanel;
import amo.randomFilm.gui.util.FileListHandler;
import amo.randomFilm.model.FilenameFilter;
import amo.randomFilm.model.MovieFile;

public class SelectableMoviePanel extends JPanel implements ActionListener {
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(SelectableMoviePanel.class);
    
    /** Color of Font */
    private static final Color DROPPER_FONT_COLOR = Color.BLACK;
    
    /** Label for empty drop panel */
    private static final String TEXT_EMPTY_DROPPER = "Feed Me!!!";
    
    /** font style for empty drop panel */
    private static final Font EMPTY_DROPPER_FONT = new Font("SansSerif", Font.BOLD, 20);
    
    /** Background color */
    private static final Color BG_COLOR = Color.WHITE;
    
    /** height of a MoviePanel */
    private static final int ITEM_HEIGHT = 110;
    
    /** Manages the list of MoviePanels */
    private FileListHandler listHandler = new FileListHandler();
    
    /**
     * Standard Constructor.
     * 
     * @param parent
     *            the Window that is displaying this Panel
     */
    public SelectableMoviePanel() {
        super();
        this.setName(this.getClass().getSimpleName());
        this.setBackground(BG_COLOR);
        this.setLayout(null); // do it yourself layout
    }
    
    /**
     * Tries to add given files to the list of movies.
     * 
     * @param fileList
     *            the List of files to add
     */
    void addMovieFiles(List<File> fileList, MovieDataProvider movieDataProvider) {
        List<MovieFile> movieFiles = FilenameFilter.getMovieNames(fileList);
        for (MovieFile movieFile : movieFiles) {
            if (!this.listHandler.contains(movieFile.getFile())) {
                MoviePanel item = new MoviePanel(movieFile.getFile(), movieFile.getTitle(), movieDataProvider,
                        this.getWidth(), this.ITEM_HEIGHT, this);
                item.setBounds(2, (this.getComponentCount() * this.ITEM_HEIGHT) + 2, this.getWidth() - 4,
                        this.ITEM_HEIGHT);
                
                if (this.listHandler.insertItem(item))
                    this.add(item);
            }
        }
    }
    
    /**
     * Adds given Movie to the list of movies. duplicates are not checked !!!!
     * 
     * @param movie
     *            the movie to add
     */
    void addMovie(Movie movie) {
//        if (!this.listHandler.contains(moviePanel.getFile())) {
        MoviePanel moviePanel = new MoviePanel(movie, this.getWidth(), this.ITEM_HEIGHT, this);
        moviePanel.setBounds(2, (this.getComponentCount() * this.ITEM_HEIGHT) + 2, this.getWidth() - 4,
                this.ITEM_HEIGHT);
        if (this.listHandler.insertItem(moviePanel))
            this.add(moviePanel);
//        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (this.listHandler.isEmpty()) {
            // text
            int halfWidth = (int) (this.getWidth() / 2.0);
            int halfheight = (int) (this.getHeight() / 2.0);
            g.setColor(DROPPER_FONT_COLOR);
            g.setFont(EMPTY_DROPPER_FONT);
            g.drawString(TEXT_EMPTY_DROPPER, halfWidth - 120, halfheight + 5);
        }
    }
    
    /**
     * Automatically resizes this panel according to the number of movie items
     */
    protected void resizePanel() {
        this.setPreferredSize(new Dimension(this.getWidth(), (this.getComponentCount() * (this.ITEM_HEIGHT)) + 2));
        this.revalidate();
    }
    
    protected FileListHandler getFileListHandler() {
        return this.listHandler;
    }
    
    protected void resortList() {
        this.listHandler.sort();
    }
    
    protected void resetComponentBounds() {
        int nextY = 2;
        JComponent component;
        Rectangle rect;
        
        Iterator<MoviePanel> iter = this.listHandler.getList().iterator();
        
        while (iter.hasNext()) {
            component = iter.next();
            rect = component.getBounds();
            if (rect.getY() != nextY) {
                component.setBounds(2, nextY, this.getWidth() - 4, this.ITEM_HEIGHT);
            }
            nextY += this.ITEM_HEIGHT;
        }
        
    }
    
    protected void removeMoviePanel(MoviePanel movie) {
        this.remove(movie);
        this.listHandler.remove(movie);
        this.resetComponentBounds();
        this.resizePanel();
        this.repaint();
    }
    
    protected Iterator<MoviePanel> getMovieIterator() {
        return this.listHandler.getList().iterator();
    }
    
    protected void removeAllMovies() {
        this.removeAll();
        this.listHandler.clearList();
        this.resizePanel();
        this.repaint();
    }
    
    /**
     * @return a random movie from the list of movies or <code>null</code> if none is available.
     */
    protected MoviePanel getRandomMovie() {
        List<MoviePanel> listOfItems = this.listHandler.getList();
        if (listOfItems.size() > 0) {
            Random random = new Random(new Date().getTime());
            return listOfItems.get(random.nextInt(listOfItems.size()));
        } else {
            return null;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        logger.warn("Action Listener not implemented for event: " + e);
    }
    
}
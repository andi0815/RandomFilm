package amo.randomFilm.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelViewBasic;
import amo.randomFilm.gui.util.FileListHandler;

public class SelectableMoviePanelView {
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(SelectableMoviePanelView.class);
    
    /** Color of Font */
    private static final Color DROPPER_FONT_COLOR = Color.BLACK;
    
    /** Label for empty drop panel */
    private static final String TEXT_EMPTY_DROPPER = "Feed Me!!!";
    
    /** font style for empty drop panel */
    private static final Font EMPTY_DROPPER_FONT = new Font("SansSerif", Font.BOLD, 20);
    
    /** height of a MoviePanel */
    private static final int ITEM_HEIGHT = 110;
    
    /** Manages the list of MoviePanels */
    private FileListHandler listHandler = new FileListHandler();
    
    /** reference to the panel */
    private JPanel dropperPanel;
    
    /**
     * Standard Constructor.
     * 
     * @param parent
     *            the Window that is displaying this Panel
     */
    public SelectableMoviePanelView() {
        super();
        this.dropperPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                logger.debug("Paint Dropper: "
                        + (SelectableMoviePanelView.this.listHandler.isEmpty() ? "empty List"
                                : SelectableMoviePanelView.this.listHandler.getList().size()));
                if (SelectableMoviePanelView.this.listHandler.isEmpty()) {
                    // text
                    int halfWidth = (int) (this.getWidth() / 2.0);
                    int halfheight = (int) (this.getHeight() / 2.0);
                    g.setColor(DROPPER_FONT_COLOR);
                    g.setFont(EMPTY_DROPPER_FONT);
                    g.drawString(TEXT_EMPTY_DROPPER, halfWidth - 120, halfheight + 5);
                }
                logger.debug("My DropTarget Listener: " + this.getDropTarget());
            }
        };
        this.dropperPanel.setName(this.dropperPanel.getClass().getSimpleName());
        this.dropperPanel.setBackground(GuiConstants.BG_COLOR);
//        this.dropperPanel.setLayout(null); // do it yourself layout
    }
    
//    /**
//     * Tries to add given files to the list of movies.
//     * 
//     * @param fileList
//     *            the List of files to add
//     */
//    void addMovieFiles(List<File> fileList, MovieDataProvider movieDataProvider) {
//        List<MovieFile> movieFiles = FilenameFilter.getMovieNames(fileList);
//        for (MovieFile movieFile : movieFiles) {
//            if (!this.listHandler.contains(movieFile.getFile())) {
//                MoviePanel item = new MoviePanel(movieFile.getFile(), movieFile.getTitle(), movieDataProvider,
//                        this.getWidth(), this.ITEM_HEIGHT, this);
//                item.setBounds(2, (this.getComponentCount() * this.ITEM_HEIGHT) + 2, this.getWidth() - 4,
//                        this.ITEM_HEIGHT);
//                
//                if (this.listHandler.insertItem(item))
//                    this.add(item);
//            }
//        }
//    }
//    
//    /**
//     * Adds given Movie to the list of movies. duplicates are not checked !!!!
//     * 
//     * @param movie
//     *            the movie to add
//     */
//    void addMovie(Movie movie) {
////        if (!this.listHandler.contains(moviePanel.getFile())) {
//        MoviePanel moviePanel = new MoviePanel(movie, this.getWidth(), this.ITEM_HEIGHT, this);
//        moviePanel.setBounds(2, (this.getComponentCount() * this.ITEM_HEIGHT) + 2, this.getWidth() - 4,
//                this.ITEM_HEIGHT);
//        if (this.listHandler.insertItem(moviePanel))
//            this.add(moviePanel);
////        }
//    }
    
    protected FileListHandler getFileListHandler() {
        return this.listHandler;
    }
    
    protected void resortList() {
        this.listHandler.sort();
    }
    
//    protected void resetComponentBounds() {
//        int nextY = 2;
//        MoviePanelViewNoButtons component;
//        Rectangle rect;
//        
//        Iterator<MoviePanelViewNoButtons> iter = this.listHandler.getList().iterator();
//        
//        while (iter.hasNext()) {
//            component = iter.next();
//            rect = component.getBounds();
//            if (rect.getY() != nextY) {
//                component.setBounds(2, nextY, this.dropperPanel.getWidth() - 4, this.ITEM_HEIGHT);
//            }
//            nextY += this.ITEM_HEIGHT;
//        }
//        
//    }
    
    /**
     * Automatically resizes this panel according to the number of movie items
     */
    protected void resizePanel() {
        this.dropperPanel.setPreferredSize(new Dimension(this.dropperPanel.getWidth(), (this.dropperPanel
                .getComponentCount() * (this.ITEM_HEIGHT)) + 2));
        this.dropperPanel.revalidate();
    }
    
    public void addData(MoviePanelViewBasic movie) {
        this.listHandler.insertItem(movie);
        this.dropperPanel.add(movie.getComponent());
        
    }
    
    protected void removeData(MoviePanelViewBasic movie) {
        this.dropperPanel.remove(movie.getComponent());
        this.listHandler.remove(movie);
//        this.resetComponentBounds();
        this.resizePanel();
        this.dropperPanel.repaint();
    }
    
//    protected Iterator<MoviePanelViewNoButtons> getMovieIterator() {
//        return this.listHandler.getList().iterator();
//    }
    
    protected void removeAllMovies() {
        this.dropperPanel.removeAll();
        this.listHandler.clearList();
        this.resizePanel();
        this.dropperPanel.repaint();
    }
    
    public JPanel getComponent() {
        return this.dropperPanel;
    }
    
}
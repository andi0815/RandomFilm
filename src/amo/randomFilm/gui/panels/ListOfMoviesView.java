package amo.randomFilm.gui.panels;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelBasicView;
import amo.randomFilm.model.FileListHandler;

public class ListOfMoviesView {
    
    /** Manages the list of MoviePanels */
    private FileListHandler listHandler = new FileListHandler();
    
    /** reference to the panel */
    private JPanel moviesPanel;
    
    /**
     * Standard Constructor.
     * 
     * @param parent
     *            the Window that is displaying this Panel
     */
    public ListOfMoviesView() {
        super();
        this.moviesPanel = new JPanel()
        // {
        //
        // private static final long serialVersionUID = 1L;
        // // @Override
        // // public void paint(Graphics g) {
        // // super.paint(g);
        // // logger.debug("Paint Dropper: "
        // // + (SelectableMoviePanelView.this.listHandler.isEmpty() ? "empty List"
        // // : SelectableMoviePanelView.this.listHandler.getList().size()));
        // // if (SelectableMoviePanelView.this.listHandler.isEmpty()) {
        // // // text
        // // int halfWidth = (int) (this.getWidth() / 2.0);
        // // int halfheight = (int) (this.getHeight() / 2.0);
        // // g.setColor(DROPPER_FONT_COLOR);
        // // g.setFont(EMPTY_DROPPER_FONT);
        // // g.drawString(TEXT_EMPTY_DROPPER, halfWidth - 120, halfheight + 5);
        // // }
        // // logger.debug("My DropTarget Listener: " + this.getDropTarget());
        // // }
        // }
        ;
        this.moviesPanel.setName(this.moviesPanel.getClass().getSimpleName());
        this.moviesPanel.setBackground(GuiConstants.BG_COLOR);
        this.moviesPanel.setLayout(new BoxLayout(this.moviesPanel, BoxLayout.Y_AXIS));
    }
    
    protected FileListHandler getFileListHandler() {
        return this.listHandler;
    }
    
    protected void resortList() {
        this.listHandler.sort();
    }
    
    /**
     * Automatically resizes this panel according to the number of movie items
     */
    protected void resizePanel() {
        this.moviesPanel.setPreferredSize(new Dimension(this.moviesPanel.getWidth(), (this.moviesPanel
                .getComponentCount() * (GuiConstants.HEIGHT_MOVIE_PANEL)) + 2));
        this.moviesPanel.revalidate();
    }
    
    public void addData(MoviePanelBasicView movie) {
        this.listHandler.insertItem(movie);
        JComponent moviePanelToAdd = movie.getComponent();
        moviePanelToAdd.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.moviesPanel.add(moviePanelToAdd);
        
    }
    
    protected void removeData(MoviePanelBasicView movie) {
        this.moviesPanel.remove(movie.getComponent());
        this.listHandler.remove(movie);
        this.resizePanel();
        this.moviesPanel.repaint();
    }
    
    protected void removeAllMovies() {
        this.moviesPanel.removeAll();
        this.listHandler.clearList();
        this.resizePanel();
        this.moviesPanel.repaint();
    }
    
    public JPanel getComponent() {
        return this.moviesPanel;
    }
    
}
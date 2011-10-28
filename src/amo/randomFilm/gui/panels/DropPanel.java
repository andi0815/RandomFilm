package amo.randomFilm.gui.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.MovieDataProvider;
import amo.randomFilm.datasource.exception.MovieDataProviderException;
import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.gui.GuiLabels;
import amo.randomFilm.gui.util.Dialogs;
import amo.randomFilm.gui.util.FileListHandler;
import amo.randomFilm.model.FilenameFilter;
import amo.randomFilm.model.MovieFile;

public class DropPanel extends JPanel implements DropTargetListener, DragSourceListener, DragGestureListener,
        ActionListener {
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(DropPanel.class);
    
    /** Color of Font */
    private static final Color DROPPER_FONT_COLOR = Color.BLACK;
    
    /** Label for empty drop panel */
    private static final String TEXT_EMPTY_DROPPER = "Bewirf mich mit Dateien!";
    
    /** font style for empty drop panel */
    private static final Font EMPTY_DROPPER_FONT = new Font("SansSerif", Font.BOLD, 20);
    
    /** Background color */
    private static final Color BG_COLOR = Color.WHITE;
    
    /** height of a MoviePanel */
    private static final int ITEM_HEIGHT = 110;
    
    /** Manages the list of MoviePanels */
    private FileListHandler listHandler = new FileListHandler();
    
    JFrame parent = null;
    
    // FIXME: add dynamically ...
    MovieDataProvider movieDataProvider = TmdbFacade.getInstance();
    
    public DropPanel(JFrame parent) {
        super();
        this.setName("DropPanel");
        this.parent = parent;
        this.setBackground(BG_COLOR);
        this.setLayout(null); // do it yourself layout
        
        // create a new Drop Target and associate it with this component
        new DropTarget(this, this);
        
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
     * Teste, ob Dateien gedroppt werden dürfen (nur Dateien dürfen!)
     */
    @Override
    public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
        Transferable tr = dropTargetDragEvent.getTransferable();
        if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
            dropTargetDragEvent.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
        else
            dropTargetDragEvent.rejectDrag();
        
    }
    
    /**
     * Behandle einen Drop-Event.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void drop(DropTargetDropEvent dropTargetDropEvent) {
        List<File> fileList = new ArrayList<File>();
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            Transferable tr = dropTargetDropEvent.getTransferable();
            if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                fileList = (java.util.List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
                this.addItems(fileList);
                logger.debug("finished adding items!");
                dropTargetDropEvent.getDropTargetContext().dropComplete(true);
                
            } else {
                logger.debug("Rejected adding items!");
                dropTargetDropEvent.rejectDrop();
            }
        } catch (IOException io) {
            io.printStackTrace();
            dropTargetDropEvent.rejectDrop();
        } catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
            dropTargetDropEvent.rejectDrop();
        }
        
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        this.listHandler.sort();
        this.resizePanel();
        this.repaint();
    }
    
    /**
     * unbenutzt
     */
    @Override
    public void dragExit(DropTargetEvent arg0) {
    }
    
    /**
     * unbenutzt
     */
    @Override
    public void dragOver(DropTargetDragEvent arg0) {
    }
    
    /**
     * unbenutzt
     */
    @Override
    public void dragOver(DragSourceDragEvent arg0) {
        
    }
    
    /**
     * Automatically resizes this panel according to the number of movie items
     */
    protected void resizePanel() {
        this.setPreferredSize(new Dimension(this.getWidth(), (this.getComponentCount() * (this.ITEM_HEIGHT)) + 2));
        this.revalidate();
    }
    
    /**
     * Tries to add given files to the list of movies.
     * 
     * @param fileList
     *            the List of files to add
     */
    void addItems(List<File> fileList) {
        List<MovieFile> movieFiles = FilenameFilter.getMovieNames(fileList);
        for (MovieFile movieFile : movieFiles) {
            List<? extends Movie> moviesFound = null;
            try {
                moviesFound = this.movieDataProvider.searchMovie(movieFile.getTitle());
//            moviesFound = new ArrayList<Movie>();
//            moviesFound.add(new SimpleMovie("TEst"));
                
            } catch (MovieDataProviderException e) {
                logger.warn("Could not find Movie with title: " + movieFile.getTitle(), e);
                List<SimpleMovie> movieList = new ArrayList<SimpleMovie>();
                movieList.add(new SimpleMovie(movieFile.getTitle()));
                moviesFound = movieList;
            }
            System.out.println("======== ADDITEMS ======");
            MoviePanel item = new MoviePanel(movieFile.getFile(), moviesFound, this.getWidth(), this.ITEM_HEIGHT, this);
            item.setBounds(2, (this.getComponentCount() * this.ITEM_HEIGHT) + 2, this.getWidth() - 4, this.ITEM_HEIGHT);
            
            if (this.listHandler.insertItem(item))
                this.add(item);
        }
    }
    
    public FileListHandler getFileListHandler() {
        return this.listHandler;
    }
    
    @Override
    public void dropActionChanged(DropTargetDragEvent arg0) {
    }
    
    @Override
    public void dragDropEnd(DragSourceDropEvent arg0) {
    }
    
    @Override
    public void dragEnter(DragSourceDragEvent arg0) {
    }
    
    @Override
    public void dragExit(DragSourceEvent arg0) {
    }
    
    @Override
    public void dropActionChanged(DragSourceDragEvent arg0) {
    }
    
    @Override
    public void dragGestureRecognized(DragGestureEvent arg0) {
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_DELETE)) {
            // System.out
            // .println(((JButton) e.getSource()).getParent().getClass());
            MoviePanel item = (MoviePanel) ((JButton) e.getSource()).getParent();
            
            this.remove(item);
            this.listHandler.remove(item);
            this.resetComponentBounds();
            this.resizePanel();
            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_SELECT_ALL)) {
            Iterator<MoviePanel> iter = this.listHandler.getList().iterator();
            while (iter.hasNext()) {
                iter.next().setSelected(true);
            }
            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_SELECT_NOTHING)) {
            Iterator<MoviePanel> iter = this.listHandler.getList().iterator();
            while (iter.hasNext()) {
                iter.next().setSelected(false);
            }
            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_REMOVE_SELECTED)) {
            this.removeAll();
            
            MoviePanel item;
            FileListHandler fileListHandler = new FileListHandler();
            Iterator<MoviePanel> iter = this.listHandler.getList().iterator();
            
            while (iter.hasNext()) {
                item = iter.next();
                if (!item.isSelected()) {
                    if (fileListHandler.insertItem(item))
                        this.add(item);
                }
            }
            
            this.listHandler = fileListHandler;
            this.resetComponentBounds();
            this.resizePanel();
            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_REMOVE_ALL)) {
            this.removeAll();
            this.listHandler.clearList();
            this.resizePanel();
            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_GO)) {
            List<MoviePanel> listOfItems = this.listHandler.getList();
            if (listOfItems.size() > 0) {
                Random random = new Random(new Date().getTime());
                
                MoviePanel listItem = listOfItems.get(random.nextInt(listOfItems.size()));
                
                String filmName = listItem.getFilmName();
                String filmPath = listItem.getFile().getPath();
                Image filmIcon = listItem.getIconImage();
                
                if (Dialogs.showStartFilmDialog(filmName, filmPath, filmIcon, this)) {
                    String executableName = listItem.getExecutableName();
                    this.parent.setAlwaysOnTop(false);
                    
                    if (executableName != null && !executableName.equals("")) {
                        
                        String command = executableName + " \"" + filmPath + "\"";
                        try {
                            Process child = Runtime.getRuntime().exec(command);
                        } catch (IOException e1) {
                            Dialogs.showWarning(
                                    "Kann Datei: " + filmPath + " nicht starten.\nGrund:\n" + e1.getMessage(), this);
                        }
                    }
                } else {
                    logger.debug("Doch nicht ...");
                }
            }
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_DISABLE_ALWAYS_ON_TOP)) {
            this.parent.setAlwaysOnTop(false);
            ButtonPanelDropper.setAlwaysOnTopEnabled(false);
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_ENABLE_ALWAYS_ON_TOP)) {
            this.parent.setAlwaysOnTop(true);
            ButtonPanelDropper.setAlwaysOnTopEnabled(true);
            
        }
        
    }
    
    public void resetComponentBounds() {
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
    
}
package amo.randomFilm.gui.panels;

import java.awt.Cursor;
import java.awt.Window;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelPresenter;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelViewWithButtons;
import amo.randomFilm.model.FilenameFilter;
import amo.randomFilm.model.MovieDataProvider;
import amo.randomFilm.model.MovieFile;

public class SelectableMoviePanelPresenter implements DropTargetListener, DragSourceListener, DragGestureListener {
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(SelectableMoviePanelPresenter.class);
    
    /** the Window displaying this panel */
    Window parent = null;
    
    private SelectableMoviePanelView view;
    
    List<MoviePanelPresenter> moviePresenters = new ArrayList<MoviePanelPresenter>();
    // FIXME: inject ...
    private MovieDataProvider movieDataProvider = TmdbFacade.getInstance();
    
    public SelectableMoviePanelPresenter(SelectableMoviePanelView view) {
        super();
        // create a new Drop Target and associate it with the view and this component
        DropTarget dropTarget = new DropTarget(view.getComponent(), this);
        logger.info("------------- DropTarget: " + dropTarget);
//        try {
//            dropTarget.addDropTargetListener(this);
//        } catch (TooManyListenersException e) {
//            e.printStackTrace();
//        }
        this.view = view;
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
        JPanel viewComponent = this.view.getComponent();
        viewComponent.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            Transferable tr = dropTargetDropEvent.getTransferable();
            if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                fileList = (java.util.List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
                List<MovieFile> movieNames = FilenameFilter.getMovieNames(fileList);
                movieNames = this.removeDuplicateMovies(movieNames);
                Iterator<MovieFile> iterator = movieNames.iterator();
                while (iterator.hasNext()) {
                    MovieFile next = iterator.next();
                    MoviePanelViewWithButtons movieView = new MoviePanelViewWithButtons(next);
                    this.view.addData(movieView);
                    MoviePanelPresenter moviePanelPresenter = new MoviePanelPresenter(next.getFile(), next.getTitle(),
                            movieView, this.movieDataProvider);
                    this.moviePresenters.add(moviePanelPresenter);
                }
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
        
        viewComponent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        // FIXME:
//        this.view.resortList();
        this.view.resizePanel();
//        this.view.getComponent().repaint();
    }
    
    private List<MovieFile> removeDuplicateMovies(List<MovieFile> movieNames) {
        List<MovieFile> newList = new ArrayList<MovieFile>();
        Iterator<MovieFile> iterator = movieNames.iterator();
        while (iterator.hasNext()) {
            MovieFile nextMovie = iterator.next();
            if (!this.view.getFileListHandler().contains(nextMovie.getFile())) {
                newList.add(nextMovie);
            }
        }
        return newList;
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
    
//    @Override
    // FIXME: unused
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_DELETE)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_DELETE + " -> " + e + " Source: " + e.getSource());
            
            // MoviePanelViewNoButtons item = (MoviePanelView) ((JButton)
            // e.getSource()).getParent();
//            this.removeMoviePanel(item);
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_SELECT_ALL)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_SELECT_ALL + " -> " + e + " Source: "
                    + e.getSource());
            
//            Iterator<MoviePanelViewNoButtons> iter = this.getMovieIterator();
//            while (iter.hasNext()) {
//                iter.next().setSelected(true);
//            }
//            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_SELECT_NOTHING)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_SELECT_NOTHING + " -> " + e + " Source: "
                    + e.getSource());
//            Iterator<MoviePanelViewNoButtons> iter = this.getMovieIterator();
//            while (iter.hasNext()) {
//                iter.next().setSelected(false);
//            }
//            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_REMOVE_SELECTED)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_REMOVE_SELECTED + " -> " + e + " Source: "
                    + e.getSource());
//            this.removeAll();
//            
//            MoviePanelViewNoButtons item;
//            FileListHandler fileListHandler = new FileListHandler();
//            Iterator<MoviePanelViewNoButtons> iter = this.getMovieIterator();
//            
//            while (iter.hasNext()) {
//                item = iter.next();
//                if (item.isSelected())
//                    iter.remove();
////                if (!item.isSelected()) {
////                    if (fileListHandler.insertItem(item))
////                        this.add(item);
////                }
//            }
//            
////            this.listHandler = fileListHandler;
//            this.resetComponentBounds();
//            this.resizePanel();
//            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_REMOVE_ALL)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_REMOVE_ALL + " -> " + e + " Source: "
                    + e.getSource());
            
//            this.removeAllMovies();
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_GO)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_GO + " -> " + e + " Source: " + e.getSource());
            
//            MoviePanelViewNoButtons listItem = this.getRandomMovie();
//            if (listItem != null) {
//                
//                String filmName = listItem.getFilmName();
//                String filmPath = listItem.getFile().getPath();
//                Image filmIcon = listItem.getIconImage();
//                
//                if (Dialogs.showStartFilmDialog(filmName, filmPath, filmIcon, this)) {
//                    String executableName = listItem.getExecutableName();
//                    this.parent.setAlwaysOnTop(false);
//                    
//                    if (executableName != null && !executableName.equals("")) {
//                        
//                        String command = executableName + " \"" + filmPath + "\"";
//                        try {
//                            Process child = Runtime.getRuntime().exec(command);
//                        } catch (IOException e1) {
//                            Dialogs.showWarning(
//                                    "Kann Datei: " + filmPath + " nicht starten.\nGrund:\n" + e1.getMessage(), this);
//                        }
//                    }
//                } else {
//                    logger.debug("Doch nicht ...");
//                }
//            }
            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP + " -> " + e + " Source: "
                    + e.getSource());
            
//            this.parent.setAlwaysOnTop(false);
//            ButtonPanelDropper.setAlwaysOnTopEnabled(false);
//            
        } else if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_ENABLE_ALWAYS_ON_TOP)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_ENABLE_ALWAYS_ON_TOP + " -> " + e + " Source: "
                    + e.getSource());
            
//            this.parent.setAlwaysOnTop(true);
//            ButtonPanelDropper.setAlwaysOnTopEnabled(true);
            
        }
        
    }
    
}
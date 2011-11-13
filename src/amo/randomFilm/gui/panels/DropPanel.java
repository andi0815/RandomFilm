package amo.randomFilm.gui.panels;

import java.awt.Cursor;
import java.awt.Image;
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

import javax.swing.JButton;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.MovieDataProvider;
import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.gui.GuiLabels;
import amo.randomFilm.gui.panels.moviepanel.MoviePanel;
import amo.randomFilm.gui.util.Dialogs;
import amo.randomFilm.gui.util.FileListHandler;

public class DropPanel extends SelectableMoviePanel implements DropTargetListener, DragSourceListener,
        DragGestureListener {
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(DropPanel.class);
    
    /** the Window displaying this panel */
    Window parent = null;
    
    // FIXME: add dynamically ...
    MovieDataProvider movieDataProvider = TmdbFacade.getInstance();
    
    public DropPanel(Window parent) {
        super();
        // create a new Drop Target and associate it with this component
        new DropTarget(this, this);
        this.parent = parent;
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
                this.addMovieFiles(fileList, this.movieDataProvider);
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
        this.resortList();
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
            MoviePanel item = (MoviePanel) ((JButton) e.getSource()).getParent();
            this.removeMoviePanel(item);
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_SELECT_ALL)) {
            Iterator<MoviePanel> iter = this.getMovieIterator();
            while (iter.hasNext()) {
                iter.next().setSelected(true);
            }
            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_SELECT_NOTHING)) {
            Iterator<MoviePanel> iter = this.getMovieIterator();
            while (iter.hasNext()) {
                iter.next().setSelected(false);
            }
            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_REMOVE_SELECTED)) {
            this.removeAll();
            
            MoviePanel item;
            FileListHandler fileListHandler = new FileListHandler();
            Iterator<MoviePanel> iter = this.getMovieIterator();
            
            while (iter.hasNext()) {
                item = iter.next();
                if (item.isSelected())
                    iter.remove();
//                if (!item.isSelected()) {
//                    if (fileListHandler.insertItem(item))
//                        this.add(item);
//                }
            }
            
//            this.listHandler = fileListHandler;
            this.resetComponentBounds();
            this.resizePanel();
            this.repaint();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_REMOVE_ALL)) {
            this.removeAllMovies();
            
        } else if (e.getActionCommand().equals(GuiLabels.LABEL_BTN_GO)) {
            MoviePanel listItem = this.getRandomMovie();
            if (listItem != null) {
                
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
    
}
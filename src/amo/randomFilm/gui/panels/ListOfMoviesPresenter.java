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
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelBasicPresenter;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsPresenter;
import amo.randomFilm.gui.panels.moviepanel.MoviePanelWithButtonsView;
import amo.randomFilm.gui.util.Dialogs;
import amo.randomFilm.model.FilenameFilter;
import amo.randomFilm.model.MovieDataProvider;
import amo.randomFilm.model.MovieFile;
import amo.randomFilm.model.UnknownTypes;
import amo.randomFilm.model.sorting.MovieTitleComparator;

public class ListOfMoviesPresenter implements DropTargetListener, DragSourceListener, DragGestureListener, ActionListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(ListOfMoviesPresenter.class);
    
    /** the Window displaying this panel */
    Window parentFrame = null;
    
    private ListOfMoviesView view;
    
    Set<MoviePanelBasicPresenter> moviePresenters = new TreeSet<MoviePanelBasicPresenter>(new MovieTitleComparator());
    
    // FIXME: inject ...
    private MovieDataProvider movieDataProvider = TmdbFacade.getInstance();
    
    private ButtonPanelView buttonPanel;
    
    private JTextField titleTextField;
    
    public ListOfMoviesPresenter(ListOfMoviesView view, ButtonPanelView buttonPanel, Window parentFrame) {
        super();
        this.parentFrame = parentFrame;
        // create a new Drop Target and associate it with the view and this component
        DropTarget dropTarget = new DropTarget(view.getComponent(), this);
        this.view = view;
        this.buttonPanel = buttonPanel;
        this.buttonPanel.addActionListener(this);
    }
    
    /**
     * Teste, ob Dateien gedroppt werden duerfen (nur Dateien duerfen!)
     */
    @Override
    public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
        Transferable tr = dropTargetDragEvent.getTransferable();
        if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            dropTargetDragEvent.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
        } else {
            dropTargetDragEvent.rejectDrag();
        }
        
    }
    
    public void removeMovie(MoviePanelBasicPresenter presenter) {
        this.moviePresenters.remove(presenter);
        this.view.removeData(presenter.getView());
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
                    this.addMovie(next);
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
        // this.view.resortList();
        this.view.resizePanel();
        // this.view.getComponent().repaint();
    }
    
    private void addMovie(MovieFile movieFile) {
        MoviePanelWithButtonsView movieView = new MoviePanelWithButtonsView(movieFile);
        this.view.addData(movieView);
        MoviePanelBasicPresenter moviePanelPresenter = new MoviePanelWithButtonsPresenter(movieFile.getFile(), movieFile.getTitle(), movieView, this,
                this.movieDataProvider);
        this.moviePresenters.add(moviePanelPresenter);
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_SELECT_ALL)) {
            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_SELECT_ALL + " -> " + e + " Source: " + e.getSource());
            Iterator<MoviePanelBasicPresenter> presenters = this.moviePresenters.iterator();
            while (presenters.hasNext()) {
                presenters.next().getView().setSelected(true);
            }
            
        } else
            if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_SELECT_NOTHING)) {
                logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_SELECT_NOTHING + " -> " + e + " Source: " + e.getSource());
                Iterator<MoviePanelBasicPresenter> presenters = this.moviePresenters.iterator();
                while (presenters.hasNext()) {
                    presenters.next().getView().setSelected(false);
                }
                
            } else
                if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_REMOVE_SELECTED)) {
                    logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_REMOVE_SELECTED + " -> " + e + " Source: " + e.getSource());
                    List<MoviePanelBasicPresenter> presentersToRemove = new ArrayList<MoviePanelBasicPresenter>();
                    Iterator<MoviePanelBasicPresenter> presenters = this.moviePresenters.iterator();
                    while (presenters.hasNext()) {
                        MoviePanelBasicPresenter presenter = presenters.next();
                        if (presenter.getView().isSelected()) {
                            presentersToRemove.add(presenter);
                        }
                    }
                    for (MoviePanelBasicPresenter presenter : presentersToRemove) {
                        this.removeMovie(presenter);
                    }
                    
                } else
                    if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_REMOVE_ALL)) {
                        logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_REMOVE_ALL + " -> " + e + " Source: " + e.getSource());
                        
                        this.view.removeAllMovies();
                        this.moviePresenters.clear();
                        
                    } else
                        if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_GO)) {
                            logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_GO + " -> " + e + " Source: " + e.getSource());
                            this.startRandomMovie();
                            
                        } else
                            if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP)) {
                                logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP + " -> " + e + " Source: " + e.getSource());
                                this.setAlwaysOnTop(false);
                                
                            } else
                                if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_ENABLE_ALWAYS_ON_TOP)) {
                                    logger.warn("Got Action Event: " + GuiConstants.LABEL_BTN_ENABLE_ALWAYS_ON_TOP + " -> " + e + " Source: " + e.getSource());
                                    this.setAlwaysOnTop(true);
                                    
                                } else
                                    if (e.getActionCommand().equals(GuiConstants.LABEL_BTN_ADD_TITLE)
                                    // also catch enter in textField :
                                            || (e.getSource() instanceof JTextField && ((JTextField) e.getSource()).getName().equals(
                                                    GuiConstants.LABEL_BTN_ADD_TITLE))) {
                                        
                                        logger.debug("Got Action Event: " + GuiConstants.LABEL_BTN_ADD_TITLE + " -> " + e + " Source: " + e.getSource());
                                        if (this.titleTextField != null) {
                                            
                                            this.addMovie(new MovieFile(UnknownTypes.getUnknownFile(), this.titleTextField.getText()));
                                            this.titleTextField.setText("");
                                        } else {
                                            logger.error("titleTextField has not been set, cannot get text from input field!");
                                        }
                                        
                                    } else {
                                        logger.warn("Got Unknown Event: " + e + " Source: " + e.getSource());
                                        
                                    }
        
    }
    
    private void setAlwaysOnTop(boolean isEnabled) {
        if (isEnabled) {
            this.parentFrame.setAlwaysOnTop(true);
            this.buttonPanel.btnAlwaysOnTop.setText(GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP);
        } else {
            this.parentFrame.setAlwaysOnTop(false);
            this.buttonPanel.btnAlwaysOnTop.setText(GuiConstants.LABEL_BTN_ENABLE_ALWAYS_ON_TOP);
        }
    }
    
    private void startRandomMovie() {
        if (this.moviePresenters.size() < 1) {
            return;
        }
        Random random = new Random();
        int movieIndex = random.nextInt(this.moviePresenters.size());
        Iterator<MoviePanelBasicPresenter> moviesIterator = this.moviePresenters.iterator();
        for (int i = 0; i < movieIndex; i++) {
            moviesIterator.next();
        }
        MoviePanelBasicPresenter moviePresenter = moviesIterator.next();
        logger.info("Randomly chose Movie: " + moviePresenter.getFilmName() + " (" + moviePresenter.getFile() + ")");
        if (Dialogs.showStartFilmDialog(moviePresenter.getFilmName(), moviePresenter.getFile().getName(), moviePresenter.getIconImage(), this.parentFrame)) {
            String executableName = moviePresenter.getExecutableName();
            this.parentFrame.setAlwaysOnTop(false);
            
            if (executableName != null && !executableName.equals("")) {
                String filmPath = moviePresenter.getFile().getPath();
                String command = executableName + " \"" + filmPath + "\"";
                try {
                    // Process child =
                    Runtime.getRuntime().exec(command);
                } catch (IOException e1) {
                    // FIXME: i18n !
                    Dialogs.showWarning("Kann Datei: " + filmPath + " nicht starten.\nGrund:\n" + e1.getMessage(), this.parentFrame);
                }
            }
        } else {
            logger.debug("Doch nicht ...");
        }
    }
    
    public void addTitleField(JTextField titleTextField) {
        this.titleTextField = titleTextField;
    }
    
}
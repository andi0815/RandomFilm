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
import java.awt.dnd.DragSource;
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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import amo.randomFilm.RandomFilm;
import amo.randomFilm.configuration.Configuration;
import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.MovieDataProvider;
import amo.randomFilm.datasource.exception.MovieDataProviderException;
import amo.randomFilm.datasource.tmdb.TmdbFacade;
import amo.randomFilm.gui.util.Dialogs;
import amo.randomFilm.gui.util.FileListHandler;
import amo.randomFilm.model.FilenameFilter;
import amo.randomFilm.model.MovieFile;

public class DropPanel extends JPanel implements DropTargetListener, DragSourceListener, DragGestureListener,
        ActionListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(DropPanel.class);
    
    // FIXME: in Config-File auslagern ...
    private static final String PATH_TO_BROWSER = "\"" + Configuration.getInstance().getProperty("browser.path") + "\"";
    
    private static final String FILMSTARTS_QUERY_URL = "http://www.filmstarts.de/suche/?q=";
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    private int width = 100;
    private int height = 100;
    
    private final int itemHeight = 110;
    
    private static final Font dropperFont = new Font("SansSerif", Font.BOLD, 20);
    
    private FileListHandler listHandler = new FileListHandler();
    
    DropTarget dropTarget = new DropTarget(this, this);
    DragSource dragSource = DragSource.getDefaultDragSource();
    
    private final String thumbsDb = "Thumbs.db";
    
    JFrame parent = null;
    
    // FIXME: add dynamically ...
    MovieDataProvider movieDataProvider = TmdbFacade.getInstance();
    
    public DropPanel(JFrame parent) {
        super();
        this.setName("DropPanel");
        this.parent = parent;
    }
    
    public void init(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.revalidate();
        this.setBackground(Color.white);
        this.width = width;
        this.height = height;
        
        this.setLayout(null);
        
        // dragSource.createDefaultDragGestureRecognizer( this,
        // DnDConstants.ACTION_COPY_OR_MOVE, this);
        // setDropTarget( dropTarget );
        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // int width = getPreferredSize().width;
        // int height = getPreferredSize().height;
        
        if (this.listHandler.isEmpty()) {
            // kreuze
            g.setColor(Color.lightGray);
            g.drawLine(0, 0, this.width, this.height);
            g.drawLine(this.width, 0, 0, this.height);
            
            // text
            int halfWidth = (int) (this.width / 2.0);
            int halfheight = (int) (this.height / 2.0);
            g.setColor(Color.BLACK);
            g.setFont(dropperFont);
            g.drawString("Bewirf mich mit Dateien!", halfWidth - 120, halfheight + 5);
            
            // rahmen
            g.drawRect(0, 0, this.width - 1, this.height - 1);
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
     * Behandle einen Drop-Event.
     */
    @Override
    public void drop(DropTargetDropEvent dropTargetDropEvent) {
        List fileList = new ArrayList();
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            Transferable tr = dropTargetDropEvent.getTransferable();
            if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                fileList = (java.util.List) tr.getTransferData(DataFlavor.javaFileListFlavor);
                
                // fileList = prepareListOfFiles(fileList);
                //
                // if (fileList.size() > 0) {
                this.addItems(fileList);
                if (RandomFilm.DEBUG)
                    System.out.println("DROPPER: finished adding items!");
                // } else {
                // if ( RandomFilm.DEBUG )
                // System.out.println("DROPPER: No fitting items found ...");
                // }
                //
                dropTargetDropEvent.getDropTargetContext().dropComplete(true);
                
            } else {
                System.err.println("Rejected");
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
        if (RandomFilm.DEBUG)
            this.listHandler.debugOut();
        this.resizePanel();
        this.repaint();
    }
    
    @Override
    public void dragOver(DragSourceDragEvent arg0) {
        
    }
    
    protected void resizePanel() {
        this.setPreferredSize(new Dimension(this.getWidth(), (this.getComponentCount() * (this.itemHeight)) + 2));
        this.revalidate();
        
    }
    
    void addItems(List<File> fileList) {
        List<MovieFile> movieFiles = FilenameFilter.getMovieNames(fileList);
        for (MovieFile movieFile : movieFiles) {
            try {
                List<? extends Movie> moviesFound = this.movieDataProvider.searchMovie(movieFile.getTitle());
                MoviePanel item = new MoviePanel(movieFile.getFile(), moviesFound, this.getWidth(), this.itemHeight,
                        this);
                item.setBounds(2, (this.getComponentCount() * this.itemHeight) + 2, this.getWidth() - 4,
                        this.itemHeight);
                
                if (this.listHandler.insertItem(item))
                    this.add(item);
                
            } catch (MovieDataProviderException e) {
                logger.warn("Could not find Movie with title: " + movieFile.getTitle(), e);
            }
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
        if (e.getActionCommand().equals("ask filmstarts")) {
            
            MoviePanel item = (MoviePanel) ((JButton) e.getSource()).getParent();
            String filmName = item.getFilmName();
            filmName = filmName.trim();
            filmName = filmName.replace('.', ' ');
            filmName = filmName.replace('_', ' ');
            
            String filmstartsUrlStr = null;
            try {
                filmstartsUrlStr = URLEncoder.encode(filmName, "UTF-8");
            } catch (UnsupportedEncodingException e1) {
                Dialogs.showWarning("Could not URL-encode film: " + filmName + "\n Cause: \n" + e1.getCause(), this);
            }
            String filmstartsQueryUrl = FILMSTARTS_QUERY_URL + filmstartsUrlStr;
            
            try {
                Runtime.getRuntime().exec(PATH_TO_BROWSER + " " + filmstartsQueryUrl);
            } catch (IOException e1) {
                Dialogs.showWarning("Could send URL '" + filmstartsQueryUrl + "' to " + PATH_TO_BROWSER
                        + ".\n Cause: \n" + e1.getCause(), this);
            }
            
        } else if (e.getActionCommand().equals("löschen")) {
            // System.out
            // .println(((JButton) e.getSource()).getParent().getClass());
            MoviePanel item = (MoviePanel) ((JButton) e.getSource()).getParent();
            
            this.remove(item);
            this.listHandler.remove(item);
            this.resetComponentBounds();
            this.resizePanel();
            this.repaint();
            
        } else if (e.getActionCommand().equals("Alles markieren")) {
            Iterator iter = this.listHandler.getList().iterator();
            while (iter.hasNext()) {
                ((MoviePanel) iter.next()).setSelected(true);
            }
            this.repaint();
            
        } else if (e.getActionCommand().equals("Nichts markieren")) {
            Iterator iter = this.listHandler.getList().iterator();
            while (iter.hasNext()) {
                ((MoviePanel) iter.next()).setSelected(false);
            }
            this.repaint();
            
        } else if (e.getActionCommand().equals("Markiertes löschen")) {
            this.removeAll();
            
            MoviePanel item;
            FileListHandler fileListHandler = new FileListHandler();
            Iterator iter = this.listHandler.getList().iterator();
            
            while (iter.hasNext()) {
                item = (MoviePanel) iter.next();
                if (!item.isSelected()) {
                    if (fileListHandler.insertItem(item))
                        this.add(item);
                }
            }
            
            this.listHandler = fileListHandler;
            this.resetComponentBounds();
            this.resizePanel();
            this.repaint();
            
        } else if (e.getActionCommand().equals("Liste löschen")) {
            this.removeAll();
            this.listHandler.clearList();
            this.setPreferredSize(new Dimension(this.width, this.height));
            this.resizePanel();
            this.repaint();
            
        } else if (e.getActionCommand().equals("Los geht's !")) {
            ArrayList listOfItems = this.listHandler.getList();
            if (listOfItems.size() > 0) {
                Random random = new Random(new Date().getTime());
                
                MoviePanel listItem = (MoviePanel) listOfItems.get(random.nextInt(listOfItems.size()));
                
                String filmName = listItem.getFilmName();
                String filmPath = listItem.getFile().getPath();
                Image filmIcon = listItem.getIconImage();
                
                // Dialogs.showYesNoDialog(
                // "FILM: " + listItem.getFile().getPath(), this);
                // }
                if (Dialogs.showStartFilmDialog(filmName, filmPath, filmIcon, this)) {
                    System.out.println("START");
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
                    System.out.println("Doch nicht ...");
                }
            }
        } else if (e.getActionCommand().equals(ButtonPanelDropper.DISABLE_ALWAYS_ON_TOP)) {
            this.parent.setAlwaysOnTop(false);
            ButtonPanelDropper.setAlwaysOnTopEnabled(false);
            
        } else if (e.getActionCommand().equals(ButtonPanelDropper.ENABLE_ALWAYS_ON_TOP)) {
            this.parent.setAlwaysOnTop(true);
            ButtonPanelDropper.setAlwaysOnTopEnabled(true);
            
        }
        
    }
    
    public void resetComponentBounds() {
        int nextY = 2;
        JComponent component;
        Rectangle rect;
        
        Iterator iter = this.listHandler.getList().iterator();
        
        while (iter.hasNext()) {
            component = (JComponent) iter.next();
            rect = component.getBounds();
            if (rect.getY() != nextY) {
                component.setBounds(2, nextY, this.getWidth() - 4, this.itemHeight);
            }
            nextY += this.itemHeight;
        }
        
    }
    
}
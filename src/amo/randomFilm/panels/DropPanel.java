package amo.randomFilm.panels;

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

import amo.randomFilm.RandomFilm;
import amo.randomFilm.configuration.Configuration;
import amo.randomFilm.util.Dialogs;
import amo.randomFilm.util.FileListHandler;

public class DropPanel extends JPanel implements DropTargetListener,
        DragSourceListener, DragGestureListener, ActionListener {
    
    // FIXME: in Config-File auslagern ...
    private static final String PATH_TO_BROWSER      = "\"" + Configuration.getInstance().getProperty("browser.path") + "\"";
    
    private static final String FILMSTARTS_QUERY_URL = "http://www.filmstarts.de/suche/?q=";
    
    /**
     * default Serial version id
     */
    private static final long   serialVersionUID     = 1L;
    
    private int                 width                = 100;
    private int                 height               = 100;
    
    private final int           itemHeight           = 50;
    
    private static final Font   dropperFont          = new Font("SansSerif", Font.BOLD, 20);
    
    private FileListHandler     listHandler          = new FileListHandler();
    
    DropTarget                  dropTarget           = new DropTarget(this, this);
    DragSource                  dragSource           = DragSource.getDefaultDragSource();
    
    private final String        thumbsDb             = "Thumbs.db";
    
    JFrame                      parent               = null;
    
    // private MainFrame mainFrame;
    //
    // public DropPanel( MainFrame mf ){
    // this.mainFrame = mf;
    // }
    
    public DropPanel(JFrame parent) {
        super();
        
        this.parent = parent;
    }
    
    public void init(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        revalidate();
        setBackground(Color.white);
        this.width = width;
        this.height = height;
        
        setLayout(null);
        
        // dragSource.createDefaultDragGestureRecognizer( this,
        // DnDConstants.ACTION_COPY_OR_MOVE, this);
        // setDropTarget( dropTarget );
        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // int width = getPreferredSize().width;
        // int height = getPreferredSize().height;
        
        if (listHandler.isEmpty()) {
            // kreuze
            g.setColor(Color.lightGray);
            g.drawLine(0, 0, width, height);
            g.drawLine(width, 0, 0, height);
            
            // text
            int halfWidth = (int) (width / 2.0);
            int halfheight = (int) (height / 2.0);
            g.setColor(Color.BLACK);
            g.setFont(dropperFont);
            g.drawString("Bewirf mich mit Dateien!", halfWidth - 120,
                    halfheight + 5);
            
            // rahmen
            g.drawRect(0, 0, width - 1, height - 1);
        }
    }
    
    /**
     * Teste, ob Dateien gedroppt werden dürfen (nur Dateien dürfen!)
     */
    public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
        Transferable tr = dropTargetDragEvent.getTransferable();
        if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) dropTargetDragEvent
                .acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
        else
            dropTargetDragEvent.rejectDrag();
        
    }
    
    /**
     * unbenutzt
     */
    public void dragExit(DropTargetEvent arg0) {
    }
    
    /**
     * unbenutzt
     */
    public void dragOver(DropTargetDragEvent arg0) {
    }
    
    /**
     * Behandle einen Drop-Event.
     */
    public void drop(DropTargetDropEvent dropTargetDropEvent) {
        List fileList = new ArrayList();
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        try {
            Transferable tr = dropTargetDropEvent.getTransferable();
            if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dropTargetDropEvent
                        .acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                fileList = (java.util.List) tr
                        .getTransferData(DataFlavor.javaFileListFlavor);
                
                // fileList = prepareListOfFiles(fileList);
                //				
                // if (fileList.size() > 0) {
                addItems(fileList);
                if (RandomFilm.DEBUG) System.out.println("DROPPER: finished adding items!");
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
        
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        listHandler.sort();
        if (RandomFilm.DEBUG) listHandler.debugOut();
        resizePanel();
        repaint();
    }
    
    // private List prepareListOfFiles( List<File> fileList){
    //		
    // for ( File file : fileList ) {
    // if (file.isDirectory()) {
    //				
    // } else { // is File:
    // String extension = getExtension(file);
    // System.out.println("FILE: '"+file+"'extension: "+ extension);
    // // String fileName = file.getName();
    // }
    //			
    // }
    //		
    // return fileList;
    // }
    
    private String getExtension(File file) {
        String fileName = file.getName();
        int indexOfExtension = fileName.lastIndexOf('.');
        String result;
        if (indexOfExtension > 0) {
            result = fileName.substring(indexOfExtension + 1).toLowerCase();
        } else {
            result = "";
        }
        
        return result;
        
    }
    
    private String getFilmName(File file) {
        String fileName = file.getName();
        int indexOfExtension = fileName.lastIndexOf('.');
        String result = fileName;
        if (indexOfExtension > 0) {
            result = fileName.substring(0, indexOfExtension);
        }
        return result;
        
    }
    
    public void dragOver(DragSourceDragEvent arg0) {
        
    }
    
    protected void resizePanel() {
        setPreferredSize(new Dimension(getWidth(),
                (getComponentCount() * (itemHeight)) + 2));
        revalidate();
        
    }
    
    private void addItems(List fileList) {
        Iterator iterator = fileList.iterator();
        // System.out.println("addItems: " + fileList);
        
        while (iterator.hasNext()) {
            File file = (File) iterator.next();
            
            if (file.isDirectory()) {
                addDirectory(file);
                
            } else {
                String ext = getExtension(file);
                ext = ext.toLowerCase();
                String filmName = getFilmName(file);
                
                // System.out.println(file.getName() + "  has ext: " + ext);
                if (ext.equals("avi") || ext.equals("mpg") || ext.equals("mpeg") || ext.equals("mov")
                        || ext.equals("flv") || ext.equals("divx") || ext.equals("ifo") || ext.equals("vob")
                        || ext.equals("xvid") || ext.equals("wmv")) {
                    
                    ListItem item = new ListItem(file, filmName, ext, getWidth(), itemHeight, this);
                    item.setBounds(2, (getComponentCount() * itemHeight) + 2, getWidth() - 4, itemHeight);
                    
                    if (listHandler.insertItem(item)) add(item);
                    
                    // if (RandomFilm.DEBUG || false)
                    // System.out.println(file.getAbsolutePath() + " "
                    // + getComponentCount());
                }
            }
        }
    }
    
    private void addDirectory(File file) {
        
        // System.out.println("addDirectory: " + file);
        
        File dvdVideoTsIfo = getDvdIndex(file);
        
        if (dvdVideoTsIfo != null) {
            // System.out.println("DVD found: " +
            // dvdVideoTsIfo.getAbsolutePath());
            
            String folderPath = dvdVideoTsIfo.getAbsolutePath();
            
            // System.out.println("folderPath: " + folderPath);
            
            folderPath = folderPath.replace('\\', '/');
            // System.out.println("folderPath: " + folderPath);
            String[] folderNames = folderPath.split("/");
            
            int size = folderNames.length;
            // System.out
            // .println("folderNames: " + folderNames + " size: " + size);
            
            String parentPath = folderNames[size - 1];
            
            if (size >= 2) {
                parentPath = folderNames[size - 2];
                
                // int indexOfParentPath =
                // folderPath.lastIndexOf(File.separator);
                // String parentPath =
                // folderPath.substring(0,indexOfParentPath);
                
                // System.out.println("parentPath: " + parentPath);
                
                if (parentPath.equals("VIDEO_TS") && size >= 3) { // get parent
                    // Folder
                    // Name
                    // int indexOfParentPath2 =
                    // folderPath.lastIndexOf(File.separator);
                    // parentPath = parentPath.substring(indexOfParentPath);
                    parentPath = folderNames[size - 3];
                    // System.out.println("parentPath: " + parentPath);
                }
                
                ListItem item = new ListItem(dvdVideoTsIfo, parentPath, "dvd",
                        getWidth(), itemHeight, this);
                item.setBounds(2, (getComponentCount() * itemHeight) + 2,
                        getWidth() - 4, itemHeight);
                if (listHandler.insertItem(item)) add(item);
                
                if (RandomFilm.DEBUG) System.out.println(file.getAbsolutePath() + " "
                            + getComponentCount());
                
            }
            // String parentPath = folderNames[size - 2];
            
            // System.out.println(folderPath + " is DVD Folder with Name: "
            // + parentPath);
            
        } else { // no DVD Folder
            // System.out.println("NO DVD found adding items ...");
            
            File[] filesInFolder = file.listFiles();
            List filesList = new ArrayList();
            for (int i = 0; i < filesInFolder.length; i++) {
                filesList.add(filesInFolder[i]);
            }
            addItems(filesList);
        }
        
        // for (int i=0; i<fileList.length ;i++) {
        //
        // if ( fileList[i].isDirectory() ){
        // addDirectory(fileList[i].listFiles());
        // } else if ( fileList[i].getName().equals(thumbsDb) ) {
        // continue;
        //
        // } else {
        //
        // ListItem item = new ListItem(fileList[i], getWidth(), itemHeight,
        // this);
        // item.setBounds(2,(getComponentCount()*itemHeight)+2, getWidth()-4,
        // itemHeight);
        //
        // if ( listHandler.insertItem( item ) ) add( item );
        //
        // if ( RandomFilm.DEBUG || false ) System.out.println(
        // fileList[i].getAbsolutePath() + " "+ getComponentCount() );
        // }
        //
        // }
    }
    
    private File getDvdIndex(File fileFolder) {
        File[] filesInFolder = fileFolder.listFiles();
        File videoTitleSetManagerInformation = null;
        for (int i = 0; i < filesInFolder.length; i++) {
            // System.out.println("getDvdIndex: " + filesInFolder[i].getName());
            if (filesInFolder[i].getName().equals("VIDEO_TS.IFO")) {
                videoTitleSetManagerInformation = filesInFolder[i];
            }
        }
        return videoTitleSetManagerInformation;
    }
    
    public FileListHandler getFileListHandler() {
        return listHandler;
    }
    
    public void dropActionChanged(DropTargetDragEvent arg0) {
    }
    
    public void dragDropEnd(DragSourceDropEvent arg0) {
    }
    
    public void dragEnter(DragSourceDragEvent arg0) {
    }
    
    public void dragExit(DragSourceEvent arg0) {
    }
    
    public void dropActionChanged(DragSourceDragEvent arg0) {
    }
    
    public void dragGestureRecognized(DragGestureEvent arg0) {
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ask filmstarts")) {
            
            ListItem item = (ListItem) ((JButton) e.getSource()).getParent();
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
                Dialogs.showWarning("Could send URL '" + filmstartsQueryUrl + "' to " + PATH_TO_BROWSER + ".\n Cause: \n" + e1.getCause(), this);
            }
            
        } else if (e.getActionCommand().equals("löschen")) {
            // System.out
            // .println(((JButton) e.getSource()).getParent().getClass());
            ListItem item = (ListItem) ((JButton) e.getSource()).getParent();
            
            remove(item);
            listHandler.remove(item);
            resetComponentBounds();
            resizePanel();
            repaint();
            
        } else if (e.getActionCommand().equals("Alles markieren")) {
            Iterator iter = listHandler.getList().iterator();
            while (iter.hasNext()) {
                ((ListItem) iter.next()).setSelected(true);
            }
            repaint();
            
        } else if (e.getActionCommand().equals("Nichts markieren")) {
            Iterator iter = listHandler.getList().iterator();
            while (iter.hasNext()) {
                ((ListItem) iter.next()).setSelected(false);
            }
            repaint();
            
        } else if (e.getActionCommand().equals("Markiertes löschen")) {
            removeAll();
            
            ListItem item;
            FileListHandler fileListHandler = new FileListHandler();
            Iterator iter = listHandler.getList().iterator();
            
            while (iter.hasNext()) {
                item = (ListItem) iter.next();
                if (!item.isSelected()) {
                    if (fileListHandler.insertItem(item)) add(item);
                }
            }
            
            listHandler = fileListHandler;
            resetComponentBounds();
            resizePanel();
            repaint();
            
        } else if (e.getActionCommand().equals("Liste löschen")) {
            removeAll();
            listHandler.clearList();
            setPreferredSize(new Dimension(width, height));
            resizePanel();
            repaint();
            
        } else if (e.getActionCommand().equals("Los geht's !")) {
            ArrayList listOfItems = listHandler.getList();
            if (listOfItems.size() > 0) {
                Random random = new Random(new Date().getTime());
                
                ListItem listItem = (ListItem) listOfItems.get(random
                        .nextInt(listOfItems.size()));
                
                String filmName = listItem.getFilmName();
                String filmPath = listItem.getFile().getPath();
                Image filmIcon = listItem.getIconImage();
                
                // Dialogs.showYesNoDialog(
                // "FILM: " + listItem.getFile().getPath(), this);
                // }
                if (Dialogs.showStartFilmDialog(filmName, filmPath, filmIcon, this)) {
                    System.out.println("START");
                    String executableName = listItem.getExecutableName();
                    
                    parent.setAlwaysOnTop(false);
                    
                    if (executableName != null && !executableName.equals("")) {
                        
                        String command = executableName + " \"" + filmPath + "\"";
                        try {
                            Process child = Runtime.getRuntime().exec(command);
                        } catch (IOException e1) {
                            Dialogs.showWarning("Kann Datei: " + filmPath + " nicht starten.\nGrund:\n"
                                    + e1.getMessage(), this);
                        }
                    }
                } else {
                    System.out.println("Doch nicht ...");
                }
            }
        } else if (e.getActionCommand().equals(ButtonPanelDropper.DISABLE_ALWAYS_ON_TOP)) {
            parent.setAlwaysOnTop(false);
            ButtonPanelDropper.setAlwaysOnTopEnabled(false);
            
        } else if (e.getActionCommand().equals(ButtonPanelDropper.ENABLE_ALWAYS_ON_TOP)) {
            parent.setAlwaysOnTop(true);
            ButtonPanelDropper.setAlwaysOnTopEnabled(true);
            
        }
        
    }
    
    public void resetComponentBounds() {
        int nextY = 2;
        JComponent component;
        Rectangle rect;
        
        Iterator iter = listHandler.getList().iterator();
        
        while (iter.hasNext()) {
            component = (JComponent) iter.next();
            rect = component.getBounds();
            if (rect.getY() != nextY) {
                component.setBounds(2, nextY, getWidth() - 4, itemHeight);
            }
            nextY += itemHeight;
        }
        
    }
    
}
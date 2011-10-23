package amo.randomFilm.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import amo.randomFilm.RandomFilm;
import amo.randomFilm.gui.panels.ButtonPanelDropper;
import amo.randomFilm.gui.panels.DropPanel;

public class MainFrame extends JFrame implements ComponentListener {
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    public static final String title = "Random Film";
    public static final String iconPath = "images/logo.jpg";
    
    protected DropPanel dropPane;
    private ButtonPanelDropper btnPanelDropper;
    private JScrollPane scrollPane;
    private int width, height;
    
    public MainFrame() {
        super();
    }
    
    /**
     * Initializes the Panels
     * 
     * @param width
     * @param heightDelta
     */
    public void init(int width, int heightDelta) {
        this.width = width;
        
        // zentriere Fenster
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.height = screenDimension.height - heightDelta;
        // center:
        // setLocation(d.width / 2 - width / 2, d.height / 2 - height / 2); //
        // Right:
        setLocation(screenDimension.width - width - 5, 5);
        
        // setze Fenstereigenschaften
        //
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(width, this.height));
        setLayout(null);
        setTitle(title);
        setAlwaysOnTop(true);
        setDefaultLookAndFeelDecorated(true);
        setIconImage((new ImageIcon(iconPath)).getImage());
        setResizable(false);
        
        // definiere höhen und breiten
        int startX = 5, widthX = width - 15;
        int dY = 10;
        int heightOfButtonPanels = 180;
        
        /**
         * INIT: Drop Panel
         */
        int startY = 5;
        int heightOfPanel = this.height - (heightOfButtonPanels + dY + dY);
        
        dropPane = new DropPanel(this);
        dropPane.init(widthX - 20, heightOfPanel - 5);
        dropPane.setVisible(true);
        
        scrollPane = new JScrollPane(dropPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(startX, startY, widthX, heightOfPanel);
        scrollPane.setVisible(true);
        scrollPane.setName("ScrollPaneForDropPanel");
        
        startY += heightOfPanel + dY;
        heightOfPanel = heightOfButtonPanels;
        
        /**
         * INIT: Buttons
         */
        btnPanelDropper = new ButtonPanelDropper();
        btnPanelDropper.setBounds(startX, startY, widthX, heightOfPanel);
        btnPanelDropper.init(this, dropPane, width, heightOfPanel);
        
        /**
         * START
         */
        getContentPane().add(scrollPane);
        getContentPane().add(btnPanelDropper);
        pack();
        repaint();
        
        addComponentListener(this);
        
    }
    
    /**
     * Never Used
     * 
     * @param size
     */
    private void layoutThis(Dimension size) {
        
        width = size.width;
        height = size.height;
        
        int startX = 5, widthX = width - 15;
        int dY = 10;
        int heightOfButtonPanels = 180;
        int heightOfProgressLabel = 20;
        
        /**
         * RESIZE: Drop Panels
         */
        int startY = 5;
        int heightOfPanel = height - (heightOfButtonPanels + dY + heightOfProgressLabel + dY);
        
        scrollPane.setBounds(startX, startY, widthX, heightOfPanel);
        dropPane.setPreferredSize(new Dimension(widthX - 20, heightOfPanel - 5));
        dropPane.resetComponentBounds();
        
        dropPane.revalidate();
        dropPane.repaint();
        scrollPane.repaint();
        
        startY += heightOfPanel + dY;
        heightOfPanel = heightOfButtonPanels;
        
        btnPanelDropper.setBounds(startX, startY, widthX, heightOfPanel);
        btnPanelDropper.resizeComponent(widthX - startX, heightOfPanel - startY);
        btnPanelDropper.repaint();
        
    }
    
    // public FileListHandler getFileListHandler() {
    // return dropPane.getFileListHandler();
    // }
    
    public void componentHidden(ComponentEvent arg0) {
    }
    
    public void componentMoved(ComponentEvent arg0) {
    }
    
    public void componentShown(ComponentEvent arg0) {
    }
    
    public void componentResized(ComponentEvent arg0) {
        if (RandomFilm.DEBUG) {
            Dimension size = getSize();
            System.out.println("MainFrame - RESIZE: " + size.width + " " + size.height);
        }
        // layoutThis(size);
        
        // scrollPane.resizeComponent( size.width, size.height );
        // btnPanelDropper.resizeComponent( size.width, size.height );;
        // copyPanel.resizeComponent( size.width, size.height );
        // btnPanelCopy.resizeComponent( size.width, size.height );
        
    }
    
}

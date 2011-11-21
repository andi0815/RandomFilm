package amo.randomFilm.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.panels.ButtonPanelDropper;
import amo.randomFilm.gui.panels.ButtonPanelPresenter;
import amo.randomFilm.gui.panels.SelectableMoviePanelPresenter;
import amo.randomFilm.gui.panels.SelectableMoviePanelView;

public class MainFrame extends JFrame implements ComponentListener {
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(MainFrame.class);
    
    public static final String title = "Random Film";
    
    public static final String iconPath = "images/logo.jpg";
    
    private ButtonPanelDropper btnPanelDropper;
    private JScrollPane scrollPane;
    
    private int width, height;
    
    private SelectableMoviePanelPresenter selectableMoviePanelPresenter;
    
    public MainFrame() {
        super();
    }
    
    /**
     * Initializes the Panels
     * 
     * @param width
     * @param heightDelta
     */
    public void init(int width) {
        this.width = width;
        
        // setze Fenster an den rechten rand
        Toolkit tk = Toolkit.getDefaultToolkit();
        Insets screenInsets = tk.getScreenInsets(this.getGraphicsConfiguration());
        Dimension screenDimension = tk.getScreenSize();
        logger.debug("Screen Insets: " + screenInsets);
        logger.debug("Screen Dimension: " + screenDimension);
        this.height = screenDimension.height - (screenInsets.bottom + screenInsets.top);
        this.setLocation(screenDimension.width - (width + screenInsets.right), 0);
        this.setPreferredSize(new Dimension(width, this.height));
        
        // setze Fenstereigenschaften
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
//        this.setLayout(null);
        this.setTitle(title);
        this.setAlwaysOnTop(true);
        setDefaultLookAndFeelDecorated(true);
        this.setIconImage((new ImageIcon(iconPath)).getImage());
        this.setResizable(false);
        
        /**
         * INIT: Drop Panel
         */
        SelectableMoviePanelView selectableMoviePanelView = new SelectableMoviePanelView();
        this.selectableMoviePanelPresenter = new SelectableMoviePanelPresenter(selectableMoviePanelView);
        this.scrollPane = new JScrollPane(selectableMoviePanelView.getComponent(),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setVisible(true);
        this.scrollPane.setName("ScrollPaneForDropPanel");
        
        /**
         * INIT: Buttons
         */
        int heightOfButtonPanels = 150;
        this.btnPanelDropper = new ButtonPanelDropper();
        this.btnPanelDropper.setPreferredSize(new Dimension(width, heightOfButtonPanels));
        this.btnPanelDropper.init(this, width, heightOfButtonPanels);
        ButtonPanelPresenter buttonPanelPresenter = new ButtonPanelPresenter();
        this.btnPanelDropper.addActionListener(buttonPanelPresenter);
        
        /**
         * START
         */
        this.getContentPane().add(this.scrollPane, BorderLayout.CENTER);
        this.getContentPane().add(this.btnPanelDropper, BorderLayout.PAGE_END);
        this.pack();
        this.repaint();
        
        this.addComponentListener(this);
        
    }
    
//    /**
//     * Never Used
//     * 
//     * @param size
//     */
//    private void layoutThis(Dimension size) {
//        
//        this.width = size.width;
//        this.height = size.height;
//        
//        int startX = 5, widthX = this.width - 15;
//        int dY = 10;
//        int heightOfButtonPanels = 180;
//        int heightOfProgressLabel = 20;
//        
//        /**
//         * RESIZE: Drop Panels
//         */
//        int startY = 5;
//        int heightOfPanel = this.height - (heightOfButtonPanels + dY + heightOfProgressLabel + dY);
//        
//        this.scrollPane.setBounds(startX, startY, widthX, heightOfPanel);
//        this.dropPane.setPreferredSize(new Dimension(widthX - 20, heightOfPanel - 5));
//        this.dropPane.resetComponentBounds();
//        
//        this.dropPane.revalidate();
//        this.dropPane.repaint();
//        this.scrollPane.repaint();
//        
//        startY += heightOfPanel + dY;
//        heightOfPanel = heightOfButtonPanels;
//        
//        this.btnPanelDropper.setBounds(startX, startY, widthX, heightOfPanel);
//        this.btnPanelDropper.resizeComponent(widthX - startX, heightOfPanel - startY);
//        this.btnPanelDropper.repaint();
//        
//    }
    
    @Override
    public void componentHidden(ComponentEvent arg0) {
    }
    
    @Override
    public void componentMoved(ComponentEvent arg0) {
    }
    
    @Override
    public void componentShown(ComponentEvent arg0) {
    }
    
    @Override
    public void componentResized(ComponentEvent arg0) {
        logger.debug("RESIZE: " + this.getSize().width + " " + this.getSize().height);
        // layoutThis(size);
        
        // scrollPane.resizeComponent( size.width, size.height );
        // btnPanelDropper.resizeComponent( size.width, size.height );;
        // copyPanel.resizeComponent( size.width, size.height );
        // btnPanelCopy.resizeComponent( size.width, size.height );
        
    }
    
}

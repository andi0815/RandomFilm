package amo.randomFilm.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.panels.ButtonPanelDropper;
import amo.randomFilm.gui.panels.ButtonPanelPresenter;
import amo.randomFilm.gui.panels.SelectableMoviePanelPresenter;
import amo.randomFilm.gui.panels.SelectableMoviePanelView;

public class MainFrame extends JFrame {
    
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
        
    }
    
}

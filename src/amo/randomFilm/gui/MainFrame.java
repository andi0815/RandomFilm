package amo.randomFilm.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import amo.randomFilm.gui.panels.ButtonPanelView;
import amo.randomFilm.gui.panels.ListOfMoviesPresenter;
import amo.randomFilm.gui.panels.ListOfMoviesView;

public class MainFrame extends JFrame {
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(MainFrame.class);
    
    public static final String title = "Random Film";
    
    public static final String iconPath = "images/logo.jpg";
    
    private ButtonPanelView btnPanel;
    private JScrollPane scrollPane;
    
    private int width, height;
    
    private ListOfMoviesPresenter listOfMoviesPresenter;
    
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
        this.setLayout(new BorderLayout(5, 5));
        this.setTitle(title);
        this.setAlwaysOnTop(true);
        setDefaultLookAndFeelDecorated(true);
        this.setIconImage((new ImageIcon(iconPath)).getImage());
        this.setResizable(false);
        this.getContentPane().setBackground(GuiConstants.BG_COLOR);
        
        /**
         * INIT: Input field
         */
        JTextField titleTextField = new JTextField();
        titleTextField.setName(GuiConstants.LABEL_BTN_ADD_TITLE);
        JButton btnAddMovieTitle = new JButton(GuiConstants.LABEL_BTN_ADD_TITLE);
        JPanel jPanel = new JPanel(new BorderLayout(5, 5));
        jPanel.add(titleTextField, BorderLayout.CENTER);
        jPanel.add(btnAddMovieTitle, BorderLayout.LINE_END);
        jPanel.setBackground(GuiConstants.BG_COLOR);
        
        /**
         * INIT: Buttons
         */
        int heightOfButtonPanels = 150;
        this.btnPanel = new ButtonPanelView();
        this.btnPanel.setPreferredSize(new Dimension(width, heightOfButtonPanels));
        this.btnPanel.init(this, width, heightOfButtonPanels);
        // this.btnPanel.addActionListener(this.listOfMoviesPresenter);
        
        /**
         * INIT: Drop Panel
         */
        ListOfMoviesView listOfMoviesView = new ListOfMoviesView();
        this.listOfMoviesPresenter = new ListOfMoviesPresenter(listOfMoviesView, this.btnPanel, this);
        this.scrollPane = new JScrollPane(listOfMoviesView.getComponent(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setVisible(true);
        this.scrollPane.setName("ScrollPaneForDropPanel");
        this.listOfMoviesPresenter.addTitleField(titleTextField);
        btnAddMovieTitle.addActionListener(this.listOfMoviesPresenter);
        titleTextField.addActionListener(this.listOfMoviesPresenter);
        
        /**
         * START
         */
        this.getContentPane().add(jPanel, BorderLayout.PAGE_START);
        this.getContentPane().add(this.scrollPane, BorderLayout.CENTER);
        this.getContentPane().add(this.btnPanel, BorderLayout.PAGE_END);
        this.pack();
        this.repaint();
        
    }
    
}

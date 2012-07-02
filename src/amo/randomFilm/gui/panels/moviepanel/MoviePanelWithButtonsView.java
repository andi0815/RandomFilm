package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.model.MovieFile;
import amo.randomFilm.model.SimpleMovie;
import amo.randomFilm.model.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 11.11.2011
 */
public class MoviePanelWithButtonsView extends MoviePanelBasicView {
    
    private JButton btnDelete;
    
    private JButton btnAlternatives;
    
    private JButton btnInfo;
    
    public MoviePanelWithButtonsView(File file) {
        super(file);
    }
    
    public MoviePanelWithButtonsView(MovieFile movieFile) {
        this(movieFile.getFile());
        this.titlePanel.update(new SimpleMovie(movieFile.getTitle(), true));
    }
    
    @Override
    protected void layoutPanel(File file) {
        this.moviePanel = new MPanel();
        final GroupLayout layout = new GroupLayout(this.moviePanel);
        this.moviePanel.setLayout(layout);
        
        // // We specify automatic gap insertion:
        // layout.setAutoCreateGaps(true);
        // layout.setAutoCreateContainerGaps(true);
        
        // initialize Components
        this.poster = new PosterPanel(null);
        this.titlePanel = new TitlePanel(UnknownTypes.STRING);
        this.starRater = new StarRater(10);
        this.starRater.setRating((float) UnknownTypes.DOUBLE);
        this.runtimePanel = new RuntimePanel(UnknownTypes.INT);
        this.yearPanel = new YearPanel(UnknownTypes.STRING);
        this.genrePanel = new GenrePanel(null);
        this.pathPanel = new PathPanel(file);
        this.btnDelete = new JButton(new ImageIcon(GuiConstants.IMAGEPATH_BTN_DELETE));
        this.btnDelete.setContentAreaFilled(false);
        this.btnDelete.setBorderPainted(false);
        this.btnDelete.setActionCommand(GuiConstants.LABEL_BTN_DELETE);
        this.btnDelete.setMinimumSize(new Dimension(32, 32));
        this.btnAlternatives = new JButton(new ImageIcon(GuiConstants.IMAGEPATH_BTN_ALTERNATIVES));
        this.btnAlternatives.setContentAreaFilled(false);
        this.btnAlternatives.setBorderPainted(false);
        this.btnAlternatives.setActionCommand(GuiConstants.LABEL_BTN_ALTERNATIVES);
        this.btnAlternatives.setMinimumSize(new Dimension(32, 32));
        this.btnAlternatives.setVisible(false);
        this.btnInfo = new JButton(new ImageIcon(GuiConstants.IMAGEPATH_BTN_INFO));
        this.btnInfo.setContentAreaFilled(false);
        this.btnInfo.setBorderPainted(false);
        this.btnInfo.setActionCommand(GuiConstants.LABEL_BTN_INFO);
        this.btnInfo.setMinimumSize(new Dimension(32, 32));
        this.btnInfo.setVisible(false);
        
        // Then, we define the groups and add the components. We establish a root group for each
        // dimension using the setHorizontalGroup and setVerticalGroup methods. Groups are created
        // via createSequentialGroup and createParallelGroup methods. Components are added to groups
        // by using the addComponent method.
        layout.setHorizontalGroup(layout
                .createSequentialGroup()
                .addComponent(this.poster)
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.titlePanel)
                                .addComponent(this.starRater).addComponent(this.runtimePanel)
                                .addComponent(this.yearPanel).addComponent(this.genrePanel)
                                .addComponent(this.pathPanel)) //
                .addComponent(this.btnInfo) //
                .addComponent(this.btnAlternatives) //
                .addComponent(this.btnDelete));
        layout.setVerticalGroup(layout.createParallelGroup() //
                .addComponent(this.poster, Alignment.CENTER) //
                .addGroup( //
                        layout.createSequentialGroup()//
                                .addComponent(this.titlePanel) //
                                .addComponent(this.starRater).addComponent(this.runtimePanel) //
                                .addComponent(this.yearPanel) //
                                .addComponent(this.genrePanel) //
                                .addComponent(this.pathPanel)) //
                .addComponent(this.btnInfo, Alignment.CENTER) //
                .addComponent(this.btnAlternatives, Alignment.CENTER) //
                .addComponent(this.btnDelete, Alignment.CENTER));
        
        this.moviePanel.doLayout();
        this.moviePanel.setBackground(GuiConstants.BG_COLOR);
        this.moviePanel.setVisible(true);
    }
    
    public void hasAlternatives(boolean hasAlternatives) {
        this.btnAlternatives.setVisible(hasAlternatives);
        this.moviePanel.doLayout();
        this.moviePanel.repaint();
    }
    
    public void hasDetails(boolean hasDetails) {
        this.btnInfo.setVisible(hasDetails);
        this.moviePanel.doLayout();
        this.moviePanel.repaint();
    }
    
    @Override
    public void setActionListener(ActionListener listener) {
        this.btnDelete.addActionListener(listener);
        this.btnAlternatives.addActionListener(listener);
        this.btnInfo.addActionListener(listener);
    }
    
    @Override
    public void removeActionListener(ActionListener listener) {
        this.btnDelete.removeActionListener(listener);
    }
    
}

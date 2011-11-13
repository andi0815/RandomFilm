package amo.randomFilm.gui.panels.moviepanel;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.apache.log4j.Logger;

import amo.randomFilm.datasource.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 11.11.2011
 */
public class MoviePanelView extends MoviePanelViewNoButtons {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(MoviePanelView.class);
    
    private JButton btnDelete;
    
    public MoviePanelView(File file) {
        super(file);
        
//        this.layoutPanel(file);
        
    }
    
    @Override
    protected void layoutPanel(File file) {
        this.moviePanel = new MPanel();
        final GroupLayout layout = new GroupLayout(this.moviePanel);
        this.moviePanel.setLayout(layout);
        
////       We specify automatic gap insertion:
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);
        
        // initialize Components
        this.poster = new PosterPanel(null);
        this.titlePanel = new TitlePanel(UnknownTypes.STRING);
        this.starRater = new StarRater(10);
        this.starRater.setRating((float) UnknownTypes.DOUBLE);
        this.runtimePanel = new RuntimePanel(UnknownTypes.INT);
        this.yearPanel = new YearPanel(UnknownTypes.STRING);
        this.genrePanel = new GenrePanel(null);
        this.pathPanel = new PathPanel(file);
        this.btnDelete = new JButton(new ImageIcon(IMAGE_DELETE));
        this.btnDelete.setActionCommand(LABEL_DELETE);
        
//      Then, we define the groups and add the components. We establish a root group for each dimension using the setHorizontalGroup and setVerticalGroup methods. Groups are created via createSequentialGroup and createParallelGroup methods. Components are added to groups by using the addComponent method.
        layout.setHorizontalGroup(layout
                .createSequentialGroup()
                .addComponent(this.poster)
                .addGroup(
                        layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.titlePanel)
                                .addComponent(this.starRater).addComponent(this.runtimePanel)
                                .addComponent(this.yearPanel).addComponent(this.genrePanel)
                                .addComponent(this.pathPanel)) //
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
                .addComponent(this.btnDelete, Alignment.CENTER));
        
        this.moviePanel.doLayout();
        this.moviePanel.setBackground(BG_COLOR);
        this.moviePanel.setVisible(true);
    }
    
    public void setActionListener(ActionListener listener) {
        this.btnDelete.addActionListener(listener);
    }
    
    public void removeActionListener(ActionListener listener) {
        this.btnDelete.removeActionListener(listener);
    }
    
}

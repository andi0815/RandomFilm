package amo.randomFilm.gui.panels.moviepanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.util.UpdatableView;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.UnknownTypes;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 11.11.2011
 */
public class MoviePanelBasicView implements UpdatableView {
    
    protected final class MPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            // if (isSelected) {
            // g.setColor(new Color(200, 200, 255));
            // g.fillRect(1, 1, getWidth() - 2, getHeight() - 3);
            //
            // g.setColor(Color.BLACK);
            // }
            
            // draw bottom border line
            g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1);
        }
        
        @Override
        public Dimension getMaximumSize() {
            return new Dimension(1280, 110);
        }
        
    }
    
    protected JPanel moviePanel;
    
    protected boolean isSelected = false;
    
    protected PosterPanel poster;
    
    protected Movie movie;
    
    protected TitlePanel titlePanel;
    
    protected StarRater starRater;
    
    protected RuntimePanel runtimePanel;
    
    protected YearPanel yearPanel;
    
    protected GenrePanel genrePanel;
    
    protected PathPanel pathPanel;
    
    public MoviePanelBasicView(File file) {
        super();
        
        this.layoutPanel(file);
        
    }
    
    protected void layoutPanel(File file) {
        
        this.moviePanel = new MPanel();
        // GroupLayout layout = new GroupLayout(this.moviePanel);
        BorderLayout layout = new BorderLayout();
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
        
        // // Then, we define the groups and add the components. We establish a root group for each
        // // dimension using the setHorizontalGroup and setVerticalGroup methods. Groups are
        // created
        // // via createSequentialGroup and createParallelGroup methods. Components are added to
        // groups
        // // by using the addComponent method.
        // layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(this.poster).addGroup(
        // layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.titlePanel).addComponent(
        // this.starRater).addComponent(this.runtimePanel).addComponent(this.yearPanel).addComponent(
        // this.genrePanel).addComponent(this.pathPanel)));
        // layout.setVerticalGroup(layout.createParallelGroup() //
        // .addComponent(this.poster, Alignment.CENTER) //
        // .addGroup( //
        // layout.createSequentialGroup()//
        // .addComponent(this.titlePanel) //
        // .addComponent(this.starRater).addComponent(this.runtimePanel) //
        // .addComponent(this.yearPanel) //
        // .addComponent(this.genrePanel) //
        // .addComponent(this.pathPanel)));
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(titlePanel);
        textPanel.add(starRater);
        textPanel.add(runtimePanel);
        textPanel.add(yearPanel);
        textPanel.add(genrePanel);
        textPanel.add(pathPanel);
        
        this.moviePanel.add(poster, BorderLayout.LINE_START);
        this.moviePanel.add(textPanel, BorderLayout.CENTER);
        
        this.moviePanel.doLayout();
        this.moviePanel.setBackground(GuiConstants.BG_COLOR);
        this.moviePanel.setVisible(true);
    }
    
    /*
     * ======================== PUBLIC Interface: ========================
     */

    public void setData(Movie movie) {
        this.movie = movie;
        this.poster.update(movie);
        this.titlePanel.update(movie);
        this.starRater.update(movie);
        this.runtimePanel.update(movie);
        this.yearPanel.update(movie);
        this.genrePanel.update(movie);
        // this.pathPanel.update(movie);
    }
    
    public Movie getData() {
        return this.movie;
    }
    
    @Override
    public void updateData(Movie movie) {
        setData(movie);
    }
    
    public File getFile() {
        return this.pathPanel.getFile();
    }
    
    public JComponent getComponent() {
        return this.moviePanel;
    }
    
    /*
     * ======================== SELECTION HANDLING: ========================
     */

    void setMouseListener(MouseListener listener) {
        this.moviePanel.addMouseListener(listener);
    }
    
    void removeMouseListener(MouseListener listener) {
        this.moviePanel.removeMouseListener(listener);
    }
    
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if (isSelected) {
            this.moviePanel.setBackground(GuiConstants.BG_COLOR_SELECTED);
            this.poster.setBackground(GuiConstants.BG_COLOR_SELECTED);
        } else {
            this.moviePanel.setBackground(GuiConstants.BG_COLOR);
            this.poster.setBackground(GuiConstants.BG_COLOR);
        }
        this.getComponent().repaint();
    }
    
    public boolean isSelected() {
        return this.isSelected;
    }
    
    /*
     * ======================== ACTION HANDLING: ========================
     */

    void setActionListener(ActionListener listener) {
        // To be overloaded ...
    }
    
    void removeActionListener(ActionListener listener) {
        // To be overloaded ...
    }
}

package amo.randomFilm.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import sun.awt.shell.ShellFolder;
import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.MovieDataProvider;

/**
 * Class that contains iformation about a certain movie file. The information to display is provided
 * by a {@link MovieDataProvider} within a {@link Movie} object.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 23.10.2011
 */
public class MoviePanel extends JPanel implements MouseListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(MoviePanel.class);
    
    private static final String LABEL_DELETE = "löschen";
    
    private static final Color BG_COLOR = Color.WHITE;
    
    private static final String IMAGE_DELETE = "images\\Delete.png";
    // private static final String IMAGE_FILMSTARTS = "images\\Filmstarts.png";
    
    private final File file;
    
    private boolean isSelected = false;
    
    private final List<? extends Movie> movieAlternatives;
    private Movie selectedMovie = null;
    
    public MoviePanel(File f, List<? extends Movie> movieAlternatives, int width, int height,
            ActionListener myActionListener) {
        super();
        
        this.movieAlternatives = movieAlternatives;
        this.selectedMovie = movieAlternatives != null && movieAlternatives.size() > 0 ? movieAlternatives.get(0)
                : null;
        this.file = f;
        
        this.addMouseListener(this);
        this.setLayout(new BorderLayout());
        
        // // add Filmstarts Button
        // JButton btnFilmstarts = new JButton(new ImageIcon(IMAGE_FILMSTARTS));
        // btnFilmstarts.setActionCommand("ask filmstarts");
        // btnFilmstarts.addActionListener(parent);
        // btnFilmstarts.setBounds(componentWidth - 60, ((imageHeight - 26) / 2), 26, 26);
        // add(btnFilmstarts);
        
        // film image
        PosterPanel poster = new PosterPanel(this.selectedMovie.getMovieImage());
        this.add(poster, BorderLayout.LINE_START);
        
        // film info
        MovieInfoPanel movieInfoPanel = new MovieInfoPanel(this.selectedMovie, this.file.getAbsolutePath());
        this.add(movieInfoPanel, BorderLayout.CENTER);
        
        // delete button
        JButton btnDelete = new JButton(new ImageIcon(IMAGE_DELETE));
        btnDelete.setActionCommand(LABEL_DELETE);
        btnDelete.addActionListener(myActionListener);
        // btnDelete.setBounds(this.getWidth() - 30, ((this.getHeight() - 26) / 2), 26, 26);
        this.add(btnDelete, BorderLayout.LINE_END);
        
        this.doLayout();
        
        this.setBackground(BG_COLOR);
        this.setVisible(true);
        
    }
    
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
    
    public File getFile() {
        return this.file;
    }
    
    public String getFilmName() {
        return this.selectedMovie.getMovieTitle();
    }
    
    public Image getIconImage() {
        return this.selectedMovie.getMovieImage();
    }
    
    /**
     * @return Path to application that is linked with this kind of file. Should be a Movie-Player.
     */
    public String getExecutableName() {
        String execType = null;
        try {
            ShellFolder shellFolder = ShellFolder.getShellFolder(this.file);
            execType = shellFolder.getExecutableType();
        } catch (FileNotFoundException e) {
            logger.error("Could not find file: " + this.file);
        }
        return execType;
    }
    
    public void setSelected(boolean bool) {
        this.isSelected = bool;
    }
    
    public boolean isSelected() {
        return this.isSelected;
    }
    
    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
    
    @Override
    public void mouseClicked(MouseEvent arg0) {
    }
    
    @Override
    public void mouseEntered(MouseEvent arg0) {
    }
    
    @Override
    public void mouseExited(MouseEvent arg0) {
    }
    
    @Override
    public void mousePressed(MouseEvent arg0) {
        this.isSelected = !this.isSelected;
        if (this.isSelected) {
            this.setBackground(new Color(200, 200, 255));
            this.invalidate();
        } else {
            this.setBackground(Color.WHITE);
            this.repaint();
            this.invalidate();
        }
        logger.debug(this.file.getName() + " has been Pressed on");
        this.repaint();
    }
    
}

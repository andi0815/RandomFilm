package amo.randomFilm.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import amo.randomFilm.RandomFilm;
import amo.randomFilm.datasource.Movie;

public class MoviePanel extends JPanel implements MouseListener {
    
    private static final String LABEL_DELETE = "löschen";
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    private static final Color BG_COLOR = Color.white;
    
    private static final String IMAGE_DELETE = "images\\Delete.png";
    // private static final String IMAGE_FILMSTARTS = "images\\Filmstarts.png";
    
    private final File file;
    private String extension = "";
    private String execType = "";
    
    private static int imageHeight;
    private static int imageWidth;
    private static int componentWidth;
    
    private boolean isSelected = false;
    
    private final List<? extends Movie> movieAlternatives;
    private Movie selectedMovie = null;
    private Image resizedImage = null;
    
    public MoviePanel(File f, List<? extends Movie> movieAlternatives, String extension, int width, int height,
            ActionListener myActionListener) {
        super();
        
        this.movieAlternatives = movieAlternatives;
        selectedMovie = movieAlternatives != null && movieAlternatives.size() > 0 ? movieAlternatives.get(0) : null;
        
        this.extension = extension;
        
        componentWidth = width - 2;
        imageHeight = height - 4;
        imageWidth = (int) (imageHeight * 1.5);
        
        file = f;
        
        addMouseListener(this);
        setLayout(new BorderLayout());
        
        // // add Filmstarts Button
        // JButton btnFilmstarts = new JButton(new ImageIcon(IMAGE_FILMSTARTS));
        // btnFilmstarts.setActionCommand("ask filmstarts");
        // btnFilmstarts.addActionListener(parent);
        // btnFilmstarts.setBounds(componentWidth - 60, ((imageHeight - 26) / 2), 26, 26);
        // add(btnFilmstarts);
        
        // film image
        PosterPanel poster = new PosterPanel(selectedMovie.getMovieImage());
        add(poster, BorderLayout.LINE_START);
        
        // film info
        MovieInfoPanel movieInfoPanel = new MovieInfoPanel(selectedMovie, file.getAbsolutePath());
        // TitlePanel title = new TitlePanel(selectedMovie.getMovieTitle());
        add(movieInfoPanel, BorderLayout.CENTER);
        
        // delete button
        JButton btnDelete = new JButton(new ImageIcon(IMAGE_DELETE));
        btnDelete.setActionCommand(LABEL_DELETE);
        btnDelete.addActionListener(myActionListener);
        // btnDelete.setBounds(componentWidth - 30, ((imageHeight - 26) / 2), 26, 26);
        add(btnDelete, BorderLayout.LINE_END);
        
        doLayout();
        
        setBackground(BG_COLOR);
        // setPreferredSize(new Dimension(width, height));
        setVisible(true);
        
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
        //
        // // small filepath
        // g.drawString(file.getAbsolutePath(), imageWidth + 24, imageHeight - 2);
        //
        // // Big Film Name
        // g.setFont(new Font("Sans-Serif", Font.BOLD, 18));
        // g.drawString(this.selectedMovie.getMovieTitle(), imageWidth + 4, 20);
        //
        // // draw icon
        // // if (hasIcon)
        // // g.drawImage(resizedImage, 10, 10, imageWidth, imageHeight, 0, 0, imageWidth,
        // imageHeight,
        // // null);
        // g.drawImage(resizedImage, 10, 10, null);
        // // g.drawImage( myIcon, 0, 1, imageWidth, imageHeight, 0, 1, imageWidth,
        // // imageHeight, myIcon.get);
        
        // draw bottom border line
        g.drawLine(0, getHeight() - 1, componentWidth - 1, getHeight() - 1);
        
        // paintComponents(g);
        
    }
    
    public File getFile() {
        return file;
    }
    
    public String getFilmName() {
        return selectedMovie.getMovieTitle();
    }
    
    public Image getIconImage() {
        return selectedMovie.getMovieImage();
    }
    
    public String getExecutableName() {
        return execType;
    }
    
    public void setSelected(boolean bool) {
        isSelected = bool;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void mouseReleased(MouseEvent arg0) {
    }
    
    public void mouseClicked(MouseEvent arg0) {
    }
    
    public void mouseEntered(MouseEvent arg0) {
    }
    
    public void mouseExited(MouseEvent arg0) {
    }
    
    public void mousePressed(MouseEvent arg0) {
        isSelected = !isSelected;
        if (isSelected) {
            setBackground(new Color(200, 200, 255));
            invalidate();
        } else {
            setBackground(Color.WHITE);
            repaint();
            invalidate();
        }
        if (RandomFilm.DEBUG)
            System.out.println(file.getName() + " has been Pressed on");
        repaint();
    }
    
}

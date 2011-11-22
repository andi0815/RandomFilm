package amo.randomFilm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.BasicConfigurator;
import org.junit.BeforeClass;

import amo.randomFilm.datasource.tmdb.DummyMovie;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.MovieFile;

/**
 * Base Class for Unit Tests
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 03.10.2011
 */
public abstract class AbstractTestBase {
    
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final File MOVIE_TEST_FOLDER = new File("test" + FILE_SEPARATOR + "src" + FILE_SEPARATOR
            + "testMovieFolder");
    public static final File MOVIE_TEST_FILE = new File(MOVIE_TEST_FOLDER + FILE_SEPARATOR + "Star.Wars.mpg");
    private final static DummyMovie movie1 = new DummyMovie();
    private final static DummyMovie movie2 = new DummyMovie();
    private final static DummyMovie movie3 = new DummyMovie();
    
    static {
        movie2.setGenres(Arrays.asList("Drama"));
        movie2.setImage(null);
        movie2.setRating(0.4);
        movie2.setRuntime(68);
        movie2.setShortDesc("short desc");
        movie2.setTitle("The second Dumy Movie");
        movie2.setYear("1977");
        
        movie3.setGenres(Arrays.asList("Drama"));
        movie3.setImage(null);
        movie3.setRating(0.4);
        movie3.setRuntime(68);
        movie3.setShortDesc("short desc");
        movie3.setTitle("The second Dumy Movie");
        movie3.setYear("1977");
        
    }
    
    @BeforeClass
    public static void setup() {
        // Set up a simple configuration that logs on the console.
        BasicConfigurator.configure();
    }
    
    public static void showImage(final Image image, long millies) throws InterruptedException {
        showImage(image, millies, image.getWidth(null), image.getHeight(null));
    }
    
    /**
     * Shows a given image for a given amount of milliseconds.
     * 
     * @param image
     *            image to show
     * @param millies
     *            milliseconds to show frame
     * @param width
     *            width of image to show
     * @param height
     *            height of image to show
     * 
     * @throws InterruptedException
     *             in case something went wrong
     */
    public static void showImage(final Image image, long millies, final int width, final int height)
            throws InterruptedException {
        JFrame frame = new JFrame();
        JPanel imagePanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.drawImage(image, //
                        10, 10, width, height, //
                        0, 0, width, height, null);
            }
            
        };
        imagePanel.setPreferredSize(new Dimension(width, height));
        frame.getContentPane().add(imagePanel);
        frame.pack();
        frame.setVisible(true);
        Thread.sleep(millies);
    }
    
    /**
     * Shows a given JComponent for a given period of time.
     * 
     * @param component
     *            the component to show
     * @param time
     *            the period of time in millies to show panel (calls Thread.sleep(&lt;time&gt;))
     */
    public static JFrame showComponent(JComponent component, long time) {
        
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(component, BorderLayout.CENTER);
        frame.setBackground(Color.green);
        frame.doLayout();
        frame.pack();
        frame.setVisible(true);
        
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Could not sleep. Cause: " + e);
        }
        return frame;
        
    }
    
    /**
     * @return a dummy Movie.
     */
    public static Movie getDummyMovie() {
        return movie1;
    }
    
    /**
     * @return a dummy MovieFile.
     */
    public static MovieFile getDummyMovieFile() {
        return new MovieFile(MOVIE_TEST_FILE, "Star Wars");
    }
    
    /**
     * @return a list of dummy Movies.
     */
    public static List<? extends Movie> getDummyMovieList() {
        List<DummyMovie> movies = new ArrayList<DummyMovie>();
        movies.add(movie1);
        movies.add(movie2);
        movies.add(movie3);
        return movies;
    }
    
    /**
     * Loads an image at given path
     * 
     * @param path
     *            (relative) path of image file.
     */
    public static Image loadImage(String pathToImage) {
        Image image = new ImageIcon(pathToImage, "").getImage();
        return image;
    }
    
}
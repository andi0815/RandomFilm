package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sun.awt.shell.ShellFolder;
import amo.randomFilm.datasource.Movie;
import amo.randomFilm.datasource.MovieDataProvider;
import amo.randomFilm.datasource.exception.MovieDataProviderException;
import amo.randomFilm.gui.panels.SimpleMovie;

/**
 * Class that contains information about a certain movie file. The information to display is
 * provided
 * by a {@link MovieDataProvider} within a {@link Movie} object.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 23.10.2011
 */
public class MoviePanelPresenter implements MouseListener, ActionListener {
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(MoviePanelPresenter.class);
    
    private final File file;
    
    private List<? extends Movie> movieAlternatives;
    
    private Movie selectedMovie = null;
    
    private MoviePanelViewBasic moviePanel = null;
    
    public MoviePanelPresenter(File f, String title, MoviePanelViewBasic moviePanel,
            MovieDataProvider movieDataProvider) {
        super();
        this.file = f;
        this.moviePanel = moviePanel;
        this.moviePanel.setData(new SimpleMovie(title));
        this.moviePanel.setMouseListener(this);
        this.requestMovieInfo(title, movieDataProvider);
        
    }
    
//    public MoviePanelPresenter(Movie movie) {
//        this(null, movie.getMovieTitle(), null);
//        this.selectedMovie = movie;
//    }
    
    /**
     * Starts a new Thread that requests movie information in the background.
     * 
     * @param title
     *            the title to look for
     * @param movieDataProvider
     *            the provider implementation to use
     */
    private void requestMovieInfo(final String title, final MovieDataProvider movieDataProvider) {
        Thread requestThread = new Thread() {
            @Override
            public void run() {
                try {
                    MoviePanelPresenter.this.movieAlternatives = movieDataProvider.searchMovie(title);
//                moviesFound = new ArrayList<Movie>();
//                moviesFound.add(new SimpleMovie("TEst"));
                    
                } catch (MovieDataProviderException e) {
                    logger.warn("Could not find Movie with title: " + title, e);
                    List<SimpleMovie> movieList = new ArrayList<SimpleMovie>();
                    movieList.add(new SimpleMovie(title));
                    MoviePanelPresenter.this.movieAlternatives = movieList;
                }
                
                MoviePanelPresenter.this.selectedMovie = MoviePanelPresenter.this.movieAlternatives != null
                        && MoviePanelPresenter.this.movieAlternatives.size() > 0 ? MoviePanelPresenter.this.movieAlternatives
                        .get(0) : null;
                MoviePanelPresenter.this.moviePanel.setData(MoviePanelPresenter.this.selectedMovie);
            }
        };
        requestThread.start();
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
    
    public List<? extends Movie> getMovieAlternatives() {
        return this.movieAlternatives;
    }
    
    public Movie getSelectedMovie() {
        return this.selectedMovie;
    }
    
    public void setSelectedMovie(Movie movie) {
        if (this.movieAlternatives.contains(movie)) {
            this.selectedMovie = movie;
        } else {
            throw new IllegalArgumentException("Movie " + movie + " does not exist as an alternative.");
        }
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
        this.moviePanel.setSelected(!this.moviePanel.isSelected());
    }
    
//    @Override
//    public boolean equals(Object other) {
//        if (other instanceof MoviePanelPresenter) {
//            File otherFile = ((MoviePanelPresenter) other).getFile();
//            return otherFile.equals(this.file);
//        } else {
//            return false;
//        }
//    }
//    
//    @Override
//    public int hashCode() {
//        return this.file.hashCode();
//    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        logger.warn("Caught unhandled Event: " + e);
        
    }
    
}

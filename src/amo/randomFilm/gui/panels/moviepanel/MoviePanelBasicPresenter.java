package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sun.awt.shell.ShellFolder;
import amo.randomFilm.datasource.exception.MovieDataProviderException;
import amo.randomFilm.gui.panels.SelectionHandler;
import amo.randomFilm.model.Movie;
import amo.randomFilm.model.MovieDataProvider;
import amo.randomFilm.model.SimpleMovie;

/**
 * Class that contains information about a certain movie file. The information to display is
 * provided by a {@link MovieDataProvider} within a {@link Movie} object.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 23.10.2011
 */
public class MoviePanelBasicPresenter {
    
    private final class RequestThread extends Thread {
        
        /** Logger Object for this Class */
        private final Logger logger = Logger.getLogger(MoviePanelBasicPresenter.RequestThread.class);
        private final MovieDataProvider movieDataProvider;
        private final String title;
        private MoviePanelBasicPresenter parent;
        
        private RequestThread(MovieDataProvider movieDataProvider, String title) {
            this.movieDataProvider = movieDataProvider;
            this.title = title;
        }
        
        public void setParent(MoviePanelBasicPresenter parent) {
            this.parent = parent;
        }
        
        @Override
        public void run() {
            try {
                List<? extends Movie> moviesFound = this.movieDataProvider.searchMovie(this.title);
                System.out.println("##################### FOUND: ");
                boolean initialTitleChanged = true; // add initial title to alternatives, if it
                                                    // cannot be found anymore
                for (Movie current : moviesFound) {
                    System.out.println("##################### " + current);
                    if (current.getMovieTitle().trim().toLowerCase().equals((this.title.toLowerCase()))) {
                        initialTitleChanged = false;
                        break; // stop searching
                    }
                }
                if (initialTitleChanged) {
                    // initial title differs from found ones, it must be added to alternatives
                    ArrayList<Movie> newList = new ArrayList<Movie>();
                    newList.addAll(moviesFound);
                    newList.add(new SimpleMovie(this.title, false));
                    moviesFound = newList;
                }
                this.parent.setMovieAlternatives(moviesFound);
                
            } catch (MovieDataProviderException e) {
                logger.warn("Could not find Movie with title: " + this.title, e);
                List<SimpleMovie> movieList = new ArrayList<SimpleMovie>();
                movieList.add(new SimpleMovie(this.title, false));
                this.parent.setMovieAlternatives(movieList);
            }
        }
    }
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(MoviePanelBasicPresenter.class);
    
    private File file = null;
    
    private List<? extends Movie> movieAlternatives;
    
    private Movie selectedMovie = null;
    
    protected MoviePanelBasicView moviePanel = null;
    
    private SelectionHandler selectionHandler;
    
    // /** Title of the Movie initially found */
    // private String initialTitle;
    
    public MoviePanelBasicPresenter(File f, String title, MoviePanelBasicView moviePanel,
            MovieDataProvider movieDataProvider) {
        this(new SimpleMovie(title, true), moviePanel);
        this.file = f;
        // this.initialTitle = title;
        this.requestMovieInfo(title, movieDataProvider);
        
    }
    
    public MoviePanelBasicPresenter(Movie movie, MoviePanelBasicView moviePanel) {
        super();
        this.moviePanel = moviePanel;
        this.moviePanel.setData(movie);
        this.selectionHandler = new SelectionHandler();
        this.selectionHandler.addMovie(moviePanel);
    }
    
    protected void setMovieAlternatives(List<? extends Movie> alternatives) {
        this.movieAlternatives = alternatives;
        // set first movie the selected one, if any available
        this.selectedMovie = MoviePanelBasicPresenter.this.movieAlternatives != null
                && MoviePanelBasicPresenter.this.movieAlternatives.size() > 0 ? MoviePanelBasicPresenter.this.movieAlternatives
                .get(0) : null;
        this.moviePanel.setData(MoviePanelBasicPresenter.this.selectedMovie);
    }
    
    /**
     * Starts a new Thread that requests movie information in the background.
     * 
     * @param title
     *            the title to look for
     * @param movieDataProvider
     *            the provider implementation to use
     */
    private void requestMovieInfo(final String title, final MovieDataProvider movieDataProvider) {
        RequestThread requestThread = new RequestThread(movieDataProvider, title);
        requestThread.setParent(this);
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
            this.moviePanel.setData(this.selectedMovie);
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
    
    public MoviePanelBasicView getView() {
        return this.moviePanel;
    }
    
}

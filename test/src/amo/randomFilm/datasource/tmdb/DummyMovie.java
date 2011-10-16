package amo.randomFilm.datasource.tmdb;

import java.awt.Image;
import java.util.Arrays;
import java.util.List;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.Movie;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 09.10.2011
 */
public class DummyMovie implements Movie {
    
    private String title = "DEFAULT TITLE";
    
    private double rating = 9.0;
    
    private int runtime = 123;
    
    private Image image = AbstractTestBase.loadImage("test/src/test-image-musketeers.jpg");
    
    private String shortDesc = "This is a dummy short description with some random test. Lorem ipsum lorem ipsum. Bla bla, blubber blab bla und so fort.";
    
    private String year = "1.1.1970";
    
    private List<String> genres = Arrays.asList("Action", "Sci-Fi", "Schnulze");
    
    @Override
    public String getMovieTitle() {
        return title;
    }
    
    @Override
    public double getMovieRating() {
        return rating;
    }
    
    @Override
    public int getMovieRuntime() {
        return runtime;
    }
    
    @Override
    public Image getMovieImage() {
        return image;
    }
    
    @Override
    public String getMovieShortDescription() {
        return shortDesc;
    }
    
    @Override
    public String getMovieYear() {
        return year;
    }
    
    @Override
    public List<String> getMovieGenres() {
        return genres;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setRating(double rating) {
        this.rating = rating;
    }
    
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
    
    public void setImage(Image image) {
        this.image = image;
    }
    
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }
    
    public void setYear(String year) {
        this.year = year;
    }
    
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
    
}

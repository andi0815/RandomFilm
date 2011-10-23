package amo.randomFilm.model;

import java.io.File;

import amo.randomFilm.datasource.Movie;

/**
 * Class that links a Movie File with {@link Movie} data.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 23.10.2011
 */
public class MovieFile {
    
    private final File file;
    
    private final String title;
    
    public MovieFile(File file, String title) {
        this.file = file;
        this.title = title;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MovieFile) {
            MovieFile other = (MovieFile) obj;
            if (other.getFile().equals(this.file) && other.getTitle().equals(this.title)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return (this.file.getAbsolutePath() + this.title).length();
    }
    
    @Override
    public String toString() {
        return "[MovieFile: " + this.title + "; " + (this.file != null ? this.file.getAbsolutePath() : "UNKNOWN PATH")
                + "]";
    }
    
}

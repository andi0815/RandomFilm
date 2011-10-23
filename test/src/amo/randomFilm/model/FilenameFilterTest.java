package amo.randomFilm.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 21.10.2011
 */
public class FilenameFilterTest extends AbstractTestBase {
    
    private List<String> movieFiles = new ArrayList<String>();
    
    private List<String> expectedResults = new ArrayList<String>();
    
    private static final String BASEDIR = "test" + FILE_SEPARATOR + "testmovies";
    
    @Test
    public void testFolders() throws IOException {
        
        // define directories and outcome
        this.addFile("True Grit.avi", "True Grit");
        this.addFile("this.is.it.mov", "this is it");
        this.addFile("somedir" + FILE_SEPARATOR + "another film.flv", "another film");
        this.addFile("a DVD film" + FILE_SEPARATOR + "video_ts.ifo", "a DVD film");
        this.addFile("another DVD film" + FILE_SEPARATOR + "video_ts" + FILE_SEPARATOR + "video_ts.ifo",
                "another DVD film");
        this.addFile("not a movie.test", null);
        this.addFile("dontknow.thismovie.mpg", "dontknow thismovie");
        
        // create Folders
        File[] filesMade = this.makeTestFiles(BASEDIR);
        System.out.println("FILES MADE: " + Arrays.toString(filesMade));
        
        // perform filter
        // List<String> movieNames = FilenameFilter.getMovieNames(filesMade);
        File[] files = { new File(BASEDIR) };
        List<MovieFile> movieNames = FilenameFilter.getMovieNames(files);
        System.out.println("FOUND MOVIES: " + movieNames.toString());
        
        // check result
        this.compareResults(movieNames);
        
        // remove files
        this.removeTestFiles(new File(BASEDIR));
    }
    
    /**
     * Remove all files in given dir and subfolders
     * 
     * @param directory
     *            directory to remove
     */
    private void removeTestFiles(File directory) {
        Assert.assertTrue(directory.isDirectory());
        File[] listFiles = directory.listFiles();
        for (File file : listFiles) {
            if (file.isDirectory()) {
                this.removeTestFiles(file);
            } else {
                file.delete();
            }
        }
        directory.delete();
    }
    
    /**
     * Checks whether resulting movie names contain all and only the expected results.
     * 
     * @param movieNames
     *            the movies returned by the filter
     * 
     */
    private void compareResults(List<MovieFile> moviesFound) {
        List<String> movieNames = new ArrayList<String>();
        for (MovieFile movie : moviesFound) {
            movieNames.add(movie.getTitle());
        }
        for (String result : this.expectedResults) {
            Assert.assertTrue("Movie: '" + result + "' not in List: " + movieNames.toString(),
                    movieNames.contains(result));
            movieNames.remove(result);
        }
        if (movieNames.size() > 0) {
            Assert.fail("Returned Movies contains unexpected results: " + movieNames.toString());
        }
        
    }
    
    /**
     * Set up all dirs defined.
     * 
     * @return the list of all files created
     * @throws IOException
     *             in case the files could not be set up correctly
     */
    private File[] makeTestFiles(String baseDir) throws IOException {
        for (String moviePath : this.movieFiles) {
            File file = new File(baseDir + FILE_SEPARATOR + moviePath);
            // create containing Dir
            File parentDir = file.getParentFile();
            if (!parentDir.mkdirs() && !parentDir.exists()) {
                Assert.fail("could not create Dirs for file: " + parentDir.getAbsolutePath());
            }
            // create file
            if (!file.createNewFile() && !file.exists()) {
                Assert.fail("could create file: " + file.getAbsolutePath());
            }
        }
        return new File(baseDir).listFiles();
    }
    
    /**
     * Adds a new set of file plus expected result.
     * 
     * @param testFile
     *            the file to create / test
     * @param expectedResult
     *            the expected result string or <code>null</code>, if none is expected.
     */
    private void addFile(String testFile, String expectedResult) {
        this.movieFiles.add(testFile);
        if (expectedResult != null)
            this.expectedResults.add(expectedResult);
    }
    
}

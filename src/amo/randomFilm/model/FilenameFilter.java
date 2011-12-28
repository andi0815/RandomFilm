package amo.randomFilm.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import amo.randomFilm.configuration.Configuration;

/**
 * This class provides functions to filter movie files and trying to guess the correctly formatted
 * movie title.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 21.10.2011
 */
public class FilenameFilter {
    
    private static final Pattern KNWON_EXTENSIONS = Pattern.compile(Configuration.getInstance().getProperty(
            "knownExtensions"));
    
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(FilenameFilter.class);
    
    /**
     * Hidden Constructor.
     */
    private FilenameFilter() {
        // never used!
    }
    
    /**
     * Returns a list of movie titles found in given file. Directories are also searched
     * recursively. DVD-Folders are recognized, too. This Method returns one entry per Videofile /
     * DVD-Folder found.
     * 
     * @param fileList
     *            the list of files to search.
     * @return the list of movie names found
     */
    public static List<MovieFile> getMovieNames(List<File> fileList) {
        File[] fileArray = new File[5];
        return getMovieNames(fileList.toArray(fileArray));
    }
    
    /**
     * Returns a list of movie titles found in given file. Directories are also searched
     * recursively. DVD-Folders are recognized, too. This Method returns one entry per Videofile /
     * DVD-Folder found.
     * 
     * @param fileList
     *            the list of files to search.
     * @return the list of movie names found
     */
    public static List<MovieFile> getMovieNames(File[] fileList) {
        List<MovieFile> movieTitleList = new ArrayList<MovieFile>();
        
        if (fileList == null) {
            logger.error("File List must not be null!");
            return null;
        }
        
        logger.debug("Searching Movies in Files: " + Arrays.toString(fileList));
        for (File file : fileList) {
            
            if (file == null) { // skip, if null
                continue;
                
            } else if (file.isDirectory()) { // recurse into directory
                logger.debug("Adding directory: " + file.getAbsolutePath());
                List<MovieFile> moviesInDir = addDirectory(file);
                if (moviesInDir != null) {
                    movieTitleList.addAll(moviesInDir);
                }
                
            } else { // add, if is movie
                String ext = getExtension(file);
                
                if (KNWON_EXTENSIONS.matcher(ext.toLowerCase()).matches()) {
                    logger.debug("Adding movie file: " + file);
                    movieTitleList.add(getFilmName(file));
                    
                } else { // extension not known ...
                    logger.info("File " + file.getAbsolutePath() + " is not a Movie.");
                }
                
            }
        }
        
        return movieTitleList;
    }
    
    /**
     * Recurses into a subsequent folder.
     * 
     * @param file
     *            the folder to search
     */
    protected static List<MovieFile> addDirectory(File file) {
        List<MovieFile> moviesFound = new ArrayList<MovieFile>();
        File dvdVideoTsIfo = getDvdIndex(file);
        
        if (dvdVideoTsIfo != null) { // is DVD folder
            logger.debug("Directory is DVD-Folder: " + file.getAbsolutePath());
            String folderPath = dvdVideoTsIfo.getAbsolutePath();
            
            // split into folders and file
            String file_sep = FILE_SEPARATOR;
            if (file_sep.equals("\\"))
                file_sep += "\\";
            String[] folderNames = folderPath.split(file_sep);
            String parentFolderName = folderNames[folderNames.length - 1]; // filename
            
            if (folderNames.length >= 2) { // real parent folder
                parentFolderName = folderNames[folderNames.length - 2];
                
                if (parentFolderName.toUpperCase().equals("VIDEO_TS") && folderNames.length >= 3) {
                    // use parent Folder name, if this one has VIDEO_TS as name
                    parentFolderName = folderNames[folderNames.length - 3];
                }
                
                //
                // FIXME: take one more parent.,. if folder is named Disc#, DVD#, Season?#,
                // Staffel?# etc.
                //
                
            }
            
            logger.info("Found Movie: " + parentFolderName);
            moviesFound.add(getFilmName(file, parentFolderName));
            
        } else { // no DVD Folder
            logger.debug("NO DVD found adding items ...");
            moviesFound.addAll(getMovieNames(file.listFiles()));
        }
        
        return moviesFound;
    }
    
    /**
     * Returns the Reference to the video titleset manager information file ("VIDEO_TS.IFO"), if
     * available.
     * 
     * @param fileFolder
     *            the folder to look for DVD-Files
     * @return the VIDEO_TS.IFO-file or <code>null</code>, if it is not present
     */
    private static File getDvdIndex(File fileFolder) {
        File[] filesInFolder = fileFolder.listFiles();
        File videoTitleSetManagerInformation = null;
        for (int i = 0; i < filesInFolder.length; i++) {
            // System.out.println("getDvdIndex: " + filesInFolder[i].getName());
            if (filesInFolder[i].getName().toUpperCase().equals("VIDEO_TS.IFO")) {
                videoTitleSetManagerInformation = filesInFolder[i];
            }
        }
        return videoTitleSetManagerInformation;
    }
    
    /**
     * Returns extension of given file, if there is any.
     * 
     * @param file
     *            the file to process
     * @return file extension, or <code>null</code>, if there is none
     */
    private static String getExtension(File file) {
        String fileName = file.getName();
        int indexOfExtension = fileName.lastIndexOf('.');
        String result;
        if (indexOfExtension > 0) {
            result = fileName.substring(indexOfExtension + 1).toLowerCase();
        } else {
            result = null;
        }
        return result;
        
    }
    
    /**
     * Returns {@link MovieFile} for given movie file
     * 
     * @param file
     *            file to use
     * @param inputName
     *            filename to use as search title
     * @return MovieFile with formatted movie title
     */
    protected static MovieFile getFilmName(File file, String inputName) {
        return new MovieFile(file, formatFileName(inputName));
        
    }
    
    /**
     * Returns {@link MovieFile} for given movie file
     * 
     * @param file
     *            file to use
     * @return MovieFile with formatted movie title
     */
    protected static MovieFile getFilmName(File file) {
        String fileName = file.getName();
        int indexOfExtension = fileName.lastIndexOf('.');
        String result = fileName;
        if (indexOfExtension > 0) {
            result = fileName.substring(0, indexOfExtension);
        }
        return new MovieFile(file, formatFileName(result));
        
    }
    
    // FIXME: implement intelligent filter
    private static String formatFileName(String inputName) {
        inputName = inputName.replace(".", " ");
        inputName = inputName.replace("xvid", "");
        return inputName;
    }
    
}

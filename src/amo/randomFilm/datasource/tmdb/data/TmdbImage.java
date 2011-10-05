package amo.randomFilm.datasource.tmdb.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import sun.net.www.protocol.http.HttpURLConnection;
import amo.randomFilm.datasource.exception.TmdbException;

/**
 * Class that represents a result of a TMDB image-request containing multiple posters.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 03.10.2011
 */
public class TmdbImage extends GsonObject {
    
    private static final String CONTENT_LENGTH = "Content-Length";
    
    /** Logger Object for this Class */
    private static final Logger logger = Logger.getLogger(TmdbImage.class);
    
    List<TmdbPoster> posters = null;
    
    Image image = null;
    
    public Image getImage() {
        if (image == null && posters != null && posters.size() > 0) {
            // fetch image
            for (TmdbPoster poster : posters) {
                if (poster.getType().equals("poster") && poster.getSize().equals("original")) {
                    try {
                        image = getPoster(poster.getUrl());
                    } catch (TmdbException e) {
                        logger.error("Could not fetch image from URL: " + poster.getUrl());
                    }
                    break;
                }
            }
        }
        return image;
    }
    
    private Image getPoster(String urlString) throws TmdbException {
        
        // generate URL
        URL urlToUse = null;
        try {
            urlToUse = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new TmdbException("Could not generate image URL: " + urlString, e);
        }
        
        // get Data
        HttpURLConnection conn = new HttpURLConnection(urlToUse, null);
        logger.debug("Connecting to URL: " + conn);
        try {
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            conn.connect();
        } catch (IOException e) {
            throw new TmdbException("Could not connect to image URL: " + urlString, e);
        }
        
        // validate
        try {
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new TmdbException("Server returned HTTP-Status: " + responseCode + " for image URL: " + urlString);
            }
        } catch (IOException e) {
            throw new TmdbException("Could not get Response Code from image URL: " + urlString, e);
        }
        
        // get Image Data
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
        } catch (IOException e) {
            throw new TmdbException("Could not read from image URL: " + urlString, e);
        }
        int bufsize = 4096;
        Map headerFields = conn.getHeaderFields();
        if (headerFields.containsKey(CONTENT_LENGTH)) {
            logger.debug("Has Content-Length!");
            List<String> lengthValues = (List<String>) headerFields.get(CONTENT_LENGTH);
            bufsize = Integer.parseInt(lengthValues.get(0));
            logger.debug("Content-Length is: " + bufsize);
        }
        byte[] buffer = new byte[bufsize];
        int numBytesRead = 0;
        int receivedBytes = 0;
        do {
            try {
                numBytesRead = inputStream.read(buffer, receivedBytes, bufsize - receivedBytes);
                receivedBytes += numBytesRead;
            } catch (IOException e) {
                throw new TmdbException("Could not read from HTTP input stream of image URL: " + urlString);
            }
        } while (receivedBytes < bufsize);
        
        // if (numBytesRead <= bufsize) {
        // logger.debug("All bytes read: " + numBytesRead);
        // } else {
        // logger.debug("More bytes to read than: " + numBytesRead);
        // }
        
        // convert bytes to image
        BufferedImage image;
        try {
            image = ImageIO.read(new ByteArrayInputStream(buffer));
        } catch (IOException e) {
            throw new TmdbException("Could not convert input bytes to Image of image URL: " + urlString);
        }
        
        return image;
    }
    
    @Override
    public String toString() {
        StringBuilder representation = new StringBuilder();
        representation.append("[").append(this.getClass().getSimpleName()).append(": ");
        addIfNotEmpty(representation, "posters", posters.toString());
        return representation.append("]").toString();
    }
    
}

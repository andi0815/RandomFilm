/**
 * 
 */
package amo.randomFilm.tmdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sun.net.www.protocol.http.HttpURLConnection;
import amo.randomFilm.configuration.Configuration;
import amo.randomFilm.tmdb.exception.TmdbException;

/**
 * @author andi
 */
public class TmdbSession {
    
    private static final String BASE_URL       = Configuration.getInstance().getProperty("tmdb.baseurl");
    
    private static final String API_KEY        = Configuration.getInstance().getProperty("tmdb.apikey");
    
    private static final String METHOD_GETAUTH = BASE_URL + "Auth.getToken/json/" + API_KEY;
    private static final String METHOD_SEARCH  = BASE_URL + "Movie.search/de/json/" + API_KEY + "/";
    
    private static TmdbSession  instance       = null;
    
    private static final Logger logger         = Logger.getLogger(TmdbSession.class);
    
    private TmdbSession() {
        
    }
    
    public static TmdbSession getInstance() {
        if (instance == null) {
            instance = new TmdbSession();
        }
        return instance;
    }
    
    public void getAuthToken() throws TmdbException {
        doRequest(METHOD_GETAUTH);
    }
    
    private void doRequest(String method) throws TmdbException {
        URL url = null;
        try {
            url = new URL(method);
        } catch (MalformedURLException e) {
            throw new TmdbException("Could not generate URL for Method: " + METHOD_GETAUTH, e);
        }
        HttpURLConnection conn = new HttpURLConnection(url, null);
        try {
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            conn.connect();
        } catch (IOException e) {
            throw new TmdbException("Could not connect to URL for Method: " + METHOD_GETAUTH, e);
        }
        
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
        } catch (IOException e) {
            throw new TmdbException("Could not read from URL for Method: " + METHOD_GETAUTH, e);
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        
        StringBuilder msg = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                msg.append(line);
            }
        } catch (IOException e) {
            throw new TmdbException("Could not read from InputStream of Method: " + METHOD_GETAUTH, e);
        }
        
        logger.log(Level.INFO, "Received Message: " + msg);
    }
    
    public String searchMovie(String title) throws TmdbException {
        doRequest(METHOD_SEARCH + title);
        return title;
    }
}

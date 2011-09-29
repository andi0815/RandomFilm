/**
 * 
 */
package amo.randomFilm.datasource.tmdb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sun.net.www.protocol.http.HttpURLConnection;
import amo.randomFilm.configuration.Configuration;
import amo.randomFilm.datasource.exception.TmdbException;
import amo.randomFilm.datasource.tmdb.data.Movie;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author andi
 */
public class TmdbSession {
    
    private static final String BASE_URL = Configuration.getInstance().getProperty("tmdb.baseurl");
    
    private static final String API_KEY = Configuration.getInstance().getProperty("tmdb.apikey");
    
    private static final String METHOD_GETAUTH = BASE_URL + "Auth.getToken/json/" + API_KEY;
    
    private static final String METHOD_SEARCH = BASE_URL + "Movie.search/de/json/" + API_KEY + "/";
    
    private static final String METHOD_INFO = BASE_URL + "Movie.getInfo/de/json/" + API_KEY + "/";
    
    private static final String METHOD_IMAGES = BASE_URL + "Movie.getImages/de/json/" + API_KEY + "/";
    
    private static TmdbSession instance = null;
    
    private static final Logger logger = Logger.getLogger(TmdbSession.class);
    
    private static final String encoding = System.getProperty("file.encoding");
    
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
    
    public static List<Movie> searchMovie(String title) throws TmdbException {
        String urlEncodedTitle = urlEncodeParameter(title);
        
        String jsonResult = doRequest(METHOD_SEARCH + urlEncodedTitle);
        logger.log(Level.DEBUG, "Got Search-Result: " + jsonResult);
        
        List<Movie> movies = extractMoviesFromJson(jsonResult);
        
        return movies;
    }
    
    private static List<Movie> extractMoviesFromJson(String jsonResult) {
        List<Movie> movies = new ArrayList<Movie>();
        // parse result
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(jsonResult).getAsJsonArray();
        Gson gson = new Gson();
        Iterator<JsonElement> iterator = array.iterator();
        while (iterator.hasNext()) {
            Movie movie = gson.fromJson(iterator.next(), Movie.class);
            if (movie != null) {
                movies.add(movie);
                logger.log(Level.DEBUG, "Found Movie: " + movie);
            }
        }
        return movies;
    }
    
    private static String urlEncodeParameter(String parameter) throws TmdbException {
        if (parameter == null || parameter.isEmpty()) {
            throw new TmdbException("Imput Parameter is empty or null!");
        }
        // prepare & execute Request
        String urlEncodedTitle = null;
        try {
            urlEncodedTitle = URLEncoder.encode(parameter, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new TmdbException("Could not URL-Encode: " + parameter, e);
        }
        return urlEncodedTitle;
    }
    
    public void getInfo(String tmdb_id) throws TmdbException {
        String result = doRequest(METHOD_INFO + tmdb_id);
        extractMoviesFromJson(result);
    }
    
    public byte[] getImage(String tmdb_id) throws TmdbException {
        String result = doRequest(METHOD_IMAGES + tmdb_id);
        logger.error("GET_IMAGE not implemented, yet. GOT: " + result);
        return null;
    }
    
    private static String doRequest(String method) throws TmdbException {
        URL url = null;
        try {
            url = new URL(method);
        } catch (MalformedURLException e) {
            throw new TmdbException("Could not generate URL for Method: " + method, e);
        }
        HttpURLConnection conn = new HttpURLConnection(url, null);
        logger.log(Level.INFO, "Connecting to URL: " + conn);
        try {
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            conn.connect();
        } catch (IOException e) {
            throw new TmdbException("Could not connect to URL for Method: " + method, e);
        }
        
        try {
            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new TmdbException("Server returned HTTP-Status: " + responseCode + " for Method: " + method);
            }
        }

        catch (IOException e) {
            throw new TmdbException("Could not get Response Code from URL for Method: " + method, e);
        }
        
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
        } catch (IOException e) {
            throw new TmdbException("Could not read from URL for Method: " + method, e);
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        
        StringBuilder msg = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                msg.append(line);
            }
        } catch (IOException e) {
            throw new TmdbException("Could not read from InputStream of Method: " + method, e);
        }
        
        logger.log(Level.INFO, "Received Message: " + msg);
        
        return msg.toString();
    }
    
}

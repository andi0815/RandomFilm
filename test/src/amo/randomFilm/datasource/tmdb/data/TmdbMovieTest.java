/**
 * 
 */
package amo.randomFilm.datasource.tmdb.data;

import java.awt.Image;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.model.UnknownTypes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 05.09.2011
 */
public class TmdbMovieTest extends AbstractTestBase {
    
    static String jsonMovie = "[{\"score\":null,\"popularity\":3,\"translated\":true,\"adult\":false,\"language\":\"de\",\"original_name\":\"Die drei Musketiere\",\"name\":\"Die drei Musketiere\",\"alternative_name\":null,\"movie_type\":\"movie\",\"id\":72522,\"imdb_id\":\"tt0831399\",\"url\":\"http://www.themoviedb.org/movie/72522\",\"votes\":0,\"rating\":0.0,\"certification\":\"\",\"overview\":\"Der trottelige Goofy, die zur Feigheit neigende Ente Donald und der vertr\u00e4umte M\u00e4userich Micky fristen ihr Dasein als Dienstboten des intriganten Musketierhauptmanns Carlo und tr\u00e4umen insgeheim davon, selbst als Musketiere Heldentaten zu vollbringen. Als Carlo die Prinzessin Minnie entf\u00fchren will und drei dumme Bauernopfer als deren Leibgarde sucht, erf\u00fcllt sich ihr Traum schneller als erwartet. Freilich hat Carlo nicht damit gerechnet, wie die drei Verlierer an ihrer neuen Aufgabe \u00fcber sich hinaus wachsen. \",\"released\":null,\"posters\":[{\"image\":{\"type\":\"poster\",\"size\":\"original\",\"height\":1500,\"width\":1500,\"url\":\"http://cf1.imgobject.com/posters/c44/4e4e72a27b9aa1051e000c44/die-drei-musketiere-original.jpg\",\"id\":\"4e4e72a27b9aa1051e000c44\"}},{\"image\":{\"type\":\"poster\",\"size\":\"mid\",\"height\":500,\"width\":500,\"url\":\"http://cf1.imgobject.com/posters/c44/4e4e72a27b9aa1051e000c44/die-drei-musketiere-mid.jpg\",\"id\":\"4e4e72a27b9aa1051e000c44\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w342\",\"height\":342,\"width\":342,\"url\":\"http://cf1.imgobject.com/posters/c44/4e4e72a27b9aa1051e000c44/die-drei-musketiere-w342.jpg\",\"id\":\"4e4e72a27b9aa1051e000c44\"}},{\"image\":{\"type\":\"poster\",\"size\":\"cover\",\"height\":185,\"width\":185,\"url\":\"http://cf1.imgobject.com/posters/c44/4e4e72a27b9aa1051e000c44/die-drei-musketiere-cover.jpg\",\"id\":\"4e4e72a27b9aa1051e000c44\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w154\",\"height\":154,\"width\":154,\"url\":\"http://cf1.imgobject.com/posters/c44/4e4e72a27b9aa1051e000c44/die-drei-musketiere-w154.jpg\",\"id\":\"4e4e72a27b9aa1051e000c44\"}},{\"image\":{\"type\":\"poster\",\"size\":\"thumb\",\"height\":92,\"width\":92,\"url\":\"http://cf1.imgobject.com/posters/c44/4e4e72a27b9aa1051e000c44/die-drei-musketiere-thumb.jpg\",\"id\":\"4e4e72a27b9aa1051e000c44\"}}],\"backdrops\":[],\"version\":5,\"last_modified_at\":\"2011-08-19 14:41:34\"},{\"score\":null,\"popularity\":3,\"translated\":true,\"adult\":false,\"language\":\"en\",\"original_name\":\"Les trois mousquetaires: La vengeance de Milady\",\"name\":\"Die drei Musketiere\",\"alternative_name\":\"Les Ferrets de la reine\",\"movie_type\":\"movie\",\"id\":4153,\"imdb_id\":\"tt0055548\",\"url\":\"http://www.themoviedb.org/movie/4153\",\"votes\":2,\"rating\":7.0,\"certification\":\"\",\"overview\":\"Zweiteilige Adaptation des Dumas-Stoffes\",\"released\":\"1961-10-04\",\"posters\":[{\"image\":{\"type\":\"poster\",\"size\":\"original\",\"height\":1599,\"width\":1100,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-original.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"mid\",\"height\":727,\"width\":500,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-mid.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"cover\",\"height\":269,\"width\":185,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-cover.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"thumb\",\"height\":134,\"width\":92,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-thumb.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w342\",\"height\":497,\"width\":342,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-w342.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w154\",\"height\":224,\"width\":154,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-w154.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}}],\"backdrops\":[],\"version\":77,\"last_modified_at\":\"2011-08-18 04:27:36\"},{\"score\":null,\"popularity\":3,\"translated\":true,\"adult\":false,\"language\":\"en\",\"original_name\":\"The Three Musketeers\",\"name\":\"Die drei Musketiere\",\"alternative_name\":\"Die drei Musketiere\",\"movie_type\":\"movie\",\"id\":52451,\"imdb_id\":\"tt1509767\",\"url\":\"http://www.themoviedb.org/movie/52451\",\"votes\":0,\"rating\":0.0,\"certification\":\"PG-13\",\"overview\":\"Der junge D'Artagnan macht sich auf dem Weg von seinem idyllischen Heimatdorf nach Paris, wo er den Musketieren beitreten will. Gleich bei seiner Ankunft legt er sich mit den erfahrenen Haudegen Athos, Porthos und Aramis an und schlie\u00dft dabei Freundschaft mit ihnen. Gemeinsam treten sie gegen den diabolischen Kardinal Richelieu, dessen Handlanger Rochefort und die durchtriebene Lady de Winter an, die Frankreich in den Krieg mit England treiben wollen, um den K\u00f6nig st\u00fcrzen und die Krone an sich rei\u00dfen zu k\u00f6nnen.\",\"released\":\"2011-10-21\",\"posters\":[{\"image\":{\"type\":\"poster\",\"size\":\"original\",\"height\":1443,\"width\":1000,\"url\":\"http://cf1.imgobject.com/posters/295/4dc791c45e73d624a0000295/the-three-musketeers-original.jpg\",\"id\":\"4dc791c45e73d624a0000295\"}},{\"image\":{\"type\":\"poster\",\"size\":\"mid\",\"height\":722,\"width\":500,\"url\":\"http://cf1.imgobject.com/posters/295/4dc791c45e73d624a0000295/the-three-musketeers-mid.jpg\",\"id\":\"4dc791c45e73d624a0000295\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w342\",\"height\":494,\"width\":342,\"url\":\"http://cf1.imgobject.com/posters/295/4dc791c45e73d624a0000295/the-three-musketeers-w342.jpg\",\"id\":\"4dc791c45e73d624a0000295\"}},{\"image\":{\"type\":\"poster\",\"size\":\"cover\",\"height\":267,\"width\":185,\"url\":\"http://cf1.imgobject.com/posters/295/4dc791c45e73d624a0000295/the-three-musketeers-cover.jpg\",\"id\":\"4dc791c45e73d624a0000295\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w154\",\"height\":222,\"width\":154,\"url\":\"http://cf1.imgobject.com/posters/295/4dc791c45e73d624a0000295/the-three-musketeers-w154.jpg\",\"id\":\"4dc791c45e73d624a0000295\"}},{\"image\":{\"type\":\"poster\",\"size\":\"thumb\",\"height\":133,\"width\":92,\"url\":\"http://cf1.imgobject.com/posters/295/4dc791c45e73d624a0000295/the-three-musketeers-thumb.jpg\",\"id\":\"4dc791c45e73d624a0000295\"}}],\"backdrops\":[{\"image\":{\"type\":\"backdrop\",\"size\":\"original\",\"height\":1080,\"width\":1920,\"url\":\"http://cf1.imgobject.com/backdrops/395/4d195a335e73d6083800a395/the-three-musketeers-original.jpg\",\"id\":\"4d195a335e73d6083800a395\"}},{\"image\":{\"type\":\"backdrop\",\"size\":\"poster\",\"height\":439,\"width\":780,\"url\":\"http://cf1.imgobject.com/backdrops/395/4d195a335e73d6083800a395/the-three-musketeers-poster.jpg\",\"id\":\"4d195a335e73d6083800a395\"}},{\"image\":{\"type\":\"backdrop\",\"size\":\"thumb\",\"height\":169,\"width\":300,\"url\":\"http://cf1.imgobject.com/backdrops/395/4d195a335e73d6083800a395/the-three-musketeers-thumb.jpg\",\"id\":\"4d195a335e73d6083800a395\"}},{\"image\":{\"type\":\"backdrop\",\"size\":\"w1280\",\"height\":720,\"width\":1280,\"url\":\"http://cf1.imgobject.com/backdrops/395/4d195a335e73d6083800a395/the-three-musketeers-w1280.jpg\",\"id\":\"4d195a335e73d6083800a395\"}}],\"version\":594,\"last_modified_at\":\"2011-09-05 11:26:15\"}]";
    
    @Test
    public void testMovieImport() {
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(jsonMovie).getAsJsonArray();
        Iterator<JsonElement> iterator = array.iterator();
        
        Gson gson = new Gson();
        
        TmdbMovie movie1 = gson.fromJson(iterator.next(), TmdbMovie.class);
        TmdbMovie movie2 = gson.fromJson(iterator.next(), TmdbMovie.class);
        TmdbMovie movie3 = gson.fromJson(iterator.next(), TmdbMovie.class);
        
        System.out.println("INPUT:  " + jsonMovie);
        System.out.println("MOVIE2: " + movie2);
        
        Assert.assertEquals(3.0d, movie3.getPopularity(), 0.00001);
        Assert.assertEquals(7.0d, movie2.getRating(), 0.0d);
        Assert.assertEquals(UnknownTypes.DOUBLE, movie2.getScore(), 0.0d);
        Assert.assertEquals("Les Ferrets de la reine", movie2.getAlternative_name());
        Assert.assertEquals("", movie2.getCertification());
        Assert.assertEquals("4153", movie2.getId());
        Assert.assertEquals("tt0055548", movie2.getImdb_id());
        Assert.assertEquals("en", movie2.getLanguage());
        Assert.assertEquals("2011-08-18 04:27:36", movie2.getLast_modified_at());
        Assert.assertEquals("Die drei Musketiere", movie2.getName());
        Assert.assertEquals("Les trois mousquetaires: La vengeance de Milady", movie2.getOriginal_name());
        Assert.assertEquals("Zweiteilige Adaptation des Dumas-Stoffes", movie2.getOverview());
        Assert.assertEquals("1961-10-04", movie2.getReleased());
        Assert.assertEquals("movie", movie2.getType());
        Assert.assertEquals(2, movie2.getVotes());
        Assert.assertEquals("en", movie3.getLanguage());
        Assert.assertEquals(false, movie3.isAdult());
        Assert.assertEquals(true, movie3.isTranslated());
        
        List<TmdbPoster> posters = movie2.getPosters();
        Assert.assertEquals(6, posters.size());
        TmdbPoster image = posters.get(0);
        Assert.assertEquals(
                "http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-original.jpg",
                image.getUrl());
        Assert.assertEquals(1599, image.getHeight());
        Assert.assertEquals(1100, image.getWidth());
        Assert.assertEquals("original", image.getSize());
        Assert.assertEquals("poster", image.getType());
    }
    
    @Test
    public void testExtInfo() {
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(jsonMovie).getAsJsonArray();
        Gson gson = new Gson();
        
        TmdbMovie movie = gson.fromJson(array.get(1), TmdbMovie.class);
        Assert.assertEquals(187, movie.getMovieRuntime());
        Assert.assertEquals("[Abenteuer]", movie.getMovieGenres().toString());
        
        // same in different order
        TmdbMovie movie2 = gson.fromJson(array.get(2), TmdbMovie.class);
        Assert.assertEquals("[Action, Abenteuer, Romanze]", movie2.getMovieGenres().toString());
        Assert.assertEquals(111, movie2.getMovieRuntime());
        
    }
    
    @Test
    public void testImage() throws InterruptedException {
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(jsonMovie).getAsJsonArray();
        Gson gson = new Gson();
        TmdbMovie movie = gson.fromJson(array.get(1), TmdbMovie.class);
        
        Image movieImage = movie.getMovieImage();
        Assert.assertNotNull(movieImage);
        
        showImage(movieImage, 2000);
        
    }
}

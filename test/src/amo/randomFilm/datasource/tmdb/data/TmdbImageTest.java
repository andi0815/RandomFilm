package amo.randomFilm.datasource.tmdb.data;

import java.awt.Image;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 03.10.2011
 */
public class TmdbImageTest extends AbstractTestBase {
    
    private String jsonImageResult = "[{\"id\":4153,\"name\":\"Die drei Musketiere\",\"posters\":[{\"image\":{\"type\":\"poster\",\"size\":\"original\",\"height\":1599,\"width\":1100,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-original.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"mid\",\"height\":727,\"width\":500,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-mid.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"cover\",\"height\":269,\"width\":185,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-cover.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"thumb\",\"height\":134,\"width\":92,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-thumb.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w342\",\"height\":497,\"width\":342,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-w342.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w154\",\"height\":224,\"width\":154,\"url\":\"http://cf1.imgobject.com/posters/8b6/4d5287857b9aa13aab0118b6/les-trois-mousquetaires-la-vengeance-de-milady-w154.jpg\",\"id\":\"4d5287857b9aa13aab0118b6\"}},{\"image\":{\"type\":\"poster\",\"size\":\"original\",\"height\":1541,\"width\":1100,\"url\":\"http://cf1.imgobject.com/posters/7e3/4d5287997b9aa13aba0117e3/les-trois-mousquetaires-la-vengeance-de-milady-original.jpg\",\"id\":\"4d5287997b9aa13aba0117e3\"}},{\"image\":{\"type\":\"poster\",\"size\":\"mid\",\"height\":700,\"width\":500,\"url\":\"http://cf1.imgobject.com/posters/7e3/4d5287997b9aa13aba0117e3/les-trois-mousquetaires-la-vengeance-de-milady-mid.jpg\",\"id\":\"4d5287997b9aa13aba0117e3\"}},{\"image\":{\"type\":\"poster\",\"size\":\"cover\",\"height\":259,\"width\":185,\"url\":\"http://cf1.imgobject.com/posters/7e3/4d5287997b9aa13aba0117e3/les-trois-mousquetaires-la-vengeance-de-milady-cover.jpg\",\"id\":\"4d5287997b9aa13aba0117e3\"}},{\"image\":{\"type\":\"poster\",\"size\":\"thumb\",\"height\":129,\"width\":92,\"url\":\"http://cf1.imgobject.com/posters/7e3/4d5287997b9aa13aba0117e3/les-trois-mousquetaires-la-vengeance-de-milady-thumb.jpg\",\"id\":\"4d5287997b9aa13aba0117e3\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w342\",\"height\":479,\"width\":342,\"url\":\"http://cf1.imgobject.com/posters/7e3/4d5287997b9aa13aba0117e3/les-trois-mousquetaires-la-vengeance-de-milady-w342.jpg\",\"id\":\"4d5287997b9aa13aba0117e3\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w154\",\"height\":216,\"width\":154,\"url\":\"http://cf1.imgobject.com/posters/7e3/4d5287997b9aa13aba0117e3/les-trois-mousquetaires-la-vengeance-de-milady-w154.jpg\",\"id\":\"4d5287997b9aa13aba0117e3\"}},{\"image\":{\"type\":\"poster\",\"size\":\"original\",\"height\":1439,\"width\":998,\"url\":\"http://cf1.imgobject.com/posters/bcd/4d5296f97b9aa13ab4011bcd/les-trois-mousquetaires-la-vengeance-de-milady-original.jpg\",\"id\":\"4d5296f97b9aa13ab4011bcd\"}},{\"image\":{\"type\":\"poster\",\"size\":\"mid\",\"height\":721,\"width\":500,\"url\":\"http://cf1.imgobject.com/posters/bcd/4d5296f97b9aa13ab4011bcd/les-trois-mousquetaires-la-vengeance-de-milady-mid.jpg\",\"id\":\"4d5296f97b9aa13ab4011bcd\"}},{\"image\":{\"type\":\"poster\",\"size\":\"cover\",\"height\":267,\"width\":185,\"url\":\"http://cf1.imgobject.com/posters/bcd/4d5296f97b9aa13ab4011bcd/les-trois-mousquetaires-la-vengeance-de-milady-cover.jpg\",\"id\":\"4d5296f97b9aa13ab4011bcd\"}},{\"image\":{\"type\":\"poster\",\"size\":\"thumb\",\"height\":133,\"width\":92,\"url\":\"http://cf1.imgobject.com/posters/bcd/4d5296f97b9aa13ab4011bcd/les-trois-mousquetaires-la-vengeance-de-milady-thumb.jpg\",\"id\":\"4d5296f97b9aa13ab4011bcd\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w342\",\"height\":493,\"width\":342,\"url\":\"http://cf1.imgobject.com/posters/bcd/4d5296f97b9aa13ab4011bcd/les-trois-mousquetaires-la-vengeance-de-milady-w342.jpg\",\"id\":\"4d5296f97b9aa13ab4011bcd\"}},{\"image\":{\"type\":\"poster\",\"size\":\"w154\",\"height\":222,\"width\":154,\"url\":\"http://cf1.imgobject.com/posters/bcd/4d5296f97b9aa13ab4011bcd/les-trois-mousquetaires-la-vengeance-de-milady-w154.jpg\",\"id\":\"4d5296f97b9aa13ab4011bcd\"}}],\"backdrops\":[]}]";
    
    @Test
    public void testIt() throws Exception {
        
        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(jsonImageResult).getAsJsonArray();
        Gson gson = new Gson();
        TmdbImage tmdbImageResult = gson.fromJson(array.get(0), TmdbImage.class);
        System.out.println(tmdbImageResult);
        final Image image = tmdbImageResult.getImage();
        System.out.println(image);
        
        showImage(image, 2000);
        
        // don't make another http-request !
        tmdbImageResult.getImage();
        
    }
    
}

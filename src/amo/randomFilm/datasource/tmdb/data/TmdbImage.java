/**
 * 
 */
package amo.randomFilm.datasource.tmdb.data;

import amo.randomFilm.datasource.UnknownTypes;

/**
 * TMDB Data Object that represents an image & poster.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 27.09.2011
 * 
 */
public class TmdbImage extends GsonObject {
    
    private ImageData image = null;
    
    public String getType() {
        return image != null ? image.type : UnknownTypes.STRING;
    }
    
    public String getSize() {
        return image != null ? image.size : UnknownTypes.STRING;
    }
    
    public int getHeight() {
        return image != null ? image.height : UnknownTypes.INT;
    }
    
    public int getWidth() {
        return image != null ? image.width : UnknownTypes.INT;
    }
    
    public String getUrl() {
        return image != null ? image.url : UnknownTypes.STRING;
    }
    
    public String getId() {
        return image != null ? image.id : UnknownTypes.STRING;
    }
    
    @Override
    public String toString() {
        return image != null ? image.toString() : "Image: NO_DATA";
    }
    
    private class ImageData extends GsonObject {
        
        private String type = UnknownTypes.STRING;
        
        private String size = UnknownTypes.STRING;
        
        private int height = UnknownTypes.INT;
        
        private int width = UnknownTypes.INT;
        
        private String url = UnknownTypes.STRING;
        
        private String id = UnknownTypes.STRING;
        
        @Override
        public String toString() {
            StringBuilder representation = new StringBuilder();
            representation.append("Image: ");
            addIfNotEmpty(representation, "type", type);
            addIfNotEmpty(representation, "size", size);
            addIfNotEmpty(representation, "height", height);
            addIfNotEmpty(representation, "width", width);
            addIfNotEmpty(representation, "url", url);
            addIfNotEmpty(representation, "id", id);
            return representation.toString();
        }
    }
}

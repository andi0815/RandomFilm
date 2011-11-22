/**
 * 
 */
package amo.randomFilm.datasource.tmdb.data;

import amo.randomFilm.model.UnknownTypes;

/**
 * Base Class for objects imported with Google gson.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 27.09.2011
 * 
 */
public class GsonObject {
    
    /**
     * Adds a given name/value-pair to the given StringBuilder. <br>
     * Fomat: <code>&lt;name&gt;="&lt;value&gt;", </code>
     * 
     * @param builder
     *            the StringBuilder to append to
     * @param name
     *            the name of the parameter
     * @param value
     *            the value of the parameter
     */
    protected void addIfNotEmpty(StringBuilder builder, String name, Object value) {
        if (value != null && value != UnknownTypes.STRING && !Integer.valueOf(UnknownTypes.INT).equals(value)
                && !Double.valueOf(UnknownTypes.DOUBLE).equals(value)) {
            builder.append(name).append("=\"").append(value).append("\", ");
        }
    }
    
}

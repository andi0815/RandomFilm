package amo.randomFilm.configuration;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import amo.randomFilm.gui.util.Dialogs;

/**
 * Provides configuration parameters.
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 */
public class Configuration {
    
    private static final String CONFIG_FILE_PATH = "config.properties";
    
    /** the properties object that holds the parameters */
    private static Properties properties = new Properties();
    
    /** Singleton-Reference */
    private static Configuration instance = null;
    
    /**
     * Constructs a new Configuration instance with the configuration at the given file path.
     * 
     * @param filePath
     *            the path to the configuration file
     */
    private Configuration(String filePath) {
        try {
            properties.load(new BufferedInputStream(new FileInputStream(CONFIG_FILE_PATH)));
        } catch (FileNotFoundException e) {
            Dialogs.showWarning("Could not find Properties file: " + filePath, null);
            System.exit(1);
        } catch (IOException e) {
            Dialogs.showWarning("Could not read properly from Properties file: " + filePath + ". \nCause:\n"
                    + e.getMessage(), null);
            System.exit(1);
        }
    }
    
    /**
     * @return the configuration instance
     */
    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration(CONFIG_FILE_PATH);
        }
        return instance;
        
    }
    
    /**
     * Returns the requested property value or <code>null</code>, if it could not be found.
     * 
     * @param key
     *            the property to look for
     * @return the property value or <code>null</code>, if it could not be found.
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
}

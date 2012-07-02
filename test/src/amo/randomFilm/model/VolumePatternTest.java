/**
 * 
 */
package amo.randomFilm.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import amo.randomFilm.configuration.Configuration;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 02.07.2012
 * 
 */
@RunWith(value = Parameterized.class)
public class VolumePatternTest {
    
    private static final Pattern VOLUME_REGEX = Pattern.compile(Configuration.getInstance().getProperty("volumeRegex"));
    
    static {
        System.out.println("======= REGEX: '" + VOLUME_REGEX.pattern() + "' ============");
    }
    
    private String inputFilename;
    
    private boolean matches;
    
    public VolumePatternTest(String inputFilename, boolean matches) {
        this.inputFilename = inputFilename;
        this.matches = matches;
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { //
        { "disc1", true }, //
                { "disc22", true }, //
                { "disc 122", true }, //
                { "disc 122 4", false }, //
                { "disc 145d", false }, //
                { "disc ee", false }, //
                { "disk 89", true }, //
                { "disk89", true }, //
                { "staffel 3", true }, //
                { "staffel3", true }, //
                { "season 009", true }, //
                { "season009", true }, //
                { "cd 009", true }, //
                { "dvd 009", true }, //
                { "bluray 009", true }, //
                { "blu-ray 009", true }, //
        };
        return Arrays.asList(data);
    }
    
    @Test
    public void pushTest() {
        boolean testresult = VOLUME_REGEX.matcher(inputFilename).matches();
        System.out.println(String.format("INPUT: %-30s  -matches?-> %b / %b", inputFilename, matches, testresult));
        Assert.assertEquals(matches, testresult);
    }
}

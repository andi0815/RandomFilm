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
public class PrefixPatternTest {
    
    private static final Pattern PREFIX_PATTERN = Pattern.compile(Configuration.getInstance()
            .getProperty("prefixRegex"));
    
    static {
        System.out.println("======= REGEX: '" + PREFIX_PATTERN.pattern() + "' ============");
    }
    
    private String inputFilename;
    
    private boolean matches;
    
    public PrefixPatternTest(String inputFilename, boolean matches) {
        this.inputFilename = inputFilename;
        this.matches = matches;
    }
    
    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] { //
        { "#", true }, //
                { "#123", true }, //
                { "# 123", true }, //
                { "#sdfghjk", true }, //
                { "+", true }, //
                { "+ ", true }, //
                { "+ aa", true }, //
                { "~", false }, //
                { "vfzuikn", false }, //
                { " 123", false }, //
        };
        return Arrays.asList(data);
    }
    
    @Test
    public void pushTest() {
        boolean testresult = PREFIX_PATTERN.matcher(inputFilename).matches();
        System.out.println(String.format("INPUT: %-30s  -matches?-> %b / %b", inputFilename, matches, testresult));
        Assert.assertEquals(matches, testresult);
    }
}

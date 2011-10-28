package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;

import org.junit.Test;

import amo.randomFilm.AbstractTestBase;
import amo.randomFilm.datasource.tmdb.TmdbFacade;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 09.10.2011
 */
public class MoviePanelTest extends AbstractTestBase {
    
    @Test
    public void testIt() {
        MoviePanel moviePanel = new MoviePanel(new File("testfile.avi"), "Matrix", TmdbFacade.getInstance(), 500, 60,
                null);
        // moviePanel.setPreferredSize(new Dimension(500, 50));
        // moviePanel.invalidate();
        showComponent(moviePanel, 20000);
    }
    
    // @Test
    public void testResize() throws InterruptedException {
        Image movieImage = getDummyMovie().getMovieImage();
        MyImageObserver imageObserver = new MyImageObserver();
        // showImage(movieImage, 3000);
        movieImage.getWidth(imageObserver);
        System.out.println("OBSERVER: " + imageObserver.width + "," + imageObserver.height);
        Image resizedImage = movieImage.getScaledInstance(50, -1, Image.SCALE_SMOOTH);
        // movieImage.flush();
        // resizedImage.flush();
        
        System.out.println("IMAGE: " + resizedImage + " - SIZE: " + resizedImage.getWidth(imageObserver) + " / "
                + resizedImage.getHeight(imageObserver));
        System.out.println("OBSERVER: " + imageObserver.width + "," + imageObserver.height);
        showImage(resizedImage, 10000, imageObserver.width, imageObserver.height);
    }
    
    class MyImageObserver implements ImageObserver {
        
        private int width;
        private int height;
        
        @Override
        public boolean imageUpdate(Image image, int infoflags, int x, int y, int width, int height) {
            System.out.println(image + " - " + infoflags + " - " + x + " - " + y + " - " + width + " - " + height);
            switch (infoflags) {
                case ImageObserver.ABORT:
                    System.out.println("ABORT");
                    break;
                case ImageObserver.ALLBITS:
                    System.out.println("ALLBITS");
                    // this.width = width;
                    // this.height = height;
                    break;
                case ImageObserver.ERROR:
                    System.out.println("ERROR");
                    break;
                case ImageObserver.FRAMEBITS:
                    System.out.println("FRAMEBITS");
                    break;
                case ImageObserver.HEIGHT:
                    System.out.println("HEIGHT");
                    break;
                case ImageObserver.PROPERTIES:
                    System.out.println("PROPERTIES");
                    break;
                case ImageObserver.SOMEBITS:
                    System.out.println("SOMEBITS");
                    break;
                case ImageObserver.WIDTH:
                    System.out.println("WIDTH");
                    break;
                default:
                    System.out.println("NONE: " + infoflags);
                    this.width = width;
                    this.height = height;
            }
            return true;
        }
        
    }
}

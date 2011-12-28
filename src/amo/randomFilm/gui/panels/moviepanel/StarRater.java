package amo.randomFilm.gui.panels.moviepanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.swing.JPanel;

import amo.randomFilm.model.Movie;

/**
 * The star rater panel (see:
 * http://blog.noblemaster.com/2010/08/31/star-rating-panel-for-java-swing/).
 * 
 * @author noblemaster
 * @since August 30, 2010
 */
public class StarRater extends JPanel implements Updateable {
    
    /** The listener. */
    public static interface StarListener {
        
        /**
         * Called result.
         * 
         * @param selection
         *            The selection.
         */
        void handleSelection(int selection);
    }
    
    /** Listeners. */
    private List<StarListener> listeners = new ArrayList<StarListener>();
    
    /** The number of stars n. */
    private int stars;
    /** The rating [0, 1]. 0 = no rating. */
    private float rating;
    /** The selection [0, n]. 0 = no selection. */
    private int selection;
    /** The rollover [0, n]. 0 = no rollover. */
    private int rollover;
    /** True for clicked this time. */
    private boolean done;
    
    /**
     * The constructor.
     */
    public StarRater() {
        this(5);
    }
    
    /**
     * The constructor.
     * 
     * @param stars
     *            The number of stars n.
     */
    public StarRater(int stars) {
        this(stars, 0);
    }
    
    /**
     * The constructor.
     * 
     * @param stars
     *            The number of stars n.
     * @param rating
     *            The rating [0, 1]. 0 = no rating.
     */
    public StarRater(int stars, float rating) {
        this(stars, rating, 0);
    }
    
    /**
     * The constructor.
     * 
     * @param stars
     *            The number of stars n.
     * @param rating
     *            The rating [0, 1]. 0 = no rating.
     * @param selection
     *            The selection [0, n]. 0 = no selection.
     */
    public StarRater(int stars, float rating, int selection) {
        this.stars = stars;
        if (rating > 1)
            rating = 1;
        this.rating = rating;
        this.selection = selection;
        this.rollover = 0;
        this.done = false;
        
        // set look
        this.setOpaque(false);
        this.setLayout(null);
        
        // listen to selections
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
                if (StarRater.this.isEnabled()) {
                    if (!StarRater.this.done) {
                        StarRater.this.rollover = 1 + (event.getX() / STAR_BACKGROUND_IMAGE.getWidth(null));
                        StarRater.this.repaint();
                    }
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent event) {
                if (StarRater.this.isEnabled()) {
                    StarRater.this.rollover = 0;
                    StarRater.this.done = false;
                    StarRater.this.repaint();
                }
            }
            
            @Override
            public void mousePressed(MouseEvent event) {
                if (StarRater.this.isEnabled()) {
                    StarRater.this.rollover = 0;
                    StarRater.this.done = true;
                    StarRater.this.selection = 1 + (event.getX() / STAR_BACKGROUND_IMAGE.getWidth(null));
                    for (int i = 0; i < StarRater.this.listeners.size(); i++) {
                        StarRater.this.listeners.get(i).handleSelection(StarRater.this.selection);
                    }
                    StarRater.this.repaint();
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent event) {
                if (StarRater.this.isEnabled()) {
                    if (!StarRater.this.done) {
                        StarRater.this.rollover = 1 + (event.getX() / STAR_BACKGROUND_IMAGE.getWidth(null));
                        StarRater.this.repaint();
                    }
                }
            }
        });
    }
    
    /**
     * Called to enable/disable.
     * 
     * @param enabled
     *            True for enabled.
     */
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        
        // do stuff
        if (!enabled) {
            this.rollover = 0;
            this.repaint();
        }
    }
    
    /**
     * Returns the rating.
     * 
     * @return The rating [0, 1]. 0 = no rating.
     */
    public float getRating() {
        return this.rating;
    }
    
    /**
     * Sets the rating.
     * 
     * @param rating
     *            The rating [0, 1]. 0 = no rating. Rating > 1 is truncated to 1.
     */
    public void setRating(float rating) {
        if (rating > 1)
            rating = 1;
        this.rating = rating;
        this.repaint();
    }
    
    /**
     * Returns the selection.
     * 
     * @return The selection [0, n]. 0 = no selection.
     */
    public int getSelection() {
        return this.selection;
    }
    
    /**
     * Sets the selection.
     * 
     * @param selection
     *            The selection [0, n]. 0 = no selection.
     */
    public void setSelection(int selection) {
        this.selection = selection;
        this.repaint();
    }
    
    /**
     * Returns the preferred size.
     * 
     * @return The preferred size.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(this.stars * STAR_BACKGROUND_IMAGE.getWidth(null), STAR_BACKGROUND_IMAGE.getHeight(null));
    }
    
    @Override
    public Dimension getMaximumSize() {
        return this.getPreferredSize();
    }
    
    /**
     * Paints this component.
     * 
     * @param g
     *            Where to paint to.
     */
    @Override
    protected void paintComponent(Graphics g) {
        // draw stars
        int w = STAR_BACKGROUND_IMAGE.getWidth(null);
        int h = STAR_BACKGROUND_IMAGE.getHeight(null);
        int x = 0;
        float relativeRating = this.rating * this.stars;
        
        for (int i = 0; i < this.stars; i++) {
            g.drawImage(STAR_BACKGROUND_IMAGE, x, 0, null);
            if (relativeRating > i) {
                int dw = (relativeRating >= (i + 1)) ? w : Math.round((relativeRating - i) * w);
                g.drawImage(STAR_FOREGROUND_IMAGE, x, 0, x + dw, h, 0, 0, dw, h, null);
            }
            if (this.selection > i) {
                g.drawImage(STAR_SELECTION_IMAGE, x, 0, null);
            }
            if (this.rollover > i) {
                g.drawImage(STAR_ROLLOVER_IMAGE, x, 0, null);
            }
            x += w;
        }
    }
    
    /**
     * Adds a listener.
     * 
     * @param listener
     *            The listener to add.
     */
    public void addStarListener(StarListener listener) {
        this.listeners.add(listener);
    }
    
    /**
     * Removes a listener.
     * 
     * @param listener
     *            The listener to add.
     */
    public void removeStarListener(StarListener listener) {
        this.listeners.remove(listener);
    }
    
    /**
     * Converts an image to a compressed byte array. GZIPs the image to reduce the size. Use
     * compressedByteArrayToImage(byte[] data) to retrieve the original image. The image is not
     * recognizable as image from standard tools.
     * 
     * @param image
     *            The image to convert.
     * @return The byte array.
     * @throws IOException
     *             if something goes wrong.
     */
    public byte[] imageToCompressedByteArray(Image image) throws IOException {
        // get image size
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        
        // store image data as raw int values
        try {
            int[] imageSource = new int[width * height];
            PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, imageSource, 0, width);
            pg.grabPixels();
            
            // zip data
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            GZIPOutputStream zippedStream = new GZIPOutputStream(byteStream);
            ObjectOutputStream objectStream = new ObjectOutputStream(zippedStream);
            objectStream.writeShort(width);
            objectStream.writeShort(height);
            objectStream.writeObject(imageSource);
            objectStream.flush();
            objectStream.close();
            return byteStream.toByteArray();
        } catch (Exception e) {
            throw new IOException("Error storing image in object: " + e);
        }
    }
    
    /**
     * Converts a byte array to an image that has previously been converted with
     * imageToCompressedByteArray(Image image). The image is not recognizable as image from standard
     * tools.
     * 
     * @param data
     *            The image.
     * @return The image.
     */
    public static Image compressedByteArrayToImage(byte[] data) {
        try {
            // unzip data
            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            GZIPInputStream zippedStream = new GZIPInputStream(byteStream);
            ObjectInputStream objectStream = new ObjectInputStream(zippedStream);
            int width = objectStream.readShort();
            int height = objectStream.readShort();
            int[] imageSource = (int[]) objectStream.readObject();
            objectStream.close();
            
            // create image
            MemoryImageSource mis = new MemoryImageSource(width, height, imageSource, 0, width);
            return Toolkit.getDefaultToolkit().createImage(mis);
        } catch (Exception e) {
            return null;
        }
    }
    
    /** The image. */
    private static final Image STAR_BACKGROUND_IMAGE = compressedByteArrayToImage(new byte[] { (byte) 0x1f,
            (byte) 0x8b, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x9d, (byte) 0x92, (byte) 0xbb, (byte) 0x4b, (byte) 0x82, (byte) 0x51, (byte) 0x18,
            (byte) 0x87, (byte) 0x8f, (byte) 0x92, (byte) 0x4d, (byte) 0x0d, (byte) 0x2e, (byte) 0xb5, (byte) 0x36,
            (byte) 0x04, (byte) 0x41, (byte) 0x54, (byte) 0xd0, (byte) 0x56, (byte) 0x11, (byte) 0xb4, (byte) 0x34,
            (byte) 0x35, (byte) 0x34, (byte) 0xd4, (byte) 0xd0, (byte) 0x12, (byte) 0x84, (byte) 0xf5, (byte) 0x17,
            (byte) 0x48, (byte) 0xa0, (byte) 0xd5, (byte) 0x5a, (byte) 0x6b, (byte) 0x44, (byte) 0x4b, (byte) 0xde,
            (byte) 0x09, (byte) 0x11, (byte) 0x13, (byte) 0xd2, (byte) 0xf8, (byte) 0xf0, (byte) 0xcb, (byte) 0x48,
            (byte) 0xfd, (byte) 0x44, (byte) 0x10, (byte) 0x13, (byte) 0x45, (byte) 0x04, (byte) 0x45, (byte) 0xc1,
            (byte) 0xfb, (byte) 0x6d, (byte) 0x0f, (byte) 0xa2, (byte) 0xb6, (byte) 0xbc, (byte) 0xe5, (byte) 0xe9,
            (byte) 0x77, (byte) 0x44, (byte) 0xc1, (byte) 0x4a, (byte) 0xbf, (byte) 0xb0, (byte) 0xe1, (byte) 0x59,
            (byte) 0xce, (byte) 0x79, (byte) 0x1f, (byte) 0xde, (byte) 0xf7, (byte) 0xbc, (byte) 0xe7, (byte) 0x77,
            (byte) 0xff, (byte) 0x4a, (byte) 0x64, (byte) 0xc7, (byte) 0x63, (byte) 0x44, (byte) 0x4e, (byte) 0xe4,
            (byte) 0xaa, (byte) 0x23, (byte) 0x22, (byte) 0xdd, (byte) 0xdb, (byte) 0xdc, (byte) 0x12, (byte) 0x14,
            (byte) 0xb3, (byte) 0xea, (byte) 0x97, (byte) 0x87, (byte) 0x5b, (byte) 0x29, (byte) 0x21, (byte) 0x27,
            (byte) 0x4a, (byte) 0x42, (byte) 0x24, (byte) 0x84, (byte) 0x68, (byte) 0x34, (byte) 0x9a, (byte) 0x61,
            (byte) 0xac, (byte) 0x80, (byte) 0x69, (byte) 0x91, (byte) 0xfb, (byte) 0xbf, (byte) 0xe0, (byte) 0xc1,
            (byte) 0xe9, (byte) 0x3f, (byte) 0xdd, (byte) 0x75, (byte) 0x40, (byte) 0x79, (byte) 0x9e, (byte) 0x7f,
            (byte) 0x77, (byte) 0x38, (byte) 0x1c, (byte) 0x93, (byte) 0x23, (byte) 0xba, (byte) 0x12, (byte) 0x10,
            (byte) 0x35, (byte) 0x99, (byte) 0x4c, (byte) 0xcd, (byte) 0x5c, (byte) 0x2e, (byte) 0xd7, (byte) 0x8a,
            (byte) 0xc7, (byte) 0xe3, (byte) 0x17, (byte) 0x23, (byte) 0xfa, (byte) 0xfb, (byte) 0xac, (byte) 0xb7,
            (byte) 0x20, (byte) 0x08, (byte) 0xb4, (byte) 0x5a, (byte) 0xad, (byte) 0xd2, (byte) 0x4a, (byte) 0xa5,
            (byte) 0x42, (byte) 0x39, (byte) 0x8e, (byte) 0x5b, (byte) 0x1c, (byte) 0x52, (byte) 0x2b, (byte) 0x07,
            (byte) 0x4b, (byte) 0x5d, (byte) 0xe7, (byte) 0x4a, (byte) 0xab, (byte) 0xd5, (byte) 0x3e, (byte) 0x1b,
            (byte) 0x8d, (byte) 0x46, (byte) 0x6a, (byte) 0xb7, (byte) 0xdb, (byte) 0xeb, (byte) 0xc5, (byte) 0x62,
            (byte) 0xb1, (byte) 0xe3, (byte) 0x96, (byte) 0xcb, (byte) 0xe5, (byte) 0x46, (byte) 0x2a, (byte) 0x95,
            (byte) 0xaa, (byte) 0x59, (byte) 0xad, (byte) 0xd6, (byte) 0x27, (byte) 0x83, (byte) 0xc1, (byte) 0x70,
            (byte) 0x86, (byte) 0x9a, (byte) 0x1d, (byte) 0xd4, (byte) 0xce, (byte) 0x81, (byte) 0x09, (byte) 0xa0,
            (byte) 0x00, (byte) 0x6d, (byte) 0xd4, (byte) 0xd3, (byte) 0x50, (byte) 0x28, (byte) 0xf4, (byte) 0x91,
            (byte) 0x4c, (byte) 0x26, (byte) 0x99, (byte) 0xd7, (byte) 0xee, (byte) 0xf5, (byte) 0x85, (byte) 0x4b,
            (byte) 0x4b, (byte) 0xa5, (byte) 0x12, (byte) 0xa3, (byte) 0x5d, (byte) 0x28, (byte) 0x14, (byte) 0x6a,
            (byte) 0xe9, (byte) 0x74, (byte) 0xba, (byte) 0x11, (byte) 0x8d, (byte) 0x46, (byte) 0xa9, (byte) 0xcf,
            (byte) 0xe7, (byte) 0x7b, (byte) 0xb3, (byte) 0x58, (byte) 0x2c, (byte) 0xcb, (byte) 0xdd, (byte) 0xb7,
            (byte) 0xea, (byte) 0x6d, (byte) 0x36, (byte) 0x1b, (byte) 0x0d, (byte) 0x87, (byte) 0xc3, (byte) 0x9d,
            (byte) 0x79, (byte) 0x7f, (byte) 0xba, (byte) 0x6c, (byte) 0x0e, (byte) 0x06, (byte) 0x7c, (byte) 0x9a,
            (byte) 0xcf, (byte) 0xe7, (byte) 0x29, (byte) 0x76, (byte) 0x42, (byte) 0x3d, (byte) 0x1e, (byte) 0xcf,
            (byte) 0xa1, (byte) 0xd9, (byte) 0x6c, (byte) 0xee, (byte) 0x7f, (byte) 0x87, (byte) 0x06, (byte) 0x73,
            (byte) 0xb1, (byte) 0x19, (byte) 0x7e, (byte) 0xb9, (byte) 0xcc, (byte) 0xeb, (byte) 0x77, (byte) 0xdd,
            (byte) 0x6e, (byte) 0xf7, (byte) 0xc1, (byte) 0x80, (byte) 0x3d, (byte) 0x48, (byte) 0x81, (byte) 0x4d,
            (byte) 0xaf, (byte) 0xd7, (byte) 0x7f, (byte) 0xb2, (byte) 0xf9, (byte) 0xfa, (byte) 0x5d, (byte) 0xe6,
            (byte) 0xf5, (byte) 0x5c, (byte) 0xbf, (byte) 0xdf, (byte) 0xaf, (byte) 0x14, (byte) 0xd9, (byte) 0xfb,
            (byte) 0x36, (byte) 0xdb, (byte) 0x3b, (byte) 0x6a, (byte) 0x7e, (byte) 0xcd, (byte) 0xcb, (byte) 0xc8,
            (byte) 0x66, (byte) 0xb3, (byte) 0x2d, (byte) 0x97, (byte) 0xcb, (byte) 0x35, (byte) 0x25, (byte) 0xe2,
            (byte) 0xab, (byte) 0x40, (byte) 0x33, (byte) 0x12, (byte) 0x89, (byte) 0x7c, (byte) 0x73, (byte) 0xe1,
            (byte) 0x75, (byte) 0xc8, (byte) 0x64, (byte) 0x32, (byte) 0x2d, (byte) 0xaf, (byte) 0xd7, (byte) 0xbb,
            (byte) 0x26, (byte) 0xe2, (byte) 0x5f, (byte) 0x63, (byte) 0xfe, (byte) 0x66, (byte) 0x22, (byte) 0x91,
            (byte) 0x60, (byte) 0x6e, (byte) 0x3d, (byte) 0x16, (byte) 0x8b, (byte) 0xb1, (byte) 0x7f, (byte) 0x67,
            (byte) 0x19, (byte) 0xac, (byte) 0x21, (byte) 0x43, (byte) 0x14, (byte) 0xbb, (byte) 0x6f, (byte) 0x05,
            (byte) 0x02, (byte) 0x81, (byte) 0x5d, (byte) 0x11, (byte) 0xff, (byte) 0x11, (byte) 0x99, (byte) 0xa3,
            (byte) 0xc1, (byte) 0x60, (byte) 0xb0, (byte) 0x89, (byte) 0xcc, (byte) 0xde, (byte) 0xe8, (byte) 0x74,
            (byte) 0xba, (byte) 0x05, (byte) 0x9c, (byte) 0x6d, (byte) 0x00, (byte) 0x01, (byte) 0xff, (byte) 0x4e,
            (byte) 0x9d, (byte) 0x4e, (byte) 0x27, (byte) 0xcb, (byte) 0x94, (byte) 0x1a, (byte) 0x3d, (byte) 0x06,
            (byte) 0xb9, (byte) 0xe3, (byte) 0x80, (byte) 0x03, (byte) 0x97, (byte) 0x60, (byte) 0x66, (byte) 0xc0,
            (byte) 0xfd, (byte) 0x2a, (byte) 0xb8, (byte) 0x03, (byte) 0xe7, (byte) 0x43, (byte) 0x7a, (byte) 0xcb,
            (byte) 0xba, (byte) 0x39, (byte) 0x94, (byte) 0x8a, (byte) 0xcc, (byte) 0xc7, (byte) 0xb2, (byte) 0x3a,
            (byte) 0xdf, (byte) 0xcd, (byte) 0x4c, (byte) 0xef, (byte) 0xec, (byte) 0x0b, (byte) 0xb7, (byte) 0x3f,
            (byte) 0xb8, (byte) 0x26, (byte) 0x21, (byte) 0x04, (byte) 0x00, (byte) 0x00, });
    /** The image. */
    private static final Image STAR_FOREGROUND_IMAGE = compressedByteArrayToImage(new byte[] { (byte) 0x1f,
            (byte) 0x8b, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x9d, (byte) 0x92, (byte) 0xc9, (byte) 0x4b, (byte) 0x5b, (byte) 0x51, (byte) 0x14,
            (byte) 0x87, (byte) 0xcf, (byte) 0xbd, (byte) 0x6f, (byte) 0xc8, (byte) 0x7b, (byte) 0xe9, (byte) 0x5b,
            (byte) 0x64, (byte) 0xa3, (byte) 0xdb, (byte) 0x2e, (byte) 0x04, (byte) 0x41, (byte) 0xb4, (byte) 0xd0,
            (byte) 0x5d, (byte) 0x2d, (byte) 0x05, (byte) 0x37, (byte) 0xae, (byte) 0x5c, (byte) 0xb8, (byte) 0xd0,
            (byte) 0x45, (byte) 0x37, (byte) 0x82, (byte) 0x58, (byte) 0xff, (byte) 0x02, (byte) 0x11, (byte) 0x9c,
            (byte) 0xb0, (byte) 0xb6, (byte) 0xc5, (byte) 0x6c, (byte) 0xba, (byte) 0x10, (byte) 0x11, (byte) 0x6a,
            (byte) 0xe2, (byte) 0x04, (byte) 0x62, (byte) 0x29, (byte) 0x2a, (byte) 0x6a, (byte) 0x54, (byte) 0x22,
            (byte) 0x0e, (byte) 0x10, (byte) 0xc4, (byte) 0xa9, (byte) 0x01, (byte) 0x07, (byte) 0x4a, (byte) 0xab,
            (byte) 0x36, (byte) 0x89, (byte) 0x71, (byte) 0x5a, (byte) 0x48, (byte) 0x6b, (byte) 0x8d, (byte) 0x43,
            (byte) 0xb0, (byte) 0x3b, (byte) 0x13, (byte) 0x13, (byte) 0x73, (byte) 0x3c, (byte) 0x2f, (byte) 0x44,
            (byte) 0x63, (byte) 0x31, (byte) 0x79, (byte) 0x62, (byte) 0x17, (byte) 0xdf, (byte) 0xe6, (byte) 0xde,
            (byte) 0xf3, (byte) 0xf1, (byte) 0xbb, (byte) 0xe7, (byte) 0xdc, (byte) 0xe3, (byte) 0x08, (byte) 0x82,
            (byte) 0x54, (byte) 0x27, (byte) 0x82, (byte) 0x05, (byte) 0x2c, (byte) 0x35, (byte) 0xd5, (byte) 0xc0,
            (byte) 0xcb, (byte) 0x8a, (byte) 0x8a, (byte) 0x5d, (byte) 0x15, (byte) 0xd9, (byte) 0xb5, (byte) 0x27,
            (byte) 0x13, (byte) 0xfd, (byte) 0x1c, (byte) 0xa0, (byte) 0xbe, (byte) 0x0a, (byte) 0x80, (byte) 0x01,
            (byte) 0x7c, (byte) 0x6a, (byte) 0x4c, (byte) 0x4b, (byte) 0x3e, (byte) 0xf1, (byte) 0xd4, (byte) 0xe0,
            (byte) 0xfe, (byte) 0x21, (byte) 0x9c, (byte) 0x44, (byte) 0xc3, (byte) 0x7f, (byte) 0xba, (byte) 0x05,
            (byte) 0x04, (byte) 0x4e, (byte) 0xf6, (byte) 0x4a, (byte) 0x7f, (byte) 0x47, (byte) 0xed, (byte) 0x62,
            (byte) 0xc6, (byte) 0x23, (byte) 0x5d, (byte) 0x46, (byte) 0xac, (byte) 0x76, (byte) 0x5b, (byte) 0x59,
            (byte) 0xe4, (byte) 0x70, (byte) 0x43, (byte) 0x8b, (byte) 0xfa, (byte) 0xdd, (byte) 0x6a, (byte) 0xcb,
            (byte) 0x23, (byte) 0xfd, (byte) 0x72, (byte) 0x3d, (byte) 0x7b, (byte) 0x7e, (byte) 0x58, (byte) 0xc6,
            (byte) 0xe0, (byte) 0x8e, (byte) 0x86, (byte) 0x67, (byte) 0x84, (byte) 0xb3, (byte) 0x5b, (byte) 0x7c,
            (byte) 0x96, (byte) 0xa6, (byte) 0xd6, (byte) 0x42, (byte) 0x3c, (byte) 0x4f, (byte) 0x38, (byte) 0x6d,
            (byte) 0xed, (byte) 0x4d, (byte) 0xf0, (byte) 0xb5, (byte) 0xab, (byte) 0x99, (byte) 0xa1, (byte) 0xc3,
            (byte) 0x26, (byte) 0x86, (byte) 0x03, (byte) 0xde, (byte) 0x27, (byte) 0x78, (byte) 0xb6, (byte) 0x1d,
            (byte) 0xe7, (byte) 0x72, (byte) 0x7f, (byte) 0x55, (byte) 0x09, (byte) 0xf5, (byte) 0xb7, (byte) 0xf2,
            (byte) 0xe9, (byte) 0xce, (byte) 0x0f, (byte) 0xd0, (byte) 0xdc, (byte) 0xfe, (byte) 0x16, (byte) 0x4a,
            (byte) 0xa9, (byte) 0x36, (byte) 0x87, (byte) 0xd0, (byte) 0x88, (byte) 0x0a, (byte) 0x22, (byte) 0x46,
            (byte) 0xf5, (byte) 0xf8, (byte) 0x6d, (byte) 0x46, (byte) 0xbd, (byte) 0xd8, (byte) 0x5b, (byte) 0x31,
            (byte) 0x87, (byte) 0x8f, (byte) 0x7d, (byte) 0x5a, (byte) 0x2c, (byte) 0x9e, (byte) 0x4b, (byte) 0xee,
            (byte) 0xe9, (byte) 0xb6, (byte) 0x19, (byte) 0x4f, (byte) 0xfd, (byte) 0x66, (byte) 0x3c, (byte) 0xd9,
            (byte) 0x52, (byte) 0x63, (byte) 0x01, (byte) 0xaf, (byte) 0x12, (byte) 0x3a, (byte) 0xf8, (byte) 0x2e,
            (byte) 0x5f, (byte) 0x7a, (byte) 0xe6, (byte) 0x25, (byte) 0x5c, (byte) 0x1a, (byte) 0x15, (byte) 0xce,
            (byte) 0xbf, (byte) 0xb4, (byte) 0xb0, (byte) 0x17, (byte) 0x89, (byte) 0x5e, (byte) 0xbb, (byte) 0x06,
            (byte) 0xdb, (byte) 0x04, (byte) 0xfc, (byte) 0xe1, (byte) 0x52, (byte) 0x31, (byte) 0xb8, (byte) 0xab,
            (byte) 0xe1, (byte) 0xad, (byte) 0xab, (byte) 0x7b, (byte) 0x7e, (byte) 0x55, (byte) 0x77, (byte) 0xf1,
            (byte) 0xd8, (byte) 0xa7, (byte) 0x60, (byte) 0xc0, (byte) 0x6b, (byte) 0xc2, (byte) 0x23, (byte) 0x8f,
            (byte) 0x8c, (byte) 0x7f, (byte) 0x7e, (byte) 0x4a, (byte) 0x38, (byte) 0x37, (byte) 0x24, (byte) 0x54,
            (byte) 0xf6, (byte) 0x7d, (byte) 0x64, (byte) 0x77, (byte) 0xfb, (byte) 0xb0, (byte) 0xdb, (byte) 0x9a,
            (byte) 0x80, (byte) 0xde, (byte) 0xa0, (byte) 0x24, (byte) 0x5d, (byte) 0xdd, (byte) 0xdb, (byte) 0x52,
            (byte) 0xee, (byte) 0xb9, (byte) 0xb3, (byte) 0x83, (byte) 0xfc, (byte) 0x4d, (byte) 0x8a, (byte) 0x39,
            (byte) 0x70, (byte) 0x62, (byte) 0xc0, (byte) 0xfe, (byte) 0x0e, (byte) 0xae, (byte) 0x36, (byte) 0xe7,
            (byte) 0x4c, (byte) 0xc9, (byte) 0x4c, (byte) 0x9f, (byte) 0xe9, (byte) 0x1f, (byte) 0xd7, (byte) 0x3d,
            (byte) 0xce, (byte) 0xab, (byte) 0x0c, (byte) 0xe6, (byte) 0x5e, (byte) 0xa2, (byte) 0xcf, (byte) 0xdd,
            (byte) 0x3d, (byte) 0x2e, (byte) 0x25, (byte) 0x33, (byte) 0xbd, (byte) 0xf2, (byte) 0xad, (byte) 0x7b,
            (byte) 0xb8, (byte) 0x29, (byte) 0x46, (byte) 0x67, (byte) 0x3e, (byte) 0xb3, (byte) 0x4c, (byte) 0x03,
            (byte) 0xbf, (byte) 0x86, (byte) 0x88, (byte) 0xac, (byte) 0xcf, (byte) 0xca, (byte) 0xc9, (byte) 0x4c,
            (byte) 0x8f, (byte) 0x74, (byte) 0xe3, (byte) 0xe2, (byte) 0xef, (byte) 0x0d, (byte) 0x21, (byte) 0xba,
            (byte) 0x30, (byte) 0xc2, (byte) 0x5e, (byte) 0x19, (byte) 0xf8, (byte) 0xb6, (byte) 0x8e, (byte) 0xf7,
            (byte) 0x10, (byte) 0xd9, (byte) 0x5d, (byte) 0x8e, (byte) 0x67, (byte) 0x86, (byte) 0x7d, (byte) 0x4b,
            (byte) 0x22, (byte) 0x3a, (byte) 0x7b, (byte) 0x18, (byte) 0x4e, (byte) 0xf7, (byte) 0xf1, (byte) 0xd0,
            (byte) 0xce, (byte) 0xb2, (byte) 0x80, (byte) 0xbf, (byte) 0xd6, (byte) 0x79, (byte) 0x74, (byte) 0x6d,
            (byte) 0x8a, (byte) 0xbd, (byte) 0x36, (byte) 0xf0, (byte) 0x27, (byte) 0x7b, (byte) 0xac, (byte) 0x0c,
            (byte) 0xd7, (byte) 0xa6, (byte) 0xc4, (byte) 0xc8, (byte) 0x58, (byte) 0x27, (byte) 0xef, (byte) 0xa5,
            (byte) 0x79, (byte) 0xe6, (byte) 0xd1, (byte) 0x59, (byte) 0x21, (byte) 0xe1, (byte) 0xa2, (byte) 0x7f,
            (byte) 0xa7, (byte) 0x5d, (byte) 0x06, (byte) 0x5c, (byte) 0x74, (byte) 0xb0, (byte) 0x5a, (byte) 0xca,
            (byte) 0x48, (byte) 0xe5, (byte) 0xca, (byte) 0xc4, (byte) 0x18, (byte) 0xd1, (byte) 0x4a, (byte) 0x64,
            (byte) 0xa5, (byte) 0xb8, (byte) 0x7f, (byte) 0x49, (byte) 0x0c, (byte) 0x11, (byte) 0xd6, (byte) 0x34,
            (byte) 0xd9, (byte) 0x52, (byte) 0x62, (byte) 0x0f, (byte) 0xb9, (byte) 0xc1, (byte) 0xfb, (byte) 0xf4,
            (byte) 0x5d, (byte) 0xcd, (byte) 0x4d, (byte) 0xec, (byte) 0xcc, (byte) 0xcd, (byte) 0xd9, (byte) 0x35,
            (byte) 0xd0, (byte) 0x9c, (byte) 0x26, (byte) 0xe5, (byte) 0x21, (byte) 0x04, (byte) 0x00, (byte) 0x00, });
    /** The image. */
    private static final Image STAR_ROLLOVER_IMAGE = compressedByteArrayToImage(new byte[] { (byte) 0x1f, (byte) 0x8b,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x5b, (byte) 0xf3, (byte) 0x96, (byte) 0x81, (byte) 0xb5, (byte) 0x9c, (byte) 0x85, (byte) 0x41,
            (byte) 0x80, (byte) 0x41, (byte) 0xa0, (byte) 0xb4, (byte) 0x88, (byte) 0x81, (byte) 0x29, (byte) 0xda,
            (byte) 0xd3, (byte) 0x77, (byte) 0x57, (byte) 0x82, (byte) 0x5a, (byte) 0xd9, (byte) 0xab, (byte) 0x4d,
            (byte) 0x4b, (byte) 0x99, (byte) 0x18, (byte) 0x18, (byte) 0x2a, (byte) 0x0a, (byte) 0x18, (byte) 0x18,
            (byte) 0x18, (byte) 0x19, (byte) 0x18, (byte) 0xfe, (byte) 0xb3, (byte) 0xb3, (byte) 0xe2, (byte) 0xc2,
            (byte) 0x32, (byte) 0x40, (byte) 0x2c, (byte) 0x80, (byte) 0x47, (byte) 0x9e, (byte) 0x10, (byte) 0x8e,
            (byte) 0x04, (byte) 0x62, (byte) 0x5b, (byte) 0x32, (byte) 0xf5, (byte) 0xca, (byte) 0x03, (byte) 0x71,
            (byte) 0x03, (byte) 0x10, (byte) 0x97, (byte) 0x02, (byte) 0x31, (byte) 0x37, (byte) 0x89, (byte) 0x7a,
            (byte) 0x19, (byte) 0x81, (byte) 0x38, (byte) 0x19, (byte) 0x88, (byte) 0x73, (byte) 0x81, (byte) 0xb8,
            (byte) 0x12, (byte) 0x88, (byte) 0xdd, (byte) 0x48, (byte) 0xd4, (byte) 0xaf, (byte) 0x0f, (byte) 0xb5,
            (byte) 0x5b, (byte) 0x0e, (byte) 0x88, (byte) 0xad, (byte) 0xa0, (byte) 0x6c, (byte) 0x71, (byte) 0x1c,
            (byte) 0x6a, (byte) 0x39, (byte) 0x80, (byte) 0x58, (byte) 0x02, (byte) 0xaa, (byte) 0xc7, (byte) 0x03,
            (byte) 0x88, (byte) 0x13, (byte) 0xa0, (byte) 0xea, (byte) 0xa3, (byte) 0xa0, (byte) 0xf2, (byte) 0x6c,
            (byte) 0x40, (byte) 0x5c, (byte) 0x08, (byte) 0xc4, (byte) 0xd5, (byte) 0x50, (byte) 0x31, (byte) 0x47,
            (byte) 0x20, (byte) 0xd6, (byte) 0x04, (byte) 0x62, (byte) 0x51, (byte) 0xa8, (byte) 0x9c, (byte) 0x01,
            (byte) 0x10, (byte) 0xd7, (byte) 0x43, (byte) 0xf5, (byte) 0xe4, (byte) 0x43, (byte) 0xc3, (byte) 0x0c,
            (byte) 0xa4, (byte) 0x46, (byte) 0x10, (byte) 0x2d, (byte) 0x1e, (byte) 0xdc, (byte) 0x81, (byte) 0x38,
            (byte) 0x0e, (byte) 0x1a, (byte) 0x1e, (byte) 0x20, (byte) 0xb5, (byte) 0x65, (byte) 0x50, (byte) 0x71,
            (byte) 0x90, (byte) 0x5f, (byte) 0x5d, (byte) 0xa1, (byte) 0x62, (byte) 0x66, (byte) 0x44, (byte) 0xf8,
            (byte) 0xcd, (byte) 0x1f, (byte) 0xaa, (byte) 0x56, (byte) 0x07, (byte) 0x4d, (byte) 0xdc, (byte) 0x1b,
            (byte) 0x87, (byte) 0x38, (byte) 0x72, (byte) 0x98, (byte) 0x3a, (byte) 0x42, (byte) 0xd5, (byte) 0x18,
            (byte) 0xe2, (byte) 0x90, (byte) 0x0f, (byte) 0x02, (byte) 0xe2, (byte) 0x5a, (byte) 0x20, (byte) 0x16,
            (byte) 0xc1, (byte) 0x22, (byte) 0xaf, (byte) 0x05, (byte) 0xd5, (byte) 0x6b, (byte) 0x81, (byte) 0xc7,
            (byte) 0x6d, (byte) 0x9a, (byte) 0xd0, (byte) 0xb0, (byte) 0x10, (byte) 0xc5, (byte) 0x22, (byte) 0x67,
            (byte) 0x00, (byte) 0x35, (byte) 0x1b, (byte) 0x5f, (byte) 0x5a, (byte) 0xb0, (byte) 0x06, (byte) 0xe2,
            (byte) 0x2a, (byte) 0xa4, (byte) 0x70, (byte) 0x37, (byte) 0x83, (byte) 0xfa, (byte) 0x87, (byte) 0x11,
            (byte) 0x1a, (byte) 0x7f, (byte) 0x35, (byte) 0xd0, (byte) 0x38, (byte) 0xc5, (byte) 0xa5, (byte) 0xdf,
            (byte) 0x13, (byte) 0x88, (byte) 0x4b, (byte) 0x80, (byte) 0xd8, (byte) 0x0e, (byte) 0x88, (byte) 0x8b,
            (byte) 0xa0, (byte) 0xee, (byte) 0x05, (byte) 0xe1, (byte) 0x8c, (byte) 0xff, (byte) 0x90, (byte) 0x74,
            (byte) 0x0c, (byte) 0xd2, (byte) 0xaf, (byte) 0x8d, (byte) 0x47, (byte) 0x7f, (byte) 0x04, (byte) 0x54,
            (byte) 0x3d, (byte) 0xc8, (byte) 0x9d, (byte) 0x7e, (byte) 0x40, (byte) 0x2c, (byte) 0x06, (byte) 0xc4,
            (byte) 0x4a, (byte) 0x40, (byte) 0x1c, (byte) 0x83, (byte) 0x64, (byte) 0x96, (byte) 0x15, (byte) 0x0e,
            (byte) 0xbd, (byte) 0xcc, (byte) 0x40, (byte) 0x1c, (byte) 0x0e, (byte) 0x8d, (byte) 0x67, (byte) 0x21,
            (byte) 0x2c, (byte) 0xf2, (byte) 0xb2, (byte) 0x40, (byte) 0x1c, (byte) 0x0a, (byte) 0xc4, (byte) 0x4e,
            (byte) 0x78, (byte) 0xf4, (byte) 0x4b, (byte) 0x40, (byte) 0xfd, (byte) 0x8a, (byte) 0xcb, (byte) 0x7d,
            (byte) 0x1c, (byte) 0x50, (byte) 0x37, (byte) 0x21, (byte) 0xab, (byte) 0x01, (byte) 0x00, (byte) 0x36,
            (byte) 0x64, (byte) 0xcf, (byte) 0x53, (byte) 0x21, (byte) 0x04, (byte) 0x00, (byte) 0x00, });
    /** The image. */
    private static final Image STAR_SELECTION_IMAGE = compressedByteArrayToImage(new byte[] {
    
    (byte) 0x1f, (byte) 0x8b, (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x9d, (byte) 0x93, (byte) 0xdb, (byte) 0x6b, (byte) 0x13, (byte) 0x41,
            (byte) 0x14, (byte) 0xc6, (byte) 0x27, (byte) 0x9b, (byte) 0x39, (byte) 0xb3, (byte) 0xb3, (byte) 0xbb,
            (byte) 0x69, (byte) 0x13, (byte) 0xc5, (byte) 0x4b, (byte) 0xbc, (byte) 0xa1, (byte) 0xf5, (byte) 0x52,
            (byte) 0x85, (byte) 0x2a, (byte) 0x16, (byte) 0x7c, (byte) 0x28, (byte) 0xf1, (byte) 0x56, (byte) 0xa9,
            (byte) 0x88, (byte) 0x69, (byte) 0x89, (byte) 0xb1, (byte) 0x36, (byte) 0x1a, (byte) 0x23, (byte) 0x4d,
            (byte) 0x4e, (byte) 0x36, (byte) 0x69, (byte) 0x82, (byte) 0x22, (byte) 0x08, (byte) 0x22, (byte) 0x78,
            (byte) 0xf9, (byte) 0x87, (byte) 0x7c, (byte) 0xf3, (byte) 0xc9, (byte) 0x47, (byte) 0xff, (byte) 0x1a,
            (byte) 0x29, (byte) 0x49, (byte) 0xda, (byte) 0xdd, (byte) 0x35, (byte) 0x17, (byte) 0x13, (byte) 0x8b,
            (byte) 0x56, (byte) 0xda, (byte) 0x5d, (byte) 0xcf, (byte) 0x0e, (byte) 0x3e, (byte) 0x58, (byte) 0x9a,
            (byte) 0x54, (byte) 0xf4, (byte) 0xe1, (byte) 0x7b, (byte) 0x39, (byte) 0xcc, (byte) 0xef, (byte) 0xcc,
            (byte) 0x37, (byte) 0xe7, (byte) 0x3b, (byte) 0xf3, (byte) 0xa1, (byte) 0xcd, (byte) 0xe0, (byte) 0x2d,
            (byte) 0x67, (byte) 0x09, (byte) 0x96, (byte) 0x78, (byte) 0xfd, (byte) 0x8a, (byte) 0x69, (byte) 0xcb,
            (byte) 0xf3, (byte) 0xf7, (byte) 0x3e, (byte) 0xd9, (byte) 0x93, (byte) 0x6f, (byte) 0xdc, (byte) 0x8f,
            (byte) 0xef, (byte) 0x35, (byte) 0xc6, (byte) 0xde, (byte) 0xbd, (byte) 0x64, (byte) 0x2c, (byte) 0xc2,
            (byte) 0x58, (byte) 0xa0, (byte) 0xc3, (byte) 0x28, (byte) 0x99, (byte) 0x81, (byte) 0x00, (byte) 0x41,
            (byte) 0xda, (byte) 0xeb, (byte) 0xcc, (byte) 0x5e, (byte) 0x3a, (byte) 0x45, (byte) 0xec, (byte) 0xc1,
            (byte) 0xff, (byte) 0xe4, (byte) 0x8d, (byte) 0x40, (byte) 0x83, (byte) 0xb9, (byte) 0x1f, (byte) 0x97,
            (byte) 0xe5, (byte) 0xcc, (byte) 0xcf, (byte) 0x73, (byte) 0x52, (byte) 0x0b, (byte) 0xa2, (byte) 0xff,
            (byte) 0xc8, (byte) 0x73, (byte) 0x98, (byte) 0xf4, (byte) 0xe3, (byte) 0x50, (byte) 0xec, (byte) 0x94,
            (byte) 0x63, (byte) 0xf9, (byte) 0x5e, (byte) 0xce, (byte) 0x4a, (byte) 0xaa, (byte) 0xda, (byte) 0xdf,
            (byte) 0x7c, (byte) 0x88, (byte) 0xdf, (byte) 0xe2, (byte) 0x30, (byte) 0x16, (byte) 0x30, (byte) 0x48,
            (byte) 0x6f, (byte) 0x5c, (byte) 0x35, (byte) 0xd0, (byte) 0x7b, (byte) 0x1e, (byte) 0x43, (byte) 0xef,
            (byte) 0x99, (byte) 0x75, (byte) 0xe3, (byte) 0xfb, (byte) 0x94, (byte) 0xce, (byte) 0xa9, (byte) 0xc6,
            (byte) 0x02, (byte) 0xd8, (byte) 0xd5, (byte) 0x27, (byte) 0x4a, (byte) 0x92, (byte) 0x54, (byte) 0x8f,
            (byte) 0x93, (byte) 0x8e, (byte) 0x04, (byte) 0x06, (byte) 0x9c, (byte) 0xdd, (byte) 0x1e, (byte) 0x87,
            (byte) 0xd4, (byte) 0xe6, (byte) 0x79, (byte) 0xbd, (byte) 0xd8, (byte) 0xb6, (byte) 0x2d, (byte) 0xf4,
            (byte) 0xea, (byte) 0x16, (byte) 0xba, (byte) 0x75, (byte) 0xb3, (byte) 0xd0, (byte) 0x2e, (byte) 0x18,
            (byte) 0x57, (byte) 0x36, (byte) 0x4f, (byte) 0x8a, (byte) 0x09, (byte) 0xdf, (byte) 0x82, (byte) 0xc3,
            (byte) 0x81, (byte) 0xa4, (byte) 0xfe, (byte) 0x1c, (byte) 0x74, (byte) 0xea, (byte) 0xa3, (byte) 0x11,
            (byte) 0xbb, (byte) 0x8f, (byte) 0xb8, (byte) 0x99, (byte) 0xad, (byte) 0xa4, (byte) 0xc8, (byte) 0x0e,
            (byte) 0xd2, (byte) 0x56, (byte) 0xbe, (byte) 0xf7, (byte) 0xc8, (byte) 0x2a, (byte) 0x75, (byte) 0x30,
            (byte) 0x86, (byte) 0x5f, (byte) 0xea, (byte) 0x74, (byte) 0x6f, (byte) 0x8d, (byte) 0xf8, (byte) 0xaa,
            (byte) 0x89, (byte) 0x6e, (byte) 0x45, (byte) 0xa2, (byte) 0xb3, (byte) 0x62, (byte) 0xe0, (byte) 0xba,
            (byte) 0xad, (byte) 0x17, (byte) 0x9d, (byte) 0x82, (byte) 0xfe, (byte) 0xd0, (byte) 0xcb, (byte) 0x8a,
            (byte) 0x4c, (byte) 0xf7, (byte) 0x3a, (byte) 0x4c, (byte) 0x6f, (byte) 0x25, (byte) 0x54, (byte) 0x3e,
            (byte) 0xa1, (byte) 0xa7, (byte) 0x63, (byte) 0x7e, (byte) 0x0c, (byte) 0x16, (byte) 0x07, (byte) 0x0b,
            (byte) 0x26, (byte) 0x7a, (byte) 0x2f, (byte) 0x42, (byte) 0xd6, (byte) 0x52, (byte) 0xac, (byte) 0x1b,
            (byte) 0xb2, (byte) 0x55, (byte) 0x03, (byte) 0x1d, (byte) 0x5b, (byte) 0x2a, (byte) 0xad, (byte) 0x95,
            (byte) 0x05, (byte) 0xb6, (byte) 0x6c, (byte) 0x81, (byte) 0x8d, (byte) 0x3a, (byte) 0x4f, (byte) 0xf7,
            (byte) 0x52, (byte) 0x3c, (byte) 0xe1, (byte) 0xcb, (byte) 0x3f, (byte) 0xde, (byte) 0x42, (byte) 0x3d,
            (byte) 0xc8, (byte) 0xd7, (byte) 0xfd, (byte) 0x7e, (byte) 0xda, (byte) 0x40, (byte) 0xf7, (byte) 0xa9,
            (byte) 0xa9, (byte) 0x58, (byte) 0xa7, (byte) 0x42, (byte) 0x2c, (byte) 0xdd, (byte) 0xbd, (byte) 0x5e,
            (byte) 0xd6, (byte) 0x95, (byte) 0x5a, (byte) 0x08, (byte) 0xd8, (byte) 0xa8, (byte) 0x40, (byte) 0xc8,
            (byte) 0xc6, (byte) 0xc9, (byte) 0xff, (byte) 0xee, (byte) 0x79, (byte) 0x02, (byte) 0x9c, (byte) 0xf0,
            (byte) 0x0d, (byte) 0x58, (byte) 0xea, (byte) 0x66, (byte) 0x88, (byte) 0xa9, (byte) 0x85, (byte) 0x7e,
            (byte) 0x65, (byte) 0xe8, (byte) 0x19, (byte) 0xd7, (byte) 0x50, (byte) 0x28, (byte) 0x35, (byte) 0x6d,
            (byte) 0x9e, (byte) 0xe9, (byte) 0xcc, (byte) 0xf2, (byte) 0xfd, (byte) 0x8a, (byte) 0x85, (byte) 0xa1,
            (byte) 0x19, (byte) 0x58, (byte) 0x94, (byte) 0xf9, (byte) 0xc2, (byte) 0xd7, (byte) 0x9b, (byte) 0xc4,
            (byte) 0xd4, (byte) 0xc9, (byte) 0x2f, (byte) 0xea, (byte) 0xca, (byte) 0x73, (byte) 0xb3, (byte) 0xc4,
            (byte) 0xb1, (byte) 0x55, (byte) 0x02, (byte) 0x5c, (byte) 0xad, (byte) 0xf2, (byte) 0x5b, (byte) 0x83,
            (byte) 0x8b, (byte) 0x9c, (byte) 0xa9, (byte) 0x1c, (byte) 0x86, (byte) 0xe7, (byte) 0x77, (byte) 0x80,
            (byte) 0xde, (byte) 0x90, (byte) 0x6b, (byte) 0x67, (byte) 0xc8, (byte) 0xeb, (byte) 0x8a, (byte) 0x8e,
            (byte) 0xcd, (byte) 0x0a, (byte) 0x28, (byte) 0x35, (byte) 0x6c, (byte) 0x8e, (byte) 0x0d, (byte) 0x8c,
            (byte) 0xe2, (byte) 0xaa, (byte) 0xad, (byte) 0xcd, (byte) 0x77, (byte) 0x53, (byte) 0x5c, (byte) 0x8e,
            (byte) 0xdc, (byte) 0x25, (byte) 0x41, (byte) 0x73, (byte) 0xb4, (byte) 0x60, (byte) 0xd9, (byte) 0x79,
            (byte) 0x4c, (byte) 0x77, (byte) 0xd6, (byte) 0x20, (byte) 0xef, (byte) 0x66, (byte) 0xf9, (byte) 0xec,
            (byte) 0xb7, (byte) 0x29, (byte) 0xb8, (byte) 0xdd, (byte) 0x9f, (byte) 0xe6, (byte) 0x4f, (byte) 0x5a,
            (byte) 0x4b, (byte) 0x51, (byte) 0xfc, (byte) 0x5c, (byte) 0xd5, (byte) 0x1e, (byte) 0x38, (byte) 0x77,
            (byte) 0xb5, (byte) 0xf1, (byte) 0x91, (byte) 0xbb, (byte) 0x24, (byte) 0xe0, (byte) 0x0c, (byte) 0x65,
            (byte) 0x9f, (byte) 0x6b, (byte) 0xdf, (byte) 0x81, (byte) 0x6b, (byte) 0x1b, (byte) 0x17, (byte) 0xe0,
            (byte) 0x38, (byte) 0xcd, (byte) 0x02, (byte) 0x82, (byte) 0x88, (byte) 0xda, (byte) 0xe3, (byte) 0xd3,
            (byte) 0xdb, (byte) 0x63, (byte) 0x30, (byte) 0xd7, (byte) 0xbf, (byte) 0xc4, (byte) 0x17, (byte) 0xbd,
            (byte) 0x59, (byte) 0xed, (byte) 0x90, (byte) 0x6f, (byte) 0x0e, (byte) 0xe5, (byte) 0x23, (byte) 0xea,
            (byte) 0xbf, (byte) 0x48, (byte) 0x38, (byte) 0x4a, (byte) 0xe2, (byte) 0xc4, (byte) 0xec, (byte) 0x3c,
            (byte) 0x03, (byte) 0x10, (byte) 0xfa, (byte) 0x9e, (byte) 0xa0, (byte) 0x9e, (byte) 0xc9, (byte) 0x40,
            (byte) 0x0e, (byte) 0xf5, (byte) 0x1f, (byte) 0x51, (byte) 0x7f, (byte) 0x46, (byte) 0x1f, (byte) 0x39,
            (byte) 0xdb, (byte) 0x50, (byte) 0x9a, (byte) 0xea, (byte) 0xb3, (byte) 0xb3, (byte) 0xf6, (byte) 0x0b,
            (byte) 0xee, (byte) 0x36, (byte) 0x1e, (byte) 0xfc, (byte) 0x21, (byte) 0x04, (byte) 0x00, (byte) 0x00, });
    
    @Override
    public void update(Movie movie) {
        if (movie != null) {
            this.setRating((float) movie.getMovieRating());
        } else {
            this.setRating(0);
        }
        
    }
}

package amo.randomFilm.gui.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import amo.randomFilm.RandomFilm;

import sun.awt.shell.ShellFolder;

public class ListItem extends JPanel implements MouseListener {
    
    private static final String IMAGE_DELETE     = "images\\Delete.png";
    private static final String IMAGE_FILMSTARTS = "images\\Filmstarts.png";
    
    /**
     * default Serial version id
     */
    private static final long   serialVersionUID = 1L;
    
    private final File          file;
    private Image               myImage          = null;
    private String              filmName         = "";
    private String              extension        = "";
    private String              execType         = "";
    
    private static int          imageHeight;
    private static int          imageWidth;
    private static int          componentWidth;
    
    private boolean             isSelected       = false;
    
    private boolean             hasIcon          = false;
    
    // private DropPanel parent;
    
    // TODO: Show Big name & Small Path !!!
    public ListItem(File f, String filmName, String extension, int width,
            int height, DropPanel parent) {
        super();
        
        this.filmName = filmName;
        this.extension = extension;
        
        componentWidth = width - 2;
        imageHeight = height - 4;
        imageWidth = (int) (imageHeight * 1.5);
        
        // this.parent = parent;
        
        file = f;
        // if ( MainFrame.showPreviewImages() ) {
        // hasIcon = loadIcon();
        // }
        
        // FileSystemView view = FileSystemView.getFileSystemView();
        // Icon icon = view.getSystemIcon(file);
        
        ShellFolder shellFolder;
        try {
            shellFolder = ShellFolder.getShellFolder(file);
            execType = shellFolder.getExecutableType();
            Icon icon = new ImageIcon(shellFolder.getIcon(true));
            myImage = iconToImage(icon);
            hasIcon = true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        addMouseListener(this);
        
        setLayout(null);
        
        JButton btnFilmstarts = new JButton(new ImageIcon(IMAGE_FILMSTARTS));
        btnFilmstarts.setActionCommand("ask filmstarts");
        btnFilmstarts.addActionListener(parent);
        btnFilmstarts.setBounds(componentWidth - 60, ((imageHeight - 26) / 2), 26,
                26);
        add(btnFilmstarts);
        
        JButton btnDelete = new JButton(new ImageIcon(IMAGE_DELETE));
        btnDelete.setActionCommand("löschen");
        btnDelete.addActionListener(parent);
        btnDelete.setBounds(componentWidth - 30, ((imageHeight - 26) / 2), 26,
                26);
        add(btnDelete);
        
        setBackground(Color.white);
        setVisible(true);
        
    }
    
    static Image iconToImage(Icon icon) {
        if (icon instanceof ImageIcon) {
            return ((ImageIcon) icon).getImage();
        } else {
            int w = icon.getIconWidth();
            int h = icon.getIconHeight();
            GraphicsEnvironment ge = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g = image.createGraphics();
            icon.paintIcon(null, g, 0, 0);
            g.dispose();
            return image;
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        if (isSelected) {
            g.setColor(new Color(200, 200, 255));
            g.fillRect(1, 1, getWidth() - 2, getHeight() - 3);
            
            g.setColor(Color.BLACK);
        }
        
        // g.drawString( file.getAbsolutePath(), imageWidth + 4, (int)
        // (imageHeight * 0.6) );
        // if (hasIcon) g.drawImage( image.getImage() , 0, 1, imageWidth,
        // imageHeight, 0, 1, imageWidth, imageHeight, image.getImageObserver()
        // );
        // g.drawLine(0, getHeight()-1, componentWidth-1, getHeight()-1);
        
        // small filepath
        g.drawString(file.getAbsolutePath(), imageWidth + 24, imageHeight - 2);
        
        // Big Film Name
        g.setFont(new Font("Sans-Serif", Font.BOLD, 18));
        g.drawString(this.filmName, imageWidth + 4, 20);
        
        // draw icon
        if (hasIcon) g.drawImage(myImage,
                    10, 10, imageWidth, imageHeight,
                    0, 0, imageWidth, imageHeight,
                    null);
        // g.drawImage( myIcon, 0, 1, imageWidth, imageHeight, 0, 1, imageWidth,
        // imageHeight, myIcon.get);
        
        // draw bottom border line
        g.drawLine(0, getHeight() - 1, componentWidth - 1, getHeight() - 1);
        
        paintComponents(g);
        
    }
    
    // private boolean loadIcon() {
    // try {
    // image = new ImageIcon( file.getAbsolutePath() );
    // image.setImage( (image.getImage()).getScaledInstance(-1, imageHeight,
    // Image.SCALE_FAST) );
    // } catch (Exception e) {
    // e.printStackTrace();
    // return false;
    // }
    //		
    // return true;
    // }
    //	
    public File getFile() {
        return file;
    }
    
    public String getFilmName() {
        return filmName;
    }
    
    public Image getIconImage() {
        return myImage;
    }
    
    public String getExecutableName() {
        return execType;
    }
    
    public void setSelected(boolean bool) {
        isSelected = bool;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void mouseReleased(MouseEvent arg0) {
    }
    
    public void mouseClicked(MouseEvent arg0) {
    }
    
    public void mouseEntered(MouseEvent arg0) {
    }
    
    public void mouseExited(MouseEvent arg0) {
    }
    
    public void mousePressed(MouseEvent arg0) {
        isSelected = !isSelected;
        if (RandomFilm.DEBUG) System.out.println(file.getName() + " has been Pressed on");
        repaint();
    }
    
}

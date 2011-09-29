package amo.randomFilm.gui.panels;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import amo.randomFilm.gui.MainFrame;

public class ButtonPanelDropper extends JComponent {
    
    public static final String  DISABLE_ALWAYS_ON_TOP = "Always On Top ausschalten";
    
    public static final String  ENABLE_ALWAYS_ON_TOP  = "Always On Top einschalten";
    
    private static final String IMAGES_NEXT           = "images/Next.png";
    
    /**
     * default Serial version id
     */
    private static final long   serialVersionUID      = 1L;
    
    JButton                     btnMarkAll;
    JButton                     btnMarkNone;
    
    JButton                     btnDelete;
    JButton                     btnDeleteAll;
    
    JButton                     btnStart;
    
    JButton                     btnExit;
    static JButton              btnAlwaysOnTop;
    
    public ButtonPanelDropper() {
        super();
        
    }
    
    public void init(MainFrame mainFrame, DropPanel dropper, int width, int height) {
        setLayout(null);
        
        int btnWidth = (int) (width * 0.45);
        int btnHeight = 38;
        int btnXLeft = 4;
        int btnXRight = width - (btnWidth + 20);
        int btnY = 6;
        
        btnMarkAll = new JButton("Alles markieren");
        btnMarkNone = new JButton("Nichts markieren");
        btnDelete = new JButton("Markiertes löschen");
        btnDeleteAll = new JButton("Liste löschen");
        btnStart = new JButton("Los geht's !", new ImageIcon(IMAGES_NEXT));
        btnAlwaysOnTop = new JButton(DISABLE_ALWAYS_ON_TOP);
        // btnStart = new PaintedButton("Los geht's !", new
        // ImageIcon(IMAGES_NEXT));
        
        btnStart.setHorizontalTextPosition(JButton.LEFT);
        
        btnMarkAll.addActionListener(dropper);
        btnMarkNone.addActionListener(dropper);
        btnDelete.addActionListener(dropper);
        btnDeleteAll.addActionListener(dropper);
        btnStart.addActionListener(dropper);
        btnAlwaysOnTop.addActionListener(dropper);
        
        btnMarkAll.setBounds(btnXLeft, btnY, btnWidth, btnHeight);
        btnY += btnHeight + 4;
        btnMarkNone.setBounds(btnXLeft, btnY, btnWidth, btnHeight);
        
        btnY = 6;
        
        btnDelete.setBounds(btnXRight, btnY, btnWidth, btnHeight);
        btnY += btnHeight + 4;
        btnDeleteAll.setBounds(btnXRight, btnY, btnWidth, btnHeight);
        
        btnY += btnHeight + 13;
        btnAlwaysOnTop.setBounds(btnXLeft, btnY, btnWidth, btnHeight);
        btnStart.setBounds(btnXRight, btnY, btnWidth, btnHeight);
        
        btnMarkAll.setBackground(new Color(150, 150, 225));
        btnMarkNone.setBackground(new Color(150, 150, 225));
        
        btnDelete.setBackground(new Color(225, 150, 150));
        btnDeleteAll.setBackground(new Color(225, 150, 150));
        
        btnAlwaysOnTop.setBackground(Color.ORANGE);
        btnStart.setBackground(Color.white);
        
        add(btnMarkAll);
        add(btnMarkNone);
        add(btnDelete);
        add(btnDeleteAll);
        add(btnAlwaysOnTop);
        add(btnStart);
        
        setVisible(true);
        
    }
    
    public void resizeComponent(int width, int height) {
        int btnWidth = (int) (width * 0.45);
        int btnHeight = 38;
        int btnX = 4;
        int btnY = 6;
        
        btnMarkAll.setBounds(btnX, btnY, btnWidth, btnHeight);
        btnY += btnHeight + 4;
        btnMarkNone.setBounds(btnX, btnY, btnWidth, btnHeight);
        
        btnX = width - (btnWidth + 20);
        btnY = 6;
        
        btnDelete.setBounds(btnX, btnY, btnWidth, btnHeight);
        btnY += btnHeight + 4;
        btnDeleteAll.setBounds(btnX, btnY, btnWidth, btnHeight);
        
        btnY += btnHeight + 13;
        btnStart.setBounds(btnX, btnY, btnWidth, btnHeight);
        
        btnMarkAll.repaint();
        btnMarkNone.repaint();
        btnDelete.repaint();
        btnDeleteAll.repaint();
        btnStart.repaint();
    }
    
    public static void setAlwaysOnTopEnabled(boolean isEnabled) {
        if (isEnabled) btnAlwaysOnTop.setText(DISABLE_ALWAYS_ON_TOP);
        else
            btnAlwaysOnTop.setText(ENABLE_ALWAYS_ON_TOP);
        
    }
    
}

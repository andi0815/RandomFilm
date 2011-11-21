package amo.randomFilm.gui.panels;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import amo.randomFilm.gui.GuiConstants;
import amo.randomFilm.gui.MainFrame;

public class ButtonPanelDropper extends JComponent {
    
    private static final String IMAGES_NEXT = "images/Next.png";
    
    /**
     * default Serial version id
     */
    private static final long serialVersionUID = 1L;
    
    JButton btnMarkAll;
    JButton btnMarkNone;
    
    JButton btnDelete;
    JButton btnDeleteAll;
    
    JButton btnStart;
    
    JButton btnExit;
    static JButton btnAlwaysOnTop;
    
    public ButtonPanelDropper() {
        super();
        
    }
    
    public void init(MainFrame mainFrame, int width, int height) {
        this.setLayout(null);
        
        int btnWidth = (int) (width * 0.45);
        int btnHeight = 38;
        int btnXLeft = 4;
        int btnXRight = width - (btnWidth + 20);
        int btnY = 6;
        
        this.btnMarkAll = new JButton(GuiConstants.LABEL_BTN_SELECT_ALL);
        this.btnMarkNone = new JButton(GuiConstants.LABEL_BTN_SELECT_NOTHING);
        this.btnDelete = new JButton(GuiConstants.LABEL_BTN_REMOVE_SELECTED);
        this.btnDeleteAll = new JButton(GuiConstants.LABEL_BTN_REMOVE_ALL);
        this.btnStart = new JButton(GuiConstants.LABEL_BTN_GO, new ImageIcon(IMAGES_NEXT));
        btnAlwaysOnTop = new JButton(GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP);
        // btnStart = new PaintedButton("Los geht's !", new
        // ImageIcon(IMAGES_NEXT));
        
        this.btnStart.setHorizontalTextPosition(JButton.LEFT);
        
        this.btnMarkAll.setBounds(btnXLeft, btnY, btnWidth, btnHeight);
        btnY += btnHeight + 4;
        this.btnMarkNone.setBounds(btnXLeft, btnY, btnWidth, btnHeight);
        
        btnY = 6;
        
        this.btnDelete.setBounds(btnXRight, btnY, btnWidth, btnHeight);
        btnY += btnHeight + 4;
        this.btnDeleteAll.setBounds(btnXRight, btnY, btnWidth, btnHeight);
        
        btnY += btnHeight + 13;
        btnAlwaysOnTop.setBounds(btnXLeft, btnY, btnWidth, btnHeight);
        this.btnStart.setBounds(btnXRight, btnY, btnWidth, btnHeight);
        
        this.btnMarkAll.setBackground(new Color(150, 150, 225));
        this.btnMarkNone.setBackground(new Color(150, 150, 225));
        
        this.btnDelete.setBackground(new Color(225, 150, 150));
        this.btnDeleteAll.setBackground(new Color(225, 150, 150));
        
        btnAlwaysOnTop.setBackground(Color.ORANGE);
        this.btnStart.setBackground(Color.white);
        
        this.add(this.btnMarkAll);
        this.add(this.btnMarkNone);
        this.add(this.btnDelete);
        this.add(this.btnDeleteAll);
        this.add(btnAlwaysOnTop);
        this.add(this.btnStart);
        
        this.setVisible(true);
        
    }
    
    public void resizeComponent(int width, int height) {
        int btnWidth = (int) (width * 0.45);
        int btnHeight = 38;
        int btnX = 4;
        int btnY = 6;
        
        this.btnMarkAll.setBounds(btnX, btnY, btnWidth, btnHeight);
        btnY += btnHeight + 4;
        this.btnMarkNone.setBounds(btnX, btnY, btnWidth, btnHeight);
        
        btnX = width - (btnWidth + 20);
        btnY = 6;
        
        this.btnDelete.setBounds(btnX, btnY, btnWidth, btnHeight);
        btnY += btnHeight + 4;
        this.btnDeleteAll.setBounds(btnX, btnY, btnWidth, btnHeight);
        
        btnY += btnHeight + 13;
        this.btnStart.setBounds(btnX, btnY, btnWidth, btnHeight);
        
        this.btnMarkAll.repaint();
        this.btnMarkNone.repaint();
        this.btnDelete.repaint();
        this.btnDeleteAll.repaint();
        this.btnStart.repaint();
    }
    
    public static void setAlwaysOnTopEnabled(boolean isEnabled) {
        if (isEnabled)
            btnAlwaysOnTop.setText(GuiConstants.LABEL_BTN_DISABLE_ALWAYS_ON_TOP);
        else
            btnAlwaysOnTop.setText(GuiConstants.LABEL_BTN_ENABLE_ALWAYS_ON_TOP);
        
    }
    
    public void addActionListener(ActionListener listener) {
        this.btnMarkAll.addActionListener(listener);
        this.btnMarkNone.addActionListener(listener);
        this.btnDelete.addActionListener(listener);
        this.btnDeleteAll.addActionListener(listener);
        this.btnStart.addActionListener(listener);
        btnAlwaysOnTop.addActionListener(listener);
        
    }
    
    public void removeActionListener(ActionListener listener) {
        this.btnMarkAll.removeActionListener(listener);
        this.btnMarkNone.removeActionListener(listener);
        this.btnDelete.removeActionListener(listener);
        this.btnDeleteAll.removeActionListener(listener);
        this.btnStart.removeActionListener(listener);
        btnAlwaysOnTop.removeActionListener(listener);
    }
    
}

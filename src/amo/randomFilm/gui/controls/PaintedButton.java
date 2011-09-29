package amo.randomFilm.gui.controls;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author andi
 */
public class PaintedButton extends JButton {
    
    private final Color outterColor = Color.BLACK;
    private final Color innerColor  = Color.WHITE;
    
    /**
     * 
     */
    public PaintedButton() {
    }
    
    /**
     * @param arg0
     */
    public PaintedButton(Icon arg0) {
        super(arg0);
    }
    
    /**
     * @param arg0
     */
    public PaintedButton(String arg0) {
        super(arg0);
    }
    
    /**
     * @param arg0
     */
    public PaintedButton(Action arg0) {
        super(arg0);
    }
    
    /**
     * @param arg0
     * @param arg1
     */
    public PaintedButton(String arg0, Icon arg1) {
        super(arg0, arg1);
    }
    
    @Override
    public void paint(Graphics graphics) {
        // super.paint(arg0);
        int width = getWidth();
        int height = getHeight();
        
        int halfHeight = (height / 2);
        float colorStepR = (innerColor.getRed() - outterColor.getRed()) / halfHeight;
        float colorStepG = (innerColor.getGreen() - outterColor.getGreen()) / halfHeight;
        float colorStepB = (innerColor.getBlue() - outterColor.getBlue()) / halfHeight;
        
        int colorR = outterColor.getRed();
        int colorG = outterColor.getGreen();
        int colorB = outterColor.getBlue();
        
        System.out.println("width: " + width + " height: " + height + " halfHeight: " + halfHeight);
        System.out.println("RED: colorR: " + colorR + " InnerColor: " + innerColor.getRed() + " colorStepR:" + colorStepR);
        
        for (int line = 0; line < height; line++) {
            graphics.setColor(new Color(colorR, colorG, colorB));
            
            System.out.println("RED: " + colorR + " GREEN: " + colorG + " BLUE: " + colorB);
            
            graphics.drawLine(0, line, width, line);
            
            boolean increaseValues = (line <= halfHeight);
            colorR = nextStep(colorR, colorStepR, increaseValues);
            colorG = nextStep(colorG, colorStepG, increaseValues);
            colorB = nextStep(colorB, colorStepB, increaseValues);
        }
    }
    
    private int nextStep(int color, float stepping, boolean increaseValue) {
        // next step
        if (increaseValue) color += stepping;
        else
            color -= stepping;
        
        // check bounds
        if (color > 255) color = 255;
        if (color < 0) color = 0;
        
        return color;
    }
}

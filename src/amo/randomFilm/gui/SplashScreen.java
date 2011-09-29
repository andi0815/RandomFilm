package amo.randomFilm.gui;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;

public class SplashScreen extends JFrame implements Runnable
{
	
//	public static FileRename mainClass=null;
//	
//	public void setMainClass(FileRename main) {
//		mainClass = main;
//	}
//	
	/**
	 * default serial version id
	 */
	private static final long serialVersionUID = 1L;

	public void run()
	{
		setUndecorated( true );
		setSize(437, 487);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
		setVisible(true);
		
		try
		{
			Thread.sleep(3000);
		}
		catch (InterruptedException e)
		{
			dispose();
		}
		dispose();	

	}
	
	public void paint(Graphics g)
	{
		Image splashImage = getToolkit().getImage("images/logo-big.jpg");
		g.drawImage(splashImage, 0, 0, this);

	}
	
	
}
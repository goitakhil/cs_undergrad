
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Partial class for lab exercise using Random, Math and Color classes to draw
 * random color blobs.
 * 
 * @author Tyler Bevan
 */

@SuppressWarnings("serial")
public class ColorBlobs extends JPanel
{
	// The variables below  are instance variables, which are visible and usable from all the 
	// methods in this class. They represent the state of the object of this class.
	
	private final int TIMER_DELAY = 100; //milliseconds
	private Random rand;

	//Note: no other instance variables are necessary for this project

	/**
	 * Initialize the ColorBlobs class.
	 */
	public ColorBlobs()
	{
		// Note: no modifications to this method are necessary for this project
		rand = new Random();
		setBackground(Color.black);
		startAnimation();
		setPreferredSize(new Dimension(600, 600));
	}

	/**
	 * This method draws on the panel. It is called periodically by the
	 * animation thread.
	 */
	public void paintComponent(Graphics page)
	{
		int width = getWidth(); // width of the drawing panel
		int height = getHeight(); // height of the drawing panel
		
		// Set the oval's height and width to be a random integer between 25 and (width / 3) + 25
		int ovalWidth = rand.nextInt(width/3)+25;
		int ovalHeight = rand.nextInt(height/3)+25;
		// Set the oval's x and y origins to keep the oval from leaving the window, it can exist directly on the border.
		int ovalX = rand.nextInt(width-ovalWidth);
		int ovalY = rand.nextInt(height-ovalHeight);
		// Generate random color and draw the oval
		Color randomColor = new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
		page.setColor(randomColor);
		page.fillRect(ovalX, ovalY, ovalWidth, ovalHeight);
		
   		//Make the animation smoother (don't modify!)
		Toolkit.getDefaultToolkit().sync();
	}


	/**
	 * Create an animation thread that runs periodically. DO NOT MODIFY
	 */
	private void startAnimation()
	{
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				repaint(); // redraw the panel
			}
		};
		new Timer(TIMER_DELAY, taskPerformer).start();
	}

	/**
	 * The main method that starts up the application. DO NOT MODIFY.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Color Blobs");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new ColorBlobs());
		frame.pack();
		frame.setVisible(true);
	}
}

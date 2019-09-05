/* 
 * TrafficAnimation.java 
 * CS 121 Project 1: Traffic Animation
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.ImageIcon;

/**
 * Animates a car driving across the screen.
 * It also shows clouds and a person.
 * @author Tyler Bevan
 */
@SuppressWarnings("serial")
public class TrafficAnimation extends JPanel {
	//Note: This area is where you declare constants and variables that
	//	need to keep their values between calls	to paintComponent().
	//	Any other variables should be declared locally, in the
	//	method where they are used.

	//constant to regulate the frequency of Timer events
	// Note: 100ms is 10 frames per second - you should not need
	// a faster refresh rate than this
	private final int DELAY = 20; //milliseconds
	//anchor coordinate for drawing / animating
	private int x = 0;
	//pixels added to x each time paintComponent() is called
	private int stepSize = 4;
	//Initializes rotation angle for wheels
	private int wheelAngle = 0;
	//Initializes the carHeight variable
	private int carHeight = 0;
	
	/* This method draws on the panel's Graphics context.
	 * This is where the majority of your work will be.
	 *
	 * (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paintComponent(Graphics canvas) 
	{
		//clears the previous image
		//super.paintComponent(canvas);
		
		//account for changes to window size
		int width = getWidth(); // panel width
		int height = getHeight(); // panel height
		
		//Scale car to window
		//double xScale = getWidth() / 600.0; //Unused xScale variable
		double yScale = getHeight() / 400.0;
		
		//Fill the canvas with the background color
		canvas.setColor(getBackground());
		canvas.fillRect(0, 0, width, height);
		
		//Draw sky
		canvas.setColor(Color.blue);
		canvas.fillRect(0, 0, width, height/4);
		    	
		//Draw road
		canvas.setColor(Color.black);
		canvas.fillRect(0, height/4, width, height/4);
		canvas.setColor(Color.yellow);
		canvas.drawRect(-1, (height/4 + height/8), width+2, height/100);
		
		//Draw Sun
		canvas.fillArc((int)(-40*yScale), (int)(-40*yScale), (int)(80*yScale), (int)(80*yScale), 0, 360);
		
		//Draw Clouds
		canvas.setColor(Color.white);
		int cloudX = (width/4)*3;
		int cloudY = (height/10);
		canvas.fillArc(cloudX, cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX-(int)(20*yScale), cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX+(int)(20*yScale), cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX-(int)(10*yScale), cloudY-(int)(10*yScale), (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX+(int)(7*yScale), cloudY-(int)(10*yScale), (int)(30*yScale), (int)(30*yScale), 0, 360);
		cloudX = (width/2);
		cloudY = (height/10)-28;
		canvas.fillArc(cloudX, cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX-(int)(18*yScale), cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX+(int)(22*yScale), cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX-(int)(8*yScale), cloudY-(int)(10*yScale), (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX+(int)(10*yScale), cloudY-(int)(10*yScale), (int)(30*yScale), (int)(30*yScale), 0, 360);
		cloudX = (width/4);
		cloudY = (height/10);
		canvas.fillArc(cloudX, cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX-(int)(20*yScale), cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX+(int)(20*yScale), cloudY, (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX-(int)(10*yScale), cloudY-(int)(10*yScale), (int)(30*yScale), (int)(30*yScale), 0, 360);
		canvas.fillArc(cloudX+(int)(7*yScale), cloudY-(int)(10*yScale), (int)(30*yScale), (int)(30*yScale), 0, 360);
				
		//Calculate the new position
		x = ((x + (int)(stepSize*yScale)) % (width+(int)(95*yScale)));
    	Toolkit.getDefaultToolkit().sync();
		
    	//Calculate vertical origin of car
		carHeight = (int)(45*yScale);
		int y = (height/2 - carHeight/2) - 10;
		
		//Draw the car body
		canvas.setColor(Color.red);
		int xPoints[] = {(int)(x-(5*yScale)),(int)(x-(65*yScale)), (int)(x-(75*yScale)), (int)(x-(95*yScale))};
		int yPoints[] = {y, (int)(y-(15*yScale)), (int)(y-(15*yScale)), y};
		int nPoints = 4;
		canvas.fillPolygon(xPoints, yPoints, nPoints);
		canvas.fillRect((int)(x-(95*yScale)), y, (int)(90*yScale), (int)(8*yScale));
		
		//Draw the car windows
		canvas.setColor(Color.cyan);
		canvas.fillRect((int)(x-(75*yScale)), (int)(y-(15*yScale)), (int)(10*yScale), (int)(10*yScale));
		canvas.fillArc((int)(x-(76*yScale)), (int)(y-(25*yScale)), (int)(20*yScale), (int)(20*yScale), 270, 76);
		
		//Draw the wheel rubber
		canvas.setColor(Color.darkGray);
		canvas.fillArc((int)(x-(90*yScale)), y, (int)(20*yScale), (int)(20*yScale), 0, 360);
		canvas.fillArc((int)(x-(30*yScale)), y, (int)(20*yScale), (int)(20*yScale), 0, 360);
		
		//Draw and animate the wheel spokes
		wheelAngle = ((wheelAngle + 15) % (60));
		canvas.setColor(Color.white);
		int wheelDiameter = (int)(20*yScale);
		canvas.fillArc((int)(x-(90*yScale)), y, wheelDiameter, wheelDiameter, 0 - wheelAngle, 10); //Rear Wheel
		canvas.fillArc((int)(x-(90*yScale)), y, wheelDiameter, wheelDiameter, 60 - wheelAngle, 10);
		canvas.fillArc((int)(x-(90*yScale)), y, wheelDiameter, wheelDiameter, 120 - wheelAngle, 10);
		canvas.fillArc((int)(x-(90*yScale)), y, wheelDiameter, wheelDiameter, 180 - wheelAngle, 10);
		canvas.fillArc((int)(x-(90*yScale)), y, wheelDiameter, wheelDiameter, 240 - wheelAngle, 10);
		canvas.fillArc((int)(x-(90*yScale)), y, wheelDiameter, wheelDiameter, 300 - wheelAngle, 10);
		canvas.fillArc((int)(x-(30*yScale)), y, wheelDiameter, wheelDiameter, 0 - wheelAngle, 10); //Front Wheel
		canvas.fillArc((int)(x-(30*yScale)), y, wheelDiameter, wheelDiameter, 60 - wheelAngle, 10);
		canvas.fillArc((int)(x-(30*yScale)), y, wheelDiameter, wheelDiameter, 120 - wheelAngle, 10);
		canvas.fillArc((int)(x-(30*yScale)), y, wheelDiameter, wheelDiameter, 180 - wheelAngle, 10);
		canvas.fillArc((int)(x-(30*yScale)), y, wheelDiameter, wheelDiameter, 240 - wheelAngle, 10);
		canvas.fillArc((int)(x-(30*yScale)), y, wheelDiameter, wheelDiameter, 300 - wheelAngle, 10);
		
		//Draw Vault Boy avatar in foreground
		int avatarX = width/2;
		int avatarY = height - (int)(100*yScale); 
		ImageIcon avatar = new ImageIcon("avatar.png");
		canvas.drawImage(avatar.getImage(), avatarX-(int)(54*yScale), avatarY-(int)(80*yScale), (int)(108*yScale), (int)(160*yScale), null);
		
		//TODO: Trees for the foreground
		
		//TODO: Text overlay
		String str = "It's a nice day!";
		canvas.setFont(new Font("Courier New", Font.BOLD, (int)(36*((yScale+yScale)/2))));
		FontMetrics metrics = canvas.getFontMetrics();
		int textX = (width - metrics.stringWidth(str))/2; 
        int textY = (height -50 - metrics.getHeight());
        canvas.setColor(Color.red);
        canvas.drawString(str, textX, textY);
	}

	/**
	 * Constructor for the display panel initializes
	 * necessary variables. Only called once, when the
	 * program first begins.
	 * This method also sets up a Timer that will call
	 * paint() with frequency specified by the DELAY
	 * constant.
	 */
	public TrafficAnimation() 
	{
		setBackground(new Color(1,142,14));
		//Do not initialize larger than 800x600
		int initWidth = 600;
		int initHeight = 400;
		setPreferredSize(new Dimension(initWidth, initHeight));
		this.setDoubleBuffered(true);
		
		//Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/////////////////////////////////////////////
	// DO NOT MODIFY main() or startAnimation()
	/////////////////////////////////////////////
	
	/**
	 * Starting point for the TrafficAnimation program
	 * @param args unused
	 */
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Traffic Animation");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new TrafficAnimation());
		frame.pack();
		frame.setVisible(true);
	}

   /**
    * Create an animation thread that runs periodically
	* DO NOT MODIFY this method!
	*/
    private void startAnimation()
    {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                repaint();
            }
        };
        new Timer(DELAY, taskPerformer).start();
    }
}

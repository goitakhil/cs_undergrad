import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Animated program with a ball bouncing off the program boundaries
 * 
 * @author mvail
 * @author Tyler Bevan
 */
@SuppressWarnings("serial")
public class BouncyBall extends JPanel {
	private final int INIT_WIDTH = 600;
	private final int INIT_HEIGHT = 400;
	private final int DELAY = 1; // milliseconds between Timer events
	private Random rand; // random number generator
	private int x, y; // anchor point coordinates
	private int xDelta, yDelta; // change in x and y from one step to the next
	private final int DELTA_RANGE = 10; // range for xDelta and yDelta
	private int Radius = 10; // circle radius
	private boolean BallGrowing = true; // Determines if the ball is growing or
										// shrinking
	private boolean RedBool = true, BlueBool = true, GreenBool = true;
	private int Red = 0, Blue = 0, Green = 0;

	/**
	 * Draws a filled oval with random color and dimensions.
	 *
	 * @param g
	 *            Graphics context
	 * @return none
	 */
	public void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();

		// clear canvas
		//g.setColor(getBackground());
		//g.fillRect(0, 0, width, height);

		// Increment the size of the ball based on whether it is growing or
		// shrinking
		if (Radius == 50)
			BallGrowing = false;
		else if (Radius == 10)
			BallGrowing = true;

		if (BallGrowing == true)
			Radius++;
		else
			Radius--;

		// CALCULATE NEW X, Changing the delta if it is on a border
		if (x <= Radius) {
			xDelta = rand.nextInt(DELTA_RANGE / 2 - 2) + 2;
		} else if ((x + Radius) >= width) {
			xDelta = -(rand.nextInt(DELTA_RANGE / 2 - 2) + 2);
		}
		x += xDelta;

		// CALCULATE NEW Y, Changing the delta if it is on a border
		if (y <= Radius) {
			yDelta = rand.nextInt(DELTA_RANGE / 2 - 2) + 2;
		} else if ((y + Radius) >= height) {
			yDelta = -(rand.nextInt(DELTA_RANGE / 2 - 2) + 2);
		}
		y += yDelta;

		// Systematically change the color
		// Check if the color is increasing or decreasing
		if (Red >= 250)
			RedBool = false;
		else if (Red <= 25)
			RedBool = true;

		if (Blue >= 250)
			BlueBool = false;
		else if (Blue <= 25)
			BlueBool = true;

		if (Green >= 250)
			GreenBool = false;
		else if (Green <= 25)
			GreenBool = true;

		// Increment the RGB values by a random int from 1-5 inclusive.
		if (RedBool == true)
			Red += rand.nextInt(5) + 1;
		else
			Red -= rand.nextInt(5) + 1;

		if (BlueBool == true)
			Blue += rand.nextInt(5) + 1;
		else
			Blue -= rand.nextInt(5) + 1;

		if (GreenBool == true)
			Green += rand.nextInt(5) + 1;
		else
			Green -= rand.nextInt(5) + 1;

		// NOW PAINT THE OVAL
		g.setColor(new Color(Red, Blue, Green));
		g.fillOval(x - Radius, y - Radius, 2 * Radius, 2 * Radius);

		// Makes the animation smoother
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Constructor for the display panel initializes necessary variables. Only
	 * called once, when the program first begins. This method also sets up a
	 * Timer that will call paint() with frequency specified by the DELAY
	 * constant.
	 */
	public BouncyBall() {
		setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
		this.setDoubleBuffered(true);
		setBackground(Color.white);

		rand = new Random(); // instance variable for reuse in paint()

		// initial ball location within panel bounds
		x = rand.nextInt(INIT_WIDTH - Radius) + Radius / 2;
		y = rand.nextInt(INIT_HEIGHT - Radius) + Radius / 2;

		// deltas for x and y
		xDelta = rand.nextInt(DELTA_RANGE) - (DELTA_RANGE / 2 - 1) + 1;
		yDelta = rand.nextInt(DELTA_RANGE) - (DELTA_RANGE / 2 - 1) + 1;

		Red = rand.nextInt(256);
		Blue = rand.nextInt(256);
		Green = rand.nextInt(256);
		// Start the animation - DO NOT REMOVE
		startAnimation();
	}

	/**
	 * Create an animation thread that runs periodically DO NOT MODIFY
	 */
	private void startAnimation() {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				repaint();
			}
		};
		new Timer(DELAY, taskPerformer).start();
	}

	/**
	 * Starting point for the BouncyBall program DO NOT MODIFY
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Bouncy Ball");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new BouncyBall());
		frame.pack();
		frame.setVisible(true);
	}

}

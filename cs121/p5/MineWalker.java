import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * Driver class, displays a JFrame and adds a MineWalkerPanel.
 * 
 * @author Tyler Bevan
 */
public class MineWalker {
	/**
	 * Main Method, Creates the JFrame and contents.
	 * 
	 * @param args
	 *            (unused)
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Mine Walker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new MineWalkerPanel());
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setMinimumSize(new Dimension(800, 600));
		frame.pack();
		frame.setVisible(true);

	}

}

import java.awt.Dimension;
import javax.swing.JFrame;

public class UpDown {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Up Down");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.getContentPane().add(new UpDownPanel());
	      frame.setPreferredSize(new Dimension(200,100));
	      frame.setMinimumSize(new Dimension(200,100));
	      frame.pack();
	      frame.setVisible(true);

	}

}

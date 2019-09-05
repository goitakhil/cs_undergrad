import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Panel that displays the color key in a vertical grid.
 * 
 * @author Tyler Bevan
 */
@SuppressWarnings("serial")
public class KeyPanel extends JPanel {

	/**
	 * Constructor: Builds the panel from Color and String arrays.
	 */
	public KeyPanel() {

		Color[] keyColors = { Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED, Color.BLUE, Color.BLACK, Color.GRAY,
				Color.GRAY };
		String[] keyLabels = { "0 Mines", "1 Mine", "2 Mines", "3 Mines", "Goal", "Hit", "X = Mine", "O = Path" };
		setLayout(new GridLayout(10, 1));
		((GridLayout) getLayout()).setVgap(3);
		add(new JPanel());
		for (int i = 0; i < keyColors.length; i++) {
			JLabel newLabel = new JLabel(keyLabels[i]);
			newLabel.setBackground(keyColors[i]);
			newLabel.setOpaque(true);
			newLabel.setForeground(Color.BLACK);
			newLabel.setFocusable(false);
			newLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(newLabel);
		}
		add(new JPanel());

		getComponent(5).setForeground(Color.WHITE);
		getComponent(6).setForeground(Color.WHITE);
		getComponent(7).setForeground(Color.WHITE);
		getComponent(8).setForeground(Color.WHITE);

	}
}

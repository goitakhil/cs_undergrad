import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Demonstrates the use of one listener for multiple buttons.
 * 
 * @author Java Foundations
 */
@SuppressWarnings("serial")
public class UpDownPanel extends JPanel {
	private JButton up, down;
	private JLabel label;
	private JPanel buttonPanel;
	private int number;

	/**
	 * Constructor: Sets up the GUI.
	 */
	public UpDownPanel()
	{
		up = new JButton("Up");
		down = new JButton("Down");

		ButtonListener listener = new ButtonListener();
		up.addActionListener(listener);
		down.addActionListener(listener);
		number = 50;
		label = new JLabel("" + number);

		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(200, 40));
		buttonPanel.setBackground(Color.orange);
		buttonPanel.add(up);
		buttonPanel.add(down);

		setPreferredSize(new Dimension(200, 80));
		setBackground(Color.cyan);
		add(label);
		add(buttonPanel);
	}

	/**
	 * Represents a listener for both buttons.
	 */
	private class ButtonListener implements ActionListener {
		/**
		 * Determines which button was pressed and sets the label text
		 * accordingly.
		 */
		public void actionPerformed(ActionEvent event)
		{
			if (event.getSource() == up)
				label.setText("" + ++number);
			else
				label.setText("" + --number);
		}
	}
}

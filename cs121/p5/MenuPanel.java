import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

/**
 * Displays a menu with various game options.
 * 
 * @author Tyler Bevan
 */
@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	JButton newGameButton = new JButton("New Game");
	JButton showPathButton = new JButton("Show Path");
	JButton showMinesButton = new JButton("Show Mines");
	JSlider gridSizeSlider = new JSlider();
	JSlider numMinesSlider = new JSlider();
	JLabel lives = new JLabel("Lives = 3");
	JLabel points = new JLabel("Points = 1000");

	/**
	 * Constructor: Creates the panel in a horizontal grid.
	 */
	public MenuPanel() {
		setLayout(new GridLayout(2, 5));
		newGameButton.addActionListener(new NewGameButtonListener());
		showPathButton.addActionListener(new ShowPathButtonListener());
		showMinesButton.addActionListener(new ShowMinesButtonListener());

		configureGridSlider();
		configureMinesSlider();
		add(newGameButton);
		add(showPathButton);
		lives.setHorizontalAlignment(SwingConstants.CENTER);
		add(lives);

		JLabel gridLabel = new JLabel("Grid Size   ");
		gridLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(gridLabel);

		add(gridSizeSlider);
		add(showMinesButton);
		add(new JPanel());
		points.setHorizontalAlignment(SwingConstants.CENTER);
		add(points);

		JLabel mineLabel = new JLabel("Mines %   ");
		mineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		add(mineLabel);

		add(numMinesSlider);
	}

	/**
	 * Sets properties of the Grid Size slider.
	 */
	private void configureGridSlider() {
		gridSizeSlider.setMaximum(50);
		gridSizeSlider.setMinimum(10);
		gridSizeSlider.setSnapToTicks(true);
		gridSizeSlider.setValue(10);
		gridSizeSlider.setMajorTickSpacing(10);
		gridSizeSlider.setPaintTicks(true);
		gridSizeSlider.setPaintLabels(true);
	}

	/**
	 * Sets properties of the Mines Percentage slider.
	 */
	private void configureMinesSlider() {
		numMinesSlider.setMaximum(80);
		numMinesSlider.setMinimum(10);
		numMinesSlider.setSnapToTicks(true);
		numMinesSlider.setMajorTickSpacing(10);
		numMinesSlider.setMinorTickSpacing(5);
		numMinesSlider.setPaintTicks(true);
		numMinesSlider.setPaintLabels(true);
		numMinesSlider.setValue(25);
	}

	/**
	 * Returns the mines percentage as decimal from 0 to 1.
	 * 
	 * @return numMines The ratio of mines.
	 */
	public double getMinesRatio() {
		return numMinesSlider.getValue() / 100.0;
	}

	/**
	 * Sets the label displaying lives remaining.
	 * 
	 * @param numLives
	 *            The number of lives to display.
	 */
	public void setLives(int numLives) {
		lives.setText("Lives = " + numLives);
	}

	/**
	 * Sets the label displaying points.
	 * 
	 * @param points
	 *            The number of points to display.
	 */
	public void setPoints(int points) {
		this.points.setText("Points = " + points);
	}

	/**
	 * ActionListener object for the New Game button.
	 */
	private class NewGameButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			((MineWalkerPanel) getParent()).newGame(gridSizeSlider.getValue());
		}
	}

	/**
	 * ActionListener object for the Show Path button.
	 */
	private class ShowPathButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			((MineWalkerPanel) getParent()).showPath();
		}
	}

	/**
	 * ActionListener object for the Show Mines button.
	 */
	private class ShowMinesButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			((MineWalkerPanel) getParent()).showMines();
		}
	}

}

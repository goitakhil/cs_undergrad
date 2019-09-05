import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Parent panel, contains the menus and grid panel.
 * 
 * @author Tyler Bevan
 */
@SuppressWarnings("serial")
public class MineWalkerPanel extends JPanel {
	private MineGridPanel grid;
	private MenuPanel menu;
	private boolean pathVisible, minesVisible;
	private static final int MAX_LIVES = 5, MAX_POINTS = 600;
	private int lives, points;

	/**
	 * Constructor: Generates the sub panels and lays them out in BorderLayout.
	 */
	public MineWalkerPanel() {
		setLayout(new BorderLayout());
		grid = new MineGridPanel(10, 0.25);
		grid.setPreferredSize(new Dimension(400, 400));
		add(grid, BorderLayout.CENTER);

		KeyPanel key = new KeyPanel();
		key.setPreferredSize(new Dimension(200, 400));
		add(key, BorderLayout.WEST);

		menu = new MenuPanel();
		menu.setPreferredSize(new Dimension(400, 100));
		add(menu, BorderLayout.SOUTH);

		pathVisible = false;
		minesVisible = false;
		lives = MAX_LIVES;
		points = MAX_POINTS;
	}

	/**
	 * Creates a new game and resets all values
	 * 
	 * @param gridSize
	 */
	public void newGame(int gridSize) {
		remove(grid);
		grid = new MineGridPanel(gridSize, menu.getMinesRatio());
		add(grid);
		revalidate();
		getParent().validate();
		pathVisible = false;
		minesVisible = false;
		lives = MAX_LIVES;
		menu.setLives(lives);
		points = MAX_POINTS;
		menu.setPoints(points);
	}

	/**
	 * Removes a life and updates variables
	 */
	public void loseLife() {
		lives--;
		points -= 100;
		menu.setPoints(points);
		menu.setLives(lives);
		if (lives == 0) {
			gameOver();
		}
	}

	/**
	 * Removes a point for each step you take.
	 */
	public void losePoint() {
		points--;
		menu.setPoints(points);
	}

	/**
	 * Toggles whether the path is visible and calls grid.showPath().
	 */
	public void showPath() {
		if (!pathVisible) {
			pathVisible = true;
			grid.showPath(pathVisible);
		} else {
			pathVisible = false;
			grid.showPath(pathVisible);
		}

	}

	/**
	 * Toggles minesVisible and calls grid.showMines().
	 */
	public void showMines() {
		if (!minesVisible) {
			minesVisible = true;
			grid.showMines(minesVisible);
		} else {
			minesVisible = false;
			grid.showMines(minesVisible);
		}

	}

	/**
	 * Displays a message that you lost. Displays mines and path.
	 */
	private void gameOver() {
		grid.setGameOver();
		JOptionPane.showMessageDialog(null, "You Died!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		if (!minesVisible)
			showMines();
		if (!pathVisible)
			showPath();
	}

	/**
	 * Displays win message. Shows mines and path.
	 */
	public void gameWin() {
		grid.setGameOver();
		JOptionPane.showMessageDialog(null, "You Win!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		if (!minesVisible)
			showMines();
		if (!pathVisible)
			showPath();
	}

}

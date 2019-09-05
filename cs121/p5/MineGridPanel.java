import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Panel containing the grid of JButtons for the game.
 * 
 * @author Tyler Bevan
 */
@SuppressWarnings("serial")
public class MineGridPanel extends JPanel {
	private static final int DELAY = 500;
	private int numMines;
	private int gridSize;
	private Sector[][] grid;
	private ButtonListener listener = new ButtonListener();
	private boolean gameOver = false, colorToggle = false;
	private Sector lastStep;

	/**
	 * Constructor: Generates the panel and all sectors.
	 * 
	 * @param gridSize
	 *            The size of the grid to make.
	 * @param minesRatio
	 *            The double ratio of mines to safe spaces.
	 */
	public MineGridPanel(int gridSize, double minesRatio) {
		this.gridSize = gridSize;
		setLayout(new GridLayout(gridSize, gridSize));
		numMines = (int) ((gridSize * gridSize) * minesRatio);
		grid = new Sector[gridSize][gridSize];

		fillGrid();
		lastStep = grid[gridSize - 1][gridSize - 1];

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				this.add(grid[i][j]);
			}
		}
		startAnimation();
	}

	/**
	 * Creates all sectors and sets their properties. Each sector needs a
	 * listener, isSafe boolean, isMined boolean, and int number of nearby
	 * mines.
	 */
	private void fillGrid() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				grid[i][j] = new Sector();
				grid[i][j].addActionListener(listener);
			}
		}

		RandomWalk path = new RandomWalk(gridSize);
		path.createWalk();
		for (Point p : path.getPath()) {
			grid[p.x][p.y].setSafe(true);
		}

		int mines = 0;
		while (mines < numMines) {
			int x = (int) (Math.random() * gridSize);
			int y = (int) (Math.random() * gridSize);
			if (!grid[y][x].isSafe()) {
				grid[y][x].setMined(true);
				mines++;
			}
		}

		findDanger();

		grid[0][0].setBackground(Color.BLUE);
		grid[0][0].setText("GOAL");
		grid[0][0].setForeground(Color.WHITE);
		grid[gridSize - 1][gridSize - 1].setMovable(true);
		grid[gridSize - 1][gridSize - 2].setMovable(true);
		grid[gridSize - 2][gridSize - 1].setMovable(true);

	}

	/**
	 * Checks each sector for nearby mines and sets the int minesClose value.
	 */
	private void findDanger() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				int nearMines = 0;
				try {
					if (grid[i - 1][j].isMined())
						nearMines++;
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				try {
					if (grid[i + 1][j].isMined())
						nearMines++;
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				try {
					if (grid[i][j - 1].isMined())
						nearMines++;
				} catch (ArrayIndexOutOfBoundsException e) {
				}
				try {
					if (grid[i][j + 1].isMined())
						nearMines++;
				} catch (ArrayIndexOutOfBoundsException e) {
				}

				grid[i][j].setDanger(nearMines);
			}
		}
	}

	/**
	 * Shows and hides the safe path to the goal.
	 * 
	 * @param isVisible
	 *            Whether or not the path should be shown.
	 */
	public void showPath(boolean isVisible) {
		if (isVisible) {
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					if (grid[i][j].isSafe()) {
						grid[i][j].setText("O");
						if (gridSize >= 30) {
							grid[i][j].setFont(new java.awt.Font("ARIAL", 0, 8));

						}
					}
				}
			}
		} else {
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					if (grid[i][j].getText().equals("O")) {
						grid[i][j].setText("");
					}
					grid[0][0].setText("GOAL");
				}
			}
		}
	}

	/**
	 * Shows and hides the locations of all mines.
	 * 
	 * @param isVisible
	 *            Whether to show or hide the mines.
	 */
	public void showMines(boolean isVisible) {
		if (isVisible) {
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					if (grid[i][j].isMined()) {
						grid[i][j].setText("X");
						if (gridSize >= 30) {
							grid[i][j].setFont(new java.awt.Font("ARIAL", 0, 8));

						}
					}
				}
			}
		} else {
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					if (grid[i][j].getText().equals("X")) {
						grid[i][j].setText("");
					}
					grid[0][0].setText("GOAL");
				}
			}
		}
	}

	/**
	 * Flashes the current player location.
	 */
	private void toggleColor() {
		if (!colorToggle) {
			lastStep.setBackground(Color.WHITE);
			colorToggle = true;
		} else {
			setColor(lastStep);
			colorToggle = false;
		}
	}

	/**
	 * Colors a sector based on the nearby mines.
	 * 
	 * @param space
	 *            The sector to color.
	 */
	private void setColor(Sector space) {
		switch (space.getDanger()) {
		case 0:
			space.setBackground(Color.GREEN);
			break;
		case 1:
			space.setBackground(Color.YELLOW);
			break;
		case 2:
			space.setBackground(Color.ORANGE);
			break;
		case 3:
			space.setBackground(Color.RED);
			break;
		case 4:
			space.setBackground(Color.PINK);
			break;
		}
	}

	/**
	 * Sets the GameOver boolean to true.
	 */
	public void setGameOver() {
		gameOver = true;
	}

	////////////////////////////////////////////////////////////

	/**
	 * ActionListener class to listen for clicks.
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!gameOver) {
				Sector source = (Sector) e.getSource();
				if (source.canMove()) {
					if (source.isMined() && !source.isExploded()) {
						hitMine(source);
						source.setExploded(true);
					} else if (source.isExploded()) {
					} else if (source == grid[0][0]) {
						((MineWalkerPanel) getParent()).gameWin();
					} else {
						setColor(source);
						setMovable(source);
						setColor(lastStep);
						lastStep = source;
						((MineWalkerPanel) getParent()).losePoint();
					}
				}
			}
		}

		/**
		 * Sets the color of an exploded sector and takes a life.
		 * 
		 * @param space
		 *            The sector that was clicked.
		 */
		private void hitMine(Sector space) {
			space.setBackground(Color.BLACK);
			((MineWalkerPanel) getParent()).loseLife();
		}

		/**
		 * Check if a space is a valid move and set the movable boolean.
		 * 
		 * @param space
		 *            The sector to check.
		 */
		private void setMovable(Sector space) {
			int x = -1, y = -1;

			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					grid[i][j].setMovable(false);
				}
			}

			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					if (grid[i][j] == space) {
						x = j;
						y = i;
					}
				}
			}

			try {
				grid[y - 1][x].setMovable(true);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			try {
				grid[y + 1][x].setMovable(true);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			try {
				grid[y][x - 1].setMovable(true);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
			try {
				grid[y][x + 1].setMovable(true);
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}

	}

	////////////////////////////////////////////////////////////////////////////

	/**
	 * Performs action when timer event fires.
	 */
	private class TimerActionListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			toggleColor();
		}
	}

	/**
	 * Create an animation thread that runs periodically
	 */
	private void startAnimation() {
		TimerActionListener taskPerformer = new TimerActionListener();
		new Timer(DELAY, taskPerformer).start();
	}
}

import java.awt.Color;
import javax.swing.JButton;

/**
 * Represents a sector in the MineGrid. Extends JButton with extra variables.
 * 
 * @author Tyler Bevan
 */
@SuppressWarnings("serial")
public class Sector extends JButton {
	private boolean isMined = false;
	private boolean isExploded = false;
	private boolean isSafe = false;
	private int minesClose = 0;
	private boolean canMove = false;

	public Sector() {
		setBackground(Color.GRAY);
	}

	public boolean isMined() {
		return isMined;
	}

	public void setMined(boolean mined) {
		isMined = mined;
	}

	public boolean isSafe() {
		return isSafe;
	}

	public void setSafe(boolean safe) {
		isSafe = safe;
	}

	public int getDanger() {
		return minesClose;
	}

	public void setDanger(int mines) {
		minesClose = mines;
	}

	/**
	 * @return the isVisited
	 */
	public boolean canMove() {
		return canMove;
	}

	/**
	 * @param isVisited
	 */
	public void setMovable(boolean isVisitable) {
		canMove = isVisitable;
	}

	public boolean isExploded() {
		return isExploded;
	}

	public void setExploded(boolean isExploded) {
		this.isExploded = isExploded;
	}
}

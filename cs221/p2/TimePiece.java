
public interface TimePiece
{
	/**
	 * Resets the time to midnight
	 */
	public void reset();
	
	/**
	 * Ticks the clock forward one second
	 */
	public void tick();
	
	/**
	 * Displays the current time
	 */
	public void display();
}

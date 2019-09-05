/**
 * Represents a clock.
 * This class accounts for clock drift and
 * keeps track of clock type. Time is kept
 * using the Time class.
 * 
 * @author tbevan
 *
 */
public abstract class Clock implements TimePiece
{
	private ClockType clockType;
	private double driftPerSecond;
	protected Time time;

	/**
	 * Constructor: Creates a clock with a type and drift.
	 * @param ClockType clockType
	 * @param double driftPerSecond
	 */
	public Clock(ClockType clockType, double driftPerSecond)
	{
		this.clockType = clockType;
		this.driftPerSecond = driftPerSecond;
		reset();
	}
	
	
	@Override
	public void reset()
	{
		time = new Time(0,0,0,driftPerSecond);
	}

	@Override
	public void tick()
	{
		time.incrementTime();
	}

	@Override
	public abstract void display();

	/**
	 * Gets the ClockType of this clock.
	 * @return ClockType clockType
	 */
	public ClockType getClockType()
	{
		return clockType;
	}

	/**
	 * Sets the ClockType
	 * @param clockType
	 */
	public void setClockType(ClockType clockType)
	{
		this.clockType = clockType;
	}

	/**
	 * Gets the drift per second
	 * @return double driftPerSecond
	 */
	public double getDriftPerSecond()
	{
		double drift = driftPerSecond;
		return drift;
	}

	/**
	 * Sets the drift per second
	 * @param double driftPerSecond
	 */
	public void setDriftPerSecond(double driftPerSecond)
	{
		this.driftPerSecond = driftPerSecond;
	}

	/**
	 * Contains the possible clock types.
	 * @author tbevan
	 *
	 */
	public enum ClockType
	{
		NATURAL, MECHANICAL, DIGITAL, QUANTUM;
	}
}

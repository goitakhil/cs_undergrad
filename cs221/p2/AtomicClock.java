/**
 * Atomic Clock object, subclass of Clock
 * @author tbevan
 *
 */

public class AtomicClock extends Clock
{
	private final static ClockType TYPE = ClockType.QUANTUM;
	private final static double DRIFT = 0.0;
	
	public AtomicClock()
	{
		super(TYPE, DRIFT);
	}

	@Override
	public void display()
	{
		System.out.printf(getClockType() + " atomic clock time [%2d:%02d:%02d], total drift = %03.2f seconds\n" , time.reportedHour(), time.reportedMinute(), time.reportedSecond(), time.getTotalDrift());

	}
}

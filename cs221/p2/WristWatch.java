/**
 * Wrist watch object, subclass of Clock
 * @author tbevan
 *
 */
public class WristWatch extends Clock
{
	private final static ClockType TYPE = ClockType.DIGITAL;
	private final static double DRIFT = 0.000347222;
	
	public WristWatch()
	{
		super(TYPE, DRIFT);
	}

	@Override
	public void display()
	{
		System.out.printf(getClockType() + " wrist watch time [%2d:%02d:%02d], total drift = %03.2f seconds\n" , time.reportedHour(), time.reportedMinute(), time.reportedSecond(), time.getTotalDrift());

	}
}

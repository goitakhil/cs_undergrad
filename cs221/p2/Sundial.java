
/**
 * Sundial object, subclass of Clock
 * @author tbevan
 *
 */
public class Sundial extends Clock
{
	private final static ClockType TYPE = ClockType.NATURAL;
	private final static double DRIFT = 0.0;
	
	public Sundial()
	{
		super(TYPE, DRIFT);
	}

	@Override
	public void display()
	{
		System.out.printf(getClockType() + " sundial time [%2d:%02d:%02d], total drift = %03.2f seconds\n" , time.reportedHour(), time.reportedMinute(), time.reportedSecond(), time.getTotalDrift());

	}

}

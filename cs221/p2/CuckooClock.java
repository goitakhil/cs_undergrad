/**
 * Cuckoo Clock object, subclass of Clock
 * @author tbevan
 *
 */
public class CuckooClock extends Clock
{
	private final static ClockType TYPE = ClockType.MECHANICAL;
	private final static double DRIFT = 0.000694444;
	
	public CuckooClock()
	{
		super(TYPE, DRIFT);
	}

	@Override
	public void display()
	{
		System.out.printf(getClockType() + " cuckoo clock time [%2d:%02d:%02d], total drift = %03.2f seconds\n" , time.reportedHour(), time.reportedMinute(), time.reportedSecond(), time.getTotalDrift());

	}


}

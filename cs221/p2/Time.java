/**
 * A class for keeping track of time. 
 * @author Matt T, mvail
 * @version spring 2016
 */
public class Time
{
	// constants for calculating time in seconds 
	private final int SECONDS_PER_MINUTE = 60;
	private final int MINUTES_PER_HOUR = 60;
	private final int HOURS_PER_DAY = 24;
	private final int SECONDS_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
	// data members 
	private long startTime;	// start time in seconds
	private long actualDeltaTime;	// seconds passed since startTime
	private double totalDrift; 		// seconds lost since start time
	private double driftPerSecond; // amount of drift each second 
	
	/**
	 * Default constructor - time set to midnight, zero drift 
	 */
	public Time()
	{
		this(0, 0, 0, 0);
	}
	
	/**
	 * Constructor for Time class. 
	 * @param hour - integer representing the starting hour (0-23) 
 	 * @param minute - integer representing the starting minute (0-59)
	 * @param second - integer representing the starting second (0-59)
	 * @param driftPerSecond - double representing amount of drift (time lost) per second 
	 */
	public Time(int hour, int minute, int second, double driftPerSecond)
	{
		startTime = hour * MINUTES_PER_HOUR * SECONDS_PER_MINUTE +
				 minute * SECONDS_PER_MINUTE +
				 second;
		if (startTime > SECONDS_PER_DAY) startTime = startTime % SECONDS_PER_DAY;
		this.driftPerSecond = driftPerSecond; 
		actualDeltaTime = 0;
		totalDrift = 0;
	}
	
	/**
	 * Increment the time by 1 second
	 */
	public void incrementTime()
	{
		// update deltaTime 
		actualDeltaTime++; 
		// update drift
		totalDrift += driftPerSecond; 		
	}
	
	/**
	 * Reset the time to its starting time.  
	 */
	public void resetToStartTime()
	{
		actualDeltaTime = 0;
		totalDrift = 0;
	}
	
	/**
	 * Gets deltaTime.
	 * @return deltaTime - long integer representing the change in time 
	 * since startTime in seconds 
	 */
	public long actualElapsedTime() {
		return actualDeltaTime;
	}
	
	/**
	 * @return actual elapsed seconds minus accumulated drift
	 */
	public long reportedElapsedTime() {
		return (long)(actualDeltaTime - totalDrift); 
	}
	
	/**
	 * @return actual current hour
	 */
	public int actualHour() {
		int seconds = (int)((startTime + actualDeltaTime) % SECONDS_PER_DAY);
		return seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
	}
	
	/**
	 * @return reported hour, taking accumulated drift into account
	 */
	public int reportedHour() {
		int seconds = (int)((startTime + actualDeltaTime - totalDrift) % SECONDS_PER_DAY);
		return seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
	}
	
	/**
	 * @return actual current minutes
	 */
	public int actualMinute() {
		int seconds = (int)((startTime + actualDeltaTime) % SECONDS_PER_DAY);
		int hour = seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		seconds -= hour * (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		return seconds/SECONDS_PER_MINUTE;
	}
	
	/**
	 * @return reported current minutes, taking accumulated drift into account
	 */
	public int reportedMinute() {
		int seconds = (int)((startTime + actualDeltaTime - totalDrift) % SECONDS_PER_DAY);
		int hour = seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		seconds -= hour * (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		return seconds/SECONDS_PER_MINUTE;
	}

	/**
	 * @return actual current seconds
	 */
	public int actualSecond() {
		int seconds = (int)((startTime + actualDeltaTime) % SECONDS_PER_DAY);
		int hour = seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		seconds -= hour * (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		int minutes = seconds/SECONDS_PER_MINUTE;
		seconds -= minutes * SECONDS_PER_MINUTE;
		return seconds;
	}
	
	/**
	 * @return reported current seconds, taking accumulated drift into account
	 */
	public int reportedSecond() {
		int seconds = (int)((startTime + actualDeltaTime - totalDrift) % SECONDS_PER_DAY);
		int hour = seconds / (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		seconds -= hour * (MINUTES_PER_HOUR * SECONDS_PER_MINUTE);
		int minutes = seconds/SECONDS_PER_MINUTE;
		seconds -= minutes * SECONDS_PER_MINUTE;
		return seconds;
	}
	
	/**
	 * Display the values stored in this instance of a Time object 
	 */
	public String formattedReportedTime()
	{
		return String.format("%2d:%02d:%02d", reportedHour(), reportedMinute(), reportedSecond());
	}
	
	/**
	 * @return double representing total amount of drift in seconds 
	 */
	public double getTotalDrift()
	{
		return totalDrift;
	}

	/**
	 * @return double representing amount of drift per second 
	 */
	public double getDriftPerSecond()
	{
		return driftPerSecond;
	}

} // Time class 

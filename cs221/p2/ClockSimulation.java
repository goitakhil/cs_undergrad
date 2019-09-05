
public class ClockSimulation
{

	public static void main(String[] args)
	{
		Bag<Clock> myClocks = new Bag<Clock>();
		myClocks.add(new Sundial());
		myClocks.add(new CuckooClock());
		myClocks.add(new GrandfatherClock());
		myClocks.add(new WristWatch());
		myClocks.add(new AtomicClock());
		
		System.out.println("SUNDIAL");
		System.out.print("Initial value: ");
		testClock(myClocks.get(0), 0);
		System.out.print("After one day: ");
		testClock(myClocks.get(0), 86400);
		System.out.print("After one week: ");
		testClock(myClocks.get(0), 604800);
		System.out.print("After one month: ");
		testClock(myClocks.get(0), 2592000);
		System.out.print("After one year: ");
		testClock(myClocks.get(0), 31536000);
		
		System.out.println("\nCUCKOO CLOCK");
		System.out.print("Initial value: ");
		testClock(myClocks.get(1), 0);
		System.out.print("After one day: ");
		testClock(myClocks.get(1), 86400);
		System.out.print("After one week: ");
		testClock(myClocks.get(1), 604800);
		System.out.print("After one month: ");
		testClock(myClocks.get(1), 2592000);
		System.out.print("After one year: ");
		testClock(myClocks.get(1), 31536000);
		
		System.out.println("\nGRANDFATHER CLOCK");
		System.out.print("Initial value: ");
		testClock(myClocks.get(2), 0);
		System.out.print("After one day: ");
		testClock(myClocks.get(2), 86400);
		System.out.print("After one week: ");
		testClock(myClocks.get(2), 604800);
		System.out.print("After one month: ");
		testClock(myClocks.get(2), 2592000);
		System.out.print("After one year: ");
		testClock(myClocks.get(2), 31536000);
		
		System.out.println("\nWRIST WATCH");
		System.out.print("Initial value: ");
		testClock(myClocks.get(3), 0);
		System.out.print("After one day: ");
		testClock(myClocks.get(3), 86400);
		System.out.print("After one week: ");
		testClock(myClocks.get(3), 604800);
		System.out.print("After one month: ");
		testClock(myClocks.get(3), 2592000);
		System.out.print("After one year: ");
		testClock(myClocks.get(3), 31536000);
		
		System.out.println("\nATOMIC CLOCK");
		System.out.print("Initial value: ");
		testClock(myClocks.get(4), 0);
		System.out.print("After one day: ");
		testClock(myClocks.get(4), 86400);
		System.out.print("After one week: ");
		testClock(myClocks.get(4), 604800);
		System.out.print("After one month: ");
		testClock(myClocks.get(4), 2592000);
		System.out.print("After one year: ");
		testClock(myClocks.get(4), 31536000);
		
	}
	
	private static void testClock(Clock clock, long sec)
	{
		for (long i = 0; i < sec; i++)
		{
			clock.tick();
		}
		clock.display();
		clock.reset();
	}

}

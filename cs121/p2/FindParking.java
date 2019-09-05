import java.text.NumberFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This application generates 4 parking spots at random using a defined seed. It
 * then calculates cost and distance and reports the closest spot to the user.
 * 
 * @author Tyler Bevan
 */
public class FindParking {
	/**
	 * The main method in this class contains all the code used in the class. It
	 * will prompt the user for input on seed and time required.
	 * 
	 */
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter randomness seed: ");
		long seed = keyboard.nextLong();
		Random rand = new Random();
		rand.setSeed(seed);
		System.out.print("Number of Minutes needed: ");
		int time = keyboard.nextInt();
		
		// Set driver position
		int X = rand.nextInt(100);
		int Y = rand.nextInt(100);

		// Create parking spots. Set spots 3 and 4 to be 30 cents per 10 min.  Also, remember the closest spot.
		ParkingSpot closest = null;
		ArrayList<ParkingSpot> spots = new ArrayList<ParkingSpot>();
		for (int i = 0; i <= 3; i++) {
			spots.add(new ParkingSpot("Street " + i, rand.nextInt(100), rand.nextInt(100)));
			if (i==0) closest = spots.get(0);
			else if (closest.getDistance(X, Y) > spots.get(i).getDistance(X, Y)) closest = spots.get(i);
		}
		spots.get(2).setCharge(.3);
		spots.get(3).setCharge(.3);
		
		// Get currency format
		NumberFormat fmt = NumberFormat.getCurrencyInstance();
		
		// Display generated spots and car location
		for (ParkingSpot spot : spots) {
			System.out.println(spot.toString());
			System.out.println("\tDistance = " + spot.getDistance(X, Y) + " Cost = " + fmt.format(spot.getCost(time)));
		}
		System.out.println("Vehicle Location: X = " + X + "  Y = " + Y);

		// Display results
		System.out.println("Least Distance: " + closest.getDistance(X, Y) + " blocks");
		System.out.println("Closest Spot: " + closest);
		keyboard.close();
	}
}
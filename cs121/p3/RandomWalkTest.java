import java.util.Scanner;
/**
 * Tests the RandomWalk class
 * @author Tyler Bevan
 */
public class RandomWalkTest
{									// try grid 200 and random seed 123 using the GUI.
	private static int gridSize = 0;
	private static long seed = 0;
	private static RandomWalk walk;
	
	/**
	 * Gets the grid size and seed from the user interactively
	 */
	private static void getInput() 
	{
		Scanner kb = new Scanner(System.in);
		System.out.print("Enter the grid size: ");
		for (;;){
			gridSize = kb.nextInt();
			if (gridSize > 0){
				break;
			}
			System.out.println("Incorrect Input... Please enter a positive integer greater than 0");
			System.out.print("Enter the grid size: ");
		}
		System.out.print("Enter Random seed (0 to skip): ");
		for (;;){
			seed = kb.nextLong();
			if (seed >= 0){
				break;
			}
			System.out.println("Incorrect Input... Please enter a positive integer or 0 for random");
			System.out.print("Enter the desired seed: ");
		}
		kb.close();
	}
	
	
	/**
	 * Primary Driver method
	 * @param args
	 */
	public static void main(String[] args)
	{
		// call getInput to process user input
		getInput();
//		if (seed != 0){
//		String [] s = new String[2];
//		s[0] = "" + gridSize;
//		s[1] = "" + seed;
//		RandomWalkGUI.main(s);
//		}
//		else{
//			String [] s = new String[1];
//			s[0] = "" + gridSize;
//			RandomWalkGUI.main(s);
//		}
		// create RandomWalk object using the appropriate constructor
		if (seed == 0){
			walk = new RandomWalk(gridSize);
		}
		else {
			walk = new RandomWalk(gridSize,seed);
		}
		
		// create the random walk and then print it
		walk.createWalk();
		System.out.println(walk.toString());
		System.out.println("Steps taken: " + (walk.getPath().size() - 1));
		
	}
}

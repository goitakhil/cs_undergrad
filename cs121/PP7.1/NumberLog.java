import java.util.Scanner;

/**
 * 
 */

/**
 * @author Tyler
 *
 */
public class NumberLog {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		
		int[] log = new int[1000];
		int counter = 0;
		for (;;) {
			System.out.print("Enter an integer from 0 to 50: ");
			int input = kb.nextInt();
			if (input < 0 || input > 50) break;
			log[counter] = input;
			counter++;
		}
		int[] logParsed = new int[counter];
		int[] sums = new int[51];
		for (int i = 0; i < 51;++i){sums[i] = 0;}
		
		for (int i = 0; i < counter;i++){
			logParsed[i] = log[i];
			sums[logParsed[i]] += 1;
		}
		
		for (int i = 0; i < 51;++i){
			if (sums[i] != 0){
				System.out.println(i + ": " + sums[i]);
			}
		}
		
		kb.close();
	}

}

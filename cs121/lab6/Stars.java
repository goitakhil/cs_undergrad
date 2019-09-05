/**
 * Demonstrates the use of nested for loops.
 * 
 * @author Java Foundations
 */
public class Stars {
	/**
	 * Prints a triangle shape using asterisk (star) characters.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final int MAX_ROWS = 10;

		for (int row = MAX_ROWS; row > 0; row--) {
			for (int blank = row - 1; blank > 0; blank--) {
				System.out.print(" ");
			}
			for (int star = 11 - row; star > 0; star--)
				System.out.print("*");

			System.out.println();
		}
	}
}
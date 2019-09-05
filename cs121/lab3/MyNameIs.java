import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
/**
 * A simple application to test use of String, Math, DecimalFormat and NumberFormat classes.
 * @author Tyler Bevan 
 */
public class MyNameIs {

	public static void main(String[] args){
		// Creates new scanner object
		Scanner keyboard = new Scanner(System.in); 
		
		// Collect user input
		System.out.print("Enter your first name:\t\t"); 
		String firstName = keyboard.nextLine();
		System.out.print("Enter your last name:\t\t");
		String lastName = keyboard.nextLine();
		System.out.print("Enter a number:\t\t\t");
		double firstNum = keyboard.nextDouble();
		System.out.print("Enter another number (0-1):\t");
		double secondNum = keyboard.nextDouble();
		
		//Prints out name variations
		System.out.println();
		System.out.println("Hi, my name is " + firstName + " " + lastName + ".");
		System.out.println("You'll find me under \"" + lastName + ", " + firstName + "\"");
		String firstInital = firstName.substring(0, 1);
		System.out.println("My name badge: \"" + firstInital + ". " + lastName + "\"");
		
		//Formats and prints numbers
		DecimalFormat twoDigits = new DecimalFormat();
		twoDigits.setMaximumFractionDigits(2);
		NumberFormat percentage = NumberFormat.getPercentInstance();		
		System.out.println(percentage.format(secondNum) + " of " + twoDigits.format(firstNum) + " is " + twoDigits.format(firstNum*secondNum) + ".");
		System.out.println(twoDigits.format(firstNum) + " raised to the power of " + twoDigits.format(secondNum) + " is " + twoDigits.format(Math.pow(firstNum, secondNum)));
		keyboard.close(); //Kill scanner to make eclipse happy.
	}
}

import java.util.Scanner;

/**
 * Demonstrates the use of a programmer-defined class.
 * @author Java Foundations
 */
public class SnakeEyes
{
   /**
    * Creates two Die objects and rolls them several times, counting
    * the number of snake eyes that occur.
    * 
    * @param args (unused)
    */
   public static void main (String[] args)
   {
	  Scanner kb = new Scanner(System.in);
	  int sides = 0;
	  long seed = 0;
	  do {
		  System.out.print("Enter a positive number of sides:  ");
	  sides = kb.nextInt();
	  } while (sides <= 0);
	  System.out.print("Enter a seed or 0 for random:  ");
	  seed = kb.nextLong();
	  kb.close();
	  final int ROLLS = 500;
      int num1, num2, count = 0;
      
      Die die1, die2;
      
      if (seed == 0){
      	  die1 = new Die(sides);
      	  die2 = new Die(sides);
      }
      else {
    	  die1 = new Die(sides, seed);
    	  die2 = new Die(sides, seed);
      }
      for (int roll = 1; roll <= ROLLS; roll++)
      {
         num1 = die1.roll();         
         num2 = die2.roll();
         
         //print the value of die1 and die2
         System.out.println("roll " + roll);
         
         System.out.println("die1 value: " + die1.getFaceValue());
         System.out.println("die2 value: " + die2.getFaceValue());
         
         System.out.println();
         
         if (num1 == 1 && num2 == 1)    // check for snake eyes
            count++;
      }

      System.out.println ("Number of rolls: " + ROLLS);
      System.out.println ("Number of snake eyes: " + count);
      System.out.println ("Ratio: " + (double)count / ROLLS);
   }
}

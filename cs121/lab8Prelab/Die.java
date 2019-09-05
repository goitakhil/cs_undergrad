import java.util.Random;

/**
 * Die.java       Java Foundations
 *
 * Represents one die (singular of dice) with faces showing values
 * between 1 and n.
*/

public class Die
{
   //private final int MAX = 6;  // maximum face value

   private int faceValue;  // current value showing on the die
   private int numberOfSides;
   private Random rand = new Random();
   
   /**
    * Constructor: Sets the initial face value of this die.
    */
   public Die(int sides)
   {
      faceValue = 1;
      numberOfSides = sides;
   }
   
   /**
    * Constructor: Initalizes die with a given seed.
    * @param sides
    * @param seed
    */
   public Die(int sides, long seed)
   {
      faceValue = 1;
      numberOfSides = sides;
      this.rand = new Random(seed);
   }
   
   
   /**
    * Computes a new face value for this die and returns the result.
    * @return The new face value.
    */
   public int roll()
   {
	  //int faceValue = 5; //shows that this hides the instance variable!
	  
      //faceValue = (int)(Math.random() * numberOfSides) + 1;
	   System.out.println(faceValue);
	   System.out.println(numberOfSides);
      this.faceValue = rand.nextInt(numberOfSides) + 1;

      return faceValue;
   }

   /**
    * Face value mutator. The face value is not modified if the
    * specified value is not valid.
    * 
    * @param value The new face value. Must be between 1 and max face 
    * value.
    */
   public void setFaceValue (int value)
   {
      if (value > 0 && value <= numberOfSides)
         faceValue = value;
   }

   /**
    * Face value accessor.
    * @return The current face value.
    */
   public int getFaceValue()
   {
	   return faceValue;
   }

   /**
    * Returns a string representation of this die.
    */
   public String toString()
   {
      String result = "Die [faceValue = " + faceValue + "\tnumberOfSides = " + numberOfSides + "]";
 
      return result;
   }
}

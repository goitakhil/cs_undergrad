
import java.util.*;

public class EightPuzzle extends Driver
{
    public EightPuzzle(State initial)
    {
        super(initial);
        store = new SearchFrontierStack();
    }

    public static void main(String[] args)
    {
        //EightPuzzle p = new EightPuzzle(new EightPuzzleState(args[0]));
        //p.search();
    	for (int i = 0; i < 3; i++)
    	{
    		EightPuzzle p = new EightPuzzle(RandomEight.getRandomEight(10));
    		p.search();
    	}
    	for (int i = 0; i < 3; i++)
    	{
    		EightPuzzle p = new EightPuzzle(RandomEight.getRandomEight(20));
    		p.search();
    	}
    	for (int i = 0; i < 3; i++)
    	{
    		EightPuzzle p = new EightPuzzle(RandomEight.getRandomEight(30));
    		p.search();
    	}
    	for (int i = 0; i < 3; i++)
    	{
    		EightPuzzle p = new EightPuzzle(RandomEight.getRandomEight(100));
    		p.search();
    	}
    }
}



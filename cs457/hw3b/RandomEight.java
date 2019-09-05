
import java.util.*;

public class RandomEight
{
    public static void main(String[] args)
    {
       int ranMoves = Integer.parseInt(args[0]);
       EightPuzzleState s = getRandomEight(ranMoves);

       System.out.print(s);
    }
    
    public static EightPuzzleState getRandomEight(int steps)
    {
    	Random r = new Random();
    	EightPuzzleState s = new EightPuzzleState("12345678 ");
        for(int i=0;i<steps;i++)
        {
            Action[] actions = s.getAvailableActions();
            s = (EightPuzzleState)(actions[r.nextInt(actions.length)].updateState());
        }
        s = new EightPuzzleState(s.board);
        return s;
    }
}

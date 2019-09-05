
import java.util.*;

public abstract class SearchFrontierStorage
{
   HashSet<State> hs = new HashSet<State>();
   public final void add(State s)
   {
      if(hs.add(s)) addToStorage(s);
      //addToStorage(s);
      //else System.out.println("Repeat: " + s);
   }
   
   public final void reset()
   {
	   hs = new HashSet<State>();
   }

   public abstract boolean isEmpty();
   protected abstract void addToStorage(State s);
   public abstract State next();
}

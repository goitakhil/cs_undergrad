

import java.util.*;

public class SearchFrontierStack extends SearchFrontierStorage
{
   Stack<State> stack = new Stack<State>();
   public boolean isEmpty()
   {
       return stack.isEmpty();
   }
   protected void addToStorage(State s)
   {
       stack.push(s);
   }
   public State next()
   {
       return stack.pop();
   }
}


import java.util.*;

public abstract class State
{
    State parent;
    int depth;
    public State(State parent)
    {
        this.parent=parent;
        depth = parent.getDepth() + 1; 
    }
    public State()
    {
        parent=null;
        depth=0;
    }

    public String traverseFullList()
    {
        return ((parent!=null)?parent.traverseFullList():"") + this.toString();
    }
    
    public int getDepth()
    {
    	return depth;
    }

    /** returns a list of available actions determined by the state */
    public abstract Action[] getAvailableActions();
    public abstract boolean isGoal();
    public abstract boolean canActOn();
}



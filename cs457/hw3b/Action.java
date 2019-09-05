

import java.util.*;

public interface Action <E extends State>
{
    /** returns a new state based on the given action */
    public E updateState();
}




import java.util.*;

public abstract class Driver {
	State initial;
	SearchFrontierStorage store;

	public Driver(State initial) {
		this.initial = initial;
	}

	public int search() {
		store.add(initial);
		State s;
		int numExpanded = 0;
		int maxDepth = 0;
		while (true) {
			while (!store.isEmpty()) {
				s = store.next();
				numExpanded++;
				//if (numExpanded % 100 == 0)
					//System.out.print("\rDepth = " + maxDepth + " NumExpanded = " + numExpanded);
				if (s.isGoal()) {
					//System.out.println("\nSolution found at depth " + maxDepth);
					System.out.println("\nSolution found at depth " + maxDepth + " for:\n" + initial.toString());
					//System.out.println(s.traverseFullList());
					System.out.println("NumExpanded = \n" + numExpanded);
					return 0;
				} else if (s.getDepth() < maxDepth && s.canActOn()) {
					Action[] list = s.getAvailableActions();
					for (Action a : list) {
						store.add(a.updateState());
					}
				}
			}
			maxDepth++;
            if (maxDepth >= 500) return -1;
			store.reset();
			store.add(initial);
		}
	}

	public static void main(String[] args) {
	}
}

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a random path through a grid of defined size
 * 
 * @author Tyler Bevan
 *
 */
public class RandomWalk {
	private Point end = null;
	private ArrayList<Point> path = new ArrayList<Point>();
	private Random rand;
	private boolean done = false;
	private int failures = 0;

	/**
	 * Constructor without random seed
	 * 
	 * @param gridSize
	 *            Size of desired grid
	 */
	public RandomWalk(int gridSize) {
		end = new Point(gridSize - 1, gridSize - 1);
		path.add(new Point(0, 0));
		rand = new Random();
	}

	/**
	 * Constructor with random seed
	 * 
	 * @param gridSize
	 *            Size of desired grid
	 * @param seed
	 *            Seed for randomness generator
	 */
	public RandomWalk(int gridSize, long seed) {
		end = new Point(gridSize - 1, gridSize - 1);
		path.add(new Point(0, 0));
		rand = new Random(seed);
	}

	/**
	 * Generates the next step in the path and checks if we have finished
	 */
	public void step() {
		Point next = generateNext();
		
		if (path.contains(next) || next.x < 0 || next.y < 0){
			failures++;
			if (failures > 50) backtrack();
		} 
		else if (next.getX() >= end.getX()){
			next = new Point(end.x,next.y + 1);
			path.add(next);
			failures = 0;
		}
		else if (next.getY() >= end.getY()){
			next = new Point(next.x, end.y);
			path.add(next);
			failures = 0;
		}
		else {
			path.add(next);
			failures = 0;
		}
		if (path.get(path.size() - 1).equals(end)) done = true;
	}
	
	/**
	 * Randomly chooses a direction and calls for a new point.
	 * @return next
	 */
	private Point generateNext(){
		double randomNum = rand.nextDouble();
		Point next;
		if (randomNum <= .4) next = stepEast(path.get(path.size() - 1));
		else if (randomNum > .4 && randomNum <= .8) next = stepSouth(path.get(path.size() - 1));
		else if (randomNum > .8 && randomNum <= .9) next = stepWest(path.get(path.size() - 1));
		else next = stepNorth(path.get(path.size() - 1));
		return next;
	}
	
	/**
	 * Generates a step east.
	 * @param currentLocation
	 * @return next
	 */
	private Point stepEast(Point currentLocation){
		return new Point(currentLocation.x + 1, currentLocation.y);
	}
	
	/**
	 * Generates a step south.
	 * @param currentLocation
	 * @return next
	 */
	private Point stepSouth(Point currentLocation){
		return new Point(currentLocation.x, currentLocation.y + 1);
	}
	
	/**
	 * Generates a step west.
	 * @param currentLocation
	 * @return next
	 */
	private Point stepWest(Point currentLocation){
		return new Point(currentLocation.x - 1, currentLocation.y);
	}
	
	/**
	 * Generates a step north.
	 * @param currentLocation
	 * @return next
	 */
	private Point stepNorth(Point currentLocation){
		return new Point(currentLocation.x, currentLocation.y - 1);
	}
	
	/**
	 * Backtracks when the path is stuck.
	 */
	private void backtrack(){
		for (int i = 0; i < 50;++i){
			path.remove(path.size() - 1);
		}
	}
	
	/**
	 * Calculates the entire path
	 */
	public void createWalk() {
		for (; !isDone();) {
			step();
		} // Runs step() until the full path is calculated
	}

	/**
	 * Returns true if we have reached the end
	 * @return done
	 */
	public boolean isDone() {
		return done;
	}

	/**
	 * Returns the ArrayList of the points in the path
	 * @return path
	 */
	public ArrayList<Point> getPath() {
		return path;
	}
	
	/**
	 * Prints nice output of all points
	 */
	public String toString(){
		String s = "";
		for (Point p : path){
			s += "[" + p.x + "," + p.y + "] ";
		}
		return s;
	}
}

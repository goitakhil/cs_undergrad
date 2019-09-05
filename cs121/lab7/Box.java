
/**
 * This class represents a box with width, height, depth, and full attributes
 * 
 * @author Tyler
 *
 */
public class Box {
	private double width, height, depth;
	private boolean full;

	/**
	 * Constructor for instantiating a box
	 * 
	 * @param width
	 * @param height
	 * @param depth
	 */
	public Box(double width, double height, double depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		this.full = false;
		}

	/**
	 * Calculates and returns the box's volume
	 * 
	 * @return
	 */
	public double volume() {
		return this.width * this.height * this.depth;
	}

	/**
	 * Calculates and returns the Surface Area of the box
	 * 
	 * @return area
	 */
	public double surfaceArea() {
		double area;
		area = 2 * (this.width * this.height) + 2 * (this.height * this.depth) + 2 * (this.width * this.depth);
		return area;
	}

	///////////////////////////
	// Getters and Setters Below
	///////////////////////////
	
	/**
	 * @return width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * @return depth
	 */
	public double getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 */
	public void setDepth(double depth) {
		this.depth = depth;
	}

	/**
	 * @return if full
	 */
	public boolean isFull() {
		return full;
	}

	/**
	 * @param set
	 *            if full
	 */
	public void setFull(boolean full) {
		this.full = full;
	}

}

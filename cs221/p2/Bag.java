import java.util.Arrays;

/**
 * A simple indexed but unordered container that holds items of type T 
 * using an array implementation. 
 * @author Matt T 
 * @version fall 2015
 */
public class Bag<T>
{
	private final int INITIAL_CAPACITY = 100;   // default capacity of bag
	private int capacity;  // number of T objects the bag can hold 
	private T[] data;  // array to hold objects of type T in the Bag
	private int count; // number of integers stored in the array

	/**
	 * Default constructor creates an empty Bag
	 */
	@SuppressWarnings("unchecked")
	public Bag() 
	{
		// create array of T elements
		setCapacity(INITIAL_CAPACITY);
		data = (T[])new Object[capacity];
		count = 0;
	}

	/**
	 * Mutator for capacity of the bag. 
	 * @param newCapacity - integer representing new size of bag
	 */
	public void setCapacity(int newCapacity)
	{
		capacity = newCapacity;
	}

	/**
	 * Accessor method for number of elements in the bag. 
	 * @return integer - current number of elements in the bag 
	 */
	public int getSize()
	{
		return count;
	}
	
	/**
	 * Adds a new element to the bag. 
	 *@param element - T object to be inserted into the bag
	 */
	public void add(T element) 
	{
		// if current number of elements is at capacity
		if (count >= capacity) 
		{
			resize();		// expand size of bag 
		} 
		// add item at end of the array
		else 
		{
			data[count] = element;
			count++;
		}
	}

	/**
	 * Increases size of the bag by one half.  
	 */
	private void resize()
	{
		capacity *= 1.5; 
		data = Arrays.copyOf(data, capacity);
	}
	
	/**
	 * Print elements of the bag.
	 */
	public void print() 
	{
		// print out number of elements
		System.out.println("The bag has " + count + " elements:");
		// if not empty
		if (!isEmpty()) 
		{
			// uses toString method of objects to print them
			// separated by commas
			System.out.print(data[0].toString());
			for (int i = 1; i < count; i++) 
			{
				System.out.print(", " + data[i]);
			}
			System.out.println();
		}
	}

	/**
	 * Removes an element from the bag,
	 * throws exception if empty. 
	 * @return reference to the element removed
	 * @throws EmptyCollectionException
	 */
	public T remove()
	{
		T target = null;
		// can't remove an element if empty
		if(isEmpty())
		{
			throw new EmptyCollectionException("Bag");
		}
		//otherwise, remove last element in the bag
		target = data[count - 1];
		data[count - 1] = null;
		count--;

		return target; 
	}
	
	/**
	 * Removes particular element from the bag,
	 * throws exception if not found. 
	 * @param element - item of type T looking for 
	 * @return reference to element of type T if found 
	 * @throws ElementNotFoundException
	 */
	public T remove(T element)
	{
		T target = null;
		Boolean done = false;  // still looking for  element 
		int i = 0; 	// loop variable
		
		// iterate through array looking for element
		while(i < count && !done)
		{
			if (data[i] == element) 
			{
				// get return value
				target = data[i];
				//move last element into space left by this element
				data[i] = data[count-1];
				// set old last position to null
				data[count - 1] = null;
				// update count
				count--;
				// done, so set to true 
				done = true;
			}
			i++;
		}
		// if not done, didn't find element 
		if(!done)
		{
			throw new ElementNotFoundException("Bag");
		}
		return target;
	}

	/**
	 *  Whether bag contains a particular element or not
	 * @param target - element of type T looking for
	 * @return boolean - whether element is found 
	 */
	public boolean contains(T target)
	{
		Boolean found = false;
		int i = 0; 	// loop variable
		
		// iterate through array looking for element
		while(i < count && !found)
		{
			// if found, set found to true 
			if (data[i] == target) 
			{
				found = true;
			}
			i++;
		}
		return found;
	}

	/**
	 * Whether bag is empty or not. 
	 * @return boolean representing whether empty or not 
	 */
	public boolean isEmpty()
	{
		// assume not empty
		Boolean empty = false;
		// set empty to true if bag is empty
		if(count == 0)
		{
			empty = true;
		}
		return empty;
	}
	
	/**
	 * Returns reference to element at given index. 
	 * @param index - integer index in the array
	 * @return reference to element at that index
	 * @throws IndexOutOfBoundsException 
	 */
	public T get(int index)
	{
		T target = null;
		// if try to access index that contains an element
		if(index < 0 || index >= count)
		{
			throw new IndexOutOfBoundsException("Bag index is out of bounds.");
		}
		target = data[index];
		return target;
	}

}
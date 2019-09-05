import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * Indexed Unordered Array List class.
 * @author Tyler
 */
public class IUArrayList<T> implements IndexedUnorderedListADT<T>, Iterable<T> {

	protected int rear;
	protected T[] list;
	protected int modCount;

	/**
	 * Constructor: IUArrayList
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList() {
		list = (T[]) new Object[10];
		modCount = 0;
		rear = 0;
	}

	@Override
	public void addToFront(T element) {
		autoGrow();
		int index = rear;
		while (index > 0){
			list[index] = list[index - 1];
		}
		list[0] = element;
		rear++;
		modCount++;
	}


	@Override
	public void addToRear(T element) {
		autoGrow();
		list[rear] = element;
		rear++;
		modCount++;
	}

	@Override
	public void addAfter(T element, T target) {
		autoGrow();
		int targetIndex = rear;
		boolean found = false;
		for (int i = 0; i < rear; i++){
			if (list[i] == target){
				targetIndex = i;
				found = true;
			}			
		}
		if (!found){
			throw new ElementNotFoundException("addAfter");
		}
		for (int index = rear;index > targetIndex; index--){
			list[index] = list[index-1];
		}
		list[targetIndex] = element;
		rear++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) throw new EmptyCollectionException("removeFirst");
		T returnObject = list[0];
		for (int i = rear - 1; i >= 0; i--){
			list[i] = list[i+1];
		}
		rear--;
		modCount++;
		list[rear] = null;
		return returnObject;
	}

	@Override
	public T removeLast() {
		if (isEmpty()) throw new EmptyCollectionException("removeLast");
		T returnObject = list[rear-1];
		rear--;
		list[rear] = null;
		modCount++;
		return returnObject;
	}


	@Override
	public T remove(T element) {
		if (isEmpty()) throw new EmptyCollectionException("remove");
		int index = indexOf(element);
		return remove(index);
	}


	@Override
	public T first() {
		if (isEmpty()) throw new EmptyCollectionException("first");
		return list[0];
	}


	@Override
	public T last() {
		if (isEmpty()) throw new EmptyCollectionException("last");
		return list[rear-1];
	}


	@Override
	public boolean contains(T target) {
		boolean found = false;
		for (int i = 0; i < rear; i++){
			if (list[i] == target){
				found = true;
			}			
		}
		return found;
	}


	@Override
	public boolean isEmpty() {
		if (rear == 0){
			return true;
		} else {
			return false;
		}
	}


	@Override
	public int size() {
		return rear;
	}


	@Override
	public Iterator<T> iterator() {
		return new IUArrayListIterator();
	}


	@Override
	public void add(int index, T element) {

		if (index > rear) throw new IndexOutOfBoundsException("add()");
		for (int i = rear;i > index; i--){
			list[i] = list[i-1];
		}
		list[index] = element;
		rear++;
		modCount++;
	}



	@Override
	public void set(int index, T element) {
		if (index > rear){
			throw new IndexOutOfBoundsException("set()");
		} else {
			list[index] = element;
			modCount++;
		}
	}


	@Override
	public void add(T element) {
		addToRear(element);
	}


	@Override
	public T get(int index) {
		if (index > rear){
			throw new IndexOutOfBoundsException("get()");
		} else {
			return list[index];
		}
	}


	@Override
	public int indexOf(T element) {
		boolean found = false;
		int index = 0;
		int i = 0;
		while (!found && i < rear){
			if (list[i] == element){
				found = true;
				index = i;
			}
			i++;
		}
		if (!found){
			throw new ElementNotFoundException("indexOf()");
		}
		return index;
	}


	@Override
	public T remove(int index) {
		if (isEmpty()) throw new EmptyCollectionException("last");
		if (index > rear){
			throw new IndexOutOfBoundsException("remove()");
		} else {
			T returnObject = list[index];
			for (int i = rear - 1; i >= index; i--){
				list[i] = list[i+1];
			}
			rear--;
			modCount++;
			return returnObject;
		}
	}
	
	/**
	 * Checks if the array is full then expands if it is.
	 */
	@SuppressWarnings("unchecked")
	private void autoGrow(){
		if (rear >= list.length - 1){
			T[] tmpList = (T[]) new Object[(list.length * 2)];
			for (int i = 0; i < list.length; i++){
				tmpList[i] = list[i];
			}
			list = tmpList;
			modCount++;
		}
	}

	/**
	 * Iterator implementation, includes remove function.
	 * @author Tyler
	 *
	 */
	private class IUArrayListIterator extends IUArrayList<T> implements Iterator<T> {
		private int birthModCount;
		private int currentIndex;
		private boolean removed;

		/**
		 * Constructor for Iterator class.
		 */
		public IUArrayListIterator() {
			birthModCount = modCount;
			currentIndex = 0;
			removed = false;
		}

		@Override
		public boolean hasNext() {
			if (modCount == birthModCount) {
				if (currentIndex < rear - 1) {
					return true;
				} else {
					return false;
				}
			} else {
				throw new ConcurrentModificationException("modCount mismatch");
			}
		}

		@Override
		public T next() {
			if (modCount == birthModCount) {
				if (hasNext()) {
					currentIndex++;
					return list[currentIndex];
				} else {
					throw new ElementNotFoundException("next()");
				}
			} else {
				throw new ConcurrentModificationException("modCount mismatch");
			}
		}

		@Override
		public void remove() {
			if (modCount != birthModCount) throw new ConcurrentModificationException("modCount mismatch");
			if (!removed) {
				remove(currentIndex);
				removed = true;
			} else {
				throw new ElementNotFoundException("remove()");
			}
		}

	}

}

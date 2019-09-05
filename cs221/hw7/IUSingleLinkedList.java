import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IUSingleLinkedList<T> implements IndexedUnorderedListADT<T>, Iterable<T> {

	private Node<T> head, tail;
	private int size;
	private int modCount;

	public IUSingleLinkedList() {
		head = null;
		tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		if (size == 0) {
			addWhenEmpty(element);
		} else {
			Node<T> newNode = new Node<T>(element, head);
			head = newNode;
			size++;
			modCount++;
		}
	}

	@Override
	public void addToRear(T element) {
		if (size == 0) {
			addWhenEmpty(element);
		} else {
			Node<T> newNode = new Node<T>(element);
			tail.setNext(newNode);
			tail = newNode;
			size++;
			modCount++;
		}
	}

	@Override
	public void addAfter(T element, T target) {
		Node<T> targetNode = head;
		Node<T> newNode;
		boolean found = false;
		while (targetNode != null && !found) {
			if (targetNode.getElement().equals(target)) {
				found = true;
			} else {
				targetNode = targetNode.getNext();
			}
		}
		if (!found)
			throw new ElementNotFoundException("addAfter()");
		newNode = new Node<T>(element, targetNode.getNext());
		targetNode.setNext(newNode);
		if (newNode.getNext() == null) {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if (size == 0)
			throw new EmptyCollectionException("removeFirst");
		Node<T> removedNode = head;
		head = removedNode.getNext();
		size--;
		modCount++;
		return removedNode.getElement();
	}

	@Override
	public T removeLast() {
		if (size == 0)
			throw new EmptyCollectionException("IUSingleLinkedList");
		if (size == 1) {
			return (removeFirst());
		} else {
			Node<T> currentNode = head;
			Node<T> removedNode = null;
			boolean Done = false;
			while (!Done) {
				if (currentNode.getNext() == tail) {
					Done = true;
				} else {
					currentNode = currentNode.getNext();
				}
			}
			tail = currentNode;
			removedNode = tail.getNext();
			tail.setNext(null);
			size--;
			modCount++;
			return removedNode.getElement();
		}
	}

	@Override
	public T remove(T element) {
		if (size == 0)
			throw new ElementNotFoundException("remove()");
		Node<T> preRemovalNode = head;
		boolean found = false;
		if (head.getElement().equals(element)) {
			return removeFirst();
		} else {
			while (preRemovalNode.getNext() != null && !found) {
				if (preRemovalNode.getNext().getElement().equals(element)) {
					found = true;
				} else {
					preRemovalNode = preRemovalNode.getNext();
				}
			}
			if (!found)
				throw new ElementNotFoundException("remove()");
			Node<T> removedNode = preRemovalNode.getNext();
			preRemovalNode.setNext(removedNode.getNext());
			size--;
			modCount++;
			return removedNode.getElement();
		}
	}

	@Override
	public T first() {
		if (size == 0)
			throw new EmptyCollectionException("IUSingleLinkedList");
		return head.getElement();
	}

	@Override
	public T last() {
		if (size == 0)
			throw new EmptyCollectionException("IUSingleLinkedList");
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		boolean found = false;
		Node<T> current = head;
		while (!found && current != null) {
			if (current.getElement().equals(target)) {
				found = true;
			} else {
				current = current.getNext();
			}
		}
		return found;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new IUSLListIterator<T>(head, modCount, this);
	}

	@Override
	public void add(int index, T element) {
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException();
		if (index == 0) {
			addToFront(element);
		} else if (index == size) {
			addToRear(element);
		} else {
			int currentIndex = 0;
			Node<T> targetNode = head;
			Node<T> previousNode = null;
			while (currentIndex < index && targetNode != null) {
				previousNode = targetNode;
				targetNode = targetNode.getNext();
				currentIndex++;
			}
			Node<T> newNode = new Node<T>(element, targetNode);
			previousNode.setNext(newNode);
			size++;
			modCount++;
		}
	}

	@Override
	public void set(int index, T element) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		int currentIndex = 0;
		Node<T> targetNode = head;
		while (currentIndex < index) {
			targetNode = targetNode.getNext();
			currentIndex++;
		}
		targetNode.setElement(element);
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public T get(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		int current = 0;
		Node<T> targetNode = head;
		while (current < index) {
			targetNode = targetNode.getNext();
			current++;
		}
		return targetNode.getElement();
	}

	@Override
	public int indexOf(T element) {
		int index = 0;
		boolean found = false;
		Node<T> currentNode = head;
		while (currentNode != null && !found) {
			if (currentNode.getElement().equals(element)) {
				found = true;
			} else {
				currentNode = currentNode.getNext();
				index++;
			}
		}
		if (!found)
			index = -1;
		return index;
	}

	@Override
	public T remove(int index) {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		if (index == 0) {
			return removeFirst();
		} else if (index == size - 1) {
			return removeLast();
		} else {
			int currentIndex = 0;
			Node<T> targetNode = head;
			Node<T> previousNode = null;
			while (currentIndex < index) {
				previousNode = targetNode;
				targetNode = targetNode.getNext();
				currentIndex++;
			}
			previousNode.setNext(targetNode.getNext());
			targetNode.setNext(null);
			size--;
			modCount++;
			return targetNode.getElement();
		}
	}

	private void addWhenEmpty(T element) {
		Node<T> newNode = new Node<T>(element);
		head = newNode;
		tail = newNode;
		size = 1;
		modCount++;
	}

	public int getModCount() {
		return modCount;
	}
	
	@Override
	public String toString(){
		return "IUSingleLinkedList containing " + size + " objects.";
	}

	/**
	 * Iterator implementation for an IUSingleLinkedList
	 * 
	 * @author Tyler
	 *
	 * @param <E>
	 */
	public class IUSLListIterator<E> implements Iterator<E> {
		private int iteratorModCount;
		private Node<E> currentNode, previousNode;
		private IUSingleLinkedList<E> parent;
		private boolean removed;

		public IUSLListIterator(Node<E> head, int modCount, IUSingleLinkedList<E> parentClass) {
			iteratorModCount = modCount;
			currentNode = new Node<E>(null, head);
			parent = parentClass;
			removed = true;
		}

		@Override
		public boolean hasNext() {
			if (parent.getModCount() != iteratorModCount)
				throw new ConcurrentModificationException("IUSLListIterator");
			if (currentNode == null) return false;
			if (currentNode.getNext() == null) {
				return false;
			} else {
				return true;
			}
		}

		@Override
		public E next() {
			if (parent.getModCount() != iteratorModCount)
				throw new ConcurrentModificationException("IUSLListIterator");
			if (hasNext()){
			previousNode = currentNode;
			currentNode = currentNode.getNext();
			removed = false;
			return currentNode.getElement();
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			if (parent.getModCount() != iteratorModCount)
				throw new ConcurrentModificationException("IUSLListIterator");
			if (removed) throw new IllegalStateException();
			if (currentNode == null) throw new IllegalStateException();
			
				previousNode.setNext(currentNode.getNext());
				currentNode.setNext(null);
				currentNode = previousNode;
				removed = true;
			
		}

	}

	/**
	 * Node for singly linked list.
	 * 
	 * @author Tyler
	 *
	 * @param <E>
	 */
	public class Node<E> {
		private E element;
		private Node<E> next;

		/**
		 * Constructor without next.
		 * 
		 * @param element
		 */
		public Node(E element) {
			this.element = element;
			next = null;
		}

		/**
		 * Constructor with next.
		 * 
		 * @param element
		 * @param next
		 */
		public Node(E element, Node<E> next) {
			this.element = element;
			this.next = next;
		}

		/**
		 * Returns the element.
		 * 
		 * @return
		 */
		public E getElement() {
			return element;
		}

		/**
		 * Sets the element.
		 * 
		 * @param element
		 */
		public void setElement(E element) {
			this.element = element;
		}

		/**
		 * Returns the next Node.
		 * 
		 * @return
		 */
		public Node<E> getNext() {
			return next;
		}

		/**
		 * Sets the next Node.
		 * 
		 * @param next
		 */
		public void setNext(Node<E> next) {
			this.next = next;
		}

	}

}

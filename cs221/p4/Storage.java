/**
 * A container for storing elements of type T in one of several possible
 * underlying data structures. Additional data structures (or variations on data
 * structures) can be added by adding to the DataStructure enum values and
 * adding corresponding cases to wrapper methods.
 * 
 * @author mvail
 */
public class Storage<T> {
	/** supported underlying data structures for Storage to use */
	public static enum DataStructure {
		stack, queue
	}

	/** the data structure chosen for this Storage to use */
	private DataStructure dataStructure;
	/**
	 * the data structures - only one will be instantiated and used by this
	 * Storage
	 */
	private Queue<T> queue;
	private Stack<T> stack;

	/**
	 * Constructor
	 * 
	 * @param dataStructure
	 *            choice of DataStructures
	 */
	public Storage(DataStructure dataStructure) {
		this.dataStructure = dataStructure;
		switch (this.dataStructure) {
		case stack:
			stack = new Stack<T>();
			break;
		case queue:
			queue = new Queue<T>();
		}
	}

	/**
	 * Alternative to using the constructor returns a Storage already configured
	 * to use a Stack
	 * 
	 * @return instance of Storage configured to use a Stack
	 */
	public static <E> Storage<E> getStackInstance() {
		return new Storage<E>(DataStructure.stack);
	}

	/**
	 * Alternative to using the constructor returns a Storage already configured
	 * to use a Queue
	 * 
	 * @return instance of Storage configured to use a Queue
	 */
	public static <E> Storage<E> getQueueInstance() {
		return new Storage<E>(DataStructure.queue);
	}

	/**
	 * Add element to underlying data structure
	 * 
	 * @param element
	 *            T to store
	 */
	public void store(T element) {
		if (stack == null){
			queue.enqueue(element);
		} else {
			stack.push(element);
		}
	}

	/**
	 * Remove and return the next T from storage
	 * 
	 * @return next T from storage
	 */
	public T retrieve() {
		if (stack == null){
			return queue.dequeue();
		} else {
			return stack.pop();
		}
	}

	/** @return true if store is empty, else false */
	public boolean isEmpty() {
		if (stack == null){
			return queue.isEmpty();
		} else {
			return stack.isEmpty();
		}
	}

	/** @return size of store */
	public int size() {
		if (stack == null){
			return queue.getSize();
		} else if (queue == null) {
			return stack.getSize();
		} else
		return -1;
	}

	private class Queue<T> {
		private Node<T> front;
		private Node<T> rear;
		private int size;
		
		public Queue(){
			front = null;
			rear = null;
			size = 0;
		}
		
		public void enqueue(T element){
			if (size == 0){
				front = new Node<T>(element);
				rear = front;
				size = 1;
			} else {
				rear.setNext(new Node<T>(element));
				rear = rear.getNext();
				size++;
			}
		}
		
		public T dequeue(){
			if (size == 0) throw new EmptyCollectionException("dequeue");
			T returnVal = front.getElement();
			front = front.getNext();
			size--;
			return returnVal;
		}
		
		
		public int getSize() {
			return size;
		}
		
		public boolean isEmpty(){
			if (size == 0){
				return true;
			} else {
				return false;
			}
		}

		private class Node<T> {
			private Node<T> nextNode;
			private T element;			
			
			public Node(T element) {
				this.element = element;
			}

			public void setNext(Node<T> newNode) {
				this.nextNode = newNode;
			}

			public Node<T> getNext() {
				return nextNode;
			}

			public T getElement() {
				return element;
			}

		}

	}

	/**
	 * Stack implementation to power the Storage class.
	 * @author Tyler
	 *
	 * @param <T>
	 */
	private class Stack<T> {
		public Node<T> top;
		public int size;
		
		/**
		 * Constructor for the stack class.
		 */
		public Stack(){
			top = null;
			size = 0;
		}
		
		/**
		 * Pushes an object onto the stack.
		 * @param element
		 */
		public void push(T element){
			Node<T> newNode = new Node<T>(element, top);
			top = newNode;
			size++;
		}
		
		/**
		 * Pops an object off the stack.
		 * @return
		 */
		public T pop(){
			if (size == 0) throw new EmptyCollectionException("pop");
			T returnVal = top.getElement();
			top = top.getNext();
			size--;
			return returnVal;
		}
		
		/**
		 * Gets the size of the stack.
		 * @return
		 */
		public int getSize(){
			return size;
		}
		
		/**
		 * Returns true if the stack is empty.
		 * @return
		 */
		public boolean isEmpty(){
			if (size == 0){
				return true;
			} else {
				return false;
			}
		}

		/**
		 * Node class for the stack.
		 * @author Tyler
		 *
		 * @param <T>
		 */
		private class Node<T> {
			private Node<T> nextNode;
			private T element;
			
			/**
			 * Constructor for the Node class.
			 * @param element
			 */
			public Node(T element, Node<T> nextNode) {
				this.element = element;
				this.nextNode = nextNode;
			}

			public Node<T> getNext() {
				return nextNode;
			}

			public T getElement() {
				return element;
			}

		}
	}

} // class Storage

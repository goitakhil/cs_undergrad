package p2;

@SuppressWarnings("rawtypes")
public class MaxHeap<T extends Comparable<T>> {

	private Node[] tree;
	private int size;

	public MaxHeap() {
		tree = new Node[10];
		size = 0;
	}
	
	public MaxHeap(T[] array){
		tree = new Node[array.length];
		for (int i = 0; i < array.length; i++){
			tree[i] = new Node(array[i]);
		}
		for (int i = (array.length/2) + 1; i >= 0;i-- ){
			maxHeapifyDown(i);
		}
	}
	
	public int getSize(){
		return size;
	}

	public Comparable getObject(int index) {
		return tree[index].getElement();
	}

	@SuppressWarnings("unchecked")
	public void addObject(T newObject) {
		if (size == tree.length){
			growArray();
		}
		Node<T> newNode = new Node(newObject);
		tree[size] = newNode;
		maxHeapifyUp(size);
		size++;
	}
	
	public Comparable removeObject(int index){
		Comparable returnVal = tree[index].getElement();
		tree[index] = tree[size - 1];
		size--;
		maxHeapifyDown(index);
		return returnVal;
	}

	@SuppressWarnings("unchecked")
	public void maxHeapifyUp(int index) {
		if (index > 0) {
			if (tree[index].getElement().compareTo(tree[parent(index)].getElement()) > 0) {
				Node<T> tmpNode = tree[index];
				tree[index] = tree[parent(index)];
				tree[parent(index)] = tmpNode;
				maxHeapifyUp(parent(index));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void maxHeapifyDown(int index) {
		if (index < size) {
			if (leftChild(index) < size &&(tree[index].getElement().compareTo(tree[leftChild(index)].getElement()) < 0) && (tree[rightChild(index)].getElement().compareTo(tree[leftChild(index)].getElement()) <= 0)) {
				Node<T> tmpNode = tree[index];
				tree[index] = tree[leftChild(index)];
				tree[leftChild(index)] = tmpNode;
				maxHeapifyDown(leftChild(index));
			} else if (rightChild(index) < size && (tree[index].getElement().compareTo(tree[rightChild(index)].getElement()) < 0)  && (tree[leftChild(index)].getElement().compareTo(tree[rightChild(index)].getElement()) > 0)) {
				Node<T> tmpNode = tree[index];
				tree[index] = tree[rightChild(index)];
				tree[rightChild(index)] = tmpNode;
				maxHeapifyDown(rightChild(index));
			}
		}
	}

	private int parent(int index) {
		return ((index + 1) / 2) - 1;
	}
	
	private int leftChild(int index) {
		return ((index+1) * 2) - 1;
	}
	
	private int rightChild(int index) {
		return ((index+1) * 2);
	}
	
	private void growArray(){
		Node[] newTree = new Node[tree.length * 2];
		for (int i = 0; i < size; i++){
			newTree[i] = tree[i];
		}
		tree = newTree;
	}

	private class Node<S> {
		private Comparable element;

		public Node(Comparable element) {
			this.element = element;
		}

		public Comparable getElement() {
			return element;
		}

	}

}

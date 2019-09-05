package p1;
import java.util.NoSuchElementException;

public class Cache<T> {
	private int size;
	private Node<T> top;
	public Cache(int size){
		this.size = size;
		top = null;
	}
	
	public T getObject(T target){
		T foundObject = null;
		Node<T> nextNode = top;
		int index = 0;
		while (nextNode != null  && index < size) {
			if (nextNode.getData().equals(target)){
				foundObject = nextNode.getData();
				break;
			} else {
				nextNode = nextNode.getNext();
				index++;
			}
		}
		if (foundObject == null) {
			throw new NoSuchElementException("Cache does not contain given object.");
		}
		return foundObject;
	}
	
	public void addObject(T newItem){
		Node<T> newNode = new Node<T>(newItem, top);
		top = newNode;
	}
	
	public T removeObject(T target){
		T foundObject = null;
		Node<T> nextNode = top;
		Node<T> prevNode = null;
		int index = 0;
		while (nextNode != null  && index < size) {
			if (nextNode.getData().equals(target)){
				foundObject = nextNode.getData();
				if (nextNode.getNext() != null && prevNode != null){
					prevNode.setNext(nextNode.getNext());
					nextNode = nextNode.getNext();
				}
				index++;
			} else {
				prevNode = nextNode;
				nextNode = nextNode.getNext();
				index++;
			}
		}
		if (foundObject == null) {
			throw new NoSuchElementException("Cache does not contain given object.");
		}
		return foundObject;
	}
	
	public void clearCache(){
		top = null;
	}
	
	private class Node<U> {
		private U data;
		private Node<U> next;
		public Node(U data, Node<U> next){
			this.data = data;
			this.next = next;
		}
		public U getData(){
			return this.data;
		}
		public Node<U> getNext(){
			return this.next;
		}
		public void setNext(Node<U> newNext){
			this.next = newNext;
		}
	}
}

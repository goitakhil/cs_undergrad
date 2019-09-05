package p2;

public class HeapTest {
	public static void main(String[] args){
		MaxHeap<Integer> heap = new MaxHeap<Integer>();
		heap.addObject(new Integer(10));
		heap.addObject(new Integer(20));
		heap.addObject(new Integer(40));
		heap.addObject(new Integer(25));
		heap.addObject(new Integer(30));
		heap.addObject(new Integer(31));
		heap.addObject(new Integer(50));
		heap.addObject(new Integer(1));
		for (int i = 0; i < heap.getSize(); i++){
			System.out.println("" + heap.getObject(i));
		}
	}
}

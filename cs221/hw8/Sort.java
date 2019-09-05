
import java.util.*;

/**
 * Class for sorting a list that implements the DoubleLinkedListADT interface
 * using either natural order or a Comparator.
 *
 * @author CS221 - Spring 2016
 */
public class Sort {
	/**
	 * Sorts a list that implements the DoubleLinkedListADT using the natural
	 * ordering of list elements. DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The data type in the list must extend Comparable
	 * @param list
	 *            The list that will be sorted
	 * @see DoubleLinkedListADT
	 */
	public static <T extends Comparable<T>> void sort(DoubleLinkedListADT<T> list) {
		if (list.size() == 0 || list.size() == 1)
			return; // return if the list is sorted.

		int midpoint = list.size() / 2; // set midpoint
		ListIterator<T> listIter = list.listIterator();
		DoubleLinkedListADT<T> list1 = new WrappedDLL<T>(); // create
																	// sub-lists
		DoubleLinkedListADT<T> list2 = new WrappedDLL<T>();

		for (int n = 0; n < midpoint; n++) { // fill sub-lists
			list1.add(listIter.next());
		}
		while (listIter.hasNext()) {
			list2.add(listIter.next());
		}

		sort(list1);
		sort(list2);
		DoubleLinkedListADT<T> workingList = merge(list1, list2);
		ListIterator<T> oldListIterator = list.listIterator();
		ListIterator<T> newListIterator = workingList.listIterator();
		while (oldListIterator.hasNext()){
			oldListIterator.next();
			oldListIterator.set(newListIterator.next());
		}

	}

	private static <T extends Comparable<T>> DoubleLinkedListADT<T> merge(DoubleLinkedListADT<T> list1,
			DoubleLinkedListADT<T> list2) {
		DoubleLinkedListADT<T> mergedList = new WrappedDLL<T>();
		ListIterator<T> list1iterator = list1.listIterator();
		ListIterator<T> list2iterator = list2.listIterator();
		T list1current = list1iterator.next();
		T list2current = list2iterator.next();
		boolean list1empty = false;
		boolean list2empty = false;
		
		for (int n = 0; n < (list1.size() + list2.size()); n++){
			if (list1empty && !list2empty){
				mergedList.add(list2current);
				if (list2iterator.hasNext()){
					list2current = list2iterator.next();
				} else {
					list2empty = true;
				}
			} else if (list2empty && !list1empty){
				mergedList.add(list1current);
				if (list1iterator.hasNext()){
					list1current = list1iterator.next();
				} else {
					list1empty = true;
				}
			} else if (list1empty && list2empty) {
				break;
			} else {
			
			if (list1current.compareTo(list2current) < 0){
				mergedList.add(list1current);
				if (list1iterator.hasNext()){
					list1current = list1iterator.next();
				} else {
					list1empty = true;
				}
			} else if (list1current.compareTo(list2current) > 0){
				mergedList.add(list2current);
				if (list2iterator.hasNext()){
					list2current = list2iterator.next();
				} else {
					list2empty = true;
				}
			} else {
				mergedList.add(list1current);
				if (list1iterator.hasNext()){
					list1current = list1iterator.next();
				} else {
					list1empty = true;
				}
			}
			}
			
		}
		
		return mergedList;
	}

	/**
	 * Sorts a DoubleLinkedListADT with the provided Comparator. DO NOT MODIFY
	 * THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The type of list to sort
	 * @param list
	 *            The list to sort
	 * @param c
	 *            The Comparator to use
	 * @see DoubleLinkedListADT
	 */
	public static <T> void sort(DoubleLinkedListADT<T> list, Comparator<T> c) {
		if (list.size() == 0 || list.size() == 1)
			return; // return if the list is sorted.

		int midpoint = list.size() / 2; // set midpoint
		ListIterator<T> listIter = list.listIterator();
		DoubleLinkedListADT<T> list1 = new WrappedDLL<T>(); // create
																	// sub-lists
		DoubleLinkedListADT<T> list2 = new WrappedDLL<T>();

		for (int n = 0; n < midpoint; n++) { // fill sub-lists
			list1.add(listIter.next());
		}
		while (listIter.hasNext()) {
			list2.add(listIter.next());
		}

		sort(list1, c);
		sort(list2, c);
		DoubleLinkedListADT<T> workingList = merge(list1, list2, c);
		ListIterator<T> oldListIterator = list.listIterator();
		ListIterator<T> newListIterator = workingList.listIterator();
		while (oldListIterator.hasNext()){
			oldListIterator.next();
			oldListIterator.set(newListIterator.next());
		}

	}

	
	
	private static <T> DoubleLinkedListADT<T> merge(DoubleLinkedListADT<T> list1,
			DoubleLinkedListADT<T> list2, Comparator<T> c) {
		DoubleLinkedListADT<T> mergedList = new WrappedDLL<T>();
		ListIterator<T> list1iterator = list1.listIterator();
		ListIterator<T> list2iterator = list2.listIterator();
		T list1current = list1iterator.next();
		T list2current = list2iterator.next();
		boolean list1empty = false;
		boolean list2empty = false;
		
		for (int n = 0; n < (list1.size() + list2.size()); n++){
			if (list1empty && !list2empty){
				mergedList.add(list2current);
				if (list2iterator.hasNext()){
					list2current = list2iterator.next();
				} else {
					list2empty = true;
				}
			} else if (list2empty && !list1empty){
				mergedList.add(list1current);
				if (list1iterator.hasNext()){
					list1current = list1iterator.next();
				} else {
					list1empty = true;
				}
			} else if (list1empty && list2empty) {
				break;
			} else {
			
			if (c.compare(list1current, list2current) < 0){
				mergedList.add(list1current);
				if (list1iterator.hasNext()){
					list1current = list1iterator.next();
				} else {
					list1empty = true;
				}
			} else if (c.compare(list1current, list2current) > 0){
				mergedList.add(list2current);
				if (list2iterator.hasNext()){
					list2current = list2iterator.next();
				} else {
					list2empty = true;
				}
			} else {
				mergedList.add(list1current);
				if (list1iterator.hasNext()){
					list1current = list1iterator.next();
				} else {
					list1empty = true;
				}
			}
			}
			
		}
		return mergedList;
}
}

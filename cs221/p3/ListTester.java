import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A unit test class for lists that implement IndexedUnorderedListADT This is a
 * set of black box tests that should work for any implementation of this
 * interface.
 * 
 * NOTE: One example test is given for each interface method using a new list to
 * get you started.
 * 
 * @author mvail, mhthomas
 * @author tbevan - added new test scenarios
 */
public class ListTester {
	// possible lists that could be tested
	private enum ListToUse {
		goodList, badList, IUArrayList, singleLinkedList, doubleLinkedList
	};

	private final ListToUse LIST_TO_USE = ListToUse.doubleLinkedList;

	// possible results expected in tests
	private enum Result {
		EmptyCollection, ElementNotFound, IndexOutOfBounds, IllegalState, ConcurrentModification, NoSuchElement, NoException, UnexpectedException, True, False, Pass, Fail, MatchingValue, ValidString
	};

	// named elements for use in tests
	private static final Integer ELEMENT_A = new Integer(1);
	private static final Integer ELEMENT_B = new Integer(2);
	private static final Integer ELEMENT_C = new Integer(3);
	private static final Integer ELEMENT_D = new Integer(4);

	// instance variables for tracking test results
	private int passes = 0;
	private int failures = 0;
	private int total = 0;

	/**
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {
		// to avoid every method being static
		ListTester tester = new ListTester();
		tester.runTests();
	}

	/**
	 * Print test results in a consistent format
	 * 
	 * @param testDesc
	 *            description of the test
	 * @param result
	 *            indicates if the test passed or failed
	 */
	private void printTest(String testDesc, boolean result) {
		total++;
		if (result) {
			passes++;
		} else {
			failures++;
		}
		System.out.printf("%-46s\t%s\n", testDesc, (result ? "   PASS" : "***FAIL***"));
	}

	/** Print a final summary */
	private void printFinalSummary() {
		System.out.printf("\nTotal Tests: %d,  Passed: %d,  Failed: %d\n", total, passes, failures);
	}

	/**
	 * Run tests to confirm required functionality from list constructors and
	 * methods
	 */
	private void runTests() {
		// recommended scenario naming: start_change_result
		test_newList(); // aka noList_constructor_emptyList
		test_emptyList_addToFrontA_A(); // case 2
		test_emptyList_addToRearA_A(); // case 3
		test_emptyList_addA_A(); // case 4
		test_emptyList_add0A_A(); // case 5
		test_A_removeFirst_emptyList(); // case 6
		test_A_removeLast_emptyList(); // case 7
		test_A_removeA_emptyList(); // case 8
		test_A_addToRearB_AB(); // case 10
		test_A_remove0_emptyList(); // case 12
		test_A_set0B_B(); // case 13
		test_AB_removeFirst_B(); // case 16
		test_AB_removeLast_A(); // case 17
		test_AB_removeA_B(); // case 18
		test_AB_removeB_A(); // case 19
		test_AB_remove0_B(); // case 24
		test_AB_remove1_A(); // case 25
		test_AB_addC_ABC(); // case 28
		test_AB_add0C_CAB(); // case 29
		test_ABC_removeLast_AB(); // case 33
		test_ABC_removeA_BC(); // case 34

		// List Iterator Tests

		test_A_InextIremove_emptyList();
		test_AB_InextIremove_B();
		test_AB_InextInextIremove_A();
		test_ABC_InextIremove_BC();
		test_ABC_InextInextIremove_AC();
		test_ABC_InextInextInextIremove_AB();

		test_A_InextIpreviousIremove_emptyList();
		test_AB_InextIpreviousIremove_B();
		test_AB_InextInextIpreviousIremove_A();
		// test_ABC_InextIpreviousIremove_BC();
		// test_ABC_InextInextIpreviousIremove_AC();
		// test_ABC_InextInextInextIpreviousIremove_AB();
		// test_emptyList_IaddA_A();
		// test_A_IaddB_BA();
		// test_A_InextIaddB_AB();
		// test_A_InextIpreviousIaddB_BA(); // 16
		// test_AB_IaddC_CAB();
		test_AB_InextIaddC_ACB();
		test_AB_InextInextIaddC_ABC();
		test_AB_InextIpreviousIaddC_CAB();
		// test_AB_InextInextIpreviousIaddC_ACB(); //21
		test_A_InextIsetB_B();
		// test_A_InextIpreviousIsetB_B();
		// test_AB_InextIsetC_CB();
		// test_AB_InextInextIsetC_AC();
		// test_AB_InextIpreviousIsetC_CB();
		// test_AB_InextInextIpreviousIsetC_AC();
		// test_ABC_InextIsetD_DBC();
		// test_ABC_InextInextIsetD_ADC();
		// test_ABC_InextInextInextIsetD_ABD();
		// test_ABC_InextIpreviousIsetD_DBC();
		// test_ABC_InextInextIpreviousIsetD_ADC();
		// test_ABC_InextInextInextIpreviousIsetD_ABD();

		// report final verdict
		printFinalSummary();
	}

	//////////////////////////////////////
	// SCENARIO: NEW EMPTY LIST
	//////////////////////////////////////

	/**
	 * Returns a IndexedUnorderedListADT for the "new empty list" scenario.
	 * Scenario: no list -> constructor -> [ ]
	 * 
	 * @return a new, empty IndexedUnorderedListADT
	 */
	private DoubleLinkedListADT<Integer> newList() {
		DoubleLinkedListADT<Integer> listToUse;
		switch (LIST_TO_USE) {
		// case goodList:
		// listToUse = new GoodList<Integer>();
		// break;
		// case badList:
		// listToUse = new BadList<Integer>();
		// break;
		// case IUArrayList:
		// listToUse = new IUArrayList<Integer>();
		// break;
		// case singleLinkedList:
		// listToUse = new IUDoubleLinkedList<Integer>();
		// break;
		case doubleLinkedList:
			listToUse = new IUDoubleLinkedList<Integer>();
			break;
		default:
			listToUse = null;
		}
		return listToUse;
	}

	/** Run all tests on scenario: no list -> constructor -> [ ] */
	private void test_newList() {
		// recommended test naming: start_change_result_testName
		// e.g. A_addToFront_BA_testSize
		// AB_addC1_ACB_testFirst
		// A_remove0_empty_testLast

		// try-catch is necessary to prevent an Exception from the scenario
		// builder method from bringing
		// down the whole test suite
		try {
			// ListADT
			printTest("newList_testRemoveFirst", testRemoveFirst(newList(), null, Result.EmptyCollection));
			printTest("newList_testRemoveLast", testRemoveLast(newList(), null, Result.EmptyCollection));
			printTest("newList_testRemoveA", testRemoveElement(newList(), null, Result.ElementNotFound));
			printTest("newList_testFirst", testFirst(newList(), null, Result.EmptyCollection));
			printTest("newList_testLast", testLast(newList(), null, Result.EmptyCollection));
			printTest("newList_testContainsA", testContains(newList(), ELEMENT_A, Result.False));
			printTest("newList_testIsEmpty", testIsEmpty(newList(), Result.True));
			printTest("newList_testSize", testSize(newList(), 0));
			printTest("newList_testToString", testToString(newList(), Result.ValidString));
			// UnorderedListADT
			printTest("newList_testAddToFrontA", testAddToFront(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testAddToRearA", testAddToRear(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testAddAfterBA", testAddAfter(newList(), ELEMENT_B, ELEMENT_A, Result.ElementNotFound));
			// IndexedListADT
			printTest("newList_testAddAtIndexNeg1", testAddAtIndex(newList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testAddAtIndex0", testAddAtIndex(newList(), 0, ELEMENT_A, Result.NoException));
			printTest("newList_testAddAtIndex1", testAddAtIndex(newList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testSetNeg1A", testSet(newList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testSet0A", testSet(newList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testAddA", testAdd(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testGetNeg1", testGet(newList(), -1, null, Result.IndexOutOfBounds));
			printTest("newList_testGet0", testGet(newList(), 0, null, Result.IndexOutOfBounds));
			printTest("newList_testIndexOfA", testIndexOf(newList(), ELEMENT_A, -1));
			printTest("newList_testRemoveNeg1", testRemoveIndex(newList(), -1, null, Result.IndexOutOfBounds));
			printTest("newList_testRemove0", testRemoveIndex(newList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("newList_testIterator", testIterator(newList(), Result.NoException));
			printTest("newList_testIteratorHasNext", testIteratorHasNext(newList().iterator(), Result.False));
			printTest("newList_testIteratorNext", testIteratorNext(newList().iterator(), null, Result.NoSuchElement));
			printTest("newList_testIteratorRemove", testIteratorRemove(newList().iterator(), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_newList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [ ] -> addToFront(A) -> [A]
	////////////////////////////////////////////////

	/**
	 * Scenario: empty list -> addToFront(A) -> [A]
	 * 
	 * @return [A] after addToFront(A)
	 */
	private IndexedUnorderedListADT<Integer> emptyList_addToFrontA_A() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.addToFront(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: empty list -> addToFront(A) -> [A] */
	private void test_emptyList_addToFrontA_A() {
		// recommended test naming: start_change_result_testName
		// e.g. A_addToFront_BA_testSize
		// AB_addC1_ACB_testFirst
		// A_remove0_empty_testLast

		// try-catch is necessary to prevent an Exception from the scenario
		// builder method from bringing
		// down the whole test suite
		try {
			// ListADT
			printTest("emptyList_addToFrontA_A_testRemoveFirst",
					testRemoveFirst(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveLast",
					testRemoveLast(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveA",
					testRemoveElement(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveB",
					testRemoveElement(emptyList_addToFrontA_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addToFrontA_A_testFirst",
					testFirst(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testLast",
					testLast(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testContainsA",
					testContains(emptyList_addToFrontA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addToFrontA_A_testContainsB",
					testContains(emptyList_addToFrontA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addToFrontA_A_testIsEmpty", testIsEmpty(emptyList_addToFrontA_A(), Result.False));
			printTest("emptyList_addToFrontA_A_testSize", testSize(emptyList_addToFrontA_A(), 1));
			printTest("emptyList_addToFrontA_A_testToString",
					testToString(emptyList_addToFrontA_A(), Result.ValidString));
			// UnorderedListADT
			printTest("emptyList_addToFrontA_A_testAddToFrontB",
					testAddToFront(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddToRearB",
					testAddToRear(emptyList_addToFrontA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAfterCB",
					testAddAfter(emptyList_addToFrontA_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addToFrontA_A_testAddAfterAB",
					testAddAfter(emptyList_addToFrontA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("emptyList_addToFrontA_A_testAddAtIndexNeg1B",
					testAddAtIndex(emptyList_addToFrontA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testAddAtIndex0B",
					testAddAtIndex(emptyList_addToFrontA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAtIndex1B",
					testAddAtIndex(emptyList_addToFrontA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testSetNeg1B",
					testSet(emptyList_addToFrontA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testSet0B",
					testSet(emptyList_addToFrontA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddB",
					testAdd(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testGetNeg1",
					testGet(emptyList_addToFrontA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testGet0",
					testGet(emptyList_addToFrontA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testIndexOfA", testIndexOf(emptyList_addToFrontA_A(), ELEMENT_A, 0));
			printTest("emptyList_addToFrontA_A_testIndexOfB", testIndexOf(emptyList_addToFrontA_A(), ELEMENT_B, -1));
			printTest("emptyList_addToFrontA_A_testRemoveNeg1",
					testRemoveIndex(emptyList_addToFrontA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testRemove0",
					testRemoveIndex(emptyList_addToFrontA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemove1",
					testRemoveIndex(emptyList_addToFrontA_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("emptyList_addToFrontA_A_testIterator",
					testIterator(emptyList_addToFrontA_A(), Result.NoException));
			printTest("emptyList_addToFrontA_A_testIteratorHasNext",
					testIteratorHasNext(emptyList_addToFrontA_A().iterator(), Result.True));
			printTest("emptyList_addToFrontA_A_testIteratorNext",
					testIteratorNext(emptyList_addToFrontA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testIteratorRemove",
					testIteratorRemove(emptyList_addToFrontA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(emptyList_addToFrontA_A(), 1), Result.False));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(emptyList_addToFrontA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(emptyList_addToFrontA_A(), 1), Result.NoException));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(emptyList_addToFrontA_A(), 1)), Result.False));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(emptyList_addToFrontA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(emptyList_addToFrontA_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [ ] -> addToRear(A) -> [A]
	//
	////////////////////////////////////////////////

	/**
	 * Scenario: empty list -> addToRear(A) -> [A]
	 * 
	 * @return [A] after addToRear(A)
	 */
	private IndexedUnorderedListADT<Integer> emptyList_addToRearA_A() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.addToRear(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: empty list -> addToRear(A) -> [A] */
	private void test_emptyList_addToRearA_A() {

		try {
			// ListADT
			printTest("emptyList_addToRearA_A_testRemoveFirst",
					testRemoveFirst(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveLast",
					testRemoveLast(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveA",
					testRemoveElement(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveB",
					testRemoveElement(emptyList_addToRearA_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addToRearA_A_testFirst",
					testFirst(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testLast",
					testLast(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testContainsA",
					testContains(emptyList_addToRearA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addToRearA_A_testContainsB",
					testContains(emptyList_addToRearA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addToRearA_A_testIsEmpty", testIsEmpty(emptyList_addToRearA_A(), Result.False));
			printTest("emptyList_addToRearA_A_testSize", testSize(emptyList_addToRearA_A(), 1));
			printTest("emptyList_addToRearA_A_testToString",
					testToString(emptyList_addToRearA_A(), Result.ValidString));
			// UnorderedListADT
			printTest("emptyList_addToRearA_A_testAddToFrontB",
					testAddToFront(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddToRearB",
					testAddToRear(emptyList_addToRearA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAfterCB",
					testAddAfter(emptyList_addToRearA_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addToRearA_A_testAddAfterAB",
					testAddAfter(emptyList_addToRearA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("emptyList_addToRearA_A_testAddAtIndexNeg1B",
					testAddAtIndex(emptyList_addToRearA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testAddAtIndex0B",
					testAddAtIndex(emptyList_addToRearA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAtIndex1B",
					testAddAtIndex(emptyList_addToRearA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testSetNeg1B",
					testSet(emptyList_addToRearA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testSet0B",
					testSet(emptyList_addToRearA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddB",
					testAdd(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testGetNeg1",
					testGet(emptyList_addToRearA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testGet0",
					testGet(emptyList_addToRearA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testIndexOfA", testIndexOf(emptyList_addToRearA_A(), ELEMENT_A, 0));
			printTest("emptyList_addToRearA_A_testIndexOfB", testIndexOf(emptyList_addToRearA_A(), ELEMENT_B, -1));
			printTest("emptyList_addToRearA_A_testRemoveNeg1",
					testRemoveIndex(emptyList_addToRearA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testRemove0",
					testRemoveIndex(emptyList_addToRearA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemove1",
					testRemoveIndex(emptyList_addToRearA_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("emptyList_addToRearA_A_testIterator",
					testIterator(emptyList_addToRearA_A(), Result.NoException));
			printTest("emptyList_addToRearA_A_testIteratorHasNext",
					testIteratorHasNext(emptyList_addToRearA_A().iterator(), Result.True));
			printTest("emptyList_addToRearA_A_testIteratorNext",
					testIteratorNext(emptyList_addToRearA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testIteratorRemove",
					testIteratorRemove(emptyList_addToRearA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addToRearA_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(emptyList_addToRearA_A(), 1), Result.False));
			printTest("emptyList_addToRearA_A_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(emptyList_addToRearA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(emptyList_addToRearA_A(), 1), Result.NoException));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(emptyList_addToRearA_A(), 1)), Result.False));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(emptyList_addToRearA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(emptyList_addToRearA_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [ ] -> add(A) -> [A]
	//
	////////////////////////////////////////////////

	/**
	 * Scenario: empty list -> add(A) -> [A]
	 * 
	 * @return [A] after add(A)
	 */
	private IndexedUnorderedListADT<Integer> emptyList_addA_A() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: empty list -> add(A) -> [A] */
	private void test_emptyList_addA_A() {

		try {
			// ListADT
			printTest("emptyList_addA_A_testRemoveFirst",
					testRemoveFirst(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveLast",
					testRemoveLast(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveA",
					testRemoveElement(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveB",
					testRemoveElement(emptyList_addA_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addA_A_testFirst", testFirst(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testLast", testLast(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testContainsA", testContains(emptyList_addA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addA_A_testContainsB", testContains(emptyList_addA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addA_A_testIsEmpty", testIsEmpty(emptyList_addA_A(), Result.False));
			printTest("emptyList_addA_A_testSize", testSize(emptyList_addA_A(), 1));
			printTest("emptyList_addA_A_testToString", testToString(emptyList_addA_A(), Result.ValidString));
			// UnorderedListADT
			printTest("emptyList_addA_A_testAddToFrontB",
					testAddToFront(emptyList_addA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddToRearB",
					testAddToRear(emptyList_addA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addA_A_testAddAfterCB",
					testAddAfter(emptyList_addA_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_addA_A_testAddAfterAB",
					testAddAfter(emptyList_addA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("emptyList_addA_A_testAddAtIndexNeg1B",
					testAddAtIndex(emptyList_addA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testAddAtIndex0B",
					testAddAtIndex(emptyList_addA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddAtIndex1B",
					testAddAtIndex(emptyList_addA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testSetNeg1B",
					testSet(emptyList_addA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testSet0B", testSet(emptyList_addA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddB", testAdd(emptyList_addA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testGetNeg1", testGet(emptyList_addA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testGet0", testGet(emptyList_addA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testIndexOfA", testIndexOf(emptyList_addA_A(), ELEMENT_A, 0));
			printTest("emptyList_addA_A_testIndexOfB", testIndexOf(emptyList_addA_A(), ELEMENT_B, -1));
			printTest("emptyList_addA_A_testRemoveNeg1",
					testRemoveIndex(emptyList_addA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testRemove0",
					testRemoveIndex(emptyList_addA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemove1",
					testRemoveIndex(emptyList_addA_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("emptyList_addA_A_testIterator", testIterator(emptyList_addA_A(), Result.NoException));
			printTest("emptyList_addA_A_testIteratorHasNext",
					testIteratorHasNext(emptyList_addA_A().iterator(), Result.True));
			printTest("emptyList_addA_A_testIteratorNext",
					testIteratorNext(emptyList_addA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testIteratorRemove",
					testIteratorRemove(emptyList_addA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addA_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(emptyList_addA_A(), 1), Result.False));
			printTest("emptyList_addA_A_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(emptyList_addA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addA_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(emptyList_addA_A(), 1), Result.NoException));
			printTest("emptyList_addA_A_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(emptyList_addA_A(), 1)), Result.False));
			printTest("emptyList_addA_A_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(emptyList_addA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addA_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(emptyList_addA_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [ ] -> add(0, A) -> [A]
	//
	////////////////////////////////////////////////

	/**
	 * Scenario: empty list -> add(0, A) -> [A]
	 * 
	 * @return [A] after add(0, A)
	 */
	private IndexedUnorderedListADT<Integer> emptyList_add0A_A() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(0, ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: empty list -> add(0, A) -> [A] */
	private void test_emptyList_add0A_A() {

		try {
			// ListADT
			printTest("emptyList_add0A_A_testRemoveFirst",
					testRemoveFirst(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveLast",
					testRemoveLast(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveA",
					testRemoveElement(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveB",
					testRemoveElement(emptyList_add0A_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_add0A_A_testFirst", testFirst(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testLast", testLast(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testContainsA", testContains(emptyList_add0A_A(), ELEMENT_A, Result.True));
			printTest("emptyList_add0A_A_testContainsB", testContains(emptyList_add0A_A(), ELEMENT_B, Result.False));
			printTest("emptyList_add0A_A_testIsEmpty", testIsEmpty(emptyList_add0A_A(), Result.False));
			printTest("emptyList_add0A_A_testSize", testSize(emptyList_add0A_A(), 1));
			printTest("emptyList_add0A_A_testToString", testToString(emptyList_add0A_A(), Result.ValidString));
			// UnorderedListADT
			printTest("emptyList_add0A_A_testAddToFrontB",
					testAddToFront(emptyList_add0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddToRearB",
					testAddToRear(emptyList_add0A_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_add0A_A_testAddAfterCB",
					testAddAfter(emptyList_add0A_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("emptyList_add0A_A_testAddAfterAB",
					testAddAfter(emptyList_add0A_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("emptyList_add0A_A_testAddAtIndexNeg1B",
					testAddAtIndex(emptyList_add0A_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testAddAtIndex0B",
					testAddAtIndex(emptyList_add0A_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddAtIndex1B",
					testAddAtIndex(emptyList_add0A_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testSetNeg1B",
					testSet(emptyList_add0A_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testSet0B", testSet(emptyList_add0A_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddB", testAdd(emptyList_add0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testGetNeg1", testGet(emptyList_add0A_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testGet0", testGet(emptyList_add0A_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testIndexOfA", testIndexOf(emptyList_add0A_A(), ELEMENT_A, 0));
			printTest("emptyList_add0A_A_testIndexOfB", testIndexOf(emptyList_add0A_A(), ELEMENT_B, -1));
			printTest("emptyList_add0A_A_testRemoveNeg1",
					testRemoveIndex(emptyList_add0A_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testRemove0",
					testRemoveIndex(emptyList_add0A_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemove1",
					testRemoveIndex(emptyList_add0A_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("emptyList_add0A_A_testIterator", testIterator(emptyList_add0A_A(), Result.NoException));
			printTest("emptyList_add0A_A_testIteratorHasNext",
					testIteratorHasNext(emptyList_add0A_A().iterator(), Result.True));
			printTest("emptyList_add0A_A_testIteratorNext",
					testIteratorNext(emptyList_add0A_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testIteratorRemove",
					testIteratorRemove(emptyList_add0A_A().iterator(), Result.IllegalState));
			printTest("emptyList_add0A_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(emptyList_add0A_A(), 1), Result.False));
			printTest("emptyList_add0A_A_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(emptyList_add0A_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_add0A_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(emptyList_add0A_A(), 1), Result.NoException));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(emptyList_add0A_A(), 1)), Result.False));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(emptyList_add0A_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(emptyList_add0A_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A] -> removeFirst() -> [ ]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A] -> removeFirst() -> [ ]
	 * 
	 * @return [ ] after removeFirst()
	 */
	private IndexedUnorderedListADT<Integer> A_removeFirst_emptyList() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.removeFirst();
		return list;
	}

	/** Run all tests on scenario: [A] -> removeFirst() -> [ ] */
	private void test_A_removeFirst_emptyList() {

		try {
			// ListADT
			printTest("A_removeFirst_emptyList_testRemoveFirst",
					testRemoveFirst(A_removeFirst_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeFirst_emptyList_testRemoveLast",
					testRemoveLast(A_removeFirst_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeFirst_emptyList_testRemoveA",
					testRemoveElement(A_removeFirst_emptyList(), null, Result.ElementNotFound));
			printTest("A_removeFirst_emptyList_testFirst",
					testFirst(A_removeFirst_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeFirst_emptyList_testLast",
					testLast(A_removeFirst_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeFirst_emptyList_testContainsA",
					testContains(A_removeFirst_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeFirst_emptyList_testIsEmpty", testIsEmpty(A_removeFirst_emptyList(), Result.True));
			printTest("A_removeFirst_emptyList_testSize", testSize(A_removeFirst_emptyList(), 0));
			printTest("A_removeFirst_emptyList_testToString",
					testToString(A_removeFirst_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_removeFirst_emptyList_testAddToFrontA",
					testAddToFront(A_removeFirst_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeFirst_emptyList_testAddToRearA",
					testAddToRear(A_removeFirst_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeFirst_emptyList_testAddAfterBA",
					testAddAfter(A_removeFirst_emptyList(), ELEMENT_B, ELEMENT_A, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_removeFirst_emptyList_testAddAtIndexNeg1",
					testAddAtIndex(A_removeFirst_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testAddAtIndex0",
					testAddAtIndex(A_removeFirst_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_removeFirst_emptyList_testAddAtIndex1",
					testAddAtIndex(A_removeFirst_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testSetNeg1A",
					testSet(A_removeFirst_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testSet0A",
					testSet(A_removeFirst_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testAddA",
					testAdd(A_removeFirst_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeFirst_emptyList_testGetNeg1",
					testGet(A_removeFirst_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testGet0",
					testGet(A_removeFirst_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testIndexOfA", testIndexOf(A_removeFirst_emptyList(), ELEMENT_A, -1));
			printTest("A_removeFirst_emptyList_testRemoveNeg1",
					testRemoveIndex(A_removeFirst_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testRemove0",
					testRemoveIndex(A_removeFirst_emptyList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_removeFirst_emptyList_testIterator",
					testIterator(A_removeFirst_emptyList(), Result.NoException));
			printTest("A_removeFirst_emptyList_testIteratorHasNext",
					testIteratorHasNext(A_removeFirst_emptyList().iterator(), Result.False));
			printTest("A_removeFirst_emptyList_testIteratorNext",
					testIteratorNext(A_removeFirst_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_removeFirst_emptyList_testIteratorRemove",
					testIteratorRemove(A_removeFirst_emptyList().iterator(), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_removeFirst_emptyList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A] -> removeLast() -> [ ]
	//
	////////////////////////////////////////////////

	/**
	 * Scenario: [A] -> removeLast() -> [ ]
	 * 
	 * @return [ ] after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> A_removeLast_emptyList() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.removeLast();
		return list;
	}

	/** Run all tests on scenario: [A] -> removeLast() -> [ ] */
	private void test_A_removeLast_emptyList() {

		try {
			// ListADT
			printTest("A_removeLast_emptyList_testRemoveFirst",
					testRemoveFirst(A_removeLast_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeLast_emptyList_testRemoveLast",
					testRemoveLast(A_removeLast_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeLast_emptyList_testRemoveA",
					testRemoveElement(A_removeLast_emptyList(), null, Result.ElementNotFound));
			printTest("A_removeLast_emptyList_testFirst",
					testFirst(A_removeLast_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeLast_emptyList_testLast",
					testLast(A_removeLast_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeLast_emptyList_testContainsA",
					testContains(A_removeLast_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeLast_emptyList_testIsEmpty", testIsEmpty(A_removeLast_emptyList(), Result.True));
			printTest("A_removeLast_emptyList_testSize", testSize(A_removeLast_emptyList(), 0));
			printTest("A_removeLast_emptyList_testToString",
					testToString(A_removeLast_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_removeLast_emptyList_testAddToFrontA",
					testAddToFront(A_removeLast_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeLast_emptyList_testAddToRearA",
					testAddToRear(A_removeLast_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeLast_emptyList_testAddAfterBA",
					testAddAfter(A_removeLast_emptyList(), ELEMENT_B, ELEMENT_A, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_removeLast_emptyList_testAddAtIndexNeg1",
					testAddAtIndex(A_removeLast_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testAddAtIndex0",
					testAddAtIndex(A_removeLast_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_removeLast_emptyList_testAddAtIndex1",
					testAddAtIndex(A_removeLast_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testSetNeg1A",
					testSet(A_removeLast_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testSet0A",
					testSet(A_removeLast_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testAddA",
					testAdd(A_removeLast_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeLast_emptyList_testGetNeg1",
					testGet(A_removeLast_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testGet0",
					testGet(A_removeLast_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testIndexOfA", testIndexOf(A_removeLast_emptyList(), ELEMENT_A, -1));
			printTest("A_removeLast_emptyList_testRemoveNeg1",
					testRemoveIndex(A_removeLast_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testRemove0",
					testRemoveIndex(A_removeLast_emptyList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_removeLast_emptyList_testIterator",
					testIterator(A_removeLast_emptyList(), Result.NoException));
			printTest("A_removeLast_emptyList_testIteratorHasNext",
					testIteratorHasNext(A_removeLast_emptyList().iterator(), Result.False));
			printTest("A_removeLast_emptyList_testIteratorNext",
					testIteratorNext(A_removeLast_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_removeLast_emptyList_testIteratorRemove",
					testIteratorRemove(A_removeLast_emptyList().iterator(), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_removeLast_emptyList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A] -> remove(A) -> [ ]
	//
	////////////////////////////////////////////////

	/**
	 * Scenario: [A] -> remove(A) -> [ ]
	 * 
	 * @return [ ] after remove(A)
	 */
	private IndexedUnorderedListADT<Integer> A_removeA_emptyList() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.remove(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: [A] -> remove(A) -> [ ] */
	private void test_A_removeA_emptyList() {

		try {
			// ListADT
			printTest("A_removeA_emptyList_testRemoveFirst",
					testRemoveFirst(A_removeA_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeA_emptyList_testRemoveLast",
					testRemoveLast(A_removeA_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeA_emptyList_testRemoveA",
					testRemoveElement(A_removeA_emptyList(), null, Result.ElementNotFound));
			printTest("A_removeA_emptyList_testFirst", testFirst(A_removeA_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeA_emptyList_testLast", testLast(A_removeA_emptyList(), null, Result.EmptyCollection));
			printTest("A_removeA_emptyList_testContainsA",
					testContains(A_removeA_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeA_emptyList_testIsEmpty", testIsEmpty(A_removeA_emptyList(), Result.True));
			printTest("A_removeA_emptyList_testSize", testSize(A_removeA_emptyList(), 0));
			printTest("A_removeA_emptyList_testToString", testToString(A_removeA_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_removeA_emptyList_testAddToFrontA",
					testAddToFront(A_removeA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeA_emptyList_testAddToRearA",
					testAddToRear(A_removeA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeA_emptyList_testAddAfterBA",
					testAddAfter(A_removeA_emptyList(), ELEMENT_B, ELEMENT_A, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_removeA_emptyList_testAddAtIndexNeg1",
					testAddAtIndex(A_removeA_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testAddAtIndex0",
					testAddAtIndex(A_removeA_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_removeA_emptyList_testAddAtIndex1",
					testAddAtIndex(A_removeA_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testSetNeg1A",
					testSet(A_removeA_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testSet0A",
					testSet(A_removeA_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testAddA", testAdd(A_removeA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeA_emptyList_testGetNeg1",
					testGet(A_removeA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testGet0", testGet(A_removeA_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testIndexOfA", testIndexOf(A_removeA_emptyList(), ELEMENT_A, -1));
			printTest("A_removeA_emptyList_testRemoveNeg1",
					testRemoveIndex(A_removeA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testRemove0",
					testRemoveIndex(A_removeA_emptyList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_removeA_emptyList_testIterator", testIterator(A_removeA_emptyList(), Result.NoException));
			printTest("A_removeA_emptyList_testIteratorHasNext",
					testIteratorHasNext(A_removeA_emptyList().iterator(), Result.False));
			printTest("A_removeA_emptyList_testIteratorNext",
					testIteratorNext(A_removeA_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_removeA_emptyList_testIteratorRemove",
					testIteratorRemove(A_removeA_emptyList().iterator(), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_removeA_emptyList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A] -> addToRear(B) -> [A,B]
	//
	////////////////////////////////////////////////

	/**
	 * Scenario: [A] -> addToRear(B) -> [A, B]
	 * 
	 * @return [A,B] after addToRear(B)
	 */
	private IndexedUnorderedListADT<Integer> A_addToRearB_AB() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.addToRear(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: empty list -> add(0, A) -> [A] */
	private void test_A_addToRearB_AB() {

		try {
			// ListADT
			printTest("A_addToRearB_AB_testRemoveFirst",
					testRemoveFirst(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveLast",
					testRemoveLast(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveA",
					testRemoveElement(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveB",
					testRemoveElement(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveC",
					testRemoveElement(A_addToRearB_AB(), ELEMENT_C, Result.ElementNotFound));
			printTest("A_addToRearB_AB_testFirst", testFirst(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testLast", testLast(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testContainsA", testContains(A_addToRearB_AB(), ELEMENT_A, Result.True));
			printTest("A_addToRearB_AB_testContainsB", testContains(A_addToRearB_AB(), ELEMENT_B, Result.True));
			printTest("A_addToRearB_AB_testContainsC", testContains(A_addToRearB_AB(), ELEMENT_C, Result.False));
			printTest("A_addToRearB_AB_testIsEmpty", testIsEmpty(A_addToRearB_AB(), Result.False));
			printTest("A_addToRearB_AB_testSize", testSize(A_addToRearB_AB(), 2));
			printTest("A_addToRearB_AB_testToString", testToString(A_addToRearB_AB(), Result.ValidString));
			// UnorderedListADT
			printTest("A_addToRearB_AB_testAddToFrontC",
					testAddToFront(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddToRearC",
					testAddToRear(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddAfterDC",
					testAddAfter(A_addToRearB_AB(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			printTest("A_addToRearB_AB_testAddAfterAC",
					testAddAfter(A_addToRearB_AB(), ELEMENT_A, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("A_addToRearB_AB_testAddAtIndexNeg1C",
					testAddAtIndex(A_addToRearB_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testAddAtIndex0C",
					testAddAtIndex(A_addToRearB_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndex1C",
					testAddAtIndex(A_addToRearB_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testSetNeg1C",
					testSet(A_addToRearB_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testSet0C", testSet(A_addToRearB_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testAddC", testAdd(A_addToRearB_AB(), ELEMENT_C, Result.NoException));
			printTest("A_addToRearB_AB_testGetNeg1", testGet(A_addToRearB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testGet0", testGet(A_addToRearB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testIndexOfA", testIndexOf(A_addToRearB_AB(), ELEMENT_A, 0));
			printTest("A_addToRearB_AB_testIndexOfB", testIndexOf(A_addToRearB_AB(), ELEMENT_B, 1));
			printTest("A_addToRearB_AB_testIndexOfC", testIndexOf(A_addToRearB_AB(), ELEMENT_C, -1));
			printTest("A_addToRearB_AB_testRemoveNeg1",
					testRemoveIndex(A_addToRearB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testRemove0",
					testRemoveIndex(A_addToRearB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemove2",
					testRemoveIndex(A_addToRearB_AB(), 2, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_addToRearB_AB_testIterator", testIterator(A_addToRearB_AB(), Result.NoException));
			printTest("A_addToRearB_AB_testIteratorHasNext",
					testIteratorHasNext(A_addToRearB_AB().iterator(), Result.True));
			printTest("A_addToRearB_AB_testIteratorNext",
					testIteratorNext(A_addToRearB_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testIteratorRemove",
					testIteratorRemove(A_addToRearB_AB().iterator(), Result.IllegalState));
			printTest("A_addToRearB_AB_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(A_addToRearB_AB(), 1), Result.True));
			printTest("A_addToRearB_AB_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(A_addToRearB_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(A_addToRearB_AB(), 1), Result.NoException));
			printTest("A_addToRearB_AB_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(A_addToRearB_AB(), 1)), Result.True));
			printTest("A_addToRearB_AB_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(A_addToRearB_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(A_addToRearB_AB(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A] -> remove(0) -> [ ]
	//
	////////////////////////////////////////////////

	/**
	 * Scenario: [A] -> remove(0) -> [ ]
	 * 
	 * @return [ ] after remove(0)
	 */
	private IndexedUnorderedListADT<Integer> A_remove0_emptyList() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.remove(0);
		return list;
	}

	/** Run all tests on scenario: [A] -> remove(0) -> [ ] */
	private void test_A_remove0_emptyList() {

		try {
			// ListADT
			printTest("A_remove0_emptyList_testRemoveFirst",
					testRemoveFirst(A_remove0_emptyList(), null, Result.EmptyCollection));
			printTest("A_remove0_emptyList_testRemoveLast",
					testRemoveLast(A_remove0_emptyList(), null, Result.EmptyCollection));
			printTest("A_remove0_emptyList_testRemoveA",
					testRemoveElement(A_remove0_emptyList(), null, Result.ElementNotFound));
			printTest("A_remove0_emptyList_testFirst", testFirst(A_remove0_emptyList(), null, Result.EmptyCollection));
			printTest("A_remove0_emptyList_testLast", testLast(A_remove0_emptyList(), null, Result.EmptyCollection));
			printTest("A_remove0_emptyList_testContainsA",
					testContains(A_remove0_emptyList(), ELEMENT_A, Result.False));
			printTest("A_remove0_emptyList_testIsEmpty", testIsEmpty(A_remove0_emptyList(), Result.True));
			printTest("A_remove0_emptyList_testSize", testSize(A_remove0_emptyList(), 0));
			printTest("A_remove0_emptyList_testToString", testToString(A_remove0_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_remove0_emptyList_testAddToFrontA",
					testAddToFront(A_remove0_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_remove0_emptyList_testAddToRearA",
					testAddToRear(A_remove0_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_remove0_emptyList_testAddAfterBA",
					testAddAfter(A_remove0_emptyList(), ELEMENT_B, ELEMENT_A, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_remove0_emptyList_testAddAtIndexNeg1",
					testAddAtIndex(A_remove0_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testAddAtIndex0",
					testAddAtIndex(A_remove0_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_remove0_emptyList_testAddAtIndex1",
					testAddAtIndex(A_remove0_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testSetNeg1A",
					testSet(A_remove0_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testSet0A",
					testSet(A_remove0_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testAddA", testAdd(A_remove0_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_remove0_emptyList_testGetNeg1",
					testGet(A_remove0_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testGet0", testGet(A_remove0_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testIndexOfA", testIndexOf(A_remove0_emptyList(), ELEMENT_A, -1));
			printTest("A_remove0_emptyList_testRemoveNeg1",
					testRemoveIndex(A_remove0_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testRemove0",
					testRemoveIndex(A_remove0_emptyList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_remove0_emptyList_testIterator", testIterator(A_remove0_emptyList(), Result.NoException));
			printTest("A_remove0_emptyList_testIteratorHasNext",
					testIteratorHasNext(A_remove0_emptyList().iterator(), Result.False));
			printTest("A_remove0_emptyList_testIteratorNext",
					testIteratorNext(A_remove0_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_remove0_emptyList_testIteratorRemove",
					testIteratorRemove(A_remove0_emptyList().iterator(), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_remove0_emptyList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A] -> set(0,B) -> [B]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A] -> set(0, B) -> [B]
	 * 
	 * @return [B] after set(0, B)
	 */
	private IndexedUnorderedListADT<Integer> A_set0B_B() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.addToFront(ELEMENT_A);
		list.set(0, ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A] -> set(0,B) -> [B] */
	private void test_A_set0B_B() {

		// try-catch is necessary to prevent an Exception from the scenario
		// builder method from bringing
		// down the whole test suite
		try {
			// ListADT
			printTest("A_set0B_B_testRemoveFirst", testRemoveFirst(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testRemoveLast", testRemoveLast(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testRemoveA", testRemoveElement(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testRemoveB", testRemoveElement(A_set0B_B(), ELEMENT_C, Result.ElementNotFound));
			printTest("A_set0B_B_testFirst", testFirst(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testLast", testLast(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testContainsA", testContains(A_set0B_B(), ELEMENT_B, Result.True));
			printTest("A_set0B_B_testContainsB", testContains(A_set0B_B(), ELEMENT_C, Result.False));
			printTest("A_set0B_B_testIsEmpty", testIsEmpty(A_set0B_B(), Result.False));
			printTest("A_set0B_B_testSize", testSize(A_set0B_B(), 1));
			printTest("A_set0B_B_testToString", testToString(A_set0B_B(), Result.ValidString));
			// UnorderedListADT
			printTest("A_set0B_B_testAddToFrontB", testAddToFront(A_set0B_B(), ELEMENT_C, Result.NoException));
			printTest("A_set0B_B_testAddToRearB", testAddToRear(A_set0B_B(), ELEMENT_B, Result.NoException));
			printTest("A_set0B_B_testAddAfterCB",
					testAddAfter(A_set0B_B(), ELEMENT_C, ELEMENT_C, Result.ElementNotFound));
			printTest("A_set0B_B_testAddAfterAB", testAddAfter(A_set0B_B(), ELEMENT_B, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("A_set0B_B_testAddAtIndexNeg1B",
					testAddAtIndex(A_set0B_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testAddAtIndex0B", testAddAtIndex(A_set0B_B(), 0, ELEMENT_C, Result.NoException));
			printTest("A_set0B_B_testAddAtIndex1B", testAddAtIndex(A_set0B_B(), 1, ELEMENT_C, Result.NoException));
			printTest("A_set0B_B_testSetNeg1B", testSet(A_set0B_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testSet0B", testSet(A_set0B_B(), 0, ELEMENT_C, Result.NoException));
			printTest("A_set0B_B_testAddB", testAdd(A_set0B_B(), ELEMENT_C, Result.NoException));
			printTest("A_set0B_B_testGetNeg1", testGet(A_set0B_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testGet0", testGet(A_set0B_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testIndexOfA", testIndexOf(A_set0B_B(), ELEMENT_B, 0));
			printTest("A_set0B_B_testIndexOfB", testIndexOf(A_set0B_B(), ELEMENT_C, -1));
			printTest("A_set0B_B_testRemoveNeg1", testRemoveIndex(A_set0B_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testRemove0", testRemoveIndex(A_set0B_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testRemove1", testRemoveIndex(A_set0B_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_set0B_B_testIterator", testIterator(A_set0B_B(), Result.NoException));
			printTest("A_set0B_B_testIteratorHasNext", testIteratorHasNext(A_set0B_B().iterator(), Result.True));
			printTest("A_set0B_B_testIteratorNext",
					testIteratorNext(A_set0B_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testIteratorRemove", testIteratorRemove(A_set0B_B().iterator(), Result.IllegalState));
			printTest("A_set0B_B_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(A_set0B_B(), 1), Result.False));
			printTest("A_set0B_B_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(A_set0B_B(), 1), null, Result.NoSuchElement));
			printTest("A_set0B_B_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(A_set0B_B(), 1), Result.NoException));
			printTest("A_set0B_B_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(A_set0B_B(), 1)), Result.False));
			printTest("A_set0B_B_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(A_set0B_B(), 1)), null, Result.NoSuchElement));
			printTest("A_set0B_B_iteratorNextRemove_testIteratorRemove",
					testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(A_set0B_B(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_set0B_B");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> removeFirst() -> [B]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> removeFirst() -> [B]
	 * 
	 * @return [B] after removeFirst()
	 */
	private IndexedUnorderedListADT<Integer> AB_removeFirst_B() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.addToFront(ELEMENT_A);
		list.addToRear(ELEMENT_B);
		list.removeFirst();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> removeFirst() -> [B] */
	private void test_AB_removeFirst_B() {

		// try-catch is necessary to prevent an Exception from the scenario
		// builder method from bringing
		// down the whole test suite
		try {
			// ListADT
			printTest("AB_removeFirst_B_testRemoveFirst",
					testRemoveFirst(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveLast",
					testRemoveLast(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveA",
					testRemoveElement(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveB",
					testRemoveElement(AB_removeFirst_B(), ELEMENT_C, Result.ElementNotFound));
			printTest("AB_removeFirst_B_testFirst", testFirst(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testLast", testLast(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testContainsA", testContains(AB_removeFirst_B(), ELEMENT_B, Result.True));
			printTest("AB_removeFirst_B_testContainsB", testContains(AB_removeFirst_B(), ELEMENT_C, Result.False));
			printTest("AB_removeFirst_B_testIsEmpty", testIsEmpty(AB_removeFirst_B(), Result.False));
			printTest("AB_removeFirst_B_testSize", testSize(AB_removeFirst_B(), 1));
			printTest("AB_removeFirst_B_testToString", testToString(AB_removeFirst_B(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_removeFirst_B_testAddToFrontB",
					testAddToFront(AB_removeFirst_B(), ELEMENT_C, Result.NoException));
			printTest("AB_removeFirst_B_testAddToRearB",
					testAddToRear(AB_removeFirst_B(), ELEMENT_B, Result.NoException));
			printTest("AB_removeFirst_B_testAddAfterCB",
					testAddAfter(AB_removeFirst_B(), ELEMENT_C, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_removeFirst_B_testAddAfterAB",
					testAddAfter(AB_removeFirst_B(), ELEMENT_B, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("AB_removeFirst_B_testAddAtIndexNeg1B",
					testAddAtIndex(AB_removeFirst_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testAddAtIndex0B",
					testAddAtIndex(AB_removeFirst_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_removeFirst_B_testAddAtIndex1B",
					testAddAtIndex(AB_removeFirst_B(), 1, ELEMENT_C, Result.NoException));
			printTest("AB_removeFirst_B_testSetNeg1B",
					testSet(AB_removeFirst_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testSet0B", testSet(AB_removeFirst_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_removeFirst_B_testAddB", testAdd(AB_removeFirst_B(), ELEMENT_C, Result.NoException));
			printTest("AB_removeFirst_B_testGetNeg1", testGet(AB_removeFirst_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testGet0", testGet(AB_removeFirst_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testIndexOfA", testIndexOf(AB_removeFirst_B(), ELEMENT_B, 0));
			printTest("AB_removeFirst_B_testIndexOfB", testIndexOf(AB_removeFirst_B(), ELEMENT_C, -1));
			printTest("AB_removeFirst_B_testRemoveNeg1",
					testRemoveIndex(AB_removeFirst_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testRemove0",
					testRemoveIndex(AB_removeFirst_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemove1",
					testRemoveIndex(AB_removeFirst_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_removeFirst_B_testIterator", testIterator(AB_removeFirst_B(), Result.NoException));
			printTest("AB_removeFirst_B_testIteratorHasNext",
					testIteratorHasNext(AB_removeFirst_B().iterator(), Result.True));
			printTest("AB_removeFirst_B_testIteratorNext",
					testIteratorNext(AB_removeFirst_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testIteratorRemove",
					testIteratorRemove(AB_removeFirst_B().iterator(), Result.IllegalState));
			printTest("AB_removeFirst_B_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_removeFirst_B(), 1), Result.False));
			printTest("AB_removeFirst_B_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_removeFirst_B(), 1), null, Result.NoSuchElement));
			printTest("AB_removeFirst_B_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_removeFirst_B(), 1), Result.NoException));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_removeFirst_B(), 1)), Result.False));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_removeFirst_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_removeFirst_B(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeFirst_B");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> removeLast() -> [A]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> removeLast() -> [A]
	 * 
	 * @return [A] after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> AB_removeLast_A() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.removeLast();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> removeLast() -> [A] */
	private void test_AB_removeLast_A() {

		try {
			// ListADT
			printTest("AB_removeLast_A_testRemoveFirst",
					testRemoveFirst(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveLast",
					testRemoveLast(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveA",
					testRemoveElement(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveB",
					testRemoveElement(AB_removeLast_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("AB_removeLast_A_testFirst", testFirst(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testLast", testLast(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testContainsA", testContains(AB_removeLast_A(), ELEMENT_A, Result.True));
			printTest("AB_removeLast_A_testContainsB", testContains(AB_removeLast_A(), ELEMENT_B, Result.False));
			printTest("AB_removeLast_A_testIsEmpty", testIsEmpty(AB_removeLast_A(), Result.False));
			printTest("AB_removeLast_A_testSize", testSize(AB_removeLast_A(), 1));
			printTest("AB_removeLast_A_testToString", testToString(AB_removeLast_A(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_removeLast_A_testAddToFrontB",
					testAddToFront(AB_removeLast_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddToRearB",
					testAddToRear(AB_removeLast_A(), ELEMENT_A, Result.NoException));
			printTest("AB_removeLast_A_testAddAfterCB",
					testAddAfter(AB_removeLast_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("AB_removeLast_A_testAddAfterAB",
					testAddAfter(AB_removeLast_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("AB_removeLast_A_testAddAtIndexNeg1B",
					testAddAtIndex(AB_removeLast_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testAddAtIndex0B",
					testAddAtIndex(AB_removeLast_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddAtIndex1B",
					testAddAtIndex(AB_removeLast_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testSetNeg1B",
					testSet(AB_removeLast_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testSet0B", testSet(AB_removeLast_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddB", testAdd(AB_removeLast_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testGetNeg1", testGet(AB_removeLast_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testGet0", testGet(AB_removeLast_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testIndexOfA", testIndexOf(AB_removeLast_A(), ELEMENT_A, 0));
			printTest("AB_removeLast_A_testIndexOfB", testIndexOf(AB_removeLast_A(), ELEMENT_B, -1));
			printTest("AB_removeLast_A_testRemoveNeg1",
					testRemoveIndex(AB_removeLast_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testRemove0",
					testRemoveIndex(AB_removeLast_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemove1",
					testRemoveIndex(AB_removeLast_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_removeLast_A_testIterator", testIterator(AB_removeLast_A(), Result.NoException));
			printTest("AB_removeLast_A_testIteratorHasNext",
					testIteratorHasNext(AB_removeLast_A().iterator(), Result.True));
			printTest("AB_removeLast_A_testIteratorNext",
					testIteratorNext(AB_removeLast_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testIteratorRemove",
					testIteratorRemove(AB_removeLast_A().iterator(), Result.IllegalState));
			printTest("AB_removeLast_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_removeLast_A(), 1), Result.False));
			printTest("AB_removeLast_A_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_removeLast_A(), 1), null, Result.NoSuchElement));
			printTest("AB_removeLast_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_removeLast_A(), 1), Result.NoException));
			printTest("AB_removeLast_A_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_removeLast_A(), 1)), Result.False));
			printTest("AB_removeLast_A_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_removeLast_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeLast_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_removeLast_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> remove(A) -> [B]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> remove(A) -> [B]
	 * 
	 * @return [B] after remove(A)
	 */
	private IndexedUnorderedListADT<Integer> AB_removeA_B() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.addToFront(ELEMENT_A);
		list.addToRear(ELEMENT_B);
		list.remove(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> remove(A) -> [B] */
	private void test_AB_removeA_B() {

		// try-catch is necessary to prevent an Exception from the scenario
		// builder method from bringing
		// down the whole test suite
		try {
			// ListADT
			printTest("AB_removeA_B_testRemoveFirst", testRemoveFirst(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveLast", testRemoveLast(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveA", testRemoveElement(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveB", testRemoveElement(AB_removeA_B(), ELEMENT_C, Result.ElementNotFound));
			printTest("AB_removeA_B_testFirst", testFirst(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testLast", testLast(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testContainsA", testContains(AB_removeA_B(), ELEMENT_B, Result.True));
			printTest("AB_removeA_B_testContainsB", testContains(AB_removeA_B(), ELEMENT_C, Result.False));
			printTest("AB_removeA_B_testIsEmpty", testIsEmpty(AB_removeA_B(), Result.False));
			printTest("AB_removeA_B_testSize", testSize(AB_removeA_B(), 1));
			printTest("AB_removeA_B_testToString", testToString(AB_removeA_B(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_removeA_B_testAddToFrontB", testAddToFront(AB_removeA_B(), ELEMENT_C, Result.NoException));
			printTest("AB_removeA_B_testAddToRearB", testAddToRear(AB_removeA_B(), ELEMENT_B, Result.NoException));
			printTest("AB_removeA_B_testAddAfterCB",
					testAddAfter(AB_removeA_B(), ELEMENT_C, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_removeA_B_testAddAfterAB",
					testAddAfter(AB_removeA_B(), ELEMENT_B, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("AB_removeA_B_testAddAtIndexNeg1B",
					testAddAtIndex(AB_removeA_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testAddAtIndex0B",
					testAddAtIndex(AB_removeA_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_removeA_B_testAddAtIndex1B",
					testAddAtIndex(AB_removeA_B(), 1, ELEMENT_C, Result.NoException));
			printTest("AB_removeA_B_testSetNeg1B", testSet(AB_removeA_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testSet0B", testSet(AB_removeA_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_removeA_B_testAddB", testAdd(AB_removeA_B(), ELEMENT_C, Result.NoException));
			printTest("AB_removeA_B_testGetNeg1", testGet(AB_removeA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testGet0", testGet(AB_removeA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testIndexOfA", testIndexOf(AB_removeA_B(), ELEMENT_B, 0));
			printTest("AB_removeA_B_testIndexOfB", testIndexOf(AB_removeA_B(), ELEMENT_C, -1));
			printTest("AB_removeA_B_testRemoveNeg1",
					testRemoveIndex(AB_removeA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testRemove0", testRemoveIndex(AB_removeA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemove1", testRemoveIndex(AB_removeA_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_removeA_B_testIterator", testIterator(AB_removeA_B(), Result.NoException));
			printTest("AB_removeA_B_testIteratorHasNext", testIteratorHasNext(AB_removeA_B().iterator(), Result.True));
			printTest("AB_removeA_B_testIteratorNext",
					testIteratorNext(AB_removeA_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testIteratorRemove",
					testIteratorRemove(AB_removeA_B().iterator(), Result.IllegalState));
			printTest("AB_removeA_B_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_removeA_B(), 1), Result.False));
			printTest("AB_removeA_B_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_removeA_B(), 1), null, Result.NoSuchElement));
			printTest("AB_removeA_B_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_removeA_B(), 1), Result.NoException));
			printTest("AB_removeA_B_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_removeA_B(), 1)), Result.False));
			printTest("AB_removeA_B_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_removeA_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeA_B_iteratorNextRemove_testIteratorRemove",
					testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_removeA_B(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeA_B");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> remove(B) -> [A]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> remove(B) -> [A]
	 * 
	 * @return [A] after remove(B)
	 */
	private IndexedUnorderedListADT<Integer> AB_removeB_A() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.removeLast();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> remove(B) -> [A] */
	private void test_AB_removeB_A() {

		try {
			// ListADT
			printTest("AB_removeB_A_testRemoveFirst", testRemoveFirst(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemoveLast", testRemoveLast(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemoveA", testRemoveElement(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemoveB", testRemoveElement(AB_removeB_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("AB_removeB_A_testFirst", testFirst(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testLast", testLast(AB_removeB_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testContainsA", testContains(AB_removeB_A(), ELEMENT_A, Result.True));
			printTest("AB_removeB_A_testContainsB", testContains(AB_removeB_A(), ELEMENT_B, Result.False));
			printTest("AB_removeB_A_testIsEmpty", testIsEmpty(AB_removeB_A(), Result.False));
			printTest("AB_removeB_A_testSize", testSize(AB_removeB_A(), 1));
			printTest("AB_removeB_A_testToString", testToString(AB_removeB_A(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_removeB_A_testAddToFrontB", testAddToFront(AB_removeB_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddToRearB", testAddToRear(AB_removeB_A(), ELEMENT_A, Result.NoException));
			printTest("AB_removeB_A_testAddAfterCB",
					testAddAfter(AB_removeB_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("AB_removeB_A_testAddAfterAB",
					testAddAfter(AB_removeB_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("AB_removeB_A_testAddAtIndexNeg1B",
					testAddAtIndex(AB_removeB_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testAddAtIndex0B",
					testAddAtIndex(AB_removeB_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddAtIndex1B",
					testAddAtIndex(AB_removeB_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testSetNeg1B", testSet(AB_removeB_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testSet0B", testSet(AB_removeB_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testAddB", testAdd(AB_removeB_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeB_A_testGetNeg1", testGet(AB_removeB_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testGet0", testGet(AB_removeB_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testIndexOfA", testIndexOf(AB_removeB_A(), ELEMENT_A, 0));
			printTest("AB_removeB_A_testIndexOfB", testIndexOf(AB_removeB_A(), ELEMENT_B, -1));
			printTest("AB_removeB_A_testRemoveNeg1",
					testRemoveIndex(AB_removeB_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeB_A_testRemove0", testRemoveIndex(AB_removeB_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testRemove1", testRemoveIndex(AB_removeB_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_removeB_A_testIterator", testIterator(AB_removeB_A(), Result.NoException));
			printTest("AB_removeB_A_testIteratorHasNext", testIteratorHasNext(AB_removeB_A().iterator(), Result.True));
			printTest("AB_removeB_A_testIteratorNext",
					testIteratorNext(AB_removeB_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeB_A_testIteratorRemove",
					testIteratorRemove(AB_removeB_A().iterator(), Result.IllegalState));
			printTest("AB_removeB_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_removeB_A(), 1), Result.False));
			printTest("AB_removeB_A_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_removeB_A(), 1), null, Result.NoSuchElement));
			printTest("AB_removeB_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_removeB_A(), 1), Result.NoException));
			printTest("AB_removeB_A_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_removeB_A(), 1)), Result.False));
			printTest("AB_removeB_A_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_removeB_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeB_A_iteratorNextRemove_testIteratorRemove",
					testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_removeB_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> remove(0) -> [B]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> remove(0) -> [B]
	 * 
	 * @return [B] after remove(0)
	 */
	private IndexedUnorderedListADT<Integer> AB_remove0_B() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.addToFront(ELEMENT_A);
		list.addToRear(ELEMENT_B);
		list.remove(0);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> remove(0) -> [B] */
	private void test_AB_remove0_B() {

		// try-catch is necessary to prevent an Exception from the scenario
		// builder method from bringing
		// down the whole test suite
		try {
			// ListADT
			printTest("AB_remove0_B_testRemoveFirst", testRemoveFirst(AB_remove0_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_remove0_B_testRemoveLast", testRemoveLast(AB_remove0_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_remove0_B_testRemoveA", testRemoveElement(AB_remove0_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_remove0_B_testRemoveB", testRemoveElement(AB_remove0_B(), ELEMENT_C, Result.ElementNotFound));
			printTest("AB_remove0_B_testFirst", testFirst(AB_remove0_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_remove0_B_testLast", testLast(AB_remove0_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_remove0_B_testContainsA", testContains(AB_remove0_B(), ELEMENT_B, Result.True));
			printTest("AB_remove0_B_testContainsB", testContains(AB_remove0_B(), ELEMENT_C, Result.False));
			printTest("AB_remove0_B_testIsEmpty", testIsEmpty(AB_remove0_B(), Result.False));
			printTest("AB_remove0_B_testSize", testSize(AB_remove0_B(), 1));
			printTest("AB_remove0_B_testToString", testToString(AB_remove0_B(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_remove0_B_testAddToFrontB", testAddToFront(AB_remove0_B(), ELEMENT_C, Result.NoException));
			printTest("AB_remove0_B_testAddToRearB", testAddToRear(AB_remove0_B(), ELEMENT_B, Result.NoException));
			printTest("AB_remove0_B_testAddAfterCB",
					testAddAfter(AB_remove0_B(), ELEMENT_C, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_remove0_B_testAddAfterAB",
					testAddAfter(AB_remove0_B(), ELEMENT_B, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("AB_remove0_B_testAddAtIndexNeg1B",
					testAddAtIndex(AB_remove0_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_remove0_B_testAddAtIndex0B",
					testAddAtIndex(AB_remove0_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_remove0_B_testAddAtIndex1B",
					testAddAtIndex(AB_remove0_B(), 1, ELEMENT_C, Result.NoException));
			printTest("AB_remove0_B_testSetNeg1B", testSet(AB_remove0_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_remove0_B_testSet0B", testSet(AB_remove0_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_remove0_B_testAddB", testAdd(AB_remove0_B(), ELEMENT_C, Result.NoException));
			printTest("AB_remove0_B_testGetNeg1", testGet(AB_remove0_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_remove0_B_testGet0", testGet(AB_remove0_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_remove0_B_testIndexOfA", testIndexOf(AB_remove0_B(), ELEMENT_B, 0));
			printTest("AB_remove0_B_testIndexOfB", testIndexOf(AB_remove0_B(), ELEMENT_C, -1));
			printTest("AB_remove0_B_testRemoveNeg1",
					testRemoveIndex(AB_remove0_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_remove0_B_testRemove0", testRemoveIndex(AB_remove0_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_remove0_B_testRemove1", testRemoveIndex(AB_remove0_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_remove0_B_testIterator", testIterator(AB_remove0_B(), Result.NoException));
			printTest("AB_remove0_B_testIteratorHasNext", testIteratorHasNext(AB_remove0_B().iterator(), Result.True));
			printTest("AB_remove0_B_testIteratorNext",
					testIteratorNext(AB_remove0_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_remove0_B_testIteratorRemove",
					testIteratorRemove(AB_remove0_B().iterator(), Result.IllegalState));
			printTest("AB_remove0_B_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_remove0_B(), 1), Result.False));
			printTest("AB_remove0_B_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_remove0_B(), 1), null, Result.NoSuchElement));
			printTest("AB_remove0_B_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_remove0_B(), 1), Result.NoException));
			printTest("AB_remove0_B_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_remove0_B(), 1)), Result.False));
			printTest("AB_remove0_B_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_remove0_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_remove0_B_iteratorNextRemove_testIteratorRemove",
					testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_remove0_B(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_remove0_B");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> remove(1) -> [A]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> remove(1) -> [A]
	 * 
	 * @return [A] after remove(1)
	 */
	private IndexedUnorderedListADT<Integer> AB_remove1_A() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.remove(1);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> remove(1) -> [A] */
	private void test_AB_remove1_A() {

		try {
			// ListADT
			printTest("AB_remove1_A_testRemoveFirst", testRemoveFirst(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testRemoveLast", testRemoveLast(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testRemoveA", testRemoveElement(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testRemoveB", testRemoveElement(AB_remove1_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("AB_remove1_A_testFirst", testFirst(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testLast", testLast(AB_remove1_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testContainsA", testContains(AB_remove1_A(), ELEMENT_A, Result.True));
			printTest("AB_remove1_A_testContainsB", testContains(AB_remove1_A(), ELEMENT_B, Result.False));
			printTest("AB_remove1_A_testIsEmpty", testIsEmpty(AB_remove1_A(), Result.False));
			printTest("AB_remove1_A_testSize", testSize(AB_remove1_A(), 1));
			printTest("AB_remove1_A_testToString", testToString(AB_remove1_A(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_remove1_A_testAddToFrontB", testAddToFront(AB_remove1_A(), ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testAddToRearB", testAddToRear(AB_remove1_A(), ELEMENT_A, Result.NoException));
			printTest("AB_remove1_A_testAddAfterCB",
					testAddAfter(AB_remove1_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("AB_remove1_A_testAddAfterAB",
					testAddAfter(AB_remove1_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("AB_remove1_A_testAddAtIndexNeg1B",
					testAddAtIndex(AB_remove1_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testAddAtIndex0B",
					testAddAtIndex(AB_remove1_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testAddAtIndex1B",
					testAddAtIndex(AB_remove1_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testSetNeg1B", testSet(AB_remove1_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testSet0B", testSet(AB_remove1_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testAddB", testAdd(AB_remove1_A(), ELEMENT_B, Result.NoException));
			printTest("AB_remove1_A_testGetNeg1", testGet(AB_remove1_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testGet0", testGet(AB_remove1_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testIndexOfA", testIndexOf(AB_remove1_A(), ELEMENT_A, 0));
			printTest("AB_remove1_A_testIndexOfB", testIndexOf(AB_remove1_A(), ELEMENT_B, -1));
			printTest("AB_remove1_A_testRemoveNeg1",
					testRemoveIndex(AB_remove1_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_remove1_A_testRemove0", testRemoveIndex(AB_remove1_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testRemove1", testRemoveIndex(AB_remove1_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_remove1_A_testIterator", testIterator(AB_remove1_A(), Result.NoException));
			printTest("AB_remove1_A_testIteratorHasNext", testIteratorHasNext(AB_remove1_A().iterator(), Result.True));
			printTest("AB_remove1_A_testIteratorNext",
					testIteratorNext(AB_remove1_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_remove1_A_testIteratorRemove",
					testIteratorRemove(AB_remove1_A().iterator(), Result.IllegalState));
			printTest("AB_remove1_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_remove1_A(), 1), Result.False));
			printTest("AB_remove1_A_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_remove1_A(), 1), null, Result.NoSuchElement));
			printTest("AB_remove1_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_remove1_A(), 1), Result.NoException));
			printTest("AB_remove1_A_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_remove1_A(), 1)), Result.False));
			printTest("AB_remove1_A_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_remove1_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_remove1_A_iteratorNextRemove_testIteratorRemove",
					testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_remove1_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> add(C) -> [A,B,C]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> add(C) -> [A,B,C]
	 * 
	 * @return [A,B,C] after add(C)
	 */
	private IndexedUnorderedListADT<Integer> AB_addC_ABC() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: empty list -> add(0, A) -> [A] */
	private void test_AB_addC_ABC() {

		try {
			// ListADT
			printTest("AB_addC_ABC_testRemoveFirst", testRemoveFirst(AB_addC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC_testRemoveLast", testRemoveLast(AB_addC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC_testRemoveA", testRemoveElement(AB_addC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC_testRemoveC", testRemoveElement(AB_addC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC_testRemoveD", testRemoveElement(AB_addC_ABC(), ELEMENT_D, Result.ElementNotFound));
			printTest("AB_addC_ABC_testFirst", testFirst(AB_addC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC_testLast", testLast(AB_addC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_addC_ABC_testContainsA", testContains(AB_addC_ABC(), ELEMENT_A, Result.True));
			printTest("AB_addC_ABC_testContainsB", testContains(AB_addC_ABC(), ELEMENT_B, Result.True));
			printTest("AB_addC_ABC_testContainsC", testContains(AB_addC_ABC(), ELEMENT_C, Result.True));
			printTest("AB_addC_ABC_testContainsD", testContains(AB_addC_ABC(), ELEMENT_D, Result.False));
			printTest("AB_addC_ABC_testIsEmpty", testIsEmpty(AB_addC_ABC(), Result.False));
			printTest("AB_addC_ABC_testSize", testSize(AB_addC_ABC(), 3));
			printTest("AB_addC_ABC_testToString", testToString(AB_addC_ABC(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_addC_ABC_testAddToFrontC", testAddToFront(AB_addC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC_testAddToRearC", testAddToRear(AB_addC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC_testAddAfterDC",
					testAddAfter(AB_addC_ABC(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_addC_ABC_testAddAfterAD",
					testAddAfter(AB_addC_ABC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("AB_addC_ABC_testAddAtIndexNeg1C",
					testAddAtIndex(AB_addC_ABC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC_testAddAtIndex0C", testAddAtIndex(AB_addC_ABC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC_testAddAtIndex1C", testAddAtIndex(AB_addC_ABC(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC_testSetNeg1C", testSet(AB_addC_ABC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC_testSet0C", testSet(AB_addC_ABC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC_testAddC", testAdd(AB_addC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_addC_ABC_testGetNeg1", testGet(AB_addC_ABC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC_testGet0", testGet(AB_addC_ABC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC_testIndexOfA", testIndexOf(AB_addC_ABC(), ELEMENT_A, 0));
			printTest("AB_addC_ABC_testIndexOfB", testIndexOf(AB_addC_ABC(), ELEMENT_B, 1));
			printTest("AB_addC_ABC_testIndexOfC", testIndexOf(AB_addC_ABC(), ELEMENT_C, 2));
			printTest("AB_addC_ABC_testIndexOfD", testIndexOf(AB_addC_ABC(), ELEMENT_D, -1));
			printTest("AB_addC_ABC_testRemoveNeg1", testRemoveIndex(AB_addC_ABC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_addC_ABC_testRemove0", testRemoveIndex(AB_addC_ABC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC_testRemove3", testRemoveIndex(AB_addC_ABC(), 3, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_addC_ABC_testIterator", testIterator(AB_addC_ABC(), Result.NoException));
			printTest("AB_addC_ABC_testIteratorHasNext", testIteratorHasNext(AB_addC_ABC().iterator(), Result.True));
			printTest("AB_addC_ABC_testIteratorNext",
					testIteratorNext(AB_addC_ABC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_addC_ABC_testIteratorRemove",
					testIteratorRemove(AB_addC_ABC().iterator(), Result.IllegalState));
			printTest("AB_addC_ABC_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_addC_ABC(), 1), Result.True));
			printTest("AB_addC_ABC_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_addC_ABC(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("AB_addC_ABC_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_addC_ABC(), 1), Result.NoException));
			printTest("AB_addC_ABC_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 1)), Result.True));
			printTest("AB_addC_ABC_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("AB_addC_ABC_iteratorNextRemove_testIteratorRemove",
					testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_addC_ABC(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> add(0, C) -> [C,A,B]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> add(0, C) -> [C,A,B]
	 * 
	 * @return [C,A,B] after add(0, C)
	 */
	private IndexedUnorderedListADT<Integer> AB_add0C_CAB() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(0, ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> add(0, A) -> [A,B,C] */
	private void test_AB_add0C_CAB() {

		try {
			// ListADT
			printTest("AB_add0C_CAB_testRemoveFirst", testRemoveFirst(AB_add0C_CAB(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_add0C_CAB_testRemoveLast", testRemoveLast(AB_add0C_CAB(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_add0C_CAB_testRemoveA", testRemoveElement(AB_add0C_CAB(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_add0C_CAB_testRemoveC", testRemoveElement(AB_add0C_CAB(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_add0C_CAB_testRemoveD", testRemoveElement(AB_add0C_CAB(), ELEMENT_D, Result.ElementNotFound));
			printTest("AB_add0C_CAB_testFirst", testFirst(AB_add0C_CAB(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_add0C_CAB_testLast", testLast(AB_add0C_CAB(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_add0C_CAB_testContainsA", testContains(AB_add0C_CAB(), ELEMENT_A, Result.True));
			printTest("AB_add0C_CAB_testContainsB", testContains(AB_add0C_CAB(), ELEMENT_B, Result.True));
			printTest("AB_add0C_CAB_testContainsC", testContains(AB_add0C_CAB(), ELEMENT_C, Result.True));
			printTest("AB_add0C_CAB_testContainsD", testContains(AB_add0C_CAB(), ELEMENT_D, Result.False));
			printTest("AB_add0C_CAB_testIsEmpty", testIsEmpty(AB_add0C_CAB(), Result.False));
			printTest("AB_add0C_CAB_testSize", testSize(AB_add0C_CAB(), 3));
			printTest("AB_add0C_CAB_testToString", testToString(AB_add0C_CAB(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_add0C_CAB_testAddToFrontC", testAddToFront(AB_add0C_CAB(), ELEMENT_D, Result.NoException));
			printTest("AB_add0C_CAB_testAddToRearC", testAddToRear(AB_add0C_CAB(), ELEMENT_D, Result.NoException));
			printTest("AB_add0C_CAB_testAddAfterDC",
					testAddAfter(AB_add0C_CAB(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_add0C_CAB_testAddAfterAD",
					testAddAfter(AB_add0C_CAB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("AB_add0C_CAB_testAddAtIndexNeg1C",
					testAddAtIndex(AB_add0C_CAB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_add0C_CAB_testAddAtIndex0C",
					testAddAtIndex(AB_add0C_CAB(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_add0C_CAB_testAddAtIndex1C",
					testAddAtIndex(AB_add0C_CAB(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_add0C_CAB_testSetNeg1C", testSet(AB_add0C_CAB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_add0C_CAB_testSet0C", testSet(AB_add0C_CAB(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_add0C_CAB_testAddC", testAdd(AB_add0C_CAB(), ELEMENT_D, Result.NoException));
			printTest("AB_add0C_CAB_testGetNeg1", testGet(AB_add0C_CAB(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_add0C_CAB_testGet0", testGet(AB_add0C_CAB(), 0, ELEMENT_C, Result.MatchingValue));
			printTest("AB_add0C_CAB_testIndexOfA", testIndexOf(AB_add0C_CAB(), ELEMENT_A, 1));
			printTest("AB_add0C_CAB_testIndexOfB", testIndexOf(AB_add0C_CAB(), ELEMENT_B, 2));
			printTest("AB_add0C_CAB_testIndexOfC", testIndexOf(AB_add0C_CAB(), ELEMENT_C, 0));
			printTest("AB_add0C_CAB_testIndexOfD", testIndexOf(AB_add0C_CAB(), ELEMENT_D, -1));
			printTest("AB_add0C_CAB_testRemoveNeg1",
					testRemoveIndex(AB_add0C_CAB(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_add0C_CAB_testRemove0", testRemoveIndex(AB_add0C_CAB(), 0, ELEMENT_C, Result.MatchingValue));
			printTest("AB_add0C_CAB_testRemove3", testRemoveIndex(AB_add0C_CAB(), 3, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_add0C_CAB_testIterator", testIterator(AB_add0C_CAB(), Result.NoException));
			printTest("AB_add0C_CAB_testIteratorHasNext", testIteratorHasNext(AB_add0C_CAB().iterator(), Result.True));
			printTest("AB_add0C_CAB_testIteratorNext",
					testIteratorNext(AB_add0C_CAB().iterator(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_add0C_CAB_testIteratorRemove",
					testIteratorRemove(AB_add0C_CAB().iterator(), Result.IllegalState));
			printTest("AB_add0C_CAB_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_add0C_CAB(), 1), Result.True));
			printTest("AB_add0C_CAB_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_add0C_CAB(), 1), ELEMENT_A, Result.MatchingValue));
			printTest("AB_add0C_CAB_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_add0C_CAB(), 1), Result.NoException));
			printTest("AB_add0C_CAB_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_add0C_CAB(), 1)), Result.True));
			printTest("AB_add0C_CAB_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_add0C_CAB(), 1)), ELEMENT_A, Result.MatchingValue));
			printTest("AB_add0C_CAB_iteratorNextRemove_testIteratorRemove",
					testIteratorRemove(iteratorAfterRemove(iteratorAfterNext(AB_add0C_CAB(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A, B, C] -> removeLast() -> [A,B]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A, B, C] -> removeLast() -> [A, B]
	 * 
	 * @return [A,B] after removeLast()
	 */
	private IndexedUnorderedListADT<Integer> ABC_removeLast_AB() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.removeLast();
		return list;
	}

	/** Run all tests on scenario: empty list -> add(0, A) -> [A] */
	private void test_ABC_removeLast_AB() {

		try {
			// ListADT
			printTest("ABC_removeLast_AB_testRemoveFirst",
					testRemoveFirst(ABC_removeLast_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemoveLast",
					testRemoveLast(ABC_removeLast_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemoveA",
					testRemoveElement(ABC_removeLast_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemoveB",
					testRemoveElement(ABC_removeLast_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemoveC",
					testRemoveElement(ABC_removeLast_AB(), ELEMENT_C, Result.ElementNotFound));
			printTest("ABC_removeLast_AB_testFirst", testFirst(ABC_removeLast_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testLast", testLast(ABC_removeLast_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testContainsA", testContains(ABC_removeLast_AB(), ELEMENT_A, Result.True));
			printTest("ABC_removeLast_AB_testContainsB", testContains(ABC_removeLast_AB(), ELEMENT_B, Result.True));
			printTest("ABC_removeLast_AB_testContainsC", testContains(ABC_removeLast_AB(), ELEMENT_C, Result.False));
			printTest("ABC_removeLast_AB_testIsEmpty", testIsEmpty(ABC_removeLast_AB(), Result.False));
			printTest("ABC_removeLast_AB_testSize", testSize(ABC_removeLast_AB(), 2));
			printTest("ABC_removeLast_AB_testToString", testToString(ABC_removeLast_AB(), Result.ValidString));
			// UnorderedListADT
			printTest("ABC_removeLast_AB_testAddToFrontC",
					testAddToFront(ABC_removeLast_AB(), ELEMENT_C, Result.NoException));
			printTest("ABC_removeLast_AB_testAddToRearC",
					testAddToRear(ABC_removeLast_AB(), ELEMENT_C, Result.NoException));
			printTest("ABC_removeLast_AB_testAddAfterDC",
					testAddAfter(ABC_removeLast_AB(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			printTest("ABC_removeLast_AB_testAddAfterAC",
					testAddAfter(ABC_removeLast_AB(), ELEMENT_A, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("ABC_removeLast_AB_testAddAtIndexNeg1C",
					testAddAtIndex(ABC_removeLast_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testAddAtIndex0C",
					testAddAtIndex(ABC_removeLast_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("ABC_removeLast_AB_testAddAtIndex1C",
					testAddAtIndex(ABC_removeLast_AB(), 1, ELEMENT_C, Result.NoException));
			printTest("ABC_removeLast_AB_testSetNeg1C",
					testSet(ABC_removeLast_AB(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testSet0C", testSet(ABC_removeLast_AB(), 0, ELEMENT_C, Result.NoException));
			printTest("ABC_removeLast_AB_testAddC", testAdd(ABC_removeLast_AB(), ELEMENT_C, Result.NoException));
			printTest("ABC_removeLast_AB_testGetNeg1", testGet(ABC_removeLast_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testGet0", testGet(ABC_removeLast_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testIndexOfA", testIndexOf(ABC_removeLast_AB(), ELEMENT_A, 0));
			printTest("ABC_removeLast_AB_testIndexOfB", testIndexOf(ABC_removeLast_AB(), ELEMENT_B, 1));
			printTest("ABC_removeLast_AB_testIndexOfC", testIndexOf(ABC_removeLast_AB(), ELEMENT_C, -1));
			printTest("ABC_removeLast_AB_testRemoveNeg1",
					testRemoveIndex(ABC_removeLast_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testRemove0",
					testRemoveIndex(ABC_removeLast_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemove2",
					testRemoveIndex(ABC_removeLast_AB(), 2, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("ABC_removeLast_AB_testIterator", testIterator(ABC_removeLast_AB(), Result.NoException));
			printTest("ABC_removeLast_AB_testIteratorHasNext",
					testIteratorHasNext(ABC_removeLast_AB().iterator(), Result.True));
			printTest("ABC_removeLast_AB_testIteratorNext",
					testIteratorNext(ABC_removeLast_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testIteratorRemove",
					testIteratorRemove(ABC_removeLast_AB().iterator(), Result.IllegalState));
			printTest("ABC_removeLast_AB_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(ABC_removeLast_AB(), 1), Result.True));
			printTest("ABC_removeLast_AB_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(ABC_removeLast_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(ABC_removeLast_AB(), 1), Result.NoException));
			printTest("ABC_removeLast_AB_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(ABC_removeLast_AB(), 1)), Result.True));
			printTest("ABC_removeLast_AB_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(ABC_removeLast_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(ABC_removeLast_AB(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A, B, C] -> remove(A) -> [A,B]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B,C] -> remove(A) -> [B,C]
	 * 
	 * @return [B,C] after remove(A)
	 */
	private IndexedUnorderedListADT<Integer> ABC_removeA_BC() {
		IndexedUnorderedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(ELEMENT_A);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(A) -> [B,C] */
	private void test_ABC_removeA_BC() {

		try {
			// ListADT
			printTest("ABC_removeA_BC_testRemoveFirst",
					testRemoveFirst(ABC_removeA_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemoveLast",
					testRemoveLast(ABC_removeA_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemoveA",
					testRemoveElement(ABC_removeA_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemoveB",
					testRemoveElement(ABC_removeA_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemoveC",
					testRemoveElement(ABC_removeA_BC(), ELEMENT_D, Result.ElementNotFound));
			printTest("ABC_removeA_BC_testFirst", testFirst(ABC_removeA_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testLast", testLast(ABC_removeA_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_testContainsA", testContains(ABC_removeA_BC(), ELEMENT_B, Result.True));
			printTest("ABC_removeA_BC_testContainsB", testContains(ABC_removeA_BC(), ELEMENT_C, Result.True));
			printTest("ABC_removeA_BC_testContainsC", testContains(ABC_removeA_BC(), ELEMENT_D, Result.False));
			printTest("ABC_removeA_BC_testIsEmpty", testIsEmpty(ABC_removeA_BC(), Result.False));
			printTest("ABC_removeA_BC_testSize", testSize(ABC_removeA_BC(), 2));
			printTest("ABC_removeA_BC_testToString", testToString(ABC_removeA_BC(), Result.ValidString));
			// UnorderedListADT
			printTest("ABC_removeA_BC_testAddToFrontC",
					testAddToFront(ABC_removeA_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddToRearC", testAddToRear(ABC_removeA_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddAfterDC",
					testAddAfter(ABC_removeA_BC(), ELEMENT_A, ELEMENT_D, Result.ElementNotFound));
			printTest("ABC_removeA_BC_testAddAfterAC",
					testAddAfter(ABC_removeA_BC(), ELEMENT_B, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("ABC_removeA_BC_testAddAtIndexNeg1C",
					testAddAtIndex(ABC_removeA_BC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testAddAtIndex0C",
					testAddAtIndex(ABC_removeA_BC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddAtIndex1C",
					testAddAtIndex(ABC_removeA_BC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testSetNeg1C", testSet(ABC_removeA_BC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testSet0C", testSet(ABC_removeA_BC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testAddC", testAdd(ABC_removeA_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeA_BC_testGetNeg1", testGet(ABC_removeA_BC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testGet0", testGet(ABC_removeA_BC(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testIndexOfA", testIndexOf(ABC_removeA_BC(), ELEMENT_B, 0));
			printTest("ABC_removeA_BC_testIndexOfB", testIndexOf(ABC_removeA_BC(), ELEMENT_C, 1));
			printTest("ABC_removeA_BC_testIndexOfC", testIndexOf(ABC_removeA_BC(), ELEMENT_D, -1));
			printTest("ABC_removeA_BC_testRemoveNeg1",
					testRemoveIndex(ABC_removeA_BC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeA_BC_testRemove0",
					testRemoveIndex(ABC_removeA_BC(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testRemove2",
					testRemoveIndex(ABC_removeA_BC(), 2, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("ABC_removeA_BC_testIterator", testIterator(ABC_removeA_BC(), Result.NoException));
			printTest("ABC_removeA_BC_testIteratorHasNext",
					testIteratorHasNext(ABC_removeA_BC().iterator(), Result.True));
			printTest("ABC_removeA_BC_testIteratorNext",
					testIteratorNext(ABC_removeA_BC().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeA_BC_testIteratorRemove",
					testIteratorRemove(ABC_removeA_BC().iterator(), Result.IllegalState));
			printTest("ABC_removeA_BC_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(ABC_removeA_BC(), 1), Result.True));
			printTest("ABC_removeA_BC_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(ABC_removeA_BC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(ABC_removeA_BC(), 1), Result.NoException));
			printTest("ABC_removeA_BC_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(ABC_removeA_BC(), 1)), Result.True));
			printTest("ABC_removeA_BC_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(ABC_removeA_BC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeA_BC_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(ABC_removeA_BC(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}
	// XXX

	////////////////////////////////////////////////
	// SCENARIO: [A] -> removeFirst() -> [ ]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A] -> removeFirst() -> [ ]
	 * 
	 * @return [ ] after removeFirst()
	 */
	private DoubleLinkedListADT<Integer> A_InextIremove_emptyList() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		ListIterator<Integer> lInt = list.listIterator();
		lInt.next();
		lInt.remove();
		return list;
	}

	/** Run all tests on scenario: [A] -> removeFirst() -> [ ] */
	private void test_A_InextIremove_emptyList() {

		try {
			// ListADT
			printTest("A_InextIremove_emptyList_testRemoveFirst",
					testRemoveFirst(A_InextIremove_emptyList(), null, Result.EmptyCollection));
			printTest("A_InextIremove_emptyList_testRemoveLast",
					testRemoveLast(A_InextIremove_emptyList(), null, Result.EmptyCollection));
			printTest("A_InextIremove_emptyList_testRemoveA",
					testRemoveElement(A_InextIremove_emptyList(), null, Result.ElementNotFound));
			printTest("A_InextIremove_emptyList_testFirst",
					testFirst(A_InextIremove_emptyList(), null, Result.EmptyCollection));
			printTest("A_InextIremove_emptyList_testLast",
					testLast(A_InextIremove_emptyList(), null, Result.EmptyCollection));
			printTest("A_InextIremove_emptyList_testContainsA",
					testContains(A_InextIremove_emptyList(), ELEMENT_A, Result.False));
			printTest("A_InextIremove_emptyList_testIsEmpty", testIsEmpty(A_InextIremove_emptyList(), Result.True));
			printTest("A_InextIremove_emptyList_testSize", testSize(A_InextIremove_emptyList(), 0));
			printTest("A_InextIremove_emptyList_testToString",
					testToString(A_InextIremove_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_InextIremove_emptyList_testAddToFrontA",
					testAddToFront(A_InextIremove_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_InextIremove_emptyList_testAddToRearA",
					testAddToRear(A_InextIremove_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_InextIremove_emptyList_testAddAfterBA",
					testAddAfter(A_InextIremove_emptyList(), ELEMENT_B, ELEMENT_A, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_InextIremove_emptyList_testAddAtIndexNeg1",
					testAddAtIndex(A_InextIremove_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_InextIremove_emptyList_testAddAtIndex0",
					testAddAtIndex(A_InextIremove_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_InextIremove_emptyList_testAddAtIndex1",
					testAddAtIndex(A_InextIremove_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_InextIremove_emptyList_testSetNeg1A",
					testSet(A_InextIremove_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_InextIremove_emptyList_testSet0A",
					testSet(A_InextIremove_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_InextIremove_emptyList_testAddA",
					testAdd(A_InextIremove_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_InextIremove_emptyList_testGetNeg1",
					testGet(A_InextIremove_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_InextIremove_emptyList_testGet0",
					testGet(A_InextIremove_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_InextIremove_emptyList_testIndexOfA", testIndexOf(A_InextIremove_emptyList(), ELEMENT_A, -1));
			printTest("A_InextIremove_emptyList_testRemoveNeg1",
					testRemoveIndex(A_InextIremove_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_InextIremove_emptyList_testRemove0",
					testRemoveIndex(A_InextIremove_emptyList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_InextIremove_emptyList_testIterator",
					testIterator(A_InextIremove_emptyList(), Result.NoException));
			printTest("A_InextIremove_emptyList_testIteratorHasNext",
					testIteratorHasNext(A_InextIremove_emptyList().iterator(), Result.False));
			printTest("A_InextIremove_emptyList_testIteratorNext",
					testIteratorNext(A_InextIremove_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_InextIremove_emptyList_testIteratorRemove",
					testIteratorRemove(A_InextIremove_emptyList().iterator(), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "A_InextIremove_emptyList");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B] -> listIterator next remove -> [B]
	 * 
	 * @return [B] after remove()
	 */
	private DoubleLinkedListADT<Integer> AB_InextIremove_B() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		ListIterator<Integer> lInt = list.listIterator();
		lInt.next();
		lInt.remove();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> listIterator next remove -> [B] */
	private void test_AB_InextIremove_B() {

		// try-catch is necessary to prevent an Exception from the scenario
		// builder method from bringing
		// down the whole test suite
		try {
			// ListADT
			printTest("AB_InextIremove_B_testRemoveFirst",
					testRemoveFirst(AB_InextIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIremove_B_testRemoveLast",
					testRemoveLast(AB_InextIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIremove_B_testRemoveA",
					testRemoveElement(AB_InextIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIremove_B_testRemoveB",
					testRemoveElement(AB_InextIremove_B(), ELEMENT_C, Result.ElementNotFound));
			printTest("AB_InextIremove_B_testFirst", testFirst(AB_InextIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIremove_B_testLast", testLast(AB_InextIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIremove_B_testContainsA", testContains(AB_InextIremove_B(), ELEMENT_B, Result.True));
			printTest("AB_InextIremove_B_testContainsB", testContains(AB_InextIremove_B(), ELEMENT_C, Result.False));
			printTest("AB_InextIremove_B_testIsEmpty", testIsEmpty(AB_InextIremove_B(), Result.False));
			printTest("AB_InextIremove_B_testSize", testSize(AB_InextIremove_B(), 1));
			printTest("AB_InextIremove_B_testToString", testToString(AB_InextIremove_B(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_InextIremove_B_testAddToFrontB",
					testAddToFront(AB_InextIremove_B(), ELEMENT_C, Result.NoException));
			printTest("AB_InextIremove_B_testAddToRearB",
					testAddToRear(AB_InextIremove_B(), ELEMENT_B, Result.NoException));
			printTest("AB_InextIremove_B_testAddAfterCB",
					testAddAfter(AB_InextIremove_B(), ELEMENT_C, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_InextIremove_B_testAddAfterAB",
					testAddAfter(AB_InextIremove_B(), ELEMENT_B, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("AB_InextIremove_B_testAddAtIndexNeg1B",
					testAddAtIndex(AB_InextIremove_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_InextIremove_B_testAddAtIndex0B",
					testAddAtIndex(AB_InextIremove_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_InextIremove_B_testAddAtIndex1B",
					testAddAtIndex(AB_InextIremove_B(), 1, ELEMENT_C, Result.NoException));
			printTest("AB_InextIremove_B_testSetNeg1B",
					testSet(AB_InextIremove_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_InextIremove_B_testSet0B", testSet(AB_InextIremove_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_InextIremove_B_testAddB", testAdd(AB_InextIremove_B(), ELEMENT_C, Result.NoException));
			printTest("AB_InextIremove_B_testGetNeg1", testGet(AB_InextIremove_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextIremove_B_testGet0", testGet(AB_InextIremove_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIremove_B_testIndexOfA", testIndexOf(AB_InextIremove_B(), ELEMENT_B, 0));
			printTest("AB_InextIremove_B_testIndexOfB", testIndexOf(AB_InextIremove_B(), ELEMENT_C, -1));
			printTest("AB_InextIremove_B_testRemoveNeg1",
					testRemoveIndex(AB_InextIremove_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextIremove_B_testRemove0",
					testRemoveIndex(AB_InextIremove_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIremove_B_testRemove1",
					testRemoveIndex(AB_InextIremove_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_InextIremove_B_testIterator", testIterator(AB_InextIremove_B(), Result.NoException));
			printTest("AB_InextIremove_B_testIteratorHasNext",
					testIteratorHasNext(AB_InextIremove_B().iterator(), Result.True));
			printTest("AB_InextIremove_B_testIteratorNext",
					testIteratorNext(AB_InextIremove_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIremove_B_testIteratorRemove",
					testIteratorRemove(AB_InextIremove_B().iterator(), Result.IllegalState));
			printTest("AB_InextIremove_B_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_InextIremove_B(), 1), Result.False));
			printTest("AB_InextIremove_B_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_InextIremove_B(), 1), null, Result.NoSuchElement));
			printTest("AB_InextIremove_B_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_InextIremove_B(), 1), Result.NoException));
			printTest("AB_InextIremove_B_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_InextIremove_B(), 1)), Result.False));
			printTest("AB_InextIremove_B_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_InextIremove_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_InextIremove_B_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_InextIremove_B(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_InextIremove_B");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// SCENARIO: [A,B] -> List Integer remove -> [A]
	////////////////////////////////////////////////

	/**
	 * Scenario: [A,B] -> List Integer remove -> [A]
	 * 
	 * @return [A] after addToFront(A)
	 */
	private DoubleLinkedListADT<Integer> AB_InextInextIremove_A() {
		DoubleLinkedListADT<Integer> list = newList();
		list.addToFront(ELEMENT_A);
		list.add(ELEMENT_B);
		ListIterator<Integer> lInt = list.listIterator();
		lInt.next();
		lInt.next();
		lInt.remove();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> List Integer remove -> [A] */
	private void test_AB_InextInextIremove_A() {

		try {
			// ListADT
			printTest("AB_InextInextIremove_A_testRemoveFirst",
					testRemoveFirst(AB_InextInextIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIremove_A_testRemoveLast",
					testRemoveLast(AB_InextInextIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIremove_A_testRemoveA",
					testRemoveElement(AB_InextInextIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIremove_A_testRemoveB",
					testRemoveElement(AB_InextInextIremove_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("AB_InextInextIremove_A_testFirst",
					testFirst(AB_InextInextIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIremove_A_testLast",
					testLast(AB_InextInextIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIremove_A_testContainsA",
					testContains(AB_InextInextIremove_A(), ELEMENT_A, Result.True));
			printTest("AB_InextInextIremove_A_testContainsB",
					testContains(AB_InextInextIremove_A(), ELEMENT_B, Result.False));
			printTest("AB_InextInextIremove_A_testIsEmpty", testIsEmpty(AB_InextInextIremove_A(), Result.False));
			printTest("AB_InextInextIremove_A_testSize", testSize(AB_InextInextIremove_A(), 1));
			printTest("AB_InextInextIremove_A_testToString",
					testToString(AB_InextInextIremove_A(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_InextInextIremove_A_testAddToFrontB",
					testAddToFront(AB_InextInextIremove_A(), ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIremove_A_testAddToRearB",
					testAddToRear(AB_InextInextIremove_A(), ELEMENT_A, Result.NoException));
			printTest("AB_InextInextIremove_A_testAddAfterCB",
					testAddAfter(AB_InextInextIremove_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("AB_InextInextIremove_A_testAddAfterAB",
					testAddAfter(AB_InextInextIremove_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("AB_InextInextIremove_A_testAddAtIndexNeg1B",
					testAddAtIndex(AB_InextInextIremove_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_InextInextIremove_A_testAddAtIndex0B",
					testAddAtIndex(AB_InextInextIremove_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIremove_A_testAddAtIndex1B",
					testAddAtIndex(AB_InextInextIremove_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIremove_A_testSetNeg1B",
					testSet(AB_InextInextIremove_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_InextInextIremove_A_testSet0B",
					testSet(AB_InextInextIremove_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIremove_A_testAddB",
					testAdd(AB_InextInextIremove_A(), ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIremove_A_testGetNeg1",
					testGet(AB_InextInextIremove_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextInextIremove_A_testGet0",
					testGet(AB_InextInextIremove_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIremove_A_testIndexOfA", testIndexOf(AB_InextInextIremove_A(), ELEMENT_A, 0));
			printTest("AB_InextInextIremove_A_testIndexOfB", testIndexOf(AB_InextInextIremove_A(), ELEMENT_B, -1));
			printTest("AB_InextInextIremove_A_testRemoveNeg1",
					testRemoveIndex(AB_InextInextIremove_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextInextIremove_A_testRemove0",
					testRemoveIndex(AB_InextInextIremove_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIremove_A_testRemove1",
					testRemoveIndex(AB_InextInextIremove_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_InextInextIremove_A_testIterator",
					testIterator(AB_InextInextIremove_A(), Result.NoException));
			printTest("AB_InextInextIremove_A_testIteratorHasNext",
					testIteratorHasNext(AB_InextInextIremove_A().iterator(), Result.True));
			printTest("AB_InextInextIremove_A_testIteratorNext",
					testIteratorNext(AB_InextInextIremove_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIremove_A_testIteratorRemove",
					testIteratorRemove(AB_InextInextIremove_A().iterator(), Result.IllegalState));
			printTest("AB_InextInextIremove_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_InextInextIremove_A(), 1), Result.False));
			printTest("AB_InextInextIremove_A_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_InextInextIremove_A(), 1), null, Result.NoSuchElement));
			printTest("AB_InextInextIremove_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_InextInextIremove_A(), 1), Result.NoException));
			printTest("AB_InextInextIremove_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(AB_InextInextIremove_A(), 1)), Result.False));
			printTest("AB_InextInextIremove_A_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_InextInextIremove_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_InextInextIremove_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_InextInextIremove_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_InextInextIremove_A");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B,C] -> Iterator remove -> [B, C]
	 * 
	 * @return [B,C] after remove
	 */
	private DoubleLinkedListADT<Integer> ABC_InextIremove_BC() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		ListIterator<Integer> lIterator = list.listIterator();
		lIterator.next();
		lIterator.remove();
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> Iterator remove -> [B, C] */
	private void test_ABC_InextIremove_BC() {

		try {
			// ListADT
			printTest("ABC_InextIremove_BC_testRemoveFirst",
					testRemoveFirst(ABC_InextIremove_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testRemoveLast",
					testRemoveLast(ABC_InextIremove_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testRemoveA",
					testRemoveElement(ABC_InextIremove_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testRemoveB",
					testRemoveElement(ABC_InextIremove_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testRemoveC",
					testRemoveElement(ABC_InextIremove_BC(), ELEMENT_D, Result.ElementNotFound));
			printTest("ABC_InextIremove_BC_testFirst",
					testFirst(ABC_InextIremove_BC(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testLast", testLast(ABC_InextIremove_BC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testContainsA", testContains(ABC_InextIremove_BC(), ELEMENT_B, Result.True));
			printTest("ABC_InextIremove_BC_testContainsB", testContains(ABC_InextIremove_BC(), ELEMENT_C, Result.True));
			printTest("ABC_InextIremove_BC_testContainsC",
					testContains(ABC_InextIremove_BC(), ELEMENT_D, Result.False));
			printTest("ABC_InextIremove_BC_testIsEmpty", testIsEmpty(ABC_InextIremove_BC(), Result.False));
			printTest("ABC_InextIremove_BC_testSize", testSize(ABC_InextIremove_BC(), 2));
			printTest("ABC_InextIremove_BC_testToString", testToString(ABC_InextIremove_BC(), Result.ValidString));
			// UnorderedListADT
			printTest("ABC_InextIremove_BC_testAddToFrontC",
					testAddToFront(ABC_InextIremove_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextIremove_BC_testAddToRearC",
					testAddToRear(ABC_InextIremove_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextIremove_BC_testAddAfterDC",
					testAddAfter(ABC_InextIremove_BC(), ELEMENT_A, ELEMENT_D, Result.ElementNotFound));
			printTest("ABC_InextIremove_BC_testAddAfterAC",
					testAddAfter(ABC_InextIremove_BC(), ELEMENT_B, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("ABC_InextIremove_BC_testAddAtIndexNeg1C",
					testAddAtIndex(ABC_InextIremove_BC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_InextIremove_BC_testAddAtIndex0C",
					testAddAtIndex(ABC_InextIremove_BC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_InextIremove_BC_testAddAtIndex1C",
					testAddAtIndex(ABC_InextIremove_BC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_InextIremove_BC_testSetNeg1C",
					testSet(ABC_InextIremove_BC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_InextIremove_BC_testSet0C",
					testSet(ABC_InextIremove_BC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_InextIremove_BC_testAddC", testAdd(ABC_InextIremove_BC(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextIremove_BC_testGetNeg1",
					testGet(ABC_InextIremove_BC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_InextIremove_BC_testGet0",
					testGet(ABC_InextIremove_BC(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testIndexOfA", testIndexOf(ABC_InextIremove_BC(), ELEMENT_B, 0));
			printTest("ABC_InextIremove_BC_testIndexOfB", testIndexOf(ABC_InextIremove_BC(), ELEMENT_C, 1));
			printTest("ABC_InextIremove_BC_testIndexOfC", testIndexOf(ABC_InextIremove_BC(), ELEMENT_D, -1));
			printTest("ABC_InextIremove_BC_testRemoveNeg1",
					testRemoveIndex(ABC_InextIremove_BC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_InextIremove_BC_testRemove0",
					testRemoveIndex(ABC_InextIremove_BC(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testRemove2",
					testRemoveIndex(ABC_InextIremove_BC(), 2, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("ABC_InextIremove_BC_testIterator", testIterator(ABC_InextIremove_BC(), Result.NoException));
			printTest("ABC_InextIremove_BC_testIteratorHasNext",
					testIteratorHasNext(ABC_InextIremove_BC().iterator(), Result.True));
			printTest("ABC_InextIremove_BC_testIteratorNext",
					testIteratorNext(ABC_InextIremove_BC().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_testIteratorRemove",
					testIteratorRemove(ABC_InextIremove_BC().iterator(), Result.IllegalState));
			printTest("ABC_InextIremove_BC_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(ABC_InextIremove_BC(), 1), Result.True));
			printTest("ABC_InextIremove_BC_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(ABC_InextIremove_BC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(ABC_InextIremove_BC(), 1), Result.NoException));
			printTest("ABC_InextIremove_BC_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(ABC_InextIremove_BC(), 1)), Result.True));
			printTest("ABC_InextIremove_BC_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(ABC_InextIremove_BC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextIremove_BC_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(ABC_InextIremove_BC(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B,C] -> Iterator remove -> [A, C]
	 * 
	 * @return [A,C] after remove
	 */
	private DoubleLinkedListADT<Integer> ABC_InextInextIremove_AC() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		ListIterator<Integer> lIterator = list.listIterator();
		lIterator.next();
		lIterator.next();
		lIterator.remove();
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> Iterator remove -> [A, C] */
	private void test_ABC_InextInextIremove_AC() {

		try {
			// ListADT
			printTest("ABC_InextInextIremove_AC_testRemoveFirst",
					testRemoveFirst(ABC_InextInextIremove_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testRemoveLast",
					testRemoveLast(ABC_InextInextIremove_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testRemoveA",
					testRemoveElement(ABC_InextInextIremove_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testRemoveB",
					testRemoveElement(ABC_InextInextIremove_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testRemoveC",
					testRemoveElement(ABC_InextInextIremove_AC(), ELEMENT_D, Result.ElementNotFound));
			printTest("ABC_InextInextIremove_AC_testFirst",
					testFirst(ABC_InextInextIremove_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testLast",
					testLast(ABC_InextInextIremove_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testContainsA",
					testContains(ABC_InextInextIremove_AC(), ELEMENT_A, Result.True));
			printTest("ABC_InextInextIremove_AC_testContainsB",
					testContains(ABC_InextInextIremove_AC(), ELEMENT_C, Result.True));
			printTest("ABC_InextInextIremove_AC_testContainsC",
					testContains(ABC_InextInextIremove_AC(), ELEMENT_D, Result.False));
			printTest("ABC_InextInextIremove_AC_testIsEmpty", testIsEmpty(ABC_InextInextIremove_AC(), Result.False));
			printTest("ABC_InextInextIremove_AC_testSize", testSize(ABC_InextInextIremove_AC(), 2));
			printTest("ABC_InextInextIremove_AC_testToString",
					testToString(ABC_InextInextIremove_AC(), Result.ValidString));
			// UnorderedListADT
			printTest("ABC_InextInextIremove_AC_testAddToFrontC",
					testAddToFront(ABC_InextInextIremove_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextIremove_AC_testAddToRearC",
					testAddToRear(ABC_InextInextIremove_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextIremove_AC_testAddAfterDC",
					testAddAfter(ABC_InextInextIremove_AC(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			printTest("ABC_InextInextIremove_AC_testAddAfterAC",
					testAddAfter(ABC_InextInextIremove_AC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("ABC_InextInextIremove_AC_testAddAtIndexNeg1C",
					testAddAtIndex(ABC_InextInextIremove_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_InextInextIremove_AC_testAddAtIndex0C",
					testAddAtIndex(ABC_InextInextIremove_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextIremove_AC_testAddAtIndex1C",
					testAddAtIndex(ABC_InextInextIremove_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextIremove_AC_testSetNeg1C",
					testSet(ABC_InextInextIremove_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_InextInextIremove_AC_testSet0C",
					testSet(ABC_InextInextIremove_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextIremove_AC_testAddC",
					testAdd(ABC_InextInextIremove_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextIremove_AC_testGetNeg1",
					testGet(ABC_InextInextIremove_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_InextInextIremove_AC_testGet0",
					testGet(ABC_InextInextIremove_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testIndexOfA", testIndexOf(ABC_InextInextIremove_AC(), ELEMENT_A, 0));
			printTest("ABC_InextInextIremove_AC_testIndexOfB", testIndexOf(ABC_InextInextIremove_AC(), ELEMENT_C, 1));
			printTest("ABC_InextInextIremove_AC_testIndexOfC", testIndexOf(ABC_InextInextIremove_AC(), ELEMENT_D, -1));
			printTest("ABC_InextInextIremove_AC_testRemoveNeg1",
					testRemoveIndex(ABC_InextInextIremove_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_InextInextIremove_AC_testRemove0",
					testRemoveIndex(ABC_InextInextIremove_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testRemove2",
					testRemoveIndex(ABC_InextInextIremove_AC(), 2, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("ABC_InextInextIremove_AC_testIterator",
					testIterator(ABC_InextInextIremove_AC(), Result.NoException));
			printTest("ABC_InextInextIremove_AC_testIteratorHasNext",
					testIteratorHasNext(ABC_InextInextIremove_AC().iterator(), Result.True));
			printTest("ABC_InextInextIremove_AC_testIteratorNext",
					testIteratorNext(ABC_InextInextIremove_AC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_testIteratorRemove",
					testIteratorRemove(ABC_InextInextIremove_AC().iterator(), Result.IllegalState));
			printTest("ABC_InextInextIremove_AC_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(ABC_InextInextIremove_AC(), 1), Result.True));
			printTest("ABC_InextInextIremove_AC_iteratorNext_testIteratorNext", testIteratorNext(
					iteratorAfterNext(ABC_InextInextIremove_AC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(ABC_InextInextIremove_AC(), 1), Result.NoException));
			printTest("ABC_InextInextIremove_AC_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(ABC_InextInextIremove_AC(), 1)), Result.True));
			printTest("ABC_InextInextIremove_AC_iteratorNextRemove_testIteratorNext",
					testIteratorNext(iteratorAfterRemove(iteratorAfterNext(ABC_InextInextIremove_AC(), 1)), ELEMENT_C,
							Result.MatchingValue));
			printTest("ABC_InextInextIremove_AC_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(ABC_InextInextIremove_AC(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B,C] -> Iterator remove -> [A, B]
	 * 
	 * @return [A,B] after remove
	 */
	private DoubleLinkedListADT<Integer> ABC_InextInextInextIremove_AB() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		ListIterator<Integer> lIterator = list.listIterator();
		lIterator.next();
		lIterator.next();
		lIterator.next();
		lIterator.remove();
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> Iterator remove -> [A, B] */
	private void test_ABC_InextInextInextIremove_AB() {

		try {
			// ListADT
			printTest("ABC_InextInextInextIremove_AB_testRemoveFirst",
					testRemoveFirst(ABC_InextInextInextIremove_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testRemoveLast",
					testRemoveLast(ABC_InextInextInextIremove_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testRemoveA",
					testRemoveElement(ABC_InextInextInextIremove_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testRemoveB",
					testRemoveElement(ABC_InextInextInextIremove_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testRemoveC",
					testRemoveElement(ABC_InextInextInextIremove_AB(), ELEMENT_D, Result.ElementNotFound));
			printTest("ABC_InextInextInextIremove_AB_testFirst",
					testFirst(ABC_InextInextInextIremove_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testLast",
					testLast(ABC_InextInextInextIremove_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testContainsA",
					testContains(ABC_InextInextInextIremove_AB(), ELEMENT_A, Result.True));
			printTest("ABC_InextInextInextIremove_AB_testContainsB",
					testContains(ABC_InextInextInextIremove_AB(), ELEMENT_B, Result.True));
			printTest("ABC_InextInextInextIremove_AB_testContainsC",
					testContains(ABC_InextInextInextIremove_AB(), ELEMENT_D, Result.False));
			printTest("ABC_InextInextInextIremove_AB_testIsEmpty",
					testIsEmpty(ABC_InextInextInextIremove_AB(), Result.False));
			printTest("ABC_InextInextInextIremove_AB_testSize", testSize(ABC_InextInextInextIremove_AB(), 2));
			printTest("ABC_InextInextInextIremove_AB_testToString",
					testToString(ABC_InextInextInextIremove_AB(), Result.ValidString));
			// UnorderedListADT
			printTest("ABC_InextInextInextIremove_AB_testAddToFrontC",
					testAddToFront(ABC_InextInextInextIremove_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextInextIremove_AB_testAddToRearC",
					testAddToRear(ABC_InextInextInextIremove_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextInextIremove_AB_testAddAfterDC",
					testAddAfter(ABC_InextInextInextIremove_AB(), ELEMENT_D, ELEMENT_B, Result.ElementNotFound));
			printTest("ABC_InextInextInextIremove_AB_testAddAfterAC",
					testAddAfter(ABC_InextInextInextIremove_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("ABC_InextInextInextIremove_AB_testAddAtIndexNeg1C",
					testAddAtIndex(ABC_InextInextInextIremove_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_InextInextInextIremove_AB_testAddAtIndex0C",
					testAddAtIndex(ABC_InextInextInextIremove_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextInextIremove_AB_testAddAtIndex1C",
					testAddAtIndex(ABC_InextInextInextIremove_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextInextIremove_AB_testSetNeg1C",
					testSet(ABC_InextInextInextIremove_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_InextInextInextIremove_AB_testSet0C",
					testSet(ABC_InextInextInextIremove_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextInextIremove_AB_testAddC",
					testAdd(ABC_InextInextInextIremove_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_InextInextInextIremove_AB_testGetNeg1",
					testGet(ABC_InextInextInextIremove_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_InextInextInextIremove_AB_testGet0",
					testGet(ABC_InextInextInextIremove_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testIndexOfA",
					testIndexOf(ABC_InextInextInextIremove_AB(), ELEMENT_A, 0));
			printTest("ABC_InextInextInextIremove_AB_testIndexOfB",
					testIndexOf(ABC_InextInextInextIremove_AB(), ELEMENT_B, 1));
			printTest("ABC_InextInextInextIremove_AB_testIndexOfC",
					testIndexOf(ABC_InextInextInextIremove_AB(), ELEMENT_D, -1));
			printTest("ABC_InextInextInextIremove_AB_testRemoveNeg1",
					testRemoveIndex(ABC_InextInextInextIremove_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_InextInextInextIremove_AB_testRemove0",
					testRemoveIndex(ABC_InextInextInextIremove_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testRemove2",
					testRemoveIndex(ABC_InextInextInextIremove_AB(), 2, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("ABC_InextInextInextIremove_AB_testIterator",
					testIterator(ABC_InextInextInextIremove_AB(), Result.NoException));
			printTest("ABC_InextInextInextIremove_AB_testIteratorHasNext",
					testIteratorHasNext(ABC_InextInextInextIremove_AB().iterator(), Result.True));
			printTest("ABC_InextInextInextIremove_AB_testIteratorNext",
					testIteratorNext(ABC_InextInextInextIremove_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_testIteratorRemove",
					testIteratorRemove(ABC_InextInextInextIremove_AB().iterator(), Result.IllegalState));
			printTest("ABC_InextInextInextIremove_AB_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(ABC_InextInextInextIremove_AB(), 1), Result.True));
			printTest("ABC_InextInextInextIremove_AB_iteratorNext_testIteratorNext", testIteratorNext(
					iteratorAfterNext(ABC_InextInextInextIremove_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(ABC_InextInextInextIremove_AB(), 1), Result.NoException));
			printTest("ABC_InextInextInextIremove_AB_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(ABC_InextInextInextIremove_AB(), 1)), Result.True));
			printTest("ABC_InextInextInextIremove_AB_iteratorNextRemove_testIteratorNext",
					testIteratorNext(iteratorAfterRemove(iteratorAfterNext(ABC_InextInextInextIremove_AB(), 1)),
							ELEMENT_B, Result.MatchingValue));
			printTest("ABC_InextInextInextIremove_AB_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(ABC_InextInextInextIremove_AB(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A] -> ListIterator remove() -> [ ]
	 * 
	 * @return [ ] after remove()
	 */
	private DoubleLinkedListADT<Integer> A_InextIpreviousIremove_emptyList() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		ListIterator<Integer> lInt = list.listIterator();
		lInt.next();
		lInt.previous();
		lInt.remove();
		return list;
	}

	/** Run all tests on scenario: [A] -> remove() -> [ ] */
	private void test_A_InextIpreviousIremove_emptyList() {

		try {
			// ListADT
			printTest("A_InextIpreviousIremove_emptyList_testRemoveFirst",
					testRemoveFirst(A_InextIpreviousIremove_emptyList(), null, Result.EmptyCollection));
			printTest("A_InextIpreviousIremove_emptyList_testRemoveLast",
					testRemoveLast(A_InextIpreviousIremove_emptyList(), null, Result.EmptyCollection));
			printTest("A_InextIpreviousIremove_emptyList_testRemoveA",
					testRemoveElement(A_InextIpreviousIremove_emptyList(), null, Result.ElementNotFound));
			printTest("A_InextIpreviousIremove_emptyList_testFirst",
					testFirst(A_InextIpreviousIremove_emptyList(), null, Result.EmptyCollection));
			printTest("A_InextIpreviousIremove_emptyList_testLast",
					testLast(A_InextIpreviousIremove_emptyList(), null, Result.EmptyCollection));
			printTest("A_InextIpreviousIremove_emptyList_testContainsA",
					testContains(A_InextIpreviousIremove_emptyList(), ELEMENT_A, Result.False));
			printTest("A_InextIpreviousIremove_emptyList_testIsEmpty",
					testIsEmpty(A_InextIpreviousIremove_emptyList(), Result.True));
			printTest("A_InextIpreviousIremove_emptyList_testSize", testSize(A_InextIpreviousIremove_emptyList(), 0));
			printTest("A_InextIpreviousIremove_emptyList_testToString",
					testToString(A_InextIpreviousIremove_emptyList(), Result.ValidString));
			// UnorderedListADT
			printTest("A_InextIpreviousIremove_emptyList_testAddToFrontA",
					testAddToFront(A_InextIpreviousIremove_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_InextIpreviousIremove_emptyList_testAddToRearA",
					testAddToRear(A_InextIpreviousIremove_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_InextIpreviousIremove_emptyList_testAddAfterBA",
					testAddAfter(A_InextIpreviousIremove_emptyList(), ELEMENT_B, ELEMENT_A, Result.ElementNotFound));
			// IndexedListADT
			printTest("A_InextIpreviousIremove_emptyList_testAddAtIndexNeg1",
					testAddAtIndex(A_InextIpreviousIremove_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_InextIpreviousIremove_emptyList_testAddAtIndex0",
					testAddAtIndex(A_InextIpreviousIremove_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_InextIpreviousIremove_emptyList_testAddAtIndex1",
					testAddAtIndex(A_InextIpreviousIremove_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_InextIpreviousIremove_emptyList_testSetNeg1A",
					testSet(A_InextIpreviousIremove_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_InextIpreviousIremove_emptyList_testSet0A",
					testSet(A_InextIpreviousIremove_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_InextIpreviousIremove_emptyList_testAddA",
					testAdd(A_InextIpreviousIremove_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_InextIpreviousIremove_emptyList_testGetNeg1",
					testGet(A_InextIpreviousIremove_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_InextIpreviousIremove_emptyList_testGet0",
					testGet(A_InextIpreviousIremove_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_InextIpreviousIremove_emptyList_testIndexOfA",
					testIndexOf(A_InextIpreviousIremove_emptyList(), ELEMENT_A, -1));
			printTest("A_InextIpreviousIremove_emptyList_testRemoveNeg1",
					testRemoveIndex(A_InextIpreviousIremove_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_InextIpreviousIremove_emptyList_testRemove0",
					testRemoveIndex(A_InextIpreviousIremove_emptyList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_InextIpreviousIremove_emptyList_testIterator",
					testIterator(A_InextIpreviousIremove_emptyList(), Result.NoException));
			printTest("A_InextIpreviousIremove_emptyList_testIteratorHasNext",
					testIteratorHasNext(A_InextIpreviousIremove_emptyList().iterator(), Result.False));
			printTest("A_InextIpreviousIremove_emptyList_testIteratorNext",
					testIteratorNext(A_InextIpreviousIremove_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_InextIpreviousIremove_emptyList_testIteratorRemove",
					testIteratorRemove(A_InextIpreviousIremove_emptyList().iterator(), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "A_InextIpreviousIremove_emptyList");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B] -> listIterator next previous remove -> [B]
	 * 
	 * @return [B] after remove()
	 */
	private DoubleLinkedListADT<Integer> AB_InextIpreviousIremove_B() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		ListIterator<Integer> lInt = list.listIterator();
		lInt.next();
		lInt.previous();
		lInt.remove();
		return list;
	}

	/**
	 * Run all tests on scenario: [A,B] -> listIterator next previous remove ->
	 * [B]
	 */
	private void test_AB_InextIpreviousIremove_B() {

		try {
			// ListADT
			printTest("AB_InextIpreviousIremove_B_testRemoveFirst",
					testRemoveFirst(AB_InextIpreviousIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIremove_B_testRemoveLast",
					testRemoveLast(AB_InextIpreviousIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIremove_B_testRemoveA",
					testRemoveElement(AB_InextIpreviousIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIremove_B_testRemoveB",
					testRemoveElement(AB_InextIpreviousIremove_B(), ELEMENT_C, Result.ElementNotFound));
			printTest("AB_InextIpreviousIremove_B_testFirst",
					testFirst(AB_InextIpreviousIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIremove_B_testLast",
					testLast(AB_InextIpreviousIremove_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIremove_B_testContainsA",
					testContains(AB_InextIpreviousIremove_B(), ELEMENT_B, Result.True));
			printTest("AB_InextIpreviousIremove_B_testContainsB",
					testContains(AB_InextIpreviousIremove_B(), ELEMENT_C, Result.False));
			printTest("AB_InextIpreviousIremove_B_testIsEmpty",
					testIsEmpty(AB_InextIpreviousIremove_B(), Result.False));
			printTest("AB_InextIpreviousIremove_B_testSize", testSize(AB_InextIpreviousIremove_B(), 1));
			printTest("AB_InextIpreviousIremove_B_testToString",
					testToString(AB_InextIpreviousIremove_B(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_InextIpreviousIremove_B_testAddToFrontB",
					testAddToFront(AB_InextIpreviousIremove_B(), ELEMENT_C, Result.NoException));
			printTest("AB_InextIpreviousIremove_B_testAddToRearB",
					testAddToRear(AB_InextIpreviousIremove_B(), ELEMENT_B, Result.NoException));
			printTest("AB_InextIpreviousIremove_B_testAddAfterCB",
					testAddAfter(AB_InextIpreviousIremove_B(), ELEMENT_C, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_InextIpreviousIremove_B_testAddAfterAB",
					testAddAfter(AB_InextIpreviousIremove_B(), ELEMENT_B, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("AB_InextIpreviousIremove_B_testAddAtIndexNeg1B",
					testAddAtIndex(AB_InextIpreviousIremove_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_InextIpreviousIremove_B_testAddAtIndex0B",
					testAddAtIndex(AB_InextIpreviousIremove_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_InextIpreviousIremove_B_testAddAtIndex1B",
					testAddAtIndex(AB_InextIpreviousIremove_B(), 1, ELEMENT_C, Result.NoException));
			printTest("AB_InextIpreviousIremove_B_testSetNeg1B",
					testSet(AB_InextIpreviousIremove_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("AB_InextIpreviousIremove_B_testSet0B",
					testSet(AB_InextIpreviousIremove_B(), 0, ELEMENT_C, Result.NoException));
			printTest("AB_InextIpreviousIremove_B_testAddB",
					testAdd(AB_InextIpreviousIremove_B(), ELEMENT_C, Result.NoException));
			printTest("AB_InextIpreviousIremove_B_testGetNeg1",
					testGet(AB_InextIpreviousIremove_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextIpreviousIremove_B_testGet0",
					testGet(AB_InextIpreviousIremove_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIremove_B_testIndexOfA",
					testIndexOf(AB_InextIpreviousIremove_B(), ELEMENT_B, 0));
			printTest("AB_InextIpreviousIremove_B_testIndexOfB",
					testIndexOf(AB_InextIpreviousIremove_B(), ELEMENT_C, -1));
			printTest("AB_InextIpreviousIremove_B_testRemoveNeg1",
					testRemoveIndex(AB_InextIpreviousIremove_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextIpreviousIremove_B_testRemove0",
					testRemoveIndex(AB_InextIpreviousIremove_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIremove_B_testRemove1",
					testRemoveIndex(AB_InextIpreviousIremove_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_InextIpreviousIremove_B_testIterator",
					testIterator(AB_InextIpreviousIremove_B(), Result.NoException));
			printTest("AB_InextIpreviousIremove_B_testIteratorHasNext",
					testIteratorHasNext(AB_InextIpreviousIremove_B().iterator(), Result.True));
			printTest("AB_InextIpreviousIremove_B_testIteratorNext",
					testIteratorNext(AB_InextIpreviousIremove_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIremove_B_testIteratorRemove",
					testIteratorRemove(AB_InextIpreviousIremove_B().iterator(), Result.IllegalState));
			printTest("AB_InextIpreviousIremove_B_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_InextIpreviousIremove_B(), 1), Result.False));
			printTest("AB_InextIpreviousIremove_B_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_InextIpreviousIremove_B(), 1), null, Result.NoSuchElement));
			printTest("AB_InextIpreviousIremove_B_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_InextIpreviousIremove_B(), 1), Result.NoException));
			printTest("AB_InextIpreviousIremove_B_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(AB_InextIpreviousIremove_B(), 1)), Result.False));
			printTest("AB_InextIpreviousIremove_B_iteratorNextRemove_testIteratorNext",
					testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_InextIpreviousIremove_B(), 1)), null,
							Result.NoSuchElement));
			printTest("AB_InextIpreviousIremove_B_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_InextIpreviousIremove_B(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_InextIpreviousIremove_B");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B] -> List Integer remove -> [A]
	 * 
	 * @return [A] after remove
	 */
	private DoubleLinkedListADT<Integer> AB_InextInextIpreviousIremove_A() {
		DoubleLinkedListADT<Integer> list = newList();
		list.addToFront(ELEMENT_A);
		list.add(ELEMENT_B);
		ListIterator<Integer> lInt = list.listIterator();
		lInt.next();
		lInt.next();
		lInt.previous();
		lInt.remove();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> List Integer remove -> [A] */
	private void test_AB_InextInextIpreviousIremove_A() {

		try {
			// ListADT
			printTest("AB_InextInextIpreviousIremove_A_testRemoveFirst",
					testRemoveFirst(AB_InextInextIpreviousIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIpreviousIremove_A_testRemoveLast",
					testRemoveLast(AB_InextInextIpreviousIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIpreviousIremove_A_testRemoveA",
					testRemoveElement(AB_InextInextIpreviousIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIpreviousIremove_A_testRemoveB",
					testRemoveElement(AB_InextInextIpreviousIremove_A(), ELEMENT_B, Result.ElementNotFound));
			printTest("AB_InextInextIpreviousIremove_A_testFirst",
					testFirst(AB_InextInextIpreviousIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIpreviousIremove_A_testLast",
					testLast(AB_InextInextIpreviousIremove_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIpreviousIremove_A_testContainsA",
					testContains(AB_InextInextIpreviousIremove_A(), ELEMENT_A, Result.True));
			printTest("AB_InextInextIpreviousIremove_A_testContainsB",
					testContains(AB_InextInextIpreviousIremove_A(), ELEMENT_B, Result.False));
			printTest("AB_InextInextIpreviousIremove_A_testIsEmpty",
					testIsEmpty(AB_InextInextIpreviousIremove_A(), Result.False));
			printTest("AB_InextInextIpreviousIremove_A_testSize", testSize(AB_InextInextIpreviousIremove_A(), 1));
			printTest("AB_InextInextIpreviousIremove_A_testToString",
					testToString(AB_InextInextIpreviousIremove_A(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_InextInextIpreviousIremove_A_testAddToFrontB",
					testAddToFront(AB_InextInextIpreviousIremove_A(), ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIpreviousIremove_A_testAddToRearB",
					testAddToRear(AB_InextInextIpreviousIremove_A(), ELEMENT_A, Result.NoException));
			printTest("AB_InextInextIpreviousIremove_A_testAddAfterCB",
					testAddAfter(AB_InextInextIpreviousIremove_A(), ELEMENT_C, ELEMENT_B, Result.ElementNotFound));
			printTest("AB_InextInextIpreviousIremove_A_testAddAfterAB",
					testAddAfter(AB_InextInextIpreviousIremove_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			// IndexedListADT
			printTest("AB_InextInextIpreviousIremove_A_testAddAtIndexNeg1B",
					testAddAtIndex(AB_InextInextIpreviousIremove_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_InextInextIpreviousIremove_A_testAddAtIndex0B",
					testAddAtIndex(AB_InextInextIpreviousIremove_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIpreviousIremove_A_testAddAtIndex1B",
					testAddAtIndex(AB_InextInextIpreviousIremove_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIpreviousIremove_A_testSetNeg1B",
					testSet(AB_InextInextIpreviousIremove_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_InextInextIpreviousIremove_A_testSet0B",
					testSet(AB_InextInextIpreviousIremove_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIpreviousIremove_A_testAddB",
					testAdd(AB_InextInextIpreviousIremove_A(), ELEMENT_B, Result.NoException));
			printTest("AB_InextInextIpreviousIremove_A_testGetNeg1",
					testGet(AB_InextInextIpreviousIremove_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextInextIpreviousIremove_A_testGet0",
					testGet(AB_InextInextIpreviousIremove_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIpreviousIremove_A_testIndexOfA",
					testIndexOf(AB_InextInextIpreviousIremove_A(), ELEMENT_A, 0));
			printTest("AB_InextInextIpreviousIremove_A_testIndexOfB",
					testIndexOf(AB_InextInextIpreviousIremove_A(), ELEMENT_B, -1));
			printTest("AB_InextInextIpreviousIremove_A_testRemoveNeg1",
					testRemoveIndex(AB_InextInextIpreviousIremove_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextInextIpreviousIremove_A_testRemove0",
					testRemoveIndex(AB_InextInextIpreviousIremove_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIpreviousIremove_A_testRemove1",
					testRemoveIndex(AB_InextInextIpreviousIremove_A(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_InextInextIpreviousIremove_A_testIterator",
					testIterator(AB_InextInextIpreviousIremove_A(), Result.NoException));
			printTest("AB_InextInextIpreviousIremove_A_testIteratorHasNext",
					testIteratorHasNext(AB_InextInextIpreviousIremove_A().iterator(), Result.True));
			printTest("AB_InextInextIpreviousIremove_A_testIteratorNext",
					testIteratorNext(AB_InextInextIpreviousIremove_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIpreviousIremove_A_testIteratorRemove",
					testIteratorRemove(AB_InextInextIpreviousIremove_A().iterator(), Result.IllegalState));
			printTest("AB_InextInextIpreviousIremove_A_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_InextInextIpreviousIremove_A(), 1), Result.False));
			printTest("AB_InextInextIpreviousIremove_A_iteratorNext_testIteratorNext", testIteratorNext(
					iteratorAfterNext(AB_InextInextIpreviousIremove_A(), 1), null, Result.NoSuchElement));
			printTest("AB_InextInextIpreviousIremove_A_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_InextInextIpreviousIremove_A(), 1), Result.NoException));
			printTest("AB_InextInextIpreviousIremove_A_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(AB_InextInextIpreviousIremove_A(), 1)), Result.False));
			printTest("AB_InextInextIpreviousIremove_A_iteratorNextRemove_testIteratorNext",
					testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_InextInextIpreviousIremove_A(), 1)), null,
							Result.NoSuchElement));
			printTest("AB_InextInextIpreviousIremove_A_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_InextInextIpreviousIremove_A(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_InextInextIpreviousIremove_A");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B] -> add(C) -> [A,C,B]
	 * 
	 * @return [A,C,B] after add(C)
	 */
	private DoubleLinkedListADT<Integer> AB_InextIaddC_ACB() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		ListIterator<Integer> listIter = list.listIterator();
		listIter.next();
		listIter.add(ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: [A,B]-> add(C) -> [A,C,B] */
	private void test_AB_InextIaddC_ACB() {

		try {
			// ListADT
			printTest("AB_InextIaddC_ACB_testRemoveFirst",
					testRemoveFirst(AB_InextIaddC_ACB(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testRemoveLast",
					testRemoveLast(AB_InextIaddC_ACB(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testRemoveA",
					testRemoveElement(AB_InextIaddC_ACB(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testRemoveC",
					testRemoveElement(AB_InextIaddC_ACB(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testRemoveD",
					testRemoveElement(AB_InextIaddC_ACB(), ELEMENT_D, Result.ElementNotFound));
			printTest("AB_InextIaddC_ACB_testFirst", testFirst(AB_InextIaddC_ACB(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testLast", testLast(AB_InextIaddC_ACB(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testContainsA", testContains(AB_InextIaddC_ACB(), ELEMENT_A, Result.True));
			printTest("AB_InextIaddC_ACB_testContainsB", testContains(AB_InextIaddC_ACB(), ELEMENT_B, Result.True));
			printTest("AB_InextIaddC_ACB_testContainsC", testContains(AB_InextIaddC_ACB(), ELEMENT_C, Result.True));
			printTest("AB_InextIaddC_ACB_testContainsD", testContains(AB_InextIaddC_ACB(), ELEMENT_D, Result.False));
			printTest("AB_InextIaddC_ACB_testIsEmpty", testIsEmpty(AB_InextIaddC_ACB(), Result.False));
			printTest("AB_InextIaddC_ACB_testSize", testSize(AB_InextIaddC_ACB(), 3));
			printTest("AB_InextIaddC_ACB_testToString", testToString(AB_InextIaddC_ACB(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_InextIaddC_ACB_testAddToFrontC",
					testAddToFront(AB_InextIaddC_ACB(), ELEMENT_B, Result.NoException));
			printTest("AB_InextIaddC_ACB_testAddToRearC",
					testAddToRear(AB_InextIaddC_ACB(), ELEMENT_B, Result.NoException));
			printTest("AB_InextIaddC_ACB_testAddAfterDC",
					testAddAfter(AB_InextIaddC_ACB(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_InextIaddC_ACB_testAddAfterAD",
					testAddAfter(AB_InextIaddC_ACB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("AB_InextIaddC_ACB_testAddAtIndexNeg1C",
					testAddAtIndex(AB_InextIaddC_ACB(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_InextIaddC_ACB_testAddAtIndex0C",
					testAddAtIndex(AB_InextIaddC_ACB(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_InextIaddC_ACB_testAddAtIndex1C",
					testAddAtIndex(AB_InextIaddC_ACB(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_InextIaddC_ACB_testSetNeg1C",
					testSet(AB_InextIaddC_ACB(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_InextIaddC_ACB_testSet0C", testSet(AB_InextIaddC_ACB(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_InextIaddC_ACB_testAddC", testAdd(AB_InextIaddC_ACB(), ELEMENT_B, Result.NoException));
			printTest("AB_InextIaddC_ACB_testGetNeg1", testGet(AB_InextIaddC_ACB(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextIaddC_ACB_testGet0", testGet(AB_InextIaddC_ACB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testIndexOfA", testIndexOf(AB_InextIaddC_ACB(), ELEMENT_A, 0));
			printTest("AB_InextIaddC_ACB_testIndexOfB", testIndexOf(AB_InextIaddC_ACB(), ELEMENT_B, 2));
			printTest("AB_InextIaddC_ACB_testIndexOfC", testIndexOf(AB_InextIaddC_ACB(), ELEMENT_C, 1));
			printTest("AB_InextIaddC_ACB_testIndexOfD", testIndexOf(AB_InextIaddC_ACB(), ELEMENT_D, -1));
			printTest("AB_InextIaddC_ACB_testRemoveNeg1",
					testRemoveIndex(AB_InextIaddC_ACB(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextIaddC_ACB_testRemove0",
					testRemoveIndex(AB_InextIaddC_ACB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testRemove3",
					testRemoveIndex(AB_InextIaddC_ACB(), 3, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_InextIaddC_ACB_testIterator", testIterator(AB_InextIaddC_ACB(), Result.NoException));
			printTest("AB_InextIaddC_ACB_testIteratorHasNext",
					testIteratorHasNext(AB_InextIaddC_ACB().iterator(), Result.True));
			printTest("AB_InextIaddC_ACB_testIteratorNext",
					testIteratorNext(AB_InextIaddC_ACB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_testIteratorRemove",
					testIteratorRemove(AB_InextIaddC_ACB().iterator(), Result.IllegalState));
			printTest("AB_InextIaddC_ACB_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_InextIaddC_ACB(), 1), Result.True));
			printTest("AB_InextIaddC_ACB_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_InextIaddC_ACB(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_InextIaddC_ACB(), 1), Result.NoException));
			printTest("AB_InextIaddC_ACB_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(AB_InextIaddC_ACB(), 1)), Result.True));
			printTest("AB_InextIaddC_ACB_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(AB_InextIaddC_ACB(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIaddC_ACB_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_InextIaddC_ACB(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B] -> add(C) -> [A,B,C]
	 * 
	 * @return [A,B,C] after add(C)
	 */
	private DoubleLinkedListADT<Integer> AB_InextInextIaddC_ABC() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		ListIterator<Integer> listIter = list.listIterator();
		listIter.next();
		listIter.next();
		listIter.add(ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> add(C) -> [A,B,C] */
	private void test_AB_InextInextIaddC_ABC() {

		try {
			// ListADT
			printTest("AB_InextInextIaddC_ABC_testRemoveFirst",
					testRemoveFirst(AB_InextInextIaddC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testRemoveLast",
					testRemoveLast(AB_InextInextIaddC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testRemoveA",
					testRemoveElement(AB_InextInextIaddC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testRemoveC",
					testRemoveElement(AB_InextInextIaddC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testRemoveD",
					testRemoveElement(AB_InextInextIaddC_ABC(), ELEMENT_D, Result.ElementNotFound));
			printTest("AB_InextInextIaddC_ABC_testFirst",
					testFirst(AB_InextInextIaddC_ABC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testLast",
					testLast(AB_InextInextIaddC_ABC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testContainsA",
					testContains(AB_InextInextIaddC_ABC(), ELEMENT_A, Result.True));
			printTest("AB_InextInextIaddC_ABC_testContainsB",
					testContains(AB_InextInextIaddC_ABC(), ELEMENT_B, Result.True));
			printTest("AB_InextInextIaddC_ABC_testContainsC",
					testContains(AB_InextInextIaddC_ABC(), ELEMENT_C, Result.True));
			printTest("AB_InextInextIaddC_ABC_testContainsD",
					testContains(AB_InextInextIaddC_ABC(), ELEMENT_D, Result.False));
			printTest("AB_InextInextIaddC_ABC_testIsEmpty", testIsEmpty(AB_InextInextIaddC_ABC(), Result.False));
			printTest("AB_InextInextIaddC_ABC_testSize", testSize(AB_InextInextIaddC_ABC(), 3));
			printTest("AB_InextInextIaddC_ABC_testToString",
					testToString(AB_InextInextIaddC_ABC(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_InextInextIaddC_ABC_testAddToFrontC",
					testAddToFront(AB_InextInextIaddC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_InextInextIaddC_ABC_testAddToRearC",
					testAddToRear(AB_InextInextIaddC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_InextInextIaddC_ABC_testAddAfterDC",
					testAddAfter(AB_InextInextIaddC_ABC(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_InextInextIaddC_ABC_testAddAfterAD",
					testAddAfter(AB_InextInextIaddC_ABC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("AB_InextInextIaddC_ABC_testAddAtIndexNeg1C",
					testAddAtIndex(AB_InextInextIaddC_ABC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_InextInextIaddC_ABC_testAddAtIndex0C",
					testAddAtIndex(AB_InextInextIaddC_ABC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_InextInextIaddC_ABC_testAddAtIndex1C",
					testAddAtIndex(AB_InextInextIaddC_ABC(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_InextInextIaddC_ABC_testSetNeg1C",
					testSet(AB_InextInextIaddC_ABC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_InextInextIaddC_ABC_testSet0C",
					testSet(AB_InextInextIaddC_ABC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_InextInextIaddC_ABC_testAddC",
					testAdd(AB_InextInextIaddC_ABC(), ELEMENT_D, Result.NoException));
			printTest("AB_InextInextIaddC_ABC_testGetNeg1",
					testGet(AB_InextInextIaddC_ABC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextInextIaddC_ABC_testGet0",
					testGet(AB_InextInextIaddC_ABC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testIndexOfA", testIndexOf(AB_InextInextIaddC_ABC(), ELEMENT_A, 0));
			printTest("AB_InextInextIaddC_ABC_testIndexOfB", testIndexOf(AB_InextInextIaddC_ABC(), ELEMENT_B, 1));
			printTest("AB_InextInextIaddC_ABC_testIndexOfC", testIndexOf(AB_InextInextIaddC_ABC(), ELEMENT_C, 2));
			printTest("AB_InextInextIaddC_ABC_testIndexOfD", testIndexOf(AB_InextInextIaddC_ABC(), ELEMENT_D, -1));
			printTest("AB_InextInextIaddC_ABC_testRemoveNeg1",
					testRemoveIndex(AB_InextInextIaddC_ABC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextInextIaddC_ABC_testRemove0",
					testRemoveIndex(AB_InextInextIaddC_ABC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testRemove3",
					testRemoveIndex(AB_InextInextIaddC_ABC(), 3, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_InextInextIaddC_ABC_testIterator",
					testIterator(AB_InextInextIaddC_ABC(), Result.NoException));
			printTest("AB_InextInextIaddC_ABC_testIteratorHasNext",
					testIteratorHasNext(AB_InextInextIaddC_ABC().iterator(), Result.True));
			printTest("AB_InextInextIaddC_ABC_testIteratorNext",
					testIteratorNext(AB_InextInextIaddC_ABC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_testIteratorRemove",
					testIteratorRemove(AB_InextInextIaddC_ABC().iterator(), Result.IllegalState));
			printTest("AB_InextInextIaddC_ABC_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_InextInextIaddC_ABC(), 1), Result.True));
			printTest("AB_InextInextIaddC_ABC_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(AB_InextInextIaddC_ABC(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_InextInextIaddC_ABC(), 1), Result.NoException));
			printTest("AB_InextInextIaddC_ABC_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(AB_InextInextIaddC_ABC(), 1)), Result.True));
			printTest("AB_InextInextIaddC_ABC_iteratorNextRemove_testIteratorNext",
					testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_InextInextIaddC_ABC(), 1)), ELEMENT_B,
							Result.MatchingValue));
			printTest("AB_InextInextIaddC_ABC_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_InextInextIaddC_ABC(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A,B] -> add(C) -> [C,A,B]
	 * 
	 * @return [C,A,B] after add(C)
	 */
	private DoubleLinkedListADT<Integer> AB_InextIpreviousIaddC_CAB() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		ListIterator<Integer> listIter = list.listIterator();
		listIter.next();
		listIter.previous();
		listIter.add(ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: [A,B] -> add(C) -> [C,A,B] */
	private void test_AB_InextIpreviousIaddC_CAB() {

		try {
			// ListADT
			printTest("AB_InextIpreviousIaddC_CAB_testRemoveFirst",
					testRemoveFirst(AB_InextIpreviousIaddC_CAB(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testRemoveLast",
					testRemoveLast(AB_InextIpreviousIaddC_CAB(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testRemoveA",
					testRemoveElement(AB_InextIpreviousIaddC_CAB(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testRemoveC",
					testRemoveElement(AB_InextIpreviousIaddC_CAB(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testRemoveD",
					testRemoveElement(AB_InextIpreviousIaddC_CAB(), ELEMENT_D, Result.ElementNotFound));
			printTest("AB_InextIpreviousIaddC_CAB_testFirst",
					testFirst(AB_InextIpreviousIaddC_CAB(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testLast",
					testLast(AB_InextIpreviousIaddC_CAB(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testContainsA",
					testContains(AB_InextIpreviousIaddC_CAB(), ELEMENT_A, Result.True));
			printTest("AB_InextIpreviousIaddC_CAB_testContainsB",
					testContains(AB_InextIpreviousIaddC_CAB(), ELEMENT_B, Result.True));
			printTest("AB_InextIpreviousIaddC_CAB_testContainsC",
					testContains(AB_InextIpreviousIaddC_CAB(), ELEMENT_C, Result.True));
			printTest("AB_InextIpreviousIaddC_CAB_testContainsD",
					testContains(AB_InextIpreviousIaddC_CAB(), ELEMENT_D, Result.False));
			printTest("AB_InextIpreviousIaddC_CAB_testIsEmpty",
					testIsEmpty(AB_InextIpreviousIaddC_CAB(), Result.False));
			printTest("AB_InextIpreviousIaddC_CAB_testSize", testSize(AB_InextIpreviousIaddC_CAB(), 3));
			printTest("AB_InextIpreviousIaddC_CAB_testToString",
					testToString(AB_InextIpreviousIaddC_CAB(), Result.ValidString));
			// UnorderedListADT
			printTest("AB_InextIpreviousIaddC_CAB_testAddToFrontD",
					testAddToFront(AB_InextIpreviousIaddC_CAB(), ELEMENT_D, Result.NoException));
			printTest("AB_InextIpreviousIaddC_CAB_testAddToRearD",
					testAddToRear(AB_InextIpreviousIaddC_CAB(), ELEMENT_D, Result.NoException));
			printTest("AB_InextIpreviousIaddC_CAB_testAddAfterDC",
					testAddAfter(AB_InextIpreviousIaddC_CAB(), ELEMENT_D, ELEMENT_C, Result.ElementNotFound));
			printTest("AB_InextIpreviousIaddC_CAB_testAddAfterAD",
					testAddAfter(AB_InextIpreviousIaddC_CAB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			// IndexedListADT
			printTest("AB_InextIpreviousIaddC_CAB_testAddAtIndexNeg1C",
					testAddAtIndex(AB_InextIpreviousIaddC_CAB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_InextIpreviousIaddC_CAB_testAddAtIndex0C",
					testAddAtIndex(AB_InextIpreviousIaddC_CAB(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_InextIpreviousIaddC_CAB_testAddAtIndex1C",
					testAddAtIndex(AB_InextIpreviousIaddC_CAB(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_InextIpreviousIaddC_CAB_testSetNeg1C",
					testSet(AB_InextIpreviousIaddC_CAB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_InextIpreviousIaddC_CAB_testSet0C",
					testSet(AB_InextIpreviousIaddC_CAB(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_InextIpreviousIaddC_CAB_testAddC",
					testAdd(AB_InextIpreviousIaddC_CAB(), ELEMENT_D, Result.NoException));
			printTest("AB_InextIpreviousIaddC_CAB_testGetNeg1",
					testGet(AB_InextIpreviousIaddC_CAB(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextIpreviousIaddC_CAB_testGet0",
					testGet(AB_InextIpreviousIaddC_CAB(), 0, ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testIndexOfA",
					testIndexOf(AB_InextIpreviousIaddC_CAB(), ELEMENT_A, 1));
			printTest("AB_InextIpreviousIaddC_CAB_testIndexOfB",
					testIndexOf(AB_InextIpreviousIaddC_CAB(), ELEMENT_B, 2));
			printTest("AB_InextIpreviousIaddC_CAB_testIndexOfC",
					testIndexOf(AB_InextIpreviousIaddC_CAB(), ELEMENT_C, 0));
			printTest("AB_InextIpreviousIaddC_CAB_testIndexOfD",
					testIndexOf(AB_InextIpreviousIaddC_CAB(), ELEMENT_D, -1));
			printTest("AB_InextIpreviousIaddC_CAB_testRemoveNeg1",
					testRemoveIndex(AB_InextIpreviousIaddC_CAB(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_InextIpreviousIaddC_CAB_testRemove0",
					testRemoveIndex(AB_InextIpreviousIaddC_CAB(), 0, ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testRemove3",
					testRemoveIndex(AB_InextIpreviousIaddC_CAB(), 3, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("AB_InextIpreviousIaddC_CAB_testIterator",
					testIterator(AB_InextIpreviousIaddC_CAB(), Result.NoException));
			printTest("AB_InextIpreviousIaddC_CAB_testIteratorHasNext",
					testIteratorHasNext(AB_InextIpreviousIaddC_CAB().iterator(), Result.True));
			printTest("AB_InextIpreviousIaddC_CAB_testIteratorNext",
					testIteratorNext(AB_InextIpreviousIaddC_CAB().iterator(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_testIteratorRemove",
					testIteratorRemove(AB_InextIpreviousIaddC_CAB().iterator(), Result.IllegalState));
			printTest("AB_InextIpreviousIaddC_CAB_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(AB_InextIpreviousIaddC_CAB(), 1), Result.True));
			printTest("AB_InextIpreviousIaddC_CAB_iteratorNext_testIteratorNext", testIteratorNext(
					iteratorAfterNext(AB_InextIpreviousIaddC_CAB(), 1), ELEMENT_A, Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(AB_InextIpreviousIaddC_CAB(), 1), Result.NoException));
			printTest("AB_InextIpreviousIaddC_CAB_iteratorNextRemove_testIteratorHasNext", testIteratorHasNext(
					iteratorAfterRemove(iteratorAfterNext(AB_InextIpreviousIaddC_CAB(), 1)), Result.True));
			printTest("AB_InextIpreviousIaddC_CAB_iteratorNextRemove_testIteratorNext",
					testIteratorNext(iteratorAfterRemove(iteratorAfterNext(AB_InextIpreviousIaddC_CAB(), 1)), ELEMENT_A,
							Result.MatchingValue));
			printTest("AB_InextIpreviousIaddC_CAB_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(AB_InextIpreviousIaddC_CAB(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	/**
	 * Scenario: [A] -> listIterator next set(B) -> [B]
	 * 
	 * @return [B] after set(B)
	 */
	private DoubleLinkedListADT<Integer> A_InextIsetB_B() {
		DoubleLinkedListADT<Integer> list = newList();
		list.add(ELEMENT_A);
		ListIterator<Integer> lInt = list.listIterator();
		lInt.next();
		lInt.set(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A] -> listIterator next set(B) -> [B] */
	private void test_A_InextIsetB_B() {

		// try-catch is necessary to prevent an Exception from the scenario
		// builder method from bringing
		// down the whole test suite
		try {
			// ListADT
			printTest("A_InextIsetB_B_testRemoveFirst",
					testRemoveFirst(A_InextIsetB_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_InextIsetB_B_testRemoveLast",
					testRemoveLast(A_InextIsetB_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_InextIsetB_B_testRemoveA",
					testRemoveElement(A_InextIsetB_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_InextIsetB_B_testRemoveB",
					testRemoveElement(A_InextIsetB_B(), ELEMENT_C, Result.ElementNotFound));
			printTest("A_InextIsetB_B_testFirst", testFirst(A_InextIsetB_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_InextIsetB_B_testLast", testLast(A_InextIsetB_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_InextIsetB_B_testContainsA", testContains(A_InextIsetB_B(), ELEMENT_B, Result.True));
			printTest("A_InextIsetB_B_testContainsB", testContains(A_InextIsetB_B(), ELEMENT_C, Result.False));
			printTest("A_InextIsetB_B_testIsEmpty", testIsEmpty(A_InextIsetB_B(), Result.False));
			printTest("A_InextIsetB_B_testSize", testSize(A_InextIsetB_B(), 1));
			printTest("A_InextIsetB_B_testToString", testToString(A_InextIsetB_B(), Result.ValidString));
			// UnorderedListADT
			printTest("A_InextIsetB_B_testAddToFrontB",
					testAddToFront(A_InextIsetB_B(), ELEMENT_C, Result.NoException));
			printTest("A_InextIsetB_B_testAddToRearB", testAddToRear(A_InextIsetB_B(), ELEMENT_B, Result.NoException));
			printTest("A_InextIsetB_B_testAddAfterCB",
					testAddAfter(A_InextIsetB_B(), ELEMENT_C, ELEMENT_C, Result.ElementNotFound));
			printTest("A_InextIsetB_B_testAddAfterAB",
					testAddAfter(A_InextIsetB_B(), ELEMENT_B, ELEMENT_C, Result.NoException));
			// IndexedListADT
			printTest("A_InextIsetB_B_testAddAtIndexNeg1B",
					testAddAtIndex(A_InextIsetB_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_InextIsetB_B_testAddAtIndex0B",
					testAddAtIndex(A_InextIsetB_B(), 0, ELEMENT_C, Result.NoException));
			printTest("A_InextIsetB_B_testAddAtIndex1B",
					testAddAtIndex(A_InextIsetB_B(), 1, ELEMENT_C, Result.NoException));
			printTest("A_InextIsetB_B_testSetNeg1B", testSet(A_InextIsetB_B(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_InextIsetB_B_testSet0B", testSet(A_InextIsetB_B(), 0, ELEMENT_C, Result.NoException));
			printTest("A_InextIsetB_B_testAddB", testAdd(A_InextIsetB_B(), ELEMENT_C, Result.NoException));
			printTest("A_InextIsetB_B_testGetNeg1", testGet(A_InextIsetB_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_InextIsetB_B_testGet0", testGet(A_InextIsetB_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_InextIsetB_B_testIndexOfA", testIndexOf(A_InextIsetB_B(), ELEMENT_B, 0));
			printTest("A_InextIsetB_B_testIndexOfB", testIndexOf(A_InextIsetB_B(), ELEMENT_C, -1));
			printTest("A_InextIsetB_B_testRemoveNeg1",
					testRemoveIndex(A_InextIsetB_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_InextIsetB_B_testRemove0",
					testRemoveIndex(A_InextIsetB_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_InextIsetB_B_testRemove1",
					testRemoveIndex(A_InextIsetB_B(), 1, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_InextIsetB_B_testIterator", testIterator(A_InextIsetB_B(), Result.NoException));
			printTest("A_InextIsetB_B_testIteratorHasNext",
					testIteratorHasNext(A_InextIsetB_B().iterator(), Result.True));
			printTest("A_InextIsetB_B_testIteratorNext",
					testIteratorNext(A_InextIsetB_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_InextIsetB_B_testIteratorRemove",
					testIteratorRemove(A_InextIsetB_B().iterator(), Result.IllegalState));
			printTest("A_InextIsetB_B_iteratorNext_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterNext(A_InextIsetB_B(), 1), Result.False));
			printTest("A_InextIsetB_B_iteratorNext_testIteratorNext",
					testIteratorNext(iteratorAfterNext(A_InextIsetB_B(), 1), null, Result.NoSuchElement));
			printTest("A_InextIsetB_B_iteratorNext_testIteratorRemove",
					testIteratorRemove(iteratorAfterNext(A_InextIsetB_B(), 1), Result.NoException));
			printTest("A_InextIsetB_B_iteratorNextRemove_testIteratorHasNext",
					testIteratorHasNext(iteratorAfterRemove(iteratorAfterNext(A_InextIsetB_B(), 1)), Result.False));
			printTest("A_InextIsetB_B_iteratorNextRemove_testIteratorNext", testIteratorNext(
					iteratorAfterRemove(iteratorAfterNext(A_InextIsetB_B(), 1)), null, Result.NoSuchElement));
			printTest("A_InextIsetB_B_iteratorNextRemove_testIteratorRemove", testIteratorRemove(
					iteratorAfterRemove(iteratorAfterNext(A_InextIsetB_B(), 1)), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_InextIsetB_B");
			e.printStackTrace();
		}
	}

	// //////////////////////////
	// LIST_ADT TESTS XXX
	// //////////////////////////

	/**
	 * Runs removeFirst() method on given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param expectedElement
	 *            element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveFirst(IndexedUnorderedListADT<Integer> list, Integer expectedElement,
			Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.removeFirst();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (EmptyCollectionException e) {
			result = Result.EmptyCollection;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveFirst", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeLast() method on given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param expectedElement
	 *            element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveLast(IndexedUnorderedListADT<Integer> list, Integer expectedElement,
			Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.removeLast();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (EmptyCollectionException e) {
			result = Result.EmptyCollection;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveLast", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeLast() method on given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param element
	 *            element to remove
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveElement(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.remove(element);
			if (retVal.equals(element)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (ElementNotFoundException e) {
			result = Result.ElementNotFound;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveElement", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs first() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param expectedElement
	 *            element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testFirst(IndexedUnorderedListADT<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.first();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (EmptyCollectionException e) {
			result = Result.EmptyCollection;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testFirst", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs last() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param expectedElement
	 *            element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testLast(IndexedUnorderedListADT<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.last();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (EmptyCollectionException e) {
			result = Result.EmptyCollection;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testLast", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs contains() method on a given list and element and checks result
	 * against expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testContains(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			if (list.contains(element)) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testContains", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs isEmpty() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIsEmpty(IndexedUnorderedListADT<Integer> list, Result expectedResult) {
		Result result;
		try {
			if (list.isEmpty()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIsEmpty", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs size() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param expectedSize
	 * @return test success
	 */
	private boolean testSize(IndexedUnorderedListADT<Integer> list, int expectedSize) {
		try {
			return (list.size() == expectedSize);
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testSize", e.toString());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Runs toString() method on given list and attempts to confirm non-default
	 * or empty String difficult to test - just confirm that default address
	 * output has been overridden
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testToString(IndexedUnorderedListADT<Integer> list, Result expectedResult) {
		Result result;
		try {
			String str = list.toString();
			System.out.println("toString() output: " + str);
			if (str.length() == 0) {
				result = Result.Fail;
			}
			char lastChar = str.charAt(str.length() - 1);
			if (str.contains("@") && !str.contains(" ") && Character.isLetter(str.charAt(0))
					&& (Character.isDigit(lastChar) || (lastChar >= 'a' && lastChar <= 'f'))) {
				result = Result.Fail; // looks like default toString()
			} else {
				result = Result.ValidString;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testToString", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	// /////////////////////////////////////////
	// UNORDERED_LIST_ADT TESTS XXX
	// /////////////////////////////////////////

	/**
	 * Runs addToFront() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddToFront(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addToFront(element);
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddToFront", e.toString());
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs addToRear() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddToRear(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addToRear(element);
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddToRear", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs addAfter() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param target
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddAfter(IndexedUnorderedListADT<Integer> list, Integer target, Integer element,
			Result expectedResult) {
		Result result;
		try {
			list.addAfter(element, target);
			result = Result.NoException;
		} catch (ElementNotFoundException e) {
			result = Result.ElementNotFound;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAfter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	// /////////////////////////////////////
	// INDEXED_LIST_ADT TESTS XXX
	// /////////////////////////////////////

	/**
	 * Runs add(int, T) method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param index
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddAtIndex(IndexedUnorderedListADT<Integer> list, int index, Integer element,
			Result expectedResult) {
		Result result;
		try {
			list.add(index, element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAtIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs add(T) method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAdd(IndexedUnorderedListADT<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.add(element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAtIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs set(int, T) method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param index
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testSet(IndexedUnorderedListADT<Integer> list, int index, Integer element, Result expectedResult) {
		Result result;
		try {
			list.set(index, element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testSet", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs get() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param index
	 * @param expectedElement
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testGet(IndexedUnorderedListADT<Integer> list, int index, Integer expectedElement,
			Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.get(index);
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testGet", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs remove(index) method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param index
	 * @param expectedElement
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveIndex(IndexedUnorderedListADT<Integer> list, int index, Integer expectedElement,
			Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.remove(index);
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs indexOf() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param element
	 * @param expectedIndex
	 * @return test success
	 */
	private boolean testIndexOf(IndexedUnorderedListADT<Integer> list, Integer element, int expectedIndex) {
		// Result result;
		try {
			return list.indexOf(element) == expectedIndex;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIndexOf", e.toString());
			e.printStackTrace();
			return false;
		}
	}

	////////////////////////////
	// ITERATOR TESTS XXX
	////////////////////////////

	/**
	 * Runs iterator() method on a given list and checks result against
	 * expectedResult
	 * 
	 * @param list
	 *            a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIterator(IndexedUnorderedListADT<Integer> list, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			Iterator<Integer> it = list.iterator();
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterator", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator hasNext() method on a given list and checks result
	 * against expectedResult
	 * 
	 * @param iterator
	 *            an iterator already positioned for the call to hasNext()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIteratorHasNext(Iterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			if (iterator.hasNext()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIteratorHasNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator next() method on a given list and checks result
	 * against expectedResult
	 * 
	 * @param iterator
	 *            an iterator already positioned for the call to hasNext()
	 * @param expectedValue
	 *            the Integer expected from next() or null if an exception is
	 *            expected
	 * @param expectedResult
	 *            MatchingValue or expected exception
	 * @return test success
	 */
	private boolean testIteratorNext(Iterator<Integer> iterator, Integer expectedValue, Result expectedResult) {
		Result result;
		try {
			Integer retVal = iterator.next();
			if (retVal.equals(expectedValue)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (NoSuchElementException e) {
			result = Result.NoSuchElement;
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIteratorNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator remove() method on a given list and checks result
	 * against expectedResult
	 * 
	 * @param iterator
	 *            an iterator already positioned for the call to remove()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIteratorRemove(Iterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			iterator.remove();
			result = Result.NoException;
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIteratorRemove", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	//////////////////////////////////////////////////////////
	// HELPER METHODS FOR TESTING ITERATORS XXX
	//////////////////////////////////////////////////////////
	/**
	 * Helper for testing iterators. Return an Iterator that has been advanced
	 * numCallsToNext times.
	 * 
	 * @param list
	 * @param numCallsToNext
	 * @return Iterator for given list, after numCallsToNext
	 */
	private Iterator<Integer> iteratorAfterNext(IndexedUnorderedListADT<Integer> list, int numCallsToNext) {
		Iterator<Integer> it = list.iterator();
		for (int i = 0; i < numCallsToNext; i++) {
			it.next();
		}
		return it;
	}

	/**
	 * Helper for testing iterators. Return an Iterator that has had remove()
	 * called once.
	 * 
	 * @param iterator
	 * @return same Iterator following a call to remove()
	 */
	private Iterator<Integer> iteratorAfterRemove(Iterator<Integer> iterator) {
		iterator.remove();
		return iterator;
	}

}// end class ListTester

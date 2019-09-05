import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class HashTest {
	private static int mode;
	private static int debug;
	private static float loadFactor;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args){
		if (args.length > 3 || args.length < 2) {
			printUsage();
			return;
		}
		switch (args[0]) {
		case "1":
			mode = 1;
			break;
		case "2":
			mode = 2;
			break;
		case "3":
			mode = 3;
			break;
		default:
			printUsage();
			return;
		}
		if (args.length == 3) {
			switch (args[2]) {
			case "1":
				debug = 1;
				break;
			case "2":
				debug = 2;
				break;
			case "0":
				debug = 0;
				break;
			default:
				printUsage();
				return;
			}
		} else {
			debug = 0;
		}

		try {
			loadFactor = Float.parseFloat(args[1]);
			if (loadFactor < 0.1 || loadFactor >= 1) {
				printUsage();
				return;
			}
		} catch (NumberFormatException e) {
			printUsage();
			return;
		}

		HashTable tableLinear;
		HashTable tableDouble;
		long linearSteps = 0;
		long doubleSteps = 0;
		if (mode == 1) {
			Random rand = new Random();
			tableLinear = new HashTable<Integer>(HashTable.ProbeType.LINEAR, 95500);
			tableDouble = new HashTable<Integer>(HashTable.ProbeType.DOUBLE_HASH, 95500);
			while (tableLinear.getLoadFactor() < loadFactor) {
				int newVal = Math.abs(rand.nextInt());
				HashObject<Integer> newObject = new HashObject<Integer>(newVal, newVal);
				int linearStepsThis = tableLinear.insert(newObject);
				if (debug == 2) System.out.println("<" + newVal + "> linear steps: " + linearStepsThis);
				int doubleStepsThis = tableDouble.insert(newObject);
				if (debug == 2) System.out.println("<" + newVal + "> double steps: " + doubleStepsThis);
				linearSteps += linearStepsThis;
				doubleSteps += doubleStepsThis;
			}
		} else if (mode == 2) {
			tableLinear = new HashTable<Long>(HashTable.ProbeType.LINEAR, 95500);
			tableDouble = new HashTable<Long>(HashTable.ProbeType.DOUBLE_HASH, 95500);
			while (tableLinear.getLoadFactor() < loadFactor) {
				long newVal = System.currentTimeMillis();
				HashObject<Long> newObject = new HashObject<Long>(newVal, newVal);
				int linearStepsThis = tableLinear.insert(newObject);
				if (debug == 2) System.out.println("<" + newVal + "> linear steps: " + linearStepsThis);
				int doubleStepsThis = tableDouble.insert(newObject);
				if (debug == 2) System.out.println("<" + newVal + "> double steps: " + doubleStepsThis);
				linearSteps += linearStepsThis;
				doubleSteps += doubleStepsThis;
			}
		} else {
			Scanner wordlist;
			try {
			wordlist = new Scanner(new File("word-list"));
			} catch (FileNotFoundException e){
				System.out.println("File not found: word-list");
				return;
			}
			tableLinear = new HashTable<String>(HashTable.ProbeType.LINEAR, 95500);
			tableDouble = new HashTable<String>(HashTable.ProbeType.DOUBLE_HASH, 95500);
			while (tableLinear.getLoadFactor() < loadFactor) {
				String newVal = wordlist.nextLine().trim();
				HashObject<String> newObject = new HashObject<String>(newVal.hashCode(), newVal);
				int linearStepsThis = tableLinear.insert(newObject);
				if (debug == 2) System.out.println("<" + newVal + "> linear steps: " + linearStepsThis);
				int doubleStepsThis = tableDouble.insert(newObject);
				if (debug == 2) System.out.println("<" + newVal + "> double steps: " + doubleStepsThis);
				linearSteps += linearStepsThis;
				doubleSteps += doubleStepsThis;
			}
			wordlist.close();
		}
		

		if (debug == 0) {
			System.out.println("Found a good table size: " + tableLinear.length());
			if (mode == 1) {
				System.out.println("Data Source: Random integer Generator");
			} else if (mode == 2) {
				System.out.println("Data Source: Current Time long integers");
			} else {
				System.out.println("Data Source: Strings from text file");
			}
			System.out.println("");
			System.out.println("Using Linear Hashing:");
			System.out.println("Inserted " + tableLinear.getStoredObjects() + " elements, with 0 duplicates");
			System.out.printf("Load Factor = %f, Avg. no. of probes %f\n", loadFactor, ((double)linearSteps / (double) tableLinear.getStoredObjects()));
			System.out.println("");
            System.out.println("Using Double Hashing:");
			System.out.println("Inserted " + tableDouble.getStoredObjects() + " elements, with 0 duplicates");
			System.out.printf("Load Factor = %f, Avg. no. of probes %f\n", loadFactor, ((double)doubleSteps / (double) tableDouble.getStoredObjects()));
			System.out.println("");

		} else if (debug == 1) {
			tableLinear.print();
			tableDouble.print();
		}

	}

	private static void printUsage(){
		System.out.println("java HashTest <input type> <load factor> [<debug level>]");
		System.out.println("");
		System.out.println("input type:  1 for integers, 2 for long, 3 for strings");
		System.out.println("");
		System.out.println("load factor: float from 0.1 to 0.99");
		System.out.println("");
		System.out.println("debug level: 0 to print a summary, 1 to print the hash\n" +
		                   "table, 2 to print the number of probes for each insert");
	}

}

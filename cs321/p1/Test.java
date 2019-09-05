package p1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Test {
	private static Scanner fileReader;
	public static void main(String args[]){
		if (args[0].equals("1")){
			try {
				if (Integer.parseInt(args[1]) > 0){
					fileReader = new Scanner(new File(args[2]));
					simulateSingleCache(Integer.parseInt(args[1]), fileReader);
				} else {
					printUsage();
				}
			} catch (NumberFormatException e){
				printUsage();
			} catch (FileNotFoundException e){
				System.out.println("File not found: " + args[2]);
			}
		} else if (args[0].equals("2")){
			try {
				if (Integer.parseInt(args[1]) > 0){
					if (Integer.parseInt(args[2]) > 0){
						fileReader = new Scanner(new File(args[3]));
						simulateDoubleCache(Integer.parseInt(args[1]), Integer.parseInt(args[2]), fileReader);
					} else {
						printUsage();
					}
				} else {
					printUsage();
				}
			} catch (NumberFormatException e){
				printUsage();
			} catch (FileNotFoundException e){
				System.out.println("File not found: " + args[3]);
			}
		} else {
			printUsage();
			System.exit(1);
		}
	}	
	private static void simulateSingleCache(int size, Scanner fileScanner){
		System.out.println("Creating Level One Cache of size: " + size);
		int numHits = 0;
		int numRefs = 0;
		Cache<String> cacheOne = new Cache<String>(size);
		
		while (fileScanner.hasNextLine() == true){
			String line = fileScanner.nextLine().trim();
			if (line.equals("")) {
				continue;
			}
			String[] words = line.split("\\s+");
			for (String s : words){
				numRefs++;
				try {
					cacheOne.getObject(s);
					numHits++;
					cacheOne.removeObject(s);
					cacheOne.addObject(s);
				} catch (NoSuchElementException e){
					cacheOne.addObject(s);
				}
			}
		}
		
		System.out.println("Total Cache References:\t" + numRefs);
		System.out.println("Total Cache Hits:\t" + numHits);
		System.out.println("Total Hit Ratio:\t" + ((long)(numHits) / (long)(numRefs)));
		
	}
	
	private static void simulateDoubleCache(int size1, int size2, Scanner fileScanner){
		System.out.println("Creating Level One Cache of size: " + size1);
		System.out.println("Creating Level Two Cache of size: " + size2);
		int numHitsOne = 0;
		int numHitsTwo = 0;
		int numRefsTotal = 0;
		Cache<String> cacheOne = new Cache<String>(size1);
		Cache<String> cacheTwo = new Cache<String>(size2);
		while (fileScanner.hasNextLine() == true){
			String line = fileScanner.nextLine().trim();
			if (line.equals("")) {
				continue;
			}
			String[] words = line.split("\\s+");
			for (String s : words){
				if (s.replaceAll("\\s","") == "") {
					continue;
				} else {
				String str = s.replaceAll("\\s","");
				numRefsTotal++;
				try {
					cacheOne.getObject(str);
					numHitsOne++;
					cacheOne.removeObject(str);
					cacheOne.addObject(str);
					cacheTwo.removeObject(str);
					cacheTwo.addObject(str);
				} catch (NoSuchElementException e){
					try {
						cacheTwo.getObject(str);
						numHitsTwo++;
						cacheTwo.removeObject(str);
						cacheTwo.addObject(str);
						cacheOne.addObject(str);
					} catch (NoSuchElementException f){
						cacheOne.addObject(str);
						cacheTwo.addObject(str);
					}
				}
			}
			}
		}

		System.out.println("Total Cache References: " + numRefsTotal);
		System.out.println("Total Cache Hits:       " + (numHitsOne + numHitsTwo));
		System.out.println("Total Cache Hit Ratio:  " +((double)(numHitsOne + numHitsTwo) /  (double)numRefsTotal));
		System.out.println("Level One Hits:         " + numHitsOne);
		System.out.println("Level One Hit Ratio:    " + ((double)(numHitsOne) / (double)(numRefsTotal)));
		System.out.println("Level Two Hits:         " + numHitsTwo);
		System.out.println("Level Two Hit Ratio:    " + ((double)(numHitsTwo) / (double)(numRefsTotal - numHitsOne)));
		
	}
	
	private static void printUsage(){
		System.out.println("Usage:");
		System.out.println("java Test <1|2> <Cache 1 Size> [Cache 2 Size] <Input File>");
		System.out.println("");
		System.out.println("1 or 2\tUse a single cache or double cache.(required)");
		System.out.println("Cache 1 Size\tHow many objects the level 1 cache holds. Integer > 0 (required)");
		System.out.println("Cache 2 Size\tHow many objects the level 2 cache holds. Integer > 0 (required if double cache is enabled)");
		System.out.println("Input File\tFilename of the input file. (required)");
		
	}
}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Parses a given .gbk file and creates subSequences of a given length. It then
 * sends these subSequences to a BTree in form of a binary sequence.
 * 
 * @author Mack Bryan
 *
 */
public class GeneBankCreateBTree {
	static int degree;
	static int useCache;
	static int sequenceLength;
	static int cacheSize;

	static int debugLevel;
	static String fileName;
	static GeneBankCreateBTree createTree;
	static BTree tree;
	static String origin = "origin";
	static String end = "//";
	static private DataStore traversalFile;

	public GeneBankCreateBTree() {
		if (useCache == 1) {
			tree = new BTree(useCache, degree, fileName, sequenceLength, cacheSize);
		} else {
			tree = new BTree(useCache, degree, fileName, sequenceLength, 0);
		}
	}

	/**
	 * Decides the current DNA character
	 * 
	 * @param appendStr
	 *            current string to append to
	 * @param firstChar
	 *            first binary number
	 * @param secondChar
	 *            second binary number
	 * @return The DNA character matching the given binary numbers
	 */
	public static String decideChar(String appendStr, int firstChar, int secondChar) {
		String retVal = appendStr;

		if (firstChar == 0 && secondChar == 0) {
			retVal += "A";
		} else if (firstChar == 1 && secondChar == 1) {
			retVal += "T";
		} else if (firstChar == 0 && secondChar == 1) {
			retVal += "C";
		} else if (firstChar == 1 && secondChar == 0) {
			retVal += "G";
		}

		return retVal;
	}

	/**
	 * Converts a given binary sequence of type String to its DNA character
	 * representation.
	 * 
	 * @param binString
	 *            The binary sequence in form of a String.
	 * @return The DNA character representation of the binary sequence
	 */
	public static String convertToCharSeq(String binString) {
		String retVal = "";
		int firstC = 2; // to represent binary numbers 1 & 0
		int secondC = 2;// 2 is default or error
		int cCount = 0;
		// loop through the binary string and grab the
		// the characters in "pairs". This is done with the
		// cCount variable, a properly formatted binString
		// will always be an even number of characters
		for (int i = 0; i < binString.length(); i++) {
			// if both characters found decide the character
			// and reset values
			if (cCount == 2) {
				retVal = decideChar(retVal, firstC, secondC);
				firstC = 2;
				secondC = 2;
				cCount = 0;
			}
			// grab the first and second characters and increment count
			if (cCount == 1) {
				String tmp1 = "" + binString.charAt(i);
				secondC = Integer.parseInt(tmp1);
				cCount++;
			} else if (cCount == 0) {
				String tmp0 = "" + binString.charAt(i);
				firstC = Integer.parseInt(tmp0);
				cCount++;
			} else {
				if (debugLevel == 0) {
					System.err.println("ERROR: convertToCharSeq!");
				}
			}

		}
		// now decide the last character in the substring
		retVal = decideChar(retVal, firstC, secondC);
		// and return the DNA subString
		return retVal.toLowerCase();
	}

	/**
	 * Add preceding 0's to a string binary sequence if missing them
	 * 
	 * @param binaryIn
	 *            sequence
	 * @return modified sequence
	 */
	private static String checkZeros(String binaryIn) {
		String retVal = binaryIn;
		if (retVal.length() != (sequenceLength * 2)) {

			String temp = retVal;
			retVal = "";
			int diff = (sequenceLength * 2) - temp.length();
			for (int i = 0; i < diff; i++) {
				retVal += "0";
			}
			retVal += temp;
			return retVal;
		}

		return retVal;
	}

	/**
	 * Prints usage to console
	 */
	private static void printUsage() {
		System.out.println("USAGE: java GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file>"
				+ " <sequence length> [<cache size>] [<debug level>]");
		System.out.println("degree = 0 program chooses optimal degree");
		System.out.println("sequence length must be between 1 and 31 inclusive.");
		System.out.println("cache size and debug level are optional.");
		System.out.println("Debug level between 0 and 1.");
		System.exit(1);
	}

	/**
	 * Parses the arguments instructed .gbk file
	 * 
	 * @param k
	 *            is the sequence length
	 * @param fName
	 *            is the name of the file to be parsed
	 * @throws FileNotFoundException
	 */
	private static void parse(int k, String fName) throws FileNotFoundException {
		
		String word = "";
		int kCount = 0;
		String subString = "";
		String prevString = "";
		int in = 0;
		long binaryInsert = 0;
		try{
			File fileIn = new File(fName);
			Scanner scan = new Scanner(fileIn);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			StringTokenizer token = new StringTokenizer(line);

			while (token.hasMoreTokens()) {
				// while not in a sequence
				if (in == 0) {
					word = token.nextToken().toLowerCase();

					// System.out.println(word);//DEBUGSTATEMENT
					if (word.equals(origin)) {
						// System.out.println("IN!");
						kCount = 0;
						in = 1;
					}
				} else if (in == 1) {// while in a sequence
					word = token.nextToken().toLowerCase();

					// check for end sequence
					if (word.equals(end)) {
						if (debugLevel == 0) {
							System.err.println("Final Substring :" + subString);// DEBUGSTATEMENT
						}
						binaryInsert = convertToBinary(subString);
						if (subString.length() == sequenceLength) {
							if (debugLevel == 0) {
								System.err.println("Inserting: " + binaryInsert);
							}
							tree.insert(binaryInsert);
						}
						subString = "";
						// System.out.println("OUT!");// DEBUGSTATEMENT
						in = 0;
						// Check the first character of the current token to
						// ensure it is not the new line's character count
						// then proceed to create the substring
					} else if (word.charAt(0) == 'g' || word.charAt(0) == 'c' || word.charAt(0) == 't'
							|| word.charAt(0) == 'a') {
						for (int i = 0; i < word.length(); i++) {
							if (kCount == k) {
								if (debugLevel == 0) {
									System.err.println("Substring: " + subString);// DEBUGSTATEMENT
								}
								prevString = subString;

								binaryInsert = convertToBinary(subString);
								if (debugLevel == 0) {
									System.err.println("Inserting : " + binaryInsert);
									System.err.println();
								}
								if (!subString.contains("n")) {
									tree.insert(binaryInsert);
								}
								subString = "";
								// tree.insert(binaryInsert);
								subString = prevString.substring(1);
								kCount = k - 1;
							}
							if (word.charAt(i) == 'n') {
								subString += word.charAt(i);
								kCount++;

								// SHOULD N CHARACTERS BE INCLUDED IN K INC!!!
							} else {
								subString += word.charAt(i);
								kCount++;
							}
						} // for
					} else {
						//
					}
				} // else if(in ==1)
			} // has more Tokens
		} // has nextLine
		}catch(FileNotFoundException e){
			System.err.println("File \"" + fileName + "\" could not be opened.");
			System.err.println(e.getMessage());
			System.exit(1);
		}

	}// parse

	public static long binaryToLong(String binary) {
		char[] numbers = binary.toCharArray();
		long result = 0;
		// int currPower = 1;
		for (int i = numbers.length - 1; i >= 0; i--) {
			if (numbers[i] == '1') {
				result += (long) Math.pow(2, (numbers.length - i - 1));
			}
		}
		return result;
	}

	/**
	 * Converts a string containing a subString of a DNA sequence into a long
	 * binary.
	 * 
	 * @param sequence
	 *            of DNA characters
	 * @return sequence in binary
	 */
	static long convertToBinary(String sequence) {
		long retVal = 0;
		String temp = "";
		char c;
		for (int i = 0; i < sequence.length(); i++) {
			c = sequence.charAt(i);
			if (c == 'a' || c == 'A') {
				temp += "00";
			} else if (c == 't' || c == 'T') {
				temp += "11";
			} else if (c == 'c' || c == 'C') {
				temp += "01";
			} else if (c == 'g' || c == 'G') {
				temp += "10";
			}
		}

		// convert our binary string and convert it to a long base 10
		long convTemp = binaryToLong(temp);

		retVal = convTemp;

		return retVal;
	}

	/**
	 * In-order traversal
	 * 
	 * @param root
	 */
	public static void treeTraversal(BTreeNode root) {
		traversalFile = tree.getDataFile();
		if (root != null) {

			Path file = Paths.get("./dump");
			for (int i = 0; i < root.getNumKeys(); i++) {
				if (i < root.getNumChildren()) {
					treeTraversal(traversalFile.getNode(root.getChildAt(i)));
				}
				List<String> str = Arrays.asList(convertToCharSeq(checkZeros(Long.toBinaryString(root.getKeyAt(i))))
						+ ": " + root.getValueAt(i));

				try {
					Files.write(file, str, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (root.getNumKeys() < root.getNumChildren()) {
				treeTraversal(traversalFile.getNode(root.getChildAt(root.getNumKeys())));
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Beginning...");
		// check that args length is at least 4
		if (args.length < 4) {
			printUsage();
		}
		// Parse first 4 required arguments
		if (Integer.parseInt(args[0]) > 2) {
			printUsage();
		}
		useCache = Integer.parseInt(args[0]);
		if (Integer.parseInt(args[1]) < 0) {
			printUsage();
		} else if (Integer.parseInt(args[1]) == 0) {
			degree = 128;
		} else {
			degree = Integer.parseInt(args[1]);
		}
		
		
		fileName = args[2];
		
		if (Integer.parseInt(args[3]) < 1 || Integer.parseInt(args[3]) > 31) {
			printUsage();
		}
		sequenceLength = Integer.parseInt(args[3]);
		createTree = new GeneBankCreateBTree();
		// Check for optional console parameters
		if (args.length == 5) {
			if(useCache == 0){
				printUsage();
			}
			cacheSize = Integer.parseInt(args[4]);
			debugLevel = 0;
		} else if (args.length == 6) {
			cacheSize = Integer.parseInt(args[4]);
			debugLevel = Integer.parseInt(args[5]);
		}
		if (useCache == 1) {
			tree.initCache(cacheSize);
		}

		System.out.println("Parsing Data...");
		// parse the given file
		parse(sequenceLength, fileName);

		if (debugLevel == 1) {
			System.out.println("Dumping Inorder Traversal...");
			File f = new File("./dump");
			try {
				f.delete();
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			treeTraversal(tree.getRoot());
		}
		System.out.println("Done.");
	}

}

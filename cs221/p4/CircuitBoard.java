import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 * 
 * @author mvail
 */
public class CircuitBoard {
	private char[][] board;
	private Point startingPoint;
	private Point endingPoint;

	// constants you may find useful
	private final int ROWS; // initialized in constructor
	private final int COLS; // initialized in constructor
	private final char OPEN = 'O';
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/**
	 * Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows: 'O' an open position 'X' an occupied,
	 * unavailable position '1' first of two components needing to be connected
	 * '2' second of two components needing to be connected 'T' is not expected
	 * in input files - represents part of the trace connecting components 1 and
	 * 2 in the solution
	 * 
	 * @param filename
	 *            file containing a grid of characters
	 * @throws FileNotFoundException
	 * @throws InvalidFileFormatException
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		Scanner fileScan = new Scanner(new File(filename));
		Scanner lineReader;
		// throw FileNotFoundException if Scanner cannot read the file
		// throw InvalidFileFormatException if any formatting or parsing issues
		// are encountered
		boolean startFound = false;
		boolean endFound = false;
		if (!fileScan.hasNextLine())
			throw new InvalidFileFormatException("Empty File");
		lineReader = new Scanner(fileScan.nextLine());
		try {
			ROWS = lineReader.nextInt(); // replace with initialization
											// statements using values from file
			COLS = lineReader.nextInt();
			board = new char[ROWS][COLS];
			
		} catch (NumberFormatException e) {
			lineReader.close();
			throw new InvalidFileFormatException("Failed to parse grid coordinates");
		}
		// Check if first line is valid.
		if (lineReader.hasNextInt())
			throw new InvalidFileFormatException("Too many values on grid parameter line");
		
		for (int i = 0; i < ROWS; i++){
			if (!fileScan.hasNextLine()) throw new InvalidFileFormatException("Not enough rows in the file");
			String currentLine = fileScan.nextLine();
			if (currentLine.length() != (COLS*2) - 1) throw new InvalidFileFormatException("Wrong number of values in row " + (i + 1));
			for (int j = 0; j < COLS; j++){
				if (ALLOWED_CHARS.contains((CharSequence)currentLine.substring(j*2,j*2))){
					char currentChar = currentLine.charAt(j*2);
					switch (currentChar) {
					case '1':
						if (startFound) throw new InvalidFileFormatException("Two end markers found, which is not allowed");
						board[i][j] = currentChar;
						startFound = true;
						startingPoint = new Point(i,j);
						break;
					case '2':
						if (endFound) throw new InvalidFileFormatException("Two end markers found, which is not allowed");
						board[i][j] = currentChar;
						endFound = true;
						endingPoint = new Point(i,j);
						break;
					case 'T':
						throw new InvalidFileFormatException("Unexpected T found in file. T is used in output files only.");
					default:
						board[i][j] = currentChar;
					}
				} else {
					throw new InvalidFileFormatException("Invalid Character Found at row " + i + " column " + j + " : " + currentLine.substring(j,j));
				}
			}
		}
		
		fileScan.close();
		lineReader.close();
	}

	/**
	 * Copy constructor - duplicates original board
	 * 
	 * @param original
	 *            board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/**
	 * utility method for copy constructor
	 * 
	 * @return copy of board array
	 */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}

	/**
	 * Return the char at board position x,y
	 * 
	 * @param row
	 *            row coordinate
	 * @param col
	 *            col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}

	/**
	 * Return whether given board position is open
	 * 
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}

	/**
	 * Set given position to be a 'T'
	 * 
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException
	 *             if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}

	/** @return starting Point */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}

	/** @return ending Point */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}

	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}

	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}

}// class CircuitBoard

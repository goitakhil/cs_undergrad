import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Analyzes a text file to generate some statistics.
 * 
 * @author Tyler Bevan
 *
 */
public class TextStatistics implements TextStatisticsInterface {
	private int charCount = 0, wordCount = 0, lineCount = 0;
	private int[] letterCount = new int[26], wordLengthCount = new int[24];

	public TextStatistics(File sourceFile) {
		try {

			Scanner fileReader = new Scanner(sourceFile);
			while (fileReader.hasNextLine()) {
				String currentLine = fileReader.nextLine();
				lineCount++;
				charCount += currentLine.length() + 1;
				StringTokenizer tokenizer = new StringTokenizer(currentLine,
						" ,.;:'\"&!?-_\n\t12345678910[]{}()@#$%^*/+-");

				while (tokenizer.hasMoreTokens()) {
					String currentWord = tokenizer.nextToken().toLowerCase();
					wordCount++;
					wordLengthCount[currentWord.length()]++;

					for (int index = 0; index < currentWord.length(); index++) {
						char currentChar = currentWord.charAt(index);
						letterCount[(currentChar - 97)]++;

					}
				}

			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + sourceFile.getAbsolutePath());
			e.printStackTrace();
		}
	}

	@Override
	public int getCharCount() {
		return charCount;
	}

	@Override
	public int getWordCount() {
		return wordCount;
	}

	@Override
	public int getLineCount() {
		return lineCount;
	}

	@Override
	public int[] getLetterCount() {
		return letterCount;
	}

	@Override
	public int[] getWordLengthCount() {
		return wordLengthCount;
	}

	@Override
	public double getAverageWordLength() {
		int sum = 0;
		for (int i = 0; i < wordLengthCount.length; i++) {
			sum += wordLengthCount[i] * i;
		}
		return (((double) sum) / (double) wordCount);
	}

	@Override
	public String toString() {
		// TODO toString method.
		return null;
	}

}

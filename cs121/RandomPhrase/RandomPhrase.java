import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class RandomPhrase {
	public static void main(String[] args){
		String[] words = new String[52875];
		File list = new File("52875_words.txt");
		try {
			Scanner reader = new Scanner(list);
			for (int i = 0;reader.hasNextLine();){
				words[i] = reader.nextLine();
				i++;
			}
			reader.close();
			Random rand = new Random();
			String phrase = "";
			for (int i = 0; i < 3;++i){
				phrase += words[rand.nextInt(52875) + 1] + " ";
			}
			System.out.println(phrase);
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found: 52875_words.txt");
			e.printStackTrace();
		}
	}
}

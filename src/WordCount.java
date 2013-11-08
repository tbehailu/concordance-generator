import java.io.*;
import java.util.*;

/*
 * Generates a concordance of an arbitrary text document written in English:
 * the text can be read from stdin, and the program should output the
 * concordance to stdout or a file. For each word, it should print the count
 * and the sorted list of citations, in this case the zero-indexed sentence
 * number in which that word occurs. You may assume that the input contains
 * only spaces, newlines, standard English letters, and standard English
 * punctuation marks.
 */
public class WordCount {

	private static ArrayList<String> AllWords = new ArrayList<String>(); // arraylist for words in document
	
	public static void main (String [] args) {
        File file = new File(args[0]);
		BufferedReader inputReader = null;
		
		try {
			inputReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
		}

		String curLine = "";
		try {
			while ((curLine = inputReader.readLine()) != null) {
				String contents = curLine;
				addWords(contents);
				Collections.sort(AllWords); // alphabetize list of words in document
				Iterator<String> wordItr = AllWords.iterator();
				while (wordItr.hasNext()) {
					contents = curLine;
					String curWord = wordItr.next();
					solve(contents, curWord);
				}
			}
		} catch (IOException e) {
		}

		try {
			inputReader.close();
		} catch (IOException e) {
		}
        
		return;
	}
	
	
	/*
	 * Compute the sentence count of the given word and list of sentences it
	 * appears in and print.
	 */
	private static void solve(String given, String word) {
		Scanner scan = new Scanner(given);
		int wordCount = 0;
		int sentenceCount = 0;
		String sentenceLine = "";
		char lastChar;
		
		String curWord;
		while (scan.hasNext()) {
			curWord = scan.next().toLowerCase();
			lastChar = curWord.charAt(curWord.length()-1);
			curWord = trimWord(curWord);
			if (curWord.equals(word)) {
				wordCount++;
				sentenceLine += "" + sentenceCount + ",";
			}
			if (isEnding(lastChar)) {
				sentenceCount++;
			}
		}
		scan.close();
		if (!word.isEmpty()) {
			System.out.println(word.toLowerCase() + "\t" + "{" + wordCount + ":" + sentenceLine.substring(0, sentenceLine.length()-1) + "}");
		}
	}
	
	
	/*
	 * Adds all the words in the argument to AllWords.
	 */
	private static void addWords(String line) {
		Scanner scan = new Scanner(line);
		String curWord;
		while (scan.hasNext()) {
			curWord = scan.next().toLowerCase();
			curWord = trimWord(curWord);
			if (!AllWords.contains(curWord)){
				AllWords.add(curWord);
			}
		}
		scan.close();
	}
	
	/*
	 * Returns a boolean indicating whether the argument is a sentence
	 * ending.
	 */
	private static boolean isEnding(char given) {
		return given == '.' || given == '!' || given == '?';
	}
	
	/*
	 * Returns a boolean indicating whether the argument is a standard
	 * English punctuation mark.
	 */
	private static boolean isPunctuation(char given) {
		return given == '.' || given == '!' || given == '?'
				|| given == ':' || given == ';' || given == '"'
				|| given == ',' || given == '(' || given == ')'
				|| given == '[' || given == ']' || given == '-'
				|| given == '/';
	}
	
	/*
	 * Remove punctuation marks surrounding argument and return.
	 */
	private static String trimWord(String given) {
		char firstChar, nextLastChar, lastChar;
		firstChar =  given.charAt(0);
		lastChar = given.charAt(given.length()-1);
		
		if (isPunctuation(firstChar)){
			given = given.substring(1, given.length()-1);
		}
		if (isPunctuation(lastChar) &!isPunctuation(firstChar)){
			nextLastChar = lastChar;
			while (isPunctuation(nextLastChar)) {
				given = given.substring(0, given.length()-1);
				nextLastChar = given.charAt(given.length()-1);
			}
		}
		
		return given;
	}
	
}

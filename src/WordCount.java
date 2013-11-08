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

	private static Set<String> WordInfo = new HashSet<String>(); // store word, word count, and sentence numbers
	private static ArrayList<String> AllWords = new ArrayList<String>();
	
	public static void main (String [] args) {

        File file = new File(args[0]);
		BufferedReader inputReader = null;
		
		try {
			inputReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			
		}

		// Add all words to HashMap
		String curLine = "";
		try {
			while ((curLine = inputReader.readLine()) != null) {
				String contents = curLine;
				// System.out.println(contents);
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

		//solve(AllLines);
        
		return;
	}
	
	
	
	private static void solve(String given, String word) {
		//System.out.println(given);
		Scanner scan = new Scanner(given);
		int wordCount = 0;
		int sentenceCount = 0;
		String sentenceLine = "";
		ArrayList<Integer> sentences = new ArrayList<Integer>();
		char firstChar, nextLastChar, lastChar;
		String curWord;

		while (scan.hasNext()) {
			curWord = scan.next().toLowerCase();
			firstChar =  curWord.charAt(0);
			lastChar = curWord.charAt(curWord.length()-1);
			if (isPunctuation(firstChar)){
				curWord = curWord.substring(1, curWord.length()-1);
			}
			
			if (isPunctuation(lastChar) &!isPunctuation(firstChar)){
				nextLastChar = lastChar;
				while (isPunctuation(nextLastChar)) {
					curWord = curWord.substring(0, curWord.length()-1);
					nextLastChar = curWord.charAt(curWord.length()-1);
				}
			}
			//System.out.println(curWord);
			if (curWord.equals(word)) {
				wordCount++;
				sentenceLine += "" + sentenceCount + ",";
			}
			if (isEnding(lastChar)) {
				//System.out.println(curWord + " " + sentenceCount + " " + lastChar);
				sentences.add(sentenceCount);
				sentenceCount++;
			}
		}
		//System.out.println(word + " " + sentenceCount);
		scan.close();
		sentences.add(0, wordCount); // add word count as first element of arraylist
		//WordInfo.put(word.toLowerCase(),sentences); // associate word with arraylist
		if (!word.isEmpty()) {
			//String s = "E.G.";
			
			System.out.println(word.toLowerCase() + "\t" + "{" + wordCount + ":" + sentenceLine.substring(0, sentenceLine.length()-1) + "}");
		}
	}
	
	
	private static void addWords(String line) {
		//System.out.println(line);
		Scanner scan = new Scanner(line);
		String word;
		
		char firstChar, nextLastChar, lastChar;
		String curWord;
		while (scan.hasNext()) {
			curWord = scan.next().toLowerCase();
			firstChar =  curWord.charAt(0);
			lastChar = curWord.charAt(curWord.length()-1);
			if (isPunctuation(firstChar)){
				curWord = curWord.substring(1, curWord.length()-1);
			}
			if (isPunctuation(lastChar) &!isPunctuation(firstChar)){
				nextLastChar = lastChar;
				while (isPunctuation(nextLastChar)) {
					curWord = curWord.substring(0, curWord.length()-1);
					nextLastChar = curWord.charAt(curWord.length()-1);
				}
			}
			//System.out.println(curWord);
			if (WordInfo.add(curWord)) {
				AllWords.add(curWord);
			}
		}
		scan.close();
	}
	
	private static boolean isEnding(char given) {
		return given == '.' || given == '!' || given == '?';
	}
	
	private static boolean isPunctuation(char given) {
		return given == '.' || given == '!' || given == '?'
				|| given == ':' || given == ';' || given == '"'
				|| given == ',' || given == '(' || given == ')'
				|| given == '[' || given == ']' || given == '-'
				|| given == '/';
	}
	
}

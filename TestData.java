import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TestData {

	private static String positiveFile = "/Users/svelrith/Documents/workspace/TwitterNLP/src/data/positiveTrainingTweets.txt";
	private static String negativeFile = "/Users/svelrith/Documents/workspace/TwitterNLP/src/data/negativeTrainingTweets.txt";
	private static String stopWordsFile= "/Users/svelrith/Documents/workspace/TwitterNLP/src/data/stopWords.txt";
	private static String negationWordsFile= "/Users/svelrith/Documents/workspace/TwitterNLP/src/data/negationWords.txt";

	/*
	 * Computes the sentiment for a text
	 * Receives an ArrayList containing the words of the text and a String (positive or negative) indicating which sentiment to calculate
	 * Returns a double indicating the sentiment score
	 * Algorithm words by looking at word and seeing what percent of the time that word appears in either the negative or positive training cases
	 * The percentage for all the words are then added up and returned
	 */
	public double textSentiment(String textString, String sentiment) throws IOException{
		double percentOfAllText = 0;
		double percentText = 0;
		ArrayList<String> text = preProcessText(textString); //ArrayList contains the words of the text
		int positiveFileLines = countLines(positiveFile);
		int negativeFileLines = countLines(negativeFile);
		
		for(int i=0; i<text.size(); i++){
			String tempWord = text.get(i);
			String prevWord = "";
			if(i > 0){ //prevWord used to check for negation words
				prevWord = text.get(i-1);
			}//end if
			
			//percentOfAllText = percentOfAllText + (totalWordOcurance(tempWord,negativeFile) + totalWordOcurance(tempWord,positiveFile)) / ((double)positiveFileLines + (double)negativeFileLines);
			//percentPositiveText = (totalWordOcurance(tempWord,positiveFile) / (double)countLines(positiveFile));
			//percentNegativeText = (totalWordOcurance(tempWord,negativeFile) / (double)countLines(negativeFile));
			//System.out.println(tempWord+" total: "+percentOfAllText+" positive: "+percentPositiveText+" negative "+percentNegativeText);
			
			if(sentiment.equals("negative")){
				percentText = percentText + (totalWordOcurance(tempWord,negativeFile) / (double)negativeFileLines);
			}else{
				if(isNegationWord(prevWord)){ //Check if previous word is negation (ie "I am not cool")
					percentText = percentText + (totalWordOcurance(tempWord,positiveFile) / (double)positiveFileLines);
					percentText = percentText * -1;
				}else{
					percentText = percentText + (totalWordOcurance(tempWord,positiveFile) / (double)positiveFileLines);
				}//end if
			}//end if
		}//end for
		return percentText;
	}//end method	
	
	/*
	 * Check to see if a word is a negation word
	 * Receives the word to check
	 * Returns a boolean value indicating if the word is a negation word or not
	 */
	private boolean isNegationWord(String word) throws IOException{
		String negationWords[] = readFromFile(negationWordsFile);
		if(Arrays.asList(negationWords).contains(word)){
			return true;
		}else{
			return false;
		}//end if
	}//end if
	
	/*
	 * Pre-processes a text String
	 * Receives the text String to pre-processes
	 * Returns an array list of the words in the text with stop words removed
	 */
	private ArrayList<String> preProcessText(String textString) throws IOException{
		ArrayList <String>tempArrayList = new ArrayList<String>(); //Add each word to an ArrayList to be used for proccessing
		StringTokenizer textToken = new StringTokenizer(textString);
		while(textToken.hasMoreTokens()){
			String tempWord = textToken.nextToken();
			if(!tempWord.equals(null)){
				tempWord = stripWord(tempWord);
				tempArrayList.add(tempWord); //if not white space than add to ArrayList
			}//end if
		}//end while
		ArrayList<String> text = removeStopWords(tempArrayList); //Remove stops words
		return text;
	}//end method
	
	/*
	 * Removes stop words in text
	 * Receives an ArrayList of words 
	 * Returns an ArrayList of words with the stop words removed
	 */
	private ArrayList<String> removeStopWords(ArrayList<String> text) throws IOException{
		String stopWords[] = readFromFile(stopWordsFile);
		ArrayList <String>arrayList = new ArrayList<String>();
		for(int i=0; i<text.size(); i++){
			if(!Arrays.asList(stopWords).contains(stripWord(text.get(i)))){
				arrayList.add(text.get(i));
			}//end if
		}//end for
		return arrayList;
	}//end method
	
	/*
	 * Counts the number of times a word appears in given text file
	 * Receives the word to look for and the path of the file
	 * Returns an int indicating the number of times the word appeared in the file
	 */
	private int totalWordOcurance(String word, String path) throws IOException{
		Scanner input = new Scanner(new File(path));
		int wordCounter = 0;
		while(input.hasNextLine()){
			//Extract a single word
			String tempWord = input.next(); 
			tempWord = stripWord(tempWord);
			if(tempWord.equals(word)){
				wordCounter++;
			}//end if
		}//end while
		return wordCounter;
	}//end method
	
	/*
	 * Reads in lines from a text file
	 * Receives a string indicating the name of the text file to read from
	 * Returns a string array containing each line in text file
	 */
	private String[] readFromFile(String path) throws IOException{
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		int numberOfLines = countLines(path);
		String[] textData = new String[numberOfLines];
		for(int i=0; i<numberOfLines; i++){
			textData[i] = textReader.readLine();
		}//end for
		textReader.close();
		return textData;
	}//end method
	
	/*
	 * Counts the number of lines in a text file
	 * Receives the path of the text file to count
	 * Returns an int indicating the number of lines in the text file
	 */
	private int countLines(String path) throws IOException{
		FileReader fileToRead = new FileReader(path);
		BufferedReader bf = new BufferedReader(fileToRead);
		int numberOfLines = 0;
		String aLine;
		while((aLine = bf.readLine()) != null){
			numberOfLines++;
		}//end while
		bf.close();
		return numberOfLines;	
	}//end method
	
	/*
	 * Takes a word and removes punctuation and converts to lower case
	 * Takes the word to strip
	 * Returns the word with punctuation removed and in lower case
	 */
	private String stripWord(String word){
		word = word.toLowerCase();
		word = word.replaceAll("([a-z]+)[?:!.,;]*", "$1"); //Remove punctuation
		return word;
	}//end method
}//end class

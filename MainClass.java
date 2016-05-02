import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import twitter4j.TwitterException;

public class MainClass {
	public static void main(String[] args) throws TwitterException, IOException{  
		MainClass algo = new MainClass();
		algo.analyze();
		//algo.populateTextFile();
	}//end main
	
	public void analyze() throws IOException, TwitterException{
		//Create frame for GUI
		GUIFrame gui = new GUIFrame(); // create EventsFrame
		gui.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		gui.setSize( 750, 500 ); // set frame size
		gui.setVisible( true ); // display frame
	}//end method
	
	public void populateTextFile() throws TwitterException, IOException{
		String happyFace = ":)";
		String sadFace = ":(";
		String happyFace2 = ":-)";
		String sadFace2 = ":-(";
		TwitterData twitter = new TwitterData();
		String searchTerm = ":(&lang:en";
		ArrayList<String> tweets = twitter.search(searchTerm);
		for(int i=0; i<tweets.size(); i++){
			StringTokenizer text = new StringTokenizer(tweets.get(i));
			String newString = "";
			while(text.hasMoreTokens()){
				String tempString = text.nextToken();  
				if(!tempString.contains("#") && !tempString.contains("@") && !tempString.contains("http://t.co") && !tempString.contains("https://t.co") && !tempString.contains(happyFace) && !tempString.contains(sadFace) && !tempString.contains(":p") && !tempString.contains(":P") && !tempString.contains(":D") && !tempString.contains(sadFace2) && !tempString.contains(happyFace2) && !tempString.contains("RT") && !tempString.contains("♥") && !tempString.contains("<3") && !tempString.contains("rt")){
					newString = newString+" "+tempString;
				}//end if
			}//end while
			writeToFile(newString);
			System.out.println(newString);
			//System.out.println(" ");
		}//end for
	}//end method
	
	public void writeToFile(String text) throws IOException{
		//Write to txt file
		String dataFile = "/Documents/workspace/TwitterNLP/src/data/test.txt";

		//Writing to file
		try {
		    BufferedWriter out = new BufferedWriter(new FileWriter(dataFile,true));
		    out.newLine();
		    out.write(text);
		    out.close();
		} catch (IOException e) {}
	}//end method
}//end class

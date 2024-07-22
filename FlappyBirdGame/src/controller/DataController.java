package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * The DataController class contains the logic to load and save the user's high score
 * 
 * @author Andrew Polyak
 * @version June 21, 2024
 */
public class DataController {

	private static final File USER_DB = new File("res/user-data.txt"); // Represents the user database File
	
	
	/**
	 * The loadHighScore method loads the user's high score from the USER_DB file
	 * 
	 * @return userData (high score)
	 */
	public int loadHighScore() {
		int userData = 0; // Default high score is 0
		
		try {
			Scanner fileReader = new Scanner(USER_DB); // Create fileReader to parse the database
			
			while (fileReader.hasNext()) { // For each data line of the database
				userData = fileReader.nextInt(); // Acquire the high score
			}
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return userData;
	}

	
	/**
	 * The saveHighScore method saves the user's high score to the USER_DB file
	 * 
	 * @param allTimeHighScore
	 */
	public void saveHighScore(String allTimeHighScore) {
		try {
			FileWriter fileWriter = new FileWriter(USER_DB); // Create fileWriter to write to the database (overwriting the entire thing)
			
			fileWriter.write(allTimeHighScore); // Save the high score
			fileWriter.close(); // Close the writer once the database has been fully written to
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
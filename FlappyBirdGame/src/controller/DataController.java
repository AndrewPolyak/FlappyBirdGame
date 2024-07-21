package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


/**
 * TODO
 */
public class DataController {

	private static final File USER_DB = new File("res/user-data.txt"); // Represents the user database File
	
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public int loadHighScore() {
		int userData = 0;
		
		try {
			Scanner fileReader = new Scanner(USER_DB); // Create fileReader to parse the database
			
			while (fileReader.hasNext()) { // For each data line of the database
				userData = fileReader.nextInt();
			}
			fileReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return userData;
	}

	
	/**
	 * TODO
	 * @param allTimeHighScore
	 */
	public void saveData(String allTimeHighScore) {
		try {
			FileWriter fileWriter = new FileWriter(USER_DB); // Create fileWriter to write to the database (overwriting the entire thing)
			
			fileWriter.write(allTimeHighScore);
			fileWriter.close(); // Close the writer once the database has been fully written to
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
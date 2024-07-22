package controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * TODO
 * 
 * @author Andrew Polyak
 * @version June 21, 2024
 */
public class FlappyBirdPostGameMenuController {

	AnchorPane postGameMenu; // Represents the menu displayed after the game ends
	AnchorPane mainMenu; // Represents the main menu of the application
	
	
	/**
	 * The FlappyBirdPostGameMenuController constructor initializes the post-game and main menus
	 * 
	 * @param postGameMenu
	 * @param mainMenu
	 */
	public FlappyBirdPostGameMenuController(AnchorPane postGameMenu, AnchorPane mainMenu) {
		super();
		this.postGameMenu = postGameMenu;
		this.mainMenu = mainMenu;
	}


	/**
	 * The detectInput method listens for user input to navigate out of the post-game screen
	 */
	public void detectInput() {
		returnToMainMenuHandler();
	}
	
	
	/**
	 * The returnToMainMenuHandler method listens for a SPACE input to navigate back to the main menu
	 */
	private void returnToMainMenuHandler() {
		postGameMenu.requestFocus();
		
		// If the user presses SPACE, exit the post-game menu and show the main menu
		postGameMenu.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.SPACE) {
				postGameMenu.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
	}
	
}

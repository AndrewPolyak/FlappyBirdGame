package controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * TODO
 * 
 * @author Andrew Polyak
 * @version June 20, 2024
 */
public class FlappyBirdPostGameMenuController {

	AnchorPane postGameMenu; // TODO
	AnchorPane mainMenu; // TODO
	Text highScore; // TODO
	
	int lastScore; // TODO
	
	
	/**
	 * TODO
	 * 
	 * @param postGameMenu
	 * @param mainMenu
	 * @param mapOneToggleBtn
	 * @param highScore
	 * @param lastScore
	 */
	public FlappyBirdPostGameMenuController(AnchorPane postGameMenu, AnchorPane mainMenu) {
		super();
		this.postGameMenu = postGameMenu;
		this.mainMenu = mainMenu;
		this.highScore = highScore;
		this.lastScore = lastScore;
	}


	/**
	 * TODO
	 */
	public void detectInput() {
		returnToMainMenuHandler();
	}
	
	
	/**
	 * TODO
	 */
	private void returnToMainMenuHandler() {
		postGameMenu.requestFocus();
		
		postGameMenu.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.SPACE) {
				postGameMenu.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
	}
	
}

package controller;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * The FlappyBirdMenuController class contains the logic related to controlling the application's menu
 * 
 * @author Andrew Polyak
 * @version July 5, 2024
 */
public class FlappyBirdMenuController {
	
	private Button mapOneToggleBtn; // Represents the button to toggle map one
	private Button mapTwoToggleBtn; // Represents the button to toggle map two
	private Button mapThreeToggleBtn; // Represents the button to toggle map three
	
	private Button birdOneToggleBtn; // Represents the button to toggle bird one
	private Button birdTwoToggleBtn; // Represents the button to toggle bird two
	private Button birdThreeToggleBtn; // Represents the button to toggle bird three
	
	private ImageView mapOne; // Represents map one
	private ImageView mapTwo; // Represents map two
	private ImageView mapThree; // Represents map three
	
	private ImageView birdOne; // Represents bird one
	private ImageView birdTwo; // Represents bird two
	private ImageView birdThree; // Represents bird three
	
	private boolean birdOneVisible; // Represents whether bird one is selected and visible
	private boolean birdTwoVisible; // Represents whether bird two is selected and visible
	private boolean birdThreeVisible; // Represents whether bird three is selected and visible
	
	private Runnable onGameStart;
	
	
	/**
	 * The FlappyBirdMenuController initializes the properties relevant to menu controlling
	 * 
	 * @param mapOneToggleBtn
	 * @param mapTwoToggleBtn
	 * @param mapThreeToggleBtn
	 * @param birdOneToggleBtn
	 * @param birdTwoToggleBtn
	 * @param birdThreeToggleBtn
	 * @param mapOne
	 * @param mapTwo
	 * @param mapThree
	 * @param birdOne
	 * @param birdTwo
	 * @param birdThree
	 */
	public FlappyBirdMenuController(Button mapOneToggleBtn, Button mapTwoToggleBtn, Button mapThreeToggleBtn,
			Button birdOneToggleBtn, Button birdTwoToggleBtn, Button birdThreeToggleBtn, ImageView mapOne,
			ImageView mapTwo, ImageView mapThree, ImageView birdOne, ImageView birdTwo, ImageView birdThree) {
		this.mapOneToggleBtn = mapOneToggleBtn;
		this.mapTwoToggleBtn = mapTwoToggleBtn;
		this.mapThreeToggleBtn = mapThreeToggleBtn;
		this.birdOneToggleBtn = birdOneToggleBtn;
		this.birdTwoToggleBtn = birdTwoToggleBtn;
		this.birdThreeToggleBtn = birdThreeToggleBtn;
		this.mapOne = mapOne;
		this.mapTwo = mapTwo;
		this.mapThree = mapThree;
		this.birdOne = birdOne;
		this.birdTwo = birdTwo;
		this.birdThree = birdThree;
	}
	
	
	/**
	 * The detectInput method contains event listeners for all menu interactions
	 */
	public void detectInput() {
		// Detect map toggle button presses
		mapOneToggleBtnHandler();
		mapTwoToggleBtnHandler();
		mapThreeToggleBtnHandler();
		
		// Detect bird skin toggle button presses
		birdOneToggleBtnHandler();
		birdTwoToggleBtnHandler();
		birdThreeToggleBtnHandler();
		
		// Detect game start button presses
		startGameHandler();
	}
	
	
	/**
	 * The mapOneToggleBtnHandler method loads map one
	 */
	private void mapOneToggleBtnHandler() {
		mapOneToggleBtn.setOnMouseClicked(e -> {
			mapOne.setVisible(true);
			mapTwo.setVisible(false);
			mapThree.setVisible(false);
		});
	}
	
	
	/**
	 * The mapTwoToggleBtnHandler method loads map two
	 */
	private void mapTwoToggleBtnHandler() {
		mapTwoToggleBtn.setOnMouseClicked(e -> {
			mapOne.setVisible(false);
			mapTwo.setVisible(true);
			mapThree.setVisible(false);
		});
	}
	
	
	/**
	 * The mapThreeToggleBtnHandler method loads map three
	 */
	private void mapThreeToggleBtnHandler() {
		mapThreeToggleBtn.setOnMouseClicked(e -> {
			mapOne.setVisible(false);
			mapTwo.setVisible(false);
			mapThree.setVisible(true);
		});
	}
	
	
	/**
	 * The birdOneToggleBtnHandler method loads bird one
	 */
	private void birdOneToggleBtnHandler() {
		birdOneToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(true);
			birdTwo.setVisible(false);
			birdThree.setVisible(false);
			
			birdOneVisible = true;
			birdTwoVisible = false;
			birdThreeVisible = false;
		});
	}
	
	
	/**
	 * The birdTwoToggleBtnHandler method loads bird two
	 */
	private void birdTwoToggleBtnHandler() {
		birdTwoToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(false);
			birdTwo.setVisible(true);
			birdThree.setVisible(false);
			
			birdOneVisible = false;
			birdTwoVisible = true;
			birdThreeVisible = false;
		});
	}
	
	
	/**
	 * The birdThreeToggleBtnHandler method loads bird three
	 */
	private void birdThreeToggleBtnHandler() {
		birdThreeToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(false);
			birdTwo.setVisible(false);
			birdThree.setVisible(true);
			
			birdOneVisible = false;
			birdTwoVisible = false;
			birdThreeVisible = true;
		});
	}
	
	
	/**
	 * The startGameHandler method contains a listener for a space bar press which will start the game
	 */
	private void startGameHandler() {
		
		/*
		 * The focus may be on any button on any time, so for the listener to function, this method 
		 * will check all buttons and call the startGame method... The startGame button handler 
		 * logic will work once the button-in-focus is found throughout the numerous function calls
		 */
		startGame(mapOneToggleBtn);
		startGame(mapTwoToggleBtn);
		startGame(mapThreeToggleBtn);
		startGame(birdOneToggleBtn);
		startGame(birdTwoToggleBtn);
		startGame(birdThreeToggleBtn);
	}
	
	
	/**
	 * The startGame method detects a space bar press and subsequently starts the game
	 * 
	 * @param button
	 */
	private void startGame(Button button) {
		button.setOnKeyPressed(e -> { // If a key has been pressed while the button is in focus...
			if (e.getCode() == KeyCode.SPACE) { // If the pressed key was the space bar...
				
				// TODO logic to retrieve chosen bird and also open the game UI
				
				if (onGameStart != null) {
					onGameStart.run();
				}
			}
		});
	}
	
	
	/**
	 * TODO
	 * 
	 * @param onGameStart
	 */
	public void setOnGameStartSuccess(Runnable onGameStart) {
		this.onGameStart = onGameStart;
	}
	
}

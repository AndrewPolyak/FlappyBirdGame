package controller;


import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * The FlappyBirdMenuController class contains the logic related to controlling the application's menu
 * 
 * @author Andrew Polyak
 * @version July 20, 2024
 */
public class FlappyBirdMenuController {
	
	private Button mapOneToggleBtn; // Represents the button to toggle map one
	private Button mapTwoToggleBtn; // Represents the button to toggle map two
	private Button mapThreeToggleBtn; // Represents the button to toggle map three
	
	private Button birdOneToggleBtn; // Represents the button to toggle bird one
	private Button birdTwoToggleBtn; // Represents the button to toggle bird two
	private Button birdThreeToggleBtn; // Represents the button to toggle bird three
	private Button birdFourToggleBtn; // TODO
	private Button birdFiveToggleBtn; // TODO
	private Button birdSixToggleBtn; // TODO
	
	private Button resetStatsBtn; // TODO
	
	private ImageView mapOne; // Represents map one
	private ImageView mapTwo; // Represents map two
	private ImageView mapThree; // Represents map three
	
	private ImageView birdOne; // Represents bird one
	private ImageView birdTwo; // Represents bird two
	private ImageView birdThree; // Represents bird three
	private ImageView birdFour; // TODO
	private ImageView birdFive; // TODO
	private ImageView birdSix; // TODO
	
	private boolean birdOneVisible = true; // Represents whether bird one is selected and visible
	private boolean birdTwoVisible = false; // Represents whether bird two is selected and visible
	private boolean birdThreeVisible = false; // TODO
	private boolean birdFourVisible = false; // TODO
	private boolean birdFiveVisible = false; // TODO
	private boolean birdSixVisible = false; // TODO
	
	private Runnable onGameStart; // Represents the thread to run the game
	
	
	/**
	 * The FlappyBirdMenuController initializes the properties relevant to menu controlling
	 * 
	 * @param mapOneToggleBtn
	 * @param mapTwoToggleBtn
	 * @param mapThreeToggleBtn
	 * @param birdOneToggleBtn
	 * @param birdTwoToggleBtn
	 * @param birdThreeToggleBtn
	 * @param birdFourToggleBtn
	 * @param birdFiveToggleBtn
	 * @param birdSixToggleBtn
	 * @param resetStatsBtn
	 * @param mapOne
	 * @param mapTwo
	 * @param mapThree
	 * @param birdOne
	 * @param birdTwo
	 * @param birdThree
	 * @param birdFour
	 * @param birdFive
	 * @param birdSix
	 * @param menuScreen
	 */
	public FlappyBirdMenuController(Button mapOneToggleBtn, Button mapTwoToggleBtn, Button mapThreeToggleBtn,
			Button birdOneToggleBtn, Button birdTwoToggleBtn, Button birdThreeToggleBtn, Button birdFourToggleBtn,
			Button birdFiveToggleBtn, Button birdSixToggleBtn, Button resetStatsBtn, ImageView mapOne, ImageView mapTwo, 
			ImageView mapThree, ImageView birdOne, ImageView birdTwo, ImageView birdThree, 
			ImageView birdFour, ImageView birdFive, ImageView birdSix, AnchorPane menuScreen) {
		this.mapOneToggleBtn = mapOneToggleBtn;
		this.mapTwoToggleBtn = mapTwoToggleBtn;
		this.mapThreeToggleBtn = mapThreeToggleBtn;
		this.birdOneToggleBtn = birdOneToggleBtn;
		this.birdTwoToggleBtn = birdTwoToggleBtn;
		this.birdThreeToggleBtn = birdThreeToggleBtn;
		this.birdFourToggleBtn = birdFourToggleBtn;
		this.birdFiveToggleBtn = birdFiveToggleBtn;
		this.birdSixToggleBtn = birdSixToggleBtn;
		this.resetStatsBtn = resetStatsBtn;
		this.mapOne = mapOne;
		this.mapTwo = mapTwo;
		this.mapThree = mapThree;
		this.birdOne = birdOne;
		this.birdTwo = birdTwo;
		this.birdThree = birdThree;
		this.birdFour = birdFour;
		this.birdFive = birdFive;
		this.birdSix = birdSix;
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
		birdFourToggleBtnHandler();
		birdFiveToggleBtnHandler();
		birdSixToggleBtnHandler();
		
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
	 * The birdOneToggleBtnHandler method loads bird one // FIXME
	 */
	private void birdOneToggleBtnHandler() {
		birdOneToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(true);
			birdTwo.setVisible(false);
			birdThree.setVisible(false);
			birdFour.setVisible(false);
			birdFive.setVisible(false);
			birdSix.setVisible(false);
			
			birdOneVisible = true;
			birdTwoVisible = false;
			birdThreeVisible = false;
			birdFourVisible = false;
			birdFiveVisible = false;
			birdSixVisible = false;
		});
	}
	
	
	/**
	 * The birdTwoToggleBtnHandler method loads bird two // FIXME
	 */
	private void birdTwoToggleBtnHandler() {
		birdTwoToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(false);
			birdTwo.setVisible(true);
			birdThree.setVisible(false);
			birdFour.setVisible(false);
			birdFive.setVisible(false);
			birdSix.setVisible(false);
			
			birdOneVisible = false;
			birdTwoVisible = true;
			birdThreeVisible = false;
			birdFourVisible = false;
			birdFiveVisible = false;
			birdSixVisible = false;
		});
	}
	
	
	/**
	 * The birdThreeToggleBtnHandler method loads bird three // FIXME
	 */
	private void birdThreeToggleBtnHandler() {
		birdThreeToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(false);
			birdTwo.setVisible(false);
			birdThree.setVisible(true);
			birdFour.setVisible(false);
			birdFive.setVisible(false);
			birdSix.setVisible(false);
			
			birdOneVisible = false;
			birdTwoVisible = false;
			birdThreeVisible = true;
			birdFourVisible = false;
			birdFiveVisible = false;
			birdSixVisible = false;
		});
	}
	
	
	/**
	 * TODO
	 */
	private void birdFourToggleBtnHandler() {
		birdFourToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(false);
			birdTwo.setVisible(false);
			birdThree.setVisible(false);
			birdFour.setVisible(true);
			birdFive.setVisible(false);
			birdSix.setVisible(false);
			
			birdOneVisible = false;
			birdTwoVisible = false;
			birdThreeVisible = false;
			birdFourVisible = true;
			birdFiveVisible = false;
			birdSixVisible = false;
		});
	}
	
	
	/**
	 * TODO
	 */
	private void birdFiveToggleBtnHandler() {
		birdFiveToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(false);
			birdTwo.setVisible(false);
			birdThree.setVisible(false);
			birdFour.setVisible(false);
			birdFive.setVisible(true);
			birdSix.setVisible(false);
			
			birdOneVisible = false;
			birdTwoVisible = false;
			birdThreeVisible = false;
			birdFourVisible = false;
			birdFiveVisible = true;
			birdSixVisible = false;
		});
	}
	
	
	/**
	 * TODO
	 */
	private void birdSixToggleBtnHandler() {
		birdSixToggleBtn.setOnMouseClicked(e -> {
			birdOne.setVisible(false);
			birdTwo.setVisible(false);
			birdThree.setVisible(false);
			birdFour.setVisible(false);
			birdFive.setVisible(false);
			birdSix.setVisible(true);
			
			birdOneVisible = false;
			birdTwoVisible = false;
			birdThreeVisible = false;
			birdFourVisible = false;
			birdFiveVisible = false;
			birdSixVisible = true;
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
		startGame(birdFourToggleBtn);
		startGame(birdFiveToggleBtn);
		startGame(birdSixToggleBtn);
		startGame(resetStatsBtn);
	}
	
	
	/**
	 * The startGame method detects a space bar press and subsequently starts the game
	 * 
	 * @param button
	 */
	private void startGame(Button button) {
		button.setOnKeyPressed(e -> { // If a key has been pressed while the button is in focus...
			if (e.getCode() == KeyCode.SPACE) { // If the pressed key was the space bar...
				
				if (onGameStart != null) {
					onGameStart.run(); // Run game thread
				}
			}
		});
	}
	
	
	/**
	 * The getBirdSkin method accesses the bird skin (i.e., ImageView object representing the bird's appearance)
	 * 
	 * @return bird's ImageView object
	 */
	public ImageView getBirdSkin() {
		if (birdOneVisible) {
			return birdOne;
		} else if (birdTwoVisible) {
			return birdTwo;
		} else if (birdThreeVisible) {
			return birdThree;
		} else if (birdFourVisible) {
			return birdFour;
		} else if (birdFiveVisible) {
			return birdFive;
		} else if (birdSixVisible) {
			return birdSix;
		} else {
			return null;
		}
	}
	
	
	/**
	 * The setOnGameStartSuccess method instantiates onGameStart (to allow the thread to run)
	 * 
	 * @param onGameStart
	 */
	public void setOnGameStartSuccess(Runnable onGameStart) {
		this.onGameStart = onGameStart;
	}
	
}

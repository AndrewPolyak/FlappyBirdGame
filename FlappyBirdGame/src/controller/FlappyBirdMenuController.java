package controller;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * TODO
 */
public class FlappyBirdMenuController {
	
	private Button mapOneToggleBtn; // TODO
	private Button mapTwoToggleBtn; // TODO
	private Button mapThreeToggleBtn; // TODO
	
	private Button birdOneToggleBtn; // TODO
	private Button birdTwoToggleBtn; // TODO
	private Button birdThreeToggleBtn; // TODO
	
	private ImageView mapOne; // TODO
	private ImageView mapTwo; // TODO
	private ImageView mapThree; // TODO
	
	private ImageView birdOne; // TODO
	private ImageView birdTwo; // TODO
	private ImageView birdThree; // TODO
	
	private boolean birdOneVisible; // TODO
	private boolean birdTwoVisible; // TODO
	private boolean birdThreeVisible; // TODO
	
	private AnchorPane menuScreen; // TODO
	
	
	/**
	 * TODO
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
	 * TODO
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
	 * TODO
	 */
	private void mapOneToggleBtnHandler() {
		mapOneToggleBtn.setOnMouseClicked(e -> {
			mapOne.setVisible(true);
			mapTwo.setVisible(false);
			mapThree.setVisible(false);
		});
	}
	
	
	/**
	 * TODO
	 */
	private void mapTwoToggleBtnHandler() {
		mapTwoToggleBtn.setOnMouseClicked(e -> {
			mapOne.setVisible(false);
			mapTwo.setVisible(true);
			mapThree.setVisible(false);
		});
	}
	
	
	/**
	 * TODO
	 */
	private void mapThreeToggleBtnHandler() {
		mapThreeToggleBtn.setOnMouseClicked(e -> {
			mapOne.setVisible(false);
			mapTwo.setVisible(false);
			mapThree.setVisible(true);
		});
	}
	
	
	/**
	 * TODO
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
	 * TODO
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
	 * TODO
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
	 * TODO
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
	 * TODO
	 * 
	 * @param button
	 */
	private void startGame(Button button) {
		button.setOnKeyPressed(e -> { // If a key has been pressed while a button is in focus...
			if (e.getCode() == KeyCode.SPACE) { // If the pressed key was the space bar...
				System.out.println("GAME STARTED");
			}
		});
	}
	
}

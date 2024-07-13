package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Bird;
import model.Tube;


/**
 * The FlappyBirdGameController class contains the logic for starting, running, and ending the gameplay of Flappy Bird
 * 
 * @author Andrew Polyak
 * @version July 13, 2024
 */
public class FlappyBirdGameController {

	private AnimationTimer animate; // Represents the animation timer that will run and animate the game
	
	private Rectangle topTubeOneHitBox; // Represents the hit box of the first top tube
	private Rectangle topTubeTwoHitBox; // Represents the hit box of the second top tube
	private Rectangle topTubeThreeHitBox; // Represents the hit box of the third top tube
	private Rectangle bottomTubeOneHitBox; // Represents the hit box of the first bottom tube
	private Rectangle bottomTubeTwoHitBox; // Represents the hit box of the second bottom tube
	private Rectangle bottomTubeThreeHitBox; // Represents the hit box of the third bottom tube
	
	private Bird bird; // Represents the bird model
	private Tube tube; // Represents the tube model
	
	private AnchorPane gameScreen; // Represents the UI elements of the game (i.e., bird, tubes, score)
	private AnchorPane menuScreen; // Represents the UI elements of the main menu (i.e., buttons, title card)
	private Button mapOneToggleBtn; // Represents the map one toggle button on the main menu (it is here because it needs to have focus placed back onto it after the game)
	
	private Text scoreCounter; // Represents the score counter UI element
	private int score; // Represents the current score
	
	private int TOP_WALL = 0; // Represents the y-coordinate of the top of the screen
	private int BOTTOM_WALL = 650; // Represents the y-coordinate of the bottom of the screen
	private static final int LEFT_WALL = 0; // Represents the x-coordinate of the left of the screen
	
	private boolean rising = false; // Represents the current state of the bird rising (true if rising, false otherwise)
	private double yCoordStart; // Represents the y-coordinate the bird starting it's rise at
	
	private double birdRotation = 0; // Represents the current bird rotation amount
	
	private static final int SCORE_COORDINATE = 500; // Represents the x coordinate which a tube must reach to facilitate a score increase
	
	private boolean tubeOnePairScoreCounts; // This boolean remains true if the first pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter
	private boolean tubeTwoPairScoreCounts; // This boolean remains true if the second pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter
	private boolean tubeThreePairScoreCounts; // This boolean remains true if the third pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter

	
	// TODO JavaDoc
	public FlappyBirdGameController(Rectangle topTubeOneHitBox, Rectangle topTubeTwoHitBox, Rectangle topTubeThreeHitBox, 
			Rectangle bottomTubeOneHitBox, Rectangle bottomTubeTwoHitBox, Rectangle bottomTubeThreeHitBox, AnchorPane gameScreen, 
			AnchorPane menuScreen, Button mapOneToggleBtn, Bird bird, Text scoreCounter) {
		this.topTubeOneHitBox = topTubeOneHitBox;
		this.topTubeTwoHitBox = topTubeTwoHitBox;
		this.topTubeThreeHitBox = topTubeThreeHitBox;
		this.bottomTubeOneHitBox = bottomTubeOneHitBox;
		this.bottomTubeTwoHitBox = bottomTubeTwoHitBox;
		this.bottomTubeThreeHitBox = bottomTubeThreeHitBox;
		this.gameScreen = gameScreen;
		this.menuScreen = menuScreen;
		this.mapOneToggleBtn = mapOneToggleBtn;
		this.bird = bird;
		this.scoreCounter = scoreCounter;
	}
	
	
	/**
	 * The play method sets up UI elements related to gameplay, gives an initial flap to the bird, and calls the AnimationTimer handler override method
	 */
	public void play() {
		scoreCounter.setVisible(true); // Display the score counter on the UI
		
		// Allow for an initial flap at game start
		rising = true;
		yCoordStart = bird.getyCoord(); // Y-coordinate at start of flap is used to measure maximum possible rise
		
		// Begin game animation logic
		animate = new AnimationTimer() {

			/**
			 * The handle override method contains the logic for running & animating the game
			 */
			@Override
			public void handle(long arg0) {
				moveBirdListener(); // Logic for player to control the bird
				moveTubes(); // Logic to move the tubes from right-to-left
				incrementScoreListener(); // Logic to check if the bird made it through a pair of tubes (to increment score)
				respawnTubesListener(); // Logic to respawn tubes once they reach the left side of the screen (for game to run indefinitely)
				endGameListener(); // Logic to end the game once the bird hits a tube, the floor, or the ceiling
			}
		};
		animate.start(); // Start game
	}
	
	
	/**
	 * The moveBirdListener method listens for a space bar press from the user to initialize a flap <br>
	 * Once the bird is ready to flap, the Bird class's rise method will be called to allow the bird to rise <br>
	 * The flapComplete method will be called to check if the bird has reached the maximum height allowed by a single flap <br>
	 * Once the maximum flap height is reached, the Bird class's fall method will be called too allow the bird to fall <br>
	 * The bird will rotate upward during rising and downward during falling to give the animation some more life
	 */
	private void moveBirdListener() {
		gameScreen.requestFocus(); // gameScreen needs focus to track space bar presses
		
		// Listen for the user SPACE input to set rising to true to begin ascent
		gameScreen.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.SPACE) { // If the user pressed the space bar...
				
				rising = true;
				yCoordStart = bird.getyCoord(); // Y-coordinate at start of flap is used to measure maximum possible rise
			}
		});
		
		// Once the maximum rise height for a flap is reached, set rising to false to begin descent
		if (flapComplete()) {
			rising = false;
		}
		
		// Move the bird
		if (rising) { // If the bird is allowed to rise (i.e., the space bar has been pressed and the maximum flap height has not been reached yet...)
			if (bird.getRotation() >= Bird.getMaxUpRotation()) { // If the bird is not yet at the maximum upward rotation...
				birdRotation = birdRotation - Bird.getUpwardRotationIncrement(); // Increment the rotation value upward
			}
			
			bird.rise(birdRotation); // Move the bird upward and rotate it
			
		} else { // If the bird is not allowed to rise (i.e., the maximum flap height has been met...)
			if (bird.getRotation() <= Bird.getMaxDownRotation()) { // If the bird is not yet at the maximum downward rotation..
				birdRotation = birdRotation + Bird.getDownwardRotationIncrement(); // Increment the rotation value downward
			} 
			
			bird.fall(birdRotation); // Move the bird downward and rotate it
		}
	}
	
	
	/**
	 * The flapComplete method returns true if the bird's y-coordinate height has met or exceeded it's maximum
	 * y-coordinate per rise relative to it's y-coordinate when the rise began and returns false if the maximum
	 * has not been exceeded yet
	 * 
	 * @return true if the max flap height is met, false otherwise
	 */
	private boolean flapComplete() {
		return yCoordStart - bird.getyCoord() >= Bird.getMaxRise();
	}
	
	
	/**
	 * TODO
	 */
	private void moveTubes() {
		// TODO
	}
	
	
	/**
	 * TODO
	 */
	private void respawnTubesListener() {
		if (tubesAtScreenEnd(bottomTubeOneHitBox, topTubeOneHitBox)) { // If the first pair of tubes have reached the edge of the screen...
			// TODO spawn pair of tubes
			tubeOnePairScoreCounts = true;
			
		} else if (tubesAtScreenEnd(bottomTubeTwoHitBox, topTubeTwoHitBox)) {
			// TODO spawn pair of tubes
			tubeTwoPairScoreCounts = true;
			
		} else if ((tubesAtScreenEnd(bottomTubeThreeHitBox, topTubeThreeHitBox))) {
			// TODO spawn pair of tubes
			tubeThreePairScoreCounts = true;
			
		}
	}
	
	
	/**
	 * TODO
	 * @param bottomTube
	 * @param topTube
	 * @return
	 */
	private boolean tubesAtScreenEnd(Rectangle bottomTube, Rectangle topTube) {
		return bottomTube.getLayoutX() <= LEFT_WALL && topTube.getLayoutX() <= LEFT_WALL;
	}
	
	
	/**
	 * The incrementScoreListener method adds +1 to the score if the bird successfully passed through a pair of tubes
	 */
	private void incrementScoreListener() {
		if (tubesPassedBird(bottomTubeOneHitBox, topTubeOneHitBox)) { // The bird passes through the first pair of tubes
			if (tubeOnePairScoreCounts) { // If the score has not already been added...
				score++;
				scoreCounter.setText(score + "");
				tubeOnePairScoreCounts = false; // Set to false to prevent score from constantly repeating (due to how logic works) before the tube resets
			}
			
		} else if (tubesPassedBird(bottomTubeTwoHitBox, topTubeTwoHitBox)) { // The bird passes through the second pair of tubes
			if (tubeTwoPairScoreCounts) { // If the score has not already been added...
				score++;
				scoreCounter.setText(score + "");
				tubeTwoPairScoreCounts = false; // Set to false to prevent score from constantly repeating (due to how logic works) before the tube resets
			}
			
		} else if (tubesPassedBird(bottomTubeThreeHitBox, topTubeThreeHitBox)) { // The bird passes through the third pair of tubes
			if (tubeThreePairScoreCounts) { // If the score has not already been added...
				score++;
				scoreCounter.setText(score + "");
				tubeThreePairScoreCounts = false; // Set to false to prevent score from constantly repeating (due to how logic works) before the tube resets
			}
		}
	}
	
	
	/**
	 * The tubesPassedBird method checks if the tubes have successfully passed by the bird without the bird hitting them (true) or not (false)
	 * 
	 * @param bottomTube
	 * @param topTube
	 * @return true if the tubes successfully passed by the bird, false otherwise
	 */
	private boolean tubesPassedBird(Rectangle bottomTube, Rectangle topTube) {
		return bottomTube.getLayoutX() <= SCORE_COORDINATE && topTube.getLayoutX() <= SCORE_COORDINATE;
	}
	
	
	/**
	 * The endGameListener method ends the game if the bird collides with the floor, ceiling, or any tube
	 */
	private void endGameListener() {
		if (birdTouchingWall() || birdTouchingTube()) { // If the bird collides with the floor, ceiling, or any tube...
			bird.spawn(); // Reset bird
			animate.stop(); // Stop animation timer (i.e., frame generation & game movement)
			scoreCounter.setVisible(false); // Hide score counter
			menuScreen.setVisible(true); // Show main menu
			mapOneToggleBtn.requestFocus(); // Set focus to a button on the main menu to allow for the user to easily start another game (because the space bar listener exists on a button node)
		} 
	}
	
	
	/**
	 * The birdTouchingWall method checks if the bird's y-coordinates have exceeded the boundaries of the top or bottom of the screen,
	 * and returns true if so, false otherwise
	 * 
	 * @return true if the bird's y-coordinates exceeded the bottom or top wall, false otherwise
	 */
	private boolean birdTouchingWall() {
		return (bird.getyCoord() > BOTTOM_WALL) || (bird.getyCoord() < TOP_WALL);
	}
	
	
	/**
	 * The birdTouchingTube method checks whether the bird has collided with any tube hit box, and returns true if so, false otherwise
	 * 
	 * @return true if the bird collided with a tube, false otherwise
	 */
	private boolean birdTouchingTube() {
		if (bird.hitTube(bottomTubeOneHitBox)) {
			return true;
		} else if (bird.hitTube(bottomTubeTwoHitBox)) {
			return true;
		} else if (bird.hitTube(bottomTubeThreeHitBox)) {
			return true;
		} else if (bird.hitTube(topTubeOneHitBox)) {
			return true;
		} else if (bird.hitTube(topTubeTwoHitBox)) {
			return true;
		} else if (bird.hitTube(topTubeThreeHitBox)) {
			return true;
		} else { // If the bird did not hit a tube, return false
			return false;
		}
	}
	
}

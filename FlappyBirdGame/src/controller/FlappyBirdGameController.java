package controller;

import java.security.SecureRandom;

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
 * @version July 19, 2024
 */
public class FlappyBirdGameController {

	private AnimationTimer animate; // Represents the animation timer that will run and animate the game
	
	private Tube topTubeOne;
	private Tube topTubeTwo;
	private Tube topTubeThree;
	private Tube bottomTubeOne;
	private Tube bottomTubeTwo;
	private Tube bottomTubeThree;
	
	private Bird bird; // Represents the bird model
	
	private AnchorPane gameScreen; // Represents the UI elements of the game (i.e., bird, tubes, score)
	private AnchorPane menuScreen; // Represents the UI elements of the main menu (i.e., buttons, title card)
	private Button mapOneToggleBtn; // Represents the map one toggle button on the main menu (it is here because it needs to have focus placed back onto it after the game)
	
	private Text scoreCounter; // Represents the score counter UI element
	private int score; // Represents the current score
	
	private int TOP_WALL = 0; // Represents the y-coordinate of the top of the screen
	private int BOTTOM_WALL = 650; // Represents the y-coordinate of the bottom of the screen
	private static final int LEFT_WALL = 0 - 100; // Represents the x-coordinate of the left of the screen (left wall - width of tube)
	
	private boolean rising = false; // Represents the current state of the bird rising (true if rising, false otherwise)
	private double yCoordStart; // Represents the y-coordinate the bird starting it's rise at
	
	private double birdRotation = 0; // Represents the current bird rotation amount
	
	private boolean tubeOnePairScoreCounts = true; // This boolean remains true if the first pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter
	private boolean tubeTwoPairScoreCounts = true; // This boolean remains true if the second pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter
	private boolean tubeThreePairScoreCounts = true; // This boolean remains true if the third pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter

	private boolean tubeTwoPairMoving = false;
	private boolean tubeThreePairMoving = false;
	
	private boolean initialSpawn = true;
	
	private static final double TUBES_MAX_DISTANCE_APART = 360;
	
	private SecureRandom random;
	
	
	// TODO JavaDoc
	public FlappyBirdGameController(AnchorPane gameScreen, AnchorPane menuScreen, Button mapOneToggleBtn, Bird bird, Text scoreCounter, 
			Tube topTubeOne, Tube topTubeTwo, Tube topTubeThree, Tube bottomTubeOne, Tube bottomTubeTwo, Tube bottomTubeThree) {
		this.gameScreen = gameScreen;
		this.menuScreen = menuScreen;
		this.mapOneToggleBtn = mapOneToggleBtn;
		this.bird = bird;
		this.scoreCounter = scoreCounter;
		this.topTubeOne = topTubeOne;
		this.topTubeTwo = topTubeTwo;
		this.topTubeThree = topTubeThree;
		this.bottomTubeOne = bottomTubeOne;
		this.bottomTubeTwo = bottomTubeTwo;
		this.bottomTubeThree = bottomTubeThree;
		random = new SecureRandom();
	}
	
	
	
	
	/**
	 * The play method sets up UI elements related to gameplay, gives an initial flap to the bird, and calls the AnimationTimer handler override method
	 */
	public void play() {
		score = 0;
		scoreCounter.setText(score + "");
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
				if (initialSpawn) {
					respawnTubes(true, true, true);
					initialSpawn = false;
				} else {
					respawnTubes(false, false, false); // Logic to respawn tubes once they reach the left side of the screen (for game to run indefinitely)
					moveBirdListener(); // Logic for player to control the bird
					moveTubes(); // Logic to move the tubes from right-to-left
					incrementScoreListener(); // Logic to check if the bird made it through a pair of tubes (to increment score)
					endGameListener(); // Logic to end the game once the bird hits a tube, the floor, or the ceiling
				}
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
		topTubeOne.move();
		bottomTubeOne.move();
		
		if (tubeTwoPairMoving) {
			topTubeTwo.move();
			bottomTubeTwo.move();
		}
		
		if (tubeThreePairMoving) {
			topTubeThree.move();
			bottomTubeThree.move();
		}
		
		if (topTubeTwo.getxCoord() - topTubeOne.getxCoord() >= TUBES_MAX_DISTANCE_APART) {
			tubeTwoPairMoving = true;
		}
		
		if (topTubeThree.getxCoord() - topTubeTwo.getxCoord() >= TUBES_MAX_DISTANCE_APART) {
			tubeThreePairMoving = true;
		}
		
		if (tubesAtScreenEnd(topTubeOne)) {
			respawnTubes(true, false, false);
		} else if (tubesAtScreenEnd(topTubeTwo)) {
			respawnTubes(false, true, false);
		} else if (tubesAtScreenEnd(topTubeThree)) {
			respawnTubes(false, false, true);
		}
	}
	
	
	/**
	 * TODO
	 */
	private void respawnTubes(boolean respawnOne, boolean respawnTwo, boolean respawnThree) {
		if (respawnOne) { 
			setTubeHeight(topTubeOne, bottomTubeOne, random.nextInt(3));
			topTubeOne.spawn();
			bottomTubeOne.spawn();
			tubeOnePairScoreCounts = true;
		}
		
		if (respawnTwo) {
			setTubeHeight(topTubeTwo, bottomTubeTwo, random.nextInt(3));
			topTubeTwo.spawn();
			bottomTubeTwo.spawn();
			tubeTwoPairScoreCounts = true;
		}
		
		if (respawnThree) {
			setTubeHeight(topTubeThree, bottomTubeThree, random.nextInt(3));
			topTubeThree.spawn();
			bottomTubeThree.spawn();
			tubeThreePairScoreCounts = true;
		}
	}
	
	
	private void setTubeHeight(Tube topTube, Tube bottomTube, int modifier) {
		if (modifier == 0) { // Top and bottom pipe height is equal
			topTube.spawnRegularTube(true);
			bottomTube.spawnRegularTube(false);
			
		} else if (modifier == 1) { // Top pipe is tall; Bottom pipe is short
			topTube.spawnTallTube(true);
			bottomTube.spawnShortTube(false); // FIXME
			
		} else if (modifier == 2){ // Top pipe is short; Bottom pipe is tall
			topTube.spawnShortTube(true);
			bottomTube.spawnTallTube(false); // FIXME
			
		}
	}
	
	
	/**
	 * TODO
	 * @param bottomTube
	 * @param topTube
	 * @return
	 */
	private boolean tubesAtScreenEnd(Tube topTube) {
		return topTube.getxCoord() <= LEFT_WALL;
	}
	
	
	/**
	 * The incrementScoreListener method adds +1 to the score if the bird successfully passed through a pair of tubes 
	 */
	private void incrementScoreListener() {
		if (tubesPassedBird(topTubeOne)) { // The bird passes through the first pair of tubes
			if (tubeOnePairScoreCounts) { // If the score has not already been added...
				score++;
				scoreCounter.setText(score + "");
				tubeOnePairScoreCounts = false; // Set to false to prevent score from constantly repeating (due to how logic works) before the tube resets
			}
			
		}
		
		if (tubesPassedBird(topTubeTwo)) { // The bird passes through the second pair of tubes
			if (tubeTwoPairScoreCounts) { // If the score has not already been added...
				score++;
				scoreCounter.setText(score + "");
				tubeTwoPairScoreCounts = false; // Set to false to prevent score from constantly repeating (due to how logic works) before the tube resets
			}
			
		}
		
		if (tubesPassedBird(topTubeThree)) { // The bird passes through the third pair of tubes
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
	private boolean tubesPassedBird(Tube topTube) {
		return topTube.getxCoord() <= bird.getxCoord() - topTube.getHitBox().getWidth();
	}
	
	
	/**
	 * The endGameListener method ends the game if the bird collides with the floor, ceiling, or any tube
	 */
	private void endGameListener() {
		if (birdTouchingWall() || birdTouchingTube()) { // If the bird collides with the floor, ceiling, or any tube...
			bird.spawn(); // Reset bird
			respawnTubes(true, true, true);
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
		if (bird.hitTube(bottomTubeOne.getHitBox())) {
			return true;
		} else if (bird.hitTube(bottomTubeTwo.getHitBox())) {
			return true;
		} else if (bird.hitTube(bottomTubeThree.getHitBox())) {
			return true;
		} else if (bird.hitTube(topTubeOne.getHitBox())) {
			return true;
		} else if (bird.hitTube(topTubeTwo.getHitBox())) {
			return true;
		} else if (bird.hitTube(topTubeThree.getHitBox())) {
			return true;
		} else { // If the bird did not hit a tube, return false
			return false;
		}
	}
	
}

package controller;

import java.security.SecureRandom;

import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import model.Bird;
import model.Tube;


/**
 * The FlappyBirdGameController class contains the logic for starting, running, and ending the gameplay of Flappy Bird
 * 
 * @author Andrew Polyak
 * @version July 21, 2024
 */
public class FlappyBirdGameController {

	private DataController data; // Represents a DataController object to save and load high score data
	
	private AnimationTimer animate; // Represents the animation timer that will run and animate the game
	
	private Tube topTubeOne; // Represents the first top tube
	private Tube topTubeTwo; // Represents the second top tube
	private Tube topTubeThree; // Represents the third top tube
	private Tube bottomTubeOne; // Represents the first bottom tube
	private Tube bottomTubeTwo; // Represents the second bottom tube
	private Tube bottomTubeThree; // Represents the third bottom tube
	
	private Bird bird; // Represents the bird model
	
	private AnchorPane gameScreen; // Represents the UI elements of the game (i.e., bird, tubes, score)
	private AnchorPane postGameScreen; // Represents the UI elements of the post-game screen
	
	private Text gameEndHighScore; // Represents the high score displayed on postGameScreen
	private Text gameEndLastScore; // Represents the score achieved from the last game played displayed on postGameScreen
	private int allTimeHighScore; // Represents gameEndHighScore as an integer value
	
	private Text mainMenuHighScoreCounter; // Represents the high score displayed on main menu
	
	private Text scoreCounter; // Represents the score counter UI element
	private int score; // Represents the current score
	
	private int TOP_WALL = 0; // Represents the y-coordinate of the top of the screen
	private int BOTTOM_WALL = 650; // Represents the y-coordinate of the bottom of the screen
	private static final int LEFT_WALL = -100; // Represents the x-coordinate of the left of the screen (left wall - width of tube)
	
	private boolean rising = false; // Represents the current state of the bird rising (true if rising, false otherwise)
	private double yCoordStart; // Represents the y-coordinate the bird starting it's rise at
	
	private double birdRotation = 0; // Represents the current bird rotation amount
	
	private boolean tubeOnePairScoreCounts = true; // This boolean remains true if the first pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter
	private boolean tubeTwoPairScoreCounts = true; // This boolean remains true if the second pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter
	private boolean tubeThreePairScoreCounts = true; // This boolean remains true if the third pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter

	private boolean tubeTwoPairMoving = false; // Represents whether the second tube pair is moving (true) or not (false) (starts as false)
	private boolean tubeThreePairMoving = false; // Represents whether the third tube pair is moving (true) or not (false) (starts as false)
	
	private boolean initialSpawn = true; // Represents whether the tubes are spawning for the first time in the game (true) or not (false)
	
	private static final double TUBES_MAX_DISTANCE_APART = 360; // Represents the maximum distance between any pair of tubes - they must remain at this distance from each other
	
	private SecureRandom random; // Represents a random number generator
	
	private AudioClip gameOverSound; // Represents an AudioClip object representing the sound effect played for the game-over instance
	private AudioClip scoreAddition; // Represents an AudioClip object representing the sound effect played for when the player scores a point
	
	
	/**
	 * The FlappyBirdGameController constructor initializes all elements related to running a Flappy Bird game
	 * 
	 * @param gameScreen
	 * @param postGameScreen
	 * @param gameEndHighScore
	 * @param gameEndLastScore
	 * @param bird
	 * @param scoreCounter
	 * @param topTubeOne
	 * @param topTubeTwo
	 * @param topTubeThree
	 * @param bottomTubeOne
	 * @param bottomTubeTwo
	 * @param bottomTubeThree
	 */
	public FlappyBirdGameController(
			AnchorPane gameScreen, 
			AnchorPane postGameScreen, 
			Text gameEndHighScore, 
			Text gameEndLastScore, 
			Text mainMenuHighScoreCounter,
			Bird bird, 
			Text scoreCounter, 
			Tube topTubeOne, 
			Tube topTubeTwo, 
			Tube topTubeThree, 
			Tube bottomTubeOne, 
			Tube bottomTubeTwo, 
			Tube bottomTubeThree) {
		this.data = new DataController();
		this.gameScreen = gameScreen;
		this.postGameScreen = postGameScreen;
		this.gameEndHighScore = gameEndHighScore;
		this.gameEndLastScore = gameEndLastScore;
		this.mainMenuHighScoreCounter = mainMenuHighScoreCounter;
		this.bird = bird;
		this.scoreCounter = scoreCounter;
		this.topTubeOne = topTubeOne;
		this.topTubeTwo = topTubeTwo;
		this.topTubeThree = topTubeThree;
		this.bottomTubeOne = bottomTubeOne;
		this.bottomTubeTwo = bottomTubeTwo;
		this.bottomTubeThree = bottomTubeThree;
		random = new SecureRandom();
		gameOverSound = new AudioClip(getClass().getResource("/game_assets/game_over.mp3").toExternalForm());
		scoreAddition = new AudioClip(getClass().getResource("/game_assets/score_addition.mp3").toExternalForm());
	}
	
	
	/**
	 * The play method sets up UI elements related to gameplay, gives an initial flap to the bird, and calls the AnimationTimer handler override method
	 */
	public void play() {
		// Initialize high score
		allTimeHighScore = data.loadHighScore();
		gameEndHighScore.setText(allTimeHighScore + "");
		
		// Initialize score counter
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
				if (initialSpawn) { // Perform this block of logic before the main logic block
					respawnTubes(true, true, true); // Ensure all tubes are spawned properly
					initialSpawn = false; // Ensure this block doesn't run again
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
	 * The moveTubes method contains the logic for infinitely moving and respawning the tubes so the gameplay can continue indefinitely
	 */
	private void moveTubes() {
		// Start moving the first pair of tubes by default
		topTubeOne.move();
		bottomTubeOne.move();
		
		// Once the boolean is true, move the second pair of tubes
		if (tubeTwoPairMoving) {
			topTubeTwo.move();
			bottomTubeTwo.move();
		}
		
		// Once the boolean is true, move the third pair of tubes
		if (tubeThreePairMoving) {
			topTubeThree.move();
			bottomTubeThree.move();
		}
		
		// If the second pair of tubes are sufficient distance from the first pair, set their movement boolean to true
		if (tubesMaxDistanceApart(topTubeTwo, topTubeOne)) {
			tubeTwoPairMoving = true;
		}
		
		// If the third pair of tubes are sufficient distance from the second pair, set their movement boolean to true
		if (tubesMaxDistanceApart(topTubeThree, topTubeTwo)) {
			tubeThreePairMoving = true;
		}
		
		// Once the tube pairs reach the end of the screen, respawn them at the starting position to continue the gameplay indefinitely (recycle 3 pairs infinitely)
		if (tubesAtScreenEnd(topTubeOne)) { // If the first pair reaches the end of the screen, respawn them, and so on...
			respawnTubes(true, false, false);
		} else if (tubesAtScreenEnd(topTubeTwo)) {
			respawnTubes(false, true, false);
		} else if (tubesAtScreenEnd(topTubeThree)) {
			respawnTubes(false, false, true);
		}
	}
	
	
	/**
	 * The respawnTubes method takes in a boolean value to represent which pair of tubes should be respawned,
	 * and respawns them at the beginning of their cycle in a random position (i.e., gap is near top, near bottom, or in middle)
	 */
	private void respawnTubes(boolean respawnOne, boolean respawnTwo, boolean respawnThree) {
		if (respawnOne) { // Respawn the first tube pair
			setTubeHeight(topTubeOne, bottomTubeOne, random.nextInt(3)); // Randomly pick the tube's heights
			topTubeOne.spawn();
			bottomTubeOne.spawn();
			tubeOnePairScoreCounts = true; // Allow the tube pair to once again allow the user to score if they make it through them
		}
		
		if (respawnTwo) { // Respawn the second tube pair
			setTubeHeight(topTubeTwo, bottomTubeTwo, random.nextInt(3)); // Randomly pick the tube's heights
			topTubeTwo.spawn();
			bottomTubeTwo.spawn();
			tubeTwoPairScoreCounts = true; // Allow the tube pair to once again allow the user to score if they make it through them
		}
		
		if (respawnThree) { // Respawn the third tube pair
			setTubeHeight(topTubeThree, bottomTubeThree, random.nextInt(3)); // Randomly pick the tube's heights
			topTubeThree.spawn();
			bottomTubeThree.spawn();
			tubeThreePairScoreCounts = true; // Allow the tube pair to once again allow the user to score if they make it through them
		}
	}
	
	
	/**
	 * The setTubeHeight method takes in a tube pair and a randomly generated modifier, then sets the tube's heights accordingly
	 * 
	 * @param topTube
	 * @param bottomTube
	 * @param modifier
	 */
	private void setTubeHeight(Tube topTube, Tube bottomTube, int modifier) {
		if (modifier == 0) { // Top and bottom pipe height is equal
			topTube.spawnRegularTube(true);
			bottomTube.spawnRegularTube(false);
			
		} else if (modifier == 1) { // Top pipe is tall; Bottom pipe is short
			topTube.spawnTallTube(true);
			bottomTube.spawnShortTube(false);
			
		} else if (modifier == 2){ // Top pipe is short; Bottom pipe is tall
			topTube.spawnShortTube(true);
			bottomTube.spawnTallTube(false);
			
		}
	}
	
	
	/**
	 * The incrementScoreListener method adds +1 to the score if the bird successfully passed through a pair of tubes 
	 */
	private void incrementScoreListener() {
		if (tubesPassedBird(topTubeOne)) { // The bird passes through the first pair of tubes
			if (tubeOnePairScoreCounts) { // If the score has not already been added...
				scoreAddition.play();
				score++;
				scoreCounter.setText(score + "");
				tubeOnePairScoreCounts = false; // Set to false to prevent score from constantly repeating (due to how logic works) before the tube resets
			}
			
		}
		
		if (tubesPassedBird(topTubeTwo)) { // The bird passes through the second pair of tubes
			if (tubeTwoPairScoreCounts) { // If the score has not already been added...
				scoreAddition.play();
				score++;
				scoreCounter.setText(score + "");
				tubeTwoPairScoreCounts = false; // Set to false to prevent score from constantly repeating (due to how logic works) before the tube resets
			}
			
		}
		
		if (tubesPassedBird(topTubeThree)) { // The bird passes through the third pair of tubes
			if (tubeThreePairScoreCounts) { // If the score has not already been added...
				scoreAddition.play();
				score++;
				scoreCounter.setText(score + "");
				tubeThreePairScoreCounts = false; // Set to false to prevent score from constantly repeating (due to how logic works) before the tube resets
			}
		}
	}
	
	
	/**
	 * The endGameListener method ends the game if the bird collides with the floor, ceiling, or any tube
	 */
	private void endGameListener() {
		if (birdTouchingWall() || birdTouchingTube()) { // If the bird collides with the floor, ceiling, or any tube...
			gameOverSound.play();
			bird.spawn(); // Reset bird
			respawnTubes(true, true, true); // Reset all tubes
			animate.stop(); // Stop animation timer (i.e., frame generation & game movement)
			scoreCounter.setVisible(false); // Hide score counter
			updateHighScore(); // Update high score counter on post game screen
			postGameScreen.setVisible(true); // Display the post-game screen
			postGameScreen.requestFocus();
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
	 * The tubesMaxDistanceApart method checks whether the non-moving tube is TUBES_MAX_DISTANCE_APART away from the moving tube
	 * 
	 * @param delayedTube
	 * @param movingTube
	 * @return true if the max distance has been met or exceeded, false otherwise
	 */
	private boolean tubesMaxDistanceApart(Tube delayedTube, Tube movingTube) {
		return delayedTube.getxCoord() - movingTube.getxCoord() >= TUBES_MAX_DISTANCE_APART;
	}
	
	
	/**
	 * The tubesAtScreenEnd method checks if a tube pair (just checking topTube is enough, since the bottomTube is synced) has reached the end of the screen)
	 * 
	 * @param bottomTube
	 * @param topTube
	 * @return true if the tube has reached LEFT_WALL, false otherwise
	 */
	private boolean tubesAtScreenEnd(Tube topTube) {
		return topTube.getxCoord() <= LEFT_WALL;
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
	 * The updateHighScore method displays the user's achieved score and high score on the game-end screen <br>
	 * If the user has achieved a new high score, this is reflected in the game-end screen and is saved to the user's high-score database
	 */
	private void updateHighScore() {
		if (score > Integer.parseInt(gameEndLastScore.getText())) { // If new high score was achieved...
			gameEndHighScore.setText(score + ""); // Display the new high score on the post-game screen
			mainMenuHighScoreCounter.setText(score + ""); // Display the new high score on the main menu
			data.saveHighScore(score + ""); // Save the new high score
		}
		gameEndLastScore.setText(score + "");
		
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

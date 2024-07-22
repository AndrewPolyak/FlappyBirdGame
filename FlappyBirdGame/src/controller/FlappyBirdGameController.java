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

	private DataController data;
	
	private AnimationTimer animate; // Represents the animation timer that will run and animate the game
	
	private Tube topTubeOne; // Represents the first top tube
	private Tube topTubeTwo; // Represents the second top tube
	private Tube topTubeThree; // Represents the third top tube
	private Tube bottomTubeOne; // Represents the first bottom tube
	private Tube bottomTubeTwo; // Represents the second bottom tube
	private Tube bottomTubeThree; // Represents the third bottom tube
	
	private Bird bird; // Represents the bird model
	
	private AnchorPane gameScreen; // Represents the UI elements of the game (i.e., bird, tubes, score)
	private AnchorPane postGameScreen; // TODO
	
	private Text gameEndHighScore; // TODO
	private Text gameEndLastScore; // TODO
	private int allTimeHighScore; // TODO
	
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
	
	private AudioClip gameOverSound; // TODO
	private AudioClip scoreAddition; // TODO
	
	
	// TODO doc
	public FlappyBirdGameController(
			AnchorPane gameScreen, 
			AnchorPane postGameScreen, 
			Text gameEndHighScore, 
			Text gameEndLastScore, 
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
		
		if (tubesMaxDistanceApart(topTubeTwo, topTubeOne)) {
			tubeTwoPairMoving = true;
		}
		
		if (tubesMaxDistanceApart(topTubeThree, topTubeTwo)) {
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
	 * 
	 * @param delayedTube
	 * @param movingTube
	 * @return
	 */
	private boolean tubesMaxDistanceApart(Tube delayedTube, Tube movingTube) {
		return delayedTube.getxCoord() - movingTube.getxCoord() >= TUBES_MAX_DISTANCE_APART;
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
	
	
	/**
	 * TODO
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
			bottomTube.spawnShortTube(false); // FIXME
			
		} else if (modifier == 2){ // Top pipe is short; Bottom pipe is tall
			topTube.spawnShortTube(true);
			bottomTube.spawnTallTube(false); // FIXME
			
		}
	}
	
	
	/**
	 * TODO
	 * 
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
			gameOverSound.play();
			bird.spawn(); // Reset bird
			respawnTubes(true, true, true);
			animate.stop(); // Stop animation timer (i.e., frame generation & game movement)
			scoreCounter.setVisible(false); // Hide score counter
			updateHighScore(); // Update high score counter on post game screen
			postGameScreen.setVisible(true);
			postGameScreen.requestFocus();
		} 
	}
	
	
	/**
	 * TODO
	 */
	private void updateHighScore() {
		if (score > Integer.parseInt(gameEndLastScore.getText())) {
			gameEndHighScore.setText(score + "");
			data.saveData(score + "");
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

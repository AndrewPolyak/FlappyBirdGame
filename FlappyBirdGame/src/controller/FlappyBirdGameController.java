package controller;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Bird;
import model.Tube;


/**
 * TODO
 * 
 * @author Andrew Polyak
 * @version July 13, 2024
 */
public class FlappyBirdGameController {

	private AnimationTimer animate; // Represents the animation timer that will run and animate the game
	
	private Circle birdHitBox; // TODO comment
	
	private Rectangle topPipeOneHitBox; // TODO comment
	private Rectangle topPipeTwoHitBox; // TODO comment
	private Rectangle topPipeThreeHitBox; // TODO comment
	private Rectangle bottomPipeOneHitBox; // TODO comment
	private Rectangle bottomPipeTwoHitBox; // TODO comment
	private Rectangle bottomPipeThreeHitBox; // TODO comment
	
	private Bird bird; // TODO comment
	private Tube tube; // TODO comment
	
	private AnchorPane gameScreen; // TODO comment
	private AnchorPane menuScreen; // TODO comment
	private Button mapOneToggleBtn; // TODO comment
	
	private Text scoreCounter; // TODO comment
	private int score;
	
	private int TOP_WALL = 0; // TODO comment
	private int BOTTOM_WALL = 650; // TODO comment
	private static final int LEFT_WALL = 0; // TODO comment
	
	private boolean rising = false; // TODO comment
	private double yCoordStart; // TODO comment
	
	private double birdRotation = 0; // TODO comment
	private static final double BIRD_UPWARD_ROTATION = 5; // TODO comment
	private static final double BIRD_DOWNWARD_ROTATION = 2; // TODO comment
	
	private static final int SCORE_COORDINATE = 500; // Represents the x coordinate which a tube must reach to facilitate a score increase
	
	private boolean tubeOnePairScoreCounts; // This boolean remains true if the first pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter
	private boolean tubeTwoPairScoreCounts; // This boolean remains true if the second pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter
	private boolean tubeThreePairScoreCounts; // This boolean remains true if the third pair of tubes have not been passed through by the bird, and become false when the bird passes through them... This is to ensure only one point is added to the score counter

	
	// TODO JavaDoc
	public FlappyBirdGameController(Circle birdHitBox, Rectangle topPipeOneHitBox,
			Rectangle topPipeTwoHitBox, Rectangle topPipeThreeHitBox, Rectangle bottomPipeOneHitBox,
			Rectangle bottomPipeTwoHitBox, Rectangle bottomPipeThreeHitBox, AnchorPane gameScreen, AnchorPane menuScreen, Button mapOneToggleBtn,
			Bird bird, Text scoreCounter) {
		this.birdHitBox = birdHitBox;
		this.topPipeOneHitBox = topPipeOneHitBox;
		this.topPipeTwoHitBox = topPipeTwoHitBox;
		this.topPipeThreeHitBox = topPipeThreeHitBox;
		this.bottomPipeOneHitBox = bottomPipeOneHitBox;
		this.bottomPipeTwoHitBox = bottomPipeTwoHitBox;
		this.bottomPipeThreeHitBox = bottomPipeThreeHitBox;
		this.gameScreen = gameScreen;
		this.menuScreen = menuScreen;
		this.mapOneToggleBtn = mapOneToggleBtn;
		this.bird = bird;
		this.scoreCounter = scoreCounter;
	}
	
	
	/**
	 * TODO
	 */
	public void play() {
		scoreCounter.setVisible(true);
		
		// Allow for an initial flap at game start
		rising = true;
		yCoordStart = bird.getyCoord(); // Y-coordinate at start of flap is used to measure maximum possible rise
		
		// Begin game animation logic
		animate = new AnimationTimer() {

			/**
			 * TODO
			 */
			@Override
			public void handle(long arg0) {
				moveBirdListener(); // Logic for player to control the bird
				moveTubes(); // Logic to move the tubes from right-to-left
				incrementScoreListener(); // Logic to check if the bird made it through a pair of tubes
				respawnTubesListener(); // Logic to respawn tubes once they reach the left side of the screen
				endGameListener(); // Logic to end the game once the bird hits a tube, the floor, or the ceiling
			}
		};
		animate.start(); // Start game
	}
	
	
	/**
	 * TODO
	 */
	private void moveBirdListener() {
		gameScreen.requestFocus();
		
		// Listen for the user SPACE input to set rising to true to begin ascent
		gameScreen.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.SPACE) {
				
				rising = true;
				yCoordStart = bird.getyCoord(); // Y-coordinate at start of flap is used to measure maximum possible rise
			}
		});
		
		// Once the maximum rise height for a flap is reached, set rising to false to begin descent
		if (flapComplete()) {
			rising = false;
		}
		
		// Move the bird
		if (rising) {
			
			if (bird.getRotation() >= Bird.getMaxUpRotation()) {
				birdRotation = birdRotation - BIRD_UPWARD_ROTATION;
			}
			
			bird.rise(birdRotation);
			
		} else {
			
			if (bird.getRotation() <= Bird.getMaxDownRotation()) {
				birdRotation = birdRotation + BIRD_DOWNWARD_ROTATION;
			} 
			
			bird.fall(birdRotation);
		}
	}
	
	
	/**
	 * TODO
	 * 
	 * @return
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
		if (tubesAtScreenEnd(bottomPipeOneHitBox, topPipeOneHitBox)) { // If the first pair of tubes have reached the edge of the screen...
			// TODO spawn pair of tubes
			tubeOnePairScoreCounts = true;
			
		} else if (tubesAtScreenEnd(bottomPipeTwoHitBox, topPipeTwoHitBox)) {
			// TODO spawn pair of tubes
			tubeTwoPairScoreCounts = true;
			
		} else if ((tubesAtScreenEnd(bottomPipeThreeHitBox, topPipeThreeHitBox))) {
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
	 * TODO
	 */
	private void incrementScoreListener() {
		
		if (tubesPassedBird(bottomPipeOneHitBox, topPipeOneHitBox)) {
			if (tubeOnePairScoreCounts) {
				score++;
				scoreCounter.setText(score + "");
				tubeOnePairScoreCounts = false;
			}
			
		} else if (tubesPassedBird(bottomPipeTwoHitBox, topPipeTwoHitBox)) {
			if (tubeTwoPairScoreCounts) {
				score++;
				scoreCounter.setText(score + "");
				tubeTwoPairScoreCounts = false;
			}
			
		} else if (tubesPassedBird(bottomPipeThreeHitBox, topPipeThreeHitBox)) {
			if (tubeThreePairScoreCounts) {
				score++;
				scoreCounter.setText(score + "");
				tubeThreePairScoreCounts = false;
			}
		}
	}
	
	
	/**
	 * TODO
	 * 
	 * @param bottomTube
	 * @param topTube
	 * @return
	 */
	private boolean tubesPassedBird(Rectangle bottomTube, Rectangle topTube) {
		return bottomTube.getLayoutX() <= SCORE_COORDINATE && topTube.getLayoutX() <= SCORE_COORDINATE;
	}
	
	
	/**
	 * TODO
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
	 * TODO
	 * 
	 * @return
	 */
	private boolean birdTouchingWall() {
		return (bird.getyCoord() > BOTTOM_WALL) || (bird.getyCoord() < TOP_WALL);
	}
	
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	private boolean birdTouchingTube() {
		if (bird.hitTube(bottomPipeOneHitBox)) {
			return true;
		} else if (bird.hitTube(bottomPipeTwoHitBox)) {
			return true;
		} else if (bird.hitTube(bottomPipeThreeHitBox)) {
			return true;
		} else if (bird.hitTube(topPipeOneHitBox)) {
			return true;
		} else if (bird.hitTube(topPipeTwoHitBox)) {
			return true;
		} else if (bird.hitTube(topPipeThreeHitBox)) {
			return true;
		} else {
			return false;
		}
	}
	
}

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
 * @version July 11, 2024
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
	
	private Text scoreCounter; // TODO comment
	private int score;
	
	private int TOP_WALL = 0; // TODO comment
	private int BOTTOM_WALL = 650; // TODO comment
	
	private boolean rising = false; // TODO comment
	private double yCoordStart; // TODO comment
	
	private double birdRotation = 0; // TODO comment
	private static final double BIRD_UPWARD_ROTATION = 5; // TODO comment
	private static final double BIRD_DOWNWARD_ROTATION = 2; // TODO comment
	
	
	// TODO JavaDoc
	public FlappyBirdGameController(Circle birdHitBox, Rectangle topPipeOneHitBox,
			Rectangle topPipeTwoHitBox, Rectangle topPipeThreeHitBox, Rectangle bottomPipeOneHitBox,
			Rectangle bottomPipeTwoHitBox, Rectangle bottomPipeThreeHitBox, AnchorPane gameScreen,
			Bird bird, Text scoreCounter) {
		this.birdHitBox = birdHitBox;
		this.topPipeOneHitBox = topPipeOneHitBox;
		this.topPipeTwoHitBox = topPipeTwoHitBox;
		this.topPipeThreeHitBox = topPipeThreeHitBox;
		this.bottomPipeOneHitBox = bottomPipeOneHitBox;
		this.bottomPipeTwoHitBox = bottomPipeTwoHitBox;
		this.bottomPipeThreeHitBox = bottomPipeThreeHitBox;
		this.gameScreen = gameScreen;
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
				moveBird();
				
				endGame();
			}
		};
		animate.start(); // Start game
	}
	
	
	/**
	 * TODO
	 */
	private void moveBird() {
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
	private void endGame() {
		if (birdTouchingWall()) {
			bird.spawn();
			animate.stop();
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
	
}

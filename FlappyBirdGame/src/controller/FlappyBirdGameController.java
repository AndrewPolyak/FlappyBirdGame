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
 * @version TODO
 */
public class FlappyBirdGameController {

	private AnimationTimer animate; // Represents the animation timer that will run and animate the game
	
	private Circle birdHitBox;
	
	private Rectangle topPipeOneHitBox;
	private Rectangle topPipeTwoHitBox;
	private Rectangle topPipeThreeHitBox;
	private Rectangle bottomPipeOneHitBox;
	private Rectangle bottomPipeTwoHitBox;
	private Rectangle bottomPipeThreeHitBox;
	
	private Bird bird;
	private Tube tube;
	
	private AnchorPane gameScreen;
	
	private Text scoreCounter;
	
	private int TOP_WALL = 0;
	private int BOTTOM_WALL = 650;
	
	private boolean rising = false;
	private double yCoordStart;
	
	
	
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
			bird.rise();
		} else {
			bird.fall();
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

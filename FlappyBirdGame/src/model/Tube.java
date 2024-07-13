package model;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * The Tube class contains the unique properties and methods related to tubes
 * 
 * @author Andrew Polyak
 * @version July 5, 2024
 */
public class Tube {
	
	private static final int X_SPAWN = 1000; // Represents the x-coordinate the tube will spawn on
	private static final int Y_TOP_SPAWN = 0; // Represents the y-coordinate the top-tube will spawn on
	private static final int Y_BOTTOM_SPAWN = 425; // Represents the y-coordinate the bottom-tube will spawn on
	
	private static final double X_VELOCITY = 5; // Represents the horizontal velocity of the tube from right-to-left
	
	private boolean topTube; // Represents whether the tube should spawn on the top of the screen (true) or not (false)
	
	private double height; // Represents the tube's height
	
	private ImageView tube; // Represents the tube sprite
	private Rectangle tubeHitBox; // Represents the tube hit box
	

	/**
	 * The Tube constructor initializes the tube's unique properties
	 * 
	 * @param xCoordLeft
	 * @param xCoordRight
	 * @param yCoordTop
	 * @param yCoordBottom
	 * @param tube
	 * @param topTube
	 */
	public Tube(ImageView tube, Rectangle tubeHitBox, boolean topTube, double height) {
		this.tube = tube;
		this.tubeHitBox = tubeHitBox;
		this.topTube = topTube;
		this.height = height;
	}

	
	/**
	 * The move method moves the tube leftwards
	 */
	public void move() {
		tube.setLayoutX(tube.getLayoutX() - X_VELOCITY); // Move tube leftwards
		syncHitBox();
	}
	
	
	/**
	 * The spawn method spawns the tube on the right side of the screen and either on the top or bottom of the screen depending on topTube's boolean value
	 */
	public void spawn() {
		if (topTube) { // If tube is meant to spawn on the top...
			tube.setLayoutY(Y_TOP_SPAWN); // Spawn tube on top of screen
		} else {
			tube.setLayoutY(Y_BOTTOM_SPAWN); // Spawn tube on bottom of screen
		}
		tube.setLayoutX(X_SPAWN); // Spawn the tube on the right of the screen
		syncHitBox();
	}
	
	
	/**
	 * The syncHitBox method sets aligns the tube's hit box coordinates with it's sprite coordinates
	 */
	private void syncHitBox() {
		tube.setLayoutY(tubeHitBox.getLayoutY());
		tube.setLayoutX(tubeHitBox.getLayoutX());
	}


	/**
	 * The getHeight method accesses the tube's height value
	 * 
	 * @return height
	 */
	public double getHeight() {
		return height;
	}


	/**
	 * The setHeight method assigns a value to height
	 * 
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
	}

}

package model;

import javafx.scene.shape.Rectangle;

/**
 * The Tube class is a GameModel child class which contains the unique properties and methods related to tubes
 * 
 * @author Andrew Polyak
 * @version June 30, 2024
 */
public class Tube extends GameModel {
	
	private static final int X_SPAWN = 1000; // Represents the x-coordinate the tube will spawn on
	private static final int Y_TOP_SPAWN = 0; // Represents the y-coordinate the top-tube will spawn on
	private static final int Y_BOTTOM_SPAWN = 425; // Represents the y-coordinate the bottom-tube will spawn on
	
	private static final double X_VELOCITY = 5; // Represents the horizontal velocity of the tube from right-to-left
	
	boolean topTube; // Represents whether the tube should spawn on the top of the screen (true) or not (false)
	
	private Rectangle tube; // Represents the tube
	

	/**
	 * The Tube constructor initializes the tube's superclass as well as it's own unique properties
	 * 
	 * @param xCoordLeft
	 * @param xCoordRight
	 * @param yCoordTop
	 * @param yCoordBottom
	 * @param tube
	 * @param topTube
	 */
	public Tube(double xCoordLeft, double xCoordRight, double yCoordTop, double yCoordBottom, Rectangle tube, boolean topTube) {
		super(xCoordLeft, xCoordRight, yCoordTop, yCoordBottom);
		this.topTube = topTube;
	}

	
	/**
	 * The move method moves the tube leftwards
	 */
	public void move() {
		tube.setLayoutX(tube.getLayoutX() - X_VELOCITY); // Move tube leftwards
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
	}

}

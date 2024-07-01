package model;

import javafx.scene.shape.Circle;


/**
 * The Bird class is a GameModel child class which contains the unique properties and methods related to the bird
 * 
 * @author Andrew Polyak
 * @version June 30, 2024
 */
public class Bird extends GameModel {

	private static final int RISE_VELOCITY = 5; // Represents the vertical velocity caused by the bird's flap
	private static final int MAX_RISE = 50; // Represents the vertical distance the bird's flap will cover
	
	private static final int MIN_FALL_VELOCITY = 1; // Represents the bird's minimum (and starting) falling velocity
	private static final int MAX_FALL_VELOCITY = 10; // Represents the bird's maximum vertical falling velocity
	private static final int GRAVITY = 1; // Represents the gravity which will accumulate on the falling velocity
	
	private static final int X_SPAWN = 500; // Represents the default x-coordinate position for the bird's spawn
	private static final int Y_SPAWN = 325; // Represents the default y-coordinate position for the bird's spawn
	
	private double yVelocity; // Represents the vertical velocity of the bird
	
	private boolean rising; // Represents whether the bird is rising (true) or falling (false)
	
	private Circle bird; // Represents the bird
	
	
	/**
	 * The Bird constructor initializes the bird's superclass as well as it's own unique properties
	 * 
	 * @param xCoordLeft
	 * @param xCoordRight
	 * @param yCoordTop
	 * @param yCoordBottom
	 * @param bird
	 */
	public Bird(double xCoordLeft, double xCoordRight, double yCoordTop, double yCoordBottom, Circle bird) {
		super(xCoordLeft, xCoordRight, yCoordTop, yCoordBottom);
		this.bird = bird;
	}
	
	
	/**
	 * The move method moves the bird upwards (like a flapping movement)
	 */
	public void rise() {
		rising = true;
		yVelocity = RISE_VELOCITY; // Change bird's velocity
		bird.setLayoutY(bird.getLayoutY() - yVelocity); // Move bird upward
	}
	
	
	/**
	 * The fall method moves the bird downwards and increments the falling velocity via gravity
	 */
	public void fall() {
		if (rising) { // If the bird is just beginning to fall, then rising is still true at this point
			yVelocity = MIN_FALL_VELOCITY; // Change bird's velocity
			rising = false; // Set rising to true so that this block won't repeat before the rise method is called again
		} else {
			if (yVelocity < MAX_FALL_VELOCITY) { // While falling, if yVelocity is less than the maximum falling velocity...
				yVelocity += GRAVITY; // Increase bird's velocity
			}
		}
		bird.setLayoutY(bird.getLayoutY() + yVelocity); // Move bird downward
	}
	
	
	/**
	 * The spawn method sets the bird's position to it's default starting coordinates
	 */
	public void spawn() {
		bird.setLayoutX(X_SPAWN);
		bird.setLayoutY(Y_SPAWN);
	}


	/**
	 * The getMaxRise method accesses the MAX_RISE value to track if the bird has reached it's maximum rise amount
	 * 
	 * @return MAX_RISE
	 */
	public static int getMaxRise() {
		return MAX_RISE;
	}

}

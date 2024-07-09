package model;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


/**
 * The Bird class contains the unique properties and methods related to the bird
 * 
 * @author Andrew Polyak
 * @version July 5, 2024
 */
public class Bird {

	private static final int RISE_VELOCITY = 5; // Represents the vertical velocity caused by the bird's flap TODO adjust this value
	private static final int MAX_RISE = 50; // Represents the vertical distance the bird's flap will cover TODO adjust this value
	
	private static final int MIN_FALL_VELOCITY = 1; // Represents the bird's minimum (and starting) falling velocity TODO adjust this value
	private static final int MAX_FALL_VELOCITY = 10; // Represents the bird's maximum vertical falling velocity TODO adjust this value
	private static final int GRAVITY = 1; // Represents the gravity which will accumulate on the falling velocity TODO adjust this value
	
	private static final int X_SPAWN = 500; // Represents the default x-coordinate position for the bird's spawn
	private static final int Y_SPAWN = 325; // Represents the default y-coordinate position for the bird's spawn
	
	private static final int X_HIT_BOX_SYNC = 48; // Represents the x-coordinate offset between the bird sprite and bird hit box TODO adjust this value
	private static final int Y_HIT_BOX_SYNC = 33; // Represents the y-coordinate offset between the bird sprite and bird hit box TODO adjust this value
	
	private double yVelocity; // Represents the vertical velocity of the bird
	
	private boolean rising; // Represents whether the bird is rising (true) or falling (false)

	private ImageView bird; // Represents the bird sprite
	private Circle birdHitBox; // Represents the bird hit box
	
	
	/**
	 * The Bird constructor initializes the bird's unique properties
	 * 
	 * @param xCoordLeft
	 * @param xCoordRight
	 * @param yCoordTop
	 * @param yCoordBottom
	 * @param bird
	 * @param birdHitBox
	 */
	public Bird(ImageView bird, Circle birdHitBox) {
		this.bird = bird;
		this.birdHitBox = birdHitBox;
	}
	
	
	/**
	 * The move method moves the bird upwards (like a flapping movement)
	 */
	public void rise() {
		rising = true;
		yVelocity = RISE_VELOCITY; // Change bird's velocity
		birdHitBox.setLayoutY(birdHitBox.getLayoutY() - yVelocity); // Move bird upward
		syncHitBox();
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
		birdHitBox.setLayoutY(birdHitBox.getLayoutY() + yVelocity); // Move bird downward
		syncHitBox();
	}
	
	
	/**
	 * The spawn method sets the bird's position to it's default starting coordinates
	 */
	public void spawn() {
		bird.setLayoutX(X_SPAWN);
		bird.setLayoutY(Y_SPAWN);
		syncHitBox();
	}
	
	
	/**
	 * The syncHitBox method sets aligns the bird's hit box coordinates with it's sprite coordinates
	 */
	private void syncHitBox() {
		bird.setLayoutY(birdHitBox.getLayoutY() + Y_HIT_BOX_SYNC); // FIXME coordinates are way off for the syncing
		bird.setLayoutX(birdHitBox.getLayoutX() + X_HIT_BOX_SYNC); // FIXME coordinates are way off for the syncing
	}
	
	
	/**
	 * The tubeHitBox method returns true if the bird hit the tube, false otherwise
	 * 
	 * @param tubeHitBox
	 * @return true if the bird hit the tube, false otherwise
	 */
	public boolean hitTube(Rectangle tubeHitBox) {
		return birdHitBox.getBoundsInParent().intersects(tubeHitBox.getBoundsInParent());
	}


	/**
	 * The getMaxRise method accesses the MAX_RISE value to track if the bird has reached it's maximum rise amount
	 * 
	 * @return MAX_RISE
	 */
	public static int getMaxRise() {
		return MAX_RISE;
	}
	
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public double getxCoord() {
		return birdHitBox.getLayoutX();
	}


	/**
	 * TODO
	 * 
	 * @param xCoord
	 */
	public void setxCoord(double xCoord) {
		birdHitBox.setLayoutX(birdHitBox.getLayoutX());
		syncHitBox();
	}


	/**
	 * TODO
	 * 
	 * @return
	 */
	public double getyCoord() {
		return birdHitBox.getLayoutY();
	}


	/**
	 * TODO
	 * 
	 * @param yCoord
	 */
	public void setyCoord(double yCoord) {
		birdHitBox.setLayoutY(birdHitBox.getLayoutY());
		syncHitBox();
	}

}

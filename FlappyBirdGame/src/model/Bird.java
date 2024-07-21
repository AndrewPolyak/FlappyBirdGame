package model;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


/**
 * The Bird class contains the unique properties and methods related to the bird
 * 
 * @author Andrew Polyak
 * @version July 20, 2024
 */
public class Bird {

	private static final double RISE_VELOCITY = 12; // Represents the vertical velocity caused by the bird's flap
	private static final int MAX_RISE = 120; // Represents the vertical distance the bird's flap will cover
	
	private static final double MIN_FALL_VELOCITY = 5; // Represents the bird's minimum (and starting) falling velocity
	private static final double MAX_FALL_VELOCITY = 12; // Represents the bird's maximum vertical falling velocity
	private static final double GRAVITY = 0.3; // Represents the gravity which will accumulate on the falling velocity
	
	private static final int X_SPAWN = 497; // Represents the default x-coordinate position for the bird's spawn
	private static final int Y_SPAWN = 324; // Represents the default y-coordinate position for the bird's spawn
	
	private static final int X_HIT_BOX_SYNC = 11; // Represents the x-coordinate offset between the bird sprite and bird hit box
	private static final int Y_HIT_BOX_SYNC = 0; // Represents the y-coordinate offset between the bird sprite and bird hit box
	
	private static final double STANDARD_ROTATION = 0; // Represents the standard rotation of the bird
	private static final double MAX_UP_ROTATION = -40; // Represents the maximum upward rotation of the bird
	private static final double MAX_DOWN_ROTATION = 40; // Represents the minimum downward rotation of the bird
	private static final double UPWARD_ROTATION_INCREMENT = 5; // Represents the amount-per-frame the bird will rotate upward
	private static final double DOWNWARD_ROTATION_INCREMENT = 2; /// Represents the amount-per-frame the bird will rotate downward
	
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
	public void rise(double rotation) {
		rising = true;
		yVelocity = RISE_VELOCITY; // Change bird's velocity
		birdHitBox.setLayoutY(birdHitBox.getLayoutY() - yVelocity); // Move bird upward
		syncHitBox();
		setRotation(rotation);
	}
	
	
	/**
	 * The fall method moves the bird downwards and increments the falling velocity via gravity
	 */
	public void fall(double rotation) {
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
		setRotation(rotation);
	}
	
	
	/**
	 * The spawn method sets the bird's position to it's default starting coordinates
	 */
	public void spawn() {
		birdHitBox.setLayoutX(X_SPAWN);
		birdHitBox.setLayoutY(Y_SPAWN);
		syncHitBox();
		bird.setRotate(STANDARD_ROTATION);
	}
	
	
	/**
	 * The syncHitBox method sets aligns the bird's hit box coordinates with it's sprite coordinates
	 */
	private void syncHitBox() {
		bird.setLayoutY(birdHitBox.getLayoutY() - (Y_HIT_BOX_SYNC + birdHitBox.getRadius()));
		bird.setLayoutX(birdHitBox.getLayoutX() - (X_HIT_BOX_SYNC + birdHitBox.getRadius()));
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
	 * The getxCoord method returns the bird's current x-coordinate
	 * 
	 * @return bird's current x-coordinate
	 */
	public double getxCoord() {
		return birdHitBox.getLayoutX();
	}


	/**
	 * The setxCoord method sets the bird's x-coordinate to the passed in value of xCoord
	 * 
	 * @param xCoord
	 */
	public void setxCoord(double xCoord) {
		birdHitBox.setLayoutX(birdHitBox.getLayoutX());
		syncHitBox();
	}


	/**
	 * The getyCoord method returns the bird's current y-coordinate
	 * 
	 * @return bird's current y-coordinate
	 */
	public double getyCoord() {
		return birdHitBox.getLayoutY();
	}


	/**
	 * The setyCoord method sets the bird's y-coordinate to the passed in value of yCoord
	 * 
	 * @param yCoord
	 */
	public void setyCoord(double yCoord) {
		birdHitBox.setLayoutY(birdHitBox.getLayoutY());
		syncHitBox();
	}
	
	
	/**
	 * The getRotation method returns the bird's current rotation amount
	 * 
	 * @return bird's rotation amount
	 */
	public double getRotation() {
		return bird.getRotate();
	}
	
	
	/**
	 * The setRotation method sets the bird's rotation amount to the passed in value of rotation
	 * 
	 * @param rotation
	 */
	public void setRotation(double rotation) {
		bird.setRotate(rotation);
	}


	/**
	 * The getMaxUpRotation method returns the MAX_UP_ROTATION value
	 * 
	 * @return MAX_UP_ROTATION
	 */
	public static double getMaxUpRotation() {
		return MAX_UP_ROTATION;
	}


	/**
	 * The getMaxDownRotation method returns the MAX_DOWN_ROTATION value
	 * 
	 * @return MAX_DOWN_ROTATION
	 */
	public static double getMaxDownRotation() {
		return MAX_DOWN_ROTATION;
	}


	/**
	 * The getUpwardRotationIncrement method returns the UPWARD_ROTATION_INCREMENT value
	 * 
	 * @return UPWARD_ROTATION_INCREMENT
	 */
	public static double getUpwardRotationIncrement() {
		return UPWARD_ROTATION_INCREMENT;
	}


	/**
	 * The getDownwardRotationIncrememnt method returns the DOWNWARD_ROTATION_INCREMENT value
	 * 
	 * @return DOWNWARD_ROTATION_INCREMENT
	 */
	public static double getDownwardRotationIncrement() {
		return DOWNWARD_ROTATION_INCREMENT;
	}
	
}

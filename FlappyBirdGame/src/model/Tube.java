package model;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * The Tube class contains the unique properties and methods related to tubes
 * 
 * @author Andrew Polyak
 * @version July 19, 2024
 */
public class Tube {
	
	private static final int X_SPAWN = 1000; // Represents the x-coordinate the tube will spawn on
	
	private static final double X_VELOCITY = 2; // Represents the horizontal velocity of the tube from right-to-left
	
	private double height; // Represents the tube's height
	
	private ImageView tube; // Represents the tube sprite
	private Rectangle tubeHitBox; // Represents the tube hit box
	
	private static final int SHORT_PIPE_TOP_Y_SPAWN = -210; // TODO
	private static final int TALL_PIPE_TOP_Y_SPAWN = 0; // TODO
	private static final int REGULAR_PIPE_TOP_Y_SPAWN = -110; // TODO
	
	private static final int SHORT_PIPE_BOTTOM_Y_SPAWN = 555; // TODO
	private static final int TALL_PIPE_BOTTOM_Y_SPAWN = 355; // TODO
	private static final int REGULAR_PIPE_BOTTOM_Y_SPAWN = 455; // TODO
	

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
	public Tube(ImageView tube, Rectangle tubeHitBox) {
		this.tube = tube;
		this.tubeHitBox = tubeHitBox;
	}

	
	/**
	 * The move method moves the tube leftwards
	 */
	public void move() {
		tubeHitBox.setLayoutX(tubeHitBox.getLayoutX() - X_VELOCITY); // Move tube leftwards
		syncHitBox();
	}
	
	
	/**
	 * The spawn method spawns the tube on the right side of the screen and either on the top or bottom of the screen depending on topTube's boolean value
	 */
	public void spawn() {
		tubeHitBox.setLayoutX(X_SPAWN); // Spawn the tube on the right of the screen
		syncHitBox();
	}
	
	
	/**
	 * TODO
	 * 
	 * @param topTube
	 */
	public void spawnRegularTube(boolean topTube) {
		if (topTube) {
			tubeHitBox.setLayoutY(REGULAR_PIPE_TOP_Y_SPAWN);
		} else {
			tubeHitBox.setLayoutY(REGULAR_PIPE_BOTTOM_Y_SPAWN);
		}
		syncHitBox();
	}
	
	
	/**
	 * TODO
	 * 
	 * @param topTube
	 */
	public void spawnTallTube(boolean topTube) {
		if (topTube) {
			tubeHitBox.setLayoutY(TALL_PIPE_TOP_Y_SPAWN);
		} else {
			tubeHitBox.setLayoutY(TALL_PIPE_BOTTOM_Y_SPAWN);
		}
		syncHitBox();
	}
	
	
	/**
	 * TODO
	 * 
	 * @param topTube
	 */
	public void spawnShortTube(boolean topTube) {
		if (topTube) {
			tubeHitBox.setLayoutY(SHORT_PIPE_TOP_Y_SPAWN);
		} else {
			tubeHitBox.setLayoutY(SHORT_PIPE_BOTTOM_Y_SPAWN);
		}
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
	 * TODO
	 * 
	 * @return
	 */
	public double getHeight() {
		return height;
	}


	/**
	 * TODO
	 * 
	 * @return
	 */
	public double getxCoord() {
		return tubeHitBox.getLayoutX();
	}
	
	
	/**
	 * TODO
	 * 
	 * @param yCoord
	 */
	public void setyCoord(double yCoord) {
		tubeHitBox.setLayoutY(yCoord);
	}
	
	
	/**
	 * TODO
	 */
	public void setVisible() {
		tube.setVisible(true);
	}
	
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	public Rectangle getHitBox() {
		return tubeHitBox;
	}

}

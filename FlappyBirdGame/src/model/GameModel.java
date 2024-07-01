package model;


/**
 * The GameModel abstract class contains all properties, as well as their accessors and mutators, common to game model objects
 * 
 * @author Andrew Polyak
 * @version June 30, 2024
 */
public abstract class GameModel {

	private double xCoordLeft; // The model's left-most x-coordinate
	private double xCoordRight; // The model's right-most x-coordinate
	private double yCoordTop; // The model's upper y-coordinate
	private double yCoordBottom; // The model's lower y-coordinate
	

	/**
	 * The GameModel constructor initializes all properties common to game models
	 * 
	 * @param xCoordLeft
	 * @param xCoordRight
	 * @param yCoordTop
	 * @param yCoordBottom
	 * @param width
	 * @param height
	 */
	public GameModel(double xCoordLeft, double xCoordRight, double yCoordTop, double yCoordBottom) {
		this.xCoordLeft = xCoordLeft;
		this.xCoordRight = xCoordRight;
		this.yCoordTop = yCoordTop;
		this.yCoordBottom = yCoordBottom;
	}

	
	/**
	 * The getxCoordLeft method accesses the left-most x-coordinate for the model
	 * 
	 * @return xCoordLeft
	 */
	public double getxCoordLeft() {
		return xCoordLeft;
	}


	/**
	 * The setxCoordLeft method assigns the left-most x-coordinate for the model
	 * 
	 * @param xCoordLeft
	 */
	public void setxCoordLeft(double xCoordLeft) {
		this.xCoordLeft = xCoordLeft;
	}


	/**
	 * The getxCoordRight method accesses the right-most x-coordinate for the model
	 * 
	 * @return getxCoordRight
	 */
	public double getxCoordRight() {
		return xCoordRight;
	}


	/**
	 * The setxCoordRight method assigns the right-most x-coordinate for the model
	 * 
	 * @param xCoordRight
	 */
	public void setxCoordRight(double xCoordRight) {
		this.xCoordRight = xCoordRight;
	}


	/**
	 * The getyCoordTop method accesses the upper y-coordinate for the model
	 * 
	 * @return yCoordTop
	 */
	public double getyCoordTop() {
		return yCoordTop;
	}


	/**
	 * The setyCoordTop method assigns the upper y-coordinate for the model
	 * 
	 * @param yCoordTop
	 */
	public void setyCoordTop(double yCoordTop) {
		this.yCoordTop = yCoordTop;
	}


	/**
	 * The getyCoordBottom method accesses the lower y-coordinate for the model
	 * 
	 * @return yCoordBottom
	 */
	public double getyCoordBottom() {
		return yCoordBottom;
	}


	/**
	 * The setyCoordBottom method assigns the lower y-coordinate for the model
	 * 
	 * @param yCoordBottom
	 */
	public void setyCoordBottom(double yCoordBottom) {
		this.yCoordBottom = yCoordBottom;
	}
	
}

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Bird;
import model.Tube;


/**
 * The AppController class contains the logic to coordinate the menu and gameplay of the application
 * 
 * @author Andrew Polyak
 * @version July 21, 2024
 */
public class AppController implements Initializable {
	
	@FXML
    private ImageView birdFive; // Represents the skin of bird five

    @FXML
    private Button birdFiveToggleBtn; // Represents the button to display bird five

    @FXML
    private ImageView birdFour; // Represents the skin of bird four

    @FXML
    private Button birdFourToggleBtn; // Represents the button to display bird four

    @FXML
    private Circle birdHitBox; // Represents the hit box of the bird

    @FXML
    private ImageView birdOne; // Represents the skin of bird one

    @FXML
    private Button birdOneToggleBtn; // Represents the button to display bird one

    @FXML
    private ImageView birdSix; // Represents the skin of bird six

    @FXML
    private Button birdSixToggleBtn; // Represents the button to display bird six

    @FXML
    private ImageView birdThree; // Represents the skin of bird three

    @FXML
    private Button birdThreeToggleBtn; // Represents the button to display bird three

    @FXML
    private ImageView birdTwo; // Represents the skin of bird two

    @FXML
    private Button birdTwoToggleBtn; // Represents the button to display bird two

    @FXML
    private ImageView bottomPipeOne; // Represents the skin of bottom pipe one

    @FXML
    private Rectangle bottomPipeOneHitBox; // Represents the hit box of pipe one

    @FXML
    private ImageView bottomPipeThree; // Represents the skin of bottom pipe three

    @FXML
    private Rectangle bottomPipeThreeHitBox; // Represents the hit box of bottom pipe three

    @FXML
    private ImageView bottomPipeTwo; // Represents the skin of bottom pipe two

    @FXML
    private Rectangle bottomPipeTwoHitBox; // Represents the hit box of bottom pipe two

    @FXML
    private Text gameEndHighScoreCounter; // Represents the high score counter on the post-game screen

    @FXML
    private Text gameEndLastScoreCounter; // Represents the last-game score counter on the post-game screen

    @FXML
    private AnchorPane gameEndView; // Represents the post-game screen

    @FXML
    private AnchorPane gameModelScreen; // Represents the AnchorPane containing all the game models (i.e., birds, tubes)

    @FXML
    private AnchorPane gameScreen; // Represents the gameplay screen

    @FXML
    private Text mainMenuHighScoreCounter; // Represents the high score count seen on the main menu
    
    @FXML
    private ImageView mapOne; // Represents the first background option

    @FXML
    private Button mapOneToggleBtn; // Represents the button to toggle map one

    @FXML
    private AnchorPane mapScreen; // Represents the AnchorPane containing all the backgrounds / maps

    @FXML
    private ImageView mapThree; // Represents the third background option

    @FXML
    private Button mapThreeToggleBtn; // Represents the button to toggle map three

    @FXML
    private ImageView mapTwo; // Represents the second background option

    @FXML
    private Button mapTwoToggleBtn; // Represents the button to toggle map two

    @FXML
    private AnchorPane menuScreen; // Represents the main menu screen

    @FXML
    private Button resetStatsBtn; // Represents the reset stats button on the main menu

    @FXML
    private Text scoreCounter; // Represents the score counter seen during gameplay

    @FXML
    private ImageView topPipeOne; // Represents the skin of top pipe one

    @FXML
    private Rectangle topPipeOneHitBox; // Represents the hit box of top pipe one

    @FXML
    private ImageView topPipeThree; // Represents the skin of top pipe three

    @FXML
    private Rectangle topPipeThreeHitBox; // Represents the hit box of top pipe three

    @FXML
    private ImageView topPipeTwo; // Represents the skin of top pipe two

    @FXML
    private Rectangle topPipeTwoHitBox; // Represents the hit box of top pipe two
    
    private FlappyBirdMenuController menu; // Represents a FlappyBirdMenuController object (to control menu operations)
    
    private FlappyBirdGameController game; // Represents a FlappyBirdGameController object (to control gameplay operations)
    
    private FlappyBirdPostGameMenuController postGameMenu; // Represents a FlappyBirdPostGameMenuController object (to control post-game menu operations)

    private Bird birdModel; // Represents a Bird object (to place into the FlappyBirdGameController constructor)
    
    private Tube topTubeOne; // Represents top tube one as a Tube object
	private Tube topTubeTwo; // Represents top tube two as a Tube object
	private Tube topTubeThree; // Represents top tube three as a Tube object
	private Tube bottomTubeOne; // Represents bottom tube one as a Tube object
	private Tube bottomTubeTwo; // Represents bottom tube two as a Tube object
	private Tube bottomTubeThree; // Represents bottom tube three as a Tube object
	
    
    /**
     * The initialize method instantiates a FlappyBirdMenuController object and calls it's detectInput method
     * to allow the program to listen to the user's menu inputs <br>
     * The method also calls begins the game once the user indicates they'd like to do so with the relevant input
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menu = new FlappyBirdMenuController(
				mapOneToggleBtn, 
				mapTwoToggleBtn, 
				mapThreeToggleBtn, 
                birdOneToggleBtn, 
                birdTwoToggleBtn, 
                birdThreeToggleBtn, 
                birdFourToggleBtn, 
                birdFiveToggleBtn, 
                birdSixToggleBtn, 
                resetStatsBtn, 
                mapOne, 
                mapTwo, 
                mapThree, 
                birdOne, 
                birdTwo,
                birdThree,
                birdFour,
                birdFive,
                birdSix,
                menuScreen,
                mainMenuHighScoreCounter );
        
		menu.updateStats(); // Display the latest high score on the main menu
		menu.detectInput(); // Listen and handle user menu interactions
		menu.setOnGameStartSuccess(() -> playGame()); // Begin game once the user presses the relevant input
		
		postGameMenu = new FlappyBirdPostGameMenuController(gameEndView, menuScreen);
		
		postGameMenu.detectInput(); // Listen and handle user post-game menu interactions
	}
	
	
	/**
	 * The playGame method hides the menu, shows the game-specific UI, instantiates the FlappyBirdGameController class, 
	 * and calls the class's play method to begin the game
	 */
	private void playGame() {
		menuScreen.setVisible(false);
		scoreCounter.setVisible(true);
		
		game = new FlappyBirdGameController(
				gameScreen, 
				gameEndView,
				gameEndHighScoreCounter,
				gameEndLastScoreCounter,
				mainMenuHighScoreCounter,
				initializeBirdModel(), scoreCounter, 
				initializeTubeModel(topTubeOne, topPipeOne, topPipeOneHitBox),
				initializeTubeModel(topTubeTwo, topPipeTwo, topPipeTwoHitBox), 
				initializeTubeModel(topTubeThree, topPipeThree, topPipeThreeHitBox), 
				initializeTubeModel(bottomTubeOne, bottomPipeOne, bottomPipeOneHitBox), 
				initializeTubeModel(bottomTubeTwo, bottomPipeTwo, bottomPipeTwoHitBox), 
				initializeTubeModel(bottomTubeThree, bottomPipeThree, bottomPipeThreeHitBox));
		
		game.play(); // Start game
	}
	
	
	/**
	 * The initializeBirdModel method instantiates a Bird object using the selected bird skin
	 * 
	 * @return birdModel, a Bird object
	 */
	private Bird initializeBirdModel() {
		birdModel = new Bird(menu.getBirdSkin(), birdHitBox);
		return birdModel;
	}
	
	
	/**
	 * The initializeTubeModel method instantiates a Tube object
	 * 
	 * @param tube
	 * @param model
	 * @param hitBox
	 * @return tube
	 */
	private Tube initializeTubeModel(Tube tube, ImageView model, Rectangle hitBox) {
		tube = new Tube(model, hitBox);
		return tube;
	}
	
}

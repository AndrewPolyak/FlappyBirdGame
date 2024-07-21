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
 * @version July 20, 2024
 */
public class AppController implements Initializable {
	
	@FXML
    private ImageView birdFive;

    @FXML
    private Button birdFiveToggleBtn;

    @FXML
    private ImageView birdFour;

    @FXML
    private Button birdFourToggleBtn;

    @FXML
    private Circle birdHitBox;

    @FXML
    private ImageView birdOne;

    @FXML
    private Button birdOneToggleBtn;

    @FXML
    private ImageView birdSix;

    @FXML
    private Button birdSixToggleBtn;

    @FXML
    private ImageView birdThree;

    @FXML
    private Button birdThreeToggleBtn;

    @FXML
    private ImageView birdTwo;

    @FXML
    private Button birdTwoToggleBtn;

    @FXML
    private ImageView bottomPipeOne;

    @FXML
    private Rectangle bottomPipeOneHitBox;

    @FXML
    private ImageView bottomPipeThree;

    @FXML
    private Rectangle bottomPipeThreeHitBox;

    @FXML
    private ImageView bottomPipeTwo;

    @FXML
    private Rectangle bottomPipeTwoHitBox;

    @FXML
    private Text gameEndHighScoreCounter;

    @FXML
    private Text gameEndLastScoreCounter;

    @FXML
    private AnchorPane gameEndView;

    @FXML
    private AnchorPane gameModelScreen;

    @FXML
    private AnchorPane gameScreen;

    @FXML
    private ImageView mapOne;

    @FXML
    private Button mapOneToggleBtn;

    @FXML
    private AnchorPane mapScreen;

    @FXML
    private ImageView mapThree;

    @FXML
    private Button mapThreeToggleBtn;

    @FXML
    private ImageView mapTwo;

    @FXML
    private Button mapTwoToggleBtn;

    @FXML
    private AnchorPane menuScreen;

    @FXML
    private Button resetStatsBtn;

    @FXML
    private Text scoreCounter;

    @FXML
    private ImageView topPipeOne;

    @FXML
    private Rectangle topPipeOneHitBox;

    @FXML
    private ImageView topPipeThree;

    @FXML
    private Rectangle topPipeThreeHitBox;

    @FXML
    private ImageView topPipeTwo;

    @FXML
    private Rectangle topPipeTwoHitBox;
    
    private FlappyBirdMenuController menu; // Represents a FlappyBirdMenuController object (to control menu operations)
    
    private FlappyBirdGameController game; // Represents a FlappyBirdGameController object (to control gameplay operations)
    
    private FlappyBirdPostGameMenuController postGameMenu; // TODO

    private Bird birdModel; // Represents a Bird object (to place into the FlappyBirdGameController constructor)
    
    private Tube topTubeOne; // TODO doc
	private Tube topTubeTwo; // TODO doc
	private Tube topTubeThree; // TODO doc
	private Tube bottomTubeOne; // TODO doc
	private Tube bottomTubeTwo; // TODO doc
	private Tube bottomTubeThree; // TODO doc
    
	
	/*
	 * TODO LIST
	 * 
	 * 1: Add data loading & saving to update and display your high score
	 * 2: Add end-of-game screen that displays the score achieved & your high score
	 * 3: Add a button that allows you to reset your high score to 0
	 * 4: Add sound effects & music
	 * 5: Complete documentation; Generate JavaDoc
	 */
	
    
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
                menuScreen);
        
		menu.detectInput(); // Listen and handle user menu interactions
		menu.setOnGameStartSuccess(() -> playGame()); // Begin game once the user presses the relevant input
		
		postGameMenu = new FlappyBirdPostGameMenuController(gameEndView, menuScreen);
		
		postGameMenu.detectInput();
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
	 * TODO
	 * 
	 * @param tube
	 * @param model
	 * @param hitBox
	 * @return
	 */
	private Tube initializeTubeModel(Tube tube, ImageView model, Rectangle hitBox) {
		tube = new Tube(model, hitBox);
		return tube;
	}
	
}

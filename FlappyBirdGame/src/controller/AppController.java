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
 * @version July 14, 2024
 */
public class AppController implements Initializable {
	
	@FXML
    private ImageView bird;

    @FXML
    private Circle birdHitBox;

    @FXML
    private Button birdOneToggleBtn;

    @FXML
    private Button birdThreeToggleBtn;

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

    private Bird birdModel; // Represents a Bird object (to place into the FlappyBirdGameController constructor)
    
    private Tube topTubeOne;
	private Tube topTubeTwo;
	private Tube topTubeThree;
	private Tube bottomTubeOne;
	private Tube bottomTubeTwo;
	private Tube bottomTubeThree;
    
    
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
                mapOne, 
                mapTwo, 
                mapThree, 
                bird, 
                bird, // TODO replace the bird repeats with different images
                bird, // TODO replace the bird repeats with different images
                menuScreen);
        
		menu.detectInput(); // Listen and handle user menu interactions
		
		menu.setOnGameStartSuccess(() -> playGame()); // Begin game once the user presses the relevant input
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
				menuScreen, 
				mapOneToggleBtn, 
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
	
	
	private Tube initializeTubeModel(Tube tube, ImageView model, Rectangle hitBox) {
		tube = new Tube(model, hitBox);
		return tube;
	}
	
}

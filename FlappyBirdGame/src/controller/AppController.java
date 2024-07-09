package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
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
 * TODO
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
    
    private FlappyBirdMenuController menu; // TODO
    
    private FlappyBirdGameController game;


    
    Bird b;
    
    /**
     * TODO
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		b = new Bird(bird, birdHitBox); // TEMPORARY
		
		menu = new FlappyBirdMenuController(mapOneToggleBtn, mapTwoToggleBtn, mapThreeToggleBtn, 
                birdOneToggleBtn, birdTwoToggleBtn, birdThreeToggleBtn, mapOne, mapTwo, mapThree, bird, bird, bird);
        
		//menu.detectInput(); // TODO
		
		
		
		game = new FlappyBirdGameController(birdHitBox, topPipeOneHitBox, topPipeTwoHitBox, topPipeThreeHitBox, 
				bottomPipeOneHitBox, bottomPipeTwoHitBox, bottomPipeThreeHitBox, gameScreen, b);
		
		game.play();
	}
	
}

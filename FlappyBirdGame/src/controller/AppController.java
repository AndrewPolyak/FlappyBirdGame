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


/**
 * TODO
 */
public class AppController implements Initializable {
	
	@FXML
    private ImageView bird; // TODO

    @FXML
    private Circle birdHitBox; // TODO

    @FXML
    private Button birdOneToggleBtn; // TODO

    @FXML
    private Button birdThreeToggleBtn; // TODO

    @FXML
    private Button birdTwoToggleBtn; // TODO

    @FXML
    private ImageView bottomPipeOne; // TODO

    @FXML
    private Rectangle bottomPipeOneHitBox; // TODO

    @FXML
    private ImageView bottomPipeThree; // TODO

    @FXML
    private Rectangle bottomPipeThreeHitBox; // TODO

    @FXML
    private ImageView bottomPipeTwo; // TODO

    @FXML
    private Rectangle bottomPipeTwoHitBox; // TODO

    @FXML
    private AnchorPane gameModelScreen; // TODO

    @FXML
    private AnchorPane gameScreen; // TODO

    @FXML
    private ImageView mapOne; // TODO

    @FXML
    private Button mapOneToggleBtn; // TODO

    @FXML
    private AnchorPane mapScreen; // TODO

    @FXML
    private ImageView mapThree; // TODO

    @FXML
    private Button mapThreeToggleBtn; // TODO

    @FXML
    private ImageView mapTwo; // TODO

    @FXML
    private Button mapTwoToggleBtn; // TODO

    @FXML
    private AnchorPane menuScreen; // TODO

    @FXML
    private Text scoreCounter; // TODO

    @FXML
    private ImageView topPipeOne; // TODO

    @FXML
    private Rectangle topPipeOneHitBox; // TODO

    @FXML
    private ImageView topPipeThree; // TODO

    @FXML
    private Rectangle topPipeThreeHitBox; // TODO

    @FXML
    private ImageView topPipeTwo; // TODO

    @FXML
    private Rectangle topPipeTwoHitBox; // TODO
    
    private FlappyBirdMenuController menu; // TODO


    /**
     * TODO
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menu = new FlappyBirdMenuController(mapOneToggleBtn, mapTwoToggleBtn, mapThreeToggleBtn, 
                birdOneToggleBtn, birdTwoToggleBtn, birdThreeToggleBtn, mapOne, mapTwo, mapThree, bird, bird, bird);
        
		menu.detectInput(); // TODO
		
	}
	
}

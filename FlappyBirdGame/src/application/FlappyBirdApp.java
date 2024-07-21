package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


/**
 * The FlappyBirdApp class initializes the application's user interface and logic
 * 
 * @author Andrew Polyak
 * @version July 21, 2024
 */
public class FlappyBirdApp extends Application {
	
	
	/**
	 * The start method initializes the GUI
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/view/AppView.fxml")); // Set root as AppView
			Scene scene = new Scene(root,1000,650); // Create new scene
			primaryStage.setTitle("Flappy Bird");
			primaryStage.setScene(scene); // Set stage with scene
			primaryStage.show(); // Show stage
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The main method launches the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args); // Launch application
	}
}
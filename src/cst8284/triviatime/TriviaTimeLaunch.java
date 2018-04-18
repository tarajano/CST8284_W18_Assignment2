package cst8284.triviatime;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TriviaTimeLaunch extends Application {
	/**  @Copyright Dave Houtman 2018.  For use in Winter 2018 - CST8284 classes only */
	
	private static BorderPane rootPane;
	
	@Override
	public void start(Stage primaryStage){	
	   // Display Splash Screen
	   setRootPane("Welcome to Trivial Time");
	   getRootPane().setTop(Controls.getMenuBar(primaryStage));
	   Scene scene =  new Scene(getRootPane(), 1024, 512);
	   primaryStage.setTitle("Trivia Time");
	   primaryStage.setScene(scene);
	   primaryStage.show();	
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}
	
	//  : build setRootPane(String), where the String is the logo in the Splashscreen
	//  You rootPane needs to be correctly set up so it can be loaded into the Scene
	//  using getRootPane(), as indicated in the code above
	public static void setRootPane(String logo) {
	  rootPane = new BorderPane();
	  String rootPaneCSSStyle = "-fx-font: 15px \"Trebuchet MS\"; -fx-type:sans-serif; -fx-stroke:black; -fx-stroke-width:1; -fx-background-color:#e5f5e0;";
	  rootPane.setStyle(rootPaneCSSStyle);
	  rootPane.setCenter(setLogo(logo));
	}
	
  // : build getRootPane(), which returns the rootPane stored in the variable declared above
	public static BorderPane getRootPane() {return rootPane;}
	
	// Styling the logo string
	private static Text setLogo(String text) {
    Text t = new Text();
    t.setFill(Color.BLUE);
    t.setText(text);
    t.setFont(Font.font(null, FontWeight.BOLD, 32));
    return t;
	}
		
}
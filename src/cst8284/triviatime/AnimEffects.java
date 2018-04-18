/**
 * 
 */
package cst8284.triviatime;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Manuel Alonso Tarajano (tarajano@gmail.com)
 * Apr 18, 2018  
 */
public class AnimEffects {
  
//  private static Stage stage;
  
  //ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(2000), new Text());
  
  
  public static Group insertFadeInLogo(String text) {
    Group g = new Group();  
    Text t = new Text();
    t.setFill(Color.BLUE);
    t.setText(text);
    t.setFont(Font.font(null, FontWeight.BOLD, 48));
    FadeTransition ft = new FadeTransition(Duration.millis(8000), t);
    ft.setFromValue(.05);
    ft.setToValue(5);
    ft.setAutoReverse(true);
    ft.play();
    g.getChildren().add(t);
    return g;
  }

  public static Group insertFadeInScaledLogo(String text) {
    Group g = new Group();
    
    // Text
    Text logoText = new Text();
    logoText.setFill(Color.BLUE);
    logoText.setText(text);
    logoText.setFont(Font.font(null, FontWeight.BOLD, 48));
    
    // FadeIn
    FadeTransition fadeTrans = new FadeTransition(Duration.millis(8000), logoText);
    fadeTrans.setFromValue(.05);
    fadeTrans.setToValue(5);
    fadeTrans.setAutoReverse(true);
    
    // ZoomIn
    ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(4000), logoText);
    scaleTrans.setToX(1.2f);
    scaleTrans.setToY(1.2f);
    scaleTrans.setCycleCount(1);
    //scaleTrans.setAutoReverse(true);

    // Parallel
    ParallelTransition parallelTransition = new ParallelTransition();
    parallelTransition.getChildren().addAll(fadeTrans, scaleTrans);
    //parallelTransition.setCycleCount(Timeline.INDEFINITE);
    parallelTransition.play();

    g.getChildren().add(logoText);
    return g;
  }
  
  
  
  
}

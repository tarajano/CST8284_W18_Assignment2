/**
 * 
 */
package cst8284.triviatime;

import javafx.animation.FadeTransition;
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

}

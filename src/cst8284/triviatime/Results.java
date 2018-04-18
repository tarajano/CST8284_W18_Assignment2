/**
 * 
 */
package cst8284.triviatime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Manuel Alonso Tarajano (tarajano@gmail.com)
 * Apr 1, 2018  
 */
public class Results {
  
  private static HBox resultsPane;
  private static HBox pieChartBox;
  private static VBox textSummaryBox;
  
  private static VBox getTextSummaryBox() {return textSummaryBox;}  
  private static HBox getPieChartBox() {return pieChartBox;}
  private static int getNumQuestions() {return Controls.getNumOfQuestions();}
  
  // Returns a Pane containing all the results (stats) from the quiz
  public static HBox getQuizResultSummaryBox() {
    setQuizResultSummary();
    return resultsPane;
  }
  
  // Sets a pane with 1) feedback for each question (Pass or Fail) plus 
  // a pie-chart representation of results.
  private static void setQuizResultSummary() {
    resultsPane = new HBox();
    setTextSummaryBox();
    setPieChartBox();
    resultsPane.getChildren().addAll(getTextSummaryBox(), getPieChartBox());
  }
  
  // Sets the feedback for each question (Pass or Fail)
  private static void setTextSummaryBox() {
    int idx = 0;
    int score = 0;
    boolean isCorrect;
    textSummaryBox = new VBox();
    textSummaryBox.getChildren().add(new Text("Results by question:"));
    
    for( QA qa : Controls.getQAArray() ) {
      isCorrect = qa.isCorrect();
      
      if (isCorrect)
        score++;
      
      textSummaryBox.getChildren().add( new Text((idx + 1) + ") " +
                                                (isCorrect  ? "Pass" : "Fail")));
      idx++;
    }
    
    textSummaryBox.getChildren().add(new Text(getFinalScoreString(score)));
  }
  
  // Builds a string summary for the final score
  private static String getFinalScoreString(int score) {
    double pct = (score*100/Controls.getNumOfQuestions());
    return String.format("Final score: %d/%d (%.2f %%)\n", score, Controls.getNumOfQuestions(), pct);
  }
  
  // Creates the pie-chart
  private static void setPieChartBox() {
    // http://www.java2s.com/Tutorials/Java/JavaFX/0810__JavaFX_Pie_Chart.htm
    pieChartBox = new HBox();
    PieChart pc = new PieChart();
    pc.setData(getChartData( getNumCorrectAnswers() , getNumQuestions() ));
    pieChartBox.getChildren().add(pc);
  }
  
  private static ObservableList<Data> getChartData(int okQ, int totQ) {
    // http://www.java2s.com/Tutorials/Java/JavaFX/0810__JavaFX_Pie_Chart.htm
    ObservableList<Data> answer = FXCollections.observableArrayList();  
    answer.addAll(new PieChart.Data("Incorrect", totQ - okQ),
                  new PieChart.Data("Correct", okQ));
    return answer;
  }
  
  // Counts correct answers for pie-chart
  private static int getNumCorrectAnswers() {
    int score = 0;
    for( QA qa : Controls.getQAArray() ) {
      if (qa.isCorrect())
        score++;
    }
    return score;
  }
  
}

package cst8284.triviatime;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class QAPane {
   private VBox qaPane;
   private ToggleGroup radioBtnGroup;
   private QA qa;
   private VBox vbox;
   
   // : Add a QAPane constructor that takes as an argument QA object. The object's
   // should be used to load the question and potential answer into the center pane
   // along with radio buttons next to each answer.
   //  Rather than use the default font,
   // your output will look considerably better is you use the .setStyle() method
   // to improve the look of the user interface.
   // See the Assignment 1 document for details on implementing the button handler
   // for this method
   public QAPane(QA qa) {
     this.setQA(qa);
     this.setQAPane(configQAPaneVBox(qa));
   }
   
   private void setQA(QA qa) {this.qa = qa;}
   private void setQAPane(VBox vb) {this.qaPane = vb;}
   public VBox getQAPane() {return qaPane;}

   // : write a method getAnswerPane() that takes as arguments an array of Strings
   // corresponding to the array of answers returned by getAnswers() and returns
   // a VBox loaded with radio buttons next to each string.  You'll need to load
   // each radio button into a toggle group.  Again, see the Assignment 1 document
   // for details
   private VBox insertAnswerPane(String[] answers) {
     radioBtnGroup = new ToggleGroup();
     vbox = new VBox();
     VBox btnvBox = new VBox();
     btnvBox.setSpacing(5);
     RadioButton rb;
     
     for (String answer : answers) {
       rb = new RadioButton(answer);
       rb.setToggleGroup(radioBtnGroup);
       rb.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent arg0) {
           ThatsMyAnswerBtnHndlr maBtn = new ThatsMyAnswerBtnHndlr();
           maBtn.disableAnwserBtn(false);
          }
       });
       btnvBox.getChildren().add(rb);
     }
     
     vbox.getChildren().add(btnvBox);
     return vbox;
   }
   
   private VBox insertQuestionPane(String question) {
     vbox = new VBox();
     vbox.getChildren().add(new Text(question));
     return vbox;
   }
   
   // write a method getRadioButtonSelected() that returns the number of the 
   // radio button selected.  One way to do this is to loop through each radio button
   // in the radio button array and test if isSelected() is set
   public int getRadioButtonSelected() {
     int i = 1;
     for(Toggle t : radioBtnGroup.getToggles()) {
       RadioButton rb = (RadioButton) t;
       if ( rb.isSelected() )
         return i;
       i++;
     }
     return 0;
   }
	
   private VBox configQAPaneVBox(QA qa) {
     VBox vbox = new VBox();
     vbox.setAlignment(Pos.CENTER);
     vbox.setSpacing(5);
     vbox.getChildren().addAll(this.insertQuestionPane(qa.getQuestion()), // Question
                               this.insertAnswerPane(qa.getAnswers()),    // Answers
                               new HBox(),                                // Placeholder for feedback
                               this.insertButtonsBox());                  // Buttons
     return vbox;
   }
   
   private HBox insertButtonsBox() {
     HBox btnsBox = new HBox();
     btnsBox.setSpacing(6);
     btnsBox.getChildren().addAll(this.insertThatsMyAnswerBtn(), // this is a Button 
                                  Controls.getNextQuestionPane() // this is an Button inside an HBox
                                  );
     return btnsBox;
   }

   private Button insertThatsMyAnswerBtn() {  
     Button btn = new Button("That's my answer");
     btn.setDisable(true);
     btn.setOnAction(new ThatsMyAnswerBtnHndlr());
     return btn;
   }
   
   private class ThatsMyAnswerBtnHndlr implements EventHandler<ActionEvent>{
     @Override
     public void handle(ActionEvent e){
       this.disableRadioBtns(true);
       this.disableAnwserBtn(true);
       if (Controls.getCurrentQuestionNumber() < Controls.getNumOfQuestions() - 1)
         this.disableNxtQBtn(false);
       else
         this.setShowResultsBtn();
       this.insertAnswerFeedBack();
     }
     
     private void setShowResultsBtn() {
       HBox hbOuter = (HBox) qaPane.getChildren().get(3);  // Fetching the showResults button,
       HBox hbInner = (HBox) hbOuter.getChildren().get(1); // which is contained inside 
       Button btn = (Button) hbInner.getChildren().get(0); // two HBoxes: HBox<-Hbox<-Button 
       btn.setText("Show results");
       btn.setDisable(false);
       btn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent arg0) {
           btn.setDisable(true);
           Stage pStage = Controls.getStage(); 
           BorderPane bp = (BorderPane) pStage.getScene().getRoot();
           HBox hb = Results.getQuizResultSummaryBox();
           bp.setCenter(hb);
           BorderPane.setMargin(hb, new Insets(10, 10, 10, 50) );
           // TODO Figure out how to activate "Print Result" MenuItem 
           // ONLY once results are shown.
           MenuItem mnuItm = Controls.getMnuItmPrintResults();
           mnuItm.setDisable(false);
          }
       });
     }
     
     private void disableRadioBtns(boolean b) {
       for(Toggle t : radioBtnGroup.getToggles()) {
         RadioButton rb = (RadioButton) t;
         rb.setDisable(b);
       }
    }
     
    private void disableAnwserBtn(boolean b) {
      HBox hb = (HBox) qaPane.getChildren().get(3);
      Button btn = (Button) hb.getChildren().get(0);
      btn.setDisable(b);
    }
    
    private void disableNxtQBtn(boolean b) {
      HBox hbOuter = (HBox) qaPane.getChildren().get(3);
      HBox hbInner = (HBox) hbOuter.getChildren().get(1);
      Button btn = (Button) hbInner.getChildren().get(0);
      btn.setDisable(b);
    }
    
    // Will fill the feedback's placeholder with the explanation,
    // and the feedback (Correct/Incorrect)  
    private void insertAnswerFeedBack() {
      HBox hb = (HBox) qaPane.getChildren().get(2);
      if (isAnswerRight()) {
        qa.setResult(true);
        hb.getChildren().add(new Text("That's correct! " + qa.getExplanation() ));
      }else{
        hb.getChildren().add(new Text("Sorry, that's incorrect. " + qa.getExplanation() ));
      }
    }
    
    private boolean isAnswerRight() {
      return (qa.getCorrectAnswerNumber() == getRadioButtonSelected()) ? true : false; 
    }
     
   }


    
}

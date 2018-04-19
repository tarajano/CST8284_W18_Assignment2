package cst8284.triviatime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controls {

	/**** Generic Menu/Menu Item Properties ****/
	private static MenuItem mnuItm;
	private static Menu mnu;
	
	private static Stage stage;
	private static int currentQuestion = 0;
	
	private static ArrayList<QA> qaArrayList = new ArrayList<>();
	private static BorderPane bp;
	private static QAPane qaPane;
	
//	private static String fileName = "ComputerTrivia_Java100.trivia";
//	private static String winFolderPath = "C:/TriviaTime/";
	private static String unixFolderPath = "/Dropbox/Dropbox/eclipse-workspace/CST8284_W18_Assignment1/src/cst8284/triviatime/triviaQAFiles/";
	private static String defaultFolderPath = unixFolderPath; // TODO modify for submission 
  
	/***************** MenuBar *****************/
 
	// : design a method getMenuBar() that returns a MenuBar,
	// suitable for loading into the top panel of a BorderPane
	public static MenuBar getMenuBar(Stage primaryStage) {
	  MenuBar mnuBar = new MenuBar();
	  setStage(primaryStage);
	  mnuBar.getMenus().addAll(getMnuFile(), getMnuSettings(), getMnuHelp());
	  return mnuBar;
	}
	
	/******************* Menu ******************/
	// : design a method getMnuFile() that returns a File Menu;
	// this can then loaded into the MenuBar
	private static Menu getMnuFile() {
	  mnu = new Menu("_File");
	  mnu.getItems().addAll(getMnuItmNewGame(), getMnuItmExit());
	  return mnu;
	}

	// : design a method getMnuSettings() that returns a Settings Menu;
	// This should be disabled to now; we'll use it later on
  private static Menu getMnuSettings() {
    mnu = new Menu("_Settings");
    mnu.getItems().addAll(getMnuItmRandomizeQuestions(),
                          getMnuItmIncreasingDifficulty(),
                          getMnuItmByTopic()
                          );
    mnu.setDisable(false);
    return mnu;
  }
  
  private static MenuItem getMnuItmIncreasingDifficulty() {
    /* From Marco Jakob, code.makery, */
    /* http://code.makery.ch/blog/javafx-dialogs-official/ */
    mnuItm = new MenuItem("_Increasing Difficulty");
    mnuItm.setOnAction((ActionEvent e) -> {
      Collections.sort(getQAArrayList(), new SortQByIncreasingDifficulty() );
    });
    return mnuItm; 
  }
  
  private static class SortQByIncreasingDifficulty implements Comparator<QA>{
      public int compare(QA o1, QA o2){
          return o1.getDifficulty() - o2.getDifficulty();
      }
  }
  
  private static MenuItem getMnuItmByTopic() {
    /* From Marco Jakob, code.makery, */
    /* http://code.makery.ch/blog/javafx-dialogs-official/ */
    mnuItm = new MenuItem("_By Topic");
    mnuItm.setOnAction((ActionEvent e) -> {
      Collections.sort(getQAArrayList(), new SortQByTopic() );
    });
    return mnuItm; 
  }
  
  private static class SortQByTopic implements Comparator<QA>{
    public int compare(QA o1, QA o2){
        return o1.getCategory().compareTo(o1.getCategory());
    }
  }
  
  private static MenuItem getMnuItmRandomizeQuestions() {
    /* From Marco Jakob, code.makery, */
    /* http://code.makery.ch/blog/javafx-dialogs-official/ */
    mnuItm = new MenuItem("_Randomize Questions");
    mnuItm.setOnAction((ActionEvent e) -> {
      Collections.shuffle(getQAArrayList());
    });
    return mnuItm; 
  }

  // : design a method getMnuHelp() that returns a Help Menu
  private static Menu getMnuHelp() {
    mnu = new Menu("_Help");
    mnu.getItems().addAll(getMnuItmAbout());
    return mnu;	  
	}
	
	/***************** MenuItems *****************/
	
	// : design a method called getMnuItmNewGame() that returns a New Game MenuItem
	// Clicking this menu item causes a new .triv to be opened
	// and the objects it contains to be loaded into an array.
	// Each array element contains question and answer information
	// that can be displayed in the center panel of the borderpane
  private static MenuItem getMnuItmNewGame() {
    /* From Marco Jakob, code.makery, */
    /* http://code.makery.ch/blog/javafx-dialogs-official/ */
    mnuItm = new MenuItem("_New Game");
    mnuItm.setOnAction((ActionEvent e) -> {
      Stage pStage = getStage(); 
      bp = (BorderPane) pStage.getScene().getRoot();
      setQAArrayList(defaultFolderPath);
      resetGame();
      setNxtPane();
    });
    return mnuItm; 
  }
  
  // Menu Item to print result's scene.
  public static MenuItem getMnuItmPrintResults() {
    // TODO Printing works, but I need to figure out
    // how to activate "Print results" ONLY after 
    // results are ready.
    mnuItm = new MenuItem("_Print results");
    mnuItm.setDisable(true);
    mnuItm.setOnAction((ActionEvent e) -> {
      printResults((HBox) bp.getChildren().get(0));
    });
    return mnuItm; 
  }
  
  public static void printResults(HBox node) {
    PrinterJob job = PrinterJob.createPrinterJob();
    if (job != null) {
       boolean success = job.showPrintDialog(null);
       if (success) {
           job.endJob();
       }
    }    
  }
  
	// : design a method that return the Exit MenuItem
  private static MenuItem getMnuItmExit() {
    /* From Marco Jakob, code.makery, */
    /* http://code.makery.ch/blog/javafx-dialogs-official/ */
    mnuItm = new MenuItem("_Exit");
    mnuItm.setOnAction(actionEvent -> Platform.exit());
    return mnuItm;
  }
  
	private static MenuItem getMnuItmAbout() {
		/* From Marco Jakob, code.makery, */
		/* http://code.makery.ch/blog/javafx-dialogs-official/ */
		mnuItm = new MenuItem("_About");
		mnuItm.setOnAction((ActionEvent e) -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("About Trivia Time");
			alert.setContentText("Author: Manuel Alonso\nAlgonquin College, 2018");
			alert.showAndWait();
		});
		return mnuItm;
	}
	
	// In case the user wants to play again,
	// resetGame() resets:
	// result=false for QA[], and
	// currentQuestion=0
	private static void resetGame() {
	  resetAnswersResults();
	  resetQuestionNumber();
	}
	
	private static void resetAnswersResults() {
    for( QA qa :  getQAArrayList() )
      qa.setResult(false);
	}
	
	private static void resetQuestionNumber() {currentQuestion = 0;}
	private static void setStage(Stage s) {stage=s;}
	public static Stage getStage() {return stage;}
	
  //: design a method getNextQuestionPane() that returns an HBox 
  // containing the 'Next Question' button, along with the code need to 
	// advance to the next question using the currentQuestion variable above
	public static HBox getNextQuestionPane() {
	  HBox btnNxtQHBox = new HBox();  
    Button btnNxtQ = new Button("Next Question");
    btnNxtQ.setDisable(true);
    btnNxtQ.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e){
        incrementQuestionNumber();
        setNxtPane();
      }
    });
    btnNxtQHBox.getChildren().add(btnNxtQ);
    return btnNxtQHBox;
	}
	
  private static VBox getNxtQPane() {
    qaPane = new QAPane(qaArrayList.get(getCurrentQuestionNumber()));
    return qaPane.getQAPane();
  }

  public static ArrayList<QA> getQAArrayList() {
    return qaArrayList ;
  }
  
  // To load QA objects from file
	private static ArrayList<QA> getQAArrayListFromTriviaFile(String folderPath) {
    FileUtils.setQAArrayList(folderPath);
    return FileUtils.getQAArray();
	}

  public static int getNumOfQuestions() {return qaArrayList.size();}
  private static void setQAArrayList(String folderPath) {qaArrayList = getQAArrayListFromTriviaFile(folderPath);}
  public static int getCurrentQuestionNumber() {return currentQuestion;}
  private static int incrementQuestionNumber() {return currentQuestion+=1;}

  private static void setNxtPane() {
    VBox nxtPane = getNxtQPane(); 
    bp.setCenter(nxtPane);
    BorderPane.setMargin(nxtPane, new Insets(10, 10, 10, 200) );
  }
}

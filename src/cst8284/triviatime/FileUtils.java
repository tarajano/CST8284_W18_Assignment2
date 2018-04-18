package cst8284.triviatime;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileUtils {
	
	private static ArrayList<QA> qaArrayList = new ArrayList<>();
		
	public static void setQAArrayList(String absPath) {
		if (fileExists(absPath)) {
			try {
				FileInputStream fis = new FileInputStream(absPath);
				ObjectInputStream ois = new ObjectInputStream(fis);
				while(qaArrayList.add((QA) ois.readObject()));
				ois.close();
			}
			catch (EOFException e) {}
			catch (IOException | ClassNotFoundException e) {} 
		}
		else
			qaArrayList = null;
	}
	
	public static void setQAArrayList(Stage stage, String pathToDefaultFolder) {
	  String absPath = getFileHandle(stage, pathToDefaultFolder).toString();
	  setQAArrayList(absPath);
	}

  private static File getFileHandle(Stage stage, String pathToDefaultFolder) {
    // Based on source code from: 
    // Redko, Alla.  Using JavaFX UI Control: 26 File Chooser.
    // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
    File f;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(pathToDefaultFolder));
    fileChooser.setTitle("Open Word File");
    fileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("WordList Files", "*.trivia"),
         new ExtensionFilter("All Files", "*.*"));
    
    f = fileChooser.showOpenDialog(stage);
    
    // The program will recurse until the user picks a trivia file. (Sorry for the annoyance of recursion)
    if (f == null)
      return getFileHandle(stage, pathToDefaultFolder);
    
    return f;   
  }
  
	public static ArrayList<QA> getQAArray() {return qaArrayList;}

	public static boolean fileExists(File f) {
		return (f != null && f.exists() && f.isFile() && f.canRead() && (f.length() > 2));
	}

	public static boolean fileExists(String s) {
		return (fileExists(new File(s)));
	}
	
	public static String getAbsPath(File f) {
		return f.getAbsolutePath();
	}

}

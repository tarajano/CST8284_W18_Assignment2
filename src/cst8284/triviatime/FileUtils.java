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
  
  private static File inputFileHandle; 
	
	private static ArrayList<QA> qaArrayList = new ArrayList<>();
		
	public static void setQAArrayList(String pathToFolder) {
	  setFileHandle(pathToFolder);
	  String absPathToFile = getFileHandle().toString();
		if (fileExists(absPathToFile)) {
			try {
				FileInputStream fis = new FileInputStream(absPathToFile);
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
	
//	public static void setQAArrayList(String pathToDefaultFolder) {
//	  setFileHandle(pathToDefaultFolder);
//	  setQAArrayList(getFileHandle().toString());
//	}

  private static void setFileHandle(String pathToDefaultFolder) {
    // Based on source code from: 
    // Redko, Alla.  Using JavaFX UI Control: 26 File Chooser.
    // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
    FileChooser fileChooser = new FileChooser();
    fileChooser.setInitialDirectory(new File(pathToDefaultFolder));
    fileChooser.setTitle("Open Word File");
    fileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("WordList Files", "*.trivia"),
         new ExtensionFilter("All Files", "*.*"));
    inputFileHandle = fileChooser.showOpenDialog(null);
    
    if (inputFileHandle == null) {
      System.out.println("Please select a trivia file");
      setFileHandle(pathToDefaultFolder);
    }
      
  }
  
  private static File getFileHandle() {   
    return inputFileHandle;
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

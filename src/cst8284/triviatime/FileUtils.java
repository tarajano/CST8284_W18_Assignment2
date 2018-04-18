package cst8284.triviatime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class FileUtils {
	
	private static ArrayList<QA> qaArrayList = new ArrayList<>();
		
	public static void setQAArray(String absPath) {
		if (fileExists(absPath)) {
			try {
				FileInputStream fis = new FileInputStream(absPath);
				ObjectInputStream ois = new ObjectInputStream(fis);
				while(qaArrayList.add((QA) ois.readObject()));
				ois.close();
			} catch (IOException | ClassNotFoundException e) {} 
		}
		else
			qaArrayList = null;
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

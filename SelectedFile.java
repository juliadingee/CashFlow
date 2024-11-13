package application;

import java.io.File;

public class SelectedFile {
	
	private static final SelectedFile instance = new SelectedFile();
	
	private File file;
	private String choice = "1 Month";
	
	private SelectedFile() {} // end constructor
	
	public static SelectedFile getInstance() {		
		return instance;
	} // end getInstance
	
	public File getFile() {
		return file;
	} // end getter
	
	public void setFile(File file) {
		this.file = file;
	} // end setter
	
	public String getChoice() {
		return choice;
	}
	
	public void setChoice(String choice) {
		this.choice = choice;
	}
	
} // end class

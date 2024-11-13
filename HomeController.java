package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.ReadCSV2.FinanceNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class HomeController implements Initializable {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	int selectedMonth = 0;
	int selectedYear = 0;
	int totalDays = 1;
	int today = 0;
	int prevDay = 0;
	double spent = 0;
	
	private String[] choices = {"1 Month", "3 Month", "6 Month", "1 Year"};

	//Singleton Design Pattern for CSV File
	SelectedFile file = SelectedFile.getInstance(); 
	
	@FXML
	private LineChart<Number, Number> linechart;
	@FXML
	private ChoiceBox<String> choicebox;
	
	// The following method controls the bar graph on the home page
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { 
		linechart.setLegendVisible(false);
		
		choicebox.getItems().addAll(choices);
		choicebox.setValue(file.getChoice());
		choicebox.setOnAction(this::update);
		
		if(file.getFile() != null)
			updateOptions();
		
	} // end initialize
	
	public void update(ActionEvent event) {
		updateOptions();
		file.setChoice(choicebox.getValue());
	}
	
	public void updateOptions() {
		
		String path = file.getFile().getPath();
		ReadCSV2 list = new ReadCSV2(path);
		FinanceNode head = list.getHead();
		
		if(choicebox.getValue() == "1 Month") {
			selectedMonth = head.next.month;
			selectedYear = head.next.year;
		}
		else if(choicebox.getValue() == "3 Month") {
			selectedMonth = head.next.month - 2;
			selectedYear = head.next.year;
		}
		else if(choicebox.getValue() == "6 Month") {
			selectedMonth = head.next.month - 5;
			selectedYear = head.next.year;
		}
		else if(choicebox.getValue() == "1 Year") {
			selectedMonth = head.next.month - 11;
			selectedYear = head.next.year;
		}
			
		if(selectedMonth < 0) {
			selectedMonth = selectedMonth + 12;
			selectedYear--;
			updateYearTable();
		}
		else {
		updateTable();
		}
		
	}
	
	public void updateTable() {
		
		if(file.getFile() == null)
			return;

		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		
		// Removes old file data
		if(linechart != null)
			linechart.getData().clear();
		
		String path = file.getFile().getPath();
		ReadCSV2 list = new ReadCSV2(path);
		FinanceNode head = list.getHead();
		FinanceNode tail = list.getTail();
		FinanceNode cursor = tail.prev;

		while(cursor != head) {
			
			if(cursor.month >= selectedMonth && cursor.year >=selectedYear) {
				today = cursor.day;
				
				if(prevDay == 0)
					prevDay = today;
				
				if(today != prevDay) {
					series.getData().add(new XYChart.Data<Number, Number>(totalDays,spent));
					
					//System.out.printf("%d/%d/%d Description: %s $%f\n\tTotalDays: %d, Spent:%f\n",
					//		cursor.month, cursor.day, cursor.year, cursor.description, cursor.amount, totalDays, spent);
					
					spent = 0;
					if(today - prevDay < 0)
						totalDays = totalDays + (30 - prevDay);
					else
						totalDays = totalDays + (today - prevDay);
					
				}
				
				if(cursor.amount < 0)
					spent = spent + (cursor.amount * -1);
				
				prevDay = today;
			}
			
			cursor = cursor.prev;
			
		}
		
		linechart.getData().add(series); 
		
		totalDays = 1;
		today = 0;
		prevDay = 0;
		
	} // end updateTable 
	
	public void updateYearTable() {
		
		if(file.getFile() == null)
			return;

		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		
		// Removes old file data
		if(linechart != null)
			linechart.getData().clear();
		
		String path = file.getFile().getPath();
		ReadCSV2 list = new ReadCSV2(path);
		FinanceNode head = list.getHead();
		FinanceNode tail = list.getTail();
		FinanceNode cursor = tail.prev;

		while(cursor != head) {
			
			if((cursor.month <= selectedMonth && cursor.year == selectedYear + 1) ||
					(cursor.month >= selectedMonth && cursor.year == selectedYear)) {
				today = cursor.day;
				
				if(prevDay == 0)
					prevDay = today;
				
				if(today != prevDay) {
					series.getData().add(new XYChart.Data<Number, Number>(totalDays,spent));
					
					//System.out.printf("%d/%d/%d Description: %s $%f\n\tTotalDays: %d, Spent:%f\n",
					//		cursor.month, cursor.day, cursor.year, cursor.description, cursor.amount, totalDays, spent);
					
					spent = 0;
					if(today - prevDay < 0)
						totalDays = totalDays + (30 - prevDay);
					else
						totalDays = totalDays + (today - prevDay);
					
				}
				
				if(cursor.amount < 0)
					spent = spent + (cursor.amount * -1);
				
				prevDay = today;
			}
			
			cursor = cursor.prev;
			
		}
		
		linechart.getData().add(series); 
		
		totalDays = 1;
		today = 0;
		prevDay = 0;
		
	} // end updateYearChange
	
	// Allows user to select new CSV File and sends to Singleton
	public void uploadCSV(ActionEvent event) {
		
		FileChooser fileChooser = new FileChooser();
		File csvFile = fileChooser.showOpenDialog(stage);
		file.setFile(csvFile);
		updateOptions();
		
	} // end uploadCSV
	
	// The following method controls the 'Home' button
	public void switchToHome(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end swtichToHome
	
	// The following method controls the 'List' button
	public void switchToList(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("List.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToList
	
	// The following method controls the 'Group' button
	public void switchToGroup(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Group.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToGroup
	
	// The following method controls the 'Bills' button
	public void switchToBills(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Bills.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToList
	
	// The following method controls the 'Savings' button
	public void switchToSavings(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Savings.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToList
	
	// The following method controls the 'Loan' button
	public void switchToLoan(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Loan.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToList
	
} // end HomeController class

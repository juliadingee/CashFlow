package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ReadCSV2.FinanceNode;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class GroupController implements Initializable{

	private Stage stage;
	private Scene scene;
	
	int selectedMonth = 0;
	int selectedYear = 0;
	
	double dining = 0;
	double fitness = 0;
	double transfers = 0;
	double shopping = 0; 
	double creditCards = 0;
	double entertainment = 0;
	double auto = 0;
	double home = 0;
	double groceries = 0;
	
	private String[] choices = {"1 Month", "3 Month", "6 Month", "1 Year"};
	
	//Singleton Design Pattern for CSV File
	SelectedFile file = SelectedFile.getInstance();
	
	@FXML
	private PieChart piechart;
	@FXML
	private ChoiceBox<String> choicebox;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		choicebox.setItems(FXCollections.observableArrayList(choices));
		choicebox.setValue(file.getChoice());
		choicebox.setOnAction(this::update);
		
		if(file.getFile() != null) {
			updateOptions();
		}
		
	} // end initialize
	
	public void update(ActionEvent event) {
		if(piechart != null)
			piechart.getData().clear();
		
		file.setChoice(choicebox.getValue());
		
		dining = 0;
		fitness = 0;
		transfers = 0;
		shopping = 0; 
		creditCards = 0;
		entertainment = 0;
		auto = 0;
		home = 0;
		groceries = 0;
	
		updateOptions();
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
			getCountYear();
		}
		else {
		getCount();
		}
		
		updatePie();
	}
	
	public void getCount() {
		
		if(file.getFile() == null) 
			return;

		String path = file.getFile().getPath();
		ReadCSV2 list = new ReadCSV2(path);
		FinanceNode head = list.getHead();
		FinanceNode tail = list.getTail();
		FinanceNode cursor = head.next;
		
		while(cursor != tail) {
		
			if(cursor.month >= selectedMonth && cursor.year >= selectedYear) {
				
				if(cursor.category.equals("Restaurants & Dining"))
					dining = dining + (cursor.amount * -1);
				else if(cursor.category.equals("Personal Care & Fitness"))
					fitness = fitness + (cursor.amount * -1);
				else if(cursor.category.equals("Transfers")) {
					if(cursor.amount < 0)
						transfers = transfers + (cursor.amount * -1);
				}
				else if(cursor.category.equals("Shopping"))
					shopping = shopping + (cursor.amount * -1);
				else if(cursor.category.equals("Credit Card Payments"))
					creditCards = creditCards + (cursor.amount * -1);
				else if(cursor.category.equals("Entertainment"))
					entertainment = entertainment + (cursor.amount * -1);
				else if(cursor.category.equals("Automotive Expenses") ||
						cursor.category.equals("Gasoline/Fuel"))
					auto = auto + (cursor.amount * -1);
				else if(cursor.category.equals("Home Supplies"))
					home = home + (cursor.amount * -1);
				else if(cursor.category.equals("Groceries"))
					groceries = groceries + (cursor.amount * -1);
			} // end outer if
		
			cursor = cursor.next;
		} // end while
		
		//rounding to two decimals after period
		dining = Math.round(dining * 100.0) / 100.0;
		fitness = Math.round(fitness * 100.0) / 100.0;
		transfers = Math.round(transfers * 100.0) / 100.0;
		shopping = Math.round(shopping * 100.0) / 100.0;
		creditCards = Math.round(creditCards * 100.0) / 100.0;
		
		entertainment = Math.round(entertainment * 100.0) / 100.0;
		auto = Math.round(auto * 100.0) / 100.0;
		home = Math.round(home * 100.0) / 100.0;
		groceries = Math.round(groceries * 100.0) / 100.0;
		
	} // end getCount()
	
	public void getCountYear() {
		
		if(file.getFile() == null) 
			return;
		
		String path = file.getFile().getPath();
		ReadCSV2 list = new ReadCSV2(path);
		FinanceNode head = list.getHead();
		FinanceNode tail = list.getTail();
		FinanceNode cursor = head.next;
		
		while(cursor != tail) {
		
			if((cursor.year == selectedYear + 1 && cursor.month <= selectedMonth) ||
					(cursor.year == selectedYear && cursor.month >= selectedMonth)) {
				
				if(cursor.category.equals("Restaurants & Dining"))
					dining = dining + (cursor.amount * -1);
				else if(cursor.category.equals("Personal Care & Fitness"))
					fitness = fitness + (cursor.amount * -1);
				else if(cursor.category.equals("Transfers")) {
					if(cursor.amount < 0)
						transfers = transfers + (cursor.amount * -1);
				}
				else if(cursor.category.equals("Shopping"))
					shopping = shopping + (cursor.amount * -1);
				else if(cursor.category.equals("Credit Card Payments"))
					creditCards = creditCards + (cursor.amount * -1);
				else if(cursor.category.equals("Entertainment"))
					entertainment = entertainment + (cursor.amount * -1);
				else if(cursor.category.equals("Automotive Expenses") ||
						cursor.category.equals("Gasoline/Fuel"))
					auto = auto + (cursor.amount * -1);
				else if(cursor.category.equals("Home Supplies"))
					home = home + (cursor.amount * -1);
				else if(cursor.category.equals("Groceries"))
					groceries = groceries + (cursor.amount * -1);
			} // end outer if
		
			cursor = cursor.next;
		} // end while
		
		//rounding to two decimals after period
		dining = Math.round(dining * 100.0) / 100.0;
		fitness = Math.round(fitness * 100.0) / 100.0;
		transfers = Math.round(transfers * 100.0) / 100.0;
		shopping = Math.round(shopping * 100.0) / 100.0;
		creditCards = Math.round(creditCards * 100.0) / 100.0;
		
		entertainment = Math.round(entertainment * 100.0) / 100.0;
		auto = Math.round(auto * 100.0) / 100.0;
		home = Math.round(home * 100.0) / 100.0;
		groceries = Math.round(groceries * 100.0) / 100.0;
		
	} // end getCountYear()
	
	public void updatePie() {
		
		ObservableList<PieChart.Data> pieChartData =
				FXCollections.observableArrayList(
						new PieChart.Data("Dining",dining),
						new PieChart.Data("Fitness", fitness),
						new PieChart.Data("Transfers", transfers),
						new PieChart.Data("Shopping", shopping),
						new PieChart.Data("Credit Cards", creditCards),
						new PieChart.Data("Entertainment", entertainment),
						new PieChart.Data("Auto", auto),
						new PieChart.Data("Home", home),
						new PieChart.Data("Groceries", groceries)
				);
		
		//changing what it shows next to data info on graph
		pieChartData.forEach(data ->
			data.nameProperty().bind(
					Bindings.concat(
							data.getName(), System.lineSeparator(), "$", data.pieValueProperty()
					)
			)
		);
		
		piechart.getData().addAll(pieChartData);
		piechart.setLegendSide(Side.LEFT);
		
	} // end updatePie()
	
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
		
	} // end switchToBills
	
	// The following method controls the 'Savings' button
	public void switchToSavings(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Savings.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToSavings
	
	// The following method controls the 'Loan' button
	public void switchToLoan(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Loan.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToLoan	
	
	public void switchToSideBySide(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Group2.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	} // end switchToSideBySide
	
	public void switchToCategoryList(ActionEvent event) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("Group3.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
} // end controller

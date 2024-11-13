package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.ReadCSV2.FinanceNode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import java.time.*;

public class GroupController3 implements Initializable{

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
	
	double total = 0;
	
	boolean changeYear = false;
	
	private String[] choices = {"1 Month", "3 Month", "6 Month", "1 Year"};
	
	//Singleton Design Pattern for CSV File
	SelectedFile file = SelectedFile.getInstance();
	
	@FXML
	private TreeTableView<Transaction> treeTable = new TreeTableView<Transaction>();
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
		
		if(treeTable != null)
			treeTable.getColumns().clear();
		
		file.setChoice(choicebox.getValue());
		
		clear();
		changeYear = false;
	
		updateOptions();
	}
	
	public void updateOptions() {
		
		if(file.getFile() == null) 
			return;
		
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
		}
		
		updateTable();
	} // end updateOptions
	
	public void updateTable() {
		
		String path = file.getFile().getPath();
		ReadCSV2 list = new ReadCSV2(path);
		FinanceNode head = list.getHead();
		FinanceNode tail = list.getTail();
		FinanceNode cursor = head.next;
		
		TreeItem<Transaction> root = new TreeItem<>(new Transaction("root", "", 0, ""));
		root.setExpanded(true);
			
		TreeTableColumn<Transaction, String> column1 = new TreeTableColumn<>("Category");
		column1.setPrefWidth(200);
		TreeTableColumn<Transaction, String> column2 = new TreeTableColumn<>("Date");
		column2.setPrefWidth(100);
		TreeTableColumn<Transaction, Number> column3 = new TreeTableColumn<>("Amount");
		column3.setPrefWidth(100);
		TreeTableColumn<Transaction, String> column4 = new TreeTableColumn<>("Description");
		column4.setPrefWidth(315);
		
		column1.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Transaction, String> param) -> 
				param.getValue().getValue().getCategory()
		);
		
		column2.setCellValueFactory(			
				(TreeTableColumn.CellDataFeatures<Transaction, String> param) -> 
				param.getValue().getValue().getDate()
		);
		
		column3.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Transaction, Number> param) ->
				param.getValue().getValue().getAmount()
		);
		
		column4.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Transaction, String> param) ->
				param.getValue().getValue().getDescription()
		);
		
		treeTable.getColumns().add(column1);
		treeTable.getColumns().add(column2);	
		treeTable.getColumns().add(column3);
		treeTable.getColumns().add(column4);
		
		getMonthCount(cursor.month, cursor.year);
		
		TreeItem<Transaction> treeDining = new TreeItem<>(new Transaction("Restaurant and Dining", "", dining, ""));
		TreeItem<Transaction> treeFitness = new TreeItem<>(new Transaction("Personal Care & Fitness", "", fitness, ""));
		TreeItem<Transaction> treeTransfers = new TreeItem<>(new Transaction("Transfers", "", transfers, ""));
		TreeItem<Transaction> treeShopping = new TreeItem<>(new Transaction("Shopping", "", shopping, ""));
		TreeItem<Transaction> treeCreditCards = new TreeItem<>(new Transaction("Credit Card Payments", "", creditCards, ""));
		TreeItem<Transaction> treeEntertainment = new TreeItem<>(new Transaction("Entertainment", "", entertainment, ""));
		TreeItem<Transaction> treeAuto = new TreeItem<>(new Transaction("Automotive Expenses", "", auto, ""));
		TreeItem<Transaction> treeHome = new TreeItem<>(new Transaction("Home Supplies", "", home, ""));
		TreeItem<Transaction> treeGroceries = new TreeItem<>(new Transaction("Groceries", "", groceries, ""));
		
		TreeItem<Transaction> diningItem;
		TreeItem<Transaction> fitnessItem;
		TreeItem<Transaction> transfersItem;
		TreeItem<Transaction> shoppingItem;
		TreeItem<Transaction> creditCardsItem;
		TreeItem<Transaction> entertainmentItem;
		TreeItem<Transaction> autoItem;
		TreeItem<Transaction> homeItem;
		TreeItem<Transaction> groceriesItem;
		
		while(cursor != tail) {
			
			if(cursor.year == selectedYear && cursor.month < selectedMonth)
				break;
			
			if((cursor.prev != head) && (cursor.prev.month != cursor.month)) {
				
				TreeItem<Transaction> categories = new TreeItem<>
					(new Transaction(Month.of(cursor.prev.month).name() + " " + Integer.toString(cursor.prev.year), "", total, ""));
				
				categories.getChildren().add(treeDining);
				categories.getChildren().add(treeFitness);
				categories.getChildren().add(treeTransfers);
				categories.getChildren().add(treeShopping);
				categories.getChildren().add(treeCreditCards);
				
				categories.getChildren().add(treeEntertainment);
				categories.getChildren().add(treeAuto);
				categories.getChildren().add(treeHome);
				categories.getChildren().add(treeGroceries);
				
				root.getChildren().add(categories);
				
				clear();
				
				getMonthCount(cursor.month, cursor.year);
				
				treeDining = new TreeItem<>(new Transaction("Restaurant and Dining", "", dining, ""));
				treeFitness = new TreeItem<>(new Transaction("Personal Care & Fitness", "", fitness, ""));
				treeTransfers = new TreeItem<>(new Transaction("Transfers", "", transfers, ""));
				treeShopping = new TreeItem<>(new Transaction("Shopping", "", shopping, ""));
				treeCreditCards = new TreeItem<>(new Transaction("Credit Card Payments", "", creditCards, ""));
				treeEntertainment = new TreeItem<>(new Transaction("Entertainment", "", entertainment, ""));
				treeAuto = new TreeItem<>(new Transaction("Automotive Expenses", "", auto, ""));
				treeHome = new TreeItem<>(new Transaction("Home Supplies", "", home, ""));
				treeGroceries = new TreeItem<>(new Transaction("Groceries", "", groceries, ""));
				
			} // end if
			
			if((changeYear == false) && (cursor.month >= selectedMonth) && (cursor.year >= selectedYear)) {
				
				if(cursor.category.equals("Restaurants & Dining")) {
					diningItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeDining.getChildren().add(diningItem);
				}
				else if(cursor.category.equals("Personal Care & Fitness")) {
					fitnessItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeFitness.getChildren().add(fitnessItem);
				}
				else if(cursor.category.equals("Transfers")) {
					if(cursor.amount < 0) {
						transfersItem = new TreeItem<>(
								new Transaction(cursor.category, cursor.date,
										cursor.amount * -1, cursor.description));
						treeTransfers.getChildren().add(transfersItem);
					}
				}
				else if(cursor.category.equals("Shopping")) {
					shoppingItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date,
									cursor.amount * -1, cursor.description));
					treeShopping.getChildren().add(shoppingItem);
				}
				else if(cursor.category.equals("Credit Card Payments")) {
					creditCardsItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeCreditCards.getChildren().add(creditCardsItem);
				}
				else if(cursor.category.equals("Entertainment")) {
					entertainmentItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeEntertainment.getChildren().add(entertainmentItem);
				}
				else if(cursor.category.equals("Automotive Expenses")) {
					autoItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeAuto.getChildren().add(autoItem);
				}
				else if(cursor.category.equals("Home Supplies")) {
					homeItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeHome.getChildren().add(homeItem);
				}
				else if(cursor.category.equals("Groceries")) {
					groceriesItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeGroceries.getChildren().add(groceriesItem);
				}
				
			} // end outer if
			
			else if((cursor.month <= selectedMonth && cursor.year == selectedYear + 1) ||
					(cursor.month >= selectedMonth && cursor.year == selectedYear)) {
				
				if(cursor.category.equals("Restaurants & Dining")) {
					diningItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeDining.getChildren().add(diningItem);
				}
				else if(cursor.category.equals("Personal Care & Fitness")) {
					fitnessItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeFitness.getChildren().add(fitnessItem);
				}
				else if(cursor.category.equals("Transfers")) {
					if(cursor.amount < 0) {
						transfersItem = new TreeItem<>(
								new Transaction(cursor.category, cursor.date,
										cursor.amount * -1, cursor.description));
						treeTransfers.getChildren().add(transfersItem);
					}
				}
				else if(cursor.category.equals("Shopping")) {
					shoppingItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date,
									cursor.amount * -1, cursor.description));
					treeShopping.getChildren().add(shoppingItem);
				}
				else if(cursor.category.equals("Credit Card Payments")) {
					creditCardsItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeCreditCards.getChildren().add(creditCardsItem);
				}
				else if(cursor.category.equals("Entertainment")) {
					entertainmentItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeEntertainment.getChildren().add(entertainmentItem);
				}
				else if(cursor.category.equals("Automotive Expenses")) {
					autoItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeAuto.getChildren().add(autoItem);
				}
				else if(cursor.category.equals("Home Supplies")) {
					homeItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeHome.getChildren().add(homeItem);
				}
				else if(cursor.category.equals("Groceries")) {
					groceriesItem = new TreeItem<>(
							new Transaction(cursor.category, cursor.date, 
									cursor.amount * -1, cursor.description));
					treeGroceries.getChildren().add(groceriesItem);
				}
				
			} //end else if 
		
			cursor = cursor.next;
		} // end while
		
		TreeItem<Transaction> categories = new TreeItem<>
		(new Transaction(Month.of(cursor.prev.month).name() + " " + Integer.toString(cursor.prev.year), "", total, ""));
		
		categories.getChildren().add(treeDining);
		categories.getChildren().add(treeFitness);
		categories.getChildren().add(treeTransfers);
		categories.getChildren().add(treeShopping);
		categories.getChildren().add(treeCreditCards);
		
		categories.getChildren().add(treeEntertainment);
		categories.getChildren().add(treeAuto);
		categories.getChildren().add(treeHome);
		categories.getChildren().add(treeGroceries);
		
		root.getChildren().add(categories);
		
		treeTable.setRoot(root);
		treeTable.setShowRoot(false);

	} // end updateTable
	
	public void getMonthCount(int month, int year) {
		
		String path = file.getFile().getPath();
		ReadCSV2 list = new ReadCSV2(path);
		FinanceNode head = list.getHead();
		FinanceNode tail = list.getTail();
		FinanceNode cursor = head.next;
		
		while(cursor != tail) {
		
			if(cursor.month == month && cursor.year == year) {
				
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
		
		total = dining + fitness + transfers + shopping + creditCards + entertainment + auto + home + groceries;
		total = Math.round(total * 100.0) / 100.0;
		
	} // end getMonthCount()
	
	public void clear() {
		dining = 0;
		fitness = 0;
		transfers = 0;
		shopping = 0; 
		creditCards = 0;
		entertainment = 0;
		auto = 0;
		home = 0;
		groceries = 0;
		
		total = 0;
	} // end clear
	
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

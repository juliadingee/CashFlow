package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import application.ReadCSV2.FinanceNode;

public class ListController {
    @FXML
    private TableView<Transaction> tableView;

    @FXML
    private TableColumn<Transaction, String> txType;

    @FXML
    private TableColumn<Transaction, Double> amount;

    @FXML
    private TableColumn<Transaction, String> description;
    
    @FXML
    private TableColumn<Transaction, String> date;


    private ObservableList<Transaction> transactionData;

    // Singleton Design Pattern for CSV File
    SelectedFile file = SelectedFile.getInstance();

    private Stage stage;
    private Scene scene;

    @FXML
    public void initialize() {
    	if (file.getFile() != null) {
            String path = file.getFile().getPath();
            ReadCSV2 csvReader = new ReadCSV2(path);

            FinanceNode cursor = csvReader.getHead().next;
            List<Transaction> transactions = new ArrayList<>();

            while (cursor != null && cursor != csvReader.getTail()) {
                transactions.add(new Transaction(
                        cursor.category,
                        String.format("%02d/%02d/%04d", cursor.month, cursor.day, cursor.year),
                        cursor.amount,
                        cursor.description
                ));

                cursor = cursor.next;
            }

            // Initialize transactionData and set it to the TableView
            transactionData = FXCollections.observableArrayList(transactions);
            tableView.setItems(transactionData);

            // Set cell value factories using lambdas
            txType.setCellValueFactory(cellData -> cellData.getValue().getCategory());
            amount.setCellValueFactory(cellData -> cellData.getValue().getAmount().asObject());
            description.setCellValueFactory(cellData -> cellData.getValue().getDescription());
            date.setCellValueFactory(cellData -> cellData.getValue().getDate());
        }
    }
    
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
    
}
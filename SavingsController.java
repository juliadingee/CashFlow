package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;


public class SavingsController {
	private Stage stage;
    private Scene scene;
    
    @FXML
    private TableView<SavingsModel> tableView;

    @FXML
    private TableColumn<SavingsModel, String> accountTotalColumn;
    
    @FXML
    private TableColumn<SavingsModel, String> addColumn;
    
    @FXML
    private TableColumn<SavingsModel, String> removeColumn;

    @FXML
    private TextField textField;
    
    @FXML
    private TextField totalText;

    private static ObservableList<SavingsModel> data = FXCollections.observableArrayList();

    private double totalAmount = 0;
    
    private double amountRemoved = 0;
    
    private double amountAdded = 0;

    @FXML
    public void initialize() {
        data.clear(); // Clear the list
        data.addAll(DataHandler.loadData()); // Add loaded data

        // Iterate through the data and accumulate the values
        for (SavingsModel model : data) {
            amountAdded = model.getAddAmount();
            amountRemoved = model.getRemoveAmount();
            totalAmount = model.getAmount();

        }

        tableView.setItems(data);
        
        // Set the cell value factories for the columns
        accountTotalColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(totalAmount));

        });
        addColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(amountAdded));
        });        
        removeColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(String.valueOf(amountRemoved));
        });
        
        updateTotalText();
    }
    
    // Save data when adding or removing money
    static void saveData() {
       DataHandler.saveData(data);
    }

    
    @FXML
    private void addMoneyToTotal(ActionEvent event) {
        String inputText = textField.getText();
        if (!inputText.isEmpty()) {
            try {
                amountAdded = Double.parseDouble(inputText);
                totalAmount += amountAdded;
                amountRemoved = 0.0;
                SavingsModel newData = new SavingsModel( amountAdded, amountRemoved, totalAmount);
                data.add(newData);
                textField.clear();
                updateTotalText();
                saveData();
            } catch (NumberFormatException e) {
                return;
            }
        }
    }

	@FXML
	private void removeMoneyFromTotal(ActionEvent event) {
	    String inputText = textField.getText();
	    if (!inputText.isEmpty()) {
	        try {
	            amountRemoved = Double.parseDouble(inputText);
	            totalAmount -= amountRemoved;
	            amountAdded = 0.0;
	            SavingsModel newData = new SavingsModel(amountAdded, amountRemoved, totalAmount); // Use a negative amount to represent removal
	            data.add(newData);
	            textField.clear();
	            updateTotalText();
	            saveData();
	        } catch (NumberFormatException e) {
	            return;
	        }
	        
	    }
	}
    
    private void updateTotalText() {
        totalText.setText(String.format("Account Total: $%.2f", totalAmount));
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

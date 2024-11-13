
package application;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillsController implements Initializable  {
	
	private Stage stage;
    private Scene scene;

    @FXML
    private TableColumn<IndividualBill,LocalDate > DateColumn;
    
    @FXML
    private TableColumn<IndividualBill,String > AmountColumn;
   

    @FXML
    private TableColumn<IndividualBill,String > DescriptionColumn;
    
    
    @FXML
    private TableColumn<IndividualBill,String > CategoryColumn;
    
    @FXML
    private TableView<IndividualBill> tableView;
    
   
    @FXML
    private TextField dueDateText;
    @FXML
    private TextField amountText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField categoryText;
    
    @FXML
    private TextField totalBillAmount;
    
    @FXML
    private TextField billToIncome;
    
    @FXML
    private TextField NextBillDue;
    
    LocalDate storedDate;
    
    
    
    private int billTotal =0;

    
    
   private ObservableList<IndividualBill> billItem = FXCollections.observableArrayList();
    //private ObservableList<IndividualBill> billItem = ();   
   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
	
        DateColumn.setCellValueFactory(new PropertyValueFactory<IndividualBill, LocalDate>("Date"));
        AmountColumn.setCellValueFactory(new PropertyValueFactory<IndividualBill, String>("Amount")); 
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<IndividualBill, String>("Description"));
        CategoryColumn.setCellValueFactory(new PropertyValueFactory<IndividualBill, String>("Category"));

	   
      
    }
   
   
    @FXML
	 void addButton(ActionEvent e) {
    	
    	//get the text for the amount and convert to int
    	//String amountTxt = amountText.getText();
    	int amountTextToInt = roundUpDouble(amountText.getText());
    	
    	//get the due date text to create the date object
    	LocalDate billDate = convertDate();


    	//keep track of the amount of bills input so far
    	billTotal += amountTextToInt;
    	updateText(billTotal);
    	
    	//updates the next bill due field
    	updateNextBillDue(billDate);
    	
    	//creates the bill object 
	    IndividualBill bill = new IndividualBill(
	    		billDate,
	    		amountTextToInt,
	    		//amountText.getText(),
	    		descriptionText.getText(),
	    		categoryText.getText());
	    
	    //adds bill object to the Observable list
	    billItem.add(bill);
	    //sets the table view so the observable list can be seen in the table
	    tableView.setItems(billItem);
	    
	    //clears the fields to 
	    clearFields();
	    
    }
    
  //get the due date text to create the date object
    private LocalDate convertDate() {
    	String dateText = dueDateText.getText();
    	
    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
    	LocalDate billDate = LocalDate.parse(dateText, dateFormatter);
    	
    	return billDate;
    	
    }

    
    private void clearFields() {
	    dueDateText.clear();
	    amountText.clear();
	    descriptionText.clear();
	    categoryText.clear();
    	
    }
    private void updateText(int billTotal) {
    	String bill = Integer.toString(billTotal);
    	
    	totalBillAmount.setText(bill);
    	
    	
    	
    }
    
    private void updateNextBillDue(LocalDate dateDue) {
    	
    //get the current local date
    	LocalDate currentDate = LocalDate.now(); 
    //if the due date is before the current date the bill has passed
        if (dateDue.isBefore(currentDate)) {
            NextBillDue.setText("no upcoming bills");
            return;
        } else if (dateDue.isAfter(currentDate)) {
            if (storedDate == null || storedDate.isBefore(currentDate)) {
                storedDate = dateDue; // Store dateDue as the storedDate for future comparisons
                NextBillDue.setText(dateDue.toString());
            } else {
                // the stored date should be closest to the current date
                if (storedDate.isAfter(dateDue)) {
                    // If the stored date is after the new dateDue, update it
                    storedDate = dateDue;
                    NextBillDue.setText(dateDue.toString());
                }
                // Else, do nothing as the storedDate is already the closest one
            }
        }
    }
  

//round up the number to the next whole integer
    private int roundUpDouble(String initialValue) {
    	int result =0;
    	double amountTxtDouble = Double.parseDouble(initialValue);
    	//round up to integer
    	 result =  (int) Math.ceil(amountTxtDouble);
    	 
    	 return result;
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





	
} // end controller

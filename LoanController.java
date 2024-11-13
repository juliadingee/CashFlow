package application;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.Scanner;
import java.text.SimpleDateFormat;
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

public class LoanController {
	
	private Stage stage;
	private Scene scene;
	
	double totalAmount = 0;
	double oldTotalAmount = 0;
	double paymentAmount = 0;
	
	ObservableList<Payment> payments = FXCollections.observableArrayList();
	
//	2950.0
//	10/10/2023,40.0,3060.0,
//	11/12/2023,60.0,3000.0,
//	11/25/2023,50.0,2950.0,
	
	@FXML
	private TextField totalField;
	@FXML
	private TextField paymentField;
	@FXML
	private TableView<Payment> loanTable;
	@FXML
	private TableColumn<Payment, String> dateColumn;
	@FXML
	private TableColumn<Payment, Number> paidColumn;
	@FXML
	private TableColumn<Payment, Number> totalColumn;
	
	@FXML
	public void initialize() throws IOException {

		readTotal();
		readPayment();
		
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		paidColumn.setCellValueFactory(cellData -> cellData.getValue().paidProperty());
		totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
		
		loanTable.setItems(payments);
		
	} // end initialize

	public void readTotal() throws IOException {
		
		File file = new File(System.getProperty("user.dir") + "\\src\\application\\LoanData.csv");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		
		totalAmount = Double.parseDouble(line);
		oldTotalAmount = totalAmount;
		br.close();
		
		totalField.setText("$" + totalAmount);
		
	} // end setTotal
	
	public void writeTotal() throws IOException {
		
		String pathHolder = new File("").getAbsolutePath() + "/src/application/LoanData.csv";
		pathHolder = pathHolder.replace("\\", "/");
		
		File file = new File(pathHolder);
		Scanner sc = new Scanner(file);
		StringBuffer buffer = new StringBuffer();
		
		while(sc.hasNextLine()) {
			buffer.append(sc.nextLine() + "\n");
		} // end while
		
		String fileContents = buffer.toString();
		sc.close();
		
		String oldTotal = String.valueOf(oldTotalAmount);
		String newTotal = String.valueOf(totalAmount);
		
		fileContents = fileContents.replaceFirst(oldTotal, newTotal);
		
		FileWriter writer = new FileWriter(file);
		
		writer.append(fileContents);
		writer.flush();
		writer.close();
		
		oldTotalAmount = totalAmount;
		
	} // end writeTotal
	
	public void payment(ActionEvent event) throws IOException {
		
        String inputText = paymentField.getText();
        
        if (!inputText.isEmpty()) {
            	
            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String strDate = dateFormat.format(today);
            
            paymentAmount = Double.parseDouble(inputText);
            totalAmount -= paymentAmount;
            
            Payment payment = new Payment(strDate, paymentAmount, totalAmount);
            payments.add(payment);
            paymentField.clear();
            
            writePayment(strDate);
            writeTotal();
            readTotal();

        } // end if
        
	} // end payment
	
	public void writePayment(String date) throws IOException {

		String pathHolder = new File("").getAbsolutePath() + "/src/application/LoanData.csv";
		pathHolder = pathHolder.replace("\\", "/");
		
		File file = new File(pathHolder);
		FileWriter fileWriter = new FileWriter(file, true);

		fileWriter.append(date + ',');
		fileWriter.append(String.valueOf(paymentAmount) + ',');
		fileWriter.append(String.valueOf(totalAmount) + ',');
		fileWriter.append('\n');
		fileWriter.close();
		
	} // end writePayment
	
	public void readPayment() throws IOException {
		
		File file = new File(System.getProperty("user.dir") + "\\src\\application\\LoanData.csv");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		
		while((line = br.readLine()) != null) {
			String[] paymentRow = line.split(",");
			Payment payment = new Payment(
					paymentRow[0], Double.parseDouble(paymentRow[1]), Double.parseDouble(paymentRow[2])
					);
			payments.add(payment);
			
		} // end while
		
		br.close();
		
	} // end readPayment
	
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
	
} // end LoanController

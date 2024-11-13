package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Transaction {

	private SimpleStringProperty category;
	private SimpleStringProperty date;
	private SimpleDoubleProperty amount;
	private SimpleStringProperty description;
	
	public Transaction() {}
	
	public Transaction(String category, String date, double amount, String description) {
		this.date = new SimpleStringProperty(date);
		this.amount = new SimpleDoubleProperty(amount);
		this.description = new SimpleStringProperty(description);
		this.category = new SimpleStringProperty(category);
	} // end Transaction constructor
	
	public SimpleStringProperty getCategory() {
		return category;
	} // end getCategory
	
	public void setCategory(String category) {
		this.category = new SimpleStringProperty(category);
	} // end setCategory
	
	public SimpleStringProperty getDate() {
		return date;
	} // end getDate
	
	public void setDate(String date) {
		this.date = new SimpleStringProperty(date);
	} // end setDate
	
	public SimpleDoubleProperty getAmount() {
		return amount;
	} // end getAmount
	
	public void setAmount(double amount) {
		this.amount = new SimpleDoubleProperty(amount);
	} // end setAmount
	
	public SimpleStringProperty getDescription() {
		return description;
	} // end getDescription
	
	public void setDescription(String description) {
		this.description = new SimpleStringProperty(description);
	} // end setDescription
	
} // end Transaction class

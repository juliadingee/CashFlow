package application;

import java.time.LocalDate;



public class IndividualBill {
    private String Category;
    private LocalDate Date;
    private int Amount;
    private String Description;

    /*public IndividualBill(String Category, String Date, String Amount, String Description) {
    	 public IndividualBill(LocalDate Date, String Amount, String Description,String Category) {
        this.Amount = Amount;
        this.Category = Category;
        this.Description = Description;
        this.Date = Date;
    }
*/
    public IndividualBill(LocalDate localDate, int Amount, String Description,String Category) {
        this.Amount = Amount;
        this.Category = Category;
        this.Description = Description;
        this.Date = localDate;
    }
		

	public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate Date) {
        this.Date = Date;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }
}


package application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingsModel {
    private final DoubleProperty amount;
    private final DoubleProperty addAmount;
    private final DoubleProperty removeAmount;

    public SavingsModel( double addAmount, double removeAmount, double amount) {
        this.amount = new SimpleDoubleProperty(amount);
        this.addAmount = new SimpleDoubleProperty(addAmount);
        this.removeAmount = new SimpleDoubleProperty(removeAmount);
        
        //this.addAmount = new SimpleDoubleProperty(this, "addAmount", addAmount);
    }
    
    // Override the toString() method to provide a custom string representation
    @Override
    public String toString() {
        return "application.SavingsModel@" + Integer.toHexString(hashCode());
    }

    public double getAmount() {
        return amount.get();
    }

    public double getAddAmount() {
        return addAmount.get();
    }

    public double getRemoveAmount() {
        return removeAmount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public DoubleProperty addAmountProperty() {
        return addAmount;
    }

    public DoubleProperty removeAmountProperty() {
        return removeAmount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public void setAddAmount(double addAmount) {
        this.addAmount.set(addAmount);
    }

    public void setRemoveAmount(double removeAmount) {
        this.removeAmount.set(removeAmount);
    }
}
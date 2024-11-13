package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private static final String CSV_FILE = "savings_data.csv";

    public static void saveData(List<SavingsModel> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            for (SavingsModel model : data) {
                String text = model.getAddAmount() + "," + model.getRemoveAmount() + "," + model.getAmount()  + "\n";
                writer.write(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<SavingsModel> loadData() {
        List<SavingsModel> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length == 3) {
                    
                    double addAmount = Double.parseDouble(values[0]);
                    double removeAmount = Double.parseDouble(values[1]);
                    double amount = Double.parseDouble(values[2]);

                    SavingsModel savingsModel = new SavingsModel(addAmount, removeAmount, amount);
                    System.out.println(savingsModel);
                    
                    data.add(savingsModel);
                    
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return data;
    }
}


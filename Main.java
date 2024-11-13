package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;




public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// window title and icon logo
			primaryStage.setTitle("FinanceTracker");
			Image icon = new Image("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRhC0W8VGwFexLhl4uMhcD4WWCYLDV22ZDeRhSErNLx6WSTP93n");
			primaryStage.getIcons().add(icon);
			
			Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
		    // Save data when the application is closed
		    SavingsController.saveData();
		}));
	}
}

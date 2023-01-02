package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * The main class, does the things it needs to do in a FX project
 * @author pascaru
 */
public class Main extends Application {
	
	public static Scene scene;
	
	/**
	 * Nothing interesting here
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage){
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
			scene = new Scene(root);
			String css = this.getClass().getResource("application.css").toExternalForm();
			root.requestFocus();
			
			stage.setMinHeight(540);
			stage.setMinWidth(810);
			stage.setHeight(540);
			stage.setWidth(810);
			
			stage.setTitle("U-Safe");
			
			Image icon = new Image("logo.png");
			stage.getIcons().add(icon);
		
			scene.getStylesheets().add(css);
			
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}

package net.etfbl.mdp.admin;

import java.io.File;
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
			
		 Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
	     primaryStage.setTitle("CZMDP");
	     
	     primaryStage.getIcons().add(new Image(new FileInputStream(new File("src/images/admin.png" ))));
	     primaryStage.setScene(new Scene(root, 630, 450));
	     primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

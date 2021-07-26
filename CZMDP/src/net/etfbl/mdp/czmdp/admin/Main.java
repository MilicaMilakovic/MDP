package net.etfbl.mdp.czmdp.admin;

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
	    // primaryStage.getIcons().add(new Image(new FileInputStream(new File(file.getAbsolutePath()+File.separator+"icon.png"))));
	     primaryStage.setScene(new Scene(root, 600, 400));
	     primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

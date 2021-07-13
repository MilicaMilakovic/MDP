package net.etfbl.mdp.zsmdp.main;
	
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static final File file = new File("./resources");
	public static final String resources= file.getAbsolutePath();
	public static final String[] locations = {"Banjaluka","Bijeljina","Sarajevo","Mostar","Trebinje"};
	
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("ZSMDP");
      
        primaryStage.getIcons().add(new Image(new FileInputStream(new File(resources+File.separator+"icon.png"))));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
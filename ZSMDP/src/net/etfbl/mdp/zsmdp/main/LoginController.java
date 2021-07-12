package net.etfbl.mdp.zsmdp.main;


import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.etfbl.mdp.czmdp.soap.UserAuthentication;
import net.etfbl.mdp.czmdp.soap.UserAuthenticationServiceLocator;
import net.etfbl.mdp.model.User;

public class LoginController implements Initializable {

	@FXML
	public TextField username;
	
	@FXML
	public PasswordField password;
	
	@FXML
	public ChoiceBox<String> cities;
	
	@FXML
	public Button button;
	
	
   public void onClick() {
	
	   String name = username.getText();
	   String pass = password.getText();
	   String city = cities.getValue();
	  
	 /*  try {
			XMLEncoder encoder = new XMLEncoder(new FileOutputStream(new File("xml.out"),true));
			encoder.writeObject(new User("milica","milica","Banjaluka"));
			//encoder.writeObject(new User("aco","aco","Trebinje"));
			encoder.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
	   
	  // System.out.println(name + " " + pass + " " + city);
	   
	   User user = new User(name,pass,city);
	   
	   System.out.println(user);
	   
	   
	   UserAuthenticationServiceLocator locator = new UserAuthenticationServiceLocator();
	   
	   try {
		   
		UserAuthentication service = locator.getUserAuthentication();
		
		if(service.verify(user))
			System.out.println("dobar");
		else
			System.out.println("nije dobro");
		
		if(service.verify(user))
		{
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
			primaryStage.setTitle("ZSMDP");
			primaryStage.setScene(new Scene(root,900,600));
			
			primaryStage.show();
			Stage stage = (Stage) button.getScene().getWindow();
			stage.close();
		}
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	  
   }



@Override
public void initialize(URL location, ResourceBundle resources) {
	
	cities.getItems().add("Banjaluka");
	cities.getItems().add("Bijeljina");
	cities.getItems().add("Sarajevo");
	cities.getItems().add("Mostar");
	cities.getItems().add("Trebinje");
}

    
}
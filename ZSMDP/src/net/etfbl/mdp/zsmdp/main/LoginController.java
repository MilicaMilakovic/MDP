package net.etfbl.mdp.zsmdp.main;


import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.czmdp.soap.UserServiceServiceLocator;
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
	
	   if(username.getText().equals("") || password.getText().equals("") || cities.getValue() == null)
	   {
		   moveButton(button);
		   return;
	   }
	   
	   String name = username.getText();
	   String pass = password.getText();
	   String city = cities.getValue();		   
	   	   	 	   
	   UserServiceServiceLocator locator = new UserServiceServiceLocator();
	   
	   try {	  
		   
		UserService service = locator.getUserService();
		
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		KeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		byte[] hash = factory.generateSecret(spec).getEncoded();
		
		User user = new User(name, Base64.getEncoder().encodeToString(hash), city);		
		
		if(service.verify(user))
			System.out.println("dobar");
		else
		{
			moveButton(button);
			return;
		}
		
		
		if(service.verify(user))
		{
			if(service.getOnlineUsers() != null && Arrays.asList(service.getOnlineUsers()).contains(user) )	{
				
				System.out.println("Korisnik vec prijavljen");
				
			} else{
				
			MainPageController.user = user;
			
			Stage primaryStage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
			primaryStage.setTitle("ZSMDP");
			primaryStage.getIcons().add(new Image(new FileInputStream(new File(Main.resources+File.separator+"icon.png"))));
			primaryStage.setScene(new Scene(root,900,600));
						
			primaryStage.show();
			Stage stage = (Stage) button.getScene().getWindow();
			stage.close();
			}
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

	private void moveButton(Button btn) {		
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200));
		translateTransition.setNode(btn);
		translateTransition.setFromX(-5);
		translateTransition.setToX(5);
		translateTransition.setAutoReverse(true);
		translateTransition.setCycleCount(3);
		translateTransition.setInterpolator(Interpolator.EASE_BOTH);
		translateTransition.play();
		translateTransition.setOnFinished(e -> {btn.setTranslateX(0);});		
	
	}
}
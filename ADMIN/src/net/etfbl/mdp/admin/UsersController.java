package net.etfbl.mdp.admin;

import java.net.URL;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.czmdp.soap.UserServiceServiceLocator;
import net.etfbl.mdp.model.MyLogger;
import net.etfbl.mdp.model.User;

public class UsersController implements Initializable {
	
	@FXML
	public TextArea allUsers;
	@FXML
	public TextField usernameField;
	@FXML
	public TextField passwordField;
	@FXML
	public ChoiceBox<String> locations;
	@FXML
	public Button btn_add;
	
	@FXML
	public TextField usernameDel;
	@FXML
	public ChoiceBox<String> locDel;
	@FXML
	public Button btn_del;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		UserServiceServiceLocator locator= new UserServiceServiceLocator();
		
		try {
			UserService service = locator.getUserService();
			
			User[] users= service.getAllUsers();
			
			for(User u : users) {
				allUsers.appendText("�  " + u.toString()+"\n");
			}
		} catch (Exception e) {
            MyLogger.log(Level.WARNING,e.getMessage(),e);

		}
		
		locations.getItems().add("Banjaluka");
		locations.getItems().add("Bijeljina");
		locations.getItems().add("Sarajevo");
		locations.getItems().add("Mostar");
		locations.getItems().add("Trebinje");	
		
		locDel.getItems().add("Banjaluka");
		locDel.getItems().add("Bijeljina");
		locDel.getItems().add("Sarajevo");
		locDel.getItems().add("Mostar");
		locDel.getItems().add("Trebinje");	
	}
	
	public void addUser() {
		
		String username = usernameField.getText();
		String password = passwordField.getText();
		String city = locations.getValue();
		
		if(!username.equals("") && !password.equals("") && !city.equals("") ) {			
			
			
			UserServiceServiceLocator locator = new UserServiceServiceLocator();
			try {
				
				UserService service = locator.getUserService();
				
				SecureRandom random = new SecureRandom();
				byte[] salt = new byte[16];
				KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
				
				byte[] hash = factory.generateSecret(spec).getEncoded();
				
				User user = new User(username, Base64.getEncoder().encodeToString(hash), city);
				
				service.addUser(user);
				
				allUsers.clear();
				User[] users= service.getAllUsers();
				
				for(User u : users) {
					allUsers.appendText("�  " + u.toString()+"\n");
				}
				
				
				
			} catch (Exception e) {
	            MyLogger.log(Level.WARNING,e.getMessage(),e);

			}
			
			usernameField.clear();
			passwordField.clear();
			locations.getSelectionModel().clearSelection();
			
		}  else {
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200));
			translateTransition.setNode(btn_add);
			translateTransition.setFromX(-5);
			translateTransition.setToX(5);
			translateTransition.setAutoReverse(true);
			translateTransition.setCycleCount(3);
			translateTransition.setInterpolator(Interpolator.EASE_BOTH);
			translateTransition.play();
			translateTransition.setOnFinished(e -> {btn_add.setTranslateX(0);});
			
		}
	}
	
	
	public void deleteUser() {
		String username = usernameDel.getText();
		String city = locDel.getValue();
		
		if(!username.equals("") && !city.equals(""))
		{
			User user = new User();
			user.setCity(city);
			user.setUsername(username);
			
			UserServiceServiceLocator locator = new UserServiceServiceLocator();
			try {
				UserService service = locator.getUserService();
				
				service.deleteUser(user);
				
				allUsers.clear();
				User[] users= service.getAllUsers();
				
				for(User u : users) {
					allUsers.appendText("�  " + u.toString()+"\n");
				}
				
			} catch(Exception e) {
	            MyLogger.log(Level.WARNING,e.getMessage(),e);

			}			
			
			usernameDel.clear();
			locDel.getSelectionModel().clearSelection();
			
		} else {
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200));
			translateTransition.setNode(btn_del);
			translateTransition.setFromX(-5);
			translateTransition.setToX(5);
			translateTransition.setAutoReverse(true);
			translateTransition.setCycleCount(3);
			translateTransition.setInterpolator(Interpolator.EASE_BOTH);
			translateTransition.play();
			translateTransition.setOnFinished(e -> {btn_del.setTranslateX(0);});
			
		}
		
	}
	
	
}

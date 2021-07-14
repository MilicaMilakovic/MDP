package net.etfbl.mdp.zsmdp.main;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.czmdp.soap.UserServiceServiceLocator;
import net.etfbl.mdp.model.User;

public class MainPageController implements Initializable {	
	
	@FXML
	public  Label username = new Label();
	@FXML
	public  Label location = new Label();
	
	public static User user;
	@FXML
	public ChoiceBox<String> locations;
	@FXML
	public ChoiceBox<String> activeUsers;
	@FXML
	public Button logOutBtn;
	
	
	public void showOnlineUsers(String city) {
		User[] users = null;
	
		UserServiceServiceLocator locator = new UserServiceServiceLocator();
		try {
			UserService service = locator.getUserService();
			users = service.getOnlineUsers();
			
			User u = service.getActiveUser(city);
			
			activeUsers.getItems().clear();
			if(u != null)
			{
				//activeUsers.getItems().clear();
				activeUsers.getItems().add(u.getUsername());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		username.setText(user.getUsername());
		location.setText(user.getCity());
		
	
		UserServiceServiceLocator locator = new UserServiceServiceLocator();
		
		try {
			UserService service = locator.getUserService();
			service.registerLogin(user);				
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		locations.getItems().add("Banjaluka");
		locations.getItems().add("Bijeljina");
		locations.getItems().add("Sarajevo");
		locations.getItems().add("Mostar");
		locations.getItems().add("Trebinje");
		
		locations.getSelectionModel().select(Arrays.asList(Main.locations).indexOf(user.getCity()));
		activeUsers.getItems().clear();
	
	}
	
	public void locationSelected() {
		String selectedCity = locations.getValue();
		
		showOnlineUsers(selectedCity);
	}
	
	public void logOut() {
		UserServiceServiceLocator locator = new UserServiceServiceLocator();
		try {
			UserService service = locator.getUserService();
			service.registerLogout(user);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		Stage stage = (Stage)logOutBtn.getScene().getWindow();
		stage.close();
		
	}
	
}

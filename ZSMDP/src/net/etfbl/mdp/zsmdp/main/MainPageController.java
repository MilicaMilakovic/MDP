package net.etfbl.mdp.zsmdp.main;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		username.setText(user.getUsername());
		location.setText(user.getCity());
		
		User[] users = null;
		UserServiceServiceLocator locator = new UserServiceServiceLocator();
		try {
			UserService service = locator.getUserService();
			service.registerLogin(user);	
			
			users = service.getOnlineUsers();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		locations.getItems().add("Banjaluka");
		locations.getItems().add("Bijeljina");
		locations.getItems().add("Sarajevo");
		locations.getItems().add("Mostar");
		locations.getItems().add("Trebinje");
		
		locations.getSelectionModel().select(Arrays.asList(Main.locations).indexOf(user.getCity()));
		if(users!=null)
		{
			for(User u : users)
			{
				if(u.getCity().equals(user.getCity()))
					activeUsers.getItems().add(u.getUsername());
			}
		}
	
	}
	
	public void locationSelected() {
		String selectedCity = locations.getValue();
		
		System.out.println(selectedCity);
	}
	
	
	
}

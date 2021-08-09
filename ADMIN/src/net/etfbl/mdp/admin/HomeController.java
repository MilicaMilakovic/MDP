package net.etfbl.mdp.admin;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class HomeController implements Initializable{
	
	@FXML
	public TextArea notificationField;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		notificationField.setText(MainPageController.allNotifications);
		
	}
	public void deleteAll() {
		notificationField.clear();
	}
}

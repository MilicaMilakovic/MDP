package net.etfbl.mdp.admin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class MainPageController implements Initializable {
	
	@FXML
	public AnchorPane mainPage;
	@FXML
	public AnchorPane contentArea;
	
	 @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		 
	}
	 
	public void users() {
		Parent root = null;
		try {
			
			root = FXMLLoader.load(getClass().getResource("Users.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reports() {
		Parent root = null;
		try {
						
			root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void schedule() {
		
	}
}

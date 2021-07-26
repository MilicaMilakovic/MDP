package net.etfbl.mdp.czmdp.admin;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import net.etfbl.mdp.rmi.ReportInterface;

public class ReportsController implements Initializable {
	
	@FXML
	public TextArea reports;
	@FXML
	public TextField reportID;
	@FXML
	public Button btn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			ReportInterface server = (ReportInterface) registry.lookup("ReportServer");
			
			for(String s : server.getAllReports()) {
				reports.appendText("• "+s+"\n");
			}
			
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void download() {
		
		if(reportID.getText()!= null && !reportID.getText().equals("")) {
			
		} else {
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
}

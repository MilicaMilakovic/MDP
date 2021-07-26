package net.etfbl.mdp.czmdp.admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
import net.etfbl.mdp.model.Report;
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
		
		String id=reportID.getText();
		if(id!= null && !id.equals("")) {
			
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry(1099);
				ReportInterface server = (ReportInterface) registry.lookup("ReportServer");
				
				Report r = server.downloadReport(Integer.parseInt(id));
				
				if(r!=null)
				{						
					File file = new File("./reports"+File.separator+r.getReportName());
					
					Files.write(file.toPath(), r.getReportContent());
					System.out.println("Izvjestaj preuzet. Nalazi se u folderu reports");
				}
				
			} catch (RemoteException e) {				
				e.printStackTrace();
			} catch (NotBoundException e) {				
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
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

package net.etfbl.mdp.admin;

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
import java.util.logging.Level;

import com.google.gson.Gson;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import net.etfbl.mdp.model.MyLogger;
import net.etfbl.mdp.model.Report;
import net.etfbl.mdp.model.ReportInfo;
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
		
		Gson gson = new Gson();
		
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			ReportInterface server = (ReportInterface) registry.lookup("ReportServer");
			
			for(String s : server.getAllReports()) {
				ReportInfo info = gson.fromJson(s, ReportInfo.class); 
				reports.appendText("� "+info.toString()+"\n");
			}
			
		} catch (RemoteException | NotBoundException e) {
            MyLogger.log(Level.WARNING,e.getMessage(),e);
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
					System.out.println("Izvjestaj preuzet. Nalazi se u folderu reports.");
				}
				
			} catch (RemoteException e) {				
	            MyLogger.log(Level.WARNING,e.getMessage(),e);
			} catch (NotBoundException e) {				
	            MyLogger.log(Level.WARNING,e.getMessage(),e);
			} catch (IOException e) {
	            MyLogger.log(Level.WARNING,e.getMessage(),e);
			}
			
			
			reportID.clear();
			
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

package net.etfbl.mdp.zsmdp.main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import net.etfbl.mdp.model.TrainStation;

public class TrainPassedController implements Initializable {

	@FXML
	public TextArea schedule;
	@FXML
	public TextField line;
	@FXML 
	public Button btn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getSchedule();	
		
	}
	
	public void register() {
		
		if(line.getText() != null && !line.getText().equals("")) {
			
			int trainLine = Integer.parseInt(line.getText());
			
			try {
				HttpRequest request = HttpRequest.newBuilder()
												 .uri(new URI("http://localhost:8080/CZMDP/api/linije/"+trainLine+"/"+TrainScheduleController.location))
												 .GET()
												 .build();
				HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
				String body = response.body();
				
				Gson gson = new Gson();
				TrainStation station = gson.fromJson(body, TrainStation.class);
				station.setPassed(true);
				station.setRealTime(new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
				
				String station_str = gson.toJson(station);
				
				System.out.println(station_str);
				
				HttpRequest req = HttpRequest.newBuilder()
											 .uri(new URI("http://localhost:8080/CZMDP/api/linije/" + trainLine))
											 .header("Accept", "application/json")
											 .header("Content-type", "application/json")
											 .PUT(BodyPublishers.ofString(station_str))	
											 .build();
				
				HttpResponse<String> response2 = HttpClient.newBuilder().build().send(req, BodyHandlers.ofString());
				
				System.out.println(response2.body());
				getSchedule();
				line.clear();
				
			} catch (URISyntaxException e) {				
				MyLogger.log(Level.WARNING,e.getMessage(),e);
			} catch (IOException e) {				
				MyLogger.log(Level.WARNING,e.getMessage(),e);
			} catch (InterruptedException e) {				
				MyLogger.log(Level.WARNING,e.getMessage(),e);
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
	
	private void getSchedule() {
		try {
			HttpRequest request = HttpRequest.newBuilder()
											 .uri(new URI("http://localhost:8080/CZMDP/api/linije/stanice/"+TrainScheduleController.location))
											 .GET()
											 .build();
			
			HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
			String json = response.body();
			
			schedule.setText(json);
			
		} catch (URISyntaxException | IOException | InterruptedException e) {
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
	}
	
}

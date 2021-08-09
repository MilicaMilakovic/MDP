package net.etfbl.mdp.admin;

import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.google.gson.Gson;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import net.etfbl.mdp.model.MyLogger;
import net.etfbl.mdp.model.TrainLine;
import net.etfbl.mdp.model.TrainStation;

public class ScheduleController implements Initializable {
	
	@FXML
	public TextArea schedule;
	@FXML
	public ChoiceBox<String> locations;
	@FXML
	public TextField time;
	@FXML
	public TextArea line;
	@FXML
	public TextField lineID;
	
	@FXML
	public Button station_btn;
	@FXML
	public Button line_btn;
	@FXML
	public Button delete_btn;
	@FXML
	public TextField lineIDField;
	
	private boolean stationAdded;
	
	private HashMap<String,TrainStation> stations = new HashMap<String, TrainStation>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		locations.getItems().add("Banjaluka");
		locations.getItems().add("Bijeljina");
		locations.getItems().add("Sarajevo");
		locations.getItems().add("Mostar");
		locations.getItems().add("Trebinje");	
		
		getSchedule();
		
	}
	
	private void getSchedule() {
		try {
			HttpRequest request = HttpRequest.newBuilder()
											 .uri(new URI("http://localhost:8080/CZMDP/api/linije"))
											 .GET()
											 .build();
			HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
			
			String body = response.body();
						
			schedule.setText(body);
			
		} catch(Exception e) {
            MyLogger.log(Level.WARNING,e.getMessage(),e);

		}
	}

	
	public void addStation() {
		
		String city = locations.getValue();
		String expectedTime = time.getText();
		
		
		if(!city.equals("") && !expectedTime.equals(""))
		{
			TrainStation station = new TrainStation(city);
			station.setExpectedTime(expectedTime);
			
			stations.put(city, station);
			
			line.appendText(station.toString());
			stationAdded = true;
			
		} else {
			moveButton(station_btn);
		}
		
	}
	
	public void addLine() {
		
		String id = lineIDField.getText();
		if(stationAdded && !id.equals("")) {
			
			TrainLine trainLine = new TrainLine();
			
			trainLine.setTrainLine(stations);
			trainLine.setId(Integer.parseInt(id));
			
			Gson gson = new Gson();
			String line_str = gson.toJson(trainLine);
			try {
				HttpRequest request = HttpRequest.newBuilder()
												 .uri(new URI("http://localhost:8080/CZMDP/api/linije"))
												 .header("Accept", "application/json")
												 .header("Content-type", "application/json")
												 .POST(BodyPublishers.ofString(line_str))
												 .build();
				HttpResponse<String > response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
				
				
				getSchedule();
				
				line.clear();
				locations.getSelectionModel().clearSelection();
				time.clear();
				stations.clear();
				lineIDField.clear();
				
			} catch(Exception e) {
	            MyLogger.log(Level.WARNING,e.getMessage(),e);

			}
		} else 
			moveButton(line_btn);
	}
	
	
	public void deleteLine() {
		
		String input = lineID.getText();		
		
		if(!input.equals("")) {
			int line = Integer.parseInt(input);
			try {
				HttpRequest request = HttpRequest.newBuilder()	
												 .uri(new URI("http://localhost:8080/CZMDP/api/linije/"+line))
												 .DELETE()
												 .build();
				
				HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
				
				getSchedule();
				lineID.clear();
			} catch(Exception e ) {
	            MyLogger.log(Level.WARNING,e.getMessage(),e);

			}
			
		}else 
			moveButton(delete_btn);
		
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

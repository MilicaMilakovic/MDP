package net.etfbl.mdp.zsmdp.main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class TrainScheduleController implements Initializable {

	public static String location;
	@FXML
	public Label city;
	@FXML
	public TextArea timetable;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		city.setText(location);
		
		try {
			HttpRequest request = HttpRequest.newBuilder()
											 .uri(new URI("http://localhost:8080/CZMDP/api/linije/stanice/"+location))
											 .GET()
											 .build();
			
			HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
			String json = response.body();
			
			timetable.setText(json);
		} catch (URISyntaxException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

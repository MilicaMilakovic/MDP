package net.etfbl.mdp.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TrainLine {
		
	private static int serialID;
	
	private int id;
	private String name;
	
	private HashMap<String, TrainStation> trainLine = new HashMap<String, TrainStation>();

	public TrainLine() {
		super();

		id = serialID++;
		name = "Linija #" + id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, TrainStation> getTrainLine() {
		return trainLine;
	}

	public void setTrainLine(HashMap<String, TrainStation> trainLine) {
		this.trainLine = trainLine;
	}
	
	@Override
	public String toString() {
		String stations = name+"    ";
		
		for(TrainStation ts : trainLine.values()) {
			stations += ts.toString();
		}
		
		return stations+"\n";
	}
}

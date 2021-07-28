package net.etfbl.mdp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TrainLine implements Serializable {
		
	private static int serialID;
	
	private int id;
	private String name;
	
	private HashMap<String, TrainStation> trainLine = new HashMap<String, TrainStation>();

	
	public TrainLine() {
		super();

		//id = serialID++;
		name = "Linija #" + id;
	}

	
	public TrainLine(int id) {
		super();
		this.id = id;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		name = "Linija #" + id;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrainLine other = (TrainLine) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}

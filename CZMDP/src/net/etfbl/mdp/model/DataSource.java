package net.etfbl.mdp.model;

import java.util.ArrayList;
import java.util.HashMap;

public class DataSource {

	public ArrayList<TrainLine> trainSchedule = new ArrayList<TrainLine>();
	
	private static DataSource instance = null;
	
	public static DataSource getInstace() {
		if(instance == null ) {
			instance = new DataSource();
		}
		
		return instance;
	}
	
	private DataSource() {
		
		HashMap<String, TrainStation> map1 = new HashMap<String, TrainStation>();
		TrainStation ts1 = new TrainStation("Banjaluka");
		TrainStation ts2 = new TrainStation("Bijeljina");
		
		ts2.setExpectedTime("17:00");
		map1.put(ts1.getName(), ts1);
		map1.put(ts2.getName(), ts2);
		
		// linija Banjaluka - Bijeljina
		TrainLine l1 = new TrainLine();
		l1.setTrainLine(map1);
		
		
		HashMap<String, TrainStation> map2 = new HashMap<String, TrainStation>();
		TrainStation ts3 =new TrainStation("Sarajevo");
		TrainStation ts4 = new TrainStation("Banjaluka");
		ts4.setExpectedTime("18:00");
		
		map2.put(ts3.getName(), ts3);
		map2.put(ts4.getName(), ts4);
		
		// Linija Sarajevo - Banjaluka
		TrainLine l2 = new TrainLine();
		l2.setTrainLine(map2);
		

		trainSchedule.add(l1);
		trainSchedule.add(l2);
		
		
//		for(TrainLine tl : trainSchedule)
//			System.out.println(tl);
	}

	public ArrayList<TrainLine> getTrainSchedule() {
	
		return trainSchedule;
	}

	public void setTrainSchedule(ArrayList<TrainLine> trainSchedule) {
		this.trainSchedule = trainSchedule;
	}
	
	
}

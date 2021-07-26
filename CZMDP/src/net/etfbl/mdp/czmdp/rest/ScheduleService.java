package net.etfbl.mdp.czmdp.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import net.etfbl.mdp.model.DataSource;
import net.etfbl.mdp.model.TrainLine;
import net.etfbl.mdp.model.TrainStation;

public class ScheduleService {

	DataSource dataSource = DataSource.getInstace();
	
	public String getSchedule(){
		
		String s = "";
		for(TrainLine tl : dataSource.getTrainSchedule())
			s+= tl.toString();
		return s;
	}
	
	public String getByLocation(String city){
		
		ArrayList<TrainLine> lines = new ArrayList<TrainLine>();
		String s = "";
	
		for(TrainLine tl : dataSource.trainSchedule)
		{
			if(tl.getTrainLine().containsKey(city))
			{
				lines.add(tl);
				s+= tl.toString();
			}
		}
		
		return s;
	}
	
	public ArrayList<TrainLine> getLines()
	{
		return dataSource.trainSchedule;
	}
	
	
	public boolean update(int id, TrainStation station) {
		
		int index = dataSource.trainSchedule.indexOf(new TrainLine(id));
		if(index < 0)
			return false;
		
		TrainLine line = dataSource.trainSchedule.get(index);
		if(line.getTrainLine().containsKey(station.getName())) {
			line.getTrainLine().put(station.getName(), station);
			return true;
		}
		
		return false;
	}
	
	public TrainStation getStation(int id, String location) {
	
		int index = dataSource.trainSchedule.indexOf(new TrainLine(id));
		if(index < 0)
			return null;
		
		TrainLine line = dataSource.trainSchedule.get(index);
		
		return line.getTrainLine().get(location);
		
	}
}

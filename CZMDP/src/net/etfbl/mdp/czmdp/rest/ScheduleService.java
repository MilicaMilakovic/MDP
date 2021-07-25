package net.etfbl.mdp.czmdp.rest;

import java.util.ArrayList;

import net.etfbl.mdp.model.DataSource;
import net.etfbl.mdp.model.TrainLine;

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
		String status="";
		for(TrainLine tl : dataSource.trainSchedule)
		{
			if(tl.getTrainLine().containsKey(city))
			{
					if(tl.getTrainLine().get(city).isPassed())
						status="\u2714";
					else 
						status = "\u274C";

						lines.add(tl);
					s+= (status+"   " + tl.toString());
			}
		}
		
		return s;
	}
	
}

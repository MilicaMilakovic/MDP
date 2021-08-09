package net.etfbl.mdp.czmdp.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;
import java.util.logging.Level;

import com.google.gson.Gson;

import net.etfbl.mdp.model.DataSource;
import net.etfbl.mdp.model.MyLogger;
import net.etfbl.mdp.model.TrainLine;
import net.etfbl.mdp.model.TrainStation;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ScheduleService {

	public static String instanceName = "Schedule";
	DataSource dataSource = DataSource.getInstace();
	JedisPool pool = new JedisPool();
	
	static {
		try {
			MyLogger.setup();
		} catch(Exception e) {
			 MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
	}
	public String getSchedule(){	
		
		String s = "";
		ArrayList<TrainLine> lines = getLines();
		
		if(lines!= null)
		{
			for(TrainLine tl : lines )
				s+=tl.toString();
		}
			
		
//		for(TrainLine tl : dataSource.getTrainSchedule())
//			s+= tl.toString();
		return s;
	}
	
	public String getByLocation(String city){
		
		ArrayList<TrainLine> lines = new ArrayList<TrainLine>();
		String s = "";
		
		ArrayList<TrainLine> trainSchedule = getLines();
		
		for(TrainLine tl : trainSchedule)
		{
			if(tl.getTrainLine().containsKey(city))
			{
				lines.add(tl);
				s+= tl.toString();
			}
		}
			
		return s;
	
//		for(TrainLine tl : dataSource.trainSchedule)
//		{
//			if(tl.getTrainLine().containsKey(city))
//			{
//				lines.add(tl);
//				s+= tl.toString();
//			}
//		}
//		
//		return s;
	}
	
	public ArrayList<TrainLine> getLines()
	{
		ArrayList<TrainLine> lines = new ArrayList<TrainLine>();
		Gson gson = new Gson();
		
		JedisPool pool = new JedisPool("localhost");
		
		try(Jedis jedis = pool.getResource()){	
			
			Set<String> keys = jedis.keys("*");
			for(String key : keys) {
				lines.add(gson.fromJson(jedis.get(key), TrainLine.class));
			}				
			
		}catch(Exception e) {
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		
		pool.close();
//		return dataSource.trainSchedule;
		return lines;
	}
	
	
	public boolean update(int id, TrainStation station) {
		
		JedisPool pool = new JedisPool("localhost");
		
		try(Jedis jedis = pool.getResource()){
			
			String line_str = jedis.get(""+id);
			
			Gson gson = new Gson();
			
			TrainLine line = gson.fromJson(line_str, TrainLine.class);
			line.getTrainLine().put(station.getName(), station);
			
			line_str = gson.toJson(line);
			jedis.set(""+id, line_str);
			jedis.save();
			
			pool.close();
			return true;
			
			
		}catch(Exception e ) {
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		
		return false;
		
//		int index = dataSource.trainSchedule.indexOf(new TrainLine(id));
//		if(index < 0)
//			return false;
//		
//		TrainLine line = dataSource.trainSchedule.get(index);
//		if(line.getTrainLine().containsKey(station.getName())) {
//			line.getTrainLine().put(station.getName(), station);
//			return true;
//		}
//		
//		return false;
	}
	
	public TrainStation getStation(int id, String location) {
	
		JedisPool pool = new JedisPool("localhost");
		
		try(Jedis jedis = pool.getResource()){
			
			String line_str = jedis.get(""+id);
			
			Gson gson = new Gson();
			TrainLine line = gson.fromJson(line_str, TrainLine.class);
			
			pool.close();
			return line.getTrainLine().get(location);			
			
		}catch(Exception e ) {
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		
		pool.close();		
		return null;
//		int index = dataSource.trainSchedule.indexOf(new TrainLine(id));
//		if(index < 0)
//			return null;
//		
//		TrainLine line = dataSource.trainSchedule.get(index);
//		
//		return line.getTrainLine().get(location);
//		
	}
	
	public void addLine(TrainLine line) {
		JedisPool pool = new JedisPool("localhost");
		
		try(Jedis jedis = pool.getResource()){
			
			Gson gson  = new Gson();
			String line_str = gson.toJson(line);
			
//			Set<String> keys = jedis.keys("*");
//			int newId = keys.size();
//			//Long size = jedis.dbSize();
//			line.setId(++newId);
			
	
			
			jedis.set(""+line.getId(), line_str);
			jedis.save();
			
		}catch(Exception e ) {
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		pool.close();
	}
	
	public void deleteLine(int id) {
		JedisPool pool = new JedisPool("localhost");
		
		try(Jedis jedis = pool.getResource()){
			jedis.del(""+id);
			jedis.save();
		}catch (Exception e) {
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		
		pool.close();
	}
}

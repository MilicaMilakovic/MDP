package net.etfbl.mdp.czmdp.soap;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import net.etfbl.mdp.model.User;

public class UserService {
	
	public static final File file = new File("./resources");
	public static final String users = file.getAbsolutePath()+File.separator+"users";
	
	public static ArrayList<User> onlineUsers = new ArrayList<User>();
	
	// uraditi preko hes mape, grad- lista korisnika
	public static HashMap<String, User> activeUsers = new HashMap<String, User>();

	public boolean verify(User user) {
		
		try {
			
			File cityFolder = new File(users+File.separator+user.getCity().toLowerCase());	
			File[] files = cityFolder.listFiles();
			
			XMLDecoder decoder = null;
			
			if(files == null)
				return false;
			
			for(File file : files)
			{
				decoder = new XMLDecoder(new FileInputStream(file));
				User users =(User) decoder.readObject();				
				
				if(users.equals(user)){
					decoder.close();
					return true;
				}
			}			
					
			decoder.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		return false;
	}	
	
	public void registerLogin(User user) {
		
		onlineUsers.add(user);
		activeUsers.put(user.getCity(), user);
		System.out.println("Prijavio se korisnik " + user+ " | ukupno online:" + onlineUsers.size()) ;
	}
	
	public User[] getOnlineUsers() {
		return onlineUsers.toArray(new User[onlineUsers.size()]);
	}
	
	public User getActiveUser(String city) {
		return activeUsers.get(city);
	}
	
	public void registerLogout(User user) {
		
		onlineUsers.remove(user);
		activeUsers.replace(user.getCity(), null);
		System.out.println("Odjavio se korisnik " + user+ " | ukupno online:" + onlineUsers.size()) ;
	}
	
}

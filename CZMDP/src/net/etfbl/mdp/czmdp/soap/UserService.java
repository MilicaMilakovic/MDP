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
	public static int count;
	private static int port = 9000;
	// svi online korisnici
	public static ArrayList<User> onlineUsers = new ArrayList<User>();
	
	// grad, korisnik
	public static HashMap<String, User> activeUsers = new HashMap<String, User>();
	// username - user
	public static HashMap<String, Integer> username_port = new HashMap<String, Integer>();
	
	
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
		++port;
		user.setPort(port);
		onlineUsers.add(user);
		activeUsers.put(user.getCity(), user);
		username_port.put(user.getUsername(),port);
		System.out.println(username_port);
		System.out.println("Prijavio se korisnik " + user+ " | ukupno online:" + onlineUsers.size()) ;
	}
	
	public User[] getOnlineUsers() {
		return onlineUsers.toArray(new User[onlineUsers.size()]);
	}
	
	public User getActiveUser(String city) {
		return activeUsers.get(city);
	}
	
	public int assignPort() {
		return port;
	}
	
	public void registerLogout(User user) {
		
		onlineUsers.remove(user);
		activeUsers.replace(user.getCity(), null);
		System.out.println("Odjavio se korisnik " + user+ " | ukupno online:" + onlineUsers.size()) ;
	}
	public int getPort(String username) {
		return username_port.get(username);
	}
}

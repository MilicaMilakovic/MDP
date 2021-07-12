package net.etfbl.mdp.czmdp.soap;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import net.etfbl.mdp.model.User;

public class UserAuthentication {
	
	public boolean verify(User user) {
		
		try {
			
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(new File(getClass().getResource("xml.out").getFile())));
			User users =(User) decoder.readObject();
			
			
			System.out.println(users);
			if(users.equals(user))
				return true;
		
						
			decoder.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		return false;
	}	
	
}

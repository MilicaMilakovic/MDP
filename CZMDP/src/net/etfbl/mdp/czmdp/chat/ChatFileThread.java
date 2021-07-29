package net.etfbl.mdp.czmdp.chat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.google.gson.Gson;

import net.etfbl.mdp.model.MyFile;

public class ChatFileThread extends Thread {
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ChatFileThread(Socket socket) {
		this.socket = socket;
		
		start();
	}
	
	public void run() {
		try {
			
			in = new ObjectInputStream(socket.getInputStream());	
			out = new ObjectOutputStream(socket.getOutputStream());					
			
			System.out.println("Pokrenut ChatFileThread ");
			String file_string;
			
			file_string =(String) in.readObject();
			Gson gson = new Gson();
			
			MyFile file = gson.fromJson(file_string, MyFile.class);
			
			System.out.println("Primljen fajl : " + file.getFileName());
			
			out.writeObject("Fajl dostavljen");
	
			InetAddress addr = InetAddress.getByName("localhost");			
			
			Socket sock = new Socket(addr, file.getReceiverPort());
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			
			
			oos.writeObject(file_string);
			
			System.out.println("ChatServer proslijedio fajl na odrediste");
		} catch(Exception e ) {
			e.printStackTrace();
		}
	}

}

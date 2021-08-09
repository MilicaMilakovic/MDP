package net.etfbl.mdp.czmdp.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.model.Message;
import net.etfbl.mdp.model.MyFile;
import net.etfbl.mdp.model.MyLogger;
import net.etfbl.mdp.model.User;

import com.google.gson.Gson;

public class ChatServerThread extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private String type;
	
	public ChatServerThread(Socket socket) {
		this.socket = socket;
		
		start();
	}
	
	public void run() {
		
		try {
			
			in = new ObjectInputStream(socket.getInputStream());	
			out = new ObjectOutputStream(socket.getOutputStream());					
			
			System.out.println("Pokrenut ChatServerThread ");
			
			type = (String) in.readObject();
			
			String message_string;
			int port;
			Gson gson = new Gson();
			
			if(type.equals("MSG")) {
				
				out.writeObject("OK");
				
				message_string =(String) in.readObject();				
				Message message = gson.fromJson(message_string, Message.class);				
				
//				System.out.println(" Procitana poruka: " + message);
				
				out.writeObject("[ChatServer] : poslao si poruku");
				
				port = message.getReceiverPort();
				
			} else  {
				
				out.writeObject("OK");
				
				message_string = (String ) in.readObject();
				MyFile file = gson.fromJson(message_string, MyFile.class);
				
//				System.out.println("Primljen fajl: " + file.getFileName());
				out.writeObject("[ChatServer] : poslao si fajl");
				
				port = file.getReceiverPort();
			}
								
			InetAddress addr = InetAddress.getByName("localhost");			
						
			//Socket sock = new Socket(addr, port);
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			SSLSocket sock = (SSLSocket) sf.createSocket(addr,port);
			
			System.out.println(sock);
			
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			
			oos.writeObject(type);
			//String status = (String)in.readObject();
			
			//if(status.equals("OK"))
				oos.writeObject(message_string);	
			
		} catch (Exception e) {
			 MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		
	}

}

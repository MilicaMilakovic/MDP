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

import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.model.Message;
import net.etfbl.mdp.model.User;

import com.google.gson.Gson;

public class ChatServerThread extends Thread {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ChatServerThread(Socket socket) {
		this.socket = socket;
		try {
			
			// inicijalizacija ulaznog streama, sa kojeg cu citati poruku
			
					
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		start();
	}
	
	public void run() {
		try {
			
			in = new ObjectInputStream(socket.getInputStream());	
			out = new ObjectOutputStream(socket.getOutputStream());			
			
			
			System.out.println("Pokrenut ChatServerThread ");
			String message_string;
			message_string =(String) in.readObject();
			
			Gson gson = new Gson();
			Message message = gson.fromJson(message_string, Message.class);
			
 			System.out.println(" Procitana poruka: " + message);
			
			out.writeObject("[ChatServer] : poslao si poruku");
			
			InetAddress addr = InetAddress.getByName("localhost");			
						
			Socket sock = new Socket(addr, message.getReceiverPort());
			
			System.out.println(sock);
			ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
			

			
			oos.writeObject(message_string);
			System.out.println("Server proslijedio poruku korisniku  " + message.getReceiverUsername()+ " na portu " 
								+message.getReceiverPort());

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

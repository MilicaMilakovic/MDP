package net.etfbl.mdp.czmdp.chat;

import java.net.ServerSocket;
import java.net.Socket;

import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.model.Message;

public class ChatServer {

	public static final int PORT = 9999;
	
	public static void processMessages() {
		
		try {
			
			ServerSocket ss = new ServerSocket(PORT);
			
			while(true) {
				
				//System.out.println("ChatServer ceka klijente...");
				Socket socket = ss.accept();
				System.out.println("Prihvacen klijent " + socket);
				
				new ChatServerThread(socket);
				
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	
	public static void main(String[] args) {
		
		System.out.println("Chat server pokrenut...");
		processMessages();
	}
}

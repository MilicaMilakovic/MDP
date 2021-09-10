package net.etfbl.mdp.czmdp.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.model.Message;
import net.etfbl.mdp.model.MyLogger;

public class ChatServer {

	public static final int PORT = 8443;
	
	private static final String KEY_STORE_PATH ="./keystore.jks";
	private static final String KEY_STORE_PASSWORD = "securemdp";
	
	public static void processMessages() {
		
		System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD);
		System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);

		
		try {
			
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			
//			ServerSocket ss = new ServerSocket(PORT);
			ServerSocket ss = ssf.createServerSocket(PORT);
			
			while(true) {
				
//				System.out.println("ChatServer ceka klijente...");
//				Socket socket = ss.accept();
				SSLSocket socket = (SSLSocket) ss.accept();
//				System.out.println("Prihvacen klijent " + socket);
				
				new ChatServerThread(socket);
				
			}			
			
		} catch (Exception e) {
			 MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		
	}
		
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Chat server pokrenut...");
		MyLogger.setup();
		processMessages();
	}
}

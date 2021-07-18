package net.etfbl.mdp.zsmdp.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.xml.rpc.ServiceException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.czmdp.soap.UserServiceServiceLocator;
import net.etfbl.mdp.model.Message;
import net.etfbl.mdp.model.User;
import com.google.gson.Gson;

public class MainPageController implements Initializable {	
	
	@FXML
	public  Label username = new Label();
	@FXML
	public  Label location = new Label();
	
	public static User user;
	@FXML
	public ChoiceBox<String> locations;
	@FXML
	public ChoiceBox<String> activeUsers;
	@FXML
	public Button logOutBtn;
	@FXML
	public Button schedule;
	@FXML
	public TextField messageField;
	@FXML
	public TextField inbox;
	
	
	private static final int CHAT_PORT = 9999; 
	
	
	public void showOnlineUsers(String city) {
		User[] users = null;
	
		UserServiceServiceLocator locator = new UserServiceServiceLocator();
		try {
			UserService service = locator.getUserService();
			users = service.getOnlineUsers();
			
			User u = service.getActiveUser(city);
			
			activeUsers.getItems().clear();
			if(u != null)
			{
				//activeUsers.getItems().clear();
				activeUsers.getItems().add(u.getUsername());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		username.setText(user.getUsername());
		location.setText(user.getCity());
		
		
		UserServiceServiceLocator locator = new UserServiceServiceLocator();
		
		try {
			UserService service = locator.getUserService();
			service.registerLogin(user);
			user.setPort(service.assignPort());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println(user);
		
		locations.getItems().add("Banjaluka");
		locations.getItems().add("Bijeljina");
		locations.getItems().add("Sarajevo");
		locations.getItems().add("Mostar");
		locations.getItems().add("Trebinje");
		
		locations.getSelectionModel().select(Arrays.asList(Main.locations).indexOf(user.getCity()));
		activeUsers.getItems().clear();
		
		checkForNewMessages();
	
	}
	
	public void locationSelected() {
		String selectedCity = locations.getValue();
		
		showOnlineUsers(selectedCity);
	}
	
	public void logOut() {
		UserServiceServiceLocator locator = new UserServiceServiceLocator();
		try {
			UserService service = locator.getUserService();
			service.registerLogout(user);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		Stage stage = (Stage)logOutBtn.getScene().getWindow();
		stage.close();
		
	}
	
	public void sendMessage() {
		
		InetAddress addr;
		
		try {
			
			addr = InetAddress.getByName("localhost");
			Socket socket = new Socket(addr,CHAT_PORT);
			
			//PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
//			Message message = new Message();
//			message.setMessage(messageField.getText());
//			message.setSender(user);
//			User rec = new User();
//			rec.setUsername(activeUsers.getValue());
//			message.setReceiver(rec);
			
			// sender.username | sender.port | message | receiver.username
		//	String message = user.getUsername() + "|" + user.getPort()+"|"+ messageField.getText()+"|"+activeUsers.getValue();
			UserServiceServiceLocator locator = new UserServiceServiceLocator();
			UserService service = locator.getUserService();
			
			Message message = new Message(user.getUsername(),messageField.getText(),activeUsers.getValue(),service.getPort(activeUsers.getValue()) );
			
			Gson gson = new Gson();
			String message_string = gson.toJson(message);
			
			out.writeObject(message_string);
	
			String status=(String)in.readObject();
			System.out.println(status);
			
			
			
		} catch (UnknownHostException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void checkForNewMessages() {
			
		new Thread(() -> {
			
			System.out.println("Pokrenut tred za provjeru pristiglih poruka na portu " + user.getPort());
			
			
			Gson gson = new Gson();
			
			try {
				ServerSocket ss = new ServerSocket(user.getPort());
				
				while(true) {					
					
				Socket socket = ss.accept();
				System.out.println("Prihvacen klijent " +socket);
				
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				String message_string = (String) ois.readObject();
				
				Message message = gson.fromJson(message_string, Message.class);
				oos.writeObject("Primljenoo");		
				System.out.println("Primljena poruka " +message.getMessage());
				
				//inbox.setStyle(" -fx-text-fill: #FFFFFF; ");
				
				String m = message.getReceiverUsername() + "   \n" + message.getMessage() + "\n";
				inbox.appendText(m);
					
					
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
					
					
			}
		}
			catch(Exception e) {
				e.printStackTrace();
			}
		}).start();
	}
}

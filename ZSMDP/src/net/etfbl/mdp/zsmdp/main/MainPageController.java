package net.etfbl.mdp.zsmdp.main;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.xml.rpc.ServiceException;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.stage.Stage;
import net.etfbl.mdp.czmdp.soap.UserService;
import net.etfbl.mdp.czmdp.soap.UserServiceServiceLocator;
import net.etfbl.mdp.model.Message;
import net.etfbl.mdp.model.MyFile;
import net.etfbl.mdp.model.MyLogger;
import net.etfbl.mdp.model.User;
import net.etfbl.mdp.rmi.ReportInterface;

import com.google.gson.Gson;

public class MainPageController implements Initializable {	
	
	@FXML
	public  Label username = new Label();
	@FXML
	public  Label location = new Label();
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
	public TextArea inbox;
	@FXML
	public Circle messageArrived;
	@FXML
	public ImageView notificationBell;
	@FXML
	public TextArea notificationField;	
	@FXML
	public Button msgBtn;
	
	public static User user;
	private static final int CHAT_PORT = 8443; 
	private static final int FILE_PORT = 9998;
	private boolean sendingFile = false;
	private File fileToSend;
	
	private static final String KEY_STORE_PATH ="./keystore.jks";
	private static final String KEY_STORE_PASSWORD = "securemdp";
	
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
			 MyLogger.log(Level.WARNING,e.getMessage(),e);
		}			
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
				
		System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD);
		System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
		System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);
		
		username.setText(user.getUsername());
		location.setText(user.getCity());		
		
		UserServiceServiceLocator locator = new UserServiceServiceLocator();
		
		try {
			UserService service = locator.getUserService();
			service.registerLogin(user);
			user.setPort(service.assignPort());
			
		} catch (Exception e) {			
			 MyLogger.log(Level.WARNING,e.getMessage(),e);
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
		checkForNotifications();
	
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
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		Stage stage = (Stage)logOutBtn.getScene().getWindow();
		stage.close();
		
	}
	
	public void sendMessage() {
		
		InetAddress addr;
		
//		System.setProperty("javax.net.ssl.trustStore", KEY_STORE_PATH);
//		System.setProperty("javax.net.ssl.trustStorePassword", KEY_STORE_PASSWORD);
		
		if(locations.getValue()==null || messageField.equals("") || activeUsers.getValue()==null)
		{
			moveButton(msgBtn);
			return;
		}
		
		String receiver = activeUsers.getValue();		
		
			try {
				
				addr = InetAddress.getByName("localhost");
				//Socket socket = new Socket(addr,CHAT_PORT);				
				
				SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
				SSLSocket socket = (SSLSocket) sf.createSocket(addr, CHAT_PORT);
				
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	
				UserServiceServiceLocator locator = new UserServiceServiceLocator();
				UserService service = locator.getUserService();
				
				Gson gson = new Gson();
				String message="";
				// protokol
				
				if(sendingFile)
				{
					out.writeObject("FILE");
					
					if(((String)in.readObject()).equals("OK")) {
						byte[] content = Files.readAllBytes(fileToSend.toPath());
						MyFile file = new MyFile(fileToSend.getName(), content, user.getUsername(), receiver, service.getPort(receiver));
						message = gson.toJson(file);
					}
					
					
				} else {
					out.writeObject("MSG");
					
					if(((String)in.readObject()).equals("OK")) {
						Message msg = new Message(user.getUsername(),messageField.getText(),receiver,service.getPort(receiver) );
						
						message = gson.toJson(msg);					
					}
				}
				
				
				out.writeObject(message);
		
				String status=(String)in.readObject();
				System.out.println(status);
				
				socket.close();
				out.close();
				in.close();
				messageField.clear();
				
				
			} catch (Exception e) {
				MyLogger.log(Level.WARNING,e.getMessage(),e);
			}		
		
	}
	
	public void sendFile() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ZSMDP File Chooser");
		
		File fileSelected = fileChooser.showOpenDialog(stage);
		
		if(fileSelected!=null) {
			fileToSend = fileSelected;
			messageField.setText(fileToSend.getName());
			sendingFile = true;
		}
	}
		
	private void checkForNewMessages() {
		
//		System.setProperty("javax.net.ssl.keyStore", KEY_STORE_PATH);
//		System.setProperty("javax.net.ssl.keyStorePassword", KEY_STORE_PASSWORD);
		
		new Thread(() -> {
			
			System.out.println("Pokrenut tred za provjeru pristiglih poruka na portu " + user.getPort());			
			
			Gson gson = new Gson();
			
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
			
			try {
//				ServerSocket ss = new ServerSocket(user.getPort());
				
				ServerSocket ss = ssf.createServerSocket(user.getPort());
				
				while(true) {					
					
//				Socket socket = ss.accept();
				SSLSocket socket = (SSLSocket) ss.accept();
				//System.out.println("Prihvacen klijent " +socket);
				
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				
				String type = (String)ois.readObject();
				String message_string;
				
				if(type.equals("MSG")) {
					//oos.writeObject("OK");
					
					message_string = (String) ois.readObject();
					Message message = gson.fromJson(message_string, Message.class);
					//System.out.println("Primljena poruka " +message.getMessage());
					
					messageArrived.setVisible(true);	
					
					String m = "\uD83D\uDCAC" +" " +message.getSenderUsername() + "   \n\t" + message.getMessage() + "\n";
					inbox.appendText(m);	
					
				} else {
					//oos.writeObject("OK");
					message_string = (String) ois.readObject();
					
					MyFile file = gson.fromJson(message_string, MyFile.class);
					//System.out.println("Primljen fajl " + file.getFileName());
					
					File f = new File("./files"+File.separator+file.getFileName());
					Files.write(f.toPath(), file.getContent());
					
					messageArrived.setVisible(true);
					
					String m = "\uD83D\uDCC1" + " " +  file.getSenderUsername() +"   \n\t" + file.getFileName() + "\n";
					inbox.appendText(m);
				}
				
				oos.writeObject("Primljenoo");		
								
				Thread.sleep(500);							
					
			}
		}
			catch(Exception e) {
				 MyLogger.log(Level.WARNING,e.getMessage(),e);
			}
		}).start();
	}
	
	
	public void messageSeen() {
		messageArrived.setVisible(false);
	}
	
	
	public void sendReport() {
		
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("ZSMDP Report Chooser");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("PDF Files","*.pdf"));
		File fileSelected = fileChooser.showOpenDialog(stage);
		
		if(fileSelected != null)
		{			
			System.out.println("odabran fajl " + fileSelected.getName());
			
			System.setProperty("java.security.policy", "./resources" + File.separator + "client_policyfile.txt");			
			
			if(System.getSecurityManager() == null ) {
				System.setSecurityManager( new SecurityManager());
			}
			
			
			try {
				byte[] fileContent = Files.readAllBytes(fileSelected.toPath());
				
				String name = "ReportServer";
				Registry registry = LocateRegistry.getRegistry(1099);
				ReportInterface server = (ReportInterface) registry.lookup(name);
				
				System.out.println(user.getUsername() + " poslao izvjestaj " + fileSelected.getName() + " na AZSMDP");
				server.uploadReport(fileSelected.getName(), fileContent, user.getUsername());
				
			} catch (Exception e) {
			 MyLogger.log(Level.WARNING,e.getMessage(),e);
			}
					
		}
	}
	
	public void ringTheBell() {
		
		RotateTransition rotateTransition=new RotateTransition(Duration.millis(200), notificationBell);
		rotateTransition.setFromAngle(30);
		rotateTransition.setToAngle(-30);
		
		rotateTransition.setAutoReverse(true);
		rotateTransition.setCycleCount(3);
		rotateTransition.setInterpolator(Interpolator.EASE_BOTH);
		rotateTransition.play();
		rotateTransition.setOnFinished(e-> {notificationBell.setRotate(0);});		
	}
	
	public void sendNotification() {
		
		Stage primaryStage = new Stage();
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("SendNotification.fxml"));
		
			primaryStage.setTitle("Posalji obavjestenje");
			primaryStage.getIcons().add(new Image(new FileInputStream(new File(Main.resources+File.separator+"icon.png"))));
			primaryStage.setScene(new Scene(root,600,400));
						
			primaryStage.show();
		
		} catch (IOException e) {
			 MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		
	}
	
	public void checkForNotifications() {
				
		new Thread(()-> {
			MulticastSocket socket = null;
			byte[] buffer = new byte[256];
			
			try {
				socket = new MulticastSocket(SendNotificationController.PORT);
				InetAddress group = InetAddress.getByName(SendNotificationController.HOST);
				socket.joinGroup(group);
				
				while(true) {
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					
					String notification = new String(packet.getData(),0, packet.getLength());
					
					notificationField.appendText(notification+"\n");
					
					ringTheBell();
					
					try {
						Thread.sleep(500);
					} catch (Exception e) {
									 MyLogger.log(Level.WARNING,e.getMessage(),e);
					}
				}
				
			} catch (IOException e) {				
							 MyLogger.log(Level.WARNING,e.getMessage(),e);
			}
		}).start();
	}
	
	
	public void showSchedule() {
		
		Stage primaryStage = new Stage();
		Parent root;
		try {
			
			TrainScheduleController.location = user.getCity();
		
			root = FXMLLoader.load(getClass().getResource("TrainSchedule.fxml"));
		
			primaryStage.setTitle("Red voznje");
			primaryStage.getIcons().add(new Image(new FileInputStream(new File(Main.resources+File.separator+"icon.png"))));
			primaryStage.setScene(new Scene(root,600,400));
						
			primaryStage.show();
		
		} catch (IOException e) {
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
		
	}
	
	public void trainPassed() {
		
		Stage primaryStage = new Stage();
		Parent root;
		try {
			
			TrainScheduleController.location = user.getCity();
		
			root = FXMLLoader.load(getClass().getResource("TrainPassed.fxml"));
		
			primaryStage.setTitle("Red voznje");
			primaryStage.getIcons().add(new Image(new FileInputStream(new File(Main.resources+File.separator+"icon.png"))));
			primaryStage.setScene(new Scene(root,600,400));
						
			primaryStage.show();
		
		} catch (IOException e) {
			MyLogger.log(Level.WARNING,e.getMessage(),e);
		}
	}
	
	
	private void moveButton(Button btn) {		
		TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200));
		translateTransition.setNode(btn);
		translateTransition.setFromX(-5);
		translateTransition.setToX(5);
		translateTransition.setAutoReverse(true);
		translateTransition.setCycleCount(3);
		translateTransition.setInterpolator(Interpolator.EASE_BOTH);
		translateTransition.play();
		translateTransition.setOnFinished(e -> {btn.setTranslateX(0);});		
	
	}
}

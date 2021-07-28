package net.etfbl.mdp.admin;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class MainPageController implements Initializable {
	
	@FXML
	public AnchorPane mainPage;
	@FXML
	public AnchorPane contentArea;
	
	@FXML
	public TextArea notificationField;
	@FXML
	public ImageView notificationBell;
	
	public static String allNotifications="";
	
	 @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		 Parent root = null;
			try {
				
				root = FXMLLoader.load(getClass().getResource("Home.fxml"));
				contentArea.getChildren().removeAll();
				contentArea.getChildren().setAll(root);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 checkForNotifications();
	}
	 
	public void home() {
		Parent root = null;
		try {
			
			root = FXMLLoader.load(getClass().getResource("Home.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void users() {
		Parent root = null;
		try {
			
			root = FXMLLoader.load(getClass().getResource("Users.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void reports() {
		Parent root = null;
		try {
						
			root = FXMLLoader.load(getClass().getResource("Reports.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void schedule() {
		Parent root = null;
		try {
						
			root = FXMLLoader.load(getClass().getResource("Schedule.fxml"));
			contentArea.getChildren().removeAll();
			contentArea.getChildren().setAll(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void shutDown() {
		
		System.exit(0);
	}
	
	
	public void checkForNotifications() {
		
		new Thread(()-> {
			MulticastSocket socket = null;
			byte[] buffer = new byte[256];
			
			try {
				socket = new MulticastSocket(20000);
				InetAddress group = InetAddress.getByName("230.0.0.0");
				socket.joinGroup(group);
				
				while(true) {
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					
					String notification = new String(packet.getData(),0, packet.getLength());
					
					//notificationField.appendText(notification+"\n");
					allNotifications += notification+"\n";
					ringTheBell();
					
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			} catch (IOException e) {				
				e.printStackTrace();
			}
		}).start();
	}
	
	
	private void ringTheBell() {
		
		RotateTransition rotateTransition=new RotateTransition(Duration.millis(200), notificationBell);
		rotateTransition.setFromAngle(30);
		rotateTransition.setToAngle(-30);
		
		rotateTransition.setAutoReverse(true);
		rotateTransition.setCycleCount(3);
		rotateTransition.setInterpolator(Interpolator.EASE_BOTH);
		rotateTransition.play();
		rotateTransition.setOnFinished(e-> {notificationBell.setRotate(0);});		
	}
}

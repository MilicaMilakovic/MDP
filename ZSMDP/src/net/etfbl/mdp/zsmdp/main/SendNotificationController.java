package net.etfbl.mdp.zsmdp.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import net.etfbl.mdp.model.MyLogger;

public class SendNotificationController {
	
	@FXML
	public TextArea notification;
	@FXML
	public Button btn;
	
	public static final int PORT = 20000;
	public static final String HOST = "230.0.0.0";
	
	private DatagramSocket socket;
	private InetAddress group;
	private byte[] buffer;
	
	public void sendNotification(){
		
		String content = notification.getText();
		if(content != null && !content.equals(""))
		{
			try {
				socket = new DatagramSocket();
				group = InetAddress.getByName(HOST);
				buffer = content.getBytes();
				
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, PORT);
				socket.send(packet);
				socket.close();
				notification.clear();
				
			} catch (SocketException e) {				
				MyLogger.log(Level.WARNING,e.getMessage(),e);
			} catch (UnknownHostException e) {				
				MyLogger.log(Level.WARNING,e.getMessage(),e);
			} catch (IOException e) {				
				MyLogger.log(Level.WARNING,e.getMessage(),e);
			}
			
			
			System.out.println("->"+content);
			
		}else {
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

}

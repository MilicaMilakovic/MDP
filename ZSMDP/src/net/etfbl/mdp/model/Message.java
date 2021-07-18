package net.etfbl.mdp.model;

import java.io.File;
import java.io.Serializable;

public class Message implements Serializable {
	
	private String senderUsername;
	private String message;
	private String receiverUsername;
	private int receiverPort;
	// private File file;
	
	public Message() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Message(String senderUsername, String message, String receiverUsername, int receiverPort) {
		super();
		this.senderUsername = senderUsername;
		this.message = message;
		this.receiverUsername = receiverUsername;
		this.receiverPort = receiverPort;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReceiverUsername() {
		return receiverUsername;
	}

	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}

	

	@Override
	public String toString() {
		return "Message [senderUsername=" + senderUsername + ", message=" + message + ", receiverUsername="
				+ receiverUsername + ", receiverPort=" + receiverPort + "]";
	}

	public int getReceiverPort() {
		return receiverPort;
	}

	public void setReceiverPort(int receiverPort) {
		this.receiverPort = receiverPort;
	}

	
	
}

package net.etfbl.mdp.model;

import java.io.Serializable;

public class MyFile implements Serializable {

	private String fileName;
	private byte[] content;
	
	private String senderUsername;	
	private String receiverUsername;
	private int receiverPort;
	
	
	public MyFile(String fileName, byte[] content, String senderUsername, String receiverUsername, int receiverPort) {
		super();
		this.fileName = fileName;
		this.content = content;
		this.senderUsername = senderUsername;
		this.receiverUsername = receiverUsername;
		this.receiverPort = receiverPort;
	}


	public MyFile() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public byte[] getContent() {
		return content;
	}


	public void setContent(byte[] content) {
		this.content = content;
	}


	public String getSenderUsername() {
		return senderUsername;
	}


	public void setSenderUsername(String senderUsername) {
		this.senderUsername = senderUsername;
	}


	public String getReceiverUsername() {
		return receiverUsername;
	}


	public void setReceiverUsername(String receiverUsername) {
		this.receiverUsername = receiverUsername;
	}


	public int getReceiverPort() {
		return receiverPort;
	}


	public void setReceiverPort(int receiverPort) {
		this.receiverPort = receiverPort;
	}
	
	
	
}

package net.etfbl.mdp.model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportInfo {
	
	private static int serialID;
	
	private int id;
	private String fileName;	
	private String sender;
	private String timeStamp;
	private long size;
	
	public ReportInfo(String fileName, String sender, long size) {
		super();
		id = ++serialID;
		this.fileName = fileName;
		this.sender = sender;
		this.size = size;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
		
	}



	public ReportInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

		

	@Override
	public String toString() {
		return "ReportFile [id=" + id + ", fileName=" + fileName + ", sender=" + sender + ", timeStamp=" + timeStamp
				+ ", size=" + size + "]";
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	


	public String getSender() {
		return sender;
	}



	public void setSender(String sender) {
		this.sender = sender;
	}



	public String getTimeStamp() {
		return timeStamp;
	}



	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}



	public long getSize() {
		return size;
	}



	public void setSize(long size) {
		this.size = size;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportInfo other = (ReportInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}

package net.etfbl.mdp.model;

import java.io.Serializable;

public class Report implements Serializable {

	private byte[] reportContent;
	private String reportName;
	
	public Report(byte[] reportContent, String reportName) {
		super();
		this.reportContent = reportContent;
		this.reportName = reportName;
	}

	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}

	public byte[] getReportContent() {
		return reportContent;
	}

	public void setReportContent(byte[] reportContent) {
		this.reportContent = reportContent;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	
}

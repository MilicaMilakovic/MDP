package net.etfbl.mdp.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import net.etfbl.mdp.model.Report;
import net.etfbl.mdp.model.ReportInfo;

public interface ReportInterface extends Remote {
	
	public void uploadReport(String fileName, byte[] fileContent, String sender) throws RemoteException;	
	public Report downloadReport(int id) throws RemoteException;
	public ArrayList<String> getAllReports() throws RemoteException;
}

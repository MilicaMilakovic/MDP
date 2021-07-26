package net.etfbl.mdp.rmi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.Gson;

import net.etfbl.mdp.model.Report;
import net.etfbl.mdp.model.ReportInfo;

public class ReportServer implements ReportInterface {
	
	public static final File reportsDir = new File("./reports");
	public static final String reports = reportsDir.getAbsolutePath();
	
	public static final String PATH = "resources";
	
	public static HashMap<Integer, File> id_file = new HashMap<Integer, File>();
	public static ArrayList<ReportInfo> files = new ArrayList<ReportInfo>();
	
	public ReportServer() throws RemoteException {
		
	}
	
	public void uploadReport(String fileName, byte[] fileContent, String sender) throws RemoteException {
		
		File file = new File(reports+File.separator+fileName);
		System.out.println("Primljen izvjestaj " + fileName);
		
		try {
			Files.write(file.toPath(),fileContent);
			
			ReportInfo reportFile = new ReportInfo(fileName, sender, file.length());
			
			files.add(reportFile);
			id_file.put(reportFile.getId(), file);
			
			Gson gson = new Gson();
			FileWriter out = new FileWriter(new File(reports+File.separator+fileName.replace(".pdf", ".json")));
			out.write(gson.toJson(reportFile));
			out.close();
			
			System.out.println("Izvjestaj " + fileName + " arhiviran.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	 @Override
	public Report downloadReport(int id) throws RemoteException {
		
		 File file = findFile(id); 
		 
		 
		 System.out.println(file);
		 Report report = null;
		 try {
			 byte[] content = Files.readAllBytes(file.toPath());
			 String name = file.getName();
			 
			 report = new Report(content, name);
			 
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
		
		return report;
	}
	 
	 private File findFile(int id) {
		 
		File[] files = reportsDir.listFiles();
		
		File result= null;
			
		ArrayList<String> jsonInfo = new ArrayList<String>();
		String fileName = "";
		Gson gson = new Gson();
		
		for(File file : files)
		{
			if(file.getName().endsWith(".json")) {
				try {
					String content =new String(Files.readString(file.toPath()));
					ReportInfo info = gson.fromJson(content, ReportInfo.class);
					
					if(info.getId() == id)
					{
						fileName = info.getFileName();
						break;
					}				
					
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		if(!fileName.equals(""))
			result = new File(reports+File.separator+fileName);
		
		return result;
	 }
	 
	@Override
	public ArrayList<String> getAllReports() throws RemoteException {
		
		File[] files = reportsDir.listFiles();
		
		ArrayList<String> jsonInfo = new ArrayList<String>();
		
		Gson gson = new Gson();
		
		for(File file : files)
		{
			if(file.getName().endsWith(".json")) {
				try {
					String content =new String(Files.readString(file.toPath()));					
					//ReportInfo info = gson.fromJson(content, ReportInfo.class);
					jsonInfo.add(content);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		}
		
		return jsonInfo;
	}

	public static void main(String[] args) {
		
		System.setProperty("java.security.policy", PATH + File.separator + "server_policyfile.txt");
		if(System.getSecurityManager() == null ) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			ReportServer server = new ReportServer();
			ReportInterface stub = (ReportInterface) UnicastRemoteObject.exportObject(server,0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("ReportServer", stub);
			System.out.println("ReportServer pokrenut");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}

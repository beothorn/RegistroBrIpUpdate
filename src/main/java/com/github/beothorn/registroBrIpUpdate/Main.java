package com.github.beothorn.registroBrIpUpdate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.io.FileUtils;

import com.github.beothorn.registroBrIpUpdate.homeDir.HomeDir;
import com.github.beothorn.registroBrIpUpdate.htmlUnit.IfConfigMe;
import com.github.beothorn.registroBrIpUpdate.htmlUnit.RegistroBr;



public class Main {

	public static void main(String[] args) throws MalformedURLException, IOException{
		if(args.length <= 0 || args.length > 2){
			System.out.println("Usage:\nregistroBr user password");
			System.exit(1);
		}
		
		String user = args[0];
	    String password = args[1];
	    
	    IfConfigMe ifConfigMe = new IfConfigMe();
	    String ip = ifConfigMe.getIp();
	    ifConfigMe.close();
	    System.out.println("Current IP: "+ip);
	    String oldIp = "No previous IP.";
	    File ipFile = new File(HomeDir.homeFolder(),"ip.txt");
	    System.out.println("IP file: "+ipFile.getAbsolutePath());
	    if(ipFile.exists()){
	    	oldIp = FileUtils.readFileToString(ipFile);
	    }
	    System.out.println("Old IP: "+oldIp);
	    
	    if(oldIp.equals(ip)){
	    	System.out.println("IP has not changed. Nothing to do.");
	    }else{
	    	System.out.println("Updating IP...");
	    	updateIp(user, password, ip);
	    	FileUtils.writeStringToFile(ipFile, ip);
	    	System.out.println("IP successfully changed to "+ip);
	    }
	    System.out.println("DONE");
	}

	private static void updateIp(String user, String password, String ip) throws MalformedURLException, IOException {
		RegistroBr registroBr = new RegistroBr();
	    registroBr.updateIp(user, password, ip);
	    registroBr.close();
	}

}

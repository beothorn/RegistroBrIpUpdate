package com.github.beothorn.registroBrIpUpdate.homeDir;

import java.io.File;

public class HomeDir {

	private static final String HOME_DIR = "registroBrUpdate";

	public static final File homeFolder(){
		final File filmeUtilsFolder = new File(System.getProperty("user.home"),"."+HOME_DIR);
		if(!filmeUtilsFolder.exists()){
			filmeUtilsFolder.mkdir();
		}
		return filmeUtilsFolder;
	}
	
}

package com.github.beothorn.registroBrIpUpdate.htmlUnit;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;

public class IfConfigMe {

	private static final String IFCONFIG_ME = "http://ifconfig.me/";
	private WebClient webClient;

	public IfConfigMe() {
		webClient = new WebClient();
	}
	
	public String getIp() throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		TextPage page = webClient.getPage(IFCONFIG_ME+"ip");
		return page.getContent().trim();
	}
	
	public void close(){
		webClient.closeAllWindows();
	}
	
}

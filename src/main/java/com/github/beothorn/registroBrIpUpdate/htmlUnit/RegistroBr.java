package com.github.beothorn.registroBrIpUpdate.htmlUnit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class RegistroBr {

	private static final String REGISTRO_BR = "https://registro.br/";
	private final WebClient webClient;
	
	
	public RegistroBr() {
		webClient = new WebClient(BrowserVersion.FIREFOX_10);
	}
	
	public void close(){
		webClient.closeAllWindows();
	}
	
	public void updateIp(String user,String password, String ip) throws MalformedURLException, IOException{
		final HtmlPage loggedPage = login(user, password);
		boolean notLogged = !loggedPage.asText().contains("Você está em: Registro.br > Sistema > Tela Principal");
		if(notLogged){
			throw new RuntimeException("Logon Error!!");
		}
		HtmlPage domainEditPage = getDomainEditPage(loggedPage);
		changeIp(ip, domainEditPage);
	}

	private void changeIp(String ip, HtmlPage domainEditPage) throws IOException {
		HtmlAnchor deleteOldIpLink = domainEditPage.getHtmlElementById("rr1");
		deleteOldIpLink.click();
		final HtmlSubmitInput save = domainEditPage.getHtmlElementById("salvar");			    
		final HtmlButtonInput add = domainEditPage.getHtmlElementById("addRecord");
		add.click();
		final HtmlTextInput userInput =  domainEditPage.getHtmlElementById("A_10");
		userInput.setValueAttribute(ip);
		save.click();
	}

	private HtmlPage getDomainEditPage(final HtmlPage loggedPage) throws IOException, MalformedURLException {
		String loggedPageHtmlCode = loggedPage.getWebResponse().getContentAsString();
		Document parse = Jsoup.parse(loggedPageHtmlCode);
		Elements domainConfigLinkElement = parse.select("td.domainList > a");
		String domainConfigLink = domainConfigLinkElement.attr("href");
		HtmlPage domainConfigLinkPage = webClient.getPage(domainConfigLink);
		HtmlForm htmlForm = domainConfigLinkPage.getHtmlElementById("domainForm");
		final HtmlSubmitInput button = htmlForm.getInputByName("editZone");
		HtmlPage domainEditPage = button.click();
		return domainEditPage;
	}

	private HtmlPage login(String user, String password) throws IOException, MalformedURLException {
		final HtmlPage mainPage = webClient.getPage(REGISTRO_BR);
	    HtmlForm loginForm = getLoginForm(mainPage);
		final HtmlSubmitInput button = loginForm.getInputByValue("ENTRAR");
	    final HtmlTextInput userInput = loginForm.getInputByName("handle");
	    final HtmlPasswordInput passwordInput = loginForm.getInputByName("passwd");

		userInput.setValueAttribute(user);
		passwordInput.setValueAttribute(password);

	    final HtmlPage loggedPage = button.click();
		return loggedPage;
	}

	private static HtmlForm getLoginForm(HtmlPage mainPage) {
		List<HtmlForm> forms = mainPage.getForms();
		for (HtmlForm htmlForm : forms) {
			if(htmlForm.getActionAttribute().equals("https://registro.br/cgi-bin/nicbr/stini")){
				return htmlForm;
			}
		}
		throw new RuntimeException("Login form not found");
	}
	
}

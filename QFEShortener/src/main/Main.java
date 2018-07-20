package main;

import java.io.IOException;

import file.FileManager;
import urls.URLManager;
import webserver.WebServer;

public class Main {
	private static WebServer ws;
	private static URLManager um = new URLManager();

	public static void main(String[] args) {
		try {
			FileManager.setup();
			ws = new WebServer(80);
			ws.start();
			
			um = new URLManager();
			um.addDefaultBlockedURL("setup");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static URLManager getURLManager() {
		return um;
	}

}

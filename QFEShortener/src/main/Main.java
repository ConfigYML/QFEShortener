package main;

import java.io.IOException;

import file.FileManager;
import webserver.WebServer;

public class Main {
	private static WebServer ws;

	public static void main(String[] args) {
		try {
			FileManager.setup();
			ws = new WebServer(80);
			ws.start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}

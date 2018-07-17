package main;

import java.io.IOException;

import webserver.WebServer;

public class Main {

	public static void main(String[] args) {
		WebServer ws = new WebServer(80);
		try {
			ws.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

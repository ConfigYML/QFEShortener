package webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import file.FileManager;

public class AdminHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		if(!ex.getRequestURI().toString().equalsIgnoreCase("/favicon.ico")) {
			Scanner sc = new Scanner(FileManager.getAdminFile());
			String response = "";
			while (sc.hasNextLine()) {
				response = response + sc.nextLine();
			}
			ex.sendResponseHeaders(200, response.length());
			OutputStream os = ex.getResponseBody();
			os.write(response.getBytes());
			os.close();
			sc.close();
		}
	}

}

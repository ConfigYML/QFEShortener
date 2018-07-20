package webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import file.FileManager;
import main.Main;

public class RequestHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		if (!ex.getRequestURI().toString().equalsIgnoreCase("/favicon.ico")) {
			if (!Main.getURLManager().isBlocked(ex.getRequestURI().toString().replace("/", ""))) {
				if (Main.getURLManager().containsURL(ex.getRequestURI().toString().replace("/", ""))) {
					Scanner sc = new Scanner(FileManager.getForwardingFile());
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					response = response.replace("%url%",
							"http://" + Main.getURLManager().getURL(ex.getRequestURI().toString().replace("/", "")));
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				} else {
					System.out.println("else");
					Scanner sc = new Scanner(FileManager.getFailureFile());
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
			} else {
				Scanner sc = new Scanner(FileManager.getFileFromName(ex.getRequestURI().toString().replace("/", "")));
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

}

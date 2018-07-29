package webserver;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import file.FileManager;
import main.Main;
import urls.Shortener;

public class RequestHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		String request = ex.getRequestURI().toString().replace("/", "");
		if (!request.equalsIgnoreCase("favicon.ico")) {
			if (!Main.getURLManager().isBlocked(request)) {
				if (Main.getURLManager().containsURL(request)) {
					Scanner sc = new Scanner(FileManager.getForwardingFile());
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					response = response.replace("%url%",
							Main.getURLManager().getURL(request));
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				} else {
					if (request.startsWith("add")) {
						String urlToAdd = request.replaceFirst("add", "");
						String newURL = "";
						if(!urlToAdd.startsWith("https://") || !urlToAdd.startsWith("http://")) {
							newURL = Shortener.shortURL("http://" + urlToAdd);
						} else {
							newURL = Shortener.shortURL(urlToAdd);
						}
						
						System.out.println(newURL);
						
						Scanner sc = new Scanner(FileManager.getURLAddedFile());
						String response = "";
						while (sc.hasNextLine()) {
							response = response + sc.nextLine();
						}
						response = response.replace("%newURL%",
								Main.getConfiguration().getEncryption() + Main.getConfiguration().getLocalDomain()+ "/" + newURL);
						ex.sendResponseHeaders(200, response.length());
						OutputStream os = ex.getResponseBody();
						os.write(response.getBytes());
						os.close();
						sc.close();
					} else {
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
				}
			} else {
				File f = FileManager.getFileFromName(ex.getRequestURI().toString().replace("/", ""));
				if (f != null) {
					Scanner sc = new Scanner(f);
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				} else {
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
			}
		}

	}

}

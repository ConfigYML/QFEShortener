package webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import file.FileManager;
import urls.URLManager;

public class RequestHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		System.out.println(ex.getRequestURI().toString());
		if(URLManager.containsURL(ex.getRequestURI().toString().replace("/", ""))) {
			Scanner sc = new Scanner(FileManager.getForwardingFile());
			String response = "";
			while(sc.hasNextLine()) {
				response = response + sc.nextLine();
			}
			response = response.replace("%url%", "http://" + URLManager.getURL(ex.getRequestURI().toString().replace("/", "")));
			ex.sendResponseHeaders(200, response.length());
			OutputStream os = ex.getResponseBody();
	        os.write(response.getBytes());
	        os.close();
	        sc.close();
		} else {
			System.out.println("else");
			Scanner sc = new Scanner(FileManager.getFailureFile());
			String response = "";
			while(sc.hasNextLine()) {
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

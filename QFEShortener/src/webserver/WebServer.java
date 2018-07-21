package webserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import main.Main;

public class WebServer {
	private final int port;
	HttpServer htps;
	public WebServer(int port) {
		this.port = port;
	}
	public int getPort() {
		return port;
	}
	public void start() throws IOException {
		htps = HttpServer.create(new InetSocketAddress(port), 0);
		HttpContext cc = htps.createContext("/admin", new AdminHandler());
		cc.setAuthenticator(new BasicAuthenticator("test") {
			
			@Override
			public boolean checkCredentials(String user, String pwd) {
				return user.equals(Main.getConfiguration().getUsername()) && pwd.equals(Main.getConfiguration().getPassword());
			}
		});
		htps.createContext("/", new RequestHandler());
		htps.start();
		System.out.println("> QFEShortener started!");
	}
	public void stop() {
		htps.stop(0);
		System.out.println("> QFEShortener stopped!");
	}
}

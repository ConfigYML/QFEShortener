package webserver;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

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
		htps.createContext("/", new RequestHandler());
		htps.start();
		System.out.println("> QFEShortener started!");
	}
	public void stop() {
		htps.stop(0);
		System.out.println("> QFEShortener stopped!");
	}
}

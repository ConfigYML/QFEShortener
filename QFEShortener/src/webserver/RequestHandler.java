package webserver;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		String response = "";
		ex.sendResponseHeaders(200, response.length());
		OutputStream os = ex.getResponseBody();
        os.write(response.getBytes());
        os.close();
	}

}

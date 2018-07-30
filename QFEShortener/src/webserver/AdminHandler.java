package webserver;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import errors.Errors;
import file.FileManager;
import main.Main;

public class AdminHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange ex) throws IOException {
		String request = ex.getRequestURI().toString().replace("/", "");
		if (!request.equalsIgnoreCase("favicon.ico")) {
			if (request.equalsIgnoreCase("admin")) {
				sendExchange(ex, FileManager.getAdminFile(), "%stats%", getStatistics());
			} else if (request.startsWith("admin")) {
				String requestRaw = request.replaceFirst("admin", "");
				if (requestRaw.startsWith("localdomain=")) {
					Main.getConfiguration().setLocalDomain(requestRaw.replace("localdomain=", ""));
					sendExchange(ex, FileManager.getInformationFile(), "%information%",
							"You changed the local domain to " + requestRaw.replace("localdomain=", "") + ".");
				} else if (requestRaw.startsWith("username=")) {
					Main.getConfiguration().setUsername(requestRaw.replace("username=", ""));
					sendExchange(ex, FileManager.getInformationFile(), "%information%",
							"You changed the username to " + requestRaw.replace("localdomain=", "") + ".");
				} else if (requestRaw.startsWith("password=")) {
					Main.getConfiguration().setPassword(requestRaw.replace("password=", ""));
					sendExchange(ex, FileManager.getInformationFile(), "%information%",
							"You changed the password to " + requestRaw.replace("password=", "") + ".");
				} else if (requestRaw.startsWith("encryption=")) {
					Main.getConfiguration().setEncryption(Integer.parseInt(requestRaw.replace("encryption=", "")));
					if (Integer.parseInt(requestRaw.replace("encryption=", "")) == 0) {
						sendExchange(ex, FileManager.getInformationFile(), "%information%",
								"You changed the encryption to http://.");
					} else {
						sendExchange(ex, FileManager.getInformationFile(), "%information%",
								"You changed the encryption to https://.");
					}
				} else if (requestRaw.startsWith("port=")) {
					Main.getConfiguration().setPort(Integer.parseInt(requestRaw.replace("port=", "")));
					sendExchange(ex, FileManager.getInformationFile(), "%information%",
							"You changed the encryption to " + requestRaw.replace("port=", "") + ".");
				} else if (requestRaw.startsWith("urllimit=")) {
					Main.getConfiguration().setURLlimit(Integer.parseInt(requestRaw.replace("urllimit=", "")));
					sendExchange(ex, FileManager.getInformationFile(), "%information%",
							"You changed the URL-limit to " + requestRaw.replace("urllimit=", "") + ".");
				} else if (requestRaw.startsWith("cupro=")) {
					if (requestRaw.contains("val=")) {
						String raw = requestRaw.replaceFirst("cupro=", "");
						String[] keyVal = raw.split("val=");
						if (keyVal.length != 2) {
							sendFailure(ex, Errors.CUSTOM_CONFIGURATION_INCORRECT_FORMAT);
						} else {
							Main.getConfiguration().addProperty(keyVal[0], keyVal[1]);
							Main.getConfiguration().save();
							sendExchange(ex, FileManager.getInformationFile(), "%information%",
									"You added a custom property named '" + keyVal[0] + "' with the value '" + keyVal[1]
											+ "'.");
						}
					} else {
						sendFailure(ex, Errors.CUSTOM_CONFIGURATION_MISSING_VALUE);
					}
				} else if (requestRaw.startsWith("getcupro=")) {
					if (Main.getConfiguration().getProperties().containsKey(requestRaw.replaceFirst("getcupro=", ""))) {
						sendExchange(ex, FileManager.getInformationFile(), "%information%",
								"The property '" + requestRaw.replaceFirst("getcupro=", "") + "' has the value '"
										+ Main.getConfiguration().getProperty(requestRaw.replaceFirst("getcupro=", ""))
										+ "'.");
					} else {
						sendFailure(ex, Errors.CUSTOM_CONFIGURATION_NO_SUCH_PROPERTY);
					}
				} else if (requestRaw.startsWith("remove=")) {
					if (Main.getURLManager().containsURL(requestRaw.replaceFirst("remove=", ""))) {
						Main.getURLManager().removeURL(requestRaw.replaceFirst("remove=", ""));
						sendExchange(ex, FileManager.getInformationFile(), "%information%",
								"The URL '" + requestRaw.replaceFirst("remove=", "")
										+ "' is now removed. It is no longer accessable.");
					} else {
						sendFailure(ex, Errors.NO_SUCH_URL);
					}
				} else {
					sendFailure(ex, Errors.CONFIGURATION_NO_PROPERTY_FOUND);
				}
			} else {
				sendFailure(ex, Errors.NO_TARGET_FOUND);
			}
		}
	}

	private String getStatistics() {
		String toRtn = "";

		toRtn += "URLs used: " + Main.getURLManager().getURLSadded() + " / " + Main.getConfiguration().getURLlimit()
				+ "<br>";
		toRtn += "Current username: " + Main.getConfiguration().getUsername() + "<br>";
		toRtn += "Current password: " + Main.getConfiguration().getPassword() + "<br>";
		if (Main.getConfiguration().getEncryption().equalsIgnoreCase("https://")) {
			toRtn += "Use SSL: true<br>";
		} else {
			toRtn += "Use SSL: false<br>";
		}
		toRtn += "Current domain displayed: " + Main.getConfiguration().getLocalDomain() + "<br>";
		toRtn += "Current Port: " + Main.getConfiguration().getPort() + "<br>";

		return toRtn;
	}

	private static void sendExchange(HttpExchange ex, File f, String toReplace, String replacement) throws IOException {
		Scanner sc = new Scanner(f);
		String response = "";
		while (sc.hasNextLine()) {
			response = response + sc.nextLine();
		}
		response = response.replace(toReplace, replacement);
		ex.sendResponseHeaders(200, response.length());
		OutputStream os = ex.getResponseBody();
		os.write(response.getBytes());
		os.close();
		sc.close();
	}

	private static void sendFailure(HttpExchange ex, Errors er) throws IOException {
		Scanner sc = new Scanner(FileManager.getFailureFile());
		String response = "";
		while (sc.hasNextLine()) {
			response = response + sc.nextLine();
		}
		response = response.replace("%error%", er.toString());
		ex.sendResponseHeaders(200, response.length());
		OutputStream os = ex.getResponseBody();
		os.write(response.getBytes());
		os.close();
		sc.close();
	}
}
package webserver;

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
				Scanner sc = new Scanner(FileManager.getAdminFile());
				String response = "";
				while (sc.hasNextLine()) {
					response = response + sc.nextLine();
				}
				response = response.replace("%urls%",
						Main.getURLManager().getURLSadded() + " / " + Main.getConfiguration().getURLlimit());
				ex.sendResponseHeaders(200, response.length());
				OutputStream os = ex.getResponseBody();
				os.write(response.getBytes());
				os.close();
				sc.close();
			} else if (request.startsWith("admin")) {
				String requestRaw = request.replaceFirst("admin", "");
				if (requestRaw.startsWith("localdomain=")) {
					Main.getConfiguration().setLocalDomain(requestRaw.replace("localdomain=", ""));
					Scanner sc = new Scanner(FileManager.getInformationFile());
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					response = response.replace("%information%",
							"You changed the local domain to " + requestRaw.replace("localdomain=", "") + ".");
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				} else if (requestRaw.startsWith("username=")) {
					Main.getConfiguration().setUsername(requestRaw.replace("username=", ""));
					Scanner sc = new Scanner(FileManager.getInformationFile());
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					response = response.replace("%information%",
							"You changed the username to " + requestRaw.replace("localdomain=", "") + ".");
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				} else if (requestRaw.startsWith("password=")) {
					Main.getConfiguration().setPassword(requestRaw.replace("password=", ""));
					Scanner sc = new Scanner(FileManager.getInformationFile());
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					response = response.replace("%information%",
							"You changed the password to " + requestRaw.replace("password=", "") + ".");
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				} else if (requestRaw.startsWith("encryption=")) {
					Main.getConfiguration().setEncryption(Integer.parseInt(requestRaw.replace("encryption=", "")));
					Scanner sc = new Scanner(FileManager.getInformationFile());
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					if (Integer.parseInt(requestRaw.replace("encryption=", "")) == 0) {
						response = response.replace("%information%", "You changed the encryption to http://.");
					} else {
						response = response.replace("%information%", "You changed the encryption to https://.");
					}
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				} else if (requestRaw.startsWith("port=")) {
					Main.getConfiguration().setPort(Integer.parseInt(requestRaw.replace("port=", "")));
					Scanner sc = new Scanner(FileManager.getInformationFile());
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					response = response.replace("%information%",
							"You changed the encryption to " + requestRaw.replace("port=", "") + ".");
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				} else if (requestRaw.startsWith("cupro=")) {
					if (requestRaw.contains("val=")) {
						String raw = requestRaw.replaceFirst("cupro=", "");
						String[] keyVal = raw.split("val=");
						if (keyVal.length != 2) {
							Scanner sc = new Scanner(FileManager.getFailureFile());
							String response = "";
							while (sc.hasNextLine()) {
								response = response + sc.nextLine();
							}
							response = response.replace("%error%",
									Errors.CUSTOM_CONFIGURATION_INCORRECT_FORMAT.toString());
							ex.sendResponseHeaders(200, response.length());
							OutputStream os = ex.getResponseBody();
							os.write(response.getBytes());
							os.close();
							sc.close();
						} else {
							Main.getConfiguration().addProperty(keyVal[0], keyVal[1]);
							Main.getConfiguration().save();
							Scanner sc = new Scanner(FileManager.getInformationFile());
							String response = "";
							while (sc.hasNextLine()) {
								response = response + sc.nextLine();
							}
							response = response.replace("%information%", "You added a custom property named '"
									+ keyVal[0] + "' with the value '" + keyVal[1] + "'.");
							ex.sendResponseHeaders(200, response.length());
							OutputStream os = ex.getResponseBody();
							os.write(response.getBytes());
							os.close();
							sc.close();
						}
					} else {
						Scanner sc = new Scanner(FileManager.getFailureFile());
						String response = "";
						while (sc.hasNextLine()) {
							response = response + sc.nextLine();
						}
						response = response.replace("%error%", Errors.CUSTOM_CONFIGURATION_MISSING_VALUE.toString());
						ex.sendResponseHeaders(200, response.length());
						OutputStream os = ex.getResponseBody();
						os.write(response.getBytes());
						os.close();
						sc.close();
					}
				} else if (requestRaw.startsWith("getcupro=")) {
					if (Main.getConfiguration().getProperties().containsKey(requestRaw.replaceFirst("getcupro=", ""))) {
						Scanner sc = new Scanner(FileManager.getInformationFile());
						String response = "";
						while (sc.hasNextLine()) {
							response = response + sc.nextLine();
						}
						System.out.println(response);
						response = response.replace("%information%",
								"The property '" + requestRaw.replaceFirst("getcupro=", "") + "' has the value '" + Main.getConfiguration().getProperty(requestRaw.replaceFirst("getcupro=", "")) + "'.");
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
						response = response.replace("%error%", Errors.CUSTOM_CONFIGURATION_NO_SUCH_PROPERTY.toString());
						ex.sendResponseHeaders(200, response.length());
						OutputStream os = ex.getResponseBody();
						os.write(response.getBytes());
						os.close();
						sc.close();
					}
				} else {
					Scanner sc = new Scanner(FileManager.getFailureFile());
					String response = "";
					while (sc.hasNextLine()) {
						response = response + sc.nextLine();
					}
					response = response.replace("%error%", Errors.CONFIGURATION_NO_PROPERTY_FOUND.toString());
					ex.sendResponseHeaders(200, response.length());
					OutputStream os = ex.getResponseBody();
					os.write(response.getBytes());
					os.close();
					sc.close();
				}
			} else {
				Scanner sc = new Scanner(FileManager.getFailureFile());
				String response = "";
				while (sc.hasNextLine()) {
					response = response + sc.nextLine();
				}
				response = response.replace("%error%", Errors.NO_TARGET_FOUND.toString());
				ex.sendResponseHeaders(200, response.length());
				OutputStream os = ex.getResponseBody();
				os.write(response.getBytes());
				os.close();
				sc.close();
			}
		}
	}

}

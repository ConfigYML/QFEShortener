package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import file.FileManager;

public class Configuration {
	
	private static Properties prop;
	
	public Configuration() {
		prop = new Properties();
		if(!FileManager.getConfigFolder().exists()) {
			FileManager.getConfigFolder().mkdir();
		}
		File f = new File(FileManager.getConfigFolder().getPath() + "/Configuration.conf");
		if(!f.exists()) {
			try {
				f.createNewFile();
				prop.load(new FileInputStream(f));
				prop.setProperty("Port", "80");
				setPassword("qfeshortener");
				setUsername("root");
				setEncryption(0);
				setLocalDomain("YourDomain.com");
				setURLlimit(1000);
				save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				prop.load(new FileInputStream(f));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setPort(int port) {
		prop.setProperty("Port", "80");
		save();
	}
	public int getPort() {
		if(prop.containsKey("Port")) {
			return Integer.valueOf(prop.getProperty("Port"));
		} else {
			return 80;
		}
	}
	public void save() {
		try {
			prop.store(new FileOutputStream(new File(FileManager.getConfigFolder().getPath() + "/Configuration.conf")), "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addProperty(String key, String value) {
		prop.setProperty(key, value);
		save();
	}
	public String getProperty(String key) {
		if(prop.containsKey(key)) {
			return prop.getProperty(key);
		} else {
			return null;
		}
	}
	public void setUsername(String username) {
		prop.setProperty("Username", username);
		save();
	}
	public void setPassword(String password) {
		prop.setProperty("Password", password);
		save();
	}
	public String getUsername() {
		if(prop.containsKey("Username")) {
			return prop.getProperty("Username");
		} else {
			return "root";
		}
	}
	public String getPassword() {
		if(prop.containsKey("Password")) {
			return prop.getProperty("Password");
		} else {
			return "qfeshortener";
		}
	}

	public String getEncryption() {
		if(prop.containsKey("Encryption")) {
			if(prop.getProperty("Encryption").equalsIgnoreCase("0")) {
				return "http://";
			} else if(prop.getProperty("Encryption").equalsIgnoreCase("1")) {
				return "https://";
			} else {
				return "http://";
			}
		} else {
			return "http://";
		}
	}
	public void setEncryption(int level) {
		prop.setProperty("Encryption", level + "");
		save();
	}

	public String getLocalDomain() {
		if(prop.containsKey("LocalDomain")) {
			return prop.getProperty("LocalDomain");
		} else {
			return "YourDomain.com";
		}
	}
	public void setLocalDomain(String localDomain) {
		prop.setProperty("LocalDomain", localDomain);
		save();
	}
	public void setURLlimit(int limit) {
		prop.setProperty("URLlimit", limit + "");
		save();
	}
	public int getURLlimit() {
		try {
			if(prop.containsKey("URLlimit")) {
				return Integer.parseInt(prop.getProperty("URLlimit"));
			} else {
				return 1000;
			}
		} catch(NumberFormatException ex) {
			System.out.println("> The URL-limit cannot be a string. It has to be an Integer.");
		}
		return 1000;
	}
}

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
		File f = new File(FileManager.getHTMLFolder().getPath() + "/Configuration.conf");
		if(!f.exists()) {
			try {
				f.createNewFile();
				prop.load(new FileInputStream(f));
				prop.setProperty("Port", "80");
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
			prop.store(new FileOutputStream(new File(FileManager.getHTMLFolder().getPath() + "/Configuration.conf")), "");
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
}

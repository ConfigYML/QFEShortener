package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileManager {
	private static HashMap<String, File> files = new HashMap<>();
	public static File getHTMLFolder() {
		return new File(System.getProperty("user.home") + "/QFE");
	}
	public static void setup() throws IOException {
		File dir = new File(System.getProperty("user.home") + "/QFE");
		File forwarding = new File(getHTMLFolder().getPath() + "/Forwarding.html");
		File failure = new File(getHTMLFolder().getPath() + "/Failure.html");
		if(!dir.exists()) {
			dir.mkdir();
			forwarding.createNewFile();
			failure.createNewFile();
			
			FileWriter fw = new FileWriter(forwarding);
			fw.write("<html><head><meta http-equiv=\"refresh\" content=\"0; URL=%url%\"><title>Forwarding....</title></head><body>You will be forwarded to %url%</body></html>");
			fw.flush();
			fw.close();
			
			FileWriter fw1 = new FileWriter(failure);
			fw1.write("<html><head><title>Error!</title></head><body>The requested shortened url isn't registered yet!</body></html>");
			fw1.flush();
			fw1.close();
			
		}
		files.put("forwarding", forwarding);
		files.put("failure", failure);
		files.put("setup", failure);
	}
	public static File getForwardingFile() {
		return new File(getHTMLFolder().getPath() + "/Forwarding.html");
	}
	public static File getFailureFile() {
		return new File(getHTMLFolder().getPath() + "/Failure.html");
	}
	public static File getFileFromName(String name) {
		if(files.containsKey(name.toLowerCase())) {
			return files.get(name.toLowerCase());
		} else {
			return null;
		}
	}
	public static boolean addToFiles(String name, File f) {
		if(!files.containsKey(name.toLowerCase())) {
			files.put(name.toLowerCase(), f);
			return true;
		} else {
			return false;
		}
	}
}

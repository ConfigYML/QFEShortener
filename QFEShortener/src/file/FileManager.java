package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class FileManager {
	private static HashMap<String, File> files = new HashMap<>();

	public static File getConfigFolder() {
		return new File(System.getProperty("user.home") + "/QFEConf");
	}

	public static File getHTMLFolder() {
		return new File(System.getProperty("user.home") + "/QFE");
	}

	public static void setup() throws IOException {
		File dir = new File(System.getProperty("user.home") + "/QFE");
		File forwarding = new File(getHTMLFolder().getPath() + "/Forwarding.html");
		File failure = new File(getHTMLFolder().getPath() + "/Failure.html");
		File admin = new File(getHTMLFolder().getPath() + "/Admin.html");
		File url_added = new File(getHTMLFolder().getPath() + "/URLAdded.html");
		File information = new File(getHTMLFolder().getPath() + "/Information.html");
		if (!dir.exists()) {
			dir.mkdir();
			forwarding.createNewFile();
			failure.createNewFile();
			admin.createNewFile();
			url_added.createNewFile();
			
			FileWriter fw = new FileWriter(forwarding);
			fw.write(
					"<html><head><meta http-equiv=\"refresh\" content=\"0; URL=%url%\"><title>Forwarding....</title></head><body>You will be forwarded to %url%</body></html>");
			fw.flush();
			fw.close();

			FileWriter fw1 = new FileWriter(failure);
			fw1.write(
					"<html><head><title>Error!</title></head><body>Error: %error%</body></html>");
			fw1.flush();
			fw1.close();

			FileWriter fw2 = new FileWriter(admin);
			fw2.write(
					"<html><head><title>Admin</title></head><body>If you see this, you have successfully logged into the Admin panel. %urls%</body></html>");
			fw2.flush();
			fw2.close();
			
			FileWriter fw3 = new FileWriter(url_added);
			fw3.write("<html><head><title>Add URL</title></head><body>Your new URL is %newURL%</body></html>");
			fw3.flush();
			fw3.close();
			
			FileWriter fw4 = new FileWriter(information);
			fw4.write("<html><head><title>Information</title></head><body>%information%</body></html>");
			fw4.flush();
			fw4.close();
		}
		files.put("forwarding", forwarding);
		files.put("failure", failure);
		files.put("admin", admin);
		files.put("urladded", url_added);
		files.put("information", information);
	}

	public static File getForwardingFile() {
		return new File(getHTMLFolder().getPath() + "/Forwarding.html");
	}

	public static File getFailureFile() {
		return new File(getHTMLFolder().getPath() + "/Failure.html");
	}

	public static File getFileFromName(String name) {
		if (files.containsKey(name.toLowerCase())) {
			return files.get(name.toLowerCase());
		} else {
			return null;
		}
	}

	public static boolean addToFiles(String name, File f) {
		if (!files.containsKey(name.toLowerCase())) {
			files.put(name.toLowerCase(), f);
			return true;
		} else {
			return false;
		}
	}

	public static File getAdminFile() {
		return new File(getHTMLFolder().getPath() + "/Admin.html");
	}

	public static File getURLAddedFile() {
		return new File(getHTMLFolder().getPath() + "/URLAdded.html");
	}
	public static File getInformationFile() {
		return new File(getHTMLFolder().getPath() + "/Information.html");
	}
}

package file;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

import main.Main;

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
		File start = new File(getHTMLFolder().getPath() + "/Start.html");
		if (!dir.exists()) {
			dir.mkdir();
			CopyOption[] options = new CopyOption[] {StandardCopyOption.REPLACE_EXISTING};
			Files.copy(Main.class.getResourceAsStream("/html_files/Forwarding.html"), forwarding.toPath(), options);
			Files.copy(Main.class.getResourceAsStream("/html_files/Failure.html"), failure.toPath(), options);
			Files.copy(Main.class.getResourceAsStream("/html_files/Admin.html"), admin.toPath(), options);
			Files.copy(Main.class.getResourceAsStream("/html_files/URLAdded.html"), url_added.toPath(), options);
			Files.copy(Main.class.getResourceAsStream("/html_files/Information.html"), information.toPath(), options);
			Files.copy(Main.class.getResourceAsStream("/html_files/Start.html"), start.toPath(), options);
		}
		files.put("forwarding", forwarding);
		files.put("failure", failure);
		files.put("admin", admin);
		files.put("urladded", url_added);
		files.put("information", information);
		files.put("start", start);
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
	public static File getStartFile() {
		return new File(getHTMLFolder().getPath() + "/Start.html");
	}
}

package file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
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
			fw.write("<html><head><meta http-equiv=\"refresh\" content=\"5; URL=%url%\"><title>Forwarding....</title></head><body>You will be forwarded to %url%</body></html>");
			fw.flush();
			fw.close();
		}
		
	}
	public static File getForwardingFile() {
		return new File(getHTMLFolder().getPath() + "/Forwarding.html");
	}
	public static File getFailureFile() {
		return new File(getHTMLFolder().getPath() + "/Failure.html");
	}
}

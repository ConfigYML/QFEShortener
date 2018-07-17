package urls;

public class Shortener {
	public static void shortURL(String formerURL) {
		String newurl = "";
		//TODO: Add algorithm
		if(!URLManager.containsURL(newurl)) {
			URLManager.addURL(formerURL, newurl);
		} else {
			shortURL(formerURL);
		}
	}
}

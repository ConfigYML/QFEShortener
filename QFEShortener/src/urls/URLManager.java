package urls;

import java.util.HashMap;

public class URLManager {
	private static HashMap<String, String> urls = new HashMap<>();
	
	public static void addURL(String formerURL, String newURL) {
		urls.put(newURL, formerURL);
	}
	public static void removeURL(String newURL) {
		urls.remove(newURL);
	}
	public static boolean containsURL(String newURL) {
		return urls.containsKey(newURL);
	}
}

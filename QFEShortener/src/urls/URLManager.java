package urls;

import java.util.ArrayList;
import java.util.HashMap;

public class URLManager {
	private static HashMap<String, String> urls = new HashMap<>();
	private static ArrayList<String> blocked = new ArrayList<>();
	
	public void addURL(String formerURL, String newURL) {
		urls.put(newURL, formerURL);
	}
	public void removeURL(String newURL) {
		urls.remove(newURL);
	}
	public boolean containsURL(String newURL) {
		return urls.containsKey(newURL);
	}
	public String getURL(String newURL) {
		return urls.get(newURL);
	}
	
	
	public void addDefaultBlockedURL(String url) {
		blocked.add(url);
	}
	public boolean isBlocked(String url) {
		return blocked.contains(url);
	}
}

package urls;

import java.util.Random;

import main.Main;

public class Shortener {
	private static final String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
			"m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1",
			"2", "3", "4", "5", "6", "7", "8", "9", "!", "&", "=", "+", "-" };

	public static String shortURL(String formerURL) {
		String newurl = "";
		for (int i = 0; i < 12; i++) {
			newurl = newurl + chars[new Random().nextInt(chars.length)];
		}
		if (!Main.getURLManager().containsURL(newurl) || !Main.getURLManager().isBlocked(newurl)) {
			Main.getURLManager().addURL(formerURL, newurl);
		} else {
			shortURL(formerURL);
		}
		return newurl;
	}
}

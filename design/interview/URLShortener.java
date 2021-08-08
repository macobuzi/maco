import javax.swing.JOptionPane;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


public class URLShortener {

  public static String generate(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(input.getBytes());
      byte[] enc = md.digest();
	  System.out.printf("hash = %s\n", new String(enc));
      String md5Sum = Base64.getEncoder().encodeToString(enc);
      return md5Sum;
    } catch (NoSuchAlgorithmException nsae) {
      System.out.println(nsae.getMessage());
      return null;
    }
  }

  public static void main(String[] args) {
	  String origin = args[0];
	  String url = generate(origin);
	  System.out.printf("shorten url = %s (size = %d)\n", url, url.length());
  }
}

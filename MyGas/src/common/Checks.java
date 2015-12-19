package common;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Checks {

	public Checks() {
		// TODO Auto-generated constructor stub
	}

	public static boolean isValidEmailAddress(String email) {
		   boolean result = true;
		   try {
		      InternetAddress emailAddr = new InternetAddress(email);
		      emailAddr.validate();
		   } catch (AddressException ex) {
		      result = false;
		   }
		   return result;
		}
	
	public static boolean isValidPassword(String password, String passwordValidate) {   
		   if (password.equals(passwordValidate)) return true;
		   return false;
		}	
}

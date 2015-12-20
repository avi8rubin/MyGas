package common;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Checks {

	public Checks() {
		// TODO Auto-generated constructor stub
	}

// MarketingRepresentative
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
	
	public static boolean isAllFieldsFilled(String field1, String field2, String field3 ) {	
		if (field1.equals("")||field2.equals("")||field3.equals(""))
			return false;
		return true;
	}
	
	// Litaf
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return if dates are valid
	 */
	public static boolean isDateValid(String startDate, String endDate){

		if(startDate==null || endDate==null)
			return false;
		String startDay=startDate.substring(0, 2);
		String EndDay=endDate.substring(0, 2);

		String startMonth=startDate.substring(3, 5);
		String EndMonth=endDate.substring(3, 5);

		String startYear=startDate.substring(8, 10);
		String EndYear=endDate.substring(8, 10);
		
		if(Integer.parseInt(EndYear)<Integer.parseInt(startYear))
			return false;
		else if(Integer.parseInt(EndMonth)<Integer.parseInt(startMonth)){
			if(Integer.parseInt(EndYear)==Integer.parseInt(startYear))
				return false;
			}
		else if(Integer.parseInt(EndDay)<Integer.parseInt(startDay)){
			if(Integer.parseInt(EndMonth)==Integer.parseInt(startMonth)){
				if(Integer.parseInt(EndYear)==Integer.parseInt(startYear))
					return false;
			}
		}
		return true;
	}
}

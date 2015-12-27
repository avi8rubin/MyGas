package common;

import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

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
	
	/**
	 * Check if the entered date is a valid date
	 * @param startDate
	 * @param endDate
	 * @return if dates are valid
	 * @author Litaf
	 */
	public static boolean isDateValid(String startDate, String endDate){

		if(startDate.equals("") || endDate.equals(""))
			return false;
		String startDay=startDate.substring(0, 2);
		String EndDay=endDate.substring(0, 2);

		String startMonth=startDate.substring(3, 5);
		String EndMonth=endDate.substring(3, 5);

		String startYear=startDate.substring(6,7);
		String EndYear=endDate.substring(6,7);
		
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
	public static boolean isNumeric(String str)  
	{ 
		str=str.replace("-","");
	  try  
	  {  
	    int d = Integer.parseInt(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
	
	/**
	 * check if string is a float number
	 * @param String
	 * @author Litaf
	 */
	public static boolean isFloat(String str)  
	{ 
		try
		{
		if(Float.parseFloat(str)<0)
			  return false;
		}catch(NumberFormatException e){
		  return false;
		}
		return true;
	}
	
	
	/**
	 * check if Date for delivery is valid
	 * @param String
	 * @author Litaf
	 */
	public static boolean isDateValid(String deliveryDate)  
	{ 
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
		Date date = new Date();
		String currDate=dateFormat.format(new Date()).toString();	
		if(isDateValid(currDate,deliveryDate))
			return true;
		return false;  
	}
	
	/**
	 * check if Time of delivery is valid
	 * @param String
	 * @author Litaf
	 */
	public static boolean isTimeValid(String deliveryTime, String deliveryDate)  
	{ 
		DateFormat TimeFormat = new SimpleDateFormat("HH:mm");
		DateFormat DateFormat = new SimpleDateFormat("dd/MM/yy");

		String currTime=TimeFormat.format(new Date()).toString();	
		String currDate=DateFormat.format(new Date()).toString();	
		
		try{
			
			int DeliveryHour=Integer.parseInt(deliveryTime.substring(0,2));
			int DeliveryMin=Integer.parseInt(deliveryTime.substring(3,5));
			//check if time inserted is valid
			if( DeliveryHour>=0 && DeliveryHour<=24 && 
				DeliveryMin>=0 && DeliveryMin<=59){
				//check time inserted with current time for validation
				int currHour=Integer.parseInt(currTime.substring(0,2));
				int currMin=Integer.parseInt(currTime.substring(3,5));
				if(DeliveryHour >= currHour){
					return true;
				}
				else if(DeliveryHour == currHour){
					if(DeliveryMin>=currMin)
						return true;
				}
			}
		}
		catch(NumberFormatException e){
			return false;
		}

		return false;  
	}
	
	
	/**
	 * Resize the column of JTable to the ultimate size.
	 * @param table
	 * @author Ohad
	 */
	public static void resizeColumnWidth(JTable table) {
	    final TableColumnModel columnModel = table.getColumnModel();
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        int width = 50; // Min width
	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer renderer = table.getCellRenderer(row, column);
	            Component comp = table.prepareRenderer(renderer, row, column);
	            width = Math.max(comp.getPreferredSize().width +1 , width);
	        }
	        columnModel.getColumn(column).setPreferredWidth(width);
	    }
	}
}

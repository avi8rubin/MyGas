import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GUI.CustomerGUI;
import GUI.Login_GUI;
import callback.callbackBuffer;
import callback.callbackCustomer;
import callback.callbackUser;
import client.Client;
import controller.CustomerController;

public class homeFuelTest {

	//public class homeFuelTest extends TestCase{

		private ArrayList<callbackCustomer> Customers= new ArrayList<callbackCustomer>();
		private callbackCustomer CustomerWithCredit= new callbackCustomer();
		private callbackCustomer CustomerWithNoCredit= new callbackCustomer();
		private callbackUser tempUser2;
		private callbackUser tempUser1;
		private CustomerGUI GuiScreen;
		CustomerController customerController;
	 

		@Before
		public void setUp() throws Exception {
			CustomerWithCredit.setCustomerFirstName("Litaf");
			CustomerWithCredit.setCustomerLastName("Kupfer");
			CustomerWithCredit.setCreditCard("44495559");
			CustomerWithCredit.setCustomersID(1234);
			CustomerWithCredit.setUserPassword("123");
			
			CustomerWithNoCredit.setCustomerFirstName("Adir");
			CustomerWithNoCredit.setCustomerLastName("Notes");
			CustomerWithNoCredit.setCustomersID(5678);
			CustomerWithNoCredit.setUserPassword("123");

			Customers.add(CustomerWithCredit);
			Customers.add(CustomerWithNoCredit);
			
			Login_GUI LoginGui= new Login_GUI();
			callbackBuffer buff=new callbackBuffer();
			Client server=new Client("127.0.0.1", 5555);
			tempUser1=new callbackUser
					(CustomerWithCredit.getCustomerFirstName(),CustomerWithNoCredit.getUserPassword());
			
			tempUser2=new callbackUser
					(CustomerWithCredit.getCustomerFirstName(),CustomerWithCredit.getUserPassword());
			
			CustomerGUI GuiScreenNoCredit= new CustomerGUI(tempUser1,server,buff,LoginGui);
			customerController= new CustomerController
					(server,buff,GuiScreenNoCredit);
			CustomerGUI GuiScreenCredit= new CustomerGUI(tempUser2,server,buff,LoginGui);
			customerController= new CustomerController
					(server,buff,GuiScreenCredit);
		}


		@Test
		public void NoCreditCardTest() {
			assertFalse(customerController.CheckForCreditCard(CustomerWithNoCredit));
		}
		


	}

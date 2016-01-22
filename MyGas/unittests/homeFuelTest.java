import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GUI.CustomerGUI;
import GUI.Login_GUI;
import callback.callbackCustomer;
import callback.callbackSale;
import callback.callbackUser;
import client.Client;
import controller.CustomerController;
import junit.framework.TestCase;

public class homeFuelTest extends TestCase{


		private ArrayList<callbackCustomer> Customers= new ArrayList<callbackCustomer>();
		private callbackCustomer CustomerWithCredit= new callbackCustomer();
		private callbackCustomer CustomerWithNoCredit= new callbackCustomer();
		private callbackUser tempUser2;
		private callbackUser tempUser1;
		private CustomerGUI GuiScreen;
		private CustomerController customerController;
		private CustomerController customerController2;

		private CustomerGUI GuiScreenCredit;
		private CustomerGUI GuiScreenNoCredit;
		
		private callbackSale sale1;
		private callbackSale sale2;

		/**
		 * Gui
		 */
		Login_GUI LoginGui;
		Client server;
		
		private ArrayList<callbackSale> orders=new ArrayList<callbackSale>();


		@Before
		public void setUp() throws Exception {
			CustomerWithCredit.setCustomerFirstName("Litaf");
			CustomerWithCredit.setCustomerLastName("Kupfer");
			CustomerWithCredit.setCreditCard("44495559");
			CustomerWithCredit.setCustomersID(1234);
			CustomerWithCredit.setUserPassword("123");
			
			CustomerWithNoCredit.setCustomerFirstName("Adir");
			CustomerWithNoCredit.setCustomerLastName("Notes");
			CustomerWithNoCredit.setCreditCard("");
			CustomerWithNoCredit.setCustomersID(5678);
			CustomerWithNoCredit.setUserPassword("123");

			Customers.add(CustomerWithCredit);
			Customers.add(CustomerWithNoCredit);
			
			callbackSale sale=new callbackSale();
			sale.setPayment(650);
			sale.setCustomersID(CustomerWithCredit.getCustomersID());
			sale.setFuelAmount(100);
			sale.setSaleID(1);
			sale.setDeliveryDateAndTime("16", "02", "10", "15", "30");
			orders.add(sale);
			
			
			sale1=new callbackSale();
			sale1.setPayment(1500);
			sale1.setCustomersID(CustomerWithCredit.getCustomersID());
			sale1.setFuelAmount(250);
			sale1.setSaleID(2);
			sale1.setOrderStatus("Not Delivered");
			sale1.setDeliveryDateAndTime("16", "02", "10", "15", "30");
		
			sale2=new callbackSale();
			sale2.setPayment(1000);
			sale2.setCustomersID(CustomerWithCredit.getCustomersID());
			sale2.setFuelAmount(200);
			sale2.setSaleID(3);
			sale2.setOrderStatus("Delivered");
			sale2.setDeliveryDateAndTime("16", "01", "10", "15", "30");
			
			orders.add(sale1);
			orders.add(sale2);
			
			tempUser1=new callbackUser
					(CustomerWithCredit.getCustomerFirstName(),CustomerWithNoCredit.getUserPassword());
			
			tempUser2=new callbackUser
					(CustomerWithCredit.getCustomerFirstName(),CustomerWithCredit.getUserPassword()); 
			
			LoginGui= new Login_GUI();
			LoginGui.setVisible(false);
			server=new Client("127.0.0.1", 5555);
			//server.deleteObserver(GuiScreenCredit.getNotificationThread());
			
				
			//no credit card
			GuiScreenNoCredit= new CustomerGUI(tempUser1,server,LoginGui);
			GuiScreenNoCredit.setVisible(false);
		//	GuiScreenNoCredit.getNotificationThread().setNotificationFlag(false);
			customerController= new CustomerController(server,GuiScreenNoCredit);

			//with credit card
			GuiScreenCredit= new CustomerGUI(tempUser2,server,LoginGui);
			GuiScreenCredit.setVisible(false);
		//	GuiScreenCredit.getNotificationThread().setNotificationFlag(false);
			customerController2= new CustomerController(server,GuiScreenCredit);

		}
		public homeFuelTest(String name){
			super(name);
		}

		public void testNoCreditCardTest() {
			assertTrue(customerController.CheckForCreditCard(CustomerWithNoCredit));

		}
		public void testWithCreditCardTest() {
			assertFalse(customerController.CheckForCreditCard(CustomerWithCredit));

		}
		
		public void testFiledsValidation() {
			
			GuiScreenCredit.setFuelAmount("100");
			GuiScreenCredit.setAddress("motzkin");
			GuiScreenCredit.setDate("16/03/16");
			GuiScreenCredit.setTime("12:00");
			assertEquals(customerController2.CheckAllValidFileds(), 4);
		}
		public void testIlligalFuel() {
			
			GuiScreenCredit.setFuelAmount("fss");
			GuiScreenCredit.setAddress("motzkin");
			GuiScreenCredit.setDate("16/03/16");
			GuiScreenCredit.setTime("12:00");
			assertEquals(customerController2.CheckAllValidFileds(), 3);
			
			GuiScreenCredit.setFuelAmount("-100");
			GuiScreenCredit.setAddress("motzkin");
			GuiScreenCredit.setDate("16/03/16");
			GuiScreenCredit.setTime("12:00");
			assertEquals(customerController2.CheckAllValidFileds(), 3);
		}
		public void testIlligalAddress() {
			
			GuiScreenCredit.setFuelAmount("100");
			GuiScreenCredit.setAddress("");
			GuiScreenCredit.setDate("16/03/16");
			GuiScreenCredit.setTime("12:00");
			assertEquals(customerController2.CheckAllValidFileds(), 3);
		}
		public void testIlligalDate() {
			
			GuiScreenCredit.setFuelAmount("250");
			GuiScreenCredit.setAddress("motzkin");
			GuiScreenCredit.setDate("16/03/15");
			GuiScreenCredit.setTime("12:00");
			assertEquals(customerController2.CheckAllValidFileds(), 3);
		}
		public void testIlligalTime() {
			
			GuiScreenCredit.setFuelAmount("200");
			GuiScreenCredit.setAddress("motzkin");
			GuiScreenCredit.setDate("20/03/16");
			GuiScreenCredit.setTime("55:00");
			assertEquals(customerController2.CheckAllValidFileds(), 3);
		}
		
		public void testAllFieldFill() {
			
			GuiScreenCredit.setFuelAmount("");
			GuiScreenCredit.setAddress("");
			GuiScreenCredit.setDate("20/03/16");
			GuiScreenCredit.setTime("12:00");
			assertEquals(customerController2.CheckAllValidFileds(), 2);
			
			GuiScreenCredit.setFuelAmount("");
			GuiScreenCredit.setAddress("lala land");
			GuiScreenCredit.setDate("20/03/16");
			GuiScreenCredit.setTime("12:45");
			assertEquals(customerController2.CheckAllValidFileds(), 3);
			
			GuiScreenCredit.setFuelAmount("");
			GuiScreenCredit.setAddress("");
			GuiScreenCredit.setDate("20/03/16");
			GuiScreenCredit.setTime("");
			assertEquals(customerController2.CheckAllValidFileds(), 1);
			
			GuiScreenCredit.setFuelAmount("");
			GuiScreenCredit.setAddress("");
			GuiScreenCredit.setDate("");
			GuiScreenCredit.setTime("");
			assertEquals(customerController2.CheckAllValidFileds(), 0);
		}
		public void testPay() {
			boolean bool=false;


			for (callbackSale order : orders) {
				if(order.getSaleID()==2)
					bool=true;
			}
			assertTrue(bool);
		}
		
		public void testCancle() {
			boolean bool=false;
			
			for (callbackSale order : orders) {
				if(order.getSaleID()==10)
					bool=true;
			}
			assertFalse(bool);
		}
		
		public void testDeliverdStatus() {
			
			assertFalse(orders.get(0).getOrderStatus()=="Delivered");
		}


	}

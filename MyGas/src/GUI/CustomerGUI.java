package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;

public class CustomerGUI extends abstractPanel_GUI {

	/**
	 * 
	 */
	
	private JLayeredPane HomeFuelOrdersCenterLayer= new JLayeredPane();


	//top buttons
	private JButton BuyHomeFuelButton;
	private JButton CheckFuelOrdersButton;
	
	private JScrollPane HomeFuelOrdersScrollPane = new JScrollPane();
	private JTable HomeFuelOrdersTable = new JTable();
	
	private static final long serialVersionUID = 1L;

	public CustomerGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer, Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);

		/* ------- Adding new button to Top Panel -------- */
		BuyHomeFuelButton= new JButton("Buy Home Fuel");
		BuyHomeFuelButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		BuyHomeFuelButton.setBounds(23, 133, 201, 38);
		TopPanel.add(BuyHomeFuelButton);	
		/* ------- Adding new button to Top Panel -------- */

		CheckFuelOrdersButton = new JButton("Check Fuel Orders");
		CheckFuelOrdersButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CheckFuelOrdersButton.setBounds(255, 133, 210, 38);
		TopPanel.add(CheckFuelOrdersButton);
		
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CheckFuelOrders			 --------*/
		HomeFuelOrdersCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		HomeFuelOrdersScrollPane.setBounds(38, 133, 600, 200);
		HomeFuelOrdersCenterLayer.add(HomeFuelOrdersScrollPane);
		
		HomeFuelOrdersScrollPane.setViewportView(HomeFuelOrdersTable);
		HomeFuelOrdersTable.setRowHeight(23);
		HomeFuelOrdersTable.setFillsViewportHeight(true);
		HomeFuelOrdersTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		HomeFuelOrdersTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		HomeFuelOrdersTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		
		CenterCardContainer.add(HomeFuelOrdersCenterLayer,"HomeFuelOrdersCenterLayer");
		HomeFuelOrdersCenterLayer.setOpaque(true);
		HomeFuelOrdersCenterLayer.setName("HomeFuelOrdersCenterLayer");
		
}
}
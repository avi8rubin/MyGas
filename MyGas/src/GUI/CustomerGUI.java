package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;

public class CustomerGUI extends abstractPanel_GUI {

	/**
	 * 
	 */
	private JLayeredPane BuyHomeFuelCenterLayer= new JLayeredPane();
	private JLayeredPane HomeFuelOrdersCenterLayer= new JLayeredPane();
	//top buttons
	private JButton BuyHomeFuelButton;
	private JButton CheckFuelOrdersButton;
	//orders
	private JScrollPane HomeFuelOrdersScrollPane = new JScrollPane();
	private JTable HomeFuelOrdersTable = new JTable();
	//buy
	private JButton PayButton;
	private JButton Cancelbutton;
	private JButton Calcbutton;

	private JDateChooser dateChooser ;
	
	private JLabel ErrorMassage = new JLabel("");
	private JLabel DateError =new JLabel("");
	private JLabel BuyHomeLabel;
	private JLabel Addresslabel;
	private JLabel DeliveryDatelabel;
	private JLabel FuelAmountLabel;
	private JLabel lblDeliveryTime;

	private JFormattedTextField DeliveryTimetextArea;
	private JTextArea AddresstextArea;
	private JTextArea CalcPricetextArea;
	private JTextArea FuelAmounttextArea;
	
	
	private static final long serialVersionUID = 1L;

	public CustomerGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer, Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);

		/* ------- Adding new button to Top Panel -------- */
		BuyHomeFuelButton= new JButton("Buy Home Fuel");
		BuyHomeFuelButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		BuyHomeFuelButton.setBounds(12, 99, 185, 38);
		TopPanel.add(BuyHomeFuelButton);	
		/* ------- Adding new button to Top Panel -------- */

		CheckFuelOrdersButton = new JButton("Check Fuel Orders");
		CheckFuelOrdersButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CheckFuelOrdersButton.setBounds(217, 99, 200, 38);
		TopPanel.add(CheckFuelOrdersButton);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
//		/*------- Create JTable surround with scroll pane and add
//		 * 				 it to CheckFuelOrders			 --------*/
//		HomeFuelOrdersCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
//
//		HomeFuelOrdersScrollPane.setBounds(38, 133, 600, 200);
//		HomeFuelOrdersCenterLayer.add(HomeFuelOrdersScrollPane);
//		
//		HomeFuelOrdersScrollPane.setViewportView(HomeFuelOrdersTable);
//		HomeFuelOrdersTable.setRowHeight(23);
//		HomeFuelOrdersTable.setFillsViewportHeight(true);
//		HomeFuelOrdersTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
//		HomeFuelOrdersTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
//		HomeFuelOrdersTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		
//		CenterCardContainer.add(HomeFuelOrdersCenterLayer,"HomeFuelOrdersCenterLayer");
//		HomeFuelOrdersCenterLayer.setOpaque(true);
//		HomeFuelOrdersCenterLayer.setName("HomeFuelOrdersCenterLayer"); 
////////////////////////////////////////////////////////////////////////////////////////////////////////////
		BuyHomeFuelCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		BuyHomeLabel = new JLabel("Buy Home Fuel");				
		BuyHomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		BuyHomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		BuyHomeLabel.setBounds(177, 13, 608, 42);
		BuyHomeFuelCenterLayer.add(BuyHomeLabel);
		
		FuelAmounttextArea = new JTextArea();
		FuelAmounttextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		FuelAmounttextArea.setBounds(277, 82, 200, 38);
		BuyHomeFuelCenterLayer.add(FuelAmounttextArea);
		
		AddresstextArea = new JTextArea();
		AddresstextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AddresstextArea.setBounds(277, 162, 200, 38);
		BuyHomeFuelCenterLayer.add(AddresstextArea);
		
		PayButton= new JButton("Pay");
		PayButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		PayButton.setBounds(631, 486, 124, 38);
		BuyHomeFuelCenterLayer.add(PayButton);
		
		Cancelbutton = new JButton("Cancel");
		Cancelbutton.setBounds(777, 486, 124, 38);
		Cancelbutton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		BuyHomeFuelCenterLayer.add(Cancelbutton);
		
		DeliveryTimetextArea = new JFormattedTextField();
		DeliveryTimetextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DeliveryTimetextArea.setHorizontalAlignment(SwingConstants.CENTER);
		DeliveryTimetextArea.setBounds(277, 322, 200, 38);
		DeliveryTimetextArea.setValue("__ : ___");
		BuyHomeFuelCenterLayer.add(DeliveryTimetextArea);
	
		Addresslabel = new JLabel("Address:");
		Addresslabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Addresslabel.setBounds(72, 162, 130, 38);
		BuyHomeFuelCenterLayer.add(Addresslabel);

		DeliveryDatelabel = new JLabel("Delivery Date:");
		DeliveryDatelabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DeliveryDatelabel.setBounds(72, 242, 130, 38);
		BuyHomeFuelCenterLayer.add(DeliveryDatelabel);
		
		FuelAmountLabel = new JLabel("Fuel Amount:");
		FuelAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		FuelAmountLabel.setBounds(72, 82, 130, 38);
		BuyHomeFuelCenterLayer.add(FuelAmountLabel);
		
		lblDeliveryTime = new JLabel("Delivery Time:");
		lblDeliveryTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDeliveryTime.setBounds(72, 321, 146, 38);
		BuyHomeFuelCenterLayer.add(lblDeliveryTime);
		
		Calcbutton = new JButton("Calculate Price");
		Calcbutton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Calcbutton.setBounds(72, 402, 169, 38);
		BuyHomeFuelCenterLayer.add(Calcbutton);
	
		CalcPricetextArea = new JTextArea();
		CalcPricetextArea.setEditable(false);
		CalcPricetextArea.setBounds(277, 402, 200, 38);
		BuyHomeFuelCenterLayer.add(CalcPricetextArea);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd/MM/yy");
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));		
		dateChooser.setBounds(277, 242, 200, 38);
		BuyHomeFuelCenterLayer.add(dateChooser);
		
		ErrorMassage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		//ErrorMassage.setText("*Incorrect Fuel Amount");
		ErrorMassage.setForeground(Color.RED);
		ErrorMassage.setBounds(277, 116, 212, 38);
		BuyHomeFuelCenterLayer.add(ErrorMassage);
	
		//DateError = new JLabel("*Incorrect Date");
		DateError.setForeground(Color.RED);
		DateError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		DateError.setBounds(277, 273, 212, 38);
		BuyHomeFuelCenterLayer.add(DateError);
		
		CenterCardContainer.add(BuyHomeFuelCenterLayer, "BuyHomeFuel");
		BuyHomeFuelCenterLayer.setOpaque(true);
		BuyHomeFuelCenterLayer.setName("BuyHomeFuel");
		
}
	
	public JButton getBuyHomeFuelButton() {
			return BuyHomeFuelButton;
	}
	public JButton getCheckFuelOrdersButton() {
		return CheckFuelOrdersButton;
	}
	public JButton getPayButton() {
		return PayButton;
	}
	public JButton getCalcButton() {
		return Calcbutton;
	}
	public JButton getCancelButton() {
		return Cancelbutton;
	}
	public void setCalcPricetextArea(String price) {
		CalcPricetextArea.setText(price);
	}
	public JTextArea getFuelAmount() {
		return FuelAmounttextArea;
	}
	public int getCurrentUserID() {
		return this.User.getUserID();
	}
	public String getCurrentUserName() {
		return String.format("%s %s", this.User.getFirstName(), this.User.getLastName());
	}
	public void DisableAllComponents(){

		DeliveryTimetextArea.setEditable(false);
		AddresstextArea.setEditable(false);
		CalcPricetextArea.setEditable(false);
		FuelAmounttextArea.setEditable(false);
//		PayButton.getDisabledSelectedIcon();
//		Cancelbutton.getDisabledSelectedIcon();
//		Calcbutton.getDisabledSelectedIcon();
//		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
//		editor.setEditable(false);
		}
}

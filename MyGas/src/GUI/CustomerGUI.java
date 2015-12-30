package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

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
	private JLabel ErrorAddressLabel =new JLabel("");
	private JLabel ErrorDeliveryTimeLabel=new JLabel("");
	private JLabel BuyHomeLabel;
	private JLabel CheckOrders;
	private JLabel Addresslabel;
	private JLabel DeliveryDatelabel;
	private JLabel FuelAmountLabel;
	private JLabel lblDeliveryTime;

	private JFormattedTextField DeliveryTimetextArea;
	private JTextArea AddresstextArea;
	private JTextArea CalcPricetextArea;
	private JTextArea FuelAmounttextArea;
	
	private JLabel OrderDetailsLabel;
	private JLabel ShowOrderDetailsLabel;
	private JLabel RemaraksLabel;
	private JLabel LitersLabel;
	private JLabel FuelPriceLabel;
	private JLabel ShippingLabel;
	private JLabel sumLabel;
	
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
		/*------- Create JTable surround with scroll pane and add
		 * 				 it to CheckFuelOrders			 --------*/
		HomeFuelOrdersCenterLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		CheckOrders = new JLabel("Your Fuel Order");				
		CheckOrders.setHorizontalAlignment(SwingConstants.CENTER);
		CheckOrders.setFont(new Font("Tahoma", Font.PLAIN, 26));
		CheckOrders.setBounds(177, 13, 608, 42);
		HomeFuelOrdersCenterLayer.add(CheckOrders);
		
		HomeFuelOrdersScrollPane.setBounds(43, 58,  900, 400);
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
		
		
		MaskFormatter DeliveryTimeFormatter = null;
		try {
			DeliveryTimeFormatter = new MaskFormatter("** : **");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		DeliveryTimetextArea=new JFormattedTextField(DeliveryTimeFormatter);

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
		CalcPricetextArea.setFont(new Font("Tahoma", Font.PLAIN, 16));		
		CalcPricetextArea.setBounds(277, 402, 200, 38);
		BuyHomeFuelCenterLayer.add(CalcPricetextArea);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd/MM/yy");
		dateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));		
		dateChooser.setBounds(277, 242, 200, 38);
		BuyHomeFuelCenterLayer.add(dateChooser);
		
		ErrorMassage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorMassage.setForeground(Color.RED);
		ErrorMassage.setBounds(277, 116, 212, 38);
		BuyHomeFuelCenterLayer.add(ErrorMassage);
	
		DateError.setForeground(Color.RED);
		DateError.setFont(new Font("Tahoma", Font.PLAIN, 16));
		DateError.setBounds(277, 273, 212, 38);
		BuyHomeFuelCenterLayer.add(DateError);
		
		ErrorAddressLabel.setForeground(Color.RED);
		ErrorAddressLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorAddressLabel.setBounds(277, 193, 212, 38);
		BuyHomeFuelCenterLayer.add(ErrorAddressLabel);

		ErrorDeliveryTimeLabel.setForeground(Color.RED);
		ErrorDeliveryTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorDeliveryTimeLabel.setBounds(277, 353, 212, 38);
		BuyHomeFuelCenterLayer.add(ErrorDeliveryTimeLabel);

		
		CenterCardContainer.add(BuyHomeFuelCenterLayer, "BuyHomeFuel");
		BuyHomeFuelCenterLayer.setOpaque(true);
		BuyHomeFuelCenterLayer.setName("BuyHomeFuel");
		
		OrderDetailsLabel = new JLabel("");
		OrderDetailsLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		OrderDetailsLabel.setBounds(609, 82, 146, 38);
		BuyHomeFuelCenterLayer.add(OrderDetailsLabel);

		ShowOrderDetailsLabel = new JLabel("");
		ShowOrderDetailsLabel.setBounds(609, 142, 176, 163);
		ShowOrderDetailsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		BuyHomeFuelCenterLayer.add(ShowOrderDetailsLabel);
		
		FuelPriceLabel = new JLabel("");
		FuelPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		FuelPriceLabel.setBounds(755, 142, 94, 22);
		BuyHomeFuelCenterLayer.add(FuelPriceLabel);
		
		LitersLabel = new JLabel("");
		LitersLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LitersLabel.setBounds(755, 188, 116, 22);
		BuyHomeFuelCenterLayer.add(LitersLabel);

		sumLabel = new JLabel("");		
		sumLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sumLabel.setBounds(755, 234, 116, 22);
		BuyHomeFuelCenterLayer.add(sumLabel);
		
		ShippingLabel = new JLabel("");
		ShippingLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ShippingLabel.setBounds(755, 278, 116, 22);
		BuyHomeFuelCenterLayer.add(ShippingLabel);
		
		RemaraksLabel = new JLabel("");
		RemaraksLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		RemaraksLabel.setForeground(Color.BLUE);
		RemaraksLabel.setBounds(609, 337, 200, 92);
		BuyHomeFuelCenterLayer.add(RemaraksLabel);

}
public void setFuelPriceLabel(String str){
	FuelPriceLabel.setText(str);
}
public void setLitersLabel(String str){
	LitersLabel.setText(str);
}	
public void setsumLabel(String str){
	sumLabel.setText(str);
}
public void setShippingLabel(String str){
	ShippingLabel.setText(str);
}
public void setRemaraksLabel(String str){
	RemaraksLabel.setText(str);
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
	public int getCurrentUserID() {
		return this.User.getUserID();
	}
	public String getCurrentUserName() {
		return String.format("%s %s", this.User.getFirstName(), this.User.getLastName());
	}	
	public String getTime() {
		return DeliveryTimetextArea.getText();
	}
	public void setTime(String str){
		DeliveryTimetextArea.setText(str);
	}
	public String getFuelAmount(){
		return FuelAmounttextArea.getText();
	}
	public void setFuelAmount(String str){
		FuelAmounttextArea.setText(str);
	}
	public String getDate(){
		return ((JTextField)dateChooser.getDateEditor().getUiComponent()).getText();
	}
	public void setDate(){
	dateChooser.setCalendar(null);
	}	
	public JLabel ErrorAmoutLabel(){
		return ErrorMassage;
	}
	public String getAddrres() {
		return AddresstextArea.getText();
	}
	public JLabel getErrorAddressLabel(){
		return ErrorAddressLabel;
	}
	public JLabel getErrorDeliveryTimeLabel(){
		return ErrorDeliveryTimeLabel;
	}
	public JLabel DateLabel(){
		return DateError;
	}
	
	public void DisablePayButton(){
		PayButton.setEnabled(false);
	}
	public void EnablePayButton(){
		PayButton.setEnabled(true);
	}
	public void DisableAllComponents(){

		for(int a = 0 ; a < BuyHomeFuelCenterLayer.getComponentCount() ; a ++){   
			BuyHomeFuelCenterLayer.getComponent(a).setEnabled(false) ;  
		}
	}
	public void setOrderDetailsLabel(String str){
		OrderDetailsLabel.setText(str);
	}
	public void setShowOrderDetailsLabel(String str){
		ShowOrderDetailsLabel.setText(str);
	}
	public void setHomeFuelOrdersTable(DefaultTableModel NewTableModel){		
		HomeFuelOrdersTable.setModel(NewTableModel);
	}
	
}

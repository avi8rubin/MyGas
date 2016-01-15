/************************************************************************** 
 * Copyright (©) MyGas System 2015-2016 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Ohad Zino <zinoohad@gmail.com>
 * 			  Adir Notes <adirno@zahav.net.il>
 * 			  Litaf Kupfer <litafkupfer@gmail.com>
 * 			  Avi Rubin <avi8rubin@gmail.com>
 **************************************************************************/
package controller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Observable;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import GUI.CEOGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackStringArray;
import callback.callbackSuccess;
import callback.callbackVector;
import callback.callbackWaitingTariff;
import client.Client;
import common.MessageType;
/**
 * CEO controller, literally, control the CEO gui
 * @author Ohad
 */
public class CEOController extends Controller {
	
	/**
	 * All GUI components 
	 */
	private CEOGUI GuiScreen;	
	private JButton TariffApprovalButton;
	private JButton TariffSaveButton;
	private JTable TariffApprovalTable;

	/**
	 * Constructor - CEO Gui
	 * @param Server
	 * @param CommonBuffer
	 * @param GuiScreen
	 */
	public CEOController(Client Server, callbackBuffer CommonBuffer, CEOGUI GuiScreen) {
		super(Server, CommonBuffer,GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);
		
		/* ------ Add action listener to buttons ------ */
		//Tariff Approval Button
		TariffApprovalButton = GuiScreen.getTariffApprovalButton();
		TariffApprovalButton.addActionListener(this);
		TariffApprovalButton.setActionCommand("Tariff Approval");								//Add action command
		
		//Tariff Save Button
		TariffSaveButton = GuiScreen.getTariffSaveButton();
		TariffSaveButton.addActionListener(this);
		/* ------------------------------------------- */
		
		GuiScreen.getCheckBox().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ContainerCard = (CardLayout)(CenterCardContainer.getLayout());
		
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Tariff Approval")){
			ContainerCard.show(CenterCardContainer, "Tariff Approval");				//The TariffApproval layer will be display
			HandleTariffApprovalPressed();											
		}
		if(e.getSource() == TariffSaveButton){
			HandleSavePressed();
		}		
		if(e.getSource() == GuiScreen.getCheckBox()) Server.handleMessageFromClient(new callbackSuccess(MessageType.StartSender));
	}
/**
 * Sends request to query from DB
 */
	private void HandleTariffApprovalPressed(){		
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getWaitingTariff));
	}
/**
 * When 'Save' button pressed, send the relevant values to save in the DB
 */
	private void HandleSavePressed(){
		int i,RowNum;
		callbackVector UpdateTariff = new callbackVector(MessageType.setWaitingTariff);
		TariffApprovalTable = GuiScreen.getTariffApprovalTable();
		DefaultTableModel CurrentTableModel = (DefaultTableModel) TariffApprovalTable.getModel();
		RowNum = CurrentTableModel.getRowCount();
		for(i=0 ; i < RowNum ; i++){
			if(!CurrentTableModel.getValueAt(i, 6).equals("Waiting")){
				callbackWaitingTariff NewTariff = new callbackWaitingTariff();
				NewTariff.setTariffUpdateID(Integer.valueOf((String) CurrentTableModel.getValueAt(i,0)));
				NewTariff.setFuelID(Integer.valueOf((String) CurrentTableModel.getValueAt(i,2)));
				NewTariff.setWantedPrice(Float.valueOf((String) CurrentTableModel.getValueAt(i,4)));
				NewTariff.setCEOConfirmation((String) CurrentTableModel.getValueAt(i,6));
				UpdateTariff.add(NewTariff);
			}
		}
		/*Just if there was a change then update DB*/
		if(UpdateTariff.size()>0){
			Server.handleMessageFromClient(UpdateTariff);
		}
		else {
			UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 20));
			UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));
				JOptionPane.showMessageDialog(GuiScreen,	
					"There are no changes in the existing records.",
						"Error Message",
			    			JOptionPane.ERROR_MESSAGE);		
		}
	}

@Override
public void update(Observable o, Object arg) {
	if(arg instanceof CallBack){
	
		switch(((CallBack) arg).getWhatToDo()){
			case getWaitingTariff:
				callbackStringArray TariffTable = (callbackStringArray) arg;		
				GuiScreen.setTariffApprovalTable(TariffTable.getDefaultTableModel());
				break;
			case setWaitingTariff:
				HandleTariffApprovalPressed();
				break;
		default:
			super.update(o, arg);
			break;
		}
	}
	
}


	
	
	
	
	
	
	
	
	
	
	
	
	
}

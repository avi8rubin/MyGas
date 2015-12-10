package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import GUI.CEOGUI;
import callback.callbackBuffer;
import callback.callbackStringArray;
import client.Client;
import common.MessageType;

public class CEOController extends Controller {
	
	/**
	 * All GUI components 
	 */
	private CEOGUI GuiScreen;	
	private JButton TariffApprovalButton;
	private JButton TariffSaveButton;
	
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

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CardLayout check = (CardLayout)(CenterCardContainer.getLayout());
		
		/* -------- Check the source of the event ---------*/
		if(e.getActionCommand().equals("Tariff Approval")){
			check.show(CenterCardContainer, "Tariff Approval");				//The TariffApproval layer will be display
			HandleTariffApprovalPressed();											
		}
		if(e.getSource() == TariffSaveButton){
			/*Do Something*/
		}
		
	}

	private void HandleTariffApprovalPressed(){
		
		Server.handleMessageFromClient(new callbackStringArray(MessageType.getWaitingTariff));
		callbackStringArray TariffTable = (callbackStringArray) getCallBackFromBuffer();
		GuiScreen.setTariffApprovalTable(TariffTable.getData(), TariffTable.getColHeaders());
	}


}

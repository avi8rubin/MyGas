package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import GUI.CEOGUI;
import GUI.MarketingRepresentativeGUI;
import callback.callbackBuffer;
import client.Client;
import common.Checks;

public class MarketingRepresentativeController extends Controller{
	/**
	 * Marketing Representative GUI components 
	 */
	
	private CardLayout ContainerCardCenter;
	private CardLayout ContainerCardLeft;
	private MarketingRepresentativeGUI GuiScreen;
	
	//Top Layer Components	
	private JButton CreateNewCustomerAccountButton;
	private JButton CustomerDetailsButton;

	//CreateNewCustomerAccountLayer Left Layer Components
	private JLayeredPane CreateNewCustomerAccountLayer;
	private JButton CreateUserButton;
	private JButton AddCarDetailsButton;
	
	//CreateUserLayer Center Layer Components
	private JLabel PasswordValidationFailedMessageLabel;
	private JButton NextButton;	
	
	// AddPersonalDetails Center Layer Components	
	private JButton CreateButton;
	
	
	
	public MarketingRepresentativeController(Client Server, callbackBuffer CommonBuffer, MarketingRepresentativeGUI GuiScreen) {
		super(Server, CommonBuffer, GuiScreen);
		this.GuiScreen = GuiScreen;
		GuiScreen.setVisible(true);

		/* ------ Add action listener to buttons ------ */
		
		//CreateNewCustomerAccount Button
		CreateNewCustomerAccountButton = GuiScreen.getCreateNewCustomerAccountButton();
		CreateNewCustomerAccountButton.addActionListener(this);
		CreateNewCustomerAccountButton.setActionCommand("CreateNewCustomerAccount"); //Add action command
		
		//CustomerDetails Button
		CustomerDetailsButton = GuiScreen.getCustomerDetailsButton();
		CustomerDetailsButton.addActionListener(this);
		CustomerDetailsButton.setActionCommand("CustomerDetails"); //Add action command

		//CreateUser Button
		CreateUserButton = GuiScreen.getCreateUserButton();
		CreateUserButton.addActionListener(this);
		CreateUserButton.setActionCommand("CreateUser"); //Add action command
		
		//AddCarDetails Button
		AddCarDetailsButton = GuiScreen.getAddCarDetailsButton();
		AddCarDetailsButton.addActionListener(this);
		AddCarDetailsButton.setActionCommand("AddCarDetails"); //Add action command	
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		
		/* -------- Check the source of the event ---------*/
		
		if(e.getActionCommand().equals("CreateNewCustomerAccount")){
			CreateNewCustomerAccountLayer = GuiScreen.getCreateNewCustomerAccountLayer();
			CreateNewCustomerAccountLayer.remove(AddCarDetailsButton);
			ContainerCardLeft.show(LeftCardContainer, "CreateNewCustomerAccountLeft");
			ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");				//The CreateNewCustomerAccount layer will be display											
		}

		if(e.getActionCommand().equals("CreateUser")){
			HandleCreateUserPressed();
		}		

		if(e.getActionCommand().equals("Next")){
			HandleNextPressed();
														
			// HAVE TO ADD HANDLER
		}		

		if(e.getActionCommand().equals("Create")){
			ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");											
			// HAVE TO ADD HANDLER
		}			
		

		}	
	
	private void HandleCreateUserPressed(){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		
		GuiScreen.CreateNewUserCenterLayer();
		ContainerCardCenter.show(CenterCardContainer, "CreateUserLayerCenter");				//The CreateUser layer will be display											
		
		//Next Button
		NextButton = GuiScreen.getNextButton();
		NextButton.addActionListener(this);
		NextButton.setActionCommand("Next"); //Add action command
		
		//Create Button
		CreateButton = GuiScreen.getCreateButton();
		CreateButton.addActionListener(this);
		CreateButton.setActionCommand("Create"); //Add action command
		
	}
	

	private void HandleNextPressed(){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		PasswordValidationFailedMessageLabel = GuiScreen.getPasswordValidationFailedMessageLabel();
		
		if (!Checks.isValidPassword(GuiScreen.getPasswordPasswordField().getText(), GuiScreen.getPasswordValidatePasswordField().getText()))
			PasswordValidationFailedMessageLabel.setVisible(true);
		else 
		{
			PasswordValidationFailedMessageLabel.setVisible(false);
			ContainerCardCenter.show(CenterCardContainer, "AddPersonalDetailsCenter");
		}
		
		
		//ContainerCardCenter.show(CenterCardContainer, "AddPersonalDetailsCenter");				//The AddPersonalDetailsCenter layer will be display

		
		//		Server.handleMessageFromClient(new callbackStringArray(MessageType.getWaitingTariff));
//		callbackStringArray TariffUpdateTable = (callbackStringArray) getCallBackFromBuffer();		
//		GuiScreen.setTariffUpdateTable(new TableModel(TariffUpdateTable.getData(), TariffUpdateTable.getColHeaders()));		
			
	}
	
	

}

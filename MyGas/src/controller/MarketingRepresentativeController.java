package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import GUI.CEOGUI;
import GUI.MarketingRepresentativeGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCustomer;
import callback.callback_Error;
import client.Client;
import common.Checks;
import common.MessageType;

public class MarketingRepresentativeController extends Controller{
	/**
	 * Marketing Representative GUI components 
	 */
	
	private CardLayout ContainerCardCenter;
	private CardLayout ContainerCardLeft;
	private MarketingRepresentativeGUI GuiScreen;
	callbackCustomer customerCallback;
	
	//Top Layer Components	
	private JButton CreateNewCustomerAccountButton;
	private JButton CustomerDetailsButton;

	//CreateNewCustomerAccountLayer Left Layer Components
	private JLayeredPane CreateNewCustomerAccountLayer;
	private JButton CreateUserButton;
	private JButton AddCarDetailsButton;
	
	//CreateUserLayer Center Layer Components
	private JLabel PasswordValidationFailedMessageLabel;
	private JLabel MissedFieldsMessageLabel;
	private JLabel UserNameExistMesaageLabel;
	private JButton NextButton;	
	
	// AddPersonalDetails Center Layer Components	
	private JButton CreateButton;
	private JLabel InvalidEmailMesaageLabel;
	private JTextField EmailTextField;
	private JLabel InvalidPhoneMesaageLabel;
	private JLabel InvalidCreditCardMesaageLabel;
	private JLabel MissedFieldsMessage;
	
	
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
		}		

		if(e.getActionCommand().equals("Create")){
			HandleCreatePressed();
			//ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");											
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
		MissedFieldsMessageLabel = GuiScreen.getMissedFieldsMessageLabel();
		UserNameExistMesaageLabel = GuiScreen.getUserNameExistMesaageLabel();
		CallBack LocalUserCallBack = null;
		
		// Show/ don't show PasswordValidationFailedMessageLabel after isValidPassword check
		if (!Checks.isValidPassword(GuiScreen.getPasswordPasswordField().getText(), GuiScreen.getPasswordValidatePasswordField().getText()))
			PasswordValidationFailedMessageLabel.setVisible(true);
		else PasswordValidationFailedMessageLabel.setVisible(false);
		
		// Show/ don't show MissedFieldsMessageLabel after isAllFieldsFilled check
		if (!Checks.isAllFieldsFilled(GuiScreen.getPasswordPasswordField().getText(), GuiScreen.getPasswordValidatePasswordField().getText(), GuiScreen.getUserNametextField().getText()))
				MissedFieldsMessageLabel.setVisible(true);
		else MissedFieldsMessageLabel.setVisible(false);
		
		// If the input OK - checking if UserName exist in DB
		if (!PasswordValidationFailedMessageLabel.isVisible()&&!MissedFieldsMessageLabel.isVisible())
			ContainerCardCenter.show(CenterCardContainer, "AddPersonalDetailsCenter");
			
//			Server.handleMessageFromClient(new callbackCustomer(MessageType.getIsUserNameExists, GuiScreen.getUserNametextField().getText(),GuiScreen.getPasswordPasswordField().getText()));
//			LocalUserCallBack = getCallBackFromBuffer();
//			if (LocalUserCallBack instanceof callback_Error) // Show UserNameExistMesaageLabel after UserName exist in DB
//				UserNameExistMesaageLabel.setVisible(true);
//			else 
//			{
//				UserNameExistMesaageLabel.setVisible(false);
//				customerCallback= (callbackCustomer)LocalUserCallBack;
//				
//			}
				
				
		//ContainerCardCenter.show(CenterCardContainer, "AddPersonalDetailsCenter");				//The AddPersonalDetailsCenter layer will be display
	
		//		Server.handleMessageFromClient(new callbackStringArray(MessageType.getWaitingTariff));
//		callbackStringArray TariffUpdateTable = (callbackStringArray) getCallBackFromBuffer();		
//		GuiScreen.setTariffUpdateTable(new TableModel(TariffUpdateTable.getData(), TariffUpdateTable.getColHeaders()));		
			
	}

	private void HandleCreatePressed(){
		
		InvalidEmailMesaageLabel = GuiScreen.getInvalidEmailMesaageLabel();
		EmailTextField = GuiScreen.getEmailTextField();
		InvalidPhoneMesaageLabel= GuiScreen.getInvalidPhoneMesaageLabel();
		InvalidCreditCardMesaageLabel =  GuiScreen.getInvalidCreditCardMesaageLabel();
		MissedFieldsMessage = GuiScreen.getMissedFieldsMessage();
		//String check;
		
		// Show/ don't show InvalidEmailMesaageLabel after isValidEmailAddress check		
		if (!Checks.isValidEmailAddress(GuiScreen.getEmailTextField().getText()))
			InvalidEmailMesaageLabel.setVisible(true);
		else InvalidEmailMesaageLabel.setVisible(false);
		
		// Show/ don't show InvalidPhoneMesaageLabel after phone number check	
		if (String.valueOf(GuiScreen.getPhoneFormattedTextField().getText().trim().length()).equals("1")) InvalidPhoneMesaageLabel.setVisible(true);
		else InvalidPhoneMesaageLabel.setVisible(false);

//		EmailTextField.setText(String.valueOf(GuiScreen.getCreditCardFormattedTextField().getText().trim().length()));
//		EmailTextField.setText(String.valueOf(GuiScreen.getCreditCardFormattedTextField().getText()));
		
		// Show/ don't show InvalidCreditCardMesaageLabel after credit card number check		
		if (!String.valueOf(GuiScreen.getCreditCardFormattedTextField().getText().trim().length()).equals("19") && !GuiScreen.getCreditCardFormattedTextField().getText().equals("    -    -    -    ") ) 
			InvalidCreditCardMesaageLabel.setVisible(true);
		else InvalidCreditCardMesaageLabel.setVisible(false);		
		
		// Show/ don't show MissedFieldsMessage after checking that all fields filled
		if ( !checks.isAllFieldsFilled(GuiScreen.getCustomerIDTextField().getText(), GuiScreen.getFirstNameTextField().getText(), GuiScreen.getLastNameTextField().getText()) ||
				InvalidEmailMesaageLabel.isVisible() || InvalidPhoneMesaageLabel.isVisible() || InvalidCreditCardMesaageLabel.isVisible() ||
				( !GuiScreen.getPrivateCustomerRadioButton().isSelected() && !GuiScreen.getCommercialCustomerRadioButton().isSelected() )   )
			MissedFieldsMessage.setVisible(true);
		else MissedFieldsMessage.setVisible(false);
			
		
		
	}
	

}

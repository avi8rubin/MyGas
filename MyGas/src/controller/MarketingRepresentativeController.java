package controller;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import GUI.CEOGUI;
import GUI.MarketingRepresentativeGUI;
import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCar;
import callback.callbackCustomer;
import callback.callbackStringArray;
import callback.callbackSuccess;
import callback.callbackVector;
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
	private JButton EditButton;
	private JButton SaveButton;	
	private JLabel InvalidCustomerIDMesaageLabel;
	private JLabel InvalidEmailMesaageLabel;
	private JLabel EmailExistMesaageLabel;
	private JTextField EmailTextField;
	private JLabel InvalidPhoneMesaageLabel;
	private JLabel InvalidCreditCardMesaageLabel;
	private JLabel MissedFieldsMessage;
	private JLabel CustomerIDExistMesaageLabel;

	// AddCars Center Layer Components		
	private JButton AddButton;
	private JLabel ExistCarNumberLabel;
	private JFormattedTextField CarNumberFormattedTextField;
	private JLabel MissedFieldMessageAddCarsLabel;
	
	//CustomerDetailsLayer Left Layer Components	
	private JLayeredPane CustomerDetailsLayer;
	private JButton SearchButton;
	private JTextField EnterCustomerIdTextField;
	private JLabel InvalidcustomerIDMessageLabel;
	private JLabel CustomerNotExistMessageLabel;
	private callbackCustomer fullCallbackCustomer;


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
		
		//Search Button
		SearchButton = GuiScreen.getSearchButton();
		SearchButton.addActionListener(this);
		SearchButton.setActionCommand("Search"); //Add action command
		
		// get values for comboBoxs
		Server.handleMessageFromClient(new callbackVector(MessageType.getMarketingRepresentativeComboBox));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		
		/* -------- Check the source of the event ---------*/
		
		if(e.getActionCommand().equals("CreateNewCustomerAccount")){
			GuiScreen.setNewCustomerFlag(true);
			CreateNewCustomerAccountLayer = GuiScreen.getCreateNewCustomerAccountLayer();
			AddCarDetailsButton.setVisible(false);
			CreateUserButton.setText("<html>Create User</html>");
			AddCarDetailsButton.setText("<html>Add Car Details</html>");
			ContainerCardLeft.show(LeftCardContainer, "CreateNewCustomerAccountLeft");
			ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");				//The CreateNewCustomerAccount layer will be display											
		}

		if(e.getActionCommand().equals("CustomerDetails")){
			GuiScreen.setNewCustomerFlag(false);
			CustomerDetailsLayer = GuiScreen.getCustomerDetailsLayer();
			CreateUserButton.setText("<html>Edit User</html>");
			AddCarDetailsButton.setText("<html>Edit Cars Details</html>");
			AddCarDetailsButton.setVisible(true);
			ContainerCardLeft.show(LeftCardContainer, "CustomerDetailsLayerLeft");
			ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");				//The CustomerDetails layer will be display											
		}		
		
		
		if(e.getActionCommand().equals("CreateUser")){
			HandleCreateUserPressed();
		}		

		if(e.getActionCommand().equals("Next")){
			HandleNextPressed();
		}		

		if(e.getActionCommand().equals("Create")){
			HandleCreatePressed();										
		}			

		if(e.getActionCommand().equals("AddCarDetails")){
			HandleAddCarPressed();										
		}		

		if(e.getActionCommand().equals("Add")){
			HandleAddPressed();										
		}	
		
		if(e.getActionCommand().equals("Search")){
			HandleSearchPressed();										
		}			
		
		if(e.getActionCommand().equals("Edit")){
			GuiScreen.EnableRelevantButtons(true);
			ContainerCardCenter.show(CenterCardContainer, "CreateUserLayerCenter");	 //The CreateUser layer will be display
		}	
		
		if(e.getActionCommand().equals("Save"))
			HandleCreatePressed();
		
		
		}	

	private void HandleCreateUserPressed(){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
		
		GuiScreen.CreateNewUserCenterLayer();
		
		GuiScreen.HideRelevantButtons();
		if ( GuiScreen.getNewCustomerFlag() ) GuiScreen.EnableRelevantButtons(true);
		else GuiScreen.EnableRelevantButtons(false);
		
		ContainerCardCenter.show(CenterCardContainer, "CreateUserLayerCenter");				//The CreateUser layer will be display											
		
		//Next Button
		NextButton = GuiScreen.getNextButton();
		NextButton.addActionListener(this);
		NextButton.setActionCommand("Next"); //Add action command
		
		//Create Button
		CreateButton = GuiScreen.getCreateButton();
		CreateButton.addActionListener(this);
		CreateButton.setActionCommand("Create"); //Add action command
		
		//Edit Button
		EditButton = GuiScreen.getEditButton();
		EditButton.addActionListener(this);
		EditButton.setActionCommand("Edit"); //Add action command
		
		//Save Button
		SaveButton = GuiScreen.getSaveButton();
		SaveButton.addActionListener(this);
		SaveButton.setActionCommand("Save"); //Add action command
		
	}

	private void HandleNextPressed(){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		PasswordValidationFailedMessageLabel = GuiScreen.getPasswordValidationFailedMessageLabel();
		MissedFieldsMessageLabel = GuiScreen.getMissedFieldsMessageLabel();
		UserNameExistMesaageLabel = GuiScreen.getUserNameExistMesaageLabel();
		//CallBack LocalUserCallBack = null;
		
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
			
	}

	private void HandleCreatePressed(){
		
		InvalidEmailMesaageLabel = GuiScreen.getInvalidEmailMesaageLabel();
		EmailTextField = GuiScreen.getEmailTextField();
		InvalidPhoneMesaageLabel= GuiScreen.getInvalidPhoneMesaageLabel();
		InvalidCreditCardMesaageLabel =  GuiScreen.getInvalidCreditCardMesaageLabel();
		MissedFieldsMessage = GuiScreen.getMissedFieldsMessage();
		InvalidCustomerIDMesaageLabel = GuiScreen.getInvalidCustomerIDMesaageLabel();
		
		// Show/ don't show InvalidCustomerIDMesaageLabel after isNumeric check		
		if ( !Checks.isNumeric(GuiScreen.getCustomerIDTextField().getText()) )
			InvalidCustomerIDMesaageLabel.setVisible(true);
		else InvalidCustomerIDMesaageLabel.setVisible(false);		
		
		// Show/ don't show InvalidEmailMesaageLabel after isValidEmailAddress check		
		if (!Checks.isValidEmailAddress(GuiScreen.getEmailTextField().getText()))
			InvalidEmailMesaageLabel.setVisible(true);
		else InvalidEmailMesaageLabel.setVisible(false);
		
		// Show/ don't show InvalidPhoneMesaageLabel after phone number check	
		if (String.valueOf(GuiScreen.getPhoneFormattedTextField().getText().trim().length()).equals("1")) InvalidPhoneMesaageLabel.setVisible(true);
		else InvalidPhoneMesaageLabel.setVisible(false);
		
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
			
		// If all input OK- start checking valid input with DB
		if ( !MissedFieldsMessage.isVisible() && !InvalidCreditCardMesaageLabel.isVisible() && !InvalidCreditCardMesaageLabel.isVisible()
				&& !InvalidPhoneMesaageLabel.isVisible() && !InvalidEmailMesaageLabel.isVisible() && !InvalidCustomerIDMesaageLabel.isVisible() )
		{
			CallBack LocalUserCallBack;
			callbackCustomer fullCallbackCustomer = GuiScreen.getFullCallbackCustomer(); // Fill CallbackCustomer with GUI data
			if (GuiScreen.getNewCustomerFlag() ) fullCallbackCustomer.setWhatToDo(MessageType.setCreateNewCustomer);
			else fullCallbackCustomer.setWhatToDo(MessageType.setUpdateCustomer);
				
			//  show error message in no_changes case of update customer details
			if ( !GuiScreen.getNewCustomerFlag() && GuiScreen.isCustomerChanged(fullCallbackCustomer) ) {
				UIManager.put("OptionPane.messageFont", new Font("System", Font.PLAIN, 20));
				UIManager.put("OptionPane.buttonFont", new Font("System", Font.PLAIN, 18));
					JOptionPane.showMessageDialog(GuiScreen,	
						"There are no changes in the existing records.",
							"Error Message",
				    			JOptionPane.ERROR_MESSAGE);					
			}
			else  //Send CallbackCustomer to DB
			{
				this.fullCallbackCustomer = fullCallbackCustomer;
				Server.handleMessageFromClient( fullCallbackCustomer);
			}
		}	
	}
	
	private void HandleCreatePressedBackFromServer(CallBack LocalUserCallBack){
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		
		// Success case
		if  (LocalUserCallBack instanceof callbackSuccess)
		{
			GuiScreen.setFullCallbackCustomer(fullCallbackCustomer);
			ContainerCardCenter.show(CenterCardContainer, "EmptyCenterPanel");
			AddCarDetailsButton.setVisible(true);
		}
		else 
		{
			callback_Error LocalErrorCallBack;
			LocalErrorCallBack= (callback_Error)LocalUserCallBack;
			
			// UserNameExist error case
			
			if (LocalErrorCallBack.getErrorMassage().equals("User name already exists in DB."))
			{
				UserNameExistMesaageLabel.setVisible(true);
				ContainerCardCenter.show(CenterCardContainer, "CreateUserLayerCenter");	 //The CreateUser layer will be display	
			}
			else UserNameExistMesaageLabel.setVisible(false);
			
			
			// CustomerID error case
			CustomerIDExistMesaageLabel = GuiScreen.getCustomerIDExistMesaageLabel();
			if (LocalErrorCallBack.getErrorMassage().equals("Customer already registered in system."))
				CustomerIDExistMesaageLabel.setVisible(true);
			else CustomerIDExistMesaageLabel.setVisible(false);	
			
			// EmailExist error case
			EmailExistMesaageLabel = GuiScreen.getEmailExistMesaageLabel();
			if (LocalErrorCallBack.getErrorMassage().equals("Email belong to another customer."))
				EmailExistMesaageLabel.setVisible(true);
			else EmailExistMesaageLabel.setVisible(false);				
				
		}			
	
	}
	
	private void HandleAddCarPressed() {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		Object[] idAraay = {GuiScreen.getCallbackCustomerUpdated().getCustomersID()};
		GuiScreen.AddCarsCenterPanelLayer();
		
		//Add Button
		AddButton = GuiScreen.getAddButton();
		AddButton.addActionListener(this);
		AddButton.setActionCommand("Add"); //Add action command
		
		callbackStringArray CarsViewTable = new callbackStringArray(MessageType.getCarDetailes);
		CarsViewTable.setVariance(idAraay);
//		CarsViewTable.setColCount(GuiScreen.getCallbackCustomerUpdated().getCustomersID());
		Server.handleMessageFromClient(CarsViewTable);			
	}
	
	private void HandleAddCarPressedBackFromServer(callbackStringArray CarsViewTable1) {
		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
		
		GuiScreen.setCarsViewTable(CarsViewTable1.getDefaultTableModel());
		ContainerCardCenter.show(CenterCardContainer, "AddCarLayerCenter");				//The AddCar layer will be display			
	}
	
	private void HandleAddPressed() {
		
		CarNumberFormattedTextField = GuiScreen.getCarNumberFormattedTextField();
		MissedFieldMessageAddCarsLabel = GuiScreen.getMissedFieldMessageAddCarsLabel();
		
		// Show/ don't show MissedFieldsMessage after checking that all fields filled		
		if (CarNumberFormattedTextField.getText().equals("  -   -  "))
			MissedFieldMessageAddCarsLabel.setVisible(true);
		else MissedFieldMessageAddCarsLabel.setVisible(false);
		
		// send CarCallback to server check
		if ( !MissedFieldMessageAddCarsLabel.isVisible() )
		{
			callbackCar fullCallbackCar = GuiScreen.getFullCallbackCar(); // Fill CallbackCustomer with GUI data
			fullCallbackCar.setWhatToDo(MessageType.setNewCar);
			
			//Send CallbackCar to DB
			Server.handleMessageFromClient(fullCallbackCar);					
		}
				
	}
	
	private void HandleAddPressedBackFromServer(CallBack LocalUserCallBack) {
		
		ExistCarNumberLabel = GuiScreen.getExistCarNumberLabel();
		// Success case
		if  (LocalUserCallBack instanceof callbackSuccess)
		{
			GuiScreen.setSuccessAddingCarMessageLabel();
			ExistCarNumberLabel.setVisible(false);
		}
		// Error case - car exist in DB
		else ExistCarNumberLabel.setVisible(true);
	}
	
	private void HandleSearchPressed() {
		
		EnterCustomerIdTextField = GuiScreen.getEnterCustomerIdTextField();
		InvalidcustomerIDMessageLabel = GuiScreen.getInvalidcustomerIDMessageLabel();
		CustomerNotExistMessageLabel = GuiScreen.getCustomerNotExistMessageLabel();
		CustomerNotExistMessageLabel.setVisible(false);
	
		// Show/ don't show InvalidcustomerIDMessageLabel after checking CustomerId is valid
		if ( !checks.isNumeric(EnterCustomerIdTextField.getText() ) ) InvalidcustomerIDMessageLabel.setVisible(true);
		else InvalidcustomerIDMessageLabel.setVisible(false);
		
		// send CustomerCallback to server check
		if (!InvalidcustomerIDMessageLabel.isVisible())
		{
			CallBack LocalUserCallBack;
			fullCallbackCustomer = GuiScreen.getCustomerIdCallbackCustomer(); // Fill CallbackCustomer with GUI data
			fullCallbackCustomer.setWhatToDo(MessageType.getCustomer);	
			
			//Send CallbackCustomer to DB
			Server.handleMessageFromClient( fullCallbackCustomer);				
		}	
	}
	
	private void HandleSearchPressedBackFromServer(CallBack LocalUserCallBack) {
		
		ContainerCardLeft = (CardLayout)(LeftCardContainer.getLayout());

		// Success case
		if  (LocalUserCallBack instanceof callbackCustomer)
		{
			fullCallbackCustomer = (callbackCustomer) LocalUserCallBack;
			GuiScreen.setFullCallbackCustomer(fullCallbackCustomer);
			ContainerCardLeft.show(LeftCardContainer, "CreateNewCustomerAccountLeft"); //The CustomerDetailsLayer layer will be display
		}
		// Error case - customer ID not exist in DB
		else CustomerNotExistMessageLabel.setVisible(true);		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof CallBack){
		
			switch(((CallBack) arg).getWhatToDo()){
				case setCreateNewCustomer:
					HandleCreatePressedBackFromServer((CallBack) arg);
					break;
				case setUpdateCustomer:
					HandleCreatePressedBackFromServer((CallBack) arg);
					break;
				case setNewCar:
					HandleAddPressedBackFromServer((CallBack) arg);
					break;
				case getCustomer:
					HandleSearchPressedBackFromServer((CallBack) arg);
					break;
				case getCarDetailes:
					HandleAddCarPressedBackFromServer((callbackStringArray) arg);
					break;					
					
			default:
				super.update(o, arg);
				break;
			}
		}
		
		
		// getMarketingRepresentativeComboBox  only case
		if(arg instanceof Vector<?>){
			Vector<String[]> ComboBoxs = (Vector<String[]>) arg;
			GuiScreen.SetComboBoxSelection(ComboBoxs.get(0));
			GuiScreen.SetFuelIDComboBoxSelection(ComboBoxs.get(1));
			GuiScreen.SetCostingModelComboBoxSelection(ComboBoxs.get(2));
		}
			
		
		
	}
	
	public callbackCustomer getFullCallbackCustomer() {
		return fullCallbackCustomer;
	}
}


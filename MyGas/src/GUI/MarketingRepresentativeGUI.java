package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import callback.CallBack;
import callback.callbackBuffer;
import callback.callbackCar;
import callback.callbackCustomer;
import callback.callbackStringArray;
import callback.callbackSuccess;
import callback.callbackUser;
import callback.callbackVector;
import client.Client;
import common.MessageType;

public class MarketingRepresentativeGUI extends abstractPanel_GUI{
	
	
	private static final long serialVersionUID = 1L;
	private static boolean newCustomerFlag=true;
	
	/**
	 * Gui variables
	 */
	
	//Top Layer Components
	private JButton CreateNewCustomerAccountButton;
	private JButton CustomerDetailsButton;
	private JButton CreateNewCampignPatternButton;
	private JButton ReportsButton;
	
	//CreateNewCustomerAccountLayer Left Layer Components
	private JLayeredPane CreateNewCustomerAccountLayer;
	private JButton CreateUserButton;
	private JButton AddCarDetailsButton;
	private JLabel LogoImage;
	
	//CreateUserLayer Center Layer Components
	private JLayeredPane CreateUserLayer;
	private JLabel UserNameLabel;
	private JPasswordField PasswordPasswordField;
	private JPasswordField PasswordValidatePasswordField;
	private JLabel UserNameExistMesaageLabel;
	private JLabel PasswordValidationFailedMessageLabel;
	private JTextField UserNametextField;
	private JLabel IsActiveLabel;
	private JCheckBox IsActiveCheckBox;	
	private JButton NextButton;
	private JLabel MissedFieldsMessageLabel;
	
	// AddPersonalDetails Center Layer Components
	private JLayeredPane AddPersonalDetails;
	private JLabel CustimerIDLabel;
	private JLabel FirstNameLabel;
	private JLabel LastNameLabel;
	private JLabel EmailLabel;
	private JLabel PhoneLabel;
	private JLabel CreditCardLabel;
	private JLabel CustomerTypeLabel;
	private JLabel PurchasePlanLabel;
	private JLabel InvalidCustomerIDMesaageLabel;
	private JLabel CustomerIDExistMesaageLabel;
	private JLabel EmailExistMesaageLabel;
	private JLabel InvalidEmailMesaageLabel;
	private JTextField CustomerIDTextField;
	private JTextField FirstNameTextField;
	private JTextField LastNameTextField;
	private JTextField EmailTextField;
	private MaskFormatter PhoneMask = null;
	private JFormattedTextField PhoneFormattedTextField;
	private MaskFormatter CreditCardMask = null;
	private JFormattedTextField CreditCardFormattedTextField;
	private ButtonGroup PurchasePlanButtonGroup;
	private JRadioButton PrivateCustomerRadioButton;
	private JRadioButton CommercialCustomerRadioButton;
	private JComboBox PurchasePlanComboBox;
	private JComboBox CostingModelComboBox;
	private JButton CreateButton;
	private JButton EditButton;
	private JButton SaveButton;
	private JLabel MissedFieldsMessage;
	private JLabel InvalidPhoneMesaageLabel;
	private JLabel InvalidCreditCardMesaageLabel;

// AddCars Center Layer Components
	private JLayeredPane AddCarPanel;
	private JLabel CarNumberLabel1;
	private JLabel FuelIDLabel;
	private JLabel NFCLabel;
	private JLabel CostingModelLabel;
	private JFormattedTextField CarNumberFormattedTextField;
	private JComboBox FuelIDComboBox;
	private JCheckBox NFCNewCheckBox;
	private JButton AddButton;
	private JLabel ExistCarNumberLabel;
	private MaskFormatter CarNumberMask;
	private JLabel MissedFieldMessageAddCarsLabel;
	private JLabel SuccessAddingCarMessageLabel;
	
	//CustomerDetailsLayer Left Layer Components
	private JLayeredPane CustomerDetailsLayer;
	private JTextField EnterCustomerIdTextField;
	private JButton SearchButton;
	private JLabel InvalidcustomerIDMessageLabel;
	private JLabel CustomerNotExistMessageLabel;
	private callbackCustomer fullCallbackCustomer ;

	

	public MarketingRepresentativeGUI(callbackUser EnteredUser, Client Server, callbackBuffer CommonBuffer,
			Login_GUI LoginScreen) {
		super(EnteredUser, Server, CommonBuffer, LoginScreen);

		/* ------- Adding new button to Top Panel -------- */	
		CreateNewCustomerAccountButton = new JButton("Create New Customer Account");
		CreateNewCustomerAccountButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateNewCustomerAccountButton.setBounds(12, 101, 329, 38);	
		TopPanel.add(CreateNewCustomerAccountButton);					
		
		/* ------- Adding new button to Top Panel -------- */				
		CustomerDetailsButton = new JButton("Customer Details");						//Global variable
		CustomerDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CustomerDetailsButton.setBounds(351, 101, 222, 38);
		TopPanel.add(CustomerDetailsButton);
			
		/* ------- Adding new button to Top Panel -------- */		
		CreateNewCampignPatternButton = new JButton("Create New Campign Pattern");		//Global variable
		CreateNewCampignPatternButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateNewCampignPatternButton.setBounds(583, 101, 354, 38);
		TopPanel.add(CreateNewCampignPatternButton);		
		
		/* ------- Adding new button to Top Panel -------- */
		ReportsButton = new JButton("Reports");											//Global variable
		ReportsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ReportsButton.setBounds(947, 101, 125, 38);
		TopPanel.add(ReportsButton);		
		
		
		/* ------- Adding new layer to CreateNewCustomerAccount Panel -------- */
		

		/* ------- Adding CreateNewCustomer Left layer to CreateNewCustomerAccount Panel -------- */
		CreateNewCustomerAccountLayer = new JLayeredPane();
		CreateNewCustomerAccountLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CreateNewCustomerAccountLayer.setBackground(new Color(169, 169, 169));

		//add left CreateNewCustomer Layer
		LeftCardContainer.add(CreateNewCustomerAccountLayer, "CreateNewCustomerAccountLeft");
		CreateNewCustomerAccountLayer.setOpaque(true);
		CreateNewCustomerAccountLayer.setName("CreateNewCustomerAccountLeft");
		
		//buttons left layer
		CreateUserButton = new JButton("<html>Create User</html>");
		CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		CreateUserButton.setBounds(27, 33, 212, 46);
		
		AddCarDetailsButton = new JButton("<html>Add Car Details</html>");
		AddCarDetailsButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		AddCarDetailsButton.setBounds(27, 99, 212, 46);
		AddCarDetailsButton.setVisible(false);
		
		// Logo on left panel
		LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(38, 313, 239, 242);
		
		CreateNewCustomerAccountLayer.add(CreateUserButton);
		CreateNewCustomerAccountLayer.add(AddCarDetailsButton);
		CreateNewCustomerAccountLayer.add(LogoImage);
		
		
		/* ------- Adding CustomerDetails Left layer to CreateNewCustomerAccount Panel -------- */
		CustomerDetailsLayer = new JLayeredPane();
		CustomerDetailsLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CustomerDetailsLayer.setBackground(new Color(169, 169, 169));

		//add left CustomerDetails Layer 
		LeftCardContainer.add(CustomerDetailsLayer, "CustomerDetailsLayerLeft");
		CustomerDetailsLayer.setOpaque(true);
		CustomerDetailsLayer.setName("CustomerDetailsLayerLeft");
		
		//Components in CustomerDetails left layer
		JLabel EnterCustomerIdLabel = new JLabel("Enter customer ID :");
		EnterCustomerIdLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		EnterCustomerIdLabel.setBounds(54, 11, 187, 31);
		CustomerDetailsLayer.add(EnterCustomerIdLabel);
		
		EnterCustomerIdTextField = new JTextField();
		EnterCustomerIdTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
		EnterCustomerIdTextField.setBounds(38, 44, 222, 31);
		CustomerDetailsLayer.add(EnterCustomerIdTextField);
		EnterCustomerIdTextField.setColumns(10);
		
		SearchButton = new JButton("Search");
		SearchButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		SearchButton.setBounds(75, 86, 125, 38);
		CustomerDetailsLayer.add(SearchButton);
		
		InvalidcustomerIDMessageLabel = new JLabel("Invalid customer ID inserted");
		InvalidcustomerIDMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidcustomerIDMessageLabel.setForeground(Color.RED);
		InvalidcustomerIDMessageLabel.setEnabled(false);
		InvalidcustomerIDMessageLabel.setVisible(false);
		InvalidcustomerIDMessageLabel.setBounds(10, 126, 265, 46);
		CustomerDetailsLayer.add(InvalidcustomerIDMessageLabel);
		
		
		CustomerNotExistMessageLabel = new JLabel("Customer is not exist");
		CustomerNotExistMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		CustomerNotExistMessageLabel.setEnabled(false);
		CustomerNotExistMessageLabel.setVisible(false);
		CustomerNotExistMessageLabel.setBounds(54, 126, 210, 46);
		CustomerDetailsLayer.add(CustomerNotExistMessageLabel);
		
		LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(38, 313, 239, 242);
		CustomerDetailsLayer.add(LogoImage);
		
		// define  comboBoxs		
		PurchasePlanComboBox = new JComboBox();
		PurchasePlanComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PurchasePlanComboBox.setBounds(179, 438, 460, 27);
		
		CostingModelComboBox = new JComboBox();
		CostingModelComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CostingModelComboBox.setBounds(179, 484, 233, 27);
		
		FuelIDComboBox = new JComboBox();
		FuelIDComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FuelIDComboBox.setBounds(154, 88, 158, 27);
		
		CreateNewUserCenterLayer();
				
	} 
	
	public JTextField getEnterCustomerIdTextField() {
		return EnterCustomerIdTextField;
	}

	public JButton getSearchButton() {
		return SearchButton;
	}

	public JLabel getInvalidcustomerIDMessageLabel() {
		return InvalidcustomerIDMessageLabel;
	}

	public JLabel getCustomerNotExistMessageLabel() {
		return CustomerNotExistMessageLabel;
	}

	public JLayeredPane getCustomerDetailsLayer() {
		return CustomerDetailsLayer;
	}

	public JButton getCreateNewCustomerAccountButton(){
		return CreateNewCustomerAccountButton;
	}	
	
	public JButton getCustomerDetailsButton(){
		return CustomerDetailsButton;
	}	
	
	public boolean getNewCustomerFlag() {
		return newCustomerFlag;
	}

	public void setNewCustomerFlag(boolean newCustomerFlag1) {
		newCustomerFlag = newCustomerFlag1;
	}
	
	public JLayeredPane getCreateNewCustomerAccountLayer(){
		return CreateNewCustomerAccountLayer;
	}	
	
	public JButton getCreateUserButton(){
		return CreateUserButton;
	}		
	
	public JButton getAddCarDetailsButton(){
		return AddCarDetailsButton;
	}		

	public JButton getNextButton(){
		return NextButton;
	}		

	public JLabel getPasswordValidationFailedMessageLabel() {
		return PasswordValidationFailedMessageLabel;
	}
	
	public JPasswordField getPasswordPasswordField() {
		return PasswordPasswordField;
	}	
	
	public JPasswordField getPasswordValidatePasswordField() {
		return PasswordValidatePasswordField;
	}	
		
	public JTextField getUserNametextField() {
		return UserNametextField;
	}		

	public JLabel getMissedFieldsMessageLabel() {
		return MissedFieldsMessageLabel;
	}		
	
	public JLabel getMissedFieldsMessage() {
		return MissedFieldsMessage;
	}		
	
	public JLabel getUserNameExistMesaageLabel() {
		return UserNameExistMesaageLabel;
	}	
	
	public JButton getCreateButton(){
		return CreateButton;
	}		
	
	public JTextField getCustomerIDTextField(){
		return CustomerIDTextField;
	}
	
	public JTextField getFirstNameTextField(){
		return FirstNameTextField;
	}
	
	public JTextField getLastNameTextField(){
		return LastNameTextField;
	}
	
	public JLabel getInvalidEmailMesaageLabel() {
		return InvalidEmailMesaageLabel;
	}	
	
	public JTextField getEmailTextField(){
		return EmailTextField;
	}
	
	public JFormattedTextField getPhoneFormattedTextField(){
		return PhoneFormattedTextField;
	}
	
	public JFormattedTextField getCreditCardFormattedTextField(){
		return CreditCardFormattedTextField;
	}
	
	public int getComboBoxSelection(){
		return PurchasePlanComboBox.getSelectedIndex();
	}
	
	public void SetComboBoxSelection(String[] PurchasePlan){
		Object[]pattern=PurchasePlan;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		PurchasePlanComboBox.setModel(combopatternModel);	
	}	
	
	public JComboBox<?> getComboBox(){
		return PurchasePlanComboBox;
	}
	
	
	public JLabel getInvalidPhoneMesaageLabel() {
		return InvalidPhoneMesaageLabel;
	}	
	
	public JLabel getInvalidCreditCardMesaageLabel() {
		return InvalidCreditCardMesaageLabel;
	}	
	
	public JRadioButton getPrivateCustomerRadioButton() {
		return PrivateCustomerRadioButton;
	}	
	
	public JRadioButton getCommercialCustomerRadioButton() {
		return CommercialCustomerRadioButton;
	}	
	
	public JLabel getEmailExistMesaageLabel() {
		return EmailExistMesaageLabel;
	}
	
	public JLabel getCustomerIDExistMesaageLabel() {
		return CustomerIDExistMesaageLabel;
	}
	
	public JLabel getInvalidCustomerIDMesaageLabel() {
		return InvalidCustomerIDMesaageLabel;
	}
	
	public void AddCarsCenterPanelLayer()
	{
		
		/* ------- Adding AddCar Center Layer -------- */

		AddCarPanel = new JLayeredPane();
		AddCarPanel.setLayout(null);
		AddCarPanel.setOpaque(true);
		AddCarPanel.setName("EmptyCenterPanel");
		AddCarPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CenterCardContainer.add(AddCarPanel, "AddCarLayerCenter");
		
		/*------- add labels, textFields and buttons 
		 * 						to AddCarLayer --------*/
		
		CarNumberLabel1 = new JLabel("Car Number : ");
		CarNumberLabel1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CarNumberLabel1.setEnabled(false);
		CarNumberLabel1.setBounds(21, 46, 115, 34);
		AddCarPanel.add(CarNumberLabel1);
		
		FuelIDLabel = new JLabel("Fuel ID : ");
		FuelIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FuelIDLabel.setEnabled(false);
		FuelIDLabel.setBounds(21, 81, 115, 34);
		AddCarPanel.add(FuelIDLabel);
		
		NFCLabel = new JLabel("NFC :");
		NFCLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		NFCLabel.setEnabled(false);
		NFCLabel.setBounds(21, 119, 115, 34);
		AddCarPanel.add(NFCLabel);
				
        try {
            //
            // Create a MaskFormatter for accepting car number, the # symbol accept
            // only a number. We can also set the empty value with a place holder
            // character.
            //
        	 CarNumberMask = new MaskFormatter("##-###-##");
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		CarNumberFormattedTextField = new JFormattedTextField(CarNumberMask);
		CarNumberFormattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CarNumberFormattedTextField.setToolTipText("Car number have to written with 7 digits. For example: 11-222-33");
		CarNumberFormattedTextField.setBounds(155, 50, 103, 27);
		AddCarPanel.add(CarNumberFormattedTextField);
		
		AddCarPanel.add(FuelIDComboBox);
		
		NFCNewCheckBox = new JCheckBox("");
		NFCNewCheckBox.setBounds(155, 130, 97, 23);
		AddCarPanel.add(NFCNewCheckBox);
		
		AddButton = new JButton("Add");
		AddButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AddButton.setBounds(442, 150, 97, 38);
		AddCarPanel.add(AddButton);
		
		ExistCarNumberLabel = new JLabel("This car already in system");
		ExistCarNumberLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		ExistCarNumberLabel.setEnabled(false);
		ExistCarNumberLabel.setBounds(283, 35, 278, 46);
		ExistCarNumberLabel.setVisible(false);
		AddCarPanel.add(ExistCarNumberLabel);

	    MissedFieldMessageAddCarsLabel = new JLabel("There is one or more missed fields");
		MissedFieldMessageAddCarsLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		MissedFieldMessageAddCarsLabel.setEnabled(false);
		MissedFieldMessageAddCarsLabel.setBounds(561, 142, 348, 46);
		MissedFieldMessageAddCarsLabel.setVisible(false);
		AddCarPanel.add(MissedFieldMessageAddCarsLabel);
		
		SuccessAddingCarMessageLabel = new JLabel("lol gadol");
		SuccessAddingCarMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		SuccessAddingCarMessageLabel.setEnabled(false);
		SuccessAddingCarMessageLabel.setBounds(442, 199, 532, 46);
		SuccessAddingCarMessageLabel.setVisible(false);
		AddCarPanel.add(SuccessAddingCarMessageLabel);
		
		
	}
	
	public int getCostingModelComboBoxSelection(){
		return CostingModelComboBox.getSelectedIndex();
	}
	
	
	public void SetCostingModelComboBoxSelection(String[] CostingModels){
		Object[]pattern=CostingModels;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		CostingModelComboBox.setModel(combopatternModel);	
	}	
	
	public int getFuelIDComboBoxSelection(){
		return FuelIDComboBox.getSelectedIndex();
	}
	
	public void SetFuelIDComboBoxSelection(String[] FuelID){
		Object[]pattern=FuelID;
		DefaultComboBoxModel<?> combopatternModel=new DefaultComboBoxModel(pattern);
		FuelIDComboBox.setModel(combopatternModel);	
	}		
	
	public JLabel getExistCarNumberLabel() {
		return ExistCarNumberLabel;
	}	

	public JLabel getMissedFieldMessageAddCarsLabel() {
		return MissedFieldMessageAddCarsLabel;
	}		
	
	public JFormattedTextField getCarNumberFormattedTextField() {
		return CarNumberFormattedTextField;
	}	
	
	public JButton getAddButton(){
		return AddButton;
	}			
	
	public void CreateNewUserCenterLayer()
	{
		
		AddCarDetailsButton.setVisible(false);
		/* ------- Adding CreateUser Center Layer -------- */
		CreateUserLayer = new JLayeredPane();
		CreateUserLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(CreateUserLayer,"CreateUserLayerCenter");
		CreateUserLayer.setOpaque(true);
		CreateUserLayer.setName("CreateUserLayerCenter");
		
		/*------- add labels, textFields and buttons 
		 * 						to CreateUserLayer --------*/		
		
		UserNameLabel = new JLabel("User Name");
		UserNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		UserNameLabel.setEnabled(false);
		UserNameLabel.setBounds(27, 47, 115, 34);
		CreateUserLayer.add(UserNameLabel);		
		
		JLabel PasswordLabel = new JLabel("Password");
		PasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordLabel.setEnabled(false);
		PasswordLabel.setBounds(27, 104, 100, 34);
		CreateUserLayer.add(PasswordLabel);
	
		JLabel PasswordValidateLabel = new JLabel("Password Validate");
		PasswordValidateLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordValidateLabel.setEnabled(false);
		PasswordValidateLabel.setBounds(27, 151, 163, 46);
		CreateUserLayer.add(PasswordValidateLabel);		
				
		PasswordPasswordField = new JPasswordField();
		PasswordPasswordField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordPasswordField.setBounds(179, 111, 201, 27);
		CreateUserLayer.add(PasswordPasswordField);
		
		PasswordValidatePasswordField = new JPasswordField();
		PasswordValidatePasswordField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PasswordValidatePasswordField.setBounds(179, 161, 201, 27);
		CreateUserLayer.add(PasswordValidatePasswordField);
		
		IsActiveLabel = new JLabel("Is Active ");
		IsActiveLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		IsActiveLabel.setEnabled(false);
		IsActiveLabel.setBounds(27, 193, 132, 46);
		CreateUserLayer.add(IsActiveLabel);
		
		IsActiveCheckBox = new JCheckBox("");
		IsActiveCheckBox.setBounds(179, 204, 97, 23);
		IsActiveCheckBox.setSelected(true);
		CreateUserLayer.add(IsActiveCheckBox);

		// Message to marketing representative - display only in User name existance case
		UserNameExistMesaageLabel = new JLabel("User name already exist in system");
		UserNameExistMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		UserNameExistMesaageLabel.setEnabled(false);
		UserNameExistMesaageLabel.setBounds(403, 41, 309, 46);	
		UserNameExistMesaageLabel.setVisible(false);
		CreateUserLayer.add(UserNameExistMesaageLabel);
		
		// Message to marketing representative - display only in Password Validation Failure case
		PasswordValidationFailedMessageLabel = new JLabel("Password Validation Failed");
		PasswordValidationFailedMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		PasswordValidationFailedMessageLabel.setEnabled(false);
		PasswordValidationFailedMessageLabel.setBounds(403, 122, 268, 46);
		PasswordValidationFailedMessageLabel.setVisible(false);
		CreateUserLayer.add(PasswordValidationFailedMessageLabel);
		
		UserNametextField = new JTextField();
		UserNametextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		UserNametextField.setColumns(10);
		UserNametextField.setBounds(179, 51, 201, 27);
		CreateUserLayer.add(UserNametextField);
		
		NextButton = new JButton("Next");
		NextButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		NextButton.setBounds(607, 248, 125, 38);
		CreateUserLayer.add(NextButton);
		
		// Message to marketing representative - display only in Missed Fields case
		MissedFieldsMessageLabel = new JLabel("There is one or more missed fields");
		MissedFieldsMessageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		MissedFieldsMessageLabel.setEnabled(false);
		MissedFieldsMessageLabel.setBounds(249, 240, 348, 46);
		MissedFieldsMessageLabel.setVisible(false);
		CreateUserLayer.add(MissedFieldsMessageLabel);
		
		/* ------- AddPersonalDetails Center Layer -------- */
		
		AddPersonalDetails = new JLayeredPane();
		AddPersonalDetails.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		CenterCardContainer.add(AddPersonalDetails,"AddPersonalDetailsCenter");
		AddPersonalDetails.setOpaque(true);
		AddPersonalDetails.setName("AddPersonalDetailsCenter");
		
		/*------- add labels, textFields and buttons 
		 * 						to CreateUserLayer --------*/		
		CustimerIDLabel = new JLabel("Custimer ID :");
		CustimerIDLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CustimerIDLabel.setEnabled(false);
		CustimerIDLabel.setBounds(39, 50, 115, 34);
		AddPersonalDetails.add(CustimerIDLabel);		
		
		FirstNameLabel = new JLabel("First Name :");
		FirstNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FirstNameLabel.setEnabled(false);
		FirstNameLabel.setBounds(39, 107, 100, 34);
		AddPersonalDetails.add(FirstNameLabel);
	
		LastNameLabel = new JLabel("Last Name :");
		LastNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		LastNameLabel.setEnabled(false);
		LastNameLabel.setBounds(39, 154, 163, 46);
		AddPersonalDetails.add(LastNameLabel);
		
		EmailLabel = new JLabel("Email :");
		EmailLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		EmailLabel.setEnabled(false);
		EmailLabel.setBounds(39, 211, 163, 46);
		AddPersonalDetails.add(EmailLabel);
		
		PhoneLabel = new JLabel("Phone :");
		PhoneLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PhoneLabel.setEnabled(false);
		PhoneLabel.setBounds(39, 267, 163, 46);
		AddPersonalDetails.add(PhoneLabel);
		
		CreditCardLabel = new JLabel("Credit Card :");
		CreditCardLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CreditCardLabel.setEnabled(false);
		CreditCardLabel.setBounds(39, 324, 163, 46);
		AddPersonalDetails.add(CreditCardLabel);
		
		CustomerTypeLabel = new JLabel("Customer Type :");
		CustomerTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CustomerTypeLabel.setEnabled(false);
		CustomerTypeLabel.setBounds(39, 381, 163, 46);
		AddPersonalDetails.add(CustomerTypeLabel);
		
		PurchasePlanLabel = new JLabel("Purchase Plan :");
		PurchasePlanLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PurchasePlanLabel.setEnabled(false);
		PurchasePlanLabel.setBounds(39, 427, 163, 46);
		AddPersonalDetails.add(PurchasePlanLabel);

		CostingModelLabel = new JLabel("Costing Model :");
		CostingModelLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CostingModelLabel.setEnabled(false);
		CostingModelLabel.setBounds(39, 480, 115, 34);
		AddPersonalDetails.add(CostingModelLabel);
		
		InvalidCustomerIDMesaageLabel = new JLabel("Invalid Customer ID ");
		InvalidCustomerIDMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidCustomerIDMesaageLabel.setEnabled(false);
		InvalidCustomerIDMesaageLabel.setBounds(403, 44, 184, 46);
		InvalidCustomerIDMesaageLabel.setVisible(false);
		AddPersonalDetails.add(InvalidCustomerIDMesaageLabel);		
		
		CustomerIDExistMesaageLabel = new JLabel("Customer ID already exist in system");
		CustomerIDExistMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		CustomerIDExistMesaageLabel.setEnabled(false);
		CustomerIDExistMesaageLabel.setBounds(403, 41, 340, 46);
		CustomerIDExistMesaageLabel.setVisible(false);
		AddPersonalDetails.add(CustomerIDExistMesaageLabel);

		EmailExistMesaageLabel = new JLabel("Email already exist in system");
		EmailExistMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		EmailExistMesaageLabel.setEnabled(false);
		EmailExistMesaageLabel.setBounds(497, 211, 278, 46);
		EmailExistMesaageLabel.setVisible(false);
		AddPersonalDetails.add(EmailExistMesaageLabel);
		
		InvalidEmailMesaageLabel = new JLabel("Invalid email");
		InvalidEmailMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidEmailMesaageLabel.setEnabled(false);
		InvalidEmailMesaageLabel.setBounds(497, 211, 278, 46);
		InvalidEmailMesaageLabel.setVisible(false);
		AddPersonalDetails.add(InvalidEmailMesaageLabel);
		
		CustomerIDTextField = new JTextField();
		CustomerIDTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CustomerIDTextField.setColumns(10);
		CustomerIDTextField.setBounds(179, 51, 201, 27);
		CustomerIDTextField.setToolTipText("Customer ID have to include only numbers.\r\nFor example: 123456789");
		AddPersonalDetails.add(CustomerIDTextField);
		
		FirstNameTextField = new JTextField();
		FirstNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		FirstNameTextField.setColumns(10);
		FirstNameTextField.setBounds(179, 108, 201, 27);
		AddPersonalDetails.add(FirstNameTextField);
		
		LastNameTextField = new JTextField();
		LastNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		LastNameTextField.setBounds(179, 161, 201, 27);
		AddPersonalDetails.add(LastNameTextField);
		
		EmailTextField = new JTextField();
		EmailTextField.setToolTipText("Email have to include @.\r\nFor example: david@gmail.com");
		EmailTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		EmailTextField.setBounds(179, 227, 295, 27);
		AddPersonalDetails.add(EmailTextField);
		
        try {
            //
            // Create a MaskFormatter for accepting phone number, the # symbol accept
            // only a number. We can also set the empty value with a place holder
            // character.
            //
        	PhoneMask = new MaskFormatter("###-#######");
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
        PhoneFormattedTextField = new JFormattedTextField(PhoneMask);
		PhoneFormattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PhoneFormattedTextField.setToolTipText("Phone have to written with 10 digits. For example: 050-1234567");
		PhoneFormattedTextField.setBounds(179, 280, 125, 27);
		AddPersonalDetails.add(PhoneFormattedTextField);
		
        try {
            //
            // Create a MaskFormatter for accepting CreditCard number, the # symbol accept
            // only a number. We can also set the empty value with a place holder
            // character.
            //
        	CreditCardMask = new MaskFormatter("####-####-####-####");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        CreditCardFormattedTextField = new JFormattedTextField(CreditCardMask);
		CreditCardFormattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CreditCardFormattedTextField.setToolTipText("Credit Card have to written with 16 digits. For example: 1234-5678-9876-4321");
		CreditCardFormattedTextField.setBounds(179, 334, 241, 27);
		AddPersonalDetails.add(CreditCardFormattedTextField);       
        
		PrivateCustomerRadioButton = new JRadioButton("Private");
		PrivateCustomerRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PrivateCustomerRadioButton.setBounds(179, 393, 100, 23);
		AddPersonalDetails.add(PrivateCustomerRadioButton);
		
		CommercialCustomerRadioButton = new JRadioButton("Commercial");
		CommercialCustomerRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		CommercialCustomerRadioButton.setBounds(279, 393, 141, 23);
		AddPersonalDetails.add(CommercialCustomerRadioButton);
		
		PurchasePlanButtonGroup = new ButtonGroup();
		PurchasePlanButtonGroup.add(PrivateCustomerRadioButton);
		PurchasePlanButtonGroup.add(CommercialCustomerRadioButton);
		
		AddPersonalDetails.add(PurchasePlanComboBox);
		AddPersonalDetails.add(CostingModelComboBox);
		
		CreateButton = new JButton("Create");
		CreateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateButton.setBounds(678, 496, 125, 38);	
		AddPersonalDetails.add(CreateButton);
		
		EditButton = new JButton("Edit");
		EditButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		EditButton.setBounds(678, 438, 125, 38);
		AddPersonalDetails.add(EditButton);
		
		SaveButton = new JButton("Save");
		SaveButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		SaveButton.setBounds(678, 496, 125, 38);
		AddPersonalDetails.add(SaveButton);
		
		MissedFieldsMessage = new JLabel("There is one or more missed fields");
		MissedFieldsMessage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		MissedFieldsMessage.setEnabled(false);
		MissedFieldsMessage.setBounds(320, 505, 348, 46);
		MissedFieldsMessage.setVisible(false);
		AddPersonalDetails.add(MissedFieldsMessage);
		
		InvalidPhoneMesaageLabel = new JLabel("Invalid phone number inserted");
		InvalidPhoneMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidPhoneMesaageLabel.setEnabled(false);
		InvalidPhoneMesaageLabel.setBounds(340, 267, 278, 46);
		InvalidPhoneMesaageLabel.setVisible(false);
		AddPersonalDetails.add(InvalidPhoneMesaageLabel);
		
		InvalidCreditCardMesaageLabel = new JLabel("Invalid credit card number inserted");
		InvalidCreditCardMesaageLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		InvalidCreditCardMesaageLabel.setEnabled(false);
		InvalidCreditCardMesaageLabel.setBounds(444, 324, 331, 46);
		InvalidCreditCardMesaageLabel.setVisible(false);
		AddPersonalDetails.add(InvalidCreditCardMesaageLabel);
		
		//show customer details on GUI
		if (!newCustomerFlag)
		{			
			UserNametextField.setText( fullCallbackCustomer.getUserName() );
			PasswordPasswordField.setText( fullCallbackCustomer.getUserPassword() );
			PasswordValidatePasswordField.setText( fullCallbackCustomer.getUserPassword() );
			if ( fullCallbackCustomer.getISActive().equals("Yes") ) IsActiveCheckBox.setSelected(true);
			else IsActiveCheckBox.setSelected(false);	
			CustomerIDTextField.setText( String.valueOf(fullCallbackCustomer.getCustomersID()) );
			FirstNameTextField.setText( fullCallbackCustomer.getCustomerFirstName() );
			LastNameTextField.setText( fullCallbackCustomer.getCustomerLastName() );
			EmailTextField.setText( fullCallbackCustomer.getEmail() );	
			PhoneFormattedTextField.setText( fullCallbackCustomer.getPhoneNumber() );
			CreditCardFormattedTextField.setText( fullCallbackCustomer.getCreditCard() );	
			if ( fullCallbackCustomer.getCustomerType().equals("Private") ) PrivateCustomerRadioButton.setSelected(true);
			else CommercialCustomerRadioButton.setSelected(true);
			PurchasePlanComboBox.setSelectedIndex(fullCallbackCustomer.getPlanID() - 1);
			
		}
		
		
	}

	public void HideRelevantButtons()
	{
		if(newCustomerFlag)
		{
			IsActiveLabel.setVisible(false);
			IsActiveCheckBox.setVisible(false);
			CustomerIDTextField.setEnabled(true);
			CreateButton.setVisible(true);
			EditButton.setVisible(false);
			SaveButton.setVisible(false);
		}
		else
		{
			IsActiveLabel.setVisible(true);
			IsActiveCheckBox.setVisible(true);
			CustomerIDTextField.setEnabled(false);
			CreateButton.setVisible(false);
			EditButton.setVisible(true);
			SaveButton.setVisible(true);			
		}
		
	}
	
 	public JButton getEditButton() {
		return EditButton;
	}

	public void EnableRelevantButtons(boolean res)
	{
			PasswordPasswordField.setEnabled(res);
			PasswordValidatePasswordField.setEnabled(res);
			UserNametextField.setEnabled(res);
			IsActiveCheckBox.setEnabled(res);
			FirstNameTextField.setEnabled(res);
			LastNameTextField.setEnabled(res);
			EmailTextField.setEnabled(res);
			PhoneFormattedTextField.setEnabled(res);
			CreditCardFormattedTextField.setEnabled(res);
			PrivateCustomerRadioButton.setEnabled(res);
			CommercialCustomerRadioButton.setEnabled(res);
			PurchasePlanComboBox.setEnabled(res);
			CostingModelComboBox.setEnabled(res);
	}
 	
 	public JButton getSaveButton() {
		return SaveButton;
	}
 	
 	public callbackCustomer getFullCallbackCustomer(){
		callbackCustomer customerCallback = new callbackCustomer();
		
		customerCallback.setUserName(UserNametextField.getText().trim());
		customerCallback.setUserPassword(PasswordPasswordField.getText().trim());
		customerCallback.setCustomersID( Integer.valueOf(CustomerIDTextField.getText().trim()) );
		customerCallback.setCustomerFirstName(FirstNameTextField.getText().trim());
		customerCallback.setCustomerLastName(LastNameTextField.getText().trim());
		customerCallback.setEmail(EmailTextField.getText().trim());
		customerCallback.setPhoneNumber(PhoneFormattedTextField.getText().trim());
		customerCallback.setCreditCard(CreditCardFormattedTextField.getText().trim());
		if (PrivateCustomerRadioButton.isSelected()) customerCallback.setCustomerType("Private");
		else customerCallback.setCustomerType("Commercial");
		customerCallback.setPlanID(getComboBoxSelection()+1);
		customerCallback.setCostingModelID(getCostingModelComboBoxSelection()+1);

		return customerCallback;
	}

 	public callbackCustomer getCustomerIdCallbackCustomer(){
		callbackCustomer customerCallback = new callbackCustomer();
		
		customerCallback.setCustomersID( Integer.valueOf(EnterCustomerIdTextField.getText().trim()) );

		return customerCallback;
	}
 	
	public callbackCar getFullCallbackCar() {
		
		callbackCar carCallback = new callbackCar();
		
		carCallback.setCarNumber(CarNumberFormattedTextField.getText().trim());
		carCallback.setFuelID(FuelIDComboBox.getSelectedIndex()+1);
		carCallback.setNFC(NFCNewCheckBox.isSelected());
		carCallback.setCustomerID(Integer.parseInt(CustomerIDTextField.getText().trim()));

		return carCallback;		

	}

	public void setSuccessAddingCarMessageLabel(){
		SuccessAddingCarMessageLabel.setText("Car number " + CarNumberFormattedTextField.getText().trim() + " added successfully to system");
		SuccessAddingCarMessageLabel.setVisible(true);
	}

	public void setFullCallbackCustomer(callbackCustomer fullCallbackCustomer) {
		this.fullCallbackCustomer = fullCallbackCustomer;
	}

	public JCheckBox getIsActiveCheckBox() {
		return IsActiveCheckBox;
	}

	

	
}

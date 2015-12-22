package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import callback.callbackBuffer;
import callback.callbackUser;
import client.Client;

public class MarketingRepresentativeGUI extends abstractPanel_GUI{
	
	
	private static final long serialVersionUID = 1L;
	
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
	private JTextField PasswordValidateTextField;
	private JTextField PasswordTextField;
	private JTextField UserNametextField;
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
	private JButton CreateButton;
	private JLabel MissedFieldsMessage;
	private JLabel InvalidPhoneMesaageLabel;
	private JLabel InvalidCreditCardMesaageLabel;

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
		

		/* ------- Adding Left layer to CreateNewCustomerAccount Panel -------- */
		CreateNewCustomerAccountLayer = new JLayeredPane();
		CreateNewCustomerAccountLayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		CreateNewCustomerAccountLayer.setBackground(new Color(169, 169, 169));

		//add to left container
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
		
		CreateNewUserCenterLayer();
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// display changes till the controller will build- please don't delete (Adir)
//		CardLayout ContainerCardCenter;
//		ContainerCardCenter = (CardLayout)(CenterCardContainer.getLayout());
//		ContainerCardCenter.show(CenterCardContainer,"CreateUserLayerCenter");
	//	CardLayout ContainerCardLeft;
	//	ContainerCardLeft	= (CardLayout)(LeftCardContainer.getLayout());
	//	ContainerCardLeft.show(LeftCardContainer, "CreateNewCustomerAccountLeft");			
	}
		
//Create User Account 
	
	public JButton getCreateNewCustomerAccountButton(){
		return CreateNewCustomerAccountButton;
	}	
	
	public JButton getCustomerDetailsButton(){
		return CustomerDetailsButton;
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
	
	public void CreateNewUserCenterLayer()
	{
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
		
		PurchasePlanComboBox = new JComboBox();
		PurchasePlanComboBox.setFont(new Font("Tahoma", Font.PLAIN, 17));
		PurchasePlanComboBox.setBounds(179, 438, 295, 27);
		AddPersonalDetails.add(PurchasePlanComboBox);
		
		CreateButton = new JButton("Create");
		CreateButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CreateButton.setBounds(678, 496, 125, 38);	
		AddPersonalDetails.add(CreateButton);
		
		MissedFieldsMessage = new JLabel("There is one or more missed fields");
		MissedFieldsMessage.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 17));
		MissedFieldsMessage.setEnabled(false);
		MissedFieldsMessage.setBounds(320, 493, 348, 46);
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
		
			
	}


}

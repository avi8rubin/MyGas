package GUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import java.awt.Label;
import java.awt.Button;
import javax.swing.JTextArea;
import com.toedter.calendar.JCalendar;
import com.toedter.components.JSpinField;
import javax.swing.JProgressBar;

public class LitafTester extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JTable ContactTable = new JTable();

	private JLayeredPane TopPanel = new JLayeredPane();
	private JLabel RoleLabel = new JLabel("<Role> Panel");
	private JLabel WelcomLabel = new JLabel("Welcom First + Last Name");
	private JLayeredPane LeftPanel = new JLayeredPane();
	private JLayeredPane CenterPanel = new JLayeredPane();
	private boolean ShowContacts = false;
	private JInternalFrame ContactFrame = new JInternalFrame("Contact List");
	private JScrollPane OrdersScrollPane= new JScrollPane();
	private JTable OrderfUpdateTable= new JTable();
	private JLayeredPane OrderDisplayLayer=new JLayeredPane();
	private JButton PayButton;
	private JButton CheckFuelOrdersButton;
	private JLabel ErrorMassage = new JLabel("");

	private JDateChooser ActivateSale_StartDateChooser;
	private JDateChooser ActivateSale_EndDateChooser;
	
	private JLabel Datelabel2;
	private JLabel Datelabel;

	//
	
	private JDateChooser StartDateChooser;
	private JDateChooser EndDateChooser;	
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LitafTester frame = new LitafTester();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LitafTester() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1300, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setResizable(false);
		this.setVisible(true);
		
		TopPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		TopPanel.setBackground(SystemColor.activeCaption);
		TopPanel.setOpaque(true);
		TopPanel.setPreferredSize(new Dimension(1295,150));
		contentPane.add(TopPanel, BorderLayout.NORTH);
		
		
		RoleLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		RoleLabel.setBounds(12, 0, 453, 47);
		TopPanel.add(RoleLabel);
		
		
		WelcomLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		WelcomLabel.setBounds(12, 40, 358, 24);
		TopPanel.add(WelcomLabel);
		
		JButton LogoutButton = new JButton("Logout");
		LogoutButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		LogoutButton.setBounds(1148, 13, 112, 38);
		TopPanel.add(LogoutButton);
		LogoutButton.addActionListener(new ActionListener() { 				//Add action listener
			public void actionPerformed(ActionEvent e) {
		        System.exit(0);
			}
		});
		
		
		JButton ContactsButton = new JButton("Contacts");
		ContactsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowContacts = !ShowContacts;
				ContactFrame.setVisible(ShowContacts);	
			}
		});
		ContactsButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ContactsButton.setBounds(1148, 64, 112, 38);
		TopPanel.add(ContactsButton);
		

		
		
		LeftPanel.setBackground(new Color(169, 169, 169));
		LeftPanel.setOpaque(true);
		LeftPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		LeftPanel.setPreferredSize(new Dimension(300,200));
		contentPane.add(LeftPanel, BorderLayout.WEST);
		
		JLabel LogoImage = new JLabel("");
		LogoImage.setIcon(new ImageIcon(abstractPanel_GUI_Tester.class.getResource("/images/Left_Panel_Logo22.jpg")));
		LogoImage.setBounds(37, 313, 239, 242);
		LeftPanel.add(LogoImage);
		
		
		CenterPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		contentPane.add(CenterPanel, BorderLayout.CENTER);
		
		// Contact list frame
		
		ContactFrame.setBounds(0, 0, 984, 555);
		ContactFrame.setVisible(false);
		//CenterPanel.add(ContactFrame);
		ContactFrame.getContentPane().setLayout(null);
		ContactTable.setBounds(12, 13, 944, 593);
		
		ContactFrame.getContentPane().add(ContactTable);
		JScrollPane scrollPane = new JScrollPane(ContactTable);
		scrollPane.setBounds(0, 612, 962, -612);
		ContactFrame.getContentPane().add(scrollPane);
		
		
		CenterPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		contentPane.add(CenterPanel, BorderLayout.CENTER);
		
		
		JLabel BuyHomeLabel = new JLabel("Buy Home Fuel");				
	BuyHomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		BuyHomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		BuyHomeLabel.setBounds(177, 13, 608, 42);
		CenterPanel.add(BuyHomeLabel);
//		
		JTextArea FuelAmounttextArea = new JTextArea();
		FuelAmounttextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		FuelAmounttextArea.setBounds(277, 82, 200, 38);
		CenterPanel.add(FuelAmounttextArea);
		
		JTextArea AddresstextArea = new JTextArea();
		AddresstextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		AddresstextArea.setBounds(277, 162, 200, 38);
		CenterPanel.add(AddresstextArea);
		PayButton= new JButton("Pay");
		PayButton.setBounds(631, 486, 124, 38);
		CenterPanel.add(PayButton);

	PayButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton Cancelbutton = new JButton("Cancel");
	Cancelbutton.setBounds(777, 486, 124, 38);
		CenterPanel.add(Cancelbutton);
	Cancelbutton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JFormattedTextField DeliveryTimetextArea = new JFormattedTextField();
		DeliveryTimetextArea.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DeliveryTimetextArea.setHorizontalAlignment(SwingConstants.CENTER);

	DeliveryTimetextArea.setBounds(277, 322, 200, 38);
	DeliveryTimetextArea.setValue("__ : ___");
		CenterPanel.add(DeliveryTimetextArea);
	
		JLabel Addresslabel = new JLabel("Address:");
		Addresslabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
	Addresslabel.setBounds(72, 162, 130, 38);
		CenterPanel.add(Addresslabel);
		
	JLabel DeliveryDatelabel = new JLabel("Delivery Date:");
		DeliveryDatelabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		DeliveryDatelabel.setBounds(72, 242, 130, 38);
CenterPanel.add(DeliveryDatelabel);
		
JLabel FuelAmountLabel = new JLabel("Fuel Amount:");
		FuelAmountLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		FuelAmountLabel.setBounds(72, 82, 130, 38);
		CenterPanel.add(FuelAmountLabel);
//		
		JLabel lblDeliveryTime = new JLabel("Delivery Time:");
		lblDeliveryTime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDeliveryTime.setBounds(72, 321, 146, 38);
		CenterPanel.add(lblDeliveryTime);
		
		JButton Calcbutton = new JButton("Calculate Price");
		Calcbutton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		Calcbutton.setBounds(72, 402, 169, 38);
	CenterPanel.add(Calcbutton);
	
		JTextArea CalcPricetextArea = new JTextArea();
		CalcPricetextArea.setBounds(277, 402, 200, 38);
	CenterPanel.add(CalcPricetextArea);
		
		JDateChooser dateChooser = new JDateChooser();
	dateChooser.setDateFormatString("dd/MM/yy");
		EndDateChooser.setFont(new Font("Tahoma", Font.PLAIN, 16));		
		dateChooser.setBounds(277, 242, 200, 38);
		CenterPanel.add(dateChooser);
		
		
		ErrorMassage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorMassage.setText("*Incorrect Fuel Amount");
	ErrorMassage.setForeground(Color.RED);
		ErrorMassage.setBounds(277, 116, 212, 38);
		CenterPanel.add(ErrorMassage);
	
		JLabel label = new JLabel("*Incorrect Date");
		label.setForeground(Color.RED);
		label.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label.setBounds(277, 273, 212, 38);
		CenterPanel.add(label);
		
		JLabel ErrorAddressLabel = new JLabel("*Incorrect Fuel Amount");
		ErrorAddressLabel.setForeground(Color.RED);
		ErrorAddressLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorAddressLabel.setBounds(277, 193, 212, 38);
		CenterPanel.add(ErrorAddressLabel);
		
		JLabel ErrorDeliveryTimeLabel = new JLabel("*Incorrect Fuel Amount");
		ErrorDeliveryTimeLabel.setForeground(Color.RED);
		ErrorDeliveryTimeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ErrorDeliveryTimeLabel.setBounds(277, 353, 212, 38);
		CenterPanel.add(ErrorDeliveryTimeLabel);
		
	////////////
		JLabel OrderDetailsLabel = new JLabel("Order Details:");
		OrderDetailsLabel.setFont(new Font("Tahoma", Font.BOLD, 20));

		OrderDetailsLabel.setBounds(609, 82, 146, 38);
		CenterPanel.add(OrderDetailsLabel);

		JLabel ShowOrderDetailsLabel = new JLabel("<html><body> Fuel Price:<br>"
				+ "+<br>"
				+ "  Liters:<br>"
				+ "		____________________<br>"
				+ "  Sum:<br><br>"
				+ "+Shipping:</body></html>");
		ShowOrderDetailsLabel.setBounds(609, 142, 176, 163);
		CenterPanel.add(ShowOrderDetailsLabel);
		ShowOrderDetailsLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		JLabel FuelPriceLabel = new JLabel("6.0");
		FuelPriceLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		FuelPriceLabel.setBounds(755, 142, 94, 22);
		CenterPanel.add(FuelPriceLabel);
		
		JLabel LitersLabel = new JLabel("100");
		LitersLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LitersLabel.setBounds(755, 188, 116, 22);
		CenterPanel.add(LitersLabel);

		JLabel sumLabel = new JLabel("600");
		sumLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sumLabel.setBounds(755, 234, 116, 22);
		CenterPanel.add(sumLabel);
		
		JLabel ShippingLabel = new JLabel("2%");
		ShippingLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ShippingLabel.setBounds(755, 278, 116, 22);
		CenterPanel.add(ShippingLabel);
		
		JLabel RemaraksLabel = new JLabel("<html>*Immediate order-within 6 hours "
				 + "is fuel cost plus 2% shipping from the"
				 + "fuel price</html>");
		RemaraksLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		RemaraksLabel.setForeground(Color.BLUE);
		RemaraksLabel.setBounds(609, 337, 200, 92);
		CenterPanel.add(RemaraksLabel);
		/////////////////////////
		/* ------- Adding new button to Top Panel -------- */
		/* ------- Adding new button to Top Panel -------- */

		CheckFuelOrdersButton = new JButton("Check Fuel Orders");
		CheckFuelOrdersButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		CheckFuelOrdersButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		CheckFuelOrdersButton.setBounds(217, 99, 200, 38);
		TopPanel.add(CheckFuelOrdersButton);
		
		JButton BuyHomeFuelButton= new JButton("Buy Home Fuel");
		BuyHomeFuelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		BuyHomeFuelButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		BuyHomeFuelButton.setBounds(12, 99, 185, 38);
		TopPanel.add(BuyHomeFuelButton);
		/*------- Create JTable surround with scroll pane and add
		 * 					 it to TariffApprovalPanel --------*/
		
		OrdersScrollPane.setBounds(43, 58,  900, 400);
		OrderDisplayLayer.add(OrdersScrollPane);
		
		OrdersScrollPane.setViewportView(OrderfUpdateTable);
		OrderfUpdateTable.setRowHeight(23);
		OrderfUpdateTable.setFillsViewportHeight(true);
		OrderfUpdateTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
		OrderfUpdateTable.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 15));
		OrderfUpdateTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//		.add(OrdersScrollPane);

		/* ------- Adding button and label to Tariff Panel -------- */
//		JLabel FuelsTariffLabel = new JLabel("Fuels Tariff");
//		FuelsTariffLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
//		FuelsTariffLabel.setBounds(48, 31, 147, 33);
		

		
		
	}
	public void setWelcomLabel(String FirstName, String LastName){
		WelcomLabel.setText("Welcom "+FirstName+" "+LastName);
	}
	
	public void setRoleLabel(String Role){
		RoleLabel.setText(Role+" Panel");
	}
}

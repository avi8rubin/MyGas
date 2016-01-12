package GUI;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.Scrollable;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.JButton;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import controller.*;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

public class Server_GUI {

	public JFrame frame;
	public Scrollable scrollBar;
	public JTextField textURL;
	public JTextField textUser;
	public JTextField textPassword;
	public JTextField textPort;
	public ServerController Control;
	public JTextArea textPane = new JTextArea();
	public int Word_Counter=0;
	public JButton btnStart;
	public JButton btnStop; 
	private JLabel MyIP;
	private JScrollPane scrollPane;
	public Server_GUI(){
		initialize();
	}
	/**
	 * Create server gui consul	screen
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(240, 240, 240));
		frame.setBounds(350, 300, 750, 434);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUrl = new JLabel("Url:");
		lblUrl.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUrl.setBounds(12, 13, 28, 16);
		frame.getContentPane().add(lblUrl);
		
		JLabel lblUser = new JLabel("User:");
		lblUser.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUser.setBounds(253, 13, 40, 16);
		frame.getContentPane().add(lblUser);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPassword.setBounds(388, 13, 78, 16);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPort.setBounds(579, 13, 46, 16);
		frame.getContentPane().add(lblPort);
		
		textUser = new JTextField();
		textUser.setText("root");
		textUser.setColumns(10);
		textUser.setBounds(301, 11, 84, 22);
		frame.getContentPane().add(textUser);
		
		textURL = new JTextField();
		textURL.setText("jdbc:mysql://localhost/mygas");
		textURL.setBounds(52, 11, 189, 22);
		frame.getContentPane().add(textURL);
		textURL.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 76, 720, 296);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(textPane);
		DefaultCaret caret = (DefaultCaret)textPane.getCaret();			//Always see the bottom of the text area
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);				//Always see the bottom of the text area
		textPane.setCaret(caret);
		
		textPassword = new JTextField();
		textPassword.setText("1234");
		textPassword.setColumns(10);
		textPassword.setBounds(476, 11, 84, 22);
		frame.getContentPane().add(textPassword);
		
		textPort = new JTextField();
		textPort.setText("5555");
		textPort.setColumns(10);
		textPort.setBounds(626, 11, 84, 22);
		frame.getContentPane().add(textPort);
		
		
		btnStart = new JButton("Start");
		btnStart.setBounds(12, 46, 97, 25);
		frame.getContentPane().add(btnStart);		
		btnStop = new JButton("Stop");
		btnStop.setBounds(589, 46, 97, 25);
		frame.getContentPane().add(btnStop);
		

		try {
			MyIP = new JLabel("My IP: "+Inet4Address.getLocalHost().getHostAddress());
			MyIP.setFont(new Font("David", Font.PLAIN, 17));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		MyIP.setBounds(263, 47, 181, 24);
		frame.getContentPane().add(MyIP);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(12, 76, 720, 296);
	}
	
	public int GetPort(){
		return Integer.parseInt(textPort.getText()); 
	}
	public String GetUser(){
		return textUser.getText();
	}
	public String GetPassword(){
		return textPassword.getText();
	}
	public String GetURL(){
		return textURL.getText();
	}
	public void setToPane(String str){
		try {
			Word_Counter+=str.length();
			textPane.getDocument().insertString(Word_Counter, str, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
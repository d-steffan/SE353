import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class LoginWindow implements ActionListener{

	JFrame frame;
	JButton btnNewButton;
	JLabel lblNewLabel,lblNewLabel2;
	public String Username;
	JCheckBox SafeSQL;
	private boolean successfulLogin = false;
	JTextField username;
	JPasswordField password;

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 180, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(10, 10, 140, 20);
		frame.getContentPane().add(lblNewLabel);
		
		username = new JTextField();
		username.setBounds(10,30,140,20);
		frame.getContentPane().add(username);
		
		lblNewLabel2 = new JLabel("Password");
		lblNewLabel2.setBounds(10, 50, 140, 20);
		frame.getContentPane().add(lblNewLabel2);
		
		password = new JPasswordField();
		password.setBounds(10,70,140,20);
		frame.getContentPane().add(password);
		
		SafeSQL = new JCheckBox("Safe SQL Login Code");
		SafeSQL.setBounds(8,90,150,20);
		SafeSQL.setSelected(true);
		SafeSQL.setToolTipText("<html>Unselect to Login via SQL Injection Auth Bypass or insert a new user as desired.<br>Hint: SHA-256 for '12345' is: 5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5<br>Have fun! :D </html>");
		frame.getContentPane().add(SafeSQL);
		
		btnNewButton = new JButton("Login");
		btnNewButton.setBounds(40, 120, 80, 20);
		btnNewButton.addActionListener(this);
		frame.getContentPane().add(btnNewButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Is volatile variable password.getPassword() in memory since it has to be generated for the call by value?
		if (e.getSource().equals(btnNewButton)) {
			if (SafeSQL.isSelected())
				this.successfulLogin = DatabaseConnection.GetInstance().checkLoginSafe(username.getText(), password.getPassword());
			else
				this.successfulLogin = DatabaseConnection.GetInstance().checkLoginUnsafe(username.getText(), password.getPassword());
			this.Username = username.getText();
			password.setText("");
		}
	}
	
	public boolean getSuccesfulLogin() {
		return this.successfulLogin;
	}
}

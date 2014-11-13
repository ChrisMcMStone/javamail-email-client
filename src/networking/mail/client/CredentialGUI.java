package networking.mail.client;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 * Represents the GUI for entering e-mail credentials to gain access to the
 * e-mail account.
 * 
 * @author Chris McMahon-Stone
 *
 */

public class CredentialGUI {

	JPasswordField pwd;
	JTextField usr;
	String username;
	String password;
	
	/**
	 * Constructor for username and password prompts
	 */
	
	public CredentialGUI() {

		username = null;
		password = null;
		usr = new JTextField(30);
		pwd = new JPasswordField(30);
		setUserName();
		setUserPassword();

	}

	/**
	 * Prompts the user for their email address.
	 */
	
	public void setUserName() {

		int action = JOptionPane.showConfirmDialog(null, usr, "Enter Username",
				JOptionPane.OK_CANCEL_OPTION);
		if (action < 0) {
			JOptionPane.showMessageDialog(null,
					"Cancel, X or escape key selected");
			System.exit(0);
		} else
			username = new String(usr.getText());
	}

	/**
	 * Prompts the user for their password.
	 */
	
	public void setUserPassword() {

		int action = JOptionPane.showConfirmDialog(null, pwd, "Enter Password",
				JOptionPane.OK_CANCEL_OPTION);
		if (action < 0) {
			JOptionPane.showMessageDialog(null,
					"Cancel, X or escape key selected");
			System.exit(0);
		} else
			password = new String(pwd.getPassword());

	}

	/**
	 * Returns the user email address as a string
	 * 
	 * @return username
	 */
	
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the user's password as a string
	 * 
	 * @return password 
	 */
	
	public String getPassword() {
		return password;
	}

}

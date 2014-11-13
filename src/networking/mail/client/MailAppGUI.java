package networking.mail.client;

import javax.swing.JFrame;
/**
 * Class holding the main method to initialise the credentials prompt
 * and then if successful details are entered, the email viewing GUI will open.
 * 
 * @author Chris McMahon-Stone
 *
 */

public class MailAppGUI {

	public static void main(String[] args) {
		
		CredentialGUI credential = new CredentialGUI();
		JFrame frame = new ViewMailGUI(credential.getUsername(), credential.getPassword());
		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);
		
	}

}

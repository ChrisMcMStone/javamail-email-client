package networking.mail.client;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JOptionPane;

import com.sun.mail.imap.IMAPFolder;

/**
 * @author Chris McMahon-Stone
 *
 */
public class ImapClient {

	Properties prop;
	Store store;
	IMAPFolder folder;

	/**
	 * Constructor for the IMAP client, set properties and initialises a session
	 * 
	 * @param username
	 * @param password
	 */

	public ImapClient(String username, String password) {

		prop = System.getProperties();
		store = null;
		folder = null;
		setMailProperties(username, password);
		initialiseSession();

	}

	/**
	 * Returns the folder containing the mail in the inbox
	 *
	 * @return IMAPFolder
	 */
	public IMAPFolder getMailFolder() {
		return folder;
	}

	/**
	 * 
	 * Set the required IMAP properties
	 * 
	 * @param username
	 * @param password
	 */
	private void setMailProperties(String username, String password) {

		prop.put("mail.user", username);
		prop.put("mail.password", password);
		prop.put("mail.store.protocol", "imaps");
		prop.put("mail.imap.host", "imap.googlemail.com");

	}

	/**
	 * 
	 * Initialises the IMAP session, creates the store for storing and
	 * retrieving messages. Populates an IMAPFolder with the inbox content.
	 * 
	 */
	private void initialiseSession() {

		Session session = Session.getDefaultInstance(prop);

		try {
			// Try to initialise session with given credentials
			store = session.getStore(prop.getProperty("mail.store.protocol"));
			store.connect(prop.getProperty("mail.imap.host"),
					prop.getProperty("mail.user"),
					prop.getProperty("mail.password"));

			folder = (IMAPFolder) store.getFolder("inbox"); // Get the inbox
															// folder
		} catch (MessagingException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Login failed."); // Show error
																	// message
																	// if login
																	// fails.
			System.exit(0);
		}
	}

}

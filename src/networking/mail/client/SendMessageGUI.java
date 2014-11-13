package networking.mail.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * Represents the GUI for creating and sending an email address.
 * 
 * @author Chris McMahon-Stone
 *
 */

public class SendMessageGUI extends JFrame {

	private JTextField subject;
	private JTextField recipients;
	private JTextField ccRecipients;
	private JTextArea messageBody;
	private JButton send;
	private ArrayList<File> attachedFiles;
	private JLabel lblAttach;

	/**
	 * Constructor for creating the JFrame and rendering all the components
	 */
	public SendMessageGUI() {

		// Call JFrame constructor giving a name for the window

		super("Compose e-mail");

		setBounds(100, 100, 434, 378);
		getContentPane().setLayout(null);

		// Add GUI components with absolute positioning.
		// (Mostly machine generated code)

		JLabel lblRec = new JLabel("Subject:");
		lblRec.setBounds(12, 25, 70, 15);
		getContentPane().add(lblRec);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(12, 52, 70, 15);
		getContentPane().add(lblTo);

		JLabel lblCC = new JLabel("CC:");
		lblCC.setBounds(12, 79, 70, 15);
		getContentPane().add(lblCC);

		subject = new JTextField();
		subject.setBounds(116, 22, 298, 21);
		getContentPane().add(subject);
		subject.setColumns(10);

		recipients = new JTextField();
		recipients.setColumns(10);
		recipients.setBounds(116, 49, 298, 21);
		getContentPane().add(recipients);

		ccRecipients = new JTextField();
		ccRecipients.setColumns(10);
		ccRecipients.setBounds(116, 76, 298, 21);
		getContentPane().add(ccRecipients);

		messageBody = new JTextArea();
		messageBody.setLineWrap(true);
		messageBody.setBounds(12, 122, 402, 181);
		messageBody.setBorder(ccRecipients.getBorder());
		getContentPane().add(messageBody);

		JButton attach = new JButton("Attach...");
		attach.setBounds(307, 101, 107, 15);
		getContentPane().add(attach);

		send = new JButton("Send");
		send.setBounds(297, 315, 117, 25);
		getContentPane().add(send);

		lblAttach = new JLabel("");
		lblAttach.setBounds(160, 101, 70, 15);
		getContentPane().add(lblAttach);

		// Initialise ArrayList of files to store the attachments
		attachedFiles = new ArrayList<File>();

		// Add the action listener for the attach button to call the file
		// selector when the button is pressed

		attach.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				attachFile();

			}
		});

		// Add the action listener for the send email button,
		// Calls the sendEmail method, informs the user whether or not the email
		// sent successfully and then closes this JFrame
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				sendEmail();
				JOptionPane.showMessageDialog(null, "Sent successfully.");
				dispose();
			}
		});

	}

	/**
	 * Method called when the attach button is clicked. Prompts the user the
	 * choose a file and then add the selected file to the arraylist.
	 * 
	 */
	protected void attachFile() {

		JFileChooser fileChooser = new JFileChooser();
		// Create file chooser and prompt user to select file
		int returnValue = fileChooser.showOpenDialog(this.getContentPane());
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			attachedFiles.add(fileChooser.getSelectedFile());
			// Add selected file to arraylist and display how many attachements
			// have been selected via a JLabel
			lblAttach.setText(attachedFiles.size() + "file(s) attached");
		}

	}

	/**
	 * Method called when the send email button is pressed. Initialises a new
	 * SMPTP client, parses the entered recipients and then hands over the rest
	 * of the work to the SMTP client.
	 */
	protected void sendEmail() {

		SmtpClient smtp = new SmtpClient();

		//Parse the recipients into an ArrayList
		
		ArrayList<String> recips = new ArrayList<String>();
		String contacts = recipients.getText();
		StringBuilder currentContact = new StringBuilder();

		for (char c : contacts.toCharArray()) {
			if (c == ' ') {
				recips.add(currentContact.toString());
				currentContact.delete(0, currentContact.length());
			} else {
				currentContact.append(c);
			}
		}
		recips.add(currentContact.toString());
		
		//Clear the StringBuilder
		currentContact.delete(0, currentContact.length());

		//Parse the CCrecipients into an ArrayList
		
		ArrayList<String> ccrecips = new ArrayList<String>();
		contacts = ccRecipients.getText();

		for (char c : contacts.toCharArray()) {
			if (c == ' ') {
				ccrecips.add(currentContact.toString());
				currentContact.delete(0, currentContact.length());
			} else {
				currentContact.append(c);
			}
		}
		ccrecips.add(currentContact.toString());

		
		// Call the sendMessage method in the SMTP client with appropriate
		// arguments
		smtp.sendMessage(messageBody.getText(), subject.getText(),
				attachedFiles, recips, ccrecips);

	}
}

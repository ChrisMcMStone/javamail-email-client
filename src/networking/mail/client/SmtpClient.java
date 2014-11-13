package networking.mail.client;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

import com.sun.mail.handlers.multipart_mixed;

/**
 * 
 * Represents the SMTP connection and sending email's via the server
 * 
 * @author Chris McMahon-Stone
 *
 */
public class SmtpClient {

	private Properties prop;
	private Session session;

	/**
	 * Constructor for the SMTP client. Required properties are set and the
	 * session is initiated.
	 */
	public SmtpClient() {

		prop = System.getProperties();

		setMailProperties();

		session = Session.getDefaultInstance(prop);

	}

	/**
	 * Set server and protocol properties.
	 */
	private void setMailProperties() {

		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

	}

	/**
	 * 
	 * Composes the e-mail from the given arguments and sends with the transport
	 * object.
	 * 
	 * @param messageBody
	 *            String
	 * @param subject
	 *            String
	 * @param attatchements
	 *            ArrayList<File>
	 * @param recipients
	 *            ArrayList<String>
	 * @param carbonRecipients
	 *            ArrayList<String>
	 */
	public void sendMessage(String messageBody, String subject,
			ArrayList<File> attatchements, ArrayList<String> recipients,
			ArrayList<String> carbonRecipients) {

		try {
			// Basic validation checks

			if (recipients.size() < 1) {
				throw new MessagingException("No recipient(s) selected");
			}

			// Adding recipients

			MimeMessage email = new MimeMessage(session);

			for (String s : recipients) {
				email.addRecipient(Message.RecipientType.TO,
						InternetAddress.parse(s)[0]);
			}

			// Adding CC recipients

			if (!carbonRecipients.get(0).equals("")) {
				for (String s : carbonRecipients) {
					email.addRecipient(Message.RecipientType.CC,
							InternetAddress.parse(s)[0]);
				}
			}

			// Set the sender property
			email.setFrom(prop.getProperty("mail.user"));
			email.setSubject(subject);

			// Initialise the main MultiPart to add all the email content to.
			MimeMultipart main = new MimeMultipart();

			// Create a MimeBodyPart to hold the body of the email.
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setText(messageBody);

			// Add the message body to the main multipart.
			main.addBodyPart(bodyPart);

			if (!attatchements.isEmpty()) {
				MimeBodyPart attachBody;
				for (File f : attatchements) {
					// Get the attachments from their file paths and add them to
					// the bodypart.
					attachBody = new MimeBodyPart();
					attachBody.attachFile(f.getAbsolutePath());
					main.addBodyPart(attachBody);
				}
			}

			// Set the content of the email and save.
			email.setContent(main);
			email.saveChanges();

			// Get the transport object from the session
			Transport tr = session.getTransport("smtp");
			tr.connect(
					prop.getProperty("mail.smtp.host"), // Connect
					prop.getProperty("mail.user"),
					prop.getProperty("mail.password"));
			tr.sendMessage(email, email.getAllRecipients()); // Send message to
																// given
																// recipients

		} catch (MessagingException | IOException e) {
			//Catch exception and inform user of problem.
			JOptionPane.showMessageDialog(null, e.getMessage());

		}

	}

}

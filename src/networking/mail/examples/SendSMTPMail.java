package networking.mail.examples;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.util.*;

public class SendSMTPMail {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		String username = "chrisxstone1@gmail.com";
		String password = "";
		String smtphost = "smtp.gmail.com";

		// Step 1: Set all Properties
		// Get system properties
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", "587");

		// Input password
		JPasswordField pwd = new JPasswordField(10);  
		int action = JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.OK_CANCEL_OPTION);  
		if(action < 0) {
			JOptionPane.showMessageDialog(null,"Cancel, X or escape key selected"); 
			System.exit(0); 
		}
		else 
			password = new String(pwd.getPassword());  

		// Set Property with username and password for authentication  
		props.setProperty("mail.user", username);
		props.setProperty("mail.password", password);

		//Step 2: Establish a mail session (java.mail.Session)
		Session session = Session.getDefaultInstance(props);

		try {

			// Step 3: Create a message
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress("heshan@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("heshan@gmail.com"));
			message.setSubject("Testing");
			message.setText("Dear SSC student,"
					+ "\n\n Try this example, please!");

			message.saveChanges();

			// Step 4: Send the message by javax.mail.Transport .			
			Transport tr = session.getTransport("smtp");	// Get Transport object from session		
			tr.connect(smtphost, username, password); // We need to connect
			tr.sendMessage(message, message.getAllRecipients()); // Send message


			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}


}

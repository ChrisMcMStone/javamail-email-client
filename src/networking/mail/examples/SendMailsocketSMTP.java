package networking.mail.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SendMailsocketSMTP {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket socket       = new Socket("mail-relay.cs.bham.ac.uk", 25);
		BufferedReader in   = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out     = new PrintWriter(socket.getOutputStream(), true);


		System.out.println(in.readLine());

		out.println("MAIL From: szh@cs.bham.ac.uk");
		System.out.println(in.readLine());

		// send to this address
		out.println("RCPT To: szh@cs.bham.ac.uk");
		System.out.println(in.readLine());

		out.println("DATA");
		System.out.println(in.readLine());

		// write message, and end with a . on a line by itself
		out.println("Date: Wed, 27 Aug 2003 11:48:15 -0400");
		out.println("From: Nobody Here <nobody@cnn.com>");
		out.println("Subject: Just wanted to say hi");
		out.println("To: sscbirmingham@gmail.com");
		out.println("X-Mailer: Spam Mailer X");
		out.println("This is the message body.");
		out.println("Here is a second line.");
		out.println(".");
		System.out.println(in.readLine());

		// clean up
		out.close();
		in.close();
		socket.close();
	}
}

package networking.tcp.examples;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class EchoClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InetAddress localhost;
		localhost = InetAddress.getLocalHost();	


		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			// echoSocket = new Socket("taranis", 7);
			String InputServerAddress = JOptionPane.showInputDialog(
			"Enter IP Address of a machine that is running a echo service on port 10900:");
			if ((InputServerAddress != null) && (InputServerAddress.length() > 0)) {
				echoSocket = new Socket(InputServerAddress, 10900);			
			}
			else {
				System.out.println("No IP address is provided, I will contact to locl host.");
				echoSocket = new Socket(localhost, 10900); // Notice the difference of the first parameter.			
			}


			out = new PrintWriter(echoSocket.getOutputStream(), true);	

            
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + localhost);
			System.exit(1);
		}

		try {
			BufferedReader stdIn = new BufferedReader(
					new InputStreamReader(System.in));
			String userInput;

			System.out.print ("input: ");
			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println("echo: " + in.readLine());
				System.out.print ("input: ");
			}

			out.close();
			in.close();
			stdIn.close();
			echoSocket.close();
		}
		catch (SocketException e) 
		{
			System.err.println("Socket connection failed."); 
			System.exit(1); 	
		}
	}		
}



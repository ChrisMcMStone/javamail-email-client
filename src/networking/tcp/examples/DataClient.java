package networking.tcp.examples;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

public class DataClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InetAddress localhost;
		localhost = InetAddress.getLocalHost();	
		Socket s = null;
		Integer portnum = 1090;
		
		String InputServerAddress = JOptionPane.showInputDialog(
				"Enter IP Address of a machine that is running a mysterial service on port 1090: ");
		if ((InputServerAddress != null) && (InputServerAddress.length() > 0)) {
			// Step 1: Open a socket.
			s = new Socket(InputServerAddress, portnum);			
		}
		else {
			System.out.println("No IP address is provided, I will contact to locl host." + localhost);
			s = new Socket(localhost, portnum); // Notice the difference of the first parameter.			
		}
		//  Step 2: Open an input stream and output stream to the socket.
		BufferedReader input =
			new BufferedReader(new InputStreamReader(s.getInputStream()));
		// Step 3: Read from and write to the stream according to the server's protocol.
		String answer = input.readLine();
		JOptionPane.showMessageDialog(null, answer);
		
		// Step 4: Close the streams.
		input.close();
		
		// Step 5: Close the socket. 
		s.close();
		System.exit(0);
	}

}

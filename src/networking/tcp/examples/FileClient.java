package networking.tcp.examples;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

public class FileClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InetAddress localhost;
		localhost = InetAddress.getLocalHost();	
		Socket fileSocket = null;
		Integer portnum = 1234;

		String InputServerAddress = JOptionPane.showInputDialog(
		"Enter IP Address of a machine that hosts a secret file: ");
		if ((InputServerAddress != null) && (InputServerAddress.length() > 0)) {
			// Step 1: Open a socket.
			fileSocket = new Socket(InputServerAddress, portnum);			
		}
		else {
			System.out.println("No IP address is provided, I will contact to local host.");
			fileSocket = new Socket(localhost, portnum); // Notice the difference of the first parameter.			
		}
		//  Step 2: Open an input stream and output stream to the socket.

		DataInputStream d = new DataInputStream(fileSocket.getInputStream());
		// First get file size.
		int length = d.readInt();
		System.out.println("File size is: "+length);
		
		// Then get file name
		String FileNameStr = d.readUTF();
		// We need to change the file name since we are using local host
		FileNameStr = FileNameStr.replaceFirst(".htm", ".bak.htm");
		
		// Then get the file
		byte[] mybytearray = new byte[length];
		d.readFully(mybytearray);
		
		FileOutputStream fos = new FileOutputStream(FileNameStr);
		DataOutputStream os = new DataOutputStream(fos);		
		os.write(mybytearray, 0, mybytearray.length);
		
		System.out.println("I have got the file and saved it to:" +  FileNameStr);

		// Step 4: Close the streams.
		os.close();
		fos.close();

		// Step 5: Close the socket. 
		fileSocket.close();
		System.exit(0);
	}

}

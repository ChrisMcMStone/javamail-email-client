package networking.udp.examples;
import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;

public class UDPClient {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		InetAddress host;	
		DatagramSocket aSocket = null;

		host = InetAddress.getLocalHost();	
		Integer serverPort = 1090;
		
		String InputServerAddress = JOptionPane.showInputDialog(
				"Enter IP Address of a machine that is running a mysterial service on port 1090: ");
		if ((InputServerAddress != null) && (InputServerAddress.length() > 0)) {
			// Step 1: Open a socket.
			InetAddress.getByName(InputServerAddress);	
		}
		else {
			System.out.println("No IP address is provided, I will contact to local host." + host);	
		}
		
		// Show a dialog to get input string from user
		String message =  JOptionPane.showInputDialog(null, "Enter in some text and I will tell you how many words:");
		

		try {
			System.out.println("Connect to host is: " + host.toString());	
			
			// Create a new DatagramSocket
			aSocket = new DatagramSocket();
			
			// Prepare datagram for sending
			// First get bytes from String
			byte [] m = message.getBytes();
			// Then construct DatagramPacket
			// We need two arguments: a byte array that contains client-specific data and the length of the byte array
			// We also need the destination IP address and port number
			DatagramPacket request = 
				new DatagramPacket(m, message.length(), host, serverPort);
			// Send DatagramPacket
			aSocket.send(request);
			
			// Prepare new datagram first for holding the incoming packet.
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
			aSocket.receive(reply);
			String ReplyStr = new String(reply.getData(), 0, reply.getLength());
		
			System.out.println("Reply from server is: " + ReplyStr);
		}
		catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		}
		catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
		finally {
			if (aSocket != null) 
				aSocket.close();
		}
	}
}

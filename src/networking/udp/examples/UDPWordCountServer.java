package networking.udp.examples;
import java.net.*;
import java.io.*;

public class UDPWordCountServer {
	
	private static int WordCount(String str) {
		int wordChar = 0;
		boolean prevCharWasSpace=true;
		for (int i = 0; i < str.length(); i++) 
		{
		    if (str.charAt(i) == ' ') {
		        prevCharWasSpace=true;
		    }
		else{
		        if(prevCharWasSpace) wordChar++;
		        prevCharWasSpace = false;

		    }
		}	
		return wordChar;
		
	}
	
	public static void main(String args[]){
		DatagramSocket aSocket = null;

		try {
			int socket_no = 1090;
			aSocket = new DatagramSocket(socket_no);
			byte[] buffer = new byte[1000];
			while(true) {
				DatagramPacket request = new DatagramPacket(buffer,
						buffer.length);
				aSocket.receive(request);
				byte[] received_data = request.getData();
				// Convert byte[] to String
				String str = new String(received_data, "UTF-8");
				Integer NumWord = WordCount(str);
				String NumWordStr = NumWord.toString();
				
				System.out.println(received_data);
				System.out.println(NumWordStr);
				
				// Convert String to byte[]
				byte [] m = NumWordStr.getBytes();
				
				// DatagramPacket only accept byte array as its argument
				DatagramPacket reply = new DatagramPacket(m,
						NumWordStr.length(),request.getAddress(),
						request.getPort());
				
				// Use send method to send datagram
				aSocket.send(reply);
			}
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

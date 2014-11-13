package networking.tcp.examples;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileServer {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		// TODO Auto-generated method stub
		Integer portnum = 1234;
		ServerSocket listener = new ServerSocket(portnum);
		String FileNameStr = "yourfile.htm";
		File secretFile = new File(FileNameStr);


		while (true) {
			Socket socket = listener.accept();
			System.out.println("Sending file...");

			DataInputStream filestream = new DataInputStream(new 
					FileInputStream(secretFile));

			DataOutputStream os =
				new DataOutputStream(socket.getOutputStream());

			byte[] mybytearray = new byte[(int) secretFile.length()];
			filestream.read(mybytearray);
			
			os.writeInt((int) secretFile.length());
			os.writeUTF(FileNameStr);
			os.write(mybytearray, 0, mybytearray.length);

			filestream.close();
			os.close();
			socket.close();

		}



	}
}



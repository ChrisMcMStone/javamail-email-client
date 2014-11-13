package networking.tcp.examples;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null; 


		serverSocket = new ServerSocket(10900); 


		Socket clientSocket = null; 
		System.out.println ("Waiting for connection.....");


		clientSocket = serverSocket.accept(); 



		System.out.println ("Connection successful");
		System.out.println ("Waiting for input.....");

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);			
			BufferedReader in = new BufferedReader( 
					new InputStreamReader( clientSocket.getInputStream())); 

			String inputLine; 

			while ((inputLine = in.readLine()) != null) 
			{ 

				if (inputLine.equalsIgnoreCase("Bye.")) {
					System.out.println ("Server: Bye!");
					out.println(inputLine);
					break; 
				}
				else {
					System.out.println ("Server: " + inputLine); 
					out.println(inputLine);
				}
			} 

			out.close(); 
			in.close(); 
			clientSocket.close(); 
			serverSocket.close(); 
		}
		catch (SocketException e) 
		{
			System.err.println("Socket connection failed."); 
			System.exit(1); 	
		}
	} 





}

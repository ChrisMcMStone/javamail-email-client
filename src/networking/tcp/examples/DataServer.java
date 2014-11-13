package networking.tcp.examples;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DataServer {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// Step 1: Open the Server Socket:
		Integer portnum = 1090;
        ServerSocket listener = new ServerSocket(portnum);
        try {
            while (true) {
            	//Step 2: Wait for the Client Request: 
                Socket socket = listener.accept();
                try {
                	// Alternative way of doing Step 3
                    //PrintWriter out =
                        //new PrintWriter(socket.getOutputStream(), true);
                    //out.println(new Date().toString());
                    //out.println("Go back to study!");
                	
                	
                	// Step 3: Create I/O streams for communicating to the client
                    DataOutputStream os =
                    	new DataOutputStream(socket.getOutputStream());
                    
                    // Step 4: Perform communication with client
                    os.writeBytes("Computer says no...");
                    // Step 5: Close the stream 
                    os.close();
                } finally {
                	//Step 6: Close the socket:
                    socket.close();
                }
            }
        }
        finally {
        	// Step 6: Close the socket:
            listener.close();
        }
	}

}

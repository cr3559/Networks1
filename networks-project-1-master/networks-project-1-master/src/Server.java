import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server 
{
	ServerSocket serverSocket;
	
    public void setupServer()
    {    	  
        try
        {
            // Everything up through bind
            serverSocket = new ServerSocket(4446); // 4446 is the port number the server will listen to
						   // only one application can listen to a port at a time
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try
        {
         // Have the server listen and accept if someone tries to connect.
	    //Blocking call - it will sit and wait here until a Client tries to connect.

	    //Socket contains all of the info about the connection
        	
            Socket incomingClient = serverSocket.accept();

	    //Now we need a way to send and recieve messages using the Socket.
            
            Scanner in = new Scanner(incomingClient.getInputStream()); //Listen for messages sent to us
            

	    //Lets us send messages to the Client
            
            DataOutputStream out = new DataOutputStream(incomingClient.getOutputStream());
            out.flush(); // Without this, the program may malfunction

            String message = in.nextLine(); // MESSAGE SENT BY CLIENT
            
             String messageToClient = reverseStringOrder(message); //reverses the string
            
             System.out.println(message);

            out.writeBytes(messageToClient + "\n");

            // If the client closes the connection if all goes well I will realize
            // it as well and my side will be closed automatically.

            // If I fail to realize the connection was closed then I may accidentally
            // leave it running and waste resources.  Why it is good to send "Are You There?"
            // messages from time to time.
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * Reverses the string that is passed into the method
     * @param forwardString the original string to be reversed
     * @return the reversed String
     */
    public static String reverseStringOrder(String forwardString)
    {
    	String reverseString = "";
    	int lastIndex = forwardString.length() -1;
    	for(int i = lastIndex; i>=0; i-- )
    	{
    		reverseString = reverseString + forwardString.charAt(i);
    	}
		return reverseString;
    	
    }


	public static void main(String[] args) 
	{
		Server server = new Server();
		server.setupServer();


	}

}


/**
 * Handles Client Requests as threads on the server
 * 
 * ChristopherRoadcap
 * James Marah
 * Project 1
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;



public class RequestHandler implements Runnable
{
	Socket socket;
	
	
	//Handles requests of clients as a thread
	public RequestHandler(Socket socket) throws IOException
	{
		this.socket = socket;
	}
	
	//Run method of thread
	public void run()
	{
		try 
		{
			while(true)
			{
				
			//OutputStream of server, writes back to client
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			//Reads message sent by client
			Scanner scanner = new Scanner(socket.getInputStream());
			String message = null;
			//The message to send back to the client
			if(scanner.hasNextLine())
			{
				message = scanner.nextLine();
				System.out.println(message);
				
				//Sends reversed message back to client
				output.writeBytes(reverseStringOrder(message));
				output.flush();
			}
		
			} 
		}
		catch (IOException e) 
		{
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
	    	//removes the new line character at the end of the string, then reverses the new string
	    	//with a new line character on the end
	    	reverseString.replace("\n", "");
			return reverseString + "\n";
	    	
	    }

}

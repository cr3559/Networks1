import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class RequestHandler implements Runnable
{
	Socket socket;
	
	
	
	public RequestHandler(Socket socket) throws IOException
	{
		this.socket = socket;
	}
	
	
	
	public void run()
	{
		try 
		{
			while(true)// Added this while loop to get C code to run
			{
			//OutputStream of server
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			//Reads input Stream of server
			Scanner scanner = new Scanner(socket.getInputStream());
			String message = scanner.nextLine();
			System.out.println(message);
			
			//Sends reversed message back to client
			output.writeBytes(reverseStringOrder(message));
			output.flush();
			
			//socket.getOutputStream().close();
			//socket.getInputStream().close();
			//scanner.close();
			
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
	    	reverseString.replace("\n", "");
			return reverseString + "\n";
	    	
	    }

}

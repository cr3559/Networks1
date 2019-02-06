
/**
 * A simple client that takes a 10 character string from the user
 * and sends it to a server. The client then listens for a message 
 * back from the server 
 * 
 * Christopher Roadcap
 * James Marah
 * 
 * Project 1
 * Java Client
 */
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
public class Client 
{
	Socket clientSocket;

    public void setupClient()
    {	
    	
    	//Scanner used to collect input from the user
    	Scanner userInput = new Scanner(System.in);
        try
        {	//sets up a socket that will try to connect to the specific address and port number.
        	this.clientSocket = new Socket("127.0.0.1", 4446); 
        	
        	//Each client may send 10 messages before closing
        	int i = 0;
        	while(i < 10)
        	{
        		
	            // Used to write messages to the server
	            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream()); //USE PrintWriter out = new PrintWriter<clientSocket.getOutputStream(),true);(FROM GIRARD);
	            out.flush(); 
	            
	            //Reads messages sent by the server
	            Scanner in = new Scanner(clientSocket.getInputStream());
	            
	            //String that holds the users input
	            String userString = userInput.nextLine() + "\n";
	            
	            //Ensures the string input by the user is exactly 11 characters (10 + \n);
	            while(userString.length() < 11 || userString.length() > 11)
	            {
	            	System.out.println("String is not 10 characters. Please enter a 10 character String.\n");
	            	userString = userInput.nextLine() + "\n";
	            }
	            
	            //Sends user input to the server
	            out.writeBytes(userString);
	            
	            //String sent back by the server
	            String message = in.nextLine();
	            
	            //prints the servers message to the client
	            System.out.println(message);
	            i++;
        	}

            // Done talking after 10 messages
            this.clientSocket.close();

        }
        //Bad address
        catch (UnknownHostException e) 
        {
            e.printStackTrace();
        }
        //Bad Connection
        catch (IOException e)	
        {
            e.printStackTrace();
        }

    }
    
    public static void main (String[] args)
    {
    	Client client = new Client();
    	client.setupClient();
    }
    
}

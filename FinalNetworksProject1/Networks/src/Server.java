/**
 * A simple server that listens for messages sent by clients.
 * the server then reverses the message, and sends it back to the client.
 * Requests are handled as threads via RequestHandlers.
 * 
 * Christopher Roadcap
 * James Marah
 * Project 1
 */
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
        	//Listens to port 4446
            serverSocket = new ServerSocket(4446); 	
        }
        //Bad port
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
        	//Server always listening for requests via loop
        	while(true)
        	{
        		// Have the server listen and accept if someone tries to connect.
        		//Creates a new thread for each socket accept
        		Thread thread =  new Thread(new RequestHandler(serverSocket.accept()));
        		thread.start();
        	}
        }
        //Bad connection
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) 
	{
		Server server = new Server();
		server.setupServer();


	}

}

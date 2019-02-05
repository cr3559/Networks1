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
            
            serverSocket = new ServerSocket(4446); 	// 4446 is the port number the server will listen to
						   							// only one application can listen to a port at a time
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try
        {

        	//Socket contains all of the info about the connection
        	while(true)
        	{
        		// Have the server listen and accept if someone tries to connect.
        		//Blocking call - it will sit and wait here until a Client tries to connect.
        		Thread thread =  new Thread(new RequestHandler(serverSocket.accept()));
        		thread.start();
            
        	}
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
	public static void main(String[] args) 
	{
		Server server = new Server();
		server.setupServer();


	}

}

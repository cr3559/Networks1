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

        try
        {
            // Setup a socket that will try to connect to a specific address(usually an IPv4 address)
            // and port number.
            Socket clientSocket = new Socket("127.0.0.1", 4446);

            // First the client sets up its send connection
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream()); //USE PrintWriter out = new PrintWriter<clientSocket.getOutputStream(),true);
            out.flush(); //Need this

            Scanner in = new Scanner(clientSocket.getInputStream());

            out.writeBytes("Hello Server\n");
            
            String message = in.nextLine();
            System.out.println(message);

            // Done talking
            clientSocket.close();

        }
        catch (UnknownHostException e) //bad address
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)	//error when setting up connection
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void main (String[] args)
    {
    	Client client = new Client();
    	client.setupClient();
    }

	
	

}

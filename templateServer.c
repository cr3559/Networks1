#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

/*TO DO
*Implement Threading.
*Check and enforce that User Input is exactly 10 characters
*in both Java and C.
*Clean up Code (unneeded print statements etc)
*/


/*
 * James Marah
 * Server - C version
 */

// Print the error and quit.
void diep(char *s)
{
	perror(s);
	exit(1);
}
 
void reverseString(char s[])
{
	char stringReversed[11];
	int length = strlen(s) -2;//to account for the null character
	for(int i = 0; i < strlen(s); i++)
	{
		stringReversed[i] = s[length];
		length--;
	}
	printf("String Reversed: %s\n",stringReversed);


	//strcpy(s,stringReversed);
	memcpy(s,stringReversed,10);

	printf("s: %s\n",s);

}

void setupServer()
{
	int servSocket;
	int incomingSocket;

	struct sockaddr_in serverInfo;
	struct sockaddr_in clientInfo;

	// Sets up the socket
	if ((servSocket = socket(PF_INET,SOCK_STREAM,IPPROTO_TCP)) < 0)
	    diep("socket() failed");

	// Key info about the server.
	serverInfo.sin_family = AF_INET;  // Internet address family
	serverInfo.sin_addr.s_addr=htonl(INADDR_ANY); // Any incoming interface.
	serverInfo.sin_port = htons(4446);  // Port to listen at.

	// Combine the socket and the serverInfo to be able to bind to a specific port.
	if (bind(servSocket, (struct sockaddr*)&serverInfo,sizeof(serverInfo)) < 0)
		diep("bind() failed");

	// Now I can listen at the port location
	if (listen(servSocket,100) < 0)
		diep("listen() failed");

	// See if there is a connection to accept.  Do not know if it stops and waits
	// for one.
	int clientLen = sizeof(clientInfo);
	int requests = 0;
	
	if ((incomingSocket = accept(servSocket,(struct sockaddr*)&clientInfo,(socklen_t*)&clientLen)) < 0)
		diep("accept() failed");

	
	
	int messageSize;
	int length = 11;
	char data[11];
	while(requests < 10)// we need to loop here because Java must make a new connection every time
	{
	if ((messageSize = recv(incomingSocket,data,length,MSG_WAITALL)) < 0)
	{
		diep("recv() failed");
	}
	

	printf("Got: %s\n",data);
	reverseString(data);
	sleep(1);
	write(incomingSocket, data, strlen(data));
	puts("Sent reversed message back to Client");
	
	requests++;
	fflush(stdout);

	}
	close(incomingSocket);
	

}
int main()
{
	setupServer();

	return 1;
}
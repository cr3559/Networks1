#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>



/*
 * James Marah
 * Chris Roadcap

 * Server - C version
 */
int servSocket;
int incomingSocket;
struct sockaddr_in clientInfo;
int requests = 0;
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
	memcpy(s,stringReversed,10);

	

}

void *clientHandler(void *cl)
{
	
	
	int messageSize;
	int length = 11;
	char data[11];
	int thisSocket = incomingSocket; // need to reasign here because incoming socket
									 // is reassigned during accept

	while(requests < 10)
	{
		if ((messageSize = recv(thisSocket,data,length,MSG_WAITALL)) < 0)
		{
			diep("recv() failed");
		}
		

		printf("Got: %s\n",data);
		reverseString(data);
		write(thisSocket, data, strlen(data));
		puts("Sent reversed message back to Client");
		requests++;
		fflush(stdout);
		sleep(1);
		
	}
	pthread_exit(0);

	
}


void setupServer()
{
	
	
	struct sockaddr_in serverInfo;
	
	

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


pthread_t clientThread; //the thread to be created
int clientLen = sizeof(clientInfo);


//Server continuously loops looking for new connections
	while(1)
	{
	


	//Creates a new thread for each new accepted connection
	while((incomingSocket = accept(servSocket,(struct sockaddr*)&clientInfo,(socklen_t*)&clientLen)))
		
	{
			pthread_create(&clientThread, NULL, &clientHandler, NULL);
	}

	//Thread returns here after 10 messages sent
	pthread_join(clientThread, NULL);

	}
		
}
int main()
{
	setupServer();

	return 1;
}

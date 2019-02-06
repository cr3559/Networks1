#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

/*
 * James Marah
 * Chris Roadcap
 * Group 7
 * Client - C Version
 */
void diep(char *s)
{
	perror(s);
	exit(1);
}

//Sets up client to communicate with the specified server
void setupClient()
{
	struct sockaddr_in serverInfo;
    int clientSocket;

    if ((clientSocket = socket(PF_INET,SOCK_STREAM,IPPROTO_TCP)) < 0)
    {
    	diep("socket() failed");
    }

    //Server Information that we wish to connect to
    serverInfo.sin_family = AF_INET;
    serverInfo.sin_addr.s_addr = inet_addr("127.0.0.1");
    serverInfo.sin_port = htons(4446);

    //Connect to the server
    if (connect(clientSocket, (struct sockaddr*)&serverInfo,sizeof(serverInfo)) < 0){
	diep("connect() failed");}
	
	int sendCount = 1;
	do{
		printf("Type a 10 character message: ");
		
		//Array to hold user input
	    char data[11];
		scanf("%s", data);
		strcat(data, "\n");

		
		//Enforces the user to input the correct sized string
		while(strlen(data) != 11 )
		{
			printf("Message not 10 characters. Please enter a 10 character message.\n");
			scanf("%s", data);
			strcat(data, "\n");

		}

		//Sends the string to the server
	    int length = strlen(data);
	    if (send(clientSocket,data,length,0) != length)
			{
				diep("send() sent a different number of bytes than expected.");
			}
		
		puts("Sent message to Server");

		//sleeps here to eliminate race condition
		sleep(1);

		//holds the message recieved by the server
		char datab[11];
		if (recv(clientSocket,datab,11,0) < 0)
			{
				diep("recv() got a different number of bytes than expected.");
			}
		
		printf("Received Message from Server: %s\n", datab);
		sendCount++;
		
	}while(sendCount <= 10);

	//Closes the socket after 10 messages sent
	printf("10 messages sent by client, closing Socket.\n");
    close(clientSocket);
}
int main()
{
	setupClient();

	return 1;
}
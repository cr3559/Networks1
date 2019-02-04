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
 * Group 7
 * Client - C Version
 */
void diep(char *s)
{
	perror(s);
	exit(1);
}
void setupClient()
{
	struct sockaddr_in serverInfo;
    int clientSocket;

    if ((clientSocket = socket(PF_INET,SOCK_STREAM,IPPROTO_TCP)) < 0)
    {
    	diep("socket() failed");
    }

    // Fill in the information for the server I want to connect to.
    serverInfo.sin_family = AF_INET;
    serverInfo.sin_addr.s_addr = inet_addr("127.0.0.1");
    serverInfo.sin_port = htons(4446);

    // Actually try and connect to the server.
    if (connect(clientSocket, (struct sockaddr*)&serverInfo,sizeof(serverInfo)) < 0){
	diep("connect() failed");}
	
	int sendCount = 1;
	do{
	printf("Type a 10 character message: ");
	
    char data[11];
	scanf("%s", data);
	strcat(data, "\n");
	
    int length = strlen(data);
    if (send(clientSocket,data,length,0) != length)
		{
			diep("send() sent a different number of bytes than expected.");
		}
	
	puts("Sent message to Server");
	sleep(1);
	char datab[11];
	if (recv(clientSocket,datab,11,0) < 0)
		{
			diep("recv() got a different number of bytes than expected.");
		}
	
	printf("Received Message from Server: %s\n", datab);
	sendCount++;
	
	}while(sendCount < 10);

    close(clientSocket);
}
int main()
{
	setupClient();

	return 1;
}

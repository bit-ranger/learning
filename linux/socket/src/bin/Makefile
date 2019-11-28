# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi

all: local net

local: local_client.c local_server.c
	$(CC) local_client.c -o local_client
	$(CC) local_server.c -o local_server

net: net_client.c net_server.c
	$(CC) net_client.c -o net_client
	$(CC) net_server.c -o net_server

hostinfo: hostinfo.c
	$(CC) hostinfo.c -o hostinfo

serviceinfo: serviceinfo.c
	$(CC) serviceinfo.c -o serviceinfo

select: select.c
	$(CC) select.c -o select



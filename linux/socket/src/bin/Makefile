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

serviceinfo_tcp: serviceinfo_tcp.c
	$(CC) serviceinfo_tcp.c -o serviceinfo_tcp

select: select.c
	$(CC) select.c -o select

select_server_multi: select_server_multi.c
	$(CC) select_server_multi.c -o select_server_multi



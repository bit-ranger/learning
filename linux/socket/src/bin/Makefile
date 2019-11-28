# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi -pedantic

all: local net hostinfo serviceinfo_tcp select_server_multi serviceinfo_udp

local: local_client.c local_server.c
	$(CC) $(CFLAGS) local_client.c -o local_client
	$(CC) $(CFLAGS) local_server.c -o local_server

net: net_client.c net_server.c
	$(CC) $(CFLAGS) net_client.c -o net_client
	$(CC) $(CFLAGS) net_server.c -o net_server

hostinfo: hostinfo.c
	$(CC) $(CFLAGS) hostinfo.c -o hostinfo

serviceinfo_tcp: serviceinfo_tcp.c
	$(CC) $(CFLAGS) serviceinfo_tcp.c -o serviceinfo_tcp

select_server_multi: select_server_multi.c
	$(CC) $(CFLAGS) select_server_multi.c -o select_server_multi

serviceinfo_udp: serviceinfo_udp.c
	$(CC) $(CFLAGS) serviceinfo_udp.c -o serviceinfo_udp



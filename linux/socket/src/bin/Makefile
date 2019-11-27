# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi

all: cs1

cs1: client1.c server1.c
	$(CC) client1.c -o client1
	$(CC) server1.c -o server1

hostinfo: hostinfo.c
	$(CC) hostinfo.c -o hostinfo

serviceinfo: serviceinfo.c
	$(CC) serviceinfo.c -o serviceinfo

select: select.c
	$(CC) select.c -o select



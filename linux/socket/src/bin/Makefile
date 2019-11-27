# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi

all: cs1

cs1: local_client.c local_server.c
	$(CC) local_client.c -o local_client
	$(CC) local_server.c -o local_server





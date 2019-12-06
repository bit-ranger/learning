# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi

hello: hello.c
	$(CC) -c hello.c -o hello.o
	ar -r libhello.a hello.o
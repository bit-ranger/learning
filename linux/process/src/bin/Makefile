# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi

# Options for release
# CFLAGS = -O -Wall –ansi
# Local Libraries
THREAD_LIB = threadlib.a

popen: popen.c
	$(CC) popen.c -o popen

pipe: pipe.c
	$(CC) pipe.c -o pipe


pipe4: pipe4.c
	$(CC) pipe4.c -o pipe4

pipe_stdin: pipe_stdin.c
	$(CC) pipe_stdin.c -o pipe_stdin




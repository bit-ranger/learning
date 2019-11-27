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

all: popen pipe pipe4 pipe_stdin fifo_producer fifo_consumer

popen: popen.c
	$(CC) popen.c -o popen

pipe: pipe.c
	$(CC) pipe.c -o pipe


pipe4: pipe4.c
	$(CC) pipe4.c -o pipe4

pipe_stdin: pipe_stdin.c
	$(CC) pipe_stdin.c -o pipe_stdin

fifo_producer: fifo_producer.c
	$(CC) fifo_producer.c -o fifo_producer

fifo_consumer: fifo_consumer.c
	$(CC) fifo_consumer.c -o fifo_consumer

fifo_client: fifo_client.c fifo_client.h
	$(CC) fifo_client.c  -o fifo_client

fifo_server: fifo_server.c fifo_client.h
	$(CC) fifo_server.c  -o fifo_server




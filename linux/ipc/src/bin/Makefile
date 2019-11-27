# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi

all: semaphore shared_memery

semaphore: semaphore.c
	$(CC) semaphore.c -o semaphore

shared_memery: shared_memory1.c shared_memory2.c
	$(CC) shared_memory1.c -o shared_memory1
	$(CC) shared_memory2.c -o shared_memory2


message_queue: message_queue_producer.c message_queue_consumer.c
	$(CC) message_queue_consumer.c -o message_queue_consumer
	$(CC) message_queue_producer.c -o message_queue_producer
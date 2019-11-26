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

create_and_join: create_and_join.c
	$(CC) create_and_join.c -o create_and_join -D_REENTRANT -I/usr/include/nptl -L/usr/lib/nptl -lpthread

semaphore: semaphore.c
	$(CC) semaphore.c -o semaphore -D_REENTRANT -I/usr/include/nptl -L/usr/lib/nptl -lpthread




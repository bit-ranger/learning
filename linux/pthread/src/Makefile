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

mutex: mutex.c
	$(CC) mutex.c -o mutex -D_REENTRANT -I/usr/include/nptl -L/usr/lib/nptl -lpthread

attr_detached: attr_detached.c
	$(CC) attr_detached.c -o attr_detached -D_REENTRANT -I/usr/include/nptl -L/usr/lib/nptl -lpthread
	
attr_sched: attr_sched.c
	$(CC) attr_sched.c -o attr_sched -D_REENTRANT -I/usr/include/nptl -L/usr/lib/nptl -lpthread




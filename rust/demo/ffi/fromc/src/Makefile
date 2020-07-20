# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi

all: hello_static hello_dynamic

hello_static: hello_static.c
	$(CC) -c hello_static.c -o hello_static.o
	ar -r libhello_static.a hello_static.o

hello_dynamic: hello_dynamic.c
	$(CC) -shared -o libhello_dynamic.so -c -fPIC hello_dynamic.c

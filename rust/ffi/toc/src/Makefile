# Which compiler
CC = gcc
# Where to install
INSTDIR = /usr/local/bin
# Where are include files kept
INCLUDE = .
# Options for development
CFLAGS = -g -Wall -ansi

all: ccall

ccall: ccall.c
	$(CC) -g ccall.c -o ccall -ltoc -L../../../target/debug


# ldd 查看依赖
# LD_LIBRARY_PATH=../../../target/debug ldd ccall

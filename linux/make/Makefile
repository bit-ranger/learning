all: myapp
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
MYLIB = mylib.a
myapp: main.o $(MYLIB)
	$(CC) -o myapp main.o $(MYLIB)

$(MYLIB): $(MYLIB)(2.o) $(MYLIB)(3.o)
main.o: main.c a.h
2.o: 2.c a.h b.h
3.o: 3.c b.h c.h

clean:
	-rm main.o 2.o 3.o $(MYLIB) myapp
	-rm -rf myapp-1.0

install: myapp
	@if [ -d $(INSTDIR) ]; \
	then \ cp myapp $(INSTDIR);\
		chmod a+ x $(INSTDIR)/ myapp;\
		chmod og- w $(INSTDIR)/ myapp;\
		echo "Installed in $(INSTDIR)";\
	else \
		echo "Sorry, $(INSTDIR) does not exist";\
	fi


dist: myapp-1.0.tar.gz

myapp-1.0.tar.gz: myapp myapp.1
	-rm -rf myapp-1.0
	mkdir myapp-1.0
	cp *.c *.h *.1 Makefile myapp-1.0
	tar zcvf $@ myapp-1.0


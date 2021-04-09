CC = clang
CFLAGS = -Wall -Wextra -pedantic -std=c99

%.o: %.c kilo.h
	$(CC) -c -g -o $@ $< $(CFLAGS)

all: kilo etags

kilo: kilo.o terminal.o buffer.o helper.c row.c key.c
	$(CC) -o $@ $^  $(CFLAGS)

etags:
	etags *.c *.h

.PHONY = clean

clean:
	rm kilo *.o TAGS log.txt
	touch log.txt

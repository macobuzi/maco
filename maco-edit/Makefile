CC = clang
CFLAGS = -Wall -Wextra -pedantic -std=c99

%.o: %.c maco.h
	$(CC) -c -g -o $@ $< $(CFLAGS)

all: maco etags

maco: maco.o terminal.o buffer.o helper.c row.c key.c
	$(CC) -o $@ $^  $(CFLAGS)

etags:
	etags *.c *.h

.PHONY = clean

clean:
	rm maco *.o TAGS log.txt
	touch log.txt

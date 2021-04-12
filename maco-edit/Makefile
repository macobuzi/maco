CC = clang
CFLAGS = -Wall -Wextra -pedantic -std=c99

%.o: %.c maco.h
	$(CC) -c -g -o $@ $< $(CFLAGS)

all: maco etags

maco: maco.o terminal.o buffer.o helper.o row.o key.o action.o
	$(CC) -o $@ $^  $(CFLAGS)

etags:
	etags *.c *.h

.PHONY = clean

clean:
	rm maco *.o TAGS log.txt
	touch log.txt

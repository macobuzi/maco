#include <stdio.h>
#include <termios.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <unistd.h>
#include <stdlib.h>
#include <ctype.h>
#include "maco.h"

void die(const char *s) {
	clear_screen();
	perror(s);
	exit(1);	
}

void disable_raw_mode() {
	if(tcsetattr(STDIN_FILENO, TCSAFLUSH, &config.orig_termios) == -1)
		die("tcsetattr");
}

void enable_raw_mode() {
	struct termios raw;
	
	if(tcgetattr(STDIN_FILENO, &config.orig_termios) == -1)
		die("tcgetattr");
	raw = config.orig_termios;
	raw.c_iflag &= ~(BRKINT | ICRNL | INPCK | ISTRIP | IXON);
	raw.c_oflag &= ~(OPOST);
	raw.c_cflag |= ~(CS8);
	raw.c_lflag &= ~(ECHO | ICANON | ISIG | IEXTEN);
	raw.c_cc[VMIN] = 0;
	raw.c_cc[VTIME] = 1; /* 100ms */	

	if(tcsetattr(STDIN_FILENO, TCSAFLUSH, &raw) == -1)
		die("tcsetattr");
}

int read_key() {
	ssize_t nread;
	char c;

	while((nread = read(STDIN_FILENO, &c, 1)) != 1)
		if (nread == -1 && errno != EAGAIN)
	    	die("read");
//	write_log("read_key = %d\n", c);

	if (c == '\x1b') {
		char buf[3];

		if (read(STDIN_FILENO, &buf[0], 1) != 1)
			return c;
		if (read(STDIN_FILENO, &buf[1], 1) != 1)
			return c;

		if (buf[0] == '[') {
			if (buf[1] >= 0 && buf[1] <= 9) {
				if (read(STDIN_FILENO, &buf[2], 1) != 1)
					return c;
				if (buf[2] == '~') {
					switch(buf[1]) {
					case '3':
						return DEL_KEY;
					case '5':
						return PAGE_UP;
					case '6':
						return PAGE_DOWN;
					}
				}
			} else {
				switch(buf[1]) {
				case 'A':
					return ARROW_UP;
				case 'B':
					return ARROW_DOWN;
				case 'C':
					return ARROW_RIGHT;
				case 'D':
					return ARROW_LEFT;
				}
			}
		}
	}
    return c;
}

void clear_screen() {
	write(STDOUT_FILENO, "\x1b[2J", 4);	
	write(STDOUT_FILENO, "\x1b[H", 3);
}

int get_window_size(int *rows, int *cols) {
	struct winsize ws;
	
	if (ioctl(STDOUT_FILENO, TIOCGWINSZ, &ws) == -1 || ws.ws_col == 0)
		return -1;
	else {
		*cols = ws.ws_col;
		*rows = ws.ws_row;
		return 0;
	}
}

void write_log(char *pattern, int data) {
	fprintf(logfp, pattern, data);
	fflush(logfp);
}

void write_log_str(char *header, char *s) {
	fprintf(logfp, "%s\n", header);
	while(*s != '\0') {
		if (isprint(*s) || *s == '\n' || *s == '\r')
			fprintf(logfp, "%c", *s);
		else
			fprintf(logfp, "<%02x>", *s);
		s++;
	}
	fprintf(logfp, "\n");
		
	fflush(logfp);
}

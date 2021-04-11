#include <stdio.h>
#include <stdarg.h>
#include <time.h>
#include <ctype.h>
#include <unistd.h>
#include <termios.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <string.h>
#include "maco.h"

struct config config;
FILE *logfp;

void editor_draw_screen() {
	struct buffer main_buffer = EMPTY_BUFFER;
	buffer_append(&main_buffer, "\x1b[?25l", 6);   /* hide cursor */
	buffer_append(&main_buffer, "\x1b[2J", 4);     /* clear screen */
	buffer_append(&main_buffer, "\x1b[H", 3);      /* set cursor to 0,0 */
	editor_draw_rows(&main_buffer);
	editor_draw_status_bar(&main_buffer);
	editor_draw_message_bar(&main_buffer);

	// set cursor position
	char cmd[32];
	snprintf(cmd, sizeof(cmd), "\x1b[%d;%dH",
			 (config.cursor_y - config.offset_y) + 1,
			 (config.screen_cursor_x - config.offset_x) + 1
	);
	buffer_append(&main_buffer, cmd, strlen(cmd)); /* set cursor at (y,x) */
	buffer_append(&main_buffer, "\x1b[?25h", 6);   /* show cursor */

//	write_log("buffer len = %d\n", main_buffer.len);
//	write_log_str("-- main buffer --", main_buffer.text);
	write(STDOUT_FILENO, main_buffer.text, main_buffer.len);
}

void editor_draw_status_bar(struct buffer *bp) {
	int i, ll, rl;
	char lstat[80], rstat[80];
	
	buffer_append(bp, "\x1b[7m", 4);

	ll = snprintf(lstat, sizeof(lstat),
				 " %.20s - %d line",
				 config.filename ? config.filename : "[No Name]", /* file name */
				 config.num_rows                                  /* row nums  */
	);
	rl = snprintf(rstat, sizeof(rstat),
				  "%d/%d ",
				  config.cursor_y + 1,                            /* current row */
				  config.num_rows
	);
	if (ll > config.screen_cols)
		ll = config.screen_cols;
	buffer_append(bp, lstat, ll);
	for (i=ll; i<config.screen_cols; i++)
		if (i == config.screen_cols - rl) {
			buffer_append(bp, rstat, rl);
			break;
		} else {
			buffer_append(bp, " ", 1);
		}
	
	buffer_append(bp, "\x1b[m", 3);
	buffer_append(bp, "\r\n", 2);
}

void editor_draw_message_bar(struct buffer *bp) {
	buffer_append(bp, "\x1b[K", 3);
	int l = strlen(config.status_msg);
	if (l > config.screen_cols)
		l = config.screen_cols;
	if (l && time(NULL) - config.status_msg_time < 5)
		buffer_append(bp, config.status_msg, l);
}

void editor_init() {
	config.cursor_x = 0;
	config.cursor_y = 0;
	config.offset_x = 0;
	config.offset_y = 0;
	config.num_rows = 0;
	config.screen_cursor_x = 0;
	config.screen_cursor_y = 0;
	config.rows = NULL;
	config.status_msg[0] = '\0';
	config.status_msg_time = 0;

	if (get_window_size(&config.screen_rows, &config.screen_cols) == -1)
		die("get_window_size");
    // remove 2 row for status bar and message bar
    config.screen_rows-=2;

	editor_set_message("HELP: C-Q = Quit");
}

void editor_open(char *filename) {
	FILE *fp = fopen(filename, "r");
	char *text = NULL;
	size_t cap = 0;
	ssize_t len;

	if (!fp)
		die("open");

	config.filename = strdup(filename);
	while((len = getline(&text, &cap, fp)) != -1) {
		while (len > 0 && (text[len-1] == '\n' || text[len-1] == '\r'))
			len--;
		text[len] = '\0';
		editor_append_row(text, len);
	}
	free(text);
	fclose(fp);
}

void editor_set_message(const char *fmt, ...) {
	va_list ap;
	va_start(ap, fmt);
	vsnprintf(config.status_msg, sizeof(config.status_msg), fmt, ap);
	va_end(ap);
	config.status_msg_time = time(NULL);
}

int main(int argc, char *argv[]) {
	logfp = fopen("log.txt", "a");
    enable_raw_mode();
    atexit(disable_raw_mode);
    editor_init();

	if (argc >= 2) {
		editor_open(argv[1]);
	}

    while (1) {
    	editor_draw_screen();
		editor_handle_key_press();
    }
    return 0;
}

#include <stdio.h>
#include <ctype.h>
#include <unistd.h>
#include <termios.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <string.h>
#include "kilo.h"

struct config config;
FILE *logfp;

void editor_draw_screen() {
	struct buffer main_buffer = EMPTY_BUFFER;
	buffer_append(&main_buffer, "\x1b[?25l", 6);   /* hide cursor */
	buffer_append(&main_buffer, "\x1b[2J", 4);     /* clear screen */
	buffer_append(&main_buffer, "\x1b[H", 3);      /* set cursor to 0,0 */
	editor_draw_rows(&main_buffer);

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

void editor_init() {
	config.cursor_x = 0;
	config.cursor_y = 0;
	config.offset_x = 0;	
	config.offset_y = 0;
	config.num_rows = 0;
	config.screen_cursor_x = 0;
	config.screen_cursor_y = 0;
	config.rows = NULL;
	
	if (get_window_size(&config.screen_rows, &config.screen_cols) == -1)
		die("get_window_size");
}

void editor_open(char *filename) {
	FILE *fp = fopen(filename, "r");
	char *text = NULL;
	size_t cap = 0;
	ssize_t len;

	if (!fp)
		die("open");

	while((len = getline(&text, &cap, fp)) != -1) {
		while (len > 0 && (text[len-1] == '\n' || text[len-1] == '\r'))
			len--;
		text[len] = '\0';
		editor_append_row(text, len);
	}
	free(text);
	fclose(fp);
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

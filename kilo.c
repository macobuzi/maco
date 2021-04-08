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

void editor_handle_key_press() {
	int c = read_key();

//	write_log("key = %d\n", c);
	
	switch(c) {
		
	case CTRL_KEY('q'):
		clear_screen();
		exit(0);
		break;
	case ARROW_UP:
	case ARROW_DOWN:
	case ARROW_RIGHT:
	case ARROW_LEFT:
	case PAGE_UP:
	case PAGE_DOWN:
		editor_move_cursor(c);
		break;
	default:
		break;
	}	
}

struct row *editor_current_row() {
	return (config.cursor_y < config.num_rows)
		? &config.rows[config.cursor_y] : NULL;
}

void editor_move_cursor(int c) {
	int page_size;
	struct row *row = editor_current_row();
	
	switch(c) {
	case ARROW_LEFT:
		if (config.cursor_x > 0)
			config.cursor_x--;
		break;
	case ARROW_RIGHT:
		if (row && config.cursor_x < row->len)
			config.cursor_x++;
		break;
	case ARROW_UP:
		if (config.cursor_y > 0)
			config.cursor_y--;
		break;
	case ARROW_DOWN:
		if (config.cursor_y < config.num_rows)
			config.cursor_y++;
		break;
	case PAGE_UP:
	case PAGE_DOWN:
		page_size = config.screen_cols;
		while (page_size--)
			editor_move_cursor(c == PAGE_UP ? ARROW_UP : ARROW_DOWN);
		break;
	}
}

void editor_may_align() {
	struct row *row = editor_current_row();
	int len = row ? row->len : 0;
//	write_log("row len = %d\n", len);
//	write_log_str("-- row content --", row ? row->text : "");
	if (config.cursor_x > len)
		config.cursor_x = len;
}

void editor_may_scroll() {
//	write_log("sr = %d\n", config.screen_rows);
//	write_log("sc = %d\n", config.screen_cols);	
//	write_log("cy = %d\n", config.cursor_y);
//	write_log("oy = %d\n", config.offset_y);
//	write_log("cx = %d\n", config.cursor_x);
//	write_log("ox = %d\n", config.offset_x);	
	
	if (config.cursor_y < config.offset_y)
		config.offset_y = config.cursor_y;
	if (config.cursor_y >= config.offset_y + config.screen_rows)
		config.offset_y = config.cursor_y - config.screen_rows + 1;
	if (config.cursor_x < config.offset_x)
		config.offset_x = config.cursor_x;
	if (config.cursor_x >= config.offset_x + config.screen_cols)
		config.offset_x = config.cursor_x - config.screen_cols + 1;
}

void editor_draw_rows(struct buffer *bp) {
	int y, r;
	
	for (y=0; y<config.screen_rows; y++) {
		r = y + config.offset_y;
		if (r >= config.num_rows) {
			if (config.num_rows == 0 && y == config.screen_rows / 3) {
				char welcome[80];
				int len = snprintf(
					welcome,
					sizeof(welcome),
					"Kilo editor -- version %s",
					VERSION
				);
			
				if (len > config.screen_cols)
					len = config.screen_cols;
				int padding = (config.screen_cols - len) / 2;
				if (padding-- > 0)
					buffer_append(bp, "~", 1);
				while (padding-- > 0)
					buffer_append(bp, " ", 1);
				buffer_append(bp, welcome, len);
			} else {
				buffer_append(bp, "~", 1);
			}
		} else {
			int len = config.rows[r].len - config.offset_x;
			if (len > config.screen_cols)
				len = config.screen_cols;
			if (len > 0)
				buffer_append(bp, &config.rows[r].text[config.offset_x], len);
			
		}
		buffer_append(bp, "\x1b[K", 3); /* Remove screen row from cursor */
		if (y < config.screen_rows - 1)
		    buffer_append(bp, "\r\n", 2);
	}
}

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
			 (config.cursor_x - config.offset_x) + 1
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

void editor_append_row(char *text, size_t len) {
	int i = config.num_rows;

	config.rows = realloc(config.rows, sizeof(struct row) * (i + 1));
	config.rows[i].len = len;
	config.rows[i].text = malloc(len + 1);
	memcpy(config.rows[i].text, text, len + 1);
	config.num_rows++;
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
		editor_may_scroll();
		editor_may_align();
    }
    return 0;
}

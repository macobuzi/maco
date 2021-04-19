#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include "maco.h"

void editor_insert_char(char c) {
	write_log("editor_insert_char %c\n",c);
	if (config.cursor_y == config.num_rows)
		editor_insert_row(config.num_rows, "", 0);
	editor_row_insert_char(&config.rows[config.cursor_y], config.cursor_x, c);
	editor_move_cursor(ARROW_RIGHT);
}

void editor_save() {
	int fb_len, fd, errno;
	char *fb;
	
	if (config.filename == NULL) {
		config.filename = editor_prompt("Save as: %s");
	}

	fb = editor_rows_to_string(&fb_len);
	errno = fd = open(config.filename, O_RDWR | O_CREAT, 0644);
	write_log("free fd and db for save\n", 0);
	if (fd != -1) {
		if ((errno = ftruncate(fd, fb_len)) != -1) {
			if ((errno = write(fd, fb, fb_len)) != -1) {
				close(fd);
				free(fb);
				editor_set_message("%d bytes written to disk", fb_len);
				config.dirty = 0;
				return;
			}
		}
		close(fd);
	}
	free(fb);
	editor_set_message("Can't save! I/O error: %s", strerror(errno));
}

void quit() {
	
	if (config.dirty > 0 && quit_time > 0) {
		editor_set_message("WARNING!!! File have unsaved changes. "
						   "Press C-Q %d more times to quit", quit_time); 
		quit_time--;
		return;
	}
	
	clear_screen();
	exit(0);
}

void editor_delete_char() {
	write_log("editor_delete_char\n",0);
	
	struct row *row, *up_row;
	
	if (config.cursor_y == config.num_rows)
		return;
	if (config.cursor_x == 0 && config.cursor_y == 0)
		return;

	row = &config.rows[config.cursor_y];
	if (config.cursor_x > 0) {
//		write_log("Delete char", 0);
		editor_row_delete_char(row, config.cursor_x - 1);
		editor_move_cursor(ARROW_LEFT);
	} else {
		up_row = &config.rows[config.cursor_y-1];
		editor_row_append_string(up_row, row->text, row->len);
		editor_row_delete(config.cursor_y);
		editor_move_cursor(ARROW_UP);
		editor_move_cursor(LINE_END);		
	}
}

void editor_insert_new_row() {
	write_log("editor_insert_new_row\n",0);
	
	struct row *row;

	row = editor_current_row();
	if (row == NULL)
		write_log("NULL row\n", 0);
	editor_insert_row(config.cursor_y + 1,
					  &row->text[config.cursor_x],
					  row->len - config.cursor_x);
	row->len = config.cursor_x;
	row->text[row->len] = '\0';
	editor_update_row(row);
	editor_move_cursor(ARROW_DOWN);
	editor_move_cursor(LINE_START);
}

char* editor_prompt(char *prompt) {
	write_log("editor_prompt\n",0);
	
	char *buffer = malloc(PROMPT_SIZE);
	int len = 0;
	
	buffer[len] = '\0';

	while(len < PROMPT_SIZE) {
		editor_set_message(prompt, buffer);
		editor_draw_screen();

		char c = read_key();
		switch(c) {
		case '\x1b':
			editor_set_message("");
			free(buffer);
			return NULL;
		case '\r':
			if (len > 0) {
				editor_set_message("");
				return buffer;
			}
		default:
			if (!iscntrl(c) && c < 128 && len < PROMPT_SIZE-1) {
				buffer[len++] = c;
				buffer[len] = '\0';
			}
		}
	}
}

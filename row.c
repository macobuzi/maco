#include <stdio.h>
#include <ctype.h>
#include <unistd.h>
#include <termios.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <string.h>
#include "kilo.h"

void editor_append_row(char *text, size_t len) {
	int i = config.num_rows;

	config.rows = realloc(config.rows, sizeof(struct row) * (i + 1));
	config.rows[i].len = len;
	config.rows[i].text = malloc(len + 1);
	memcpy(config.rows[i].text, text, len + 1);

	config.rows[i].render_text = NULL;
	config.rows[i].render_len = 0;
	editor_update_row(&config.rows[i]);
	config.num_rows++;
}

void editor_update_row(struct row *row) {
	int i, j, k, tab_num;
	char c;
	
	free(row->render_text);

	tab_num = 0;
	for (i=0; i<row->len; i++)
		if (row->text[i] == '\t') tab_num++;
	row->render_text = malloc(row->len + tab_num * (TAB_SIZE - 1) + 1);

	for (i=0, j=0; i<row->len; i++) {
		k = 1;
		c = row->text[i];
		if (c == '\t') {
			c = ' ';
			k = TAB_SIZE;
		}
		while (k--) 
			row->render_text[j++] = c;
	}
	row->render_text[j] = '\0';
	row->render_len = j;
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
			int len = config.rows[r].render_len - config.offset_x;
			if (len > config.screen_cols)
				len = config.screen_cols;
			if (len > 0)
				buffer_append(bp, &config.rows[r].render_text[config.offset_x], len);
			
		}
		buffer_append(bp, "\x1b[K", 3); /* Remove screen row from cursor */
		if (y < config.screen_rows - 1)
		    buffer_append(bp, "\r\n", 2);
	}
}

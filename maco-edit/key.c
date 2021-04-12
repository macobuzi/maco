#include <stdio.h>
#include <ctype.h>
#include <unistd.h>
#include <termios.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/ioctl.h>
#include <string.h>
#include "maco.h"

void editor_handle_key_press() {
	int c = read_key();

//	write_log("key = %d\n", c);
	
	switch(c) {

	case '\r':                  /* LINE FEED */
		/* TODO */
		break;
	case BACKSPACE:
	case CTRL_KEY('h'):
	case DEL_KEY:
		/* TODO */
		break;
	case CTRL_KEY('l'):
	case '\x1b':                /* ESC */
		/* TODO */
		break;
	case CTRL_KEY('q'):
		clear_screen();
		exit(0);
		break;
	case CTRL_KEY('s'):
		editor_save();
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
		editor_insert_char(c);
		break;
	}	
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
		else if (row && config.cursor_x == row->len) {
			config.cursor_y++;
			config.cursor_x = 0;
		}
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
	default:
		break;
	}

	// update screen cursors
	if (config.cursor_y < config.num_rows)
		config.screen_cursor_x = editor_cursor_x_to_screen(row, config.cursor_x);

	// follow up... 
	editor_may_scroll();
	editor_may_align();
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
//	write_log("scx = %d\n", config.screen_cursor_x);	
	
	if (config.cursor_y < config.offset_y)
		config.offset_y = config.cursor_y;
	if (config.cursor_y >= config.offset_y + config.screen_rows)
		config.offset_y = config.cursor_y - config.screen_rows + 1;
	if (config.screen_cursor_x < config.offset_x)
		config.offset_x = config.screen_cursor_x;
	if (config.screen_cursor_x >= config.offset_x + config.screen_cols)
		config.offset_x = config.screen_cursor_x - config.screen_cols + 1;
}

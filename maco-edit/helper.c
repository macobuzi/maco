#include "maco.h"

struct row *editor_current_row() {
	return (config.cursor_y < config.num_rows)
		? &config.rows[config.cursor_y] : NULL;
}

int editor_cursor_x_to_screen(struct row *row, int cursor_x) {
	int i, scr;

	for (i=0, scr=0; i < cursor_x && i < row->len; i++)
		if (row->text[i] == '\t') scr += TAB_SIZE;
		else scr++;

	return scr;
}

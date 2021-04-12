#include <fcntl.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include "maco.h"

void editor_insert_char(char c) {
	if (config.cursor_y == config.num_rows)
		editor_append_row("", 0);
	editor_row_insert_char(&config.rows[config.cursor_y], config.cursor_x, c);
	editor_move_cursor(ARROW_RIGHT);
}

void editor_save() {
	int fb_len, fd, errno;
	char *fb;
	
	if (config.filename == NULL)
		return;

	fb = editor_rows_to_string(&fb_len);
	errno = fd = open(config.filename, O_RDWR | O_CREAT, 0644);
	if (fd != -1) {
		if ((errno = ftruncate(fd, fb_len)) != -1) {
			if ((errno = write(fd, fb, fb_len)) != -1) {
				close(fd);
				free(fb);
				editor_set_message("%d bytes written to disk", fb_len);
				return;
			}
		}
		close(fd);
	}
	free(fb);
	editor_set_message("Can't save! I/O error: %s", strerror(errno));
}

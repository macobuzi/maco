#include <stdlib.h>
#include <string.h>
#include <termios.h>
#include "maco.h"

void buffer_append(struct buffer *bp, char *s, int len) {
	//write_log_str("buffer_append (screen)", s);
	
	char *new_text = realloc(bp->text, bp->len + len);

	if (new_text == NULL) return;
	memcpy(&new_text[bp->len], s, len);
	bp->text = new_text;
	bp->len += len;
}

void buffer_free(struct buffer *bp) {
	write_log("buffer_free (screen) \n", 0);
	free(bp->text);
}

#include <stdlib.h>
#include <string.h>
#include <termios.h>
#include "kilo.h"

void buffer_append(struct buffer *bp, char *s, int len) {
	char *new_text = realloc(bp->text, bp->len + len);

	if (new_text == NULL) return;
	memcpy(&new_text[bp->len], s, len);
	bp->text = new_text;
	bp->len += len;
}

void buffer_free(struct buffer *bp) {
	free(bp->text);
}

#ifndef MACO_H_
#define MACO_H_

#include <stdio.h>
#include <termios.h>
#include <time.h>

#define EMPTY_BUFFER {NULL, 0}
#define CTRL_KEY(k) ((k) & 0x1F)
#define VERSION "0.0.1"
#define TAB_SIZE 8
#define QUIT_TIME 3

struct row {
	char *text;
	int len;
	char *render_text;
	int render_len;
};

struct buffer {
	char *text;
	int len;
};

struct config {
	/* moving */
	int cursor_x;
	int cursor_y;
	int offset_x;
	int offset_y;
	int num_rows;
	struct row *rows;
	/* terminal info */
	int screen_rows;
	int screen_cols;
	int screen_cursor_x;
	int screen_cursor_y;
	struct termios orig_termios;
	/* status */
	char *filename;
	char status_msg[80];
	time_t status_msg_time;
	int dirty;
};

enum binding {
	BACKSPACE = 127,
	ARROW_LEFT = 1000,
	ARROW_RIGHT,
	ARROW_UP,
	ARROW_DOWN,
	PAGE_UP,
	PAGE_DOWN,
	DEL_KEY
};


extern FILE* logfp;
extern struct config config;
extern int quit_time;

/* terminal.c */
extern void die(const char*);
extern void disable_raw_mode();
extern void enable_raw_mode();
extern void clear_screen();
extern int  read_key();
extern int  get_window_size(int *rows, int *cols);
extern void write_log(char *pattern, int data);
extern void write_log_str(char *header, char *data);

/* buffer.c */
extern void buffer_append(struct buffer *bp, char *s, int len);
extern void buffer_free(struct buffer *bp);

/* maco.c */
extern void editor_init();
extern void editor_open(char *filename);
extern void editor_set_message(const char *fmt, ...);
extern void editor_draw_screen();
extern void editor_draw_status_bar(struct buffer *buffer);
extern void editor_draw_message_bar(struct buffer *buffer);

/* row.c */
extern void editor_append_row(char *text, size_t len);
extern void editor_update_row(struct row *row);
extern void editor_draw_rows();
extern void editor_row_insert_char(struct row *row, int at, char c);
extern void editor_row_delete_char(struct row *row, int at);
extern char* editor_rows_to_string(int *buffer_len);

/* key.c */
extern void editor_handle_key_press();
extern void editor_move_cursor(int c);
extern void editor_may_scroll();
extern void editor_may_align();

/* helper.c */
extern struct row *editor_current_row();
extern int editor_cursor_x_to_screen(struct row *row, int cursor_x);

/* action.c */
extern void editor_insert_char(char c);
extern void editor_delete_char();
extern void editor_save();
extern void quit();

#endif /* MACO_H_ */

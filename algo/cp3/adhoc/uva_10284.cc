#include <iostream>

using namespace std;

bool occupied(char *board, int row, int col) {
	return board[row*8+col] != 0;
}

void markIfValid(bool *attack, int row, int col) {
	if (row >= 0 && row <8 && col >= 0 && col < 8) {
		attack[row*8+col]=true;
	}
}

void attackByKing(int row, int col, bool *attack) {
	for (int i=-1; i<=1; i++) {
		for (int j=-1; j<=1; j++) {
			if (i==0 && j==0)
				continue;
			markIfValid(attack, row+i, col+j);
		}
	}
}

void attackByBishop(char *board, int row, int col, bool *attack) {
	for (int i=1; row-i>=0 && col-i>=0; i++) {
		if (occupied(board, row-i, col-i))
			break;
		markIfValid(attack, row-i, col-i);
	}
	for (int i=1; row+i>=0 && col+i>=0; i++) {
		if (occupied(board, row+i, col+i))
			break;
	    markIfValid(attack, row+i, col+i);
	}
	for (int i=1; row-i>=0 && col+i>=0; i++) {
		if (occupied(board, row-i, col+i))
			break;
	    markIfValid(attack, row-i, col+i);
	}
	for (int i=1; row+i>=0 && col-i>=0; i++) {
		if (occupied(board, row+i, col-i))
			break;
	    markIfValid(attack, row+i, col-i);
	}
}

void attackByRoot(char *board, int row, int col, bool *attack) {
	for (int i=1; col+i<8; i++) {
		if (occupied(board, row, col+i))
			break;
		markIfValid(attack, row, col+i);
	}
	for (int i=1; col-i>=0; i++) {
		if (occupied(board, row, col-i))
			break;
		markIfValid(attack, row, col-i);
	}
	for (int i=1; row+i<8; i++) {
		if (occupied(board, row+i, col))
			break;
		markIfValid(attack, row+i, col);
	}
	for (int i=1; row-i>=0; i++) {
		if (occupied(board, row-i, col))
			break;
		markIfValid(attack, row-i, col);
	}
}

void attackByQueen(char *board, int row, int col, bool *attack) {
	attackByBishop(board, row, col, attack);
	attackByRoot(board, row, col, attack);
}

void attackByPawn(int row, int col, int dir, bool *attack) {
	markIfValid(attack, row+dir, col-1);
	markIfValid(attack, row+dir, col+1);
}

void attackByKnight(int row, int col, bool *attack) {
	markIfValid(attack, row-2, col-1);
	markIfValid(attack, row-2, col+1);
	markIfValid(attack, row-1, col-2);
	markIfValid(attack, row-1, col+2);
	markIfValid(attack, row+2, col-1);
	markIfValid(attack, row+2, col+1);
	markIfValid(attack, row+1, col-2);
	markIfValid(attack, row+1, col+2);
}

void fillAttackSquare(char *board, int row, int col, bool *attack) {
	char piece = board[row*8+col];
	switch(piece) {
	case 'k':
	case 'K':
		attackByKing(row, col, attack);
		break;
	case 'q':
	case 'Q':
		attackByQueen(board, row, col, attack);
		break;
	case 'b':
	case 'B':
		attackByBishop(board, row, col, attack);
		break;
	case 'n':
	case 'N':
		attackByKnight(row, col, attack);
		break;
	case 'r':
	case 'R':
		attackByRoot(board, row, col, attack);
		break;
	case 'p':
		attackByPawn(row, col, 1, attack);
		break;
	case 'P':
		attackByPawn(row, col, -1, attack);
		break;
	}
}

int main() {
	string line;
	while (getline(cin, line) && !line.empty()) {
		char board[8*8] = {0};
		int row=0;
		int col=0;
		for (int i=0; i<line.length(); i++) {
			if (line[i] == '/') {
				row++;
				col=0;
			} else if (line[i] >= '0' && line[i] <= '9') {
				col += (line[i] - '0');
			} else {
				board[row*8 + col] = line[i];
				col++;
			}
		}

		bool attack[8*8] = {false};
		for (int row=0; row<8; row++) {
			for (int col=0; col<8; col++) {
				if (board[row*8+col]!=0) {
					fillAttackSquare(board, row, col, attack);
				}
			}
		}

		int free_cnt = 0;
		for (int row=0; row<8 ; row++) {
			for (int col=0; col<8; col++) {
				if (!occupied(board,row,col) && !attack[row*8+col]) {
					free_cnt++;
				}
			}
		}
		cout << free_cnt << endl;
	}
}

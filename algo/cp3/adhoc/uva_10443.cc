/*
  R -> P -> S -> R
*/

#include <iostream>

using namespace std;

bool foundNeighbor(char* grid, int r, int c, int i, int j, char target) {
	for (int dc=-1; dc<=1; dc++) {
		for (int dr=-1; dr<=1; dr++) {
			if ((dc == 0 && dr == 0) || (dc != 0 && dr != 0)) {
				continue;
			}
			int nr = i + dr;
			int nc = j + dc;
			if (nr>=0 && nr<r && nc>=0 && nc<c && grid[nr * c + nc] == target) {
				return true;
			}
		}
	}
	return false;
}

int main() {
	int t;
	cin >> t;
	while (t--) {
		int r, c, n;
		cin >> r >> c >> n;
		char grid[r*c], tmp[r*c];
		for (int i=0; i<r; i++) {
			string s;
			cin >> s;
			for (int j=0; j<c; j++) {
				grid[i*c + j] = s[j];
			}
		}

		while(n--) {
			for (int i=0; i<r; i++) {
				for (int j=0; j<c; j++) {
					char v = grid[i*c + j];
					tmp[i*c + j] = v;
					if (v == 'R' && foundNeighbor(grid, r, c, i, j, 'P')) {
						tmp[i*c + j] = 'P';
					} else if (v == 'P' && foundNeighbor(grid, r, c, i, j, 'S')) {
						tmp[i*c + j] = 'S';
					} else if (v == 'S' && foundNeighbor(grid, r, c, i, j, 'R')) {
						tmp[i*c + j] = 'R';
					}
				}
			}
			for (int i=0; i<r; i++) {
				for (int j=0; j<c; j++) {
					grid[i*c + j] = tmp[i*c + j];
				}
			}
		}
		for (int i=0; i<r; i++) {
			for (int j=0; j<c; j++) {
				cout << grid[i*c + j];
			}
			cout << endl;
		}
		if (t > 0) {
			cout << endl;
		}
	}
}

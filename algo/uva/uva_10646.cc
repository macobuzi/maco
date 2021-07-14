#include <iostream>

using namespace std;

#define DECK_SIZE 52
#define SPLIT_NUM 25

int getValue(char c) {
	if (c >= '2' && c <= '9') {
		return c - '0';
	}
	return 10;
}

int main() {
	int t;
	cin >> t;
	string deck[DECK_SIZE];
	for (int c=1; c<=t; c++) {
		for (int i=0; i<DECK_SIZE; i++) {
			cin >> deck[i];
		}
		int y = 0;
		int pick = DECK_SIZE - SPLIT_NUM - 1;
		for (int j=0; j<3; j++) {
			string card = deck[pick];
			int x = getValue(card[0]);
			y += x;
			pick -= (10 - x + 1);
		}
		int ans = SPLIT_NUM + (y - pick);
		cout << "Case " << c << ": " << deck[ans] << endl;
	}
}

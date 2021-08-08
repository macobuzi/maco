/*
  how to make sure prince win ?
  a > b > c
  x, y
  
  bruteforce
  36 * 52 
*/

#include <iostream>
#include <algorithm>

using namespace std;

bool isPrinceWin(int princess_hand[3], int prince_hand[3]) {
	int princess_play[3] = {0,1,2};
	for (int i=0; i<6; i++) {
		int prince_play[3] = {0,1,2};
		for (int j=0; j<6; j++) {
			int princess_score=0, prince_score=0;
			for (int k=0; k<3; k++) {
				if (princess_hand[princess_play[k]] > prince_hand[prince_play[k]]) {
					princess_score++;
				} else {
					prince_score++;
				}
			}
			if (prince_score < princess_score) {
				return false;
			}
			next_permutation(prince_play, prince_play+3);
		}
		next_permutation(princess_play, princess_play+3);
	}

	return true;
}

int main() {
	int princess_hand[3], prince_hand[3];
	int a,b,c,x,y;
	cin >> a >> b >> c >> x >> y;
	while (a || b || c || x || y) {
		princess_hand[0] = a;
		princess_hand[1] = b;
		princess_hand[2] = c;
		prince_hand[0] = x;
		prince_hand[1] = y;

	    int z = 1;
		for (; z<=52; z++) {
			if (z!=a && z!=b && z!=c && z!=x && z!=y) {
				prince_hand[2] = z;
				if (isPrinceWin(princess_hand, prince_hand)) {
					break;
				}
			}
		}
		if (z <= 52) {
			cout << z << endl;
		} else {
			cout << -1 << endl;
		}
		cin >> a >> b >> c >> x >> y;
	}
}

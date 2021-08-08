#include <iostream>

using namespace std;

bool isValidWithWeight(char c, int weight, string left[], string right[], string result[]) {
	for (int i=0; i<3; i++) {
		int leftSum=0, rightSum=0;
		for (int j=0; j<left[i].length(); j++) {
			leftSum += (left[i][j] == c) ? weight : 1;
			rightSum += (right[i][j] == c) ? weight : 1;
		}
		switch (result[i][0]) {
		case 'e':
			if (leftSum != rightSum) return false;
			break;
		case 'u':
			if (leftSum <= rightSum) return false;
			break;
		case 'd':
			if (leftSum >= rightSum) return false;
			break;
		}
	}
	return true;
}

int main() {
	int t;
	cin >> t;
	while (t--) {
		string left[3], right[3], result[3];
		for (int i=0; i<3; i++) {
			cin >> left[i] >> right[i] >> result[i];
		}
		for (char c='A'; c<='L'; c++) {
			if (isValidWithWeight(c, 100, left, right, result)) {
				cout << c << " is the counterfeit coin and it is heavy.\n";
				break;
			}
			if (isValidWithWeight(c, -100, left, right, result)) {
				cout << c << " is the counterfeit coin and it is light.\n";
				break;
			}
		}
	}
}

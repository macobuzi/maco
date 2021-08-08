#include <iostream>

using namespace std;

int ccw(int hand, int num) {
	return (num >= hand) ? (num - hand) * 9 : (40 - hand + num) * 9;
}

int cw(int hand, int num) {
	return (hand >= num) ? (hand - num) * 9 : (hand + 40 - num) * 9;
}

int main() {
	int hand, num1, num2, num3;
	while((cin >> hand >> num1 >> num2 >> num3), hand || num1 || num2 || num3) {
		int sum = 2 * 360;
		int d1 = cw(hand, num1);
		//cout << "d1 = " << d1 << endl;
		sum += d1;
		hand = num1;

		sum += 360;
		int d2 = ccw(hand, num2);
//		cout << "d2 = " << d2 << endl;
		sum += d2;
		hand = num2;

		int d3 = cw(hand, num3);
//		cout << "d3 = " << d3 << endl;
		sum += d3;
		cout << sum << endl;
	}
}

#include <iostream>
#include <string>
#include <algorithm>

using namespace std;

bool comparator(char c1, char c2) {
	return tolower(c1) == tolower(c2) ?
		c1 < c2 : tolower(c1) < tolower(c2);
}

int main() {
	int t;
	cin >> t;
	while (t--) {
		string s;
		cin >> s;
		sort(s.begin(), s.end(), comparator);
		do {
			cout << s << endl;
		} while (next_permutation(s.begin(), s.end(), comparator));
	}
}

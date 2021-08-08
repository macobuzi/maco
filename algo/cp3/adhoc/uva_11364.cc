#include <iostream>
#include <algorithm>

using namespace std;

int main() {
	int t, n;

	cin >> t;
	while(t--) {
		cin >> n;
		int l=100;
		int r=-1;
		int s;
		for (int i=0; i<n; i++) {
			cin >> s;
			l = min(l, s);
			r = max(r, s);
		}
		cout << (r-l) * 2 << endl;
	}
}

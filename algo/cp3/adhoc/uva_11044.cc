#include <iostream>
#include <cmath>

using namespace std;

int main() {
	int t;
	cin >> t;
	while (t--) {
		int n, m;
		cin >> n >> m;
		int ans = (ceil((double)(n-2) / 3.0)) * (ceil((double)(m-2) / 3.0));
		cout << ans << endl;
	}
}

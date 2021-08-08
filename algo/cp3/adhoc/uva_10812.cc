#include <iostream>

using namespace std;

int main() {
  	int n;
	cin >> n;
	while (n-- > 0) {
		long long s,d;
		cin >> s >> d;
		long long a = (s + d) / 2;
		long long b = (s - d) / 2;
		if (a >= 0 && b >= 0 && (a+b) == s && (a-b) == d) {
			cout << a << " " << b << endl;
		} else {
			cout << "impossible" << endl;
		}
	}
}

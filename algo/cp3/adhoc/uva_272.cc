#include <iostream>
#include <cstdio>

using namespace std;

int main() {
	bool open = false;
	char c;
	while ((c = getchar()) != EOF) {
		if (c == '"') {
			open = !open;
			if (open) {
				cout << "``";
			} else {
				cout << "''";
			}
		} else {
			cout << c;
		}
	}
}

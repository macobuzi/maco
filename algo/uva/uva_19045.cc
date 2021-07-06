#include <iostream>
#include <string>

using namespace std;

bool isPalindrome(string s) {
	int lo=0, hi=s.length()-1;
	while (lo < hi) {
		if (s[lo] != s[hi]) {
			return false;
		}
		lo++;
		hi--;
	}
	return true;
}

int main() {
	string s;
	while(getline(cin, s) && s != "DONE") {
		string s1;
		int len = s.length();
		for (int i=0; i<len; i++) {
			if (isalpha(s[i])) {
				s1 += tolower(s[i]);
			}
		}
		if (isPalindrome(s1)) {
			cout << "You won't be eaten!\n";
		} else {
			cout << "Uh oh..\n";
		}
	}
}

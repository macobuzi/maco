#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

string hashstr(string word) {
	word.erase(remove(word.begin(), word.end(), ' '), word.end());
	sort(word.begin(), word.end());
	return word;
}

int main() {
	int t;
	cin >> t;
	cin.ignore(2);

	while (t--) {
		string s;
		vector<string> words;

		while(getline(cin, s) && s != "") {
			words.push_back(s);
		}

		sort(words.begin(), words.end());

		for (int i=0; i<words.size(); i++) {
			for (int j=i+1; j<words.size(); j++) {
				if (hashstr(words[i]) == hashstr(words[j])) {
					cout << words[i] << " = " << words[j] << endl;
				}
			}
		}

		if (t) {
			cout << endl;
		}
	}
}

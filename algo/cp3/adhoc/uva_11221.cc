#include <string>
#include <iostream>
#include <cmath>
#include <cstdio>

using namespace std;

bool isSquarePalindrome(string s, int k) {
	string s1, s2, s3, s4;
	for (int r=0; r<k; r++) {
		for (int c=0; c<k; c++) {
			s1 += s[r*k+c];
			s2 += s[c*k+r];
		}
	}
	
    for (int r=k-1; r>=0; r--) {
		for (int c=k-1; c>=0; c--) {
			s3 += s[r*k+c];
			s4 += s[c*k+r];
		}
	}

	//cout<<s1<<endl;
	//cout<<s2<<endl;
	//cout<<s3<<endl;
	//cout<<s4<<endl;

	return s1==s2 && s2==s3 && s3==s4 && s4==s1;
}

int main() {
	int t;
	cin >> t;
	cin.ignore();

	for (int c=1; c<=t; c++) {
		string line;
		getline(cin, line);
		string s;
		for (int i=0; i<line.size(); i++) {
			if (line[i] >= 'a' && line[i] <= 'z') {
				s += line[i];
			}
		}
		//cout<<s<<endl;
		int len = s.length();
		int k = sqrt(len);
		//cout<<k<<endl;
		printf("Case #%d:\n", c);
	    if (k*k == len && isSquarePalindrome(s, k)) {
			printf("%d\n", k);
		} else {
			puts("No magic :(");
		}
	}
}

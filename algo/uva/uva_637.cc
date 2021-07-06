/*
  # sheet (M) = (n + 3) / 4
  F(1) = 1,2
  14+3/4 = 4
  F1 = _ |1|2|_
  F2 = 14|3|4|13
  F3 = 12|5|6|11
  F4 = 10|7|8|9
*/

#include <iostream>
#include <string>

using namespace std;

struct Sheet {
	int front1;
	int front2;
	int back1;
	int back2;

	Sheet(): front1(0), front2(0), back1(0), back2(0) {}
};

string print(int page) {
	return page == 0 ? "Blank" : to_string(page);
} 
	
int main() {
	int pageNum;
	cin >> pageNum;
	while (pageNum > 0) {
		int sheetNum = (pageNum + 3) / 4;
		Sheet sheets[sheetNum];
		int page = 1;
		for (int i=0; page <= pageNum && i<sheetNum; i++) {
			sheets[i].front2 = page++;
			if (page <= pageNum) {
				sheets[i].back1 = page++;
			}
		}
		for (int i=sheetNum-1; page <= pageNum && i>=0; i--) {
			sheets[i].back2 = page++;
			if (page <= pageNum) {
				sheets[i].front1 = page++;
			}
		}

		cout << "Printing order for " << pageNum << " pages:" << endl;
		for (int i=0; i<sheetNum; i++) {
			Sheet sheet = sheets[i];
			cout << "Sheet " << (i+1) << ", "
			     << "front: "<< print(sheet.front1) << ", " << print(sheet.front2) << endl;
			if (sheet.back1 > 0 || sheet.back2 > 0) {
				cout << "Sheet " << (i+1) << ", "
					 << "back : " << print(sheet.back1) << ", " << print(sheet.back2) << endl;
			}
		}
		
		cin >> pageNum;
	}
}

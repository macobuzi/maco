#include <iostream>
#include <utility>
#include <unordered_map>

using namespace std;

#define SHEET_SIZE 5
#define TOKEN_NUM 75

int main() {
    int t;
    cin >> t;
    
    while(t--) {
        int token;
        
        unordered_map<int, pair<int, int>> bingo;
        for (int row=0; row<SHEET_SIZE; row++) {
            for (int col=0; col<SHEET_SIZE; col++) {
                if (!(row == 2 && col == 2)) {
                   cin >> token;
                   bingo.insert({token, make_pair(row, col)}); 
                }
            }
        }
        
        bool sheet[SHEET_SIZE*SHEET_SIZE] = {false};
        sheet[2*SHEET_SIZE+2] = true;
        bool found = false;
        for (int i=0; i<TOKEN_NUM; i++) {
            cin >> token;
            if (found) {
                continue;
            }
                
            if (bingo.find(token) != bingo.end()) {
                pair<int, int> loc = bingo[token];
                sheet[loc.first*SHEET_SIZE + loc.second] = true;
                
                // check win
                bool won = false;
                for (int row=0; row<SHEET_SIZE && !won; row++) {
                    won = true;
                    for (int col=0; col<SHEET_SIZE && won; col++) {
                        if (!sheet[row*SHEET_SIZE+col]) {
                            won = false;
                        }
                    }
                }
                
                for (int col=0; col<SHEET_SIZE && !won; col++) {
                    won = true;
                    for (int row=0; row<SHEET_SIZE && won; row++) {
                        if (!sheet[row*SHEET_SIZE+col]) {
                            won = false;
                        }
                    }
                }
                        
                if (!won) {        
                    won = true;
                    for (int i=0; i<SHEET_SIZE && won; i++) {
                        if (!sheet[i*SHEET_SIZE+i]) {
                            won = false;
                        }
                    }
                }
                
                if (!won) {
                    won = true;
                    for (int i=0; i<SHEET_SIZE && won; i++) {
                        if (!sheet[i*SHEET_SIZE + (SHEET_SIZE-1-i)]) {
                            won = false;
                        }
                    }
                }
                
                if (won) {
                    cout << "BINGO after " << (i+1) << " numbers announced" << endl;
                    found = true;
                }
            }
        }
    }
}

#include <iostream>

using namespace std;

int main() {
    long long sz, p;
    while ((cin >> sz >> p) && sz && p) {
        long long lvl = 1;
        for (;lvl<=sz; lvl+=2) {
            long long maxLvlId = lvl * lvl;
            if (p <= maxLvlId) {
                break;
            }
        }
        //cout << "lvl = " << lvl << endl;
        long long id = lvl > 1 ? (lvl-2) * (lvl-2) + 1 : 1; 
        int min = (sz/2) + 1 - (lvl/2);
        int max = (sz/2) + 1 + (lvl/2);
        int row = max;
        int col = id > 1 ? max - 1 : max;
        //cout << "id = " << id << ", min = " << min << ", max = " << max 
           //  << ", row = " << row << ", col = " << col << endl;
        while (id < p && col > min) {
            col--;
            id++;
            //cout << "id = " << id << ", row = " << row << ", col = " << col << endl;
        }
        while (id < p && row > min) {
            row--;
            id++;
        }
        while (id < p && col < max) {
            col++;
            id++;
        }
        while (id < p && row < max) {
            row++;
            id++;
        }
        //cout << "Id = " << id << endl; 
        cout << "Line = " << row << ", column = " << col << ".\n"; 
    }
}

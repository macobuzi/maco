/*
   3x3 grid
   f(g) = sum(adj(g))
   f(0)(g) = g
   f(i+1)(g) = f(f(i)(g))
   h -> k(g)(h) -> h = f(i)(g)
    
*/
#include <iostream>

using namespace std;

int main() {
   int t;
   char c;
   cin >> t;
   int grid[3][3];
   int tmp[3][3];
   while (t--) {
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                cin >> c;
                grid[i][j] = c-'0';
            }
        }

        int fi = 0;
        // transform
        while (true) {
            bool isAllZero = true;
            for (int r=0; r<3; r++) {
                for (int c=0; c<3; c++) {
                    if (grid[r][c] == 1) {
                        isAllZero = false;
                        break;
                    }
                }
            }
            if (isAllZero) {
                cout << (fi-1) << endl;
                break;
            }
            fi++;
            //cout << fi << endl;
            for (int r=0; r<3; r++) {
                for (int c=0; c<3; c++) {
                    int sum = 0;
                    if (r > 0) sum += grid[r-1][c];
                    if (r < 2) sum += grid[r+1][c];
                    if (c > 0) sum += grid[r][c-1];
                    if (c < 2) sum += grid[r][c+1];
                    tmp[r][c] = sum % 2;
                    //cout << tmp[r][c];
                }
                //cout << endl;
            }
            for (int r=0; r<3; r++) {
                for (int c=0; c<3; c++) {
                    grid[r][c] = tmp[r][c];
                }
            }
        }
        //cout << endl;
   }
}

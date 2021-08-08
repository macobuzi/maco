#include <iostream>
#include <vector>

using namespace std;

bool neighbor(int i, int j) {
    int x = i xor j;
    int rmb = x & (-x);
    return rmb == x;
}

int main() {
    int dim;
    vector<int> cws;
    vector<int> pots;

    while(cin >> dim) {
        int cn = (1 << dim);
        cws.assign(cn, 0);
        pots.assign(cn, 0);
        for (int i=0; i<cn; i++) {
            cin>>cws[i];
        }
        for (int i=0; i<cn; i++) {
            for (int j=0; j<cn; j++) {
                if (i!=j && neighbor(i,j)) {
                    pots[i] += cws[j];
                }
            }
        }
        int maxPot = 0;
        for (int i=0; i<cn; i++) {
            for (int j=0; j<cn; j++) {
                if (i!=j && neighbor(i, j)) {
                    maxPot = max(maxPot, pots[i] + pots[j]);
                }
            }
        }
        cout << maxPot << endl;
    }
}

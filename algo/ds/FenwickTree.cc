#include <iostream>
#include <vector>

using namespace std;

class FenwickTree {
private:
    vector<int> ft;

    int leastSBit(int x) {
        return x & (-x);
    }

public:
    FenwickTree(int n) {
        ft.assign(n+1, 0);
    }

    void add(int i, int v) {
        for (; i < (int)ft.size(); i += leastSBit(i)) {
            ft[i] += v;
        }
    }

    int rsq(int i) { // [1..i]
        int ans = 0;
        for (; i > 0; i -= leastSBit(i)) {
            ans += ft[i];
        }
        return ans;
    }

    int rsq(int i, int j) { // [i..j]
        return rsq(j) - ( i > 1 ? rsq(i-1) : 0 );
    }
};

int main() {
    int f[] = {2,4,5,5,6,6,6,7,7,8,9};
    FenwickTree ft(10);
    for (int i=0; i<11; i++) {
        ft.add(f[i], 1);
    }
    cout << ft.rsq(1,1) << endl;
    cout << ft.rsq(1,2) << endl;
    cout << ft.rsq(1,6) << endl;
    cout << ft.rsq(1,10) << endl;
    cout << ft.rsq(3,6) << endl;
    ft.add(5, 2);
    cout << ft.rsq(1,10) << endl;
}

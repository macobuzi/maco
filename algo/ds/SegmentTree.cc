#include <iostream>
#include <vector>

using namespace std;

class SegmentTree {
private:
    vector<int> st;
    vector<int> a;
    int n;

    int left(int p) {
        return p<<1;
    }
    
    int right(int p) {
        return (p<<1)+1;
    }

    void build(int p, int lo, int hi) {
        if (lo == hi) {
            st[p] = lo;
        } else {
            int mid = (lo + hi) / 2;
            build(left(p), lo, mid);
            build(right(p), mid+1, hi);
            int pminLeft = st[left(p)];
            int pminRight = st[right(p)];
            st[p] = (a[pminLeft] <= a[pminRight]) ? pminLeft : pminRight;
        }
    }

    int rmq(int p, int lo, int hi, int i, int j) {
        if (i > hi || j < lo) { // i..j..lo..hi or lo..hi..i..j (out of range)
            return -1;
        }
        if (lo >= i && hi <= j) { // i..lo..hi..j (inside range)
            return st[p];
        }
        // lo..i..j..hi or lo..i..hi..j or i..lo..j..hi
        int mid = (lo + hi) / 2;
        int pminLeft = rmq(left(p), lo, mid, i, j);
        int pminRight = rmq(right(p), mid+1, hi, i, j);
        if (pminLeft == -1) {
            return pminRight;
        }
        if (pminRight == -1) {
            return pminLeft;
        }
        return (a[pminLeft] <= a[pminRight]) ? pminLeft : pminRight;
    }

public:
    SegmentTree(const vector<int> &_a) {
        a = _a;
        n = (int) a.size();
        st.assign(4*n, 0);
        build(1, 0, n-1);
    }

    int rmq(int i, int j) {
        return rmq(1, 0, n-1, i, j);
    }
};

int main() {
    int arr[] = {18, 17, 13, 19, 15, 11, 20};
    vector<int> a(arr, arr+7);
    SegmentTree st(a);
    cout << "RMQ(1,3) = " << st.rmq(1,3) << endl;
    cout << "RMQ(4,6) = " << st.rmq(4,6) << endl;
}


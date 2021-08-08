#include <iostream>
#include <vector>

using namespace std;

class SegmentTree {
    private:
        vector<char> st;
        int n;

        int left(int p) {
            return p << 1;
        }

        int right(int p) {
            return (p << 1) + 1;
        }

        char getChar(int v) {
            return (v == 0) ? '0' : (v < 0) ? '-' : '+';
        }

        char combine(char c1, char c2) {
            if (c1 == '0' || c2 == '0')
                return '0';
            if (c1 == c2) 
                return '+';
            return '-';
        }

        int build(int p, int lo, int hi, const vector<int> &seq) {
            if (lo == hi) {
                st[p] = getChar(seq[lo]);
            } else {
                int mid = (lo + hi) / 2;
                char leftChar = build(left(p), lo, mid, seq);
                char rightChar = build(right(p), mid+1, hi, seq);
                st[p] = combine(leftChar, rightChar);
            }
            return st[p];
        }

        char query(int p, int lo, int hi, int i, int j) {
            if (i > hi || j < lo) 
                return -1;
            if (i<=lo && hi<=j)
                return st[p];
            int mid = (lo + hi) / 2;
            char leftChar = query(left(p), lo, mid, i, j);
            char rightChar = query(right(p), mid+1, hi, i, j);
            if (leftChar == -1)
                return rightChar;
            if (rightChar == -1)
                return leftChar;
            return combine(leftChar, rightChar);
        }

        char update(int p, int lo, int hi, int i, int v) {
            if (i > hi || i < lo)
                return st[p];
            if (lo == hi && lo == i) {
                st[p] = getChar(v);
                return st[p];
            }
            int mid = (lo + hi) / 2;
            char leftChar = update(left(p), lo, mid, i, v);
            char rightChar = update(right(p), mid+1, hi, i, v);
            st[p] = combine(leftChar, rightChar);
            return st[p];
        }

    public:
        SegmentTree(vector<int> seq) {
            n = seq.size();
            st.assign(4*n, 0);
            build(1, 0, n-1, seq);
        }

        char query(int i, int j) {
            return query(1, 0, n-1, i, j);
        }

        void update(int i, int v) {
            update(1, 0, n-1, i, v);
        }
};

int main() {
    int seqLen, roundNum;
    while(cin >> seqLen) {
        cin >> roundNum;
        vector<int> seq;
        seq.assign(seqLen, 0);
        for (int i=0; i<seqLen; i++) {
            cin >> seq[i];
        }
        SegmentTree st(seq);
        for (int j=0; j<roundNum; j++) {
            char cmd;
            cin >> cmd;
            if (cmd == 'C') {
                int index, val;
                cin >> index >> val;
                st.update(index - 1, val);
            } else {
                int start, end;
                cin >> start >> end;
                cout << st.query(start-1,end-1);
            }
        }
        cout << endl;
    }
}

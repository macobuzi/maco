#include <iostream>
#include <vector>

using namespace std;

class UnionFind {
    private:
        vector<int> parents;
        vector<int> ranks;
    public:
        UnionFind(int n) {
            ranks.assign(n, 0);
            for (int i=0; i<n; i++) {
                parents.push_back(i);
            }
        }

        void unionSet(int i, int j) {
            int si = findSet(i);
            int sj = findSet(j);
            if (si == sj) {
                return;
            }
            if (ranks[si] == ranks[sj]) {
                ranks[sj]++;
            }
            if (ranks[si] > ranks[sj]) {
                parents[sj] = si;
            } else {
                parents[si] = sj;
            }
        }

        int findSet(int i) {
            return (parents[i] == i) ? i : (parents[i] = findSet(parents[i]));
        }

        bool isSameSet(int i, int j) {
            return findSet(i) == findSet(j);
        }
};

int main() {
    UnionFind uf(10);
    uf.unionSet(0,3);
    uf.unionSet(1,3);
    cout << "Is 0 and 1 same set" << " " << uf.isSameSet(0, 1) << endl;
    cout << "Is 0 and 9 same set" << " " << uf.isSameSet(0, 9) << endl;
}

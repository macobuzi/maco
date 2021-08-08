#include <iostream>
#include <unordered_map>
#include <algorithm>
using namespace std;

int main() {
    int t;
    cin >> t;
    unordered_map<int, int> lastLoc;
    unordered_map<int, int>::iterator it;

    while (t--) {
        int n;
        cin >> n;
        int snowflake;
        int maxSize = 0;
        int start = 0;
        lastLoc.clear();
        for (int i=0; i<n; i++) {
            cin >> snowflake;
            it = lastLoc.find(snowflake);
            if (it == lastLoc.end() || it->second < start) {
                int size = i - start + 1;
                maxSize = max(maxSize, size);
            } else {
                start = it->second+1;
            }
            lastLoc[snowflake] = i;
        }
        maxSize = max(maxSize, n - start);
        cout << maxSize << endl;
    }
}

#include <iostream>
#include <map>
#include <string>
#include <cstdio>
#include <utility>

using namespace std;

int main() {
    int t;
    map<string, int> populations;
    string tree;

    scanf("%d\n\n", &t);
    while(t--) {
        map<string, int> populations;
        int total = 0;
        while(getline(cin, tree) && !tree.empty()) {
            if (populations.count(tree) > 0) {
                populations[tree]++;
            } else {
                populations[tree] = populations[tree] + 1;
            }
            total++;
        }
        map<string, int>::iterator it = populations.begin();
        for (; it != populations.end(); it++) {
            float pct = (float)(100 * it->second) / (float)total;
            printf("%s %.4lf\n", it->first.c_str(), pct);
        }
        if (t > 0) {
            printf("\n");
            populations.clear();
        }
    }
}

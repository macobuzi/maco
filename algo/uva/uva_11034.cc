#include <iostream>
#include <string>
#include <queue>

using namespace std;

int main() {
    int c;
    cin >> c;
    queue<int> leftq, rightq;
    while (c--) {
        int ferrylen, waitlen;
        cin >> ferrylen >> waitlen;
        ferrylen *= 100;
        for (int i=0; i<waitlen; i++) {
            int carlen;
            string side;
            cin >> carlen >> side;
            if (side == "left") {
                leftq.push(carlen);
            } else {
                rightq.push(carlen);
            }
        }
        bool leftSide = true;
        int tripcnt = 0;
        while (!leftq.empty() || !rightq.empty()) {
            tripcnt++;
            int sumlen = 0;
            if (leftSide) {
                while(!leftq.empty() && sumlen + leftq.front() <= ferrylen) {
                    sumlen += leftq.front();
                    leftq.pop();
                }
            } else {
                while(!rightq.empty() && sumlen + rightq.front() <= ferrylen) {
                    sumlen += rightq.front();
                    rightq.pop();
                }
            }
            leftSide = !leftSide;
        }
        cout << tripcnt << endl;
    }
}

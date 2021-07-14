#include <iostream>

using namespace std;

int main() {
    int seqLen, roundNum;
    cin >> seqLen >> roundNum;
    int seq[seqLen];
    for (int i=0; i<seqLen; i++) {
        cin >> seq[i];
    }
    for (int j=0; j<roundNum; j++) {
        char cmd;
        cin >> c;
        if (c == 'C') {
            int oval, nval;
            cin >> oval >> nval;
        } else {
            int start, end;
            cin >> start >> end;
        }
    }
}

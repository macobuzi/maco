#include <iostream>
#include <string>
#include <list>

using namespace std;

int main() {
    string line;
    
    while(getline(cin, line)) {
        list<char> output;
        list<char>::iterator it;
        bool insertMode=false;
        for (int i=0; i<line.length(); i++) {
            switch(line[i]) {
                case '[':
                    it = output.begin();
                    insertMode = true;
                    break;
                case ']':
                    insertMode = false;
                    break;
                default:
                    if (insertMode) {
                        output.insert(it, line[i]);
                    } else {
                        output.push_back(line[i]);
                    }
            }           
        }
        for (it=output.begin(); it!=output.end(); ++it) {
            cout << *it;
        }
        cout << endl;
    }
}


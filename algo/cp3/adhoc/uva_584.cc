#include <iostream>
#include <vector>

using namespace std;

int main() {
	string line;
	getline(cin, line);
	while (line != "Game Over") {
		vector<char> rolls;
		int counter = 0;
		int startOfFrameTenth=0;
		for (int i=0; i<line.length(); i+=2) {
			rolls.push_back(line[i]);
			counter++;
			if (line[i] == 'X')
				counter++;
			if (counter == 18)
				startOfFrameTenth=rolls.size();
		}
		//cout << endOfFrameTenth << ' ' << rolls.size() << endl;
		int score = 0;
		int pinDown[rolls.size()];
		for (int j=rolls.size()-1; j>=0; j--) {
			switch(rolls[j]) {
			case '/':
				pinDown[j] = 10 - (rolls[j-1] - '0');
				score += pinDown[j];
				if (j < startOfFrameTenth) {
					score += pinDown[j+1];
				}
				break;
			case 'X':
				pinDown[j] = 10;
				score += pinDown[j];
				if (j < startOfFrameTenth) {
					score += (pinDown[j+1] + pinDown[j+2]);
				}
				break;
			default:
				pinDown[j] = rolls[j] - '0';
				score += pinDown[j];
				break;
			}
			//cout << rolls[j] << ' ' << score << endl;
		}
		cout << score << endl;
		getline(cin, line);
	}
}

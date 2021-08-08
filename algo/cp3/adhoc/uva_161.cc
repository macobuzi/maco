#include <cstdio>
#include <vector>

using namespace std;

bool isAllLightsOnAfterOrange(vector<int> lights, int time) {
	bool hasOrange = false;
	for (int i=0; i<lights.size(); i++) {
		if (!((time % (2*lights[i])) < lights[i]-5)) {
			return false;
		}
		if (time >= lights[i]-5) {
			hasOrange = true;
		}
	}
	return hasOrange;
}

int main() {
	int a,b,c;
	while(scanf("%d%d%d", &a, &b, &c) && (a || b || c)) {
		vector<int> lights;
		lights.push_back(a);
		lights.push_back(b);
		while (c) {
			lights.push_back(c);
			scanf("%d", &c);
		}

		bool found = false;
		int time=1;
		for (; time <= 18000 && !found; time++) {
			if (isAllLightsOnAfterOrange(lights, time)) {
				found = true;
			}
		}
		if (found) {
			time--;
			printf("%02d:%02d:%02d\n", time/3600, (time%3600)/60, time%60);
		} else {
			puts("Signals fail to synchronise in 5 hours");
		}
	}
}

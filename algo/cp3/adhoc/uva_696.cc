#include <iostream>
#include <algorithm>

using namespace std;

/*
Case analysis:
  * min(m,n)=1 
  -> #=max(m,n)
  * min(m,n)=2 
  -> divide to 2*4 square, each square can put 4 knights
  -> n=2, #=(m/4)*4 + ((m%4==0) ? 0 : (m%4==1) ? 2 : 4)
  ooxxooxxoox
  ooxxooxxoox
  * min(m,n) > 2 
  -> space in between
  -> #= (n * m + 1) / 2 (?)
  oxoxoxox
  xoxoxoxo
  oxoxoxox
*/

int main() {
	int m,n;
	cin >> m >> n;
	while(m != 0 && n != 0) {
		int ans = 0;
		int max = std::max(m, n);
		int min = std::min(m, n);
		if (min == 1) {
			ans = max;
		} else if (min == 2) {
			ans = (max/4) * 4 + ((max%4==0) ? 0 : (max%4==1) ? 2 : 4);
		} else {
			ans = (max * min + 1) / 2;
		}
		cout << ans << " knights may be placed on a "
			 << m << " row " << n << " column board." <<endl;
		cin >> m >> n;
	}
}

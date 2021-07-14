#include <iostream>
#include <cstdio>

int main() {
   int s,b;
   int left[100005], right[100005];

   while(scanf("%d %d", &s, &b), s||b) {
        for (int i=1; i<=s; i++) {
            left[i] = i-1;
            right[i] = i+1;
        }
        left[1] = -1;
        right[s] = -1;
        int l, r;
        while(b--) {
            scanf("%d %d", &l, &r);
            if (left[l] != -1)
                printf("%d", left[l]);
            else
                printf("*");
            if (right[r] != -1)
                printf(" %d\n", right[r]);
            else
                printf(" *\n");
            left[right[r]] = left[l];
            right[left[l]] = right[r];
        }
        printf("-\n");
   }
}

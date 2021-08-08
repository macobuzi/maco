#include <stdio.h>
#include <string.h>

using namespace std;

#define MAX_LEN 100
#define VERT_NUM 26

int dfs(int *graph, int *visited, int u) {
    int cnt = 1;
    for (int v=0; v<VERT_NUM; v++) {
        if (!visited[v] && graph[u*VERT_NUM+v]) {
            visited[v] = 1;
            cnt += dfs(graph, visited, v); 
        }
    }
    return cnt;
}

int main() {
    int t;
    char line[MAX_LEN];
    scanf("%d\n", &t);
    while(t--) {
        int graph[VERT_NUM * VERT_NUM] = {0};
        int visited[VERT_NUM] = {0}; 
        while(fgets(line, MAX_LEN, stdin) && line[0] != '*') { 
            int u = line[1] - 'A'; 
            int v = line[3] - 'A';
            graph[u*VERT_NUM + v] = 1;
            graph[v*VERT_NUM + u] = 1;
        }
        fgets(line, MAX_LEN, stdin);
        int treecnt = 0;
        int acorncnt = 0;
        for (int i=0; line[i] != '\n'; i++) {
            int v = line[i] - 'A';
            if (line[i] != ',' && !visited[v]) {
               visited[v] = 1;
               int cnt = dfs(graph, visited, v);
               //printf("--- Visit %c, cnt = %d, acorncnt = %d\n", line[i], cnt, acorncnt);
               if (cnt == 1) {
                  acorncnt++;
               } else {
                  treecnt++;
               }
            }
        }
        printf("There are %d tree(s) and %d acorn(s).\n", treecnt, acorncnt);
    }
}
